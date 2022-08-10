package com.example.collusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String NewVenue = "venueFive";
        String NewName = " Cricket";
        String NewStart = "04:00";
        String NewEnd = "22:00";
        String NewDate = "9/8/2022";

        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot users : snapshot.getChildren()){
                    for(DataSnapshot events : users.child("userScheduledEvents").getChildren())
                    {
                        Log.i("Test",events.toString());
                        if(events.child("venue").getValue().toString().equals(NewVenue) && events.child("name").getValue().toString().equals(NewName) && events.child("date").getValue().toString().equals(NewDate)){
                            if(Timechecker(events.child("startTime").getValue().toString(),events.child("endTime").getValue().toString(),NewStart,NewEnd)){
                                Toast.makeText(getApplicationContext(), "Sorry, Please change your time. There is already booking for the event from "+events.child("startTime").getValue().toString()+" till "+events.child("endTime").getValue().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public boolean Timechecker(String Start, String End, String NewStartTime, String NewEndTime) {
        //DateTimeFormatter.ofPattern()
        DateTimeFormatter Format = DateTimeFormatter.ofPattern("HH:mm",Locale.CANADA);
        LocalTime start = LocalTime.parse(Start, Format);
        LocalTime end = LocalTime.parse(End, Format);
        LocalTime NewStart = LocalTime.parse(NewStartTime, Format);
        LocalTime NewEnd = LocalTime.parse(NewEndTime, Format);

        if(Start.matches(NewStartTime+"|"+NewEndTime) || End.matches(NewStartTime+"|"+NewEndTime))
            return true;

        if (end.isAfter(start)) {
            if (start.isBefore(NewStart) && end.isAfter(NewStart)) {
                return true;
            }
        } else if (NewStart.isAfter(start) || NewStart.isBefore(end)) {
            return true;
        }

        if (end.isAfter(start)) {
            if (start.isBefore(NewEnd) && end.isAfter(NewEnd)) {
                return true;
            }
        } else if (NewEnd.isAfter(start) || NewEnd.isBefore(end)) {
            return true;
        }

        return false;
    }
}