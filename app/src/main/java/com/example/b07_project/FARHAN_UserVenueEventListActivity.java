package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FARHAN_UserVenueEventListActivity extends AppCompatActivity {

    ListView listView;

    DatabaseReference dbref;

    SharedPreferences sp;
    String username;
    String email;
    String venueName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_list);

        dbref = FirebaseDatabase.getInstance().getReference().child("Venues");
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if(getIntent().hasExtra("username")){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.putString("email", getIntent().getStringExtra("email"));
            editor.putString("venueName", getIntent().getStringExtra("venueName"));
            editor.commit();
        }
        username = sp.getString("username", null);
        email = sp.getString("email", null);
        venueName = sp.getString("venueName", null);

        ArrayList<eventModel> events = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();


        // Bind the views
        listView = findViewById(R.id.user_event_list_id);

        // Need to make out own adapter class
        MyAdapter adapter = new MyAdapter(this, events, titles);
        listView.setAdapter(adapter);

        // Now, just need to create item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                openSpecificEventActivity(username, events.get(position), email);
            }
        });

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                events.clear();
                titles.clear();
                for (DataSnapshot venue: snapshot.getChildren()) {
                    String v = venue.child("venueName").getValue().toString();
                    if (v.compareTo(venueName) == 0) {
                        for (DataSnapshot event : venue.child("venueEvents").getChildren()) {
                            try {
                                eventModel e = event.getValue(eventModel.class);
                                events.add(e);
                                titles.add(e.name);
                            } catch (Exception e) {
                                Log.d("Title", event.child("name").getValue().toString());
                            }
                        }
                    }
                }
                if (events.size() == 0){
                    Intent intent = new Intent(getApplicationContext(), noUpcomingEventsActivity.class);
                    startActivity(intent);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dbref.addValueEventListener(listener);
    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        ArrayList<eventModel> events;

        MyAdapter(Context c, ArrayList<eventModel> events, ArrayList<String> titles){
            super(c, R.layout.event_node, R.id.eventTitleid, titles);
            ArrayList<String> title = new ArrayList<>();
            for (eventModel e : events) {
                title.add(e.name);
            }
            this.events = events;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            View eventNode = layoutInflater.inflate(R.layout.event_node, parent, false);
            TextView title = eventNode.findViewById(R.id.eventTitleid);
            TextView venue = eventNode.findViewById(R.id.eventVenueid);
            TextView date = eventNode.findViewById(R.id.eventDateid);
            TextView startTime = eventNode.findViewById(R.id.eventStartTimeid);
            TextView endTime = eventNode.findViewById(R.id.eventEndTimeid);
            TextView space = eventNode.findViewById(R.id.eventSpaceid);
            TextView count = eventNode.findViewById(R.id.eventCountid);

            // Need to set resources on views
            title.setText(events.get(position).name);
            venue.setText("Venue: " + events.get(position).venue);
            date.setText("Date: " + events.get(position).date);
            startTime.setText("Start Time: " + events.get(position).startTime);
            endTime.setText("End Time: " + events.get(position).endTime);
            space.setText(events.get(position).space);
            count.setText("No Participants: " + events.get(position).noParticipants);

            return eventNode;
        }
    }

    public void openSpecificEventActivity(String username, eventModel e, String email){
        Intent intent = new Intent(this, JASON_SpecificEventActivity.class);
        intent.putExtra("event", e.convertToHashMap());
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}