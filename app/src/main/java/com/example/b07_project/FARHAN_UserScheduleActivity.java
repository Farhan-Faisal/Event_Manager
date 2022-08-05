package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FARHAN_UserScheduleActivity extends AppCompatActivity {

    EditText eventName;
    EditText eventDate;
    EditText eventTime;
    EditText noParticipants;
    Button schedule;

    SharedPreferences sp;
    String venueName;
    String username;

    DatabaseReference dbref;
    DatabaseReference dbref2;

    String userKey;
    String venueKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farhan_activity_user_schedule);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if(getIntent().hasExtra("username")){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.putString("venueName", getIntent().getStringExtra("venueName"));
            editor.commit();
        }
        username = sp.getString("username", null);
        venueName = sp.getString("venueName", null);

        // Bind the android items
        eventName = findViewById(R.id.user_schedule_event_name);
        eventDate = findViewById(R.id.user_schedule_event_date);
        eventTime = findViewById(R.id.user_schedule_event_time);
        noParticipants = findViewById(R.id.user_schedule_event_no_participants);
        schedule = findViewById(R.id.user_schedule_event_button);


        // Need to add the event to the user and venue node in Firebase
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is the scheduled event
                eventModel m = new eventModel(eventName.getText().toString(), eventDate.getText().toString(), venueName,
                        noParticipants.getText().toString(), eventTime.getText().toString());

                // Update event in venue node
                dbref = FirebaseDatabase.getInstance().getReference().child("Venues");
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Got the venue push key
                        for (DataSnapshot snap : snapshot.getChildren()){
                            String n = snap.child("venueName").getValue().toString();
                            if (n.compareTo(venueName) == 0){
                                venueKey = snap.getKey();
                            }
                        }

                        // Added event in appropriate venue node
                        DataBaseClass checker = new DataBaseClass("Venues/" + venueKey + "/venueEvents");
                        checker.add(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Event scheduled successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

                // Add event in user node
                dbref2 = FirebaseDatabase.getInstance().getReference().child("user");
                dbref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Got the user push key
                        for (DataSnapshot snap : snapshot.getChildren()){
                            String n = snap.child("username").getValue().toString();
                            if (n.compareTo(username) == 0){
                                userKey = snap.getKey();
                            }
                        }
                        DataBaseClass checker = new DataBaseClass("user/" + userKey + "/userScheduledEvents");
                        checker.add(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Event scheduled successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });
    }
}