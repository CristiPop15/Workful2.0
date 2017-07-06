package com.workful.Tools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cristian.workful20.R;
import com.squareup.picasso.Picasso;
import com.workful.templates.Comment;
import com.workful.templates.SkillLvl;
import com.workful.templates.Url;

import java.util.ArrayList;

/**
 * Created by Cristian on 7/4/2017.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private ArrayList<Comment> comments;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public RatingBar rating;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            rating = (RatingBar)view.findViewById(R.id.rating);
            image = (ImageView)view.findViewById(R.id.imageView_comment);

        }
    }

    public CommentAdapter(ArrayList<Comment> comments, Context mContext) {
        this.comments = comments;
        this.mContext = mContext;
    }

    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_comment_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.MyViewHolder holder, int position) {
        Comment comm = comments.get(position);
        Log.e("MainActivity", comm.toString());
        holder.name.setText(comm.getText());
        holder.rating.setRating(comm.getRating());
        Picasso.with(mContext).load(Url.image_url(comm.getAccount_name())).into(holder.image);

    }


    @Override
    public int getItemCount() {

        return comments.size();
    }
}
