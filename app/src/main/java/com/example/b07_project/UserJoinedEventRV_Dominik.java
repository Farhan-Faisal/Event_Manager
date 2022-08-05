package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserJoinedEventRV_Dominik extends AppCompatActivity {
    SharedPreferences sp;
    String username;
    String key;

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    userEventsJoinedAdapter_Dominik myAdapter;
    ArrayList<eventModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_events_joined_list);

        // Initialize shared preference
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if (getIntent().hasExtra("username")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.commit();
        }

        // Retrieve data from shared preference into appropriate variables
        username = sp.getString("username", null);

        recyclerView = findViewById(R.id.userJoinedEventRVid);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        list = new ArrayList<eventModel>();
        myAdapter = new userEventsJoinedAdapter_Dominik(getApplicationContext(), list);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String n = data.child("username").getValue().toString();
                    if (n.compareTo(username) == 0) {
                        HashMap<String, HashMap<String, String>> temp = (HashMap<String, HashMap<String, String>>) data.child("userEventsJoined").getValue();
                        for (HashMap<String, String> itr : temp.values()) {
                            list.add(new eventModel(itr.get("name"), itr.get("date"), itr.get("venue"),
                                    String.valueOf(itr.get("noParticipants")), itr.get("time")));
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}