package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class FARHAN_UserScheduleActivity extends AppCompatActivity {

    EditText eventName;
    Button eventDate;
    Button eventStartTime;
    Button eventEndTime;
    EditText maxParticipants;
    Button schedule;

    int hour,minute;
    int hour2,minute2;

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
        eventStartTime = findViewById(R.id.user_schedule_event_start_time);
        eventEndTime = findViewById(R.id.user_schedule_event_end_time);
        maxParticipants = findViewById(R.id.user_schedule_event_no_participants);
        schedule = findViewById(R.id.user_schedule_event_button);

        Calendar calendar = Calendar.getInstance();
        final int year  = calendar.get(Calendar.YEAR);
        final int month  = calendar.get(Calendar.MONTH);
        final int day  = calendar.get(Calendar.DAY_OF_MONTH);


        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FARHAN_UserScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        eventDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        // Need to add the event to the user and venue node in Firebase
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is the scheduled event
                eventModel m = new eventModel(eventName.getText().toString(), eventDate.getText().toString(), venueName,
                        maxParticipants.getText().toString(), "0", eventStartTime.getText().toString(),
                        eventEndTime.getText().toString());

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
    public void popTimePicker3(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                eventStartTime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void popTimePicker4 (View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour2 = selectedHour;
                minute2 = selectedMinute;
                eventEndTime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour2, minute2));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener, hour2, minute2, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}