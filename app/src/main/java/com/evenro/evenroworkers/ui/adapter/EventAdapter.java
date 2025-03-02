package com.evenro.evenroworkers.ui.adapter;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.ui.model.Event;


import java.util.ArrayList;
import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private String locationName;
    class EventViewHolder extends RecyclerView.ViewHolder {

        TextView event_name;
        TextView event_organizer_name;
        TextView event_date;
        TextView event_time;
        TextView event_location;
        TextView event_price;
        TextView event_qty;
        ImageView event_image;
        TextView event_category;



        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_title);
            event_organizer_name = itemView.findViewById(R.id.organizer_name);
            event_location = itemView.findViewById(R.id.event_location);
            event_date = itemView.findViewById(R.id.event_date);
            event_price = itemView.findViewById(R.id.event_price);
            event_time = itemView.findViewById(R.id.event_time);
            event_qty = itemView.findViewById(R.id.event_qty);
            event_image = itemView.findViewById(R.id.event_image);
            event_category = itemView.findViewById(R.id.event_category);
        }
    }

    ArrayList<Event> eventDetails;
    String locations;

    public EventAdapter(ArrayList<Event> eventDetails) {
        this.eventDetails = eventDetails;
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_details_item, parent, false);
        EventViewHolder eventViewHolder = new EventViewHolder(view);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        Event event = eventDetails.get(position);
        holder.event_name.setText(event.getEventName());
        holder.event_organizer_name.setText(event.getEventOrganizerName());
        holder.event_date.setText(event.getEventDate());
        holder.event_time.setText(event.getEventTime());
        holder.event_location.setText(locations);
        holder.event_price.setText(event.getEventPrice());
        holder.event_category.setText(event.getEventCategory());
        holder.event_qty.setText(event.getEventQty());
        Glide.with(holder.event_image.getContext())
                .load(Uri.parse(event.getImageUri()))
                .into(holder.event_image);
    }

    @Override
    public int getItemCount() {
        return eventDetails.size();
    }
}

