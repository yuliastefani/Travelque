package com.example.travelque.UI.travel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travelque.Models.Travel;
import com.example.travelque.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailTravelActivity extends AppCompatActivity {

    public static final String detTravel = "detTravel";
    ImageView travelImage;
    TextView travelName, travelDesc;
    FloatingActionButton fabAdd;
    MapView travelMap;
    GoogleMap googleMap;
    Marker travelMarker;
    AlertDialog.Builder addDialog;
    Double travelLat, travelLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_travel);

        travelImage = findViewById(R.id.travelImage);
        travelName = findViewById(R.id.travelName);
        travelDesc = findViewById(R.id.travelDescription);
        travelMap = findViewById(R.id.travelMap);
        fabAdd = findViewById(R.id.fabAdd);


        if (getIntent().getExtras() != null) {
            Travel travel = getIntent().getParcelableExtra(detTravel, Travel.class);
            Glide.with(this).load(travel.getImage()).into(travelImage);
            travelName.setText(travel.getName());
            travelDesc.setText(travel.getDescription());
            travelLat = travel.getLang();
            travelLong = travel.getLongi();

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(travel.getName());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        travelMap.onCreate(savedInstanceState);
        travelMap.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            travelMarker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(travelLat, travelLong))
                    .title(travelName.getText().toString()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(travelLat, travelLong), 20f));
        });

        fabAdd.setOnClickListener(v -> {
            addDialog = new AlertDialog.Builder(this);
            addDialog.setTitle("Add New Travel");
            addDialog.setMessage("Are you sure want to add this travel to your list?");
            addDialog.setPositiveButton("Yes", (dialog, which) -> {

            });
            addDialog.setNegativeButton("No", (dialog, which) -> {
                dialog.cancel();
            });
            addDialog.show();
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        travelMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        travelMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        travelMap.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        travelMap.onLowMemory();
    }
}