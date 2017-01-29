package com.example.tigran.rssreaderapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Tigran on 29/01/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<FeedItem> feedItems;
    Context context;

    public MyAdapter(Context context, ArrayList<FeedItem> feedItems) {
        this.feedItems = feedItems;
        this.context = context;

    }

    //inflating custom_row_news_item with our thumbnails
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row_news_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    //accessing to our text title,description,date and setting text
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FeedItem current = feedItems.get(position);
        holder.title.setText(current.getTitle());
        holder.description.setText(current.getDescription());
        holder.date.setText(current.getPubDate());
        //using Picasso lib for loading thumbnail images to our imageView
        Picasso.with(context).load(current.getThumbnailURL()).into(holder.thumbnail);
        //adding animation from YoYo
        YoYo.with(Techniques.FadeIn).playOn(holder.cardView);


    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date;
        ImageView thumbnail;
        CardView cardView = (CardView) itemView.findViewById(R.id.card_view);

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_text);
            description = (TextView) itemView.findViewById(R.id.description_text);
            date = (TextView) itemView.findViewById(R.id.date_text);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumb_img);
        }
    }
}
