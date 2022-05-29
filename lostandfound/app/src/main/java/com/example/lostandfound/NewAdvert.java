package com.example.lostandfound;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lostandfound.data.DatabaseHelper;
import com.example.lostandfound.model.Item;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;

import java.util.Arrays;
import java.util.List;

public class NewAdvert extends AppCompatActivity {
    EditText NameField;
    EditText PhoneField;
    EditText DescriptionField;
    EditText DateField;
    TextView LocationField;
    Button SaveBtn;
    RadioGroup typeSelector;
    DatabaseHelper db;
    double lat;
    double lng;
    LocationManager locationManager;
    LocationListener locationListener;
    Button CurrentLocationBtn;
    double currentLat;
    double currentLng;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {



        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                currentLat = location.getLatitude();
                currentLng = location.getLongitude();
                //set where we want the location to go . probably set the lat lng
                //and set the name of the place.
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationListener);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyAmDwfMW-gANRgFigINCAf9B5l85Uk_vnw");
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newadvert);
        db = new DatabaseHelper(this);
        NameField = (EditText) findViewById(R.id.NameField);
        PhoneField = (EditText) findViewById(R.id.PhoneField);
        DescriptionField = (EditText) findViewById(R.id.DescriptionField);
        DateField = (EditText) findViewById(R.id.DateField);
        LocationField = (TextView) findViewById(R.id.LocationField);
        SaveBtn = (Button) findViewById(R.id.SaveBtn);
        typeSelector = (RadioGroup) (findViewById(R.id.typeSelector));
        CurrentLocationBtn = (Button) findViewById(R.id.CurrentLocationbtn);


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);


        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getLatLng() + ", " + place.getId());
               // double result = ;
                String result = place.getName();
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
                LocationField.setText(result);

            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        CurrentLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lat = currentLat;
                lng = currentLng;
                autocompleteFragment.setText("Current Location");
                LocationField.setText("Check Map for location");
            }
        });
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
                String convertedLat = new Double(lat).toString();
                String convertedLng = new Double(lng).toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(location)) {
                    long dbItem = db.insertItem(new Item(type,name,phone,description, date, location, convertedLat, convertedLng));
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