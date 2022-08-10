package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

public class FARHAN_UserScheduleActivity extends AppCompatActivity {

    Button eventName;
    Button eventDate;
    Button eventStartTime;
    Button eventEndTime;

    int hour,minute;
    int hour2,minute2;

    Button schedule;

    HashMap<String, String> SportParticipant = new HashMap<String, String>();

    String[] SportsArray;

    SharedPreferences sp;
    String venueName;
    String username;
    String venueSports;

    DatabaseReference dbref;
    DatabaseReference dbref2;

    String userKey;
    String venueKey;

    TextView maxParticipants;

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
            editor.putString("venueSports", getIntent().getStringExtra("venueSports"));
            editor.commit();
        }
        username = sp.getString("username", null);
        venueName = sp.getString("venueName", null);
        venueSports = sp.getString("venueSports", null);
        Log.d("SPORTS", venueSports);
        SportsArray = venueSports.split("\\,");

        SportParticipant.put("BasketBall", "12");
        SportParticipant.put("Football", "22");
        SportParticipant.put("Soccer", "22");
        SportParticipant.put("Cricket", "20");
        SportParticipant.put("Baseball", "18");
        SportParticipant.put("Hockey", "12");
        SportParticipant.put("Tennis", "2");
        SportParticipant.put("Volleyball", "6");
        SportParticipant.put("Table Tennis", "2");
        SportParticipant.put("Rugby", "30");

        // Bind android items
        eventName = findViewById(R.id.user_schedule_event_name);
        eventDate = findViewById(R.id.user_schedule_event_date);
        eventStartTime = findViewById(R.id.user_schedule_event_start_time);
        eventEndTime = findViewById(R.id.user_schedule_event_end_time);
        maxParticipants = findViewById(R.id.user_schedule_event_no_participants);
        schedule = findViewById(R.id.user_schedule_event_button);

        // Bind the event name  functionality
        eventName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FARHAN_UserScheduleActivity.this);

                builder.setTitle("Select the Event Type");
                builder.setSingleChoiceItems(SportsArray, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eventName.setText(SportsArray[i]);
                        maxParticipants.setText(SportParticipant.get(SportsArray[i].trim()));
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

                // Show alert
                builder.show();
            }
        });

        // Implement date button functionality
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

    public void popTimePicker(View view) {
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

        timePickerDialog.setTitle("Select Start Time");
        timePickerDialog.show();
    }

    public void popTimePicker2 (View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                if ((selectedHour>hour && selectedHour<=23) || (selectedHour==hour && selectedMinute>minute && selectedMinute<59)) {
                    hour2 = selectedHour;
                    minute2 = selectedMinute;
                    eventEndTime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour2, minute2));
                } else {
                    Toast.makeText(FARHAN_UserScheduleActivity.this, "End Time should be more then Start Time and end before next day", Toast.LENGTH_LONG).show();
                    popTimePicker2(view);
                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener, hour2, minute2, true);
        timePickerDialog.setTitle("Select End Time");
        timePickerDialog.show();
    }
}