package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePageActivity extends AppCompatActivity {
    Button userPage;
    Button adminPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        userPage = findViewById(R.id.user_register_button);
        adminPage = findViewById(R.id.admin_login_button);

        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserLogin();
            }
        });

        adminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                venueModel temp = new venueModel("venueOne", "UTSC");
//                DataBaseClass dat = new DataBaseClass("Venues");
//                dat.add(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(getApplicationContext(), "Event joined successfully", Toast.LENGTH_SHORT).show();
//                    }
//                });
                openAdminLogin();
            }
        });

    }

    public void openUserLogin(){
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }
    public void openAdminLogin(){
        Intent intent = new Intent(this, AdminLoginActivity.class);
        startActivity(intent);
    }
}