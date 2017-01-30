package com.example.dam.lego;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
Info info;
    Spinner spinner;
    public class Product extends HashMap<String, Object> {
        public Product(String name, int count, String description) {
            this.put("name", name);
            this.put("count", count);
            this.put("description", description);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner)findViewById(R.id.spinner);
        updateList();
        updateSpinners();
    }
    public void init() {
        info = new Info();
        boolean loaded = info.loadFromFile(this);
        Log.d("flx", "Loaded: " + loaded);
        if (!loaded) downloadInfo();
        else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Calendar c = Calendar.getInstance();
            String now = sdf.format(c.getTime());
            c.add(Calendar.DATE, -1);
            String yesterday = sdf.format(c.getTime());
            boolean valid = now.equals(info.getTime()) || yesterday.equals(info.getTime());
            if (!valid) askForDownload();
            else updateSpinners();
        }
    }
    public void askForDownload() {

        if (info != null) {
            if (info.getTime() != null) {
                String title = "Update bricks?";
                String btnOk = "Yes";
                String btnNo = "No";
                new AlertDialog.Builder(this).setTitle(title)
                        .setPositiveButton(btnOk, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.downloadInfo();
                            }
                        })
                        .setNegativeButton(btnNo, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.init();
                            }
                        })
                        .show();
                return;
            }
        }
        String title = "Download failed";
        new AlertDialog.Builder(this).setTitle(title).show();
    }
    public void downloadInfo() {
        downloadInfo di = new downloadInfo(this);
        di.setOnInfoLoadedListener(new OnInfoLoadedListener() {
            @Override
            public void onInfoLoaded(boolean ok) {
                Log.d("flx", "Download result: " + ok);
                if (ok) init();
                else askForDownload();
            }
        });
        di.execute();
    }

    public void updateSpinners() {
        infoCursor cursor = new infoCursor(info);
        SimpleCursorAdapter adapter;
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.spinner_item,
                cursor,
                new String[]{"name", "set_num"},
                new int[]{R.id.textView, R.id.textView2},
                0);
        spinner.setAdapter(adapter);
    }
    public void updateList(){
        ListView llista4 = (ListView) findViewById(R.id.listView);

        List<Product> dades = new ArrayList<>();
        dades.add(new Product("Name1", 7, "shit"));
        dades.add(new Product("Name2", 9, "moar shit"));

        SimpleAdapter adapter = new SimpleAdapter(
                MainActivity.this,
                dades,
                R.layout.listview,
                new String[] { "name", "count", "image" },
                new int[] { R.id.name, R.id.count, R.id.image }
        );

        llista4.setAdapter(adapter);
    }
}
