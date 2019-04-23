package com.example.nebulese.myapplication.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.activities.MainActivity;
import com.example.nebulese.myapplication.datamodels.Story;
import com.example.nebulese.myapplication.datamodels.StoryDBHandler;
import com.squareup.picasso.Picasso;

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
    public ImageView deleteStoryIcon;

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
        public ImageView deleteStoryIcon;
        private Context context;

        public StoriesHolder(ViewGroup v){
            super(v);
            v.setOnClickListener(this);

            bmarkedStoryImage = (ImageView) v.findViewById(R.id.bmarkedStoryImage);
            bmarkedStoryTitle = (TextView) v.findViewById(R.id.bmarkedStoryTitle);
            final StoryDBHandler dbHandle = new StoryDBHandler(context);

//            deleteStoryIcon.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view){
//
//
//                }
//            });

            bmarkedStoryImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                   String title = (String) bmarkedStoryTitle.getText();
                   dbHandle.getBookMarkedStory(title);

                }
            });

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
        deleteStoryIcon = (ImageView) view.findViewById(R.id.storyDelete);
        return holder;
    }

    @Override
    public void onBindViewHolder(StoriesHolder holder, int index){
        //get the view at each index
        Story storyList = list.get(index);
        //set the image and text
        bmarkedStoryTitle.setText(storyList.getTitle());
        Picasso.get().load(storyList.getImgUrl()).into(bmarkedStoryImage);
    }

    //in case the list size is needed
    @Override
    public int getItemCount(){
        return list.size();
    }

}
