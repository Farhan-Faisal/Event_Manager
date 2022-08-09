package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FARHAN_UserHomepageActivity extends AppCompatActivity {
    TextView prompt;
    Button upcomingEvents;
    Button scheduleEvent;
    Button yourJoinedEvents;
    Button yourScheduledEvents;

    SharedPreferences sp;
    String username;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farhan_activity_user_homepage);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if(getIntent().hasExtra("username")){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.putString("email", getIntent().getStringExtra("email"));
            editor.commit();
        }
        username = sp.getString("username", null);
        email = sp.getString("email", null);

        // Initialize the variables
        upcomingEvents = findViewById(R.id.userUpcomingid);
        scheduleEvent = findViewById(R.id.userScheduleid);
        yourJoinedEvents = findViewById(R.id.userEventsJoinedid);
        yourScheduledEvents = findViewById(R.id.userEventsScheduledid);
        prompt = findViewById(R.id.userHomepagePromptID);

        prompt.setText("Welcome " + username +"!");

        upcomingEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserEventListOverview(username, email);
            }
        });

        scheduleEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserVenueListActivity(username, email);
            }
        });
        yourJoinedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserJoinedEventsActivity(username, email);
            }
        });
        yourScheduledEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserScheduledEventsActivity(username, email);
            }
        });
    }

    public void openUserEventListOverview(String name, String email) {
        Intent intent = new Intent(this, JASON_UserEventListActivity.class);
        intent.putExtra("username", name);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void openUserVenueListActivity(String name, String email) {
        Intent intent = new Intent(this, FARHAN_UserVenueListActivity.class);
        intent.putExtra("username", name);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void openUserJoinedEventsActivity(String name, String email) {
        Intent intent = new Intent(this, DOMINIK_userJoinedEventRV.class);
        intent.putExtra("username", name);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void openUserScheduledEventsActivity(String name, String email) {
        Intent intent = new Intent(this, DOMINIK_UserScheduledEventRV.class);
        intent.putExtra("username", name);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}

