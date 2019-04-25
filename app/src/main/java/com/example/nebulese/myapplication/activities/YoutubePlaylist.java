package com.example.nebulese.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.api.ResponseClass;
import com.example.nebulese.myapplication.api.WebLink;
import com.example.nebulese.myapplication.datamodels.Videos;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class YoutubePlaylist extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    //youtube variable
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubePlayer;
    private static String YOUTUBE_API_KEY = "AIzaSyCfMG8Hi7yY76ch5-PpPXkQfYppWpXgDP8";
    private static String PlayList_ID = "";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    Context context;


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
        youTubePlayer = (YouTubePlayerView) findViewById(R.id.youtube_player);
        //youTubePlayer.initialize(YOUTUBE_API_KEY, this);
        Log.i("id","" + PlayList_ID);
    }



    public class GetVids extends AsyncTask<Void, Void, ResponseClass>{
        private Context context;
        private ProgressBar progressBar;
        private String api = "https://www.googleapis.com/youtube/v3/playlists?part=contentDetails&id=PLsLvHNXs74oDjl92pCVCZNiyBXsS81V6Q&maxResults=20";

        public GetVids (Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //show a progress bar as the app loads data
//            progressBar = new ProgressBar(context);
//            progressBar.setIndeterminate(false);
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ResponseClass doInBackground(Void... voids) {
            Log.i("url", "" + api);
            ResponseClass responseClass = new WebLink().getResponse(api);
            Log.i("resp","" +responseClass);
            return responseClass;
        }

        @Override
        protected void onPostExecute(ResponseClass link){
            super.onPostExecute(link);
            JSONObject jsonObject = null;

            try{

                jsonObject = new JSONObject(link.getmMessaage());
                Log.i("json","" + jsonObject);
//                JSONArray jsonArray = jsonObject.getJSONArray();
//
//                ArrayList<Videos> jsonStoriesList = new ArrayList<>();
//                for(int i = 0; i < jsonArray.length(); i++){
//                    //declare a new story
//                    Videos video = new Videos();
//
//                    //put the story in the list
//                    //jsonStoriesList.add();
//                }

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
