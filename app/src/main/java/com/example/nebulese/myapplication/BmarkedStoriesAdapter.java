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
    private LayoutInflater inflater;
    ArrayList<Story> list = new ArrayList<>();
    View view;
    BmarkedStoriesAdapter.StoriesHolder holder;
    private Context context;
    public ImageView bmarkedStoryImage;
    public TextView bmarkedStoryTitle;

    public BmarkedStoriesAdapter(Context context, ArrayList<Story> list){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

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
        view = inflater.inflate(R.layout.bmarked_story_row, parent, false);
        holder = new StoriesHolder((ViewGroup) view);
        bmarkedStoryTitle = (TextView) view.findViewById(R.id.bmarkedStoryTitle);
        bmarkedStoryImage = (ImageView) view.findViewById(R.id.bmarkedStoryImage);
        return holder;
    }

    @Override
    public void onBindViewHolder(StoriesHolder holder, int index){
        Story storyList = list.get(index);
        Log.i("adapter", "title = " + storyList.getTitle());
        bmarkedStoryTitle.setText(storyList.getTitle());
        bmarkedStoryImage.setImageResource(R.mipmap.storyleadicon);
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    public void setArray(ArrayList<Story> list){
        this.list = list;
        notifyItemRangeChanged(0, list.size());
    }
}
