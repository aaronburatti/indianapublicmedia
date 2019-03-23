package com.example.nebulese.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.datamodels.Story;
import com.example.nebulese.myapplication.datamodels.StoryDBHandler;

import java.text.SimpleDateFormat;

public class NewsStories extends AppCompatActivity {
    //set the flag which looks for a created state and brings it to the
    //front of the stack
    private static final int flag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
    //register the menu items
    MenuItem action_home;
    MenuItem action_wfiu;
    MenuItem action_wtiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //make sure these gui components are available when activity starts
        action_home = (MenuItem)findViewById(R.id.action_home);
        action_wfiu = (MenuItem)findViewById(R.id.action_wfiu);
        action_wtiu = (MenuItem)findViewById(R.id.action_wtiu);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This switch statement takes the ID of the menu item passed into the function
        //as the criterion by which it uses to correlate the click with an activity to be called
        switch(item.getItemId()){
            //if home menu item was chosen
            case R.id.action_home:
                //create new intent specific to the Main Activity
                Intent intentHome = new Intent(this, MainActivity.class);
                //add the flag
                intentHome.addFlags(flag);
                //pass the intent activity method
                startActivity(intentHome);
                //satisfy boolean switch statement, exit the case
                return true;
            //if home wfiu item was chosen
            case R.id.action_wfiu:
                //create new intent specific to the Radio Activity
                Intent intentWFIU = new Intent(this, Radio.class);
                //add the flag
                intentWFIU.addFlags(flag);
                //pass the intent activity method
                startActivity(intentWFIU);
                //satisfy boolean switch statement, exit the case
                return true;
            //if home wtiu item was chosen
            case R.id.action_wtiu:
                //create new intent specific to the Television Activity
                Intent intentWTIU = new Intent(this, Television.class);
                //add the flag
                intentWTIU.addFlags(flag);
                //pass the intent activity method
                startActivity(intentWTIU);
                //satisfy boolean switch statement, exit the case
                return true;
            default:
                return true;

        }

    }

    public void onBookmarkClick(View view){
        //dummy hash for now as this will be brought in from JSON
        String hash = "46hfgkld99";
        //get title textview
        TextView titleTextView = (TextView)findViewById(R.id.storyViewTitle);
        //convert titletextview's value to a string then save in a variable
        String title = titleTextView.getText().toString();
        //dummy string url as this will come from JSON eventually
        String imgUrl = "http://indianapublicmedia.org/billmurray.png";
        //dummy date as this will be gathered from JSON
        SimpleDateFormat today = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat pubDate = today;
        //dummy author
        String author = "Hercules the Goat";
        //dummy body
        String body = "This is the latest and greatest invention from Ronco. it slices, it dices, it delouses your shoes!";
        //create the object with above data
        Story story = new Story(hash, title, imgUrl, pubDate, author, body);
        //new db object
        StoryDBHandler dbLink = new StoryDBHandler(this);
        //place the story into the db
        dbLink.bookmarkStory(story);
        //alert the user
        Toast.makeText(this,"Story Bookmarked", Toast.LENGTH_SHORT).show();
    }

}
