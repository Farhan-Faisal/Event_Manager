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

import java.util.HashMap;

public class UserRegisterActivity extends AppCompatActivity {
    // Declare layout variables
    EditText name;
    EditText password;
    Button register;

    DatabaseReference dbref;
    HashMap<String, String> userCollection = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        // Initialize layout variables
        name = findViewById(R.id.userRegisterNameid);
        password = findViewById(R.id.userRegisterPasswordid);
        register = findViewById(R.id.userRegisterButtonid);

        // Database Reference class to add data to firebase
        DataBaseClass ref = new DataBaseClass((user.class.getSimpleName()).toString());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get input name and password from the Edit texts
                user temp = new user(name.getText().toString(), password.getText().toString());

                // Retrieve all users from database
                dbref = FirebaseDatabase.getInstance().getReference().child("user");
                dbref.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            String n = data.child("username").getValue().toString();
                            String p = data.child("password").getValue().toString();
                            userCollection.put(n, p);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError er) {
                        Toast.makeText(getApplicationContext(), "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                if (temp.nameExists(userCollection) == true) {
                    Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    // See if user or not
                    ref.add(temp).addOnSuccessListener(suc ->
                    {
                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(er ->
                    {
                        Toast.makeText(getApplicationContext(), "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                    openUserLogin();
                }
            }
        });
    }

    public void openUserLogin(){
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }
}