package com.example.nebulese.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*
This is directly from the android developer docs: https://developer.android.com/guide/topics/ui/layout/recyclerview#java
and this walk through implementation: https://medium.com/@harivigneshjayapalan/android-implementing-custom-recycler-view-part-i-9ce5e9af7fea
*/

public class BmarkedStoriesAdapter extends RecyclerView.Adapter<BmarkedStoriesAdapter.StoriesHolder> {
    //register all lists, views, and classes needed globally
    ArrayList<Story> list = new ArrayList<>();
    View view;
    LayoutInflater inflater;
    BmarkedStoriesAdapter.StoriesHolder holder;
    private Context context;
    public ImageView bmarkedStoryImage;
    public TextView bmarkedStoryTitle;

    public BmarkedStoriesAdapter(Context context, ArrayList<Story> list){
        //constructor consuming the context, inflater, and list
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    //inner class to process the inflated view and make everything clickable
    //in the future this will bring up the whole of the bookmarked story
    public static class StoriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LayoutInflater inflater;
        public ImageView bmarkedStoryImage;
        public TextView bmarkedStoryTitle;

        public StoriesHolder(ViewGroup v){
            super(v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){

        }

    }


    @Override
    public StoriesHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //inflate the single bookmarked story view
        view = inflater.inflate(R.layout.bmarked_story_row, parent, false);
        //pass it to the holder
        holder = new StoriesHolder((ViewGroup) view);
        //get the image and text view
        bmarkedStoryTitle = (TextView) view.findViewById(R.id.bmarkedStoryTitle);
        bmarkedStoryImage = (ImageView) view.findViewById(R.id.bmarkedStoryImage);
        return holder;
    }

    @Override
    public void onBindViewHolder(StoriesHolder holder, int index){
        //get the view at each index
        Story storyList = list.get(index);
        //set the image and text
        bmarkedStoryTitle.setText(storyList.getTitle());
        bmarkedStoryImage.setImageResource(R.mipmap.storyleadicon);
    }

    //in case the list size is needed
    @Override
    public int getItemCount(){
        return list.size();
    }

}
