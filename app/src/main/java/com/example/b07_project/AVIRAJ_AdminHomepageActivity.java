package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AVIRAJ_AdminHomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aviraj_activity_admin_homepage);
        Button simpleButton = findViewById(R.id.button3);
        simpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity2();
            }
        });

        // DOMINIK - new button for managing venues (user story 7)
        Button manageVenuesButton = findViewById(R.id.manageVenuesButton);
        manageVenuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openManageVenues();}
        });
    }

    public void openMainActivity2(){
        Intent intent = new Intent(this, AVIRAJ_AdminVenueCreationActivity.class);
        startActivity(intent);
    }

    public void openManageVenues(){
        Intent intent = new Intent(this, manageVenuesActivity_dominik.class);
        startActivity(intent);
    }
}