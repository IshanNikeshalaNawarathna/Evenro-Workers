package com.evenro.evenroworkers.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.ui.allEvent.ExpireActivity;
import com.evenro.evenroworkers.ui.model.ExpireEvent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ExpireEventAdapter extends RecyclerView.Adapter<ExpireEventAdapter.ExpireEventHolder> {

    ArrayList<ExpireEvent> expireEvents;

    public ExpireEventAdapter(ArrayList<ExpireEvent> expireEvents) {
        this.expireEvents = expireEvents;
    }

    @NonNull
    @Override
    public ExpireEventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.expire_event_item, parent, false);
        ExpireEventHolder expireEventHolder = new ExpireEventHolder(view);
        return expireEventHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpireEventHolder holder, int position) {
        ExpireEvent event = expireEvents.get(position);
        holder.expireEventName.setText(event.getExpireEventName());
        holder.expireEventDate.setText(event.getExpireEventDate());
        holder.expireEventQty.setText(event.getExpireEventQty());
        holder.expireEventOrganizerName.setText(event.getExpireEventOrganizerName());
Glide.with(holder.expireEventImage.getContext())
                .load(Uri.parse(event.getExpireEvenImage()))
                        .into(holder.expireEventImage);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setIcon(R.drawable.delete)// Set an icon (e.g., a checkmark)
                        .setTitle("Expire Event Delete") // Set the title
                        .setMessage("Your Deleted has been successfully completed!") // Set the message
                        .setPositiveButton("OK", (dialog, which) -> {
                            String expireEventId = event.getExpireEventId();
                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            firestore.collection("event").document(expireEventId)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(v.getContext(), "Delete Success", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(v.getContext(), ExpireActivity.class);
                                            v.getContext().startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(v.getContext(), "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                            dialog.dismiss();
                        });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return expireEvents.size();
    }

    class ExpireEventHolder extends RecyclerView.ViewHolder {

        private TextView expireEventName, expireEventOrganizerName, expireEventDate, expireEventQty;
        private ImageView expireEventImage;
        private ImageView deleteButton;

        public ExpireEventHolder(@NonNull View itemView) {
            super(itemView);

            expireEventImage = itemView.findViewById(R.id.expire_img);
            expireEventName = itemView.findViewById(R.id.expire_event_name);
            expireEventOrganizerName = itemView.findViewById(R.id.expire_event_organizer_name);
            expireEventDate = itemView.findViewById(R.id.expire_event_date);
            expireEventQty = itemView.findViewById(R.id.expire_event_qty);
            deleteButton = itemView.findViewById(R.id.expire_event_delete_button);

        }
    }

}
