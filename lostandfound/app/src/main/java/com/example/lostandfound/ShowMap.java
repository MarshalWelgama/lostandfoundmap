package com.example.lostandfound;


import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.lostandfound.data.DatabaseHelper;
import com.example.lostandfound.model.Item;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.lostandfound.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class ShowMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    List<Item> itemList;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(ShowMap.this);
        itemList = db.fetchAll();
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for(int i = 0;i<itemList.size();i++){

            double Lat = Double.parseDouble(itemList.get(i).getLatitude());
            double Lng = Double.parseDouble(itemList.get(i).getLongitude());
            String Name = itemList.get(i).getDescription();
            LatLng Marker = new LatLng(Lat, Lng);
            mMap.addMarker(new MarkerOptions().position(Marker).title(Name));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(Marker));
        }

    }
}