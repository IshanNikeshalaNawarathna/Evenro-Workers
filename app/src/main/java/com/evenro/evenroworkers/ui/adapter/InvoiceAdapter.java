package com.evenro.evenroworkers.ui.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.ui.model.InvoiceData;

import java.util.ArrayList;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    class InvoiceViewHolder extends RecyclerView.ViewHolder {

        TextView event_name;
        TextView event_dates;
        TextView event_times;
        TextView event_prices;
        TextView event_qtys;
        ImageView event_images;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_dates = itemView.findViewById(R.id.event_dates);
            event_times = itemView.findViewById(R.id.event_times);
            event_prices = itemView.findViewById(R.id.event_prices);
            event_qtys = itemView.findViewById(R.id.event_qtys);
            event_images = itemView.findViewById(R.id.event_images);
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
    public void onBindViewHolder(@NonNull InvoiceAdapter.InvoiceViewHolder  holder, int position) {
        InvoiceData data = invoiceData.get(position);
        holder.event_name.setText(data.getEvent_name());
        holder.event_dates.setText(data.getEvent_date());
        holder.event_times.setText(data.getEvent_time());
        holder.event_prices.setText(data.getEvent_price());
        holder.event_qtys.setText(data.getEvent_qty());
        Glide.with(holder.event_images.getContext())
                .load(Uri.parse(data.getImageUri()))
                .into(holder.event_images);
    }

    @Override
    public int getItemCount() {
        return invoiceData.size();
    }


}


