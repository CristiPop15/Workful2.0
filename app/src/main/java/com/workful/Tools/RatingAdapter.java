package com.workful.Tools;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cristian.workful20.R;
import com.workful.templates.SkillLvl;

import java.util.ArrayList;

/**
 * Created by Cristian on 6/19/2017.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> {

    private ArrayList<SkillLvl> skills;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public RatingBar rating;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.skill_name);
            rating = (RatingBar)view.findViewById(R.id.rating);

        }
    }

    public RatingAdapter(ArrayList<SkillLvl> skills) {
        this.skills = skills;
    }

    @Override
    public RatingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_row_rating, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RatingAdapter.MyViewHolder holder, int position) {
        SkillLvl skill = skills.get(position);
        Log.e("MainActivity", skill.toString());
        holder.name.setText(skill.getName());
        holder.rating.setRating(skill.getLevel());
    }

    @Override
    public int getItemCount() {

        return skills.size();
    }
}
