package com.example.dam.lego;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dam on 30/1/17.
 */

public class downloadPart extends AsyncTask<Void, String, String> {
    private Context context;
    public downloadPart(Context context, String part_id){
        this.context = context;
        this.part_id = part_id;
    }
    private OnInfoLoadedListener listener = null;
    public void setOnInfoLoadedListener(OnInfoLoadedListener listener){
        this.listener = listener;
    }
    private ProgressDialog pDialog;
    private String xml;
    private String part_id;
    private Part part;

    public Part getPart() {
        return part;
    }

    @Override protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        Log.e("entra en prexecute", "si");
        pDialog.setIndeterminate(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(true);
        pDialog.setTitle("Loading part...");
        pDialog.setCancelable(true);
        pDialog.show();
    }


    @Override protected String doInBackground(Void... params) {
        int count;
        BufferedReader reader = null;
        URL imageUrl = null;
        HttpURLConnection conn = null;

        try {

            URL url = new URL("http://stucom.flx.cat/lego/get_part.php?part_id=" + part_id + "&key=62fb8715af2c04f5d9a3d69bdde21e65");
            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            Log.e("funciona", "hasta aqui");
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

            this.xml = new String(output.toByteArray());
            Log.e("xml: ", xml);
            reader = new BufferedReader(new StringReader(xml));
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                    String[] stats = line.split("\t");
                    if (stats.length != 8) continue;
                        Part i = new Part();
                        i.setName(stats[0]);
                        i.setFirstYear(stats[1]);
                        i.setImg_url(stats[2]);
                        i.setLastYear(stats[3]);
                        i.setPart_id(stats[4]);
                        i.setCategory(stats[5]);
                        i.setWeb_url(stats[6]);
                        i.setCategory_id(stats[7]);
                        this.part = i;

                }

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return xml;
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    @Override public void onPostExecute(String result) {
        pDialog.dismiss();
        if (listener != null) listener.onInfoLoaded(true);
    }
    public String getXml() {
        return xml;
    }

    @Override
    public String toString() {
        return "downloadInfo{" +
                "xml='" + xml + '\'' +
                '}';
    }
}