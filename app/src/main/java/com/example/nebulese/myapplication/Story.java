package com.example.nebulese.myapplication;

import java.util.Date;

public class Story {
    private String hash;
    private String title;
    private String imgUrl;
    private Date pubDate;
    private String author;
    private String body;
    private int bmarked;
    private int shared;
    private Date shareDate;
    private String shareMethod;

    public Story(String title, String imgUrl){

    }

    public Story(String hash, String title, String imgUrl, Date pubDate, String author, String body) {
        this.hash = hash;
        this.title = title;
        this.imgUrl = imgUrl;
        this.pubDate = pubDate;
        this.author = author;
        this.body = body;
    }

    public Story(String hash, String title, String imgUrl, Date pubDate, String author, String body, int bmarked) {
        this.hash = hash;
        this.title = title;
        this.imgUrl = imgUrl;
        this.pubDate = pubDate;
        this.author = author;
        this.body = body;
        this.bmarked = bmarked;
    }

    public Story(String hash, String title, int shared, Date shareDate, String shareMethod) {
        this.hash = hash;
        this.title = title;
        this.shared = shared;
        this.shareDate = shareDate;
        this.shareMethod = shareMethod;
    }

    public String getHash() {
        return hash;
    }

    public String getTitle() {
        return title;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public int isBmarked() {
        bmarked = 1;
        return bmarked;
    }

    public int isShared() {
        shared = 1;
        return shared;
    }

    public Date getShareDate() {
        return shareDate;
    }

    public String getShareMethod() {
        return shareMethod;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setBmarked(int bmarked) {
        this.bmarked = 1;
    }

    public void setShared(int shared) {
        this.shared = 1;
    }

    public void setShareDate(Date shareDate) {
        this.shareDate = shareDate;
    }

    public void setShareMethod(String shareMethod) {
        this.shareMethod = shareMethod;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    
}
