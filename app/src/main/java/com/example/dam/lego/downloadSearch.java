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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dam on 3/2/17.
 */

public class downloadSearch extends AsyncTask<Void, String, Boolean> {
    private Context context;

    public downloadSearch(Context context, String search) {
        this.context = context;
        this.search = search;
    }

    private OnInfoLoadedListener listener = null;
    public void setOnInfoLoadedListener(OnInfoLoadedListener listener){
        this.listener = listener;
    }
    private ProgressDialog pDialog;
    private String xml;
    private String search;
    private List<InfoSearch> listaInfoSearch = new ArrayList<>();
    @Override protected void onPreExecute() {
        Log.e("entra en prexecute", "si");
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(true);
        pDialog.setTitle("Loading...");
        String msg = "Updating info...";
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    @Override protected Boolean doInBackground(Void... params) {
        int count;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://stucom.flx.cat/lego/search.php?query="+ search+ "&key=62fb8715af2c04f5d9a3d69bdde21e65");
            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            Log.e("funciona", "hasta aqui");
            pDialog.setMax(lengthOfFile);
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            while ((count = input.read(data)) != -1) {
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
                    InfoSearch i = new InfoSearch();
                    i.setSet_id(stats[0]);
                    i.setYear(stats[1]);
                    i.setPieces(stats[2]);
                    i.setTheme1(stats[3]);
                    i.setTheme2(stats[4]);
                    i.setTheme3(stats[5]);
                    i.setAccessory(stats[6]);
                    i.setKit(stats[7]);
                    i.setDescr(stats[8]);
                    i.setUrl(stats[9]);
                    i.setImg_tn(stats[10]);
                    i.setImg_sm(stats[11]);
                    i.setImg_big(stats[12]);
                    this.listaInfoSearch.add(i);
                    // clase de arraylists
                    //clase base
                }

            }

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return false;
        }
        return true;
    }

    @Override public void onPostExecute(Boolean result) {
        pDialog.dismiss();
        if (listener != null) listener.onInfoLoaded(true);
    }

    public List<InfoSearch> getListaInfo() {
        return listaInfoSearch;
    }

    @Override
    public String toString() {
        return "downloadSearch{" +
                "context=" + context +
                ", listener=" + listener +
                ", pDialog=" + pDialog +
                ", xml='" + xml + '\'' +
                ", search='" + search + '\'' +
                ", listaInfoSearch=" + listaInfoSearch +
                '}';
    }
}
