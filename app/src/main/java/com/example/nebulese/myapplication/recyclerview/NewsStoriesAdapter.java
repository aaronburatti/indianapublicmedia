package com.example.nebulese.myapplication.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nebulese.myapplication.R;
import com.example.nebulese.myapplication.activities.MainActivity;
import com.example.nebulese.myapplication.activities.NewsStories;
import com.example.nebulese.myapplication.datamodels.Story;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class NewsStoriesAdapter extends RecyclerView.Adapter<NewsStoriesAdapter.StoriesHolder> {
    //register all lists, views, and classes needed globally
    ArrayList<Story> list = new ArrayList<>();
//    View view;
    LayoutInflater inflater;
    NewsStoriesAdapter.StoriesHolder holder;
    private Context context;
    public ImageView leadImageButton;
    public TextView titleText;
    private CardView storyCard;
    private ImageView twitIcon;
    ImageButton shareImageButton;


    public NewsStoriesAdapter(Context context, ArrayList<Story> list){
        //constructor consuming the context, inflater, and list
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    //inner class to process the inflated view and make everything clickable
    //in the future this will bring up the whole of the bookmarked story
    public static class StoriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LayoutInflater inflater;
        public ImageButton leadImageButton;
        public TextView titleText;
        private Context context;
        private CardView storyCard;
        private ImageView twitIcon;
        ImageButton shareImageButton;

        public StoriesHolder(View v, Context context){
            super(v);
            v.setOnClickListener(this);
            this.context = context;
            storyCard = (CardView)v.findViewById(R.id.storyCard);
            leadImageButton = (ImageButton) v.findViewById(R.id.leadImage);
            titleText = (TextView) v.findViewById(R.id.titleText);
            shareImageButton = (ImageButton)v.findViewById(R.id.shareIcon) ;
            twitIcon = (ImageView)v.findViewById(R.id.twitIcon);
            leadImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //make a new intent with the story class
                    Intent intent = new Intent(context, NewsStories.class);

                    Story story = (Story)leadImageButton.getTag();
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
                        Story story = (Story)leadImageButton.getTag();
                        String shareText = story.getBody();
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);

                        //use the native method to create and display the chooser
                        Intent chooseIntent = Intent.createChooser(intent, title);
                        //do it
                        context.startActivity(chooseIntent);

                }
            });

            twitIcon.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Story story = (Story)leadImageButton.getTag();
                    Twitter twitter = TwitterFactory.getSingleton();
                    twitter.setOAuthConsumer("63GE7RHkdFVnEwKXG62sN1VPz ", "13tPBDBPCyEFscFvucBWyUzAVq5Sg9o6kTuI9hDBceM98E9Nht");
                    RequestToken requestToken = null;
                    try {
                        requestToken = twitter.getOAuthRequestToken();
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                    AccessToken accessToken = null;
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    while (null == accessToken) {
                        String pin = null;
                        try {
                            pin = br.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try{
                            if(pin.length() > 0){
                                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                            }else{
                                accessToken = twitter.getOAuthAccessToken();
                            }
                        } catch (TwitterException te) {
                            if(401 == te.getStatusCode()){
                                System.out.println("Unable to get the access token.");
                            }else{
                                te.printStackTrace();
                            }
                        }
                    }
                    try {
                        twitter.updateStatus(story.getTitle());
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
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
