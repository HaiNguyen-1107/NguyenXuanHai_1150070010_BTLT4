package com.example.teoappnguyenxuanhaibtlt4;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Thêm phương thức xử lý nút HelloWorld
    public void onBtnHelloWorldClick(View view) {
        Toast.makeText(this, "Hello World clicked!", Toast.LENGTH_SHORT).show();
    }

    // Thêm phương thức xử lý nút Register
    public void onBtnRegisterClick(View view) {
        Toast.makeText(this, "Register clicked!", Toast.LENGTH_SHORT).show();
    }
}
