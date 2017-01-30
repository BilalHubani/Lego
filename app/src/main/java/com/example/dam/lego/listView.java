package com.example.dam.lego;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dam on 30/1/17.
 */

public class listView extends AppCompatActivity {

    public class Product extends HashMap<String, Object> {
        public Product(int id, String name, int count, String description, int image) {
            this.put("id", id);
            this.put("name", name);
            this.put("count", count);
            this.put("description", description);
            this.put("image", image);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        ListView llista4 = (ListView) findViewById(R.id.listView);

        List<Product> dades = new ArrayList<>();
        dades.add(new Product(1, "Name1", 7, "shit", 1));
        dades.add(new Product(2, "Name2", 9, "moar shit", 2));

        SimpleAdapter adapter = new SimpleAdapter(
                listView.this,
                dades,
                R.layout.listview,
                new String[] { "name", "count", "image" },
                new int[] { R.id.name, R.id.count, R.id.image }
        );

        llista4.setAdapter(adapter);
    }
}
