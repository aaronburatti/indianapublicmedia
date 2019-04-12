package com.example.nebulese.myapplication.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.datamodels.Story;
import com.example.nebulese.myapplication.datamodels.StoryDBHandler;

import java.util.ArrayList;

public class BmarkedStories extends AppCompatActivity {
    //bring in the classes needed
    BmarkedStoriesAdapter adapter;
    StoryDBHandler dbHandler;
    //register the components needed
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Story> storyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bmarked_stories);
        //get the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.bmarkedStories);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // create instance of manager to control recycler view layout
        layoutManager = new LinearLayoutManager(this);
        //create instance of the db class
        dbHandler = new StoryDBHandler(this);
        // make sure the layout is linear and attached to the recycler
        recyclerView.setLayoutManager(layoutManager);
        //call the method that fetches bookmarked stories
        storyList = dbHandler.getBookmarkedStories();
        //pass the fetched stories to the adapted
        adapter = new BmarkedStoriesAdapter(this, storyList);
        //set the adapter to the recycler view
        recyclerView.setAdapter(adapter);
    }

}
