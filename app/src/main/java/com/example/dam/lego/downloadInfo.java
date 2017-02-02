package com.example.dam.lego;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dam on 30/1/17.
 */

public class downloadInfo extends AsyncTask<Void, String, String> {
    private Context context;
    public downloadInfo(Context context, String set){
        this.context = context;
        this.set = set;
    }
    private OnInfoLoadedListener listener = null;
    public void setOnInfoLoadedListener(OnInfoLoadedListener listener){
        this.listener = listener;
    }
    private ProgressDialog pDialog;
    private String xml;
    private String set;
    private List<Info> listaInfo = new ArrayList<>();
    @Override protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(true);
        pDialog.setTitle("Loading...");
        String msg = "Updating info...";
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.show();
    }


    @Override protected String doInBackground(Void... params) {
        int count;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://stucom.flx.cat/lego/get_set_parts.php?set="+ set+ "&key=62fb8715af2c04f5d9a3d69bdde21e65");
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
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\n");
                for (String s: parts) {
                   String[] stats = line.split("\t");
                    if (stats.length!=11)continue;
                    Info i = new Info();
                    i.setPart_id(stats[0]);
                    i.setQty(stats[1]);
                    i.setLdraw_color_id(stats[2]);
                    i.setType(stats[3]);
                    i.setPart_name(stats[4]);
                    i.setColor_name(stats[5]);
                    i.setPart_img_url(stats[6]);
                    i.setElement_id(stats[7]);
                    i.setElement_img_url(stats[8]);
                    i.setRb_color_id(stats[9]);
                    i.setPart_type_id(stats[10]);
                    this.listaInfo.add(i);
                    // clase de arraylists
                    //clase base
                }

            }

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return xml;
    }
    protected void onProgressUpdate(String... progress) {
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override public void onPostExecute(String result) {
        pDialog.dismiss();
        if (listener != null) listener.onInfoLoaded(true);
    }
    public String getXml() {
        return xml;
    }

    public List<Info> getListaInfo() {
        return listaInfo;
    }

    @Override
    public String toString() {
        return "downloadInfo{" +
                "xml='" + xml + '\'' +
                '}';
    }
}
