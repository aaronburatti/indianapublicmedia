package com.example.nebulese.myapplication.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.example.nebulese.myapplication.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;


public class YoutubePlaylist extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    //youtube variable
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerFragment youTubeFrag;
    private static String YOUTUBE_API_KEY = "";
    private static final String PlayList_ID = "PLsLvHNXs74oABQAwdocnfKJDdd1Vt6eIT";
    private static final int RECOVERY_DIALOG_REQUEST = 1;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        if(YOUTUBE_API_KEY == ""){
            YOUTUBE_API_KEY = "AIzaSyCfMG8Hi7yY76ch5-PpPXkQfYppWpXgDP8";
        } else {
            YOUTUBE_API_KEY = "";
        }

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(YOUTUBE_API_KEY, this);

    }


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
