package com.example.miniproject2.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject2.R;
import com.example.miniproject2.database.AppDatabase;
import com.example.miniproject2.database.Entities;
import com.example.miniproject2.utils.SharedPrefsHelper;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppDatabase db = AppDatabase.getInstance(this);
        SharedPrefsHelper sharedPrefs = new SharedPrefsHelper(this);

        EditText etUser = findViewById(R.id.etUsername);
        EditText etPass = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String user = etUser.getText().toString();
            String pass = etPass.getText().toString();

            Entities.User loggedInUser = db.userDao().login(user, pass);
            if (loggedInUser != null) {
                // Lưu trạng thái đăng nhập
                sharedPrefs.saveLoginState(loggedInUser.id);
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                // Trả về RESULT_OK để Activity gọi nó (ProductDetail) tiếp tục luồng mua hàng
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu (Thử admin/123456)", Toast.LENGTH_LONG).show();
            }
        });
    }
}