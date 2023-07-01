package com.example.travelque.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.travelque.R;

public class BoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding);
        Button btnLetsGo = findViewById(R.id.btnLetsGo);

        btnLetsGo.setOnClickListener(v -> {
            // Start main app activity
            Intent intent = new Intent(BoardingActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        });
    }
}