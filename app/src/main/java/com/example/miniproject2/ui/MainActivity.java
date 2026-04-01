package com.example.miniproject2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btnNavLogin);
        Button btnProducts = findViewById(R.id.btnNavProducts);
        Button btnCategories = findViewById(R.id.btnNavCategories);

        // Điều hướng cơ bản
        btnLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        btnCategories.setOnClickListener(v -> startActivity(new Intent(this, CategoryListActivity.class))); // Xử lý sự kiện click

        btnProducts.setOnClickListener(v -> startActivity(new Intent(this, ProductListActivity.class)));
    }
}