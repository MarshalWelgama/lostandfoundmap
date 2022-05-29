package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button newAdvertBtn;
    Button showAllBtn;
    Button showMapBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newAdvertBtn = (Button) findViewById(R.id.newAdvertBtn);
        showAllBtn = (Button) findViewById(R.id.showAllBtn);
        showMapBtn = (Button) findViewById(R.id.showMapBtn);

        newAdvertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewAdvert.class);
                startActivity(intent);
            }
        });
        showAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowAll.class);
                startActivity(intent);
            }
        });

        showMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowMap.class);
                startActivity(intent);
            }
        });
    }


}