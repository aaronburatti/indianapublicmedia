package com.example.nebulese.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.datamodels.Story;
import com.example.nebulese.myapplication.datamodels.StoryDBHandler;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.w3c.dom.Text;

import java.net.URI;


public class NewsStories extends AppCompatActivity {
    //set the flag which looks for a created state and brings it to the
    //front of the stack
    private static final int flag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
    //register the menu items
    MenuItem action_home;
    MenuItem action_wfiu;
    MenuItem action_wtiu;
    TextView title;
    TextView date;
    TextView body;
    TextView author;
    ImageView image;
    Story story;
    Uri TwitImg;
    String shareURL;
    String hash, storyTitle, imgUrl, pubDate, storyAuthor, storyText;
    private static final String TWIT_PUBLIC_KEY = "63GE7RHkdFVnEwKXG62sN1VPz";
    private static final String TWIT_PRIVATE_KEY = "13tPBDBPCyEFscFvucBWyUzAVq5Sg9o6kTuI9hDBceM98E9Nht";

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

        //get the story object out of the tag
        Story story = getIntent().getExtras().getParcelable("story");

        //set the data from the object to the view components
        title = (TextView)findViewById(R.id.storyTitle);
        title.setText(story.getTitle());
        date = (TextView)findViewById(R.id.storyDate);
        date.setText(story.getPubDate());
        body = (TextView)findViewById(R.id.storyBody);
        author = (TextView)findViewById(R.id.storyAuthor);
        author.setText(story.getAuthor());
        //recode the html in the story that had to be decode to pass json validation
        String storyBody = story.getBody();
        storyBody = storyBody.replace("``", "--");
        storyBody = storyBody.replace("^","-");
        storyBody = storyBody.replace("\\\\","\\");
        storyBody = storyBody.replace("*","<");
        storyBody = storyBody.replace("~",">");
        storyBody = storyBody.replace("[newline]","");
        storyBody = storyBody.replace("&amp;","");
        storyBody = storyBody.replace("&","");
        storyBody = storyBody.replace("#160;"," ");
        storyBody = storyBody.replace("#8217;","'");
        storyBody = storyBody.replace("#8220;","\"");
        storyBody = storyBody.replace("#8221;","-");
        //Spanned storyText = Html.fromHtml(storyBody);
        body.setText(Html.fromHtml(storyBody));
        image = (ImageView)findViewById(R.id.storyViewLeadImage);
        Picasso.get().load(story.getImgUrl()).into(image);
        //set up strings for placement into the DB
        hash = story.getHash();
        storyTitle = story.getTitle();
        imgUrl = story.getImgUrl();
        pubDate = story.getPubDate();
        storyAuthor = story.getAuthor();
        storyText = story.getBody();
        shareURL = story.getStoryURL();




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
        Story newsStory = new Story(hash, storyTitle, imgUrl, pubDate, storyAuthor, storyText);
        //new db object
        StoryDBHandler dbLink = new StoryDBHandler(this);
        //place the story into the db
        dbLink.bookmarkStory(newsStory);
        //alert the user
        Toast.makeText(this,"Story Bookmarked", Toast.LENGTH_SHORT).show();
    }

    public void onShareIconClick(View view){
        //create a new intent to send a message with the devices messaging apps
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //title of the sharing box
        String title = "Share Via...";
        //handle subject instances
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        //load the share text
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareURL);

        //use the native method to create and display the chooser
        Intent chooseIntent = Intent.createChooser(intent, title);
        //do it
        startActivity(chooseIntent);
    }

    public void twitIconClick(View view){
        //load the story pic into the tweet intent
        Uri TwitImg = Uri.parse(imgUrl);
        //twitter api prebuilt functions
        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text(shareURL)
                .image(TwitImg);
        //make the tweet
        builder.show();
    }
}
