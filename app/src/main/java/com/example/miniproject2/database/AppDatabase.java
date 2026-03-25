package com.example.miniproject2.database;

import android.content.Context;
<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.Executors;

import com.example.miniproject2.database.entity.*;
import com.example.miniproject2.database.dao.*;

@Database(entities = {User.class, Category.class, Product.class, Order.class, OrderDetail.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract ShoppingDao shoppingDao();
=======
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.miniproject2.dao.CategoryDao;
import com.example.miniproject2.model.Category;

@Database(entities = {Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract CategoryDao categoryDao();
>>>>>>> 3ca8ad7785e38533e753f50dd77445108896c8e9

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "shopping_database")
                    .fallbackToDestructiveMigration()
<<<<<<< HEAD
                    .addCallback(roomCallback)
                    .allowMainThreadQueries()
=======
>>>>>>> 3ca8ad7785e38533e753f50dd77445108896c8e9
                    .build();
        }
        return instance;
    }
<<<<<<< HEAD

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                // User mẫu
                User user = new User();
                user.username = "admin";
                user.password = "123";
                instance.userDao().insert(user);

                // Sản phẩm mẫu
                Product p1 = new Product();
                p1.name = "iPhone 15 Pro Max";
                p1.price = 1000.0;
                p1.description = "Điện thoại Apple cao cấp nhất";

                Product p2 = new Product();
                p2.name = "Samsung Galaxy S24 Ultra";
                p2.price = 1200.0;
                p2.description = "Điện thoại Samsung AI";

                instance.shoppingDao().insertProduct(p1);
                instance.shoppingDao().insertProduct(p2);
            });
        }
    };
=======
>>>>>>> 3ca8ad7785e38533e753f50dd77445108896c8e9
}