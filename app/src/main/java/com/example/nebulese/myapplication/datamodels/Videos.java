package com.example.nebulese.myapplication.datamodels;

public class Videos {
    private String title;
    private String videoUrl;
    private String videoId;

    public Videos(){

    }
    public Videos(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
