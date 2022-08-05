package com.example.b07_project;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserScheduledEventRV_Dominik extends AppCompatActivity {
    RecyclerView recyclerView;
    //DatabaseReference databaseReferenceEventsJoined;
    DatabaseReference databaseReferenceEventsScheduled;
    userEventsJoinedAdapter_Dominik userEventsAdapter;
    ArrayList<eventModel> list;
    SharedPreferences sharedPreferences;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_scheduled_event_rv_dominik);

        sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if (getIntent().hasExtra("username")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.commit();
        }
        username = sharedPreferences.getString("username", null);
        //Log.d("CREATION", username);

        recyclerView = findViewById(R.id.userScheduledEventRVid);
        //NEED TO CREATE INTENT SO THAT I CAN GET THE USER WHICH IS LOGGED IN AND DO
        // USER + / + events
        databaseReferenceEventsScheduled = FirebaseDatabase.getInstance().getReference().child("user");
        //databaseReferenceEventsJoined = FirebaseDatabase.getInstance().getReference(username + "/" + "userScheduledEvents");
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<eventModel>();
        userEventsAdapter = new userEventsJoinedAdapter_Dominik(this, list);
        recyclerView.setAdapter(userEventsAdapter);

        databaseReferenceEventsScheduled.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot shot : snapshot.getChildren()) {
                    String eventInformation = shot.child("username").getValue().toString();
                    if (eventInformation.compareTo(username) == 0) {
                        HashMap<String, HashMap<String, String>> temp = (HashMap<String, HashMap<String, String>>) shot.child("userScheduledEvents").getValue();
                        for (HashMap<String, String> value : temp.values()) {
                            list.add(new eventModel(value.get("name"), value.get("date"), value.get("venue"),
                                    String.valueOf(value.get("noParticipants")), value.get("time")));
                        }
                    }
                }
                userEventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}

