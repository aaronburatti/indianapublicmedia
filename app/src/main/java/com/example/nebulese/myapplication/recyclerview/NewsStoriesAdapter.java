package com.example.nebulese.myapplication.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.activities.MainActivity;
import com.example.nebulese.myapplication.activities.NewsStories;
import com.example.nebulese.myapplication.datamodels.Story;
import com.example.nebulese.myapplication.datamodels.StoryDBHandler;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.ArrayList;

//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;


public class NewsStoriesAdapter extends RecyclerView.Adapter<NewsStoriesAdapter.StoriesHolder> {
    //register all lists, views, and classes needed globally
    ArrayList<Story> list = new ArrayList<>();
    LayoutInflater inflater;
    NewsStoriesAdapter.StoriesHolder holder;
    private Context context;
    public ImageView leadImageButton;
    public TextView titleText;
    public ImageView fbicon;
    private static final String TWIT_PUBLIC_KEY = "63GE7RHkdFVnEwKXG62sN1VPz";
    private static final String TWIT_PRIVATE_KEY = "13tPBDBPCyEFscFvucBWyUzAVq5Sg9o6kTuI9hDBceM98E9Nht";


    public NewsStoriesAdapter(Context context, ArrayList<Story> list){
        //constructor consuming the context, inflater, and list
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    //inner class to process the inflated view and make everything clickable
    //in the future this will bring up the whole of the bookmarked story
    public static class StoriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //register all components that will be displayed again and again
        private LayoutInflater inflater;
        public ImageButton leadImageButton;
        public TextView titleText;
        private Context context;
        private CardView storyCard;
        private ImageView twitIcon;
        ImageView shareImageButton;
        ImageView fbicon;
        ImageView bookMarkIcon;


        public StoriesHolder(View v, Context context){
            super(v);
            v.setOnClickListener(this);
            this.context = context;
            //register the card components that will be clickable

            storyCard = (CardView)v.findViewById(R.id.storyCard);
            leadImageButton = (ImageButton) v.findViewById(R.id.leadImage);
            titleText = (TextView) v.findViewById(R.id.titleText);
            shareImageButton = (ImageView)v.findViewById(R.id.shareIcon) ;
            twitIcon = (ImageView)v.findViewById(R.id.twitIcon);
            fbicon = (ImageView) v.findViewById(R.id.fbIcon);
            bookMarkIcon = (ImageView) v.findViewById(R.id.bookmarkIcon);



            //handle the lead image click
            leadImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //make a new intent with the story class
                    Intent intent = new Intent(context, NewsStories.class);
                    //get the specific story object for click
                    Story story = (Story)leadImageButton.getTag();
                    //put the object in the intent
                    intent.putExtra("story", story);
                    //send the activity
                    context.startActivity(intent);
                }
            });

            shareImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                        //create a new intent to send a message with the devices messaging apps
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        //most generic type, will probably change to something more compatible
                        //with html as this is for story sharing
                        intent.setType("text/plain");
                        //title of the sharing box
                        String title = "Share Via...";
                        //get the specific story object
                        Story story = (Story)leadImageButton.getTag();
                        String shareText = story.getStoryURL();
                        //handle subject instances
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
                        //load the share text
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);

                        //use the native method to create and display the chooser
                        Intent chooseIntent = Intent.createChooser(intent, title);
                        //do it
                        context.startActivity(chooseIntent);

                }
            });

            twitIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Story story = (Story)leadImageButton.getTag();
                    String text = story.getStoryURL();
                    Uri img = Uri.parse(story.getImgUrl());
                    TweetComposer.Builder builder = new TweetComposer.Builder(context)
                            .text(text)
                            .image(img);
                    builder.show();


                }
            });


            bookMarkIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Story story = (Story)leadImageButton.getTag();
                    //dummy hash for now as this will be brought in from JSON
                    story.setHash(story.getHash()) ;
                    story.setTitle(story.getTitle());
                    story.setImgUrl(story.getImgUrl());
                    story.setPubDate(story.getPubDate());
                    story.setAuthor(story.getAuthor());
                    story.setBody(story.getBody());
                    //initialize db instance
                    StoryDBHandler dbLink = new StoryDBHandler(context);
                    //put story in db
                    dbLink.bookmarkStory(story);
                    //close connection
                    dbLink.close();
                    //so that the user doesn't become confused and annoyed
                    //show them that the addition was succesful
                    Toast.makeText(context, "Story Bookmarked!", Toast.LENGTH_SHORT).show();
                }
            });



        }

        @Override
        public void onClick(View view){
        }

    }


    @Override
    public StoriesHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //inflate the single story view
        View view = inflater.inflate(R.layout.news_story_card, parent, false);
        //pass it to the holder
        holder = new StoriesHolder(view, parent.getContext());
        //get the image and text view

        return holder;
    }

    @Override
    public void onBindViewHolder(StoriesHolder holder, int index){
        //get the view at each index
        Story storyList = list.get(index);
        //set the image and text
        holder.titleText.setText(storyList.getTitle());
        //set items from the listview
        holder.leadImageButton.setTag(storyList);
        holder.storyCard.setTag(storyList);
        Picasso.get().load(storyList.getImgUrl()).into(holder.leadImageButton);
    }

    //in case the list size is needed
    @Override
    public int getItemCount(){
        return list.size();
    }

}
