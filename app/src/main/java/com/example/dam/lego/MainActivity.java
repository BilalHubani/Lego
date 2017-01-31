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
    downloadInfo di = new downloadInfo(this);
    Spinner spinner;
    public class Product extends HashMap<String, Object> {
        public Product(String name, String set_nums, String num_parts) {
            this.put("name", name);
            this.put("count", set_nums);
            this.put("description", num_parts);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner)findViewById(R.id.spinner);
        init();
        downloadInfo();
    }
    public void init() {
        info = new Info();
    }

    public void downloadInfo() {

        di.setOnInfoLoadedListener(new OnInfoLoadedListener() {
            @Override
            public void onInfoLoaded(boolean ok) {
                updateSpinners();
                updateList();
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
