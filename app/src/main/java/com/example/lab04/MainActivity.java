package com.example.lab04;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
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
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    MyListAdapter myAdapter;
    private Bundle elements = new Bundle();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StarWars starWars = new StarWars();
        starWars.execute("https://swapi.dev/api/people/?format=json");


    }
        private class StarWars extends AsyncTask< String, Integer, String > {

            ListView myList = (ListView) findViewById(R.id.listView);

            @Override
            protected String doInBackground(String... args) {
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
                        JSONObject starWarsList = new JSONObject(result);
                        JSONArray characters = starWarsList.getJSONArray("results");

                        for (int i=0 ; i < characters.length(); i++){
                            JSONObject obj = characters.getJSONObject(i);
                            String name = obj.getString("name");
                            elements.putString("Name", name);
                        }
                        myList.setAdapter( myAdapter = new MyListAdapter() );
                        myList.setOnItemLongClickListener((parent, view, pos, id) ->{

                            myAdapter.notifyDataSetChanged();
                            return false;
                        });

                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    catch (IOException e) {
                        // this is an error when openConnection fails
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

            return "";
            }
        }
    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return elements.size();
        }
        public String getItem(int position) {
            return elements.get(position+ "").toString();
        }
        public long getItemId(int position) {
            return (long) position;
        }
        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();
            if (newView == null) {
                newView = inflater.inflate(R.layout.activity_main, parent, false);
            }
            TextView lView = newView.findViewById(R.id.listView);
            lView.setText(getItem(position));

            return newView;
        }
    }
}