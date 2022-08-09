//package com.example.b07_project;
//
//import static android.content.ContentValues.TAG;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class MainActivity extends AppCompatActivity {
//
//    DatabaseReference databaseReferenceUser;
//    DatabaseReference databaseReferenceAdmin;
//
//
//    EditText EmailLogin;
//    EditText PasswordLogin;
//
//    Boolean UserExist;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button makeaccount = findViewById(R.id.makeaccount);
//        Button login = findViewById(R.id.login);
//
//        EmailLogin = findViewById(R.id.emaillogin);
//        PasswordLogin = findViewById(R.id.passwordlogin);
//
//        makeaccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openMainActivity2();
//            }
//        });
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String EmailString = EmailLogin.getText().toString();
//                String PasswordString = PasswordLogin.getText().toString();
//
//                checkuser(EmailString,PasswordString);
//            }
//        });
//    }
//    public void openMainActivity2(){
//        Intent intent = new Intent(this,MainActivity2.class);
//        startActivity(intent);
//    }
//
//    public void openMainActivity3(String UserName){
//        Intent intent = new Intent(this,MainActivity3.class);
//        intent.putExtra("UserName",UserName);
//        startActivity(intent);
//    }
//
//    public void checkuser(String EmailString,String PasswordString){
//        UserExist = true;
//        databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("userTest").child(String.valueOf(EmailString.hashCode()));
//        databaseReferenceAdmin = FirebaseDatabase.getInstance().getReference().child("adminTest").child(String.valueOf(EmailString.hashCode()));
//
//        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.getValue() == null) {
//                    // So its an admin
//                    databaseReferenceAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.getValue() == null) {
//                                Toast.makeText(getApplicationContext(), "User Does Not Exist", Toast.LENGTH_SHORT).show();
//                            } else {
//                                if(!PasswordString.equals(snapshot.child("Password").getValue().toString())){
//                                    Toast.makeText(getApplicationContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
//                                    openMainActivity3(snapshot.child("Name").getValue().toString());
//                                    return;
//                                }
//                            }
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                } else {
//                    // So its a user
//                    if(!PasswordString.equals(snapshot.child("Password").getValue().toString())){
//                        Toast.makeText(getApplicationContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
//                        openMainActivity3(snapshot.child("Name").getValue().toString());
//                        return;
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }
//
//}
//
//
//
//// USer login activity
//package com.example.b07_project;
//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.EditText;
//        import android.widget.Toast;
//
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//
//        import java.util.ArrayList;
//        import java.util.HashMap;
//
//public class UserLoginActivity extends AppCompatActivity {
//    // First thing we need to do is to define all the elements
//    EditText uname;
//    EditText password;
//    Button login;
//    Button register;
//
//    DatabaseReference dbref;
//
//    HashMap<String, String> userCollection = new HashMap<String, String>();
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // This means that the main activity is being loaded
//        setContentView(R.layout.activity_user_login);
//
////        ArrayList<Venue> venues =  new ArrayList<Venue>();
////        Venue one = new Venue("venueOne", "22/22/22", "17:30", "Canada", "12:15", "Hockey");
////        Venue two = new Venue("venueTwo", "22/22/22", "17:30", "USA", "12:15", "Hockey, Soccer, Cricket");
////        Venue three = new Venue("venueThree", "22/22/22", "17:30", "Spain", "12:15", "Hockey, Soccer, Cricket");
////
////
////        venues.add(one);
////        venues.add(two);
////        venues.add(three);
//
//
//        // We need to attach each variable to its XML element
//        uname = findViewById(R.id.username);
//        password = findViewById(R.id.password);
//        login = findViewById(R.id.buttonid);
//        register = findViewById(R.id.registerid);;
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get input name and password from the Edit texts
//                user temp = new user(uname.getText().toString(), password.getText().toString());
//
//                // Retrieve all users from database
//                dbref = FirebaseDatabase.getInstance().getReference().child("user");
//                dbref.addListenerForSingleValueEvent(new ValueEventListener(){
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot){
//                        for(DataSnapshot data: dataSnapshot.getChildren()){
//                            String n = data.child("username").getValue().toString();
//                            String p = data.child("password").getValue().toString();
//                            userCollection.put(n, p);
//                        }
//                        if (temp.isValid(userCollection) == true){
//                            openUserHomePageActivity(temp.username);
//                        }
//                        else if(temp.nameExists(userCollection) == true){
//                            Toast.makeText(getApplicationContext(), "Incorrect Passowrd", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getApplicationContext(), "Failed to read data",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openUserRegisterActivity();
//            }
//        });
//    }
//
//    public void openUserRegisterActivity() {
//        Intent intent = new Intent(this, UserRegisterActivity.class);
//        startActivity(intent);
//    }
//
//    public void openUserHomePageActivity(String name){
//        Intent intent = new Intent(this, FARHAN_UserHomepageActivity.class);
//        intent.putExtra("username", name);
//        startActivity(intent);
//    }
//}
//
//package com.example.b07_project;
//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.EditText;
//        import android.widget.Toast;
//
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//
//        import java.util.HashMap;
//
//public class UserRegisterActivity extends AppCompatActivity {
//    // Declare layout variables
//    EditText name;
//    EditText password;
//    Button register;
//
//    DatabaseReference dbref;
//    HashMap<String, String> userCollection = new HashMap<String, String>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_register);
//
//        // Initialize layout variables
//        name = findViewById(R.id.userRegisterNameid);
//        password = findViewById(R.id.userRegisterPasswordid);
//        register = findViewById(R.id.userRegisterButtonid);
//
//        // Database Reference class to add data to firebase
//        DataBaseClass ref = new DataBaseClass((user.class.getSimpleName()).toString());
//
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get input name and password from the Edit texts
//                user temp = new user(name.getText().toString(), password.getText().toString());
//
//                // Retrieve all users from database
//                dbref = FirebaseDatabase.getInstance().getReference().child("user");
//                dbref.addValueEventListener(new ValueEventListener(){
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot){
//                        for(DataSnapshot data: dataSnapshot.getChildren()){
//                            String n = data.child("username").getValue().toString();
//                            String p = data.child("password").getValue().toString();
//                            userCollection.put(n, p);
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError er) {
//                        Toast.makeText(getApplicationContext(), "" + er.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                if (temp.nameExists(userCollection) == true) {
//                    Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    // See if user or not
//                    ref.add(temp).addOnSuccessListener(suc ->
//                    {
//                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
//                    }).addOnFailureListener(er ->
//                    {
//                        Toast.makeText(getApplicationContext(), "" + er.getMessage(), Toast.LENGTH_SHORT).show();
//                    });
//                    openUserLogin();
//                }
//            }
//        });
//    }
//
//    public void openUserLogin(){
//        Intent intent = new Intent(this, AVIRAJ_UserLoginActivity.class);
//        startActivity(intent);
//    }
//}
//
//package com.example.b07_project;
//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.graphics.Color;
//        import android.os.Bundle;
//        import android.util.Log;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.EditText;
//        import android.widget.RadioButton;
//        import android.widget.Toast;
//
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//
//        import java.util.ArrayList;
//        import java.io.*;
//        import java.util.HashMap;
//        import java.util.regex.Matcher;
//        import java.util.regex.Pattern;
//
//public class MainActivity2 extends AppCompatActivity {
//
//    private FirebaseDatabase db = FirebaseDatabase.getInstance();
//    private DatabaseReference rootuser = db.getReference().child("user");
//
//    DatabaseReference databaseReference;
//    Boolean Confirm;
//    ArrayList<String> Emails = new ArrayList<String>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//
//        EditText UserName = findViewById(R.id.Username);
//        EditText Email = findViewById(R.id.email);
//        EditText Password = findViewById(R.id.password);
//        EditText ConfirmPassword = findViewById(R.id.confirmpassword);
//
//        Button user = findViewById(R.id.user);
//
//        email_adder();
//
//        user.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String UserNameString = UserName.getText().toString();
//                String EmailString = Email.getText().toString();
//                String PasswordString = Password.getText().toString();
//                String PasswordConfirmString = ConfirmPassword.getText().toString();
//
//                Confirm = Emails.contains(EmailString);
//
//                HashMap<String,String> users = new HashMap<>();
//
//                users.put("Name",UserNameString);
//                users.put("Email",EmailString);
//                users.put("Password",PasswordString);
//
//                if(Confirm)
//                    Toast.makeText(MainActivity2.this, "Account Already Exist", Toast.LENGTH_LONG).show();
//                else if(validate(UserNameString,EmailString,PasswordString,PasswordConfirmString)) {
//                    rootuser.child(String.valueOf(EmailString.hashCode())).setValue(users);
//
//                    Toast.makeText(MainActivity2.this, "User Account Registered", Toast.LENGTH_LONG).show();
//                    finish();
//                }
//            }
//        });
//
//        admin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String UserNameString = UserName.getText().toString();
//                String EmailString = Email.getText().toString();
//                String PasswordString = Password.getText().toString();
//                String PasswordConfirmString = ConfirmPassword.getText().toString();
//
//                Confirm = Emails.contains(EmailString);
//
//                HashMap<String,String> admins = new HashMap<>();
//
//                admins.put("Name",UserNameString);
//                admins.put("Email",EmailString);
//                admins.put("Password",PasswordString);
//
//                if(Confirm)
//                    Toast.makeText(MainActivity2.this, "Account Already Exist", Toast.LENGTH_LONG).show();
//                else if(validate(UserNameString,EmailString,PasswordString,PasswordConfirmString)) {
//                    rootadmin.child(String.valueOf(EmailString.hashCode())).setValue(admins);
//                    Toast.makeText(MainActivity2.this, "Admin Account Registered", Toast.LENGTH_LONG).show();
//                    finish();
//                }
//            }
//        });
//    }
//    public boolean validate(String UserNameString,String EmailString,String PasswordString,String PasswordConfirmString) {
//        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
//        Matcher matcher = pattern.matcher(EmailString);
//        if (UserNameString.equals("") || EmailString.equals("") || PasswordString.equals("") || PasswordConfirmString.equals("")){
//            Toast.makeText(MainActivity2.this,"Fill In All Of The Boxes",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (!(matcher.matches())){
//            Toast.makeText(MainActivity2.this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if(!PasswordString.equals(PasswordConfirmString)){
//            Toast.makeText(MainActivity2.this,"Password Not Matching",Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
//    }
//
//    public void email_adder(){
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("adminTest");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    Emails.add(data.child("Email").getValue().toString());
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("userTest");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    Emails.add(data.child("Email").getValue().toString());
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
//}
//
//package com.example.b07_project;
//
//        import static android.content.ContentValues.TAG;
//
//        import android.content.Context;
//        import android.content.Intent;
//        import android.content.SharedPreferences;
//        import android.os.Bundle;
//        import android.util.Log;
//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.recyclerview.widget.LinearLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;
//
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//
//        import java.util.ArrayList;
//        import java.util.HashMap;
//
//public class UserScheduledEventRV_Dominik extends AppCompatActivity {
//    RecyclerView recyclerView;
//    //DatabaseReference databaseReferenceEventsJoined;
//    DatabaseReference databaseReferenceEventsScheduled;
//    userEventsJoinedAdapter_Dominik userEventsAdapter;
//    ArrayList<eventModel> list;
//    SharedPreferences sharedPreferences;
//
//    String username;
//    String email
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.user_scheduled_event_rv_dominik);
//
//        sharedPreferences = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
//
//        // Retrieve data from intent into shared preference
//        if (getIntent().hasExtra("username")) {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("username", getIntent().getStringExtra("username"));
//            editor.putString("email", getIntent().getStringExtra("email"));
//            editor.commit();
//        }
//        username = sharedPreferences.getString("username", null);
//        email = sharedPreferences.getString("email", null);
//        //Log.d("CREATION", username);
//
//        recyclerView = findViewById(R.id.userScheduledEventRVid);
//        //NEED TO CREATE INTENT SO THAT I CAN GET THE USER WHICH IS LOGGED IN AND DO
//        // USER + / + events
//        databaseReferenceEventsScheduled = FirebaseDatabase.getInstance().getReference().child("user");
//        //databaseReferenceEventsJoined = FirebaseDatabase.getInstance().getReference(username + "/" + "userScheduledEvents");
//        //recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        list = new ArrayList<eventModel>();
//        userEventsAdapter = new userEventsJoinedAdapter_Dominik(this, list);
//        recyclerView.setAdapter(userEventsAdapter);
//
//        databaseReferenceEventsScheduled.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot shot : snapshot.getChildren()) {
//                    String eventInformation = shot.child("username").getValue().toString();
//                    if (eventInformation.compareTo(username) == 0) {
//                        HashMap<String, HashMap<String, String>> temp = (HashMap<String, HashMap<String, String>>) shot.child("userScheduledEvents").getValue();
//                        if (temp == null){
//                            openNoEventScheduledActivity();
//                            break;
//                        }
//                        else {
//                            for (HashMap<String, String> value : temp.values()) {
//                                list.add(new eventModel(value.get("name"), value.get("date"), value.get("venue"),
//                                        value.get("maxParticipants"), value.get("noParticipants"),
//                                        value.get("startTime"), value.get("endTime")));
//                            }
//                        }
//                    }
//                }
//                userEventsAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//    }
//
//    public void openNoEventScheduledActivity(){
//        Intent intent = new Intent(this, NoEventScheduledActivity.class);
//        startActivity(intent);
//    }
//}
//
//package com.example.b07_project;
//
//        import androidx.annotation.NonNull;
//        import androidx.annotation.Nullable;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.content.Context;
//        import android.content.Intent;
//        import android.content.SharedPreferences;
//        import android.os.Bundle;
//        import android.os.Parcelable;
//        import android.util.Log;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.AdapterView;
//        import android.widget.ArrayAdapter;
//        import android.widget.ListView;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//
//        import java.util.ArrayList;
//
//public class UserEventListActivity extends AppCompatActivity {
//
//    ListView listView;
//    String username;
//
//    DatabaseReference dbref;
//    SharedPreferences sp;
//    String key;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_event_list);
//
//        dbref = FirebaseDatabase.getInstance().getReference().child("Venues");
//        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
//
//        // Retrieve data from intent into shared preference
//        if(getIntent().hasExtra("username")){
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("username", getIntent().getStringExtra("username"));
//            editor.commit();
//        }
//        username = sp.getString("username", null);
//
//        ArrayList<eventModel> events = new ArrayList<>();
//        ArrayList<String> titles = new ArrayList<>();
////        ArrayList<String> eventTitleList = new ArrayList<>(0);
////        ArrayList<String> venueNameList = new ArrayList<>(0);
////        ArrayList<String> eventDateList = new ArrayList<>(0);
////        ArrayList<String> spaceAvailabilityList = new ArrayList<>(0);
////        ArrayList<String> noParticipantsList = new ArrayList<>(0);
////        ArrayList<String> eventTimeList = new ArrayList<>(0);
//
////        String [] eventTitle = eventTitleList.toArray(new String[0]);
////        String [] venueName = venueNameList.toArray(new String[0]);
////        String [] eventDate = eventDateList.toArray(new String[0]);
////        String [] spaceAvailability = spaceAvailabilityList.toArray(new String[0]);
////        String [] noParticipants = noParticipantsList.toArray(new String[0]);
////        String [] eventTime = eventTimeList.toArray(new String[0]);
//
//        listView = findViewById(R.id.user_event_list_id);
//
//        // Need to make out own adapter class
//        MyAdapter adapter = new MyAdapter(this, events, titles);
////        MyAdapter adapter = new MyAdapter(this, eventTitleList, venueNameList, eventDateList,
////                spaceAvailabilityList, noParticipantsList, eventTimeList);
//        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.event_node, R.id.eventTitleid, eventTitleList);
//        listView.setAdapter(adapter);
//
//        // Now, just need to create item click on list view
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                openSpecificEventActivity(username, events.get(position));
//            }
//        });
//
//        ValueEventListener listener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                events.clear();
//                titles.clear();
//                for (DataSnapshot venue: snapshot.getChildren()) {
//                    for (DataSnapshot event : venue.child("venueEvents").getChildren()) {
//
//                        try{
//                            eventModel e = event.getValue(eventModel.class);
//                            events.add(e);
//                            titles.add(e.name);
////                            eventTitleList.add(event.child("name").getValue().toString());
////                            venueNameList.add(event.child("venue").getValue().toString());
////                            eventDateList.add(event.child("date").getValue().toString());
////                            spaceAvailabilityList.add(event.child("space").getValue().toString());
////                            noParticipantsList.add("5");
////                            eventTimeList.add(event.child("time").getValue().toString() + " - " + event.child("endTime").getValue().toString());
//                        }
//                        catch(Exception e)
//                        {
//                            Log.d("Title", event.child("name").getValue().toString());
//                        }
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//        dbref.addValueEventListener(listener);
//    }
//
//    class MyAdapter extends ArrayAdapter<String> {
//        Context context;
//        ArrayList<eventModel> events;
//
//        MyAdapter(Context c, ArrayList<eventModel> events, ArrayList<String> titles){
//            super(c, R.layout.event_node, R.id.eventTitleid, titles);
//            ArrayList<String> title = new ArrayList<>();
//            for (eventModel e : events) {
//                title.add(e.name);
//            }
//            this.events = events;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
//            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService
//                    (Context.LAYOUT_INFLATER_SERVICE);
//            View eventNode = layoutInflater.inflate(R.layout.event_node, parent, false);
//            TextView title = eventNode.findViewById(R.id.eventTitleid);
//            TextView venue = eventNode.findViewById(R.id.eventVenueid);
//            TextView date = eventNode.findViewById(R.id.eventDateid);
//            TextView startTime = eventNode.findViewById(R.id.eventStartTimeid);
//            TextView endTime = eventNode.findViewById(R.id.eventEndTimeid);
//            TextView space = eventNode.findViewById(R.id.eventSpaceid);
//            TextView count = eventNode.findViewById(R.id.eventCountid);
//
//            // Need to set resources on views
//            title.setText(events.get(position).name);
//            venue.setText("Venue: " + events.get(position).venue);
//            date.setText("Date: " + events.get(position).date);
//            startTime.setText("Start Time: " + events.get(position).startTime);
//            endTime.setText("End Time: " + events.get(position).endTime);
//            space.setText(events.get(position).space);
//            count.setText("No Participants: " + events.get(position).noParticipants);
//
//            return eventNode;
//        }
//    }
//
//    public void openSpecificEventActivity(String username, eventModel e){
//        Intent intent = new Intent(this, SpecificEventActivity.class);
//        intent.putExtra("event", e.convertToHashMap());
//        intent.putExtra("username", username);
//        startActivity(intent);
//    }
//}
//
//package com.example.b07_project;
//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.recyclerview.widget.LinearLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;
//
//        import android.content.Context;
//        import android.content.SharedPreferences;
//        import android.os.Bundle;
//        import android.util.Log;
//
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//
//        import java.util.ArrayList;
//        import java.util.HashMap;
//
//public class FARHAN_UserVenueEventListActivity extends AppCompatActivity {
//    SharedPreferences sp;
//    String username;
//    String email;
//    String venueName;
//
//    ArrayList<eventModel> list;
//
//    boolean added;
//
//    RecyclerView recyclerView;
//    DatabaseReference dbref;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.farhan_activity_user_venue_specific_event_list);
//
//        // Initialize shared preference
//        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
//
//        // Retrieve data from intent into shared preference
//        if (getIntent().hasExtra("username")) {
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("username", getIntent().getStringExtra("username"));
//            editor.putString("venueName", getIntent().getStringExtra("venueName"));
//            editor.putString("email", getIntent().getStringExtra("email"));
//            editor.commit();
//        }
//
//        // Retrieve data from shared preference into appropriate variables
//        username = sp.getString("username", null);
//        venueName = sp.getString("venueName", null);
//        email = sp.getString("email", null);
//
//
//        list = new ArrayList<eventModel>();
//        recyclerView = findViewById(R.id.user_venue_specific_event_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        FARHAN_VenueEventAdapter adapter = new FARHAN_VenueEventAdapter(this, list, username, email);
//        recyclerView.setAdapter(adapter);
//
//        dbref = FirebaseDatabase.getInstance().getReference().child("Venues");
//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    String n = data.child("venueName").getValue().toString();
//                    if (n.compareTo(venueName) == 0) {
//                        HashMap<String, HashMap<String, String>> temp = (HashMap<String, HashMap<String, String>>) data.child("venueEvents").getValue();
//                        for (HashMap<String, String> itr : temp.values()) {
//                            list.add(new eventModel(itr.get("name"), itr.get("date"), itr.get("venue"),
//                                    itr.get("maxParticipants"), itr.get("noParticipants"),
//                                    itr.get("startTime"), itr.get("endTime")));
//                        }
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
//    }
//}
//
//package com.example.b07_project;
//
//        import android.content.Context;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.Button;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import androidx.annotation.NonNull;
//        import androidx.recyclerview.widget.RecyclerView;
//
//        import com.google.android.gms.tasks.OnSuccessListener;
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//
//        import java.util.ArrayList;
//
//public class FARHAN_VenueEventAdapter extends RecyclerView.Adapter<FARHAN_VenueEventAdapter.VJEViewHolder> {
//    Context context;
//    String username;
//    String email;
//
//    ArrayList<eventModel> eventList = new ArrayList<>();
//
//    public FARHAN_VenueEventAdapter(Context context, ArrayList<eventModel> eventList, String username, String email) {
//        this.context = context;
//        this.eventList = eventList;
//        this.username = username;
//        this.email = email;
//    }
//
//    @NonNull
//    @Override
//    public FARHAN_VenueEventAdapter.VJEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // Inflate the layout here
//        // This is how we give look to our rows
//        LayoutInflater inflator = LayoutInflater.from(context);
//        View view = inflator.inflate(R.layout.farhan_venue_join_event_node, parent, false);
//        return new FARHAN_VenueEventAdapter.VJEViewHolder(view, eventList, username, email);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FARHAN_VenueEventAdapter.VJEViewHolder holder, int position) {
//        // Assign values to each row depending on the position
//        holder.title.setText(eventList.get(position).getName());
//        holder.venue.setText("Venue: " + eventList.get(position).getVenue());
//        holder.date.setText("Date: " + eventList.get(position).getDate());
//        holder.startTime.setText("Start Time: " + eventList.get(position).getStartTime());
//        holder.endTime.setText("End Time: " + eventList.get(position).getEndTime());
//        holder.space.setText(eventList.get(position).getSpace());
//        holder.no_part.setText("Participants: " + eventList.get(position).getNoParticipants());
//    }
//
//    @Override
//    public int getItemCount() {
//        // Get total number of items
//        return eventList.size();
//    }
//
//    public static class VJEViewHolder extends RecyclerView.ViewHolder{
//        // Sort of like the onCreate method
//        // Assign data from row to certain variables
//        TextView title; TextView venue;
//        TextView date; TextView startTime; TextView endTime;
//        TextView no_part; TextView space;
//
//        Button join;
//
//        String Username;
//        String Email;
//        String venueKey;
//        boolean added;
//
//        public VJEViewHolder(@NonNull View itemView, ArrayList<eventModel> eventList, String username, String email) {
//            super(itemView);
//            title = itemView.findViewById(R.id.joinEventTitleid);
//            venue = itemView.findViewById(R.id.joinEventVenueid);
//            date = itemView.findViewById(R.id.joinEventDateid);
//            startTime = itemView.findViewById(R.id.joinEventStartTimeid);
//            endTime = itemView.findViewById(R.id.joinEventEndTimeid);
//            no_part = itemView.findViewById(R.id.joinEventCountid);
//            space = itemView.findViewById(R.id.joinEventSpaceid);
//            join = itemView.findViewById(R.id.joinEventButtonid);
//            Username = username;
//            Email = email;
//
//            join.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    eventModel m = eventList.get(getAdapterPosition());
//
//                    // Check if the event is already in the user node
//                    // To do this, iterate through the user's events
//                    DataBaseClass checker = new DataBaseClass("user/" + Email.hashCode() + "/userEventsJoined");
//                    checker.dbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            added = false;
//                            for (DataSnapshot snap : snapshot.getChildren()) {
//
//                                eventModel temp = snap.getValue(eventModel.class);
//                                if (m.equals(temp) == true) {
//                                    added = true;
//                                    break;
//                                }
//                            }
//
//                            // Now add the event
//                            if (added == false) {
//                                // Need to increase participant count of the event
//                                // increase participant count
//                                eventModel temp1 = new eventModel(m);
//                                updateParticipantCountInVenues(temp1);
//                                updateParticipantCountInAllUsers(temp1);
//
//                                DataBaseClass dat = new DataBaseClass("user/" + Email.hashCode() + "/userEventsJoined");
//                                dat.add(temp1).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(view.getContext(), "Event joined successfully", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            } else {
//                                Toast.makeText(view.getContext(), "Event already joined", Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {}
//                    });
//
//                };
//            });
//        }
//
//        public void updateParticipantCountInVenues(eventModel m){
//            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Venues");
//            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    // Got the user node key
//                    for (DataSnapshot snap : snapshot.getChildren()) {
//                        String n = snap.child("venueName").getValue().toString();
//                        if (n.compareTo(m.getVenue()) == 0) {
//                            venueKey = snap.getKey();
//                        }
//                    }
//
//                    // Got the venue key
//                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Venues/" + venueKey + "/venueEvents");
//                    db.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot snap : snapshot.getChildren()) {
//                                eventModel e = snap.getValue(eventModel.class);
//                                if (e.equals(m) == true){
//                                    String eventKey = snap.getKey();
//                                    e.add_participant();
//                                    db.child(eventKey + "/space").setValue(e.getSpace());
//                                    db.child(eventKey + "/noParticipants").setValue(e.getNoParticipants());
//                                    break;
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {}
//                    });
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {}
//            });
//        }
//
//        public void updateParticipantCountInAllUsers(eventModel input){
//            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("user");
//            db.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot userNode: snapshot.getChildren()){
//                        DatabaseReference db1 = db.child(userNode.getKey() + "/userEventsJoined");
//                        db1.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                for (DataSnapshot userJoinedEvent: snapshot.getChildren()){
//                                    eventModel e = userJoinedEvent.getValue(eventModel.class);
//                                    if(e.equals(input) == true) {
//                                        e.add_participant();
//                                        db1.child(userJoinedEvent.getKey() + "/space").setValue(e.getSpace());
//                                        db1.child(userJoinedEvent.getKey() + "/noParticipants").setValue(e.getNoParticipants());
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {}
//                        });
//
//                        DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("user/" + userNode.getKey() + "/userScheduledEvents");
//                        db2.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                for (DataSnapshot userScheduledEvent: snapshot.getChildren()){
//                                    eventModel e = userScheduledEvent.getValue(eventModel.class);
//                                    if(e.equals(input) == true) {
//                                        e.add_participant();
//                                        db2.child(userScheduledEvent.getKey() + "/space").setValue(e.getSpace());
//                                        db2.child(userScheduledEvent.getKey() + "/noParticipants").setValue(e.getNoParticipants());
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {}
//                        });
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {}
//            });
//        }
//    }
//}
//
