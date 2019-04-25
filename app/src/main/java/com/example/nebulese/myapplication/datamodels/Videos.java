package com.example.nebulese.myapplication.datamodels;

public class Videos {
    private String title;
    private String videoUrl;
    private String videoId;

    public Videos(){

    }
    public Videos(String title, String videoUrl, String videoId) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
