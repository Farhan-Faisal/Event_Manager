package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SpecificEventActivity extends AppCompatActivity {

    SharedPreferences sp;
    String username;
    String userKey;
    String venueKey;
    boolean added;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_event);

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

        // Bind the android features to ids
        TextView title = findViewById(R.id.specificEventTitleid);
        TextView date = findViewById(R.id.specificEventDateid);
        TextView venue = findViewById(R.id.specificEventVenueid);
        TextView noPar = findViewById(R.id.specificEventCountid);
        TextView space = findViewById(R.id.specificEventSpaceid);
        TextView startTime = findViewById(R.id.specificEventStartTimeid);
        TextView endTime = findViewById(R.id.specificEventEndTimeid);
        Button join = findViewById(R.id.specific_event_button);

        // Retrieve the intent
        HashMap<String, String> input = (HashMap<String, String>) getIntent().getSerializableExtra("event");
        eventModel specificEvent = new eventModel(input.get("name"),
                input.get("date"), input.get("venue"), input.get("maxParticipants"),
                input.get("noParticipants"), input.get("startTime"), input.get("endTime"));

        // Assign Texts to the android fields
        title.setText(specificEvent.getName());
        date.setText("- Date: " + specificEvent.getDate());
        venue.setText("- Venue: " +  specificEvent.getVenue());
        noPar.setText("- No Participants: " + specificEvent.getNoParticipants());
        space.setText("- " + specificEvent.getSpace());
        startTime.setText("- StartTime: " + specificEvent.getStartTime());
        endTime.setText("- StartTime: " + specificEvent.getEndTime());

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((specificEvent.getSpace()).compareTo("Event Full") == 0){
                    Toast.makeText(getApplicationContext(), "Event Full!", Toast.LENGTH_SHORT).show();
                }
                else {
                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("user");
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Got the user node key
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                String n = snap.child("username").getValue().toString();
                                if (n.compareTo(username) == 0) {
                                    userKey = snap.getKey();
                                }
                            }


                            // Check if the event is already in the user node
                            // To do this, iterate through the user's events
                            DataBaseClass checker = new DataBaseClass("user/" + userKey + "/userEventsJoined");
                            checker.dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    added = false;
                                    for (DataSnapshot snap : snapshot.getChildren()) {
                                        eventModel temp = snap.getValue(eventModel.class);
                                        if (specificEvent.equals(temp) == true) {
                                            added = true;
                                            break;
                                        }
                                    }

                                    // Now add the event
                                    if (added == false) {
                                        // Need to increase participant count of the event in venue node
                                        updateParticipantCountInVenues(specificEvent);
                                        updateParticipantCountInAllUsers(specificEvent);

                                        specificEvent.add_participant();
                                        // Add event in userEventsJoined node
                                        DataBaseClass dat = new DataBaseClass("user/" + userKey + "/userEventsJoined");
                                        dat.add(specificEvent).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(view.getContext(), "Event joined successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        Toast.makeText(view.getContext(), "Event already joined", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }
        });
    }

    public void updateParticipantCountInVenues(eventModel m){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Venues");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Got the user node key
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String n = snap.child("venueName").getValue().toString();
                    if (n.compareTo(m.getVenue()) == 0) {
                        venueKey = snap.getKey();
                    }
                }

                // Got the venue key
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Venues/" + venueKey + "/venueEvents");
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                           eventModel e = snap.getValue(eventModel.class);
                           if (e.equals(m) == true){
                               String eventKey = snap.getKey();
                               e.add_participant();
                               db.child(eventKey + "/space").setValue(e.getSpace());
                               db.child(eventKey + "/noParticipants").setValue(e.getNoParticipants());
                               break;
                           }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void updateParticipantCountInAllUsers(eventModel input){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("user");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userNode: snapshot.getChildren()){
                    DatabaseReference db1 = db.child(userNode.getKey() + "/userEventsJoined");
                    db1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot userJoinedEvent: snapshot.getChildren()){
                                eventModel e = userJoinedEvent.getValue(eventModel.class);
                                if(e.equals(input) == true) {
                                    e.add_participant();
                                    db1.child(userJoinedEvent.getKey() + "/space").setValue(e.getSpace());
                                    db1.child(userJoinedEvent.getKey() + "/noParticipants").setValue(e.getNoParticipants());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("user/" + userNode.getKey() + "/userScheduledEvents");
                    db2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot userScheduledEvent: snapshot.getChildren()){
                                eventModel e = userScheduledEvent.getValue(eventModel.class);
                                if(e.equals(input) == true) {
                                    e.add_participant();
                                    db2.child(userScheduledEvent.getKey() + "/space").setValue(e.getSpace());
                                    db2.child(userScheduledEvent.getKey() + "/noParticipants").setValue(e.getNoParticipants());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}