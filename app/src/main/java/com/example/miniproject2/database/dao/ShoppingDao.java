package com.example.miniproject2.database.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.miniproject2.database.entity.CartItemDTO;
import com.example.miniproject2.database.entity.Order;
import com.example.miniproject2.database.entity.OrderDetail;
import com.example.miniproject2.database.entity.Product;
import java.util.List;

@Dao
public interface ShoppingDao {
    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Query("SELECT * FROM orders WHERE userId = :userId AND status = 'PENDING' LIMIT 1")
    Order getPendingOrder(int userId);

    // JOIN 2 bảng để lấy thông tin chi tiết giỏ hàng
    @Query("SELECT p.name as productName, od.quantity, od.price FROM order_details od INNER JOIN products p ON od.productId = p.id WHERE od.orderId = :orderId")
    List<CartItemDTO> getCartItems(int orderId);

    @Insert
    long insertOrder(Order order);

    @Insert
    void insertOrderDetail(OrderDetail orderDetail);

    @Update
    void updateOrder(Order order);

    @Insert
    void insertProduct(Product product);
}