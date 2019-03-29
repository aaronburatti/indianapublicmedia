package com.example.nebulese.myapplication.api;

import android.util.Log;

import com.example.nebulese.myapplication.datamodels.Story;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class WebLink {
    private ArrayList response;
    private static final int SUCCESS_CODE = 200;

    public ArrayList getResponse(String urlLink){
        URL url = null;
        try{
            url = new URL("https://indianapublicmedia.org/feeds/newsjson.json");
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        try{
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(60000);
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());

            response = readStream(in);
            Log.i("yyy", "response is" + response);
            ResponseClass responseObject = new ResponseClass(httpURLConnection.getResponseCode(), "", "");
            if(httpURLConnection.getResponseCode() == SUCCESS_CODE){
               responseObject.setmMessaage("Successfully read the response");
            } else{
                responseObject.setmMessaage("Error in the response");
            }
            return response;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList readStream(InputStream in) throws IOException, JSONException, ParseException {
        ArrayList<Story> jsonStoriesList = new ArrayList<>();
        BufferedReader s = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder builder = new StringBuilder();
        while((line = s.readLine()) != null){
            builder.append(line);
        }
        String g = builder.toString();
        JSONObject jsonObject = new JSONObject(g);
        Log.i("json","len" + jsonObject);

        for(int i = 0; i < 20; i++){
            Story story = new Story();
            story.setTitle(jsonObject.getString("stories"));
            //story.setTitle(jsonObject.getString("title"));
            story.setHash(jsonObject.getString("hash"));
            story.setImgUrl(jsonObject.getString("img"));
            story.setPubDate(new SimpleDateFormat(jsonObject.getString("date")));
            story.setBody(jsonObject.getString("story"));
            Log.i("json","len" + jsonObject.getString("id"));

        }
        return null;
    }
}


