package com.example.miniproject2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public class Daos {

    @Dao
    public interface UserDao {
        @Insert
        long insert(Entities.User user);

        @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
        Entities.User login(String username, String password);
    }

    @Dao
    public interface CategoryDao {
        @Insert
        void insertAll(Entities.Category... categories);

        @Query("SELECT * FROM categories")
        List<Entities.Category> getAllCategories();
    }

    @Dao
    public interface ProductDao {
        @Insert
        void insertAll(Entities.Product... products);

        @Query("SELECT * FROM products")
        List<Entities.Product> getAllProducts();

        @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
        Entities.Product getProductById(int id);

        // Thêm hàm lấy sản phẩm theo danh mục
        @Query("SELECT * FROM products WHERE categoryId = :categoryId")
        List<Entities.Product> getProductsByCategoryId(int categoryId);
    }

    @Dao
    public interface OrderDao {
        @Insert
        long insert(Entities.Order order);

        @Update
        void update(Entities.Order order);

        @Query("SELECT * FROM orders WHERE userId = :userId AND status = 'Pending' LIMIT 1")
        Entities.Order getPendingOrder(int userId);
    }

    @Dao
    public interface OrderDetailDao {
        @Insert
        void insert(Entities.OrderDetail orderDetail);

        @Query("SELECT * FROM order_details WHERE orderId = :orderId")
        List<Entities.OrderDetail> getDetailsByOrderId(int orderId);
    }
}