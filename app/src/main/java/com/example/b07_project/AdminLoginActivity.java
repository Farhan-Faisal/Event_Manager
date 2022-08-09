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

public class AdminLoginActivity extends AppCompatActivity {

    EditText name;
    EditText password;
    Button login;

    DatabaseReference ref;
    HashMap<String, String> adminCollection = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aviraj_activity_admin_login);

        name = findViewById(R.id.admin_name_id);
        password = findViewById(R.id.admin_password_id);
        login = findViewById(R.id.admin_login_button_id);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin temp = new admin(name.getText().toString(), password.getText().toString());

                // Retrieve admin info from FireBase to admin_collection
                ref = FirebaseDatabase.getInstance().getReference().child("admin");
                ref.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            String n = data.child("name").getValue().toString();
                            String p = data.child("password").getValue().toString();
                            adminCollection.put(n, p);
                        }

                        if (temp.validate(adminCollection) == true){
                            openAdminHomePageActivity();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Not an admin",
                                    Toast.LENGTH_SHORT).show();
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
    }

    public void openAdminHomePageActivity() {
        Intent intent = new Intent(this, AVIRAJ_AdminHomepageActivity.class);
        startActivity(intent);
    }
}