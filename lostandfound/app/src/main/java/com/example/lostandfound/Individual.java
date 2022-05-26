package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lostandfound.data.DatabaseHelper;

public class Individual extends AppCompatActivity {
    TextView  type , name, date, description, location, phone;
    Button removeBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        DatabaseHelper db = new DatabaseHelper(Individual.this);

        type = (TextView) findViewById(R.id.type);
        name = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);
        location = (TextView) findViewById(R.id.location);
        phone = (TextView) findViewById(R.id.phone);
        removeBtn = (Button) findViewById(R.id.removeBtn);

        Intent intent = getIntent();

        String intentType = intent.getStringExtra("type");
        String intentName = intent.getStringExtra("name");
        String intentPhone = intent.getStringExtra("phone");
        String intentDescription = intent.getStringExtra("description");
        String intentDate = intent.getStringExtra("date");
        String intentLocation = intent.getStringExtra("location");

        int intentId = intent.getIntExtra("id", 0);

        type.setText(intentType);
        name.setText(intentName);
        phone.setText(intentPhone);
        description.setText(intentDescription);
        date.setText(intentDate);
        location.setText(intentLocation);

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.removeItem(intentId);
                Toast.makeText(Individual.this, "Removed item", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Individual.this, ShowAll.class); //takes you back to show all screen
                startActivity(intent);
                finish(); //unable to click back to go to previous screen
            }
        });
    }
}
