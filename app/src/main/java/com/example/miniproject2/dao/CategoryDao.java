package com.example.miniproject2.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.miniproject2.model.Category;
import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Categories")
    List<Category> getAllCategories();

    @Insert
    void insertCategory(Category category);
}