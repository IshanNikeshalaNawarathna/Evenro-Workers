package com.evenro.evenroworkers.ui.adapter;


import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.ui.model.InvoiceData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    class InvoiceViewHolder extends RecyclerView.ViewHolder {

        TextView event_name;
        TextView event_dates;
        TextView event_times;
        TextView event_prices;
        TextView event_qtys;
        ImageView event_img;
        ImageView delete_button;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_dates = itemView.findViewById(R.id.event_dates);
            event_times = itemView.findViewById(R.id.event_times);
            event_prices = itemView.findViewById(R.id.event_prices);
            event_qtys = itemView.findViewById(R.id.event_qtys);
            event_img = itemView.findViewById(R.id.event_img);
            delete_button = itemView.findViewById(R.id.expire_event_delete_button);
        }
    }

    ArrayList<InvoiceData> invoiceData;

    public InvoiceAdapter(ArrayList<InvoiceData> invoiceData) {
        this.invoiceData = invoiceData;
    }

    @NonNull
    @Override
    public InvoiceAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.invoice_item, parent, false);
        InvoiceViewHolder holder = new InvoiceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceAdapter.InvoiceViewHolder holder, int position) {
        InvoiceData data = invoiceData.get(position);
        holder.event_name.setText(data.getEvent_name());
        holder.event_dates.setText(data.getEvent_date());
        holder.event_times.setText(data.getEvent_time());
        holder.event_prices.setText(data.getEvent_price());
        holder.event_qtys.setText(data.getEvent_qty());
        Glide.with(holder.event_img.getContext())
                .load(Uri.parse(data.getImageUri()))
                .into(holder.event_img);
        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = data.getId();
                Log.i("EVENT ID", id);
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("invoice").document(id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(v.getContext(), "Delete Success", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(), "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }

    @Override
    public int getItemCount() {
        return invoiceData.size();
    }


}


