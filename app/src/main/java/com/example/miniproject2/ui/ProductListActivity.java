package com.example.miniproject2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject2.R;
import com.example.miniproject2.database.AppDatabase;
import com.example.miniproject2.database.Entities;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ListView listView = findViewById(R.id.listViewProducts);
        AppDatabase db = AppDatabase.getInstance(this);

        List<Entities.Product> products = db.productDao().getAllProducts();
        String[] productNames = new String[products.size()];
        for (int i = 0; i < products.size(); i++) {
            productNames[i] = products.get(i).name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("PRODUCT_ID", products.get(position).id);
            startActivity(intent);
        });
    }
}