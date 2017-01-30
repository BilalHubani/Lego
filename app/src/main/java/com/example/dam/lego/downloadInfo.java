package com.example.dam.lego;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dam on 30/1/17.
 */

public class downloadInfo extends AsyncTask<Void, String, Boolean> {
    private Context context;
    public downloadInfo(Context context){
        this.context = context;
    }
    private OnInfoLoadedListener listener = null;
    public void setOnInfoLoadedListener(OnInfoLoadedListener listener){
        this.listener = listener;
    }
    private ProgressDialog pDialog;

    @Override protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.setTitle("Loading...");
        String msg = "Updating info...";
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    @Override protected Boolean doInBackground(Void... params) {
        int count;
        try {
            URL url = new URL("https://rebrickable.com/api/v3/lego/sets/?key=62fb8715af2c04f5d9a3d69bdde21e65");
            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            pDialog.setMax(lengthOfFile);
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lengthOfFile));
                output.write(data, 0, count);
            }
            input.close();
            output.flush();
            String xml = new String(output.toByteArray());
            File dir = context.getExternalFilesDir(null);
            if (dir == null) return false;
            File f = new File(dir, "lego.csv");
            f.delete();
            PrintWriter wr = new PrintWriter(f);
            Pattern pattern = Pattern.compile(".*<Cube time='(.*)'.*");
            Matcher matcher = pattern.matcher(xml);
            if (!matcher.find()) return false;
            String time = matcher.group(1);
            wr.println(time);
            wr.println("EUR:1.0000");
            pattern = Pattern.compile(".*<Cube currency='(.*)' rate='(.*)'.*");
            matcher = pattern.matcher(xml);
            while (matcher.find()) {
                String currency = matcher.group(1);
                String rate = matcher.group(2);
                wr.println(currency + ":" + rate);
            }
            wr.flush();
            wr.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return false;
        }

        return true;
    }
}
