package com.example.miniproject2.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

public class Entities {

    @Entity(tableName = "users")
    public static class User {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public String username;
        public String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Entity(tableName = "categories")
    public static class Category {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public String name;

        public Category(String name) { this.name = name; }
    }

    // Quan hệ 1-n: Categories -> Products
    @Entity(tableName = "products", foreignKeys = @ForeignKey(entity = Category.class,
            parentColumns = "id", childColumns = "categoryId", onDelete = ForeignKey.CASCADE))
    public static class Product {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public int categoryId;
        public String name;
        public double price;
        public String description;

        public Product(int categoryId, String name, double price, String description) {
            this.categoryId = categoryId;
            this.name = name;
            this.price = price;
            this.description = description;
        }
    }

    // Quan hệ 1-n: Users -> Orders
    @Entity(tableName = "orders", foreignKeys = @ForeignKey(entity = User.class,
            parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE))
    public static class Order {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public int userId;
        public String status; // "Pending" hoặc "Paid"
        public long date;

        public Order(int userId, String status, long date) {
            this.userId = userId;
            this.status = status;
            this.date = date;
        }
    }

    // Quan hệ 1-n: Orders -> OrderDetails & Products -> OrderDetails
    @Entity(tableName = "order_details", foreignKeys = {
            @ForeignKey(entity = Order.class, parentColumns = "id", childColumns = "orderId", onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "productId", onDelete = ForeignKey.CASCADE)
    })
    public static class OrderDetail {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public int orderId;
        public int productId;
        public int quantity;
        public double price; // Giá tại thời điểm mua

        public OrderDetail(int orderId, int productId, int quantity, double price) {
            this.orderId = orderId;
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }
    }
}