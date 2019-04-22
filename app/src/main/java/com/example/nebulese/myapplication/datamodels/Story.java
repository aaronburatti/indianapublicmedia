package com.example.nebulese.myapplication.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;


/*

Pretty standard Object class that I made parcelable to be able
to put story objects in tags

*/

public class Story implements Parcelable {
    private int storyID;
    private String hash;
    private String title;
    private String imgUrl;
    private String storyURL;
    private String pubDate;
    private String author;
    private String body;
    private int bmarked;
    private int shared;
    private String shareDate;
    private String shareMethod;

    public Story(){

    }

    //temporary constructor for lab three
    public Story(int ID, String title, String imgUrl){
        this.storyID = ID;
        this.title = title;
        this.imgUrl = imgUrl;
    }

    //constructor for creating a full story from JSON
    public Story(String hash, String title, String imgUrl, String pubDate, String author, String body) {
        this.hash = hash;
        this.title = title;
        this.imgUrl = imgUrl;
        this.pubDate = pubDate;
        this.author = author;
        this.body = body;
    }

    //full constructor for bookmarked story, once JSON Data is around
    public Story(String hash, String title, String imgUrl, String pubDate, String author, String body, int bmarked) {
        this.hash = hash;
        this.title = title;
        this.imgUrl = imgUrl;
        this.pubDate = pubDate;
        this.author = author;
        this.body = body;
        this.bmarked = bmarked;
    }

    //constructor for shared stories
    public Story(String hash, String title, String storyURL, String shareDate, String shareMethod) {
        this.hash = hash;
        this.title = title;
        this.storyURL = storyURL;
        this.shareDate = shareDate;
        this.shareMethod = shareMethod;
    }

    //standard getters and setters
    public String getHash() {
        return hash;
    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
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

    public String getStoryURL() {
        return storyURL;
    }

    public void setStoryURL(String storyURL) {
        this.storyURL = storyURL;
    }

    public String getShareDate() {
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

    public void setPubDate(String pubDate) {
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

    public void setShareDate(String shareDate) {
        this.shareDate = shareDate;
    }

    public void setShareMethod(String shareMethod) {
        this.shareMethod = shareMethod;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getStoryID() {
        return storyID;
    }

    public void setStoryID(int storyID) {
        this.storyID = storyID;
    }

    public int getBmarked() {
        return bmarked;
    }

    public int getShared() {
        return shared;
    }


    protected Story(Parcel in) {
        storyID = in.readInt();
        hash = in.readString();
        title = in.readString();
        imgUrl = in.readString();
        storyURL = in.readString();
        pubDate = in.readString();
        author = in.readString();
        body = in.readString();
        bmarked = in.readInt();
        shared = in.readInt();
        shareDate = in.readString();
        shareMethod = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(storyID);
        dest.writeString(hash);
        dest.writeString(title);
        dest.writeString(imgUrl);
        dest.writeString(storyURL);
        dest.writeString(pubDate);
        dest.writeString(author);
        dest.writeString(body);
        dest.writeInt(bmarked);
        dest.writeInt(shared);
        dest.writeString(shareDate);
        dest.writeString(shareMethod);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}