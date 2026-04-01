package com.example.miniproject2.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject2.R;
import com.example.miniproject2.database.AppDatabase;
import com.example.miniproject2.database.Entities;
import com.example.miniproject2.utils.SharedPrefsHelper;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private AppDatabase db;
    private SharedPrefsHelper sharedPrefs;
    private Entities.Order pendingOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        db = AppDatabase.getInstance(this);
        sharedPrefs = new SharedPrefsHelper(this);

        Button btnCheckout = findViewById(R.id.btnCheckout);

        loadCartData();

        btnCheckout.setOnClickListener(v -> processPayment());
    }

    private void loadCartData() {
        int userId = sharedPrefs.getLoggedInUserId();
        pendingOrder = db.orderDao().getPendingOrder(userId);

        TextView tvCartInfo = findViewById(R.id.tvCartInfo);
        if (pendingOrder != null) {
            tvCartInfo.setText("Bạn có đơn hàng đang chờ thanh toán (Order ID: " + pendingOrder.id + ")");
        } else {
            tvCartInfo.setText("Giỏ hàng trống!");
        }
    }

    // LUỒNG DỮ LIỆU: THANH TOÁN -> CẬP NHẬT TRẠNG THÁI -> HIỂN THỊ HÓA ĐƠN
    private void processPayment() {
        if (pendingOrder == null) {
            Toast.makeText(this, "Không có đơn hàng nào!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Cập nhật trạng thái Order thành "Paid"
        pendingOrder.status = "Paid";
        db.orderDao().update(pendingOrder);

        // 2. Lấy dữ liệu chi tiết hóa đơn
        List<Entities.OrderDetail> details = db.orderDetailDao().getDetailsByOrderId(pendingOrder.id);
        double total = 0;
        StringBuilder invoiceText = new StringBuilder("HÓA ĐƠN THANH TOÁN\n\n");
        invoiceText.append("Order ID: ").append(pendingOrder.id).append("\n");

        for (Entities.OrderDetail d : details) {
            Entities.Product p = db.productDao().getProductById(d.productId);
            invoiceText.append("- ").append(p.name).append(" x").append(d.quantity)
                    .append(" : $").append(d.price).append("\n");
            total += (d.price * d.quantity);
        }
        invoiceText.append("\nTổng cộng: $").append(total);

        // 3. Hiển thị hóa đơn cho người dùng
        new AlertDialog.Builder(this)
                .setTitle("Thanh toán thành công")
                .setMessage(invoiceText.toString())
                .setPositiveButton("Đóng", (dialog, which) -> {
                    finish(); // Kết thúc quá trình mua sắm
                })
                .setCancelable(false)
                .show();
    }
}