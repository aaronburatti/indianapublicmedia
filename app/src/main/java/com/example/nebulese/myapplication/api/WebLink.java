package com.example.nebulese.myapplication.api;

import android.util.Log;

import com.example.nebulese.myapplication.datamodels.Story;

import org.json.JSONArray;
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
    private static final int SUCCESS_CODE = 200;

    public ResponseClass getResponse(String urlLink){
        URL url = null;
        try{
            url = new URL(urlLink);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        try{
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(60000);
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            Log.e("yyy", "response in " + in);
            String response = readStream(in);
            Log.e("yyy", "response is " + response);
            ResponseClass responseObject = new ResponseClass(httpURLConnection.getResponseCode(), "", "");
            if(httpURLConnection.getResponseCode() == SUCCESS_CODE){
               responseObject.setmMessaage(response);
               Log.i("", "getting response = " + response);
            } else{
                responseObject.setmMessaage("Error in the response");
            }
            return responseObject;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readStream(InputStream in) throws IOException{
        BufferedReader s = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder builder = new StringBuilder();
        while((line = s.readLine()) != null){
            builder.append(line);
        }
        String g = builder.toString();
        in.close();
        return g;
    }
}


