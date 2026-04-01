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

        List<Entities.Product> products;

        // Kiểm tra xem có nhận được categoryId từ Intent hay không
        Intent intent = getIntent();
        if (intent.hasExtra("CATEGORY_ID")) {
            int categoryId = intent.getIntExtra("CATEGORY_ID", -1);
            // Nếu có: Lọc sản phẩm theo danh mục
            products = db.productDao().getProductsByCategoryId(categoryId);
        } else {
            // Nếu không: Hiển thị toàn bộ sản phẩm (khi bấm từ Home Screen)
            products = db.productDao().getAllProducts();
        }

        String[] productNames = new String[products.size()];
        for (int i = 0; i < products.size(); i++) {
            productNames[i] = products.get(i).name + " - $" + products.get(i).price; // Hiển thị thêm giá cho trực quan
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent detailIntent = new Intent(this, ProductDetailActivity.class);
            detailIntent.putExtra("PRODUCT_ID", products.get(position).id);
            startActivity(detailIntent);
        });
    }
}