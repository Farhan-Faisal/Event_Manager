package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FARHAN_UserHomepageActivity extends AppCompatActivity {
    Button upcomingEvents;
    Button scheduleEvent;
    Button yourJoinedEvents;
    Button yourScheduledEvents;
    SharedPreferences sp;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farhan_activity_user_homepage);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if(getIntent().hasExtra("username")){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.commit();
        }
        username = sp.getString("username", null);

        // Initialize the variables
        upcomingEvents = findViewById(R.id.userUpcomingid);
        scheduleEvent = findViewById(R.id.userScheduleid);
        yourJoinedEvents = findViewById(R.id.userEventsJoinedid);
        yourScheduledEvents = findViewById(R.id.userEventsScheduledid);

        upcomingEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserEventListOverview(username);
            }
        });

        scheduleEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserVenueListActivity(username);
            }
        });
        yourJoinedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserJoinedEventsActivity(username);
            }
        });
    }

    public void openUserEventListOverview(String name) {
        Intent intent = new Intent(this, UserEventListActivity.class);
        intent.putExtra("username", name);
        startActivity(intent);
    }

    public void openUserVenueListActivity(String name) {
        Intent intent = new Intent(this, FARHAN_UserVenueListActivity.class);
        intent.putExtra("username", name);
        startActivity(intent);
    }

    public void openUserJoinedEventsActivity(String name) {
        Intent intent = new Intent(this, UserJoinedEventRV_Dominik.class);
        intent.putExtra("username", name);
        startActivity(intent);
    }
}

