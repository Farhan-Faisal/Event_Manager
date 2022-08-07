package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SpecificEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_event);

        Intent intent = getIntent();
        TextView title = findViewById(R.id.specificEventTitleid);
        TextView date = findViewById(R.id.specificEventDateid);
        TextView venue = findViewById(R.id.specificEventVenueid);
        TextView noPar = findViewById(R.id.specificEventCountid);
        TextView space = findViewById(R.id.specificEventSpaceid);
        TextView time = findViewById(R.id.specificEventTimeid);

        title.setText(intent.getStringExtra("eventTitle"));
        date.setText("-Date: " + intent.getStringExtra("eventDate"));
        venue.setText("-Venue: " +  intent.getStringExtra("venueName"));
        noPar.setText("-No Participants: " + intent.getStringExtra("noParticipants"));
        space.setText("- " + intent.getStringExtra("spaceAvailability"));
        time.setText("-Time: " + intent.getStringExtra("eventTime"));

    }
}