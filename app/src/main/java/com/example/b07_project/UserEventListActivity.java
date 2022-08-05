package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserEventListActivity extends AppCompatActivity {

    ListView listView;
    String username;

    DatabaseReference dbref;
    SharedPreferences sp;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_list);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if(getIntent().hasExtra("username")){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.commit();
        }
        username = sp.getString("username", null);

        String [] eventTitle = {"Soccer", "Basket Ball", "Hockey"};
        String [] venueName = {"venue1", "venue2", "venue3"};
        String [] eventDate = {"Dec 22, 2022", "Jan 21, 2022", "Nov 20, 2022"};
        String [] spaceAvailability = {"Full", "Full", "Join"};
        String [] noParticipants = {"22", "22", "18"};
        String [] eventTime = {"Morning", "Evening", "Morning"};

        listView = findViewById(R.id.user_event_list_id);

        // Need to make out own adapter class
        MyAdapter adapter = new MyAdapter(this, eventTitle, venueName, eventDate,
                spaceAvailability, noParticipants, eventTime);
        listView.setAdapter(adapter);

        // Now, just need to create item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                openSpecificEventActivity(username, eventTitle[position], venueName[position], eventDate[position],
                        spaceAvailability[position], noParticipants[position], eventTime[position]);
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String [] rEventTitle;
        String [] rVenueName;
        String [] rEventDate;
        String [] rSpaceAvailability;
        String [] rNoParticipants;
        String [] rEventTime;

        MyAdapter(Context c, String[] title, String[] venue, String[] date,
                  String[] space, String[] time, String[] count){
            super(c, R.layout.event_node, R.id.eventTitleid, title);
            this.rEventTitle = title;
            this.rVenueName = venue;
            this.rEventDate = date;
            this.rSpaceAvailability = space;
            this.rEventTime = time;
            this.rNoParticipants = count;
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
            TextView time = eventNode.findViewById(R.id.eventTimeid);
            TextView space = eventNode.findViewById(R.id.eventSpaceid);
            TextView count = eventNode.findViewById(R.id.eventCountid);

            // Need to set resources on views
            title.setText(rEventTitle[position]);
            venue.setText(rVenueName[position]);
            date.setText(rEventDate[position]);
            time.setText(rEventTime[position]);
            space.setText(rSpaceAvailability[position]);
            count.setText(rNoParticipants[position]);

            return eventNode;
        }
    }

    public void openSpecificEventActivity(String name, String event, String venue, String date,
                                          String space, String count, String time){
        Intent intent = new Intent(this, SpecificEventActivity.class);
        intent.putExtra("eventTitle", event);
        intent.putExtra("venueName", venue);
        intent.putExtra("eventDate", date);
        intent.putExtra("spaceAvailability", space);
        intent.putExtra("noParticipants", count);
        intent.putExtra("eventTime", time);
        intent.putExtra("username", name);
        startActivity(intent);
    }
}