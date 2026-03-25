package com.example.miniproject2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int categoryId;

    public String categoryName;

    // Constructor
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}