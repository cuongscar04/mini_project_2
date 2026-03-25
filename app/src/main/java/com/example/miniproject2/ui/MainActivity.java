package com.example.miniproject2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.miniproject2.R;
import com.example.miniproject2.adapter.ProductAdapter;
import com.example.miniproject2.database.AppDatabase;
import com.example.miniproject2.database.entity.Product;
import com.example.miniproject2.utils.SharedPrefManager;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private SharedPrefManager prefManager;
    private RecyclerView rvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        prefManager = new SharedPrefManager(this);

        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));

        Button btnCart = findViewById(R.id.btnCart);
        Button btnAuth = findViewById(R.id.btnAuth);


        updateAuthButton(btnAuth);

        btnAuth.setOnClickListener(v -> {
            if (prefManager.isLoggedIn()) {
                prefManager.logout();
                updateAuthButton(btnAuth);
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        btnCart.setOnClickListener(v -> {
            if (prefManager.isLoggedIn()) {
                startActivity(new Intent(this, CartActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        loadProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại UI khi quay lại màn hình chính
        Button btnAuth = findViewById(R.id.btnAuth);
        updateAuthButton(btnAuth);
    }

    private void updateAuthButton(Button btnAuth) {
        if (prefManager.isLoggedIn()) {
            btnAuth.setText("Đăng xuất");
        } else {
            btnAuth.setText("Đăng nhập");
        }
    }

    private void loadProducts() {
        List<Product> products = db.shoppingDao().getAllProducts();
        ProductAdapter adapter = new ProductAdapter(products, product -> {
             Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
             intent.putExtra("PRODUCT_ID", product.id);
             startActivity(intent);
        });
        rvProducts.setAdapter(adapter);
    }
}