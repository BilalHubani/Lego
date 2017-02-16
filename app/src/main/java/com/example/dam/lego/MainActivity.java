package com.example.dam.lego;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
    ImageView img;
    ListView list;
    public class Product {
        private String part_name;
        private String qty;
        private String image;


        public Product(String part_name, String qty, String image) {
            this.part_name = part_name;
            this.qty = qty;
            this.image = image;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner)findViewById(R.id.spinner);
        list = (ListView) findViewById(R.id.listView);
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
       /* list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PartActivity.class);
                intent.putExtra("part_id", listaInfos.get(i).getPart_id());
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PartActivity.class);
                intent.putExtra("part_id", listaInfos.get(i).getPart_id());
                startActivity(intent);
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
        img = (ImageView)findViewById(R.id.image);
        List<Product> dades = new ArrayList<>();
        for (Info i: listaInfos ) {
                Product p = new Product(i.getPart_name(),i.getQty(), i.getPart_img_url());
                dades.add(p);
        }
        CatalogAdapter adapter = new CatalogAdapter(this, dades);
        list.setAdapter(adapter);
    }
    public class CatalogAdapter extends BaseAdapter {

        private Context context;
        private List<Product> catalog;

        public CatalogAdapter(Context context, List<Product> catalog) {
            this.context = context;
            this.catalog = catalog;
        }

    @Override public int getCount() { return catalog.size(); }
    @Override public Object getItem(int position) { return catalog.get(position); }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public class ViewHolder {
        public TextView part_name;
        public TextView qty;
        public ImageView imImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // RECICLAT DE VISTES
        View myView = convertView;
        if (myView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.listview, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.part_name = (TextView) myView.findViewById(R.id.name);
            holder.qty = (TextView) myView.findViewById(R.id.count);
            holder.imImage = (ImageView) myView.findViewById(R.id.image);
            myView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) myView.getTag();

        Product product = catalog.get(position);
        String nom = product.getPart_name();
        holder.part_name.setText(nom);
        String price = product.getQty();
        holder.qty.setText(price);
        Picasso.with(this.context).load(product.getImage()).into(holder.imImage, new Callback() {
            @Override
            public void onSuccess() {}
            @Override
            public void onError() {}
        });
        return myView;
    }
}

}
