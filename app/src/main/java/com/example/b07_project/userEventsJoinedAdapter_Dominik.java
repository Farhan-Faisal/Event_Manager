package com.example.b07_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class userEventsJoinedAdapter_Dominik extends RecyclerView.Adapter<userEventsJoinedAdapter_Dominik.VEViewHolder> {
    Context context;
    ArrayList<eventModel> eventList = new ArrayList<>();

    public userEventsJoinedAdapter_Dominik(Context context, ArrayList<eventModel> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public VEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout here
        // This is how we give look to our rows
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.event_row_recycler_view_dominik, parent, false);
        return new userEventsJoinedAdapter_Dominik.VEViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VEViewHolder holder, int position) {
        // Assign values to each row depending on the position
        holder.title.setText(eventList.get(position).getName());
        holder.venue.setText(eventList.get(position).getVenue());
        holder.date.setText(eventList.get(position).getDate());
        holder.time.setText(eventList.get(position).getTime());
        holder.space.setText(eventList.get(position).getSpace());
        holder.no_part.setText(eventList.get(position).getMaxParticipants());
    }

    @Override
    public int getItemCount() {
        // Get total number of items
        return eventList.size();
    }

    public static class VEViewHolder extends RecyclerView.ViewHolder{
        // Sort of like the onCreate method
        // Assign data from row to certain variables
        TextView title; TextView venue; TextView date;
        TextView time; TextView no_part; TextView space;

        public VEViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.eventRowTitleid);
            venue = itemView.findViewById(R.id.eventRowVenueid);
            date = itemView.findViewById(R.id.eventRowDateid);
            time = itemView.findViewById(R.id.eventRowTimeid);
            no_part = itemView.findViewById(R.id.eventRowCountid);
            space = itemView.findViewById(R.id.eventRowSpaceid);
        }
    }
}
