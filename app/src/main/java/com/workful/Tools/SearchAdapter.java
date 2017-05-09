package com.workful.Tools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cristian.workful20.R;
import com.workful.Tools.HttpCommunication.ImageDownloader;
import com.workful.templates.SearchResult;

import java.util.List;


/**
 * Created by Cristian on 11/27/2016.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context mContext;
    private List<SearchResult> personList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, title, city, category;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            title = (TextView) view.findViewById(R.id.title);
            city = (TextView) view.findViewById(R.id.city);
            category = (TextView) view.findViewById(R.id.category);
            img = (ImageView)view.findViewById(R.id.imgSearch);

        }
    }

    public SearchAdapter(Context mContext, List<SearchResult> personList) {
        this.mContext = mContext;
        this.personList = personList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        SearchResult s = personList.get(position);
        holder.name.setText(s.getName()+" "+s.getSurname());
        holder.title.setText(s.getTitle());
        holder.city.setText(s.getCity());
        holder.category.setText(s.getCategory());

        new ImageDownloader(holder.img, s.getEmail()).execute();

    }


    @Override
    public int getItemCount() {
        return personList.size();
    }
}
