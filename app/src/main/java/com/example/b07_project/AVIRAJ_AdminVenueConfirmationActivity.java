package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AVIRAJ_AdminVenueConfirmationActivity extends AppCompatActivity {
    TextView location;
    TextView NameText;
    TextView Stattime;
    TextView Endtime;
    TextView Sports;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Venues");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aviraj_activity_admin_venue_confirm);

        NameText = findViewById(R.id.NameText);
        location = findViewById(R.id.VenueText);
        Stattime = findViewById(R.id.StimeText);
        Endtime = findViewById(R.id.EtimeText);
        Sports = findViewById(R.id.SportsText);

        Intent intent = getIntent();

        NameText.setText(intent.getStringExtra("venueName"));
        location.setText(intent.getStringExtra("location"));
        Stattime.setText(intent.getStringExtra("Start Time"));
        Endtime.setText(intent.getStringExtra("End Time"));
        Sports.setText(intent.getStringExtra("Sports"));

        Button BackButton = findViewById(R.id.back);
        Button ConfirmButton = findViewById(R.id.confirm);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = NameText.getText().toString();
                String Location = location.getText().toString();
                String Start_Time = Stattime.getText().toString();
                String End_Time = Endtime.getText().toString();
                String Sports_Selected = Sports.getText().toString();

                HashMap<String,String> Venues = new HashMap<>();

                Venues.put("venueName",Name);
                Venues.put("location",Location);
                Venues.put("Start Time",Start_Time);
                Venues.put("End Time",End_Time);
                Venues.put("Sports",Sports_Selected);

                root.push().setValue(Venues);
                Toast.makeText(getApplicationContext(), "Venue created successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}