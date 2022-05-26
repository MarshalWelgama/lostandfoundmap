package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostandfound.data.DatabaseHelper;
import com.example.lostandfound.model.Item;

public class NewAdvert extends AppCompatActivity {
    EditText NameField;
    EditText PhoneField;
    EditText DescriptionField;
    EditText DateField;
    EditText LocationField;
    Button SaveBtn;
    RadioGroup typeSelector;
    DatabaseHelper db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newadvert);
        db = new DatabaseHelper(this);
        NameField = (EditText) findViewById(R.id.NameField);
        PhoneField = (EditText) findViewById(R.id.PhoneField);
        DescriptionField = (EditText) findViewById(R.id.DescriptionField);
        DateField = (EditText) findViewById(R.id.DateField);
        LocationField = (EditText) findViewById(R.id.LocationField);
        SaveBtn = (Button) findViewById(R.id.SaveBtn);
        typeSelector = (RadioGroup) (findViewById(R.id.typeSelector));

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton checkedRadio;
                int radioId = typeSelector.getCheckedRadioButtonId();
                checkedRadio = (RadioButton) findViewById(radioId);
                String type = checkedRadio.getText().toString();
                String name = NameField.getText().toString();
                String phone = PhoneField.getText().toString();
                String description = DescriptionField.getText().toString();
                String date = DateField.getText().toString();
                String location = LocationField.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(location)) {
                    long dbItem = db.insertItem(new Item(type,name,phone,description, date, location));
                    if (dbItem > 0){

                        Toast.makeText(NewAdvert.this, "Item Saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewAdvert.this, MainActivity.class);
                        startActivity(intent);
                        finish(); //ensures that the back button wont work once saved
                    } else {
                        Toast.makeText(NewAdvert.this, "Unexpected Internal Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewAdvert.this, "All information must be filled", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}