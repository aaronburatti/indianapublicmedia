package com.example.nebulese.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/*
This is directly from the android developer docs: https://developer.android.com/guide/topics/ui/layout/recyclerview#java
with a few modifications to utilize an arraylist and not am array of strings as shown in the docs
*/


public class BmarkedStories extends AppCompatActivity {
    //instantiate the classes and list needed
    BmarkedStoriesAdapter adapter;
    StoryDBHandler dbHandler;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Story> storyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bmarked_stories);
        //get the view to be recycled for each item
        recyclerView = (RecyclerView) findViewById(R.id.bmarkedStories);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // make sure the layout is linear and attached to the recycler
        layoutManager = new LinearLayoutManager(this);
        //db object instance
        dbHandler = new StoryDBHandler(this);
        //attach the layout manager to the reused view
        recyclerView.setLayoutManager(layoutManager);
        //get all bookmarked stories and add them to the list
        storyList = dbHandler.getBookmarkedStories();
        //put the list in the adapter
        adapter = new BmarkedStoriesAdapter(this, storyList);
        //attach the adapter to the recycler view
        recyclerView.setAdapter(adapter);
    }

    public void onDeleteBmarkedStoryClick(View view){
        //get recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.bmarkedStories);
        //find the recyclerview item position
        int itemPosition = recyclerView.getChildLayoutPosition(view);
        //get the story id of that item
        int storyID = storyList.get(itemPosition).getStoryID();
        //open the db
        dbHandler = new StoryDBHandler(this);
        //delete that bad boy
        dbHandler.deleteBookmarkedStory(storyID);
        //close the db connection
        dbHandler.close();
        //delete item from the storylist
        storyList.remove(itemPosition);
    }

}
