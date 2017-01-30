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
    private String name;
    private Image image;
    private int count;
    private String description;

    public Info() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    private String time;
    private ArrayList<Info> infos;

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
                Info c = new Info();
                c.description = parts[0];
                c.count = Integer.parseInt(parts[1]);
                int resId = context.getResources().getIdentifier(
                        c.description, "string", context.getPackageName());
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
