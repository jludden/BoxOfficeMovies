package com.jludden.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Jason on 7/23/13.
 * Displays detail information for a movie
 */
public class DetailActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //TODO nicer display - something like:
        //http://developer.android.com/training/basics/fragments/fragment-ui.html
        //also how about adding a back button and other goodies?

        RelativeLayout layout = new RelativeLayout(this);
        TextView tv = new TextView(this);
        tv.setText("Loading...");

        setContentView(layout);
        layout.addView(tv);
        String toDisplay;

        Bundle b = getIntent().getExtras();
        int pos = b.getInt("pos");
        String movieData=b.getString("data");
        Log.d("jludden","pos: "+pos);

        //TODO show critics/audience ratings
        try{
         JSONObject object = (JSONObject) new JSONTokener(movieData).nextValue();
         toDisplay=
                "Film:\t"+object.getString("title")+"\n\n"+
                "Critics Consensus:\n"+object.getString("critics_consensus")+"\n\n"+
                "Synopsis:\n"+object.getString("synopsis");

         tv.setText(toDisplay);

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
