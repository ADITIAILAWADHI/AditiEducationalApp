package com.ADITIAILAWADHI.aditieducationalapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ADITIAILAWADHI.aditieducationalapp.R;
import com.ADITIAILAWADHI.aditieducationalapp.model.User;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<User> users;

    public ScoreAdapter(ArrayList<User> users) {
        this.users = users;
    }

    // create new views with R.layout
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ScoreViewHolder(view);
    }

    // Replace the content of an existing view with new data
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ScoreViewHolder scoreViewHolder = (ScoreViewHolder) holder;
        User user = users.get(position);
        scoreViewHolder.usernameView.setText(user.getUsername());
        scoreViewHolder.dateView.setText(user.getDate());
        scoreViewHolder.levelView.setText(user.getLevel());
        scoreViewHolder.scoreView.setText(user.getScore());
        scoreViewHolder.durationView.setText(user.getDuration());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    //Based on row_item, define data structure of the ViewHolder
    class ScoreViewHolder extends RecyclerView.ViewHolder{

        TextView usernameView;
        TextView levelView;
        TextView scoreView;
        TextView durationView;
        TextView dateView;

//        provide a reference for the views needed to display items in users

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameView = itemView.findViewById(R.id.usernameView);
            levelView = itemView.findViewById(R.id.levelView);
            scoreView = itemView.findViewById(R.id.scoreView);
            durationView = itemView.findViewById(R.id.durationView);
            dateView = itemView.findViewById(R.id.dateView);
        }
    }
}
