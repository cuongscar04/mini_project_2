package com.example.miniproject2.database.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId; // Khóa ngoại liên kết với users
    public String orderDate;
    public String status; // Ví dụ: "PENDING" (đang nhặt hàng), "PAID" (đã thanh toán)
}