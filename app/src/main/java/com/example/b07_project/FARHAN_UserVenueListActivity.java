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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FARHAN_UserVenueListActivity extends AppCompatActivity {
    // Declare layout variables
    RecyclerView recyclerView;

    // Declare shared preference
    SharedPreferences sp;
    String username;
    String email;

    // Declare variable to save intent/preferences
    // ArrayList<venue> venues = new ArrayList<venue>();
    ArrayList<Venue> venueModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farhan_activity_user_venue_list);

        // Initialize shared preference
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if(getIntent().hasExtra("username")){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.putString("email", getIntent().getStringExtra("email"));
            editor.commit();
        }

        // Retrieve data from shared preference into appropriate variables
        username = sp.getString("username", null);
        email = sp.getString("email", null);

        // Bind views and adapters
        recyclerView = findViewById(R.id.user_venue_list_rv_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FARHAN_UserVenueListAdapter adapter = new FARHAN_UserVenueListAdapter(this,  venueModels, username, email);
        recyclerView.setAdapter(adapter);

        // Get all venues from the database
        DataBaseClass checker = new DataBaseClass("Venues");
        checker.dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    String name = snap.child("venueName").getValue().toString();
                    String loc = snap.child("location").getValue().toString();
                    String ST = snap.child("Start Time").getValue().toString();
                    String ET = snap.child("End Time").getValue().toString();
                    String sports = snap.child("Sports").getValue().toString();
                    venueModels.add(new Venue(name, ET, loc, ST, sports));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}