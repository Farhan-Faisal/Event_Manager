package com.example.b07_project;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Use this class to add a user to the database
public class DataBaseClass {

    // Create a database reference
    public DatabaseReference dbref;

    public DataBaseClass(String name){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbref = db.getReference(name);
    }

    public Task<Void> add (Object u){
        return dbref.push().setValue(u);
    }


}
