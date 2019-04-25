package com.example.nebulese.myapplication.recyclerview;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.datamodels.Videos;
import com.google.android.youtube.player.YouTubePlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.VideosHolder> {
    //register all lists, views, and classes needed globally
    ArrayList<Videos> list = new ArrayList<>();
    LayoutInflater inflater;
    YouTubeAdapter.VideosHolder holder;
    private Context context;
    private CardView cardView;
    private YouTubePlayer youTubePlayer;




    public YouTubeAdapter(Context context, ArrayList<Videos> list){
        //constructor consuming the context, inflater, and list
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    //inner class to process the inflated view and make everything clickable
    //in the future this will bring up the whole of the bookmarked story
    public static class VideosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //register all components that will be displayed again and again
        private LayoutInflater inflater;
        private Context context;
        private CardView cardView;
        private YouTubePlayer youTubePlayer;


        public VideosHolder(View v, Context context){
            super(v);
            v.setOnClickListener(this);
            this.context = context;
            cardView = (CardView) v.findViewById(R.id.vidCard);
            youTubePlayer = (YouTubePlayer) v.findViewById(R.id.youtube_player);

        }

        @Override
        public void onClick(View view){
        }

    }


    @Override
    public VideosHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //inflate the single story view
        View view = inflater.inflate(R.layout.single_youtube_vid, parent, false);
        //pass it to the holder
        holder = new YouTubeAdapter.VideosHolder(view, parent.getContext());
        //get the image and text view

        return holder;
    }

    @Override
    public void onBindViewHolder(VideosHolder holder, int index){
        //get the view at each index
        Videos vidList = list.get(index);
        //set the image and text
        //holder.titleText.setText(vidList.getTitle());
        //set items from the listview
//        holder.leadImageButton.setTag(vidList);
//        holder.vidCard.setTag(vidList);
    }

    //in case the list size is needed
    @Override
    public int getItemCount(){
        return list.size();
    }

}

