package com.example.nebulese.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StoryDBHandler extends SQLiteOpenHelper {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int DATABASE_VERSION = 1;
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

    public StoryDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
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

        db.execSQL(CREATE_STORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        onCreate(db);
    }

    public void bookmarkStory(Story story){
        ContentValues values = new ContentValues();
        values.put(COLUMN_HASH, story.getHash());
        values.put(COLUMN_TITLE, story.getTitle());
        values.put(COLUMN_PUB_DATE, story.getPubDate().toString());
        values.put(COLUMN_AUTHOR, story.getAuthor());
        values.put(COLUMN_BODY, story.getBody());
        values.put(COLUMN_BMARKED, story.isBmarked());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_STORIES, null, values);
        db.close();
    }

    public ArrayList<Story> getBookmarkedStories(){
        ArrayList<Story> storyList = new ArrayList<>();

        String query = "SELECT * FROM "  + TABLE_STORIES + " WHERE " + COLUMN_BMARKED + " = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Story story = null;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                storyList.add(new Story(cursor.getString(2), cursor.getString(3)));
            } while(cursor.moveToNext());
        }

        return storyList;
    }

    public boolean deleteBookmarkedStory(){
        boolean result = false;


        return result;
    }
}
