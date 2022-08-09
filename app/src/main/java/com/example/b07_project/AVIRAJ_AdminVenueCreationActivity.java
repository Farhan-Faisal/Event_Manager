package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class AVIRAJ_AdminVenueCreationActivity extends AppCompatActivity {
    EditText Venue;
    EditText Name;
    Button EventDate;
    Button Stattime;
    Button Endtime;
    int hour,minute;
    int hour2,minute2;

    StringBuilder stringBuilder;
    Button Sports;
    boolean[] SelectedSports;
    ArrayList<Integer> SportsList = new ArrayList<>();
    String[] SportsArray = {"BasketBall","Football","Soccer","Cricket","Baseball","Hockey","Tennis","Volleyball","Table Tennis","Rugby"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aviraj_activity_admin_venue_creation);

        Button simpleButton = findViewById(R.id.button2);
        simpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity3();
            }
        });

        Sports = findViewById(R.id.sports);
        SelectedSports = new boolean[SportsArray.length];
        Sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AVIRAJ_AdminVenueCreationActivity.this
                );

                builder.setTitle("Select Sports Activities");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(SportsArray, SelectedSports, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b){
                            SportsList.add(i);
                            Collections.sort(SportsList);
                        } else {
                            SportsList.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        stringBuilder = new StringBuilder();
                        for(int j=0;j<SportsList.size();j++)
                        {
                            stringBuilder.append(SportsArray[SportsList.get(j)]);
                            if(j != SportsList.size()-1)
                            {
                                stringBuilder.append(", ");
                            }
                        }
                        Sports.setText(stringBuilder.toString());
                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0;j<SelectedSports.length;j++)
                        {
                            SelectedSports[j] = false;
                            SportsList.clear();
                            Sports.setText("");
                        }
                    }
                });
                builder.show();
            }
        });


        Venue = findViewById(R.id.Venue);
        Name = findViewById(R.id.Name);
        Stattime = findViewById(R.id.Stime);
        Endtime = findViewById(R.id.Etime);

        Calendar calendar = Calendar.getInstance();
        final int year  = calendar.get(Calendar.YEAR);
        final int month  = calendar.get(Calendar.MONTH);
        final int day  = calendar.get(Calendar.DAY_OF_MONTH);


//        EventDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(AVIRAJ_AdminVenueCreationActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int day) {
//                        month = month + 1;
//                        String date = day+"/"+month+"/"+year;
//                        EventDate.setText(date);
//                    }
//                },year,month,day);
//                datePickerDialog.show();
//            }
//        });
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                Stattime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void popTimePicker2 (View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour2 = selectedHour;
                minute2 = selectedMinute;
                Endtime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour2, minute2));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener, hour2, minute2, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void openMainActivity3(){
        Intent intent = new Intent(this, AVIRAJ_AdminVenueConfirmationActivity.class);
        intent.putExtra("venueName", Name.getText().toString());
        intent.putExtra("location", Venue.getText().toString());
        intent.putExtra("Start Time", Stattime.getText().toString());
        intent.putExtra("End Time", Endtime.getText().toString());
        intent.putExtra("Sports", stringBuilder.toString());
        startActivity(intent);
    }
}