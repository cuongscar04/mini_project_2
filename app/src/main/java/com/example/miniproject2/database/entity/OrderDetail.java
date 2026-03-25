package com.example.miniproject2.database.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_details")
public class OrderDetail {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int orderId;   // Liên kết với orders
    public int productId; // Liên kết với products
    public int quantity;
    public double price;  // Giá tại thời điểm mua
}