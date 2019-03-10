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
with a few modifications to utilize an arraylist and not am array of strings as shown in the docs
*/

public class BmarkedStoriesAdapter extends RecyclerView.Adapter<BmarkedStoriesAdapter.StoriesHolder> {
    //initialize the classes and list needed
    private LayoutInflater inflater;
    ArrayList<Story> list = new ArrayList<>();
    private View view;
    //make available an instance of the inner class
    private BmarkedStoriesAdapter.StoriesHolder holder;
    private Context context;
    public ImageView bmarkedStoryImage;
    public TextView bmarkedStoryTitle;

    public BmarkedStoriesAdapter(Context context, ArrayList<Story> list){
        //constructor that consumes the list of Story objects and the context
        //and inflates the XML into a view
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }


    public static class StoriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //inner class that provides a context for each data item
        //and makes them clickable
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
        //parse and inflate the single item XML
        view = inflater.inflate(R.layout.bmarked_story_row, parent, false);
        //in the single item view, get the title and image
        bmarkedStoryTitle = (TextView) view.findViewById(R.id.bmarkedStoryTitle);
        bmarkedStoryImage = (ImageView) view.findViewById(R.id.bmarkedStoryImage);
        //make the fields clickable and place in holder object
        holder = new StoriesHolder((ViewGroup) view);
        //make the holder accessible
        return holder;
    }

    @Override
    public void onBindViewHolder(StoriesHolder holder, int index){
        //for each index position in the story object list
        Story storyList = list.get(index);
        //get the title info from the object
        bmarkedStoryTitle.setText(storyList.getTitle());
        //still using dummy image. When using real data, this will be a URL
        bmarkedStoryImage.setImageResource(R.mipmap.storyleadicon);
    }

    //make available the list size
    @Override
    public int getItemCount(){
        return list.size();
    }


}
