package com.example.nebulese.myapplication.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WebLink {
    private String response;
    private static final int SUCCESS_CODE = 200;

    public ResponseClass getResponse(String urlLink){
        URL url = null;
        try{
            url = new URL("https://indianapublicmedia.org/feeds/newsjson.json");
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        try{
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//            httpURLConnection.setRequestMethod("GET");
//            httpURLConnection.setConnectTimeout(60000);
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());

            response = readStream(in);
            ResponseClass responseObject = new ResponseClass(httpURLConnection.getResponseCode(), "", "");
            if(httpURLConnection.getResponseCode() == SUCCESS_CODE){
                responseObject.setmMessaage(response);
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
        String t;
        BufferedReader s = new BufferedReader(new InputStreamReader(in));
        StringBuilder g = new StringBuilder();
        while((t = s.readLine()) != null){
            g.append(t);
        }
        return g.toString();
    }
}


