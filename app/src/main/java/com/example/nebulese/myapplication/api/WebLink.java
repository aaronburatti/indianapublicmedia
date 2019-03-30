package com.example.nebulese.myapplication.api;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/*

This class will take a json url, test its validity, build it as a string and return it to the calling asynctask

*/

public class WebLink {
    private static final int SUCCESS_CODE = 200;

    public ResponseClass getResponse(String urlLink){
        //start with clearing url
        URL url = null;
        try{
            //try the url parameter
            url = new URL(urlLink);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        try{
            //pass the link to a http object
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            //make sure its a get request
            httpURLConnection.setRequestMethod("GET");
            //give ourselves ample time for a response
            httpURLConnection.setConnectTimeout(60000);
            //read input as a stream then buffer it. pass this to an input stream object
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            //convert the stream to a string with custom method
            String response = readStream(in);
            //use the response class to handle the server response
            ResponseClass responseObject = new ResponseClass(httpURLConnection.getResponseCode(), "Yikes", "too bad");
            //if we're good
            if(httpURLConnection.getResponseCode() == SUCCESS_CODE){
                //place the response in an object
               responseObject.setmMessaage(response);
            } else{
                //otherwise alert to an error
                responseObject.setmMessaage("Error in the response");
            }
            //return the manicured response
            return responseObject;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readStream(InputStream in) throws IOException{
        //read the input stream
        BufferedReader s = new BufferedReader(new InputStreamReader(in));
        //dummy string
        String line;
        StringBuilder builder = new StringBuilder();
        //as long as there is another line
        while((line = s.readLine()) != null){
            //place the line in a builder
            builder.append(line);
        }
        //convert it to a string
        String g = builder.toString();
        //close the input stream
        in.close();
        //return the input
        return g;
    }
}


