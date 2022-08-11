package com.example.b07_project;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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

public class DOMINIK_manageVenuesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Spinner spinner;
    DatabaseReference venuesRef;
    DOMINIK_manageVenuesEventsAdapter userEventsAdapter;
    ArrayList<eventModel> list;
    ArrayList<eventModel> oldList;
    List<String> venueList;
    String filteredVenue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_venue_activity_rv_spinner_dominik);

        recyclerView = findViewById(R.id.adminManageVenuesRVid);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Spinner spinner = (Spinner) findViewById(R.id.adminVenueSpinnerid);
        venuesRef = FirebaseDatabase.getInstance().getReference().child("Venues");

        venuesRef.addValueEventListener(new ValueEventListener() {
            List<String> allVenues = new ArrayList<String>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    String venue = shot.child("venueName").getValue(String.class);
                    if(!(allVenues.contains(venue))){
                        allVenues.add(venue);
                    }
                }

                venueList = allVenues;
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(DOMINIK_manageVenuesActivity.this, android.R.layout.simple_spinner_dropdown_item, allVenues);
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
        oldList = new ArrayList<eventModel>();
        userEventsAdapter = new DOMINIK_manageVenuesEventsAdapter(this);
        userEventsAdapter.addInfo(list, oldList);
        recyclerView.setAdapter(userEventsAdapter);
        venuesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot shot : snapshot.getChildren()) {
                    HashMap<String, HashMap<String, String>> temp = (HashMap<String, HashMap<String, String>>) shot.child("venueEvents").getValue();
                    if (temp == null) {
                        continue;
                    } else {
                        for (HashMap<String, String> value : temp.values()) {
                            if (value != null) {
                                eventModel event = new eventModel(value.get("name"), value.get("date"), value.get("venue"),
                                        value.get("maxParticipants"), value.get("noParticipants"),
                                        value.get("startTime"), value.get("endTime"));
                                if(!(list.contains(event)) && !(oldList.contains(event))) {
                                    list.add(event);
                                    oldList.add(event);
                                }
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
                ((TextView)  adapterView.getChildAt(0)).setTextSize(22);
                filteredVenue = venueList.get(i);
                if(list.equals(oldList)){
                    userEventsAdapter.filter(filteredVenue);
                } else {
                    list.addAll(oldList);
                    userEventsAdapter.filter(filteredVenue);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                filteredVenue = "";
                if(list.equals(oldList)){
                    userEventsAdapter.filter(filteredVenue);
                } else {
                    list.addAll(oldList);
                    userEventsAdapter.filter(filteredVenue);
                }

            }
        });
    }
}


