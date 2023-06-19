package com.example.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Url is happening", "test");
        CatImages catImg = new CatImages();
        catImg.execute("https://cataas.com/cat?json=true");



    }
        private class CatImages extends AsyncTask< String, Integer, String > {
            Bitmap catImage;
            ProgressBar bar;

            @Override
            protected String doInBackground(String... args) {
                ImageView image = (ImageView) findViewById(R.id.CatImage);

                while(true){
                    try {

                        URL url = new URL (args[0]);
                        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                        InputStream response = urlConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                        StringBuilder sb =new StringBuilder();

                        String line = null;
                        while ((line=reader.readLine())!=null){
                            sb.append(line + "\n");
                        }
                        String result = sb.toString();
                        JSONObject imgData = new JSONObject(result);
                        String imgId= imgData.getString("_id");
                        File imgFile = new File(imgId);
                        Log.i("Does the file exist?", String.valueOf(imgFile.exists()));
                        if (!imgFile.exists()){
                            String imgUrl = "https://cataas.com"+imgData.getString("url");
                            Log.i("Does the imgUrl exist?", imgUrl);

                            URL imgurl = new URL (imgUrl);
                            HttpsURLConnection imgurlConnection = (HttpsURLConnection) imgurl.openConnection();
                            int responseCode = -1;
                            imgurlConnection.connect();
                            responseCode = imgurlConnection.getResponseCode();
                            if(responseCode == HttpURLConnection.HTTP_OK)
                            {
                                //download
                                response = imgurlConnection.getInputStream();
                                catImage = BitmapFactory.decodeStream(response);
                                response.close();
                            }
                        }
                        if (!imgFile.exists()){
                            Log.i("Does the file exist now?", String.valueOf(imgFile.exists()));

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                image.setImageBitmap(catImage);
                            }
                        });

                        for (int i = 0; i<100; i++){
                            try{
                                publishProgress(i);
                                Thread.sleep(30);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    catch (IOException e) {
                        // this is an error when openConnection fails
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            protected void onProgressUpdate(Integer ... args){
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.Progressbar);
                progressBar.setProgress(args[0]);

            }
        }

}