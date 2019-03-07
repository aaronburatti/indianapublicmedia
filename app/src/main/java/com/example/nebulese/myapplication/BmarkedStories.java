package com.example.nebulese.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class BmarkedStories extends AppCompatActivity {

    BmarkedStoriesAdapter adapter;
    StoryDBHandler dbHandler;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Story> storyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bmarked_stories);
        recyclerView = (RecyclerView) findViewById(R.id.bmarkedStories);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // make sure the layout is linear and attached to the recycler
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        storyList = dbHandler.getBookmarkedStories();
        adapter.setArray(storyList);
        recyclerView.setAdapter(adapter);
    }

}
