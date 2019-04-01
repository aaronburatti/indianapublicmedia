package com.example.nebulese.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.api.ResponseClass;
import com.example.nebulese.myapplication.api.WebLink;
import com.example.nebulese.myapplication.datamodels.Story;
import com.example.nebulese.myapplication.datamodels.StoryDBHandler;
import com.example.nebulese.myapplication.recyclerview.BmarkedStories;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //set the flag which looks for a created state and brings it to the
    //front of the stack
    private static final int flag = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
    //register the clickable components
    ImageButton leadImageButton;
    ImageButton shareImageButton;
    ImageButton leadImageButton2;
    ImageButton shareImageButton2;
    VideoView videoView;
    //register the menu items
    MenuItem action_home;
    MenuItem action_wfiu;
    MenuItem action_wtiu;
    TextView titleText;
    TextView titleText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new GetAPI(this).execute();

        //make sure everything needed is programmatically accessible
        leadImageButton = (ImageButton)findViewById(R.id.leadImage);
        shareImageButton = (ImageButton)findViewById(R.id.shareIcon);
        titleText = (TextView)findViewById(R.id.titleText);

        //these are for demonstration. I should implement a recyclerview for more
        //story objects
        leadImageButton2 = (ImageButton)findViewById(R.id.leadImage2);
        shareImageButton2 = (ImageButton)findViewById(R.id.shareIcon2);
        titleText2 = (TextView)findViewById(R.id.titleText2);


        videoView = (VideoView)findViewById(R.id.videoStory);
        action_home = (MenuItem)findViewById(R.id.action_home);
        action_wfiu = (MenuItem)findViewById(R.id.action_wfiu);
        action_wtiu = (MenuItem)findViewById(R.id.action_wtiu);

        //drawable resources


        //Android Studio Boilerplate Code for a Nav Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Android Studio Boilerplate Code for a Nav Drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //async request handler running off of the main thread
    public class GetAPI extends AsyncTask<Void, Void, ResponseClass> {
        private ProgressBar progressBar;
        private Context context;
        private String api = "https://indianapublicmedia.org/feeds/newsjson.json";

        GetAPI(Context context){this.context = context;}

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //show a progress bar as the app loads data
            progressBar = new ProgressBar(context);
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ResponseClass doInBackground(Void... voids) {
            //call the method that handles the request and passes back the data
            ResponseClass link = new WebLink().getResponse(api);
            //return the result
            return link;
        }

        @Override
        protected void onPostExecute(ResponseClass link){
            //accept the result of doInBackground
            super.onPostExecute(link);
            JSONObject jsonObject = null;
            try {
                //pull the data out of response object and put it in jsonobject
                jsonObject = new JSONObject(link.getmMessaage());
                //turn that into a json array
                JSONArray jsonArray = jsonObject.getJSONArray("stories");
                //get the arraylist ready
                ArrayList<Story> jsonStoriesList = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++){
                    //declare a new story
                    Story story = new Story();
                    //get the data out of the array and pass it to the story object
                    story.setTitle(jsonArray.getJSONObject(i).getString("title"));
                    story.setHash(jsonArray.getJSONObject(i).getString("id"));
                    story.setImgUrl(jsonArray.getJSONObject(i).getString("img"));
                    story.setPubDate(jsonArray.getJSONObject(i).getString("date"));
                    story.setBody(jsonArray.getJSONObject(i).getString("story"));
                    //put the story in the list
                    jsonStoriesList.add(story);
                }

                //display the title and image for the first two story objects
                titleText.setText(jsonStoriesList.get(0).getTitle());
                Picasso.get().load(jsonStoriesList.get(0).getImgUrl()).into(leadImageButton);
                //put the story object in a tag
                leadImageButton.setTag(jsonStoriesList.get(0));
                //this displays a second story just to prove I can
                //really, I need a recycler view to display the whole arraylist
                titleText2.setText(jsonStoriesList.get(1).getTitle());
                Picasso.get().load(jsonStoriesList.get(1).getImgUrl()).into(leadImageButton2);
                //pu the whole story object in a tag
                leadImageButton2.setTag(jsonStoriesList.get(1));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            
        }
    }

    @Override
    public void onBackPressed() {
        //Android Studio Boilerplate Code for a Nav Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Android Studio Boilerplate Code for a Nav Drawer. Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_archive) {
            //same idea as above navigation
            Intent intentArchive = new Intent(this, BmarkedStories.class);
            intentArchive.addFlags(flag);
            startActivity(intentArchive);
            return true;
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
       }
        //Android Studio Boilerplate Code for a Nav Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //have at the ready if state persistence is needed across activity
    @Override
    protected void onResume(){
        super.onResume();
    }


    //this method will be extended to bring up a specific story in the future
    public void onStoryImageClick(View view){
        //make a new intent with the story class
        Intent intent = new Intent(this, NewsStories.class);
        //get the tag containing the story object from the image clicked on
        Story story = (Story)leadImageButton.getTag();
        //name the object for later retrieval
        intent.putExtra("story", story);
        //send the activity
        startActivity(intent);
    }

    public void onStoryShareClick(View view){
        //create a new intent to send a message with the devices messaging apps
        Intent intent = new Intent(Intent.ACTION_SEND);
        //most generic type, will probably change to something more compatible
        //with html as this is for story sharing
        intent.setType("text/plain");
        //title of the sharing box
        String title = "Share Via...";
        //use the native method to create and display the chooser
        Intent chooseIntent = Intent.createChooser(intent, title);
        //do it
        startActivity(chooseIntent);
    }

    public void onBookmarkClick(View view){
        //dummy hash for now as this will be brought in from JSON
        String hash = "46hfgkld99";
        //get title textview
        TextView titleTextView = (TextView)findViewById(R.id.titleText);
        //convert titletextview's value to a string then save in a variable
        String title = titleTextView.getText().toString();
        //dummy string url as this will come from JSON eventually
        String imgUrl = "http://indianapublicmedia.org/billmurray.png";
        //dummy date as this will be gathered from JSON
        String pubDate = "06/7/19";
        //dummy author
        String author = "Hercules the Goat";
        //dummy body
        String body = "This is the latest and greatest invention from Ronco. it slices, it dices, it delouses your shoes!";
        //create the story object
        Story story = new Story(hash, title, imgUrl, pubDate, author, body);
        //initialize db instance
        StoryDBHandler dbLink = new StoryDBHandler(this);
        //put story in db
        dbLink.bookmarkStory(story);
        //close connection
        dbLink.close();
        //so that the user doesn't become confused and annoyed
        //show them that the addition was succesful
        Toast.makeText(this, "Story Bookmarked!", Toast.LENGTH_SHORT).show();
    }

}
