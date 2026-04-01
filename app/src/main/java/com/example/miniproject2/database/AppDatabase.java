package com.example.miniproject2.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.Executors;

@Database(entities = {
        Entities.User.class, Entities.Category.class, Entities.Product.class,
        Entities.Order.class, Entities.OrderDetail.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract Daos.UserDao userDao();
    public abstract Daos.CategoryDao categoryDao();
    public abstract Daos.ProductDao productDao();
    public abstract Daos.OrderDao orderDao();
    public abstract Daos.OrderDetailDao orderDetailDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "shopping_database")
                            .allowMainThreadQueries() // Phục vụ mục đích trình diễn logic luồng rõ ràng
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Callback thêm dữ liệu mẫu khi khởi tạo DB lần đầu
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase database = INSTANCE;

                // Hardcode Users
                database.userDao().insert(new Entities.User("admin", "123456"));

                // Hardcode Categories
                database.categoryDao().insertAll(
                        new Entities.Category("Điện thoại"),
                        new Entities.Category("Laptop")
                );

                // Hardcode Products
                database.productDao().insertAll(
                        new Entities.Product(1, "iPhone 15", 999.0, "Apple Smartphone"),
                        new Entities.Product(1, "Samsung S24", 899.0, "Samsung Smartphone"),
                        new Entities.Product(2, "MacBook Pro", 1999.0, "Apple Laptop")
                );
            });
        }
    };
}