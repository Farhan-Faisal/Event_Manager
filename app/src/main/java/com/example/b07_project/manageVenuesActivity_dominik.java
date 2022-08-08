package com.example.b07_project;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.util.List;

public class manageVenuesActivity_dominik extends AppCompatActivity {
    RecyclerView recyclerView;
    Spinner spinner;
    DatabaseReference venuesRef;
    userEventsJoinedAdapter_Dominik userEventsAdapter;
    ArrayList<eventModel> list;
    ArrayList<eventModel> filteredList;
    List<String> venueList;
    String filteredVenue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_venue_activity_rv_spinner_dominik);

        //String spinnerVenueText = spinner.getSelectedItem().toString();
        recyclerView = findViewById(R.id.adminManageVenuesRVid);
        Spinner spinner = (Spinner) findViewById(R.id.adminVenueSpinnerid);
        venuesRef = FirebaseDatabase.getInstance().getReference().child("Venues");

        venuesRef.addValueEventListener(new ValueEventListener() {
            List<String> allVenues = new ArrayList<String>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    String venue = shot.child("venueName").getValue(String.class);
                    allVenues.add(venue);
                }

                venueList = allVenues;
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(manageVenuesActivity_dominik.this, android.R.layout.simple_spinner_dropdown_item, allVenues);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<eventModel>();
        filteredList = new ArrayList<eventModel>();
        userEventsAdapter = new userEventsJoinedAdapter_Dominik(this, filteredList);
        recyclerView.setAdapter(userEventsAdapter);

        venuesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot shot : snapshot.getChildren()) {
                    HashMap<String, HashMap<String, String>> temp = (HashMap<String, HashMap<String, String>>) shot.child("venueEvents").getValue();
                    if (temp == null) {
                        continue;
                    } else {
                        Log.d("TAG", "1");
                        for (HashMap<String, String> value : temp.values()) {
                            if (value != null) {
                                list.add(new eventModel(value.get("name"), value.get("date"), value.get("venue"),
                                        value.get("maxParticipants"), value.get("noParticipants"),
                                        value.get("startTime"), value.get("endTime")));
                                filteredList.add(new eventModel(value.get("name"), value.get("date"), value.get("venue"),
                                        value.get("maxParticipants"), value.get("noParticipants"),
                                        value.get("startTime"), value.get("endTime")));
                            }
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filteredVenue = venueList.get(i);
                if(filteredList.equals(list)){
                    userEventsAdapter.filter(filteredVenue);
                } else {
                    filteredList.addAll(list);
                    userEventsAdapter.filter(filteredVenue);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                filteredVenue = "";
                if(filteredList.equals(list)){
                    userEventsAdapter.filter(filteredVenue);
                } else {
                    filteredList.addAll(list);
                    userEventsAdapter.filter(filteredVenue);
                }

            }
        });
    }

}


