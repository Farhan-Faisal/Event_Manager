package com.example.b07_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FARHAN_VenueEventAdapter extends RecyclerView.Adapter<FARHAN_VenueEventAdapter.VJEViewHolder> {
    Context context;
    String username;
    ArrayList<eventModel> eventList = new ArrayList<>();

    public FARHAN_VenueEventAdapter(Context context, ArrayList<eventModel> eventList, String username) {
        this.context = context;
        this.eventList = eventList;
        this.username = username;
    }

    @NonNull
    @Override
    public FARHAN_VenueEventAdapter.VJEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout here
        // This is how we give look to our rows
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.farhan_venue_join_event_node, parent, false);
        return new FARHAN_VenueEventAdapter.VJEViewHolder(view, eventList, username);
    }

    @Override
    public void onBindViewHolder(@NonNull FARHAN_VenueEventAdapter.VJEViewHolder holder, int position) {
        // Assign values to each row depending on the position
        holder.title.setText(eventList.get(position).getName());
        holder.venue.setText("Venue: " + eventList.get(position).getVenue());
        holder.date.setText("Date: " + eventList.get(position).getDate());
        holder.startTime.setText("Start Time: " + eventList.get(position).getStartTime());
        holder.endTime.setText("End Time: " + eventList.get(position).getEndTime());
        holder.space.setText(eventList.get(position).getSpace());
        holder.no_part.setText("Participants: " + eventList.get(position).getNoParticipants());
    }

    @Override
    public int getItemCount() {
        // Get total number of items
        return eventList.size();
    }

    public static class VJEViewHolder extends RecyclerView.ViewHolder{
        // Sort of like the onCreate method
        // Assign data from row to certain variables
        TextView title;
        TextView venue;
        TextView date;
        TextView startTime;
        TextView endTime;
        TextView no_part;
        TextView space;
        Button schedule;

        String key;
        boolean added;

        public VJEViewHolder(@NonNull View itemView, ArrayList<eventModel> eventList, String username) {
            super(itemView);
            title = itemView.findViewById(R.id.joinEventTitleid);
            venue = itemView.findViewById(R.id.joinEventVenueid);
            date = itemView.findViewById(R.id.joinEventDateid);
            startTime = itemView.findViewById(R.id.joinEventStartTimeid);
            endTime = itemView.findViewById(R.id.joinEventEndTimeid);
            no_part = itemView.findViewById(R.id.joinEventCountid);
            space = itemView.findViewById(R.id.joinEventSpaceid);
            schedule = itemView.findViewById(R.id.joinEventButtonid);

            schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventModel m = eventList.get(getAdapterPosition());
                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("user");
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Got the user node key
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                String n = snap.child("username").getValue().toString();
                                if (n.compareTo(username) == 0) {
                                    key = snap.getKey();
                                }
                            }


                            // Check if the event is already in the user node
                            // To do this, iterate through the user's events
                            DataBaseClass checker = new DataBaseClass("user/" + key + "/userEventsJoined");
                            checker.dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    added = false;
                                    for (DataSnapshot snap : snapshot.getChildren()) {
                                        String name = snap.child("name").getValue().toString();
                                        String date = snap.child("date").getValue().toString();
                                        String venue = snap.child("venue").getValue().toString();
                                        String startTime = snap.child("startTime").getValue().toString();
                                        String endTime = snap.child("endTime").getValue().toString();
                                        String maxParticipants = snap.child("maxParticipants").getValue().toString();
                                        String noParticipants = snap.child("noParticipants").getValue().toString();

                                        eventModel temp = new eventModel(name, date, venue, maxParticipants,
                                                noParticipants, startTime, endTime);
                                        if (m.equals(temp) == true) {
                                            added = true;
                                            break;
                                        }
                                    }

                                    // Now add the event
                                    if (added == false) {
                                        // Need to increase participant count of the event
                                        // increase participant count

                                        DataBaseClass dat = new DataBaseClass("user/" + key + "/userEventsJoined");
                                        dat.add(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(view.getContext(), "Event joined successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(view.getContext(), "Event already joined", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                };
            });
        }
    }
}
