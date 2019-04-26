package com.example.nebulese.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.api.ResponseClass;
import com.example.nebulese.myapplication.api.WebLink;
import com.example.nebulese.myapplication.datamodels.Videos;
import com.example.nebulese.myapplication.recyclerview.YouTubeAdapter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;


public class YoutubePlaylist extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    //youtube variable
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubePlayer;
    private static String YOUTUBE_API_KEY = "AIzaSyCfMG8Hi7yY76ch5-PpPXkQfYppWpXgDP8";
    private static String PlayList_ID;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    Context context;
    RecyclerView recyclerView;
    private String api;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_playlist);

        new GetVids(context).execute();
        //get the bundle and parse the identifying infor
        Bundle bun = getIntent().getExtras();
        //look for the index and get the appropriate playlist

        if(bun.getInt("index") == 1) {
            PlayList_ID = bun.getString("incard");

        } else {
            PlayList_ID = bun.getString("indroid");

        }
        //initialize the youtube view and play the playlist

        //youTubePlayer.initialize(YOUTUBE_API_KEY, this);

        recyclerView = (RecyclerView)findViewById(R.id.youtubeRecycler);
        PlayList_ID = "&playlistId="+ PlayList_ID;
        api = "https://www.googleapis.com/youtube/v3/playlistItems?part=contentDetails&maxResults=20&key="+YOUTUBE_API_KEY+PlayList_ID;
    }






    public class GetVids extends AsyncTask<Void, Void, ResponseClass>{
        private Context context;
        private ProgressBar progressBar;


        public GetVids (Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
//            show a progress bar as the app loads data
//            progressBar = new ProgressBar(context);
//            progressBar.setIndeterminate(false);
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ResponseClass doInBackground(Void... voids) {
            ResponseClass responseClass = new WebLink().getResponse(api);
            return responseClass;
        }

        @Override
        protected void onPostExecute(ResponseClass link){
            super.onPostExecute(link);
            JSONObject jsonObject = null;
            Vector<Videos> videos = new Vector<Videos>();
            try{
                jsonObject = new JSONObject(link.getmMessaage());

                JSONArray jsonArray = jsonObject.getJSONArray("items");
                ArrayList<Videos> jsonVideosList = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++){

                    Videos video = new Videos();
                    String j = jsonArray.getJSONObject(i).getString("contentDetails");
                    String a = j.substring(12,23);

                    videos.add( new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+a+"\" frameborder=\"0\" allowfullscreen></iframe>") );

                }
                YouTubeAdapter youTubeAdapter = new YouTubeAdapter(videos);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager( new LinearLayoutManager(YoutubePlaylist.this));
                recyclerView.setAdapter(youTubeAdapter);

            }catch (JSONException e){

            }
        }
    }

    /*
    these are all boilerplate youtube methods to run the playlist
     */
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failure to Initialize!", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        /** Start buffering **/
        if (!wasRestored) {
            player.cuePlaylist(PlayList_ID);
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
        }
        @Override
        public void onVideoStarted() {
        }
    };
}
