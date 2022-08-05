package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FARHAN_UserVenueEventListActivity extends AppCompatActivity {
    SharedPreferences sp;
    String username;
    String venueName;

    ArrayList<eventModel> list;

    boolean added;

    RecyclerView recyclerView;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farhan_activity_user_venue_specific_event_list);

        // Initialize shared preference
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if (getIntent().hasExtra("username")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.putString("venueName", getIntent().getStringExtra("venueName"));
            editor.commit();
        }

        // Retrieve data from shared preference into appropriate variables
        username = sp.getString("username", null);
        venueName = sp.getString("venueName", null);

        Log.d("CREATION", username);
        Log.d("CREATION", venueName);


        list = new ArrayList<eventModel>();
        recyclerView = findViewById(R.id.user_venue_specific_event_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FARHAN_VenueEventAdapter adapter = new FARHAN_VenueEventAdapter(this, list, username);
        recyclerView.setAdapter(adapter);

        dbref = FirebaseDatabase.getInstance().getReference().child("Venues");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String n = data.child("venueName").getValue().toString();
                    if (n.compareTo(venueName) == 0) {
                        HashMap<String, HashMap<String, String>> temp = (HashMap<String, HashMap<String, String>>) data.child("venueEvents").getValue();
                        for (HashMap<String, String> itr : temp.values()) {
                            list.add(new eventModel(itr.get("name"), itr.get("date"), itr.get("venue"),
                                    String.valueOf(itr.get("noParticipants")), itr.get("time")));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}