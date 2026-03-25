package com.example.miniproject2; // Dòng này cực kỳ quan trọng, phải ở dòng 1

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tvName, tvPrice, tvDesc;
    private ImageView imgProduct;
    private Button btnAddToCart;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ các ID từ file XML bạn đã dán lúc nãy
        tvName = findViewById(R.id.tvProductNameDetail);
        tvPrice = findViewById(R.id.tvProductPriceDetail);
        tvDesc = findViewById(R.id.tvProductDescDetail);
        imgProduct = findViewById(R.id.imgProductDetail);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        // Nhận ID sản phẩm từ Intent
        productId = getIntent().getIntExtra("PRODUCT_ID", -1);

        // Xử lý nút bấm
        btnAddToCart.setOnClickListener(v -> {
            checkLoginAndOrder();
        });
    }

    private void checkLoginAndOrder() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            Toast.makeText(this, "Bạn cần đăng nhập!", Toast.LENGTH_SHORT).show();
            // Chỗ này bạn có thể Intent sang LoginActivity
        } else {
            Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
        }
    }
}