package com.example.miniproject2.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.miniproject2.R;
import com.example.miniproject2.adapter.CartAdapter;
import com.example.miniproject2.database.AppDatabase;
import com.example.miniproject2.database.entity.CartItemDTO;
import com.example.miniproject2.database.entity.Order;
import com.example.miniproject2.utils.SharedPrefManager;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private AppDatabase db;
    private SharedPrefManager prefManager;
    private Order pendingOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = AppDatabase.getInstance(this);
        prefManager = new SharedPrefManager(this);

        RecyclerView rvCart = findViewById(R.id.rvCart);
        TextView tvTotal = findViewById(R.id.tvTotal);
        Button btnCheckout = findViewById(R.id.btnCheckout);

        rvCart.setLayoutManager(new LinearLayoutManager(this));

        int userId = prefManager.getUserId();
        pendingOrder = db.shoppingDao().getPendingOrder(userId);

        if (pendingOrder != null) {
            List<CartItemDTO> cartItems = db.shoppingDao().getCartItems(pendingOrder.id);

            CartAdapter adapter = new CartAdapter(cartItems);
            rvCart.setAdapter(adapter);

            // Tính tổng tiền
            double total = 0;
            for (CartItemDTO item : cartItems) {
                total += (item.price * item.quantity);
            }
            tvTotal.setText("Tổng cộng: $" + total);

            btnCheckout.setOnClickListener(v -> processCheckout());
        } else {
            tvTotal.setText("Giỏ hàng trống!");
            btnCheckout.setEnabled(false);
        }
    }

    private void processCheckout() {
        if (pendingOrder != null) {
            pendingOrder.status = "PAID";
            db.shoppingDao().updateOrder(pendingOrder);

            Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_LONG).show();
            finish(); // Về màn hình chính
        }
    }
}