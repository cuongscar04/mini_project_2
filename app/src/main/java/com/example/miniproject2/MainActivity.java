package com.example.miniproject2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject2.adapter.CategoryAdapter;
import com.example.miniproject2.database.AppDatabase;
import com.example.miniproject2.model.Category;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategories;
    private AppDatabase database;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCategories = findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new LinearLayoutManager(this));

        database = AppDatabase.getInstance(this);

        loadData();
    }

    private void loadData() {
        // Dùng luồng phụ để thao tác với DB
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {

            // 1. Kiểm tra xem DB đã có dữ liệu chưa
            List<Category> list = database.categoryDao().getAllCategories();

            // 2. Thêm cứng dữ liệu nếu danh sách rỗng (Đúng yêu cầu đề bài)
            if (list.isEmpty()) {
                database.categoryDao().insertCategory(new Category("Điện thoại"));
                database.categoryDao().insertCategory(new Category("Laptop"));
                database.categoryDao().insertCategory(new Category("Phụ kiện"));
                database.categoryDao().insertCategory(new Category("Đồng hồ"));

                // Lấy lại danh sách sau khi đã insert
                list = database.categoryDao().getAllCategories();
            }

            // 3. Cập nhật UI (Bắt buộc phải trả về luồng chính)
            List<Category> finalList = list;
            runOnUiThread(() -> {
                adapter = new CategoryAdapter(finalList);
                rvCategories.setAdapter(adapter);
            });
        });
    }
}