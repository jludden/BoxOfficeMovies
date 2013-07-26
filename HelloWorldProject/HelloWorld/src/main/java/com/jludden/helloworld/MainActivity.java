package com.jludden.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;

/**
 *  Created by Jason on 7/14/13.
 *  Downloads & displays a list of box-office movies from rotten tomatoes
 *  When a movie is selected DetailActivity is brought up for the movie
 */
public class MainActivity extends Activity {

    private ListView mainListView;
    private ArrayAdapter<String> listAdapter ;
    private JSONArray movies;

    @Override
    /**
     * Called on application start. Starts download and display of movies
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=52g5ajr5h6e6ky9k7kpha9re";
        ArrayList<String> myMovieList=new ArrayList<String>();

        RelativeLayout layout = new RelativeLayout(this);
        mainListView = new ListView(this);
        setContentView(layout);
        layout.addView(mainListView);
        Log.d("jludden","Initialized");

        //create async task to process in background
        MyDownloader myTask = new MyDownloader();
        myTask.execute(url);
        String myJSONstring="";

        //TODO better solution? - .get() will cause the UI thread to freeze
        try {
            Log.d("jludden","waiting on download....");
            myJSONstring=myTask.get();
            Log.d("jludden","finished download");

            JSONObject object = (JSONObject) new JSONTokener(myJSONstring).nextValue();
            movies = object.getJSONArray("movies");

            for(int i=0; i<movies.length(); i++){
                object = (JSONObject) new JSONTokener(movies.getString(i)).nextValue();
                myMovieList.add(object.getString("title"));
                Log.d("jludden","i:"+i);
                Log.d("jludden",object.getString("title"));
            }

            //display the movie list
            listAdapter = new ArrayAdapter<String>(this,R.layout.simplerow,myMovieList);
            mainListView.setAdapter(listAdapter);

        } catch (Exception e) {
            Log.e("jludden","error in main thread",e);
            e.printStackTrace();
        }

        //set up listener to open detail activity when a movie is clicked
        mainListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                String movieData;
                Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
                //store the position (which movie was clicked)
                myIntent.putExtra("pos", pos);

                //store data about the movie:
                try {
                    myIntent.putExtra("data",movies.getString(pos));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(myIntent);
            }
        });
    }

    //TODO do something with the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("jludden","Options");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}


