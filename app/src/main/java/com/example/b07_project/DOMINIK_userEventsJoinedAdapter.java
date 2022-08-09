package com.example.b07_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DOMINIK_userEventsJoinedAdapter extends RecyclerView.Adapter<DOMINIK_userEventsJoinedAdapter.UserEventsViewholder> {
    Context context;
    ArrayList<eventModel> events;

    public DOMINIK_userEventsJoinedAdapter(Context context, ArrayList<eventModel> list) {
        this.context = context;
        this.events = list;
    }

    @NonNull
    @Override
    public DOMINIK_userEventsJoinedAdapter.UserEventsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dominik_event_row_recycler_view, parent, false);
        return new DOMINIK_userEventsJoinedAdapter.UserEventsViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserEventsViewholder holder, int position) {
        eventModel user = events.get(position);
        holder.name.setText(user.getName());
        holder.noParticipants.setText("No. Participants: " + user.getNoParticipants());
        holder.date.setText("Date: " + user.getDate());
        holder.startTime.setText("Start Time: " + user.getStartTime());
        holder.endTime.setText("End Time: " + user.getStartTime());
        holder.venue.setText("Venue: " + user.getVenue());
        holder.space.setText(user.getSpace());
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
        TextView name, noParticipants, date, startTime, endTime, space, venue;
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


        }
    }
}