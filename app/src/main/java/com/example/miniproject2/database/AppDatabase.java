package com.example.miniproject2.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.miniproject2.dao.CategoryDao;
import com.example.miniproject2.model.Category;

@Database(entities = {Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract CategoryDao categoryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "shopping_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}