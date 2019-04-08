package com.example.nebulese.myapplication.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.datamodels.Story;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsStoriesAdapter extends RecyclerView.Adapter<NewsStoriesAdapter.StoriesHolder> {
    //register all lists, views, and classes needed globally
    ArrayList<Story> list = new ArrayList<>();
    View view;
    LayoutInflater inflater;
    NewsStoriesAdapter.StoriesHolder holder;
    private Context context;
    public ImageView leadImageButton;
    public TextView titleText;


    public NewsStoriesAdapter(Context context, ArrayList<Story> list){
        //constructor consuming the context, inflater, and list
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    //inner class to process the inflated view and make everything clickable
    //in the future this will bring up the whole of the bookmarked story
    public static class StoriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LayoutInflater inflater;
        public ImageButton leadImage;
        public TextView titleText;

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
        view = inflater.inflate(R.layout.news_story_card, parent, false);
        //pass it to the holder
        holder = new StoriesHolder((ViewGroup) view);
        //get the image and text view
        leadImageButton = (ImageButton) view.findViewById(R.id.leadImage);
        titleText = (TextView) view.findViewById(R.id.titleText);
        return holder;
    }

    @Override
    public void onBindViewHolder(StoriesHolder holder, int index){
        //get the view at each index
        Story storyList = list.get(index);
        //set the image and text
        titleText.setText(storyList.getTitle());
        Picasso.get().load(list.get(index).getImgUrl()).into(leadImageButton);    }

    //in case the list size is needed
    @Override
    public int getItemCount(){
        return list.size();
    }

}
