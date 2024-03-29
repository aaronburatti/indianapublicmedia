package com.example.nebulese.myapplication.datamodels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StoryDBHandler extends SQLiteOpenHelper {
    //SQLite doesn't have a datetime data formate, so make a formatted string
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //register db variables
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "StoriesDB.db";
    private static final String TABLE_STORIES = "Stories";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_HASH = "Hash";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_IMG_URL = "Img_Title";
    private static final String COLUMN_PUB_DATE = "Pub_Date";
    private static final String COLUMN_AUTHOR = "Author";
    private static final String COLUMN_BODY = "Body";
    private static final String COLUMN_SHARED = "Shared";
    private static final String COLUMN_BMARKED = "Bmarked";
    private static final String COLUMN_SHARE_DATE = "Share_Date";
    private static final String COLUMN_SHARE_METHOD = "Share_Method";

    //constructor consuming the context in which it finds itself
    public StoryDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //create table query
        String CREATE_STORIES_TABLE = "CREATE TABLE " +
                TABLE_STORIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HASH + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_IMG_URL + " TEXT, " +
                COLUMN_PUB_DATE + " DATETIME, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_BODY + " TEXT, " +
                COLUMN_SHARED + " INTEGER, " +
                COLUMN_BMARKED + " INTEGER, " +
                COLUMN_SHARE_DATE + " DATETIME, " +
                COLUMN_SHARE_METHOD + " TEXT)";

        //send the query
        db.execSQL(CREATE_STORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j){
        //kill the table and start over if version number increases
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        onCreate(db);
    }

    public void bookmarkStory(Story story){
        //new instance of db values object
        ContentValues values = new ContentValues();
        //places values in the object
        values.put(COLUMN_HASH, story.getHash());
        values.put(COLUMN_TITLE, story.getTitle());
        values.put(COLUMN_IMG_URL, story.getImgUrl());
        values.put(COLUMN_PUB_DATE, story.getPubDate().toString());
        values.put(COLUMN_AUTHOR, story.getAuthor());
        values.put(COLUMN_BODY, story.getBody());
        values.put(COLUMN_BMARKED, story.isBmarked());

        //new instance of db object
        SQLiteDatabase db = this.getWritableDatabase();
        //place values into the table
        db.insert(TABLE_STORIES, null, values);
        db.close();
    }

    public ArrayList<Story> getBookmarkedStories(){
        //new story list
        ArrayList<Story> storyList = new ArrayList<>();

        //create query string looking for stories with a special mark
        String query = "SELECT * FROM "  + TABLE_STORIES + " WHERE " + COLUMN_BMARKED + " = 1";
        //new db object
        SQLiteDatabase db = this.getWritableDatabase();
        //send the query and hold the results in a cursor object
        Cursor cursor = db.rawQuery(query, null);

        //if there are results from the query
        if(cursor.moveToFirst()){
            //create at least one story object
            do{
                Story story = new Story(cursor.getInt(0), cursor.getString(2), cursor.getString(3));
                storyList.add(story);
            } while(cursor.moveToNext());
        }
        //return the story list for later use
        return storyList;
    }

    public Story getBookMarkedStory(String title){
        //query to match title of bookmarked story to one in DB
        String query = "SELECT * FROM "  + TABLE_STORIES + " WHERE " + COLUMN_TITLE   + " = \"" + title + "\"";
        //fetch db
        SQLiteDatabase db = this.getWritableDatabase();
        //send the query
        Cursor cursor = db.rawQuery(query, null);
        //create the story from DB data
        if(cursor.moveToFirst()){
            Story story = new Story(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            return story;
        }
        return null;
    }

}
