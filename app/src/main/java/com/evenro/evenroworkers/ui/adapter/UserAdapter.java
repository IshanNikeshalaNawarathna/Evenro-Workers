package com.evenro.evenroworkers.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.ui.model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_view_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userDetails.get(position);
        holder.userName.setText(user.getName());
        holder.userEmail.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return userDetails.size();
    }

    ArrayList<User> userDetails;

    public UserAdapter(ArrayList<User> userDetails) {
        this.userDetails = userDetails;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView userName, userEmail;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
        }
    }

}
