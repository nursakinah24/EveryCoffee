package com.example.everycoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminHome extends AppCompatActivity {
    private ImageView ivMenu, ivTrx;
    private TextView tvMenu, tvTrx;
    private Button logoutAdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        ivMenu =  findViewById(R.id.img_menu);
        tvMenu = findViewById(R.id.tap_menu);
        ivTrx =  findViewById(R.id.img_transaction);
        tvTrx =  findViewById(R.id.tap_transaction);
        logoutAdm =  findViewById(R.id.admin_logout_button);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AdminMenu.class);
                startActivity(intent);
            }
        });

        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AdminMenu.class);
                startActivity(intent);
            }
        });

        ivTrx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AdminCheckOrder.class);
                startActivity(intent);
            }
        });

        tvTrx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AdminCheckOrder.class);
                startActivity(intent);
            }
        });

        logoutAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}