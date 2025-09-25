package com.example.teoappnguyenxuanhaibtlt4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

public class MainActivity1 extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private BroadcastReceiver smsReceiver;
    private IntentFilter filter;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Thiết lập layout
        setContentView(R.layout.activity1_main);

        // Ánh xạ TextView từ layout
        tvContent = findViewById(R.id.tv_content);

        // Kiểm tra và yêu cầu quyền RECEIVE_SMS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS}, PERMISSION_REQUEST_CODE);
        } else {
            initBroadcastReceiver();
        }
    }

    // Xử lý kết quả yêu cầu quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền được cấp, khởi tạo BroadcastReceiver
                initBroadcastReceiver();
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied. The app cannot receive SMS.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initBroadcastReceiver() {
        smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Nhận các tin nhắn SMS từ Intent
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    if (pdus != null) {
                        StringBuilder sb = new StringBuilder();
                        for (Object pdu : pdus) {
                            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                            String sender = smsMessage.getOriginatingAddress();
                            String messageBody = smsMessage.getMessageBody();
                            sb.append("From: ").append(sender).append("\n");
                            sb.append("Message: ").append(messageBody).append("\n\n");
                        }
                        String fullMessage = sb.toString();
                        tvContent.setText(fullMessage);
                        Toast.makeText(context, "Hey! You have a new message", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (smsReceiver != null) {
            // Đăng ký BroadcastReceiver khi activity hiển thị
            registerReceiver(smsReceiver, filter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (smsReceiver != null) {
            // Hủy đăng ký BroadcastReceiver khi activity không còn hiển thị
            unregisterReceiver(smsReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Đảm bảo hủy đăng ký để tránh rò rỉ bộ nhớ
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
        }
    }
}
