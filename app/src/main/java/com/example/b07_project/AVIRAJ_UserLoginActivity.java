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

public class AVIRAJ_UserLoginActivity extends AppCompatActivity {

    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceAdmin;


    EditText EmailLogin;
    EditText PasswordLogin;

    Boolean UserExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aviraj_activity_user_login);

        Button makeaccount = findViewById(R.id.makeaccount);
        Button login = findViewById(R.id.login);

        EmailLogin = findViewById(R.id.emaillogin);
        PasswordLogin = findViewById(R.id.passwordlogin);

        makeaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationActivity();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmailString = EmailLogin.getText().toString();
                String PasswordString = PasswordLogin.getText().toString();

                checkuser(EmailString,PasswordString);
            }
        });
    }
    public void openRegistrationActivity(){
        Intent intent = new Intent(this, AVIRAJ_UserRegisterActivity.class);
        startActivity(intent);
    }

    public void openUserHomePageActivity(String username, String email){
        Intent intent = new Intent(this, FARHAN_UserHomepageActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void checkuser(String EmailString,String PasswordString){
        UserExist = true;
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("user").child(String.valueOf(EmailString.hashCode()));

        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                } else {
                    // So its a user
                    if(!PasswordString.equals(snapshot.child("password").getValue().toString())){
                        Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                        openUserHomePageActivity(snapshot.child("username").getValue().toString(), snapshot.child("email").getValue().toString());
                        return;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT).show();

            }
        });

    }

}