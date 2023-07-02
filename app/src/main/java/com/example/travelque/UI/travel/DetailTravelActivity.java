package com.example.travelque.UI.travel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelque.Database.ListHelper;
import com.example.travelque.Database.UserHelper;
import com.example.travelque.Models.Travel;
import com.example.travelque.Models.User;
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
    AlertDialog.Builder addDialog, addNotes;
    Double travelLat, travelLong;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_travel);

        travelImage = findViewById(R.id.travelImage);
        travelName = findViewById(R.id.travelName);
        travelDesc = findViewById(R.id.travelDescription);
        travelMap = findViewById(R.id.travelMap);
        fabAdd = findViewById(R.id.fabAdd);

        SharedPreferences sharedUsername = getSharedPreferences("username", MODE_PRIVATE);
        String username = sharedUsername.getString("username", "");

        userHelper = new UserHelper(this);
        userHelper.open();
        User user = userHelper.fetchUser(username);
        userHelper.close();

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
                    addNotes = new AlertDialog.Builder(this);
                    addNotes.setTitle("Add Notes");
                    addNotes.setMessage("Do you want to add notes?");
                    addNotes.setPositiveButton("Yes", (dialog1, which1) -> {
                        EditText notes = new EditText(this);
                        notes.setHint("Add your notes here");
                        addNotes.setView(notes);
                        addNotes.setPositiveButton("Submit", (dialog2, which2) -> {
                            String note = notes.getText().toString();
                            ListHelper listHelper = new ListHelper(this);
                            listHelper.open();
                            listHelper.addList(note, user.getId(), travel.getId());
                            Toast.makeText(this, "Notes Submitted", Toast.LENGTH_SHORT).show();
                        });
                    });
                    addNotes.setNegativeButton("No", (dialog1, which1) -> {
                        dialog1.cancel();

                        ListHelper listHelper = new ListHelper(this);
                        listHelper.open();

                        listHelper.addList("-", user.getId(), travel.getId());
                        listHelper.close();
                        Toast.makeText(this, "Travel Added", Toast.LENGTH_SHORT).show();
                    });
                    addNotes.show();
                });
                addDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                addDialog.show();
            });
        }
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