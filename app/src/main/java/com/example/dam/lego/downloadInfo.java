package com.example.dam.lego;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
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
    private String xml;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
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

    public String getXml() {
        return xml;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        String a = null, b = null, c = null, d = null;
        Info info = new Info();
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("theme")) {
                            a = text;
                            info.setTheme_id(a);
                        } else if (name.equals("img_tn")) {
                            b = text;
                            info.setSet_img_url(b);
                        } else if (name.equals("set_id")) {
                            c = text;
                            info.setSet_num(c);
                        } else if (name.equals("pieces")) {
                            d = text;
                            info.setNum_parts(Integer.parseInt(d));
                        } else {
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListView llista4 = (ListView) findViewById(R.id.listView);
        List<MainActivity.Product> dades = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(
                MainActivity.this,
                dades,
                R.layout.listview,
                new String[] { "name", "count", "image" },
                new int[] { R.id.name, R.id.count, R.id.image }
        );

        llista4.setAdapter(adapter);
    }
    @Override protected Boolean doInBackground(Void... params) {
        int count;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://rebrickable.com/api/get_set?&key=62fb8715af2c04f5d9a3d69bdde21e65&set=60115-1&format=xml");
            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            Log.e("funciona", "hasta aqui");
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();
            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(input, null);
            Log.d("Tag Name", "Before cal");
            parseXMLAndStoreIt(myparser);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }
            input.close();
            output.flush();
            xml = new String(output.toByteArray());
            Log.e("xml: ", xml);

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return false;
        }

        return true;
    }
    protected void onProgressUpdate(String... progress) {
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override public void onPostExecute(Boolean result) {
        pDialog.dismiss();
        if (listener != null) listener.onInfoLoaded(result);
    }
}
