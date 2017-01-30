package com.example.dam.lego;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
