package com.example.nebulese.myapplication.recyclerview;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.datamodels.Videos;
import com.google.android.youtube.player.YouTubePlayer;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.VideosHolder> {
    //register all lists, views, and classes needed globally
    List<Videos> list = new ArrayList<>();
    LayoutInflater inflater;
    YouTubeAdapter.VideosHolder holder;
    private Context context;
    private CardView cardView;
    private YouTubePlayer youTubePlayer;
    private Layout vidLayout;



    public YouTubeAdapter(){

    }

    public YouTubeAdapter(List<Videos> list){
        this.list = list;
    }

    //inner class to process the inflated view and make everything clickable
    //in the future this will bring up the whole of the bookmarked story
    public static class VideosHolder extends RecyclerView.ViewHolder{
        WebView youtubeWeb;



        public VideosHolder(View v){
            super(v);

            youtubeWeb = (WebView) itemView.findViewById(R.id.youtubeWeb);

            youtubeWeb.getSettings().setJavaScriptEnabled(true);
            youtubeWeb.setWebChromeClient(new WebChromeClient() {

            } );



        }


    }


    @Override
    public VideosHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //inflate the single story view
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.single_youtube_vid, parent, false);
        //pass it to the holder

        return new VideosHolder(view);
    }

    @Override
    public void onBindViewHolder(VideosHolder holder, int index){
        holder.youtubeWeb.loadData( list.get(index).getVideoUrl(), "text/html" , "utf-8" );

    }

    //in case the list size is needed
    @Override
    public int getItemCount(){
        return list.size();
    }

}

