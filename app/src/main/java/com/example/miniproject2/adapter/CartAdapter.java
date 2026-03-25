package com.example.miniproject2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.miniproject2.R;
import com.example.miniproject2.database.entity.CartItemDTO;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItemDTO> cartItemList;

    public CartAdapter(List<CartItemDTO> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItemDTO item = cartItemList.get(position);
        holder.tvName.setText(item.productName);
        holder.tvQtyPrice.setText("SL: " + item.quantity + " - Giá: $" + item.price);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQtyPrice;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
             tvName = itemView.findViewById(R.id.tvCartItemName);
             tvQtyPrice = itemView.findViewById(R.id.tvCartItemQtyPrice);
        }
    }
}