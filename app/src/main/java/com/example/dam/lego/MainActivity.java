package com.example.dam.lego;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
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
    InfoSearch infoSearch;
    downloadInfo di;
    downloadSearch ds;
    List<Info> listaInfos;
    Spinner spinner;
    List<InfoSearch>listaInfoSearch;
    public class Product extends HashMap<String, Object> {
        public Product(String part_name, String qty, String part_img_url) {
            this.put("part_name", part_name);
            this.put("qty", qty);
            this.put("part_img_url", part_img_url);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner)findViewById(R.id.spinner);
        init();
        ImageButton search = (ImageButton)findViewById(R.id.searchButton);
        final EditText text = (EditText)findViewById(R.id.searchText);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadInfo(text.getText().toString());
            }
        });
        ImageButton buscar = (ImageButton)findViewById(R.id.searchButton2);
        final EditText texto = (EditText)findViewById(R.id.searchText2);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadSearch(texto.getText().toString());
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String codigoCaja = listaInfoSearch.get(i).getSet_id();
                downloadInfo(codigoCaja);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void init() {
        info = new Info();
        infoSearch = new InfoSearch();
    }
    public void downloadSearch(String search){
        ds = new downloadSearch(this, search);
        Log.e("entra en ds", "si");
        ds.setOnInfoLoadedListener(new OnInfoLoadedListener() {
            @Override
            public void onInfoLoaded(boolean ok) {
                listaInfoSearch = ds.getListaInfo();
                Log.e("lista pintada", listaInfoSearch.toString());
                updateSpinners();
            }
        });
        ds.execute();
    }
    public void downloadInfo(String set) {
        di = new downloadInfo(this, set);
        di.setOnInfoLoadedListener(new OnInfoLoadedListener() {
            @Override
            public void onInfoLoaded(boolean ok) {
                listaInfos = di.getListaInfo();
                updateList();
            }
        });
        di.execute();
    }

    public void updateSpinners() {
        infoCursor cursor = new infoCursor(listaInfoSearch);
        SimpleCursorAdapter adapter;
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.spinner_item,
                cursor,
                new String[]{"descr", "set_id"},
                new int[]{R.id.textView, R.id.textView2},
                0);
        spinner.setAdapter(adapter);
    }
    public void updateList(){
        ListView list = (ListView) findViewById(R.id.listView);
        List<Product> dades = new ArrayList<>();
        for (Info i: listaInfos ) {
            Product p = new Product(i.getPart_name(),i.getQty(),i.getPart_img_url());
            dades.add(p);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                MainActivity.this,
                dades,
                R.layout.listview,
                new String[] { "part_name", "qty", "part_img_url" },
                new int[] { R.id.name, R.id.count, R.id.image }
        );

        list.setAdapter(adapter);
    }

}
