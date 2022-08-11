package com.example.b07_project;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DOMINIK_manageVenuesEventsAdapter  extends RecyclerView.Adapter<DOMINIK_manageVenuesEventsAdapter.UserEventsViewholder> {

    Context context;
    ArrayList<eventModel> events;
    ArrayList<eventModel> oldList;

    public DOMINIK_manageVenuesEventsAdapter(Context context) {
        this.context = context;
        this.events = new ArrayList<eventModel>();
        //this.events = list;
        this.oldList = new ArrayList<eventModel>();
        //this.oldList = oldList;
    }

    public void addInfo(ArrayList<eventModel> list, ArrayList<eventModel> oldList){
        this.events = list;
        this.oldList = oldList;
    }

    @NonNull
    @Override
    public DOMINIK_manageVenuesEventsAdapter.UserEventsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dominik_manage_venue_events_recycler, parent, false);
        return new DOMINIK_manageVenuesEventsAdapter.UserEventsViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DOMINIK_manageVenuesEventsAdapter.UserEventsViewholder holder, int position) {
        eventModel user = events.get(position);
        holder.name.setText(user.getName());
        holder.noParticipants.setText("No. Participants: " + user.getNoParticipants());
        holder.date.setText("Date: " + user.getDate());
        holder.startTime.setText("Start Time: " + user.getStartTime());
        holder.endTime.setText("End Time: " + user.getStartTime());
        holder.venue.setText("Venue: " + user.getVenue());
        holder.space.setText(user.getSpace());
        holder.deleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deleteAlert = new AlertDialog.Builder(view.getContext());
                deleteAlert.setMessage("Are you sure you want to delete this event?");
                deleteAlert.setCancelable(true);

                deleteAlert.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference venueChild = databaseReference.child("Venues");

                                DatabaseReference userChild = databaseReference.child("user");

                                venueChild.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot shot : snapshot.getChildren()) {
                                            String venueName = shot.child("venueName").getValue().toString();
                                            if (venueName.compareTo(user.getVenue()) == 0) {
                                                if (shot.child("venueEvents").exists()) {
                                                    for (DataSnapshot childShots : shot.child("venueEvents").getChildren()) {
                                                        eventModel event = childShots.getValue(eventModel.class);
                                                        if (event.equals(user)) {
                                                            childShots.getRef().removeValue();
                                                        }

                                                    }
                                                }

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.w(TAG, "Failed to read value.", error.toException());
                                    }
                                });
                                userChild.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot shot : snapshot.getChildren()) {
                                            if (shot.child("userEventsJoined").exists()) {
                                                for (DataSnapshot joinedShot : shot.child("userEventsJoined").getChildren()) {
                                                    eventModel event = joinedShot.getValue(eventModel.class);
                                                    if (event.equals(user)) {
                                                        joinedShot.getRef().removeValue();

                                                        if(events.contains(event)) {
                                                            events.remove(event);
                                                        }
                                                        if(oldList.contains(event)){
                                                            oldList.remove(event);
                                                        }


                                                    }
                                                }
                                            }

                                            if (shot.child("userScheduledEvents").exists()) {
                                                for (DataSnapshot scheduledShot : shot.child("userScheduledEvents").getChildren()) {
                                                    eventModel event = scheduledShot.getValue(eventModel.class);
                                                    if (event.equals(user)) {
                                                        scheduledShot.getRef().removeValue();

                                                        if(events.contains(event)) {
                                                            events.remove(event);
                                                        }
                                                        if(oldList.contains(event)){
                                                            oldList.remove(event);
                                                        }


                                                    }
                                                }
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.w(TAG, "Failed to read value.", error.toException());
                                    }
                                });

                                notifyItemRemoved(events.indexOf(user));
                                notifyItemRangeChanged(events.indexOf(user), events.size());

                            }
                        });

                deleteAlert.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                );

                AlertDialog alertDialog = deleteAlert.create();
                alertDialog.show();

            }
        });
    }

    public void filter(String filterVenue){
        ArrayList<eventModel> temp = new ArrayList<eventModel>();
        temp.addAll(events);

        events.clear();
        if(filterVenue.length() == 0){
            events.addAll(temp);
        } else {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).venue.compareTo(filterVenue) == 0) {
                    events.add(temp.get(i));
                }
            }
        }
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class UserEventsViewholder extends RecyclerView.ViewHolder {
        TextView name, noParticipants, date, startTime, endTime, space, venue, deleteEventButton;
        public UserEventsViewholder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            noParticipants = itemView.findViewById(R.id.noParticipants);
            date = itemView.findViewById(R.id.date);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);
            venue = itemView.findViewById(R.id.venue);
            space = itemView.findViewById(R.id.space);
            deleteEventButton = itemView.findViewById((R.id.deleteEventButtonId));


        }
    }
}
