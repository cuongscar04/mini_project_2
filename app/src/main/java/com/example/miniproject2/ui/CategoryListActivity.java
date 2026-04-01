package com.example.miniproject2.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent; // Đã thêm dòng import này để sửa lỗi
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject2.R;
import com.example.miniproject2.database.AppDatabase;
import com.example.miniproject2.database.Entities;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        ListView listView = findViewById(R.id.listViewCategories);
        AppDatabase db = AppDatabase.getInstance(this);

        // Lấy danh sách danh mục từ Database
        List<Entities.Category> categories = db.categoryDao().getAllCategories();
        String[] categoryNames = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            categoryNames[i] = categories.get(i).name;
        }

        // Hiển thị lên ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = categories.get(position).name;
            Toast.makeText(this, "Bạn đã chọn danh mục: " + selectedCategory, Toast.LENGTH_SHORT).show();
        });
    }
}