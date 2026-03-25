package com.example.miniproject2.database.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public double price;
    public int categoryId; // Khóa ngoại liên kết với categories
    public String description;
}