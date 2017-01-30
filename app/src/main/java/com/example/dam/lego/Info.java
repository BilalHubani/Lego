package com.example.dam.lego;

import android.content.Context;
import android.media.Image;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dam on 30/1/17.
 */

public class Info {
    private String time;
    private ArrayList<Information> infos;
    public class Information {
        String set_num;
        String name;
        int year;
        int theme_id;
        int num_parts;
        String set_img_url;
    }

    public Info() {
        time = null;
        infos = new ArrayList<Information>();
    }
    public Information getInformation(String info){
        for (Information i: infos){
            if (i.name.equals(infos))return i;
        }
        return null;
    }
    public ArrayList<Information> getInfos() { return this.infos; }
    public String getTime() { return time; }
    public boolean loadFromFile(Context context) {
        BufferedReader reader = null;
        try {
            File dir = context.getExternalFilesDir(null);
            if (dir == null) return false;
            File f = new File(dir, "lego.csv");
            if (!f.exists()) return false;
            reader = new BufferedReader(new FileReader(f));
            time = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length != 2) continue;
                Information c = new Information();
                c.set_num = parts[0];
                c.num_parts = Integer.parseInt(parts[1]);
                int resId = context.getResources().getIdentifier(
                        c.name, "string", context.getPackageName());
                c.name = context.getResources().getString(resId);
                infos.add(c);
            }
            return true;
        }
        catch(Exception e) {
            Log.e("flx", "ERROR : " + e);
            return false;
        }
        finally {
            if (reader != null) {
                try { reader.close(); }
                catch (IOException e) { }
            }
        }
    }
}
