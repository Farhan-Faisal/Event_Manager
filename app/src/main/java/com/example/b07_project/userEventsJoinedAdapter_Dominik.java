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

public class userEventsJoinedAdapter_Dominik extends RecyclerView.Adapter<userEventsJoinedAdapter_Dominik.UserEventsViewholder> {
    Context context;
    ArrayList<eventModel> events;
    ArrayList<eventModel> eventsCopy;

    public userEventsJoinedAdapter_Dominik(Context context, ArrayList<eventModel> list) {
        this.context = context;
        this.events = list;
        this.eventsCopy = new ArrayList<eventModel>(list);

    }

    @NonNull
    @Override
    public userEventsJoinedAdapter_Dominik.UserEventsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_row_recycler_view_dominik, parent, false);
        return new userEventsJoinedAdapter_Dominik.UserEventsViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserEventsViewholder holder, int position) {
        eventModel user = events.get(position);
        holder.name.setText(user.getName());
        holder.noParticipants.setText(user.getNoParticipants());
        holder.date.setText(user.getDate());
        holder.startTime.setText(user.getStartTime());
        holder.endTime.setText(user.getStartTime());
        holder.venue.setText(user.getVenue());
        holder.space.setText(user.getSpace());

    }
    public void filter(String filterVenue){
        ArrayList<eventModel> temp = new ArrayList<eventModel>();
        temp.addAll(events);

        events.clear();
        if(filterVenue.length() == 0){
            events.addAll(eventsCopy);
        } else {
            for (int i = 0; i < temp.size(); i++) {
                Log.d("TAG", temp.get(i).venue);
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