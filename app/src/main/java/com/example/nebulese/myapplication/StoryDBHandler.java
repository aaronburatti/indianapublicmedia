package com.example.nebulese.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StoryDBHandler extends SQLiteOpenHelper {
    //SQLite has no native datetime support so this transforms dates to strings
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //set all variables needed
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StoriesDB.db";
    private static final String TABLE_STORIES = "Stories";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_HASH = "Hash";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_IMG_URL = "Img_URL";
    private static final String COLUMN_PUB_DATE = "Pub_Date";
    private static final String COLUMN_AUTHOR = "Author";
    private static final String COLUMN_BODY = "Body";
    private static final String COLUMN_SHARED = "Shared";
    private static final String COLUMN_BMARKED = "Bmarked";
    private static final String COLUMN_SHARE_DATE = "Share_Date";
    private static final String COLUMN_SHARE_METHOD = "Share_Method";


    public StoryDBHandler(Context context){
        //constructor for db use in class
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //create query string
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
        //if version increases, kill the db and make a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        onCreate(db);
    }

    public void bookmarkStory(Story story){
        //initialize db value object
        ContentValues values = new ContentValues();
        //place necessary values in object
        values.put(COLUMN_HASH, story.getHash());
        values.put(COLUMN_TITLE, story.getTitle());
        values.put(COLUMN_PUB_DATE, story.getPubDate().toString());
        values.put(COLUMN_AUTHOR, story.getAuthor());
        values.put(COLUMN_BODY, story.getBody());
        values.put(COLUMN_BMARKED, story.isBmarked());
        //get the db as a writable object
        SQLiteDatabase db = this.getWritableDatabase();
        //create the row
        db.insert(TABLE_STORIES, null, values);
        //close the connection
        db.close();
    }

    public ArrayList<Story> getBookmarkedStories(){
        //initialize the arraylist of story objects
        ArrayList<Story> storyList = new ArrayList<>();
        //build the query
        String query = "SELECT * FROM "  + TABLE_STORIES + " WHERE " + COLUMN_BMARKED + " = 1";
        //initialize a writable db object
        SQLiteDatabase db = this.getWritableDatabase();
        //initialize cursor, and execute the query
        Cursor cursor = db.rawQuery(query, null);
        //if the query returns true
        if(cursor.moveToFirst()){
            do{
                //loop through all returned rows, create a story object, and place in the list
                Story story = new Story(cursor.getInt(0),cursor.getString(2), cursor.getString(3));
                storyList.add(story);
                //this will execute at least once and until there are no more rows returned
            } while(cursor.moveToNext());
        }

        return storyList;
    }

    public void updateBookmarkedStory(){

    }

    public boolean deleteBookmarkedStory(int storyID){
        boolean result = false;
        //build the query
        String query = "SELECT * FROM " + TABLE_STORIES + " WHERE " + COLUMN_ID + " =\"" + storyID + "\"";
        //create instance of the db
        SQLiteDatabase db = this.getWritableDatabase();
        //create cursor and store the query boolean result
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            //create empty var to represent the entered storyID
            int tmpID = cursor.getInt(0);

            db.delete(TABLE_STORIES, COLUMN_ID + " = ?", new String[]{String.valueOf(tmpID)});
            cursor.close();
            result = true;
        }

        return result;
    }
}
