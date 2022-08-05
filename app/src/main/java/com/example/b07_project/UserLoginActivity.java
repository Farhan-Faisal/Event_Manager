package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserLoginActivity extends AppCompatActivity {
    // First thing we need to do is to define all the elements
    EditText uname;
    EditText password;
    Button login;
    Button register;

    DatabaseReference dbref;

    HashMap<String, String> userCollection = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This means that the main activity is being loaded
        setContentView(R.layout.activity_user_login);

        ArrayList<Venue> venues =  new ArrayList<Venue>();
        Venue one = new Venue("venueOne", "22/22/22", "17:30", "Canada", "12:15", "Hockey");
        Venue two = new Venue("venueTwo", "22/22/22", "17:30", "USA", "12:15", "Hockey, Soccer, Cricket");
        Venue three = new Venue("venueThree", "22/22/22", "17:30", "Spain", "12:15", "Hockey, Soccer, Cricket");


        venues.add(one);
        venues.add(two);
        venues.add(three);


        // We need to attach each variable to its XML element
        uname = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.buttonid);
        register = findViewById(R.id.registerid);;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get input name and password from the Edit texts
                user temp = new user(uname.getText().toString(), password.getText().toString());

                // Retrieve all users from database
                dbref = FirebaseDatabase.getInstance().getReference().child("user");
                dbref.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            String n = data.child("username").getValue().toString();
                            String p = data.child("password").getValue().toString();
                            userCollection.put(n, p);
                        }
                        if (temp.isValid(userCollection) == true){
                            openUserHomePageActivity(temp.username);
                        }
                        else if(temp.nameExists(userCollection) == true){
                            Toast.makeText(getApplicationContext(), "Incorrect Passowrd", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Failed to read data",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserRegisterActivity();
            }
        });
    }

    public void openUserRegisterActivity() {
        Intent intent = new Intent(this, UserRegisterActivity.class);
        startActivity(intent);
    }

    public void openUserHomePageActivity(String name){
        Intent intent = new Intent(this, FARHAN_UserHomepageActivity.class);
        intent.putExtra("username", name);
        startActivity(intent);
    }
}