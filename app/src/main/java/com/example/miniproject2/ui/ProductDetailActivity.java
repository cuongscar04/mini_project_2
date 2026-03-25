package com.example.miniproject2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.miniproject2.R;
import com.example.miniproject2.database.AppDatabase;
import com.example.miniproject2.database.entity.Order;
import com.example.miniproject2.database.entity.OrderDetail;
import com.example.miniproject2.database.entity.Product;
import com.example.miniproject2.utils.SharedPrefManager;

public class ProductDetailActivity extends AppCompatActivity {

    private SharedPrefManager prefManager;
    private AppDatabase db;
    private Product currentProduct; // Giả sử nhận được qua Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        prefManager = new SharedPrefManager(this);
        db = AppDatabase.getInstance(this);

        Button btnAddToCart = findViewById(R.id.btnAddToCart);

        btnAddToCart.setOnClickListener(v -> handleAddToCart());
    }

    private void handleAddToCart() {
        // 1. Kiểm tra đăng nhập
        if (!prefManager.isLoggedIn()) {
            Toast.makeText(this, "Vui lòng đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        int userId = prefManager.getUserId();

        // 2. Kiểm tra xem có Order nào đang PENDING chưa
        Order pendingOrder = db.shoppingDao().getPendingOrder(userId);
        long currentOrderId;

        if (pendingOrder == null) {
            // Chưa có Order (giỏ hàng trống) -> Tạo Order mới
            Order newOrder = new Order();
            newOrder.userId = userId;
            newOrder.status = "PENDING";
            newOrder.orderDate = "2023-10-25"; // Sử dụng SimpleDateFormat thực tế
            currentOrderId = db.shoppingDao().insertOrder(newOrder);
        } else {
            // Đã có Order đang PENDING
            currentOrderId = pendingOrder.id;
        }

        // 3. Tạo OrderDetail (Thêm sản phẩm vào giỏ)
        OrderDetail detail = new OrderDetail();
        detail.orderId = (int) currentOrderId;
        detail.productId = currentProduct.id;
        detail.quantity = 1; // Mặc định là 1 hoặc lấy từ EditText
        detail.price = currentProduct.price;

        db.shoppingDao().insertOrderDetail(detail);

        Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();


        finish();
    }
}