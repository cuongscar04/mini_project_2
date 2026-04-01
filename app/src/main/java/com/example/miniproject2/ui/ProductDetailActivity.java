package com.example.miniproject2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject2.R;
import com.example.miniproject2.database.AppDatabase;
import com.example.miniproject2.database.Entities;
import com.example.miniproject2.utils.SharedPrefsHelper;

public class ProductDetailActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOGIN = 1001;
    private AppDatabase db;
    private SharedPrefsHelper sharedPrefs;
    private Entities.Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        db = AppDatabase.getInstance(this);
        sharedPrefs = new SharedPrefsHelper(this);

        int productId = getIntent().getIntExtra("PRODUCT_ID", 1); // Giả sử ID = 1 nếu rỗng
        currentProduct = db.productDao().getProductById(productId);

        TextView tvName = findViewById(R.id.tvProductName);
        tvName.setText(currentProduct.name + " - $" + currentProduct.price);

        Button btnPickItem = findViewById(R.id.btnPickItem);
        btnPickItem.setOnClickListener(v -> onPickItemClicked());
    }

    // LUỒNG DỮ LIỆU: KIỂM TRA ĐĂNG NHẬP
    private void onPickItemClicked() {
        if (!sharedPrefs.isLoggedIn()) {
            // Nếu "No" (Chưa đăng nhập): Chuyển hướng sang Login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_CODE_LOGIN);
        } else {
            // Nếu "Yes" (Đã đăng nhập): Gọi trực tiếp hàm tạo Order
            processAddToCart();
        }
    }

    // Xử lý sau khi Login thành công từ màn hình Login trả về
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK) {
            // Tự động gọi hàm Tạo Order sau khi login thành công
            processAddToCart();
        }
    }

    // LUỒNG DỮ LIỆU: TẠO ORDER -> TẠO ORDER DETAILS -> HỎI NGƯỜI DÙNG
    private void processAddToCart() {
        int userId = sharedPrefs.getLoggedInUserId();

        // 1. Tạo Order (nếu chưa có)
        Entities.Order pendingOrder = db.orderDao().getPendingOrder(userId);
        if (pendingOrder == null) {
            pendingOrder = new Entities.Order(userId, "Pending", System.currentTimeMillis());
            long newOrderId = db.orderDao().insert(pendingOrder);
            pendingOrder.id = (int) newOrderId;
        }

        // 2. Tạo OrderDetails (Thêm sản phẩm vừa chọn)
        Entities.OrderDetail detail = new Entities.OrderDetail(
                pendingOrder.id, currentProduct.id, 1, currentProduct.price);
        db.orderDetailDao().insert(detail);

        // 3. Hiển thị hộp thoại hỏi "Có tiếp tục chọn sản phẩm?"
        new AlertDialog.Builder(this)
                .setTitle("Thành công")
                .setMessage("Đã thêm vào giỏ. Bạn có muốn tiếp tục chọn sản phẩm?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Nếu "Yes": Quay lại danh sách Products
                    Intent intent = new Intent(ProductDetailActivity.this, ProductListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Nếu "No": Chuyển đến màn hình Thanh toán (Checkout)
                    Intent intent = new Intent(ProductDetailActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }
}