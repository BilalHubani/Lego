package com.example.dam.lego;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by dam on 16/2/17.
 */

public class PartActivity extends AppCompatActivity{
    String part_id;
    downloadPart dp;
    Part p;
    ImageView imagen;
    TextView name;
    TextView cat;
    TextView id;
    TextView fyear;
    TextView lyear;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partview);
        // part_id = (String) b.get("part_id");
        Intent intent = this.getIntent();
        part_id = intent.getStringExtra("part_id");
        imagen = (ImageView)findViewById(R.id.partImage);
        name = (TextView)findViewById(R.id.partName);
        cat = (TextView)findViewById(R.id.partCategory);
        id = (TextView)findViewById(R.id.partId);
        fyear = (TextView)findViewById(R.id.firstYear);
        lyear = (TextView)findViewById(R.id.lastYear);
        downloadPart(part_id);
    }

    public void downloadPart(String part) {
        dp = new downloadPart(this, part);
        dp.setOnInfoLoadedListener(new OnInfoLoadedListener() {
            @Override
            public void onInfoLoaded(boolean ok) {
                p = dp.getPart();
                name.setText("Name: "+p.getName());
                cat.setText("Type: "+p.getCategory());
                id.setText("Id: "+p.getPart_id());
                fyear.setText("First year used: "+p.getFirstYear());
                lyear.setText("Last year used: "+p.getLastYear());
                Picasso.with(context).load(p.getImg_url()).into(imagen, new Callback() {
                    @Override
                    public void onSuccess() {}
                    @Override
                    public void onError() {}
                });
            }
        });
        dp.execute();
    }
}
