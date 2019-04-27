package com.example.nebulese.myapplication.recyclerview;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.datamodels.Videos;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.VideosHolder> {
    //register all lists, views, and classes needed globally
    List<Videos> list = new ArrayList<>();

    //constructors
    public YouTubeAdapter(){

    }

    public YouTubeAdapter(List<Videos> list){
        this.list = list;
    }


    public static class VideosHolder extends RecyclerView.ViewHolder{
        WebView youtubeWeb;


        //initialize the webview for each recycled view
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
        //inflate the single youtube vid view
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.single_youtube_vid, parent, false);
        //pass it to the holder
        return new VideosHolder(view);
    }

    @Override
    public void onBindViewHolder(VideosHolder holder, int index){
        //set the url from the list in the webview
        holder.youtubeWeb.loadData( list.get(index).getVideoUrl(), "text/html" , "utf-8" );

    }

    //in case the list size is needed
    @Override
    public int getItemCount(){
        return list.size();
    }

}

