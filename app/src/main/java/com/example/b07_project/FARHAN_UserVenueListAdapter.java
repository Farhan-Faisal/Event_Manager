package com.example.b07_project;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FARHAN_UserVenueListAdapter extends RecyclerView.Adapter<FARHAN_UserVenueListAdapter.UVViewHolder> {
    Context context;
    ArrayList<Venue> venueList = new ArrayList<>();
    String username;
    String email;

    public FARHAN_UserVenueListAdapter(Context context, ArrayList<Venue> venueList, String username, String email) {
        this.context = context;
        this.venueList = venueList;
        this.username = username;
        this.email = email;
    }

    @NonNull
    @Override
    public FARHAN_UserVenueListAdapter.UVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout here
        // This is how we give look to our rows
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.farhan_venue_row_recycler_view, parent, false);
        return new FARHAN_UserVenueListAdapter.UVViewHolder(view, username, venueList, email);
    }

    @Override
    public void onBindViewHolder(@NonNull FARHAN_UserVenueListAdapter.UVViewHolder holder, int position) {
        // Assign values to each row depending on the position
        holder.title.setText(venueList.get(position).getVenueName());
        holder.location.setText(venueList.get(position).getLocation());
        holder.sports.setText(venueList.get(position).getSports());
    }

    @Override
    public int getItemCount() {
        // Get total number of items
        return venueList.size();
    }

    public static class UVViewHolder extends RecyclerView.ViewHolder{
        // Sort of like the onCreate method
        // Assign data from row to certain variables
        TextView title;
        TextView location;
        TextView sports;
        Button schedule;
        Button join;

        String Username;
        String Email;

        public UVViewHolder(@NonNull View itemView, String username, ArrayList<Venue> venueList, String email) {
            super(itemView);

            this.Username = username;
            this.Email = email;

            // Bind views
            title = itemView.findViewById(R.id.venueNodeTitleid);
            location = itemView.findViewById(R.id.venueNodeLocationid);
            schedule = itemView.findViewById(R.id.venueNodeScheduleButtonid);
            join = itemView.findViewById(R.id.venueNodeJoinButtonid);
            sports = itemView.findViewById(R.id.venueNodeSportsid);

            // Need to set on click listener here
            schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), FARHAN_UserScheduleActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("venueName", venueList.get(getAdapterPosition()).getVenueName());
                    intent.putExtra("venueSports", venueList.get(getAdapterPosition()).getSports());
                    intent.putExtra("email", email);
                    view.getContext().startActivity(intent);
                }
            });

            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Venues");
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Check if venue node has any events or not
                            String venueName = venueList.get(getAdapterPosition()).getVenueName();
                            for (DataSnapshot shot : snapshot.getChildren()) {
                                String venue = shot.child("venueName").getValue().toString();
                                if(venue.compareTo(venueName) != 0){
                                    continue;
                                }
                                else {
                                    HashMap<String, HashMap<String, String>> temp = (HashMap<String, HashMap<String, String>>) shot.child("venueEvents").getValue();
                                    if (temp == null) {
                                        Toast.makeText(view.getContext(), "No events at this venue", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Intent intent = new Intent(itemView.getContext(), FARHAN_UserVenueEventListActivity.class);
                                        intent.putExtra("username", Username);
                                        intent.putExtra("venueName", venueList.get(getAdapterPosition()).getVenueName());
                                        intent.putExtra("venueSports", venueList.get(getAdapterPosition()).getSports());
                                        intent.putExtra("email", Email);
                                        view.getContext().startActivity(intent);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
        }
    }
}

