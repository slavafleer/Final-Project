package com.slavafleer.gson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements QuerySenderAsyncTask.Callbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_POIS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=31.2589,34.7997&type=cafe&rankby=distance&key=AIzaSyBqZywUvsonHXo6gVpiI0p-ABQ9oRuYdJw");
            new QuerySenderAsyncTask(this, REQUEST_POIS).execute(url);

        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }


        Gson gson = new GsonBuilder().create();

        String jsonText = gson.toJson("The new Json Text");

        System.out.println(jsonText);

        String jsonTestPut = gson.toJson(new JsonTest("OK Ilinishna."));

        System.out.println(jsonTestPut);

        JsonTest result = gson.fromJson(jsonTestPut, JsonTest.class);

        System.out.println(result);

        JsonTest jsonTestResult = gson.fromJson(getResources().getString(R.string.json), JsonTest.class);

        System.out.println(jsonTestResult);


//        Poi poi = gson.fromJson(jsonResult, Poi.class);

//        JsonTest jsonTest = gson.fromJson(jsonResult, JsonTest.class);
//
//        System.out.println(jsonTest.toString());
    }

    @Override
    public void onAboutToStartQuerySender() {

    }

    @Override
    public void onSuccessQuerySender(String result, int respondId) {

    }

    @Override
    public void onErrorQuerySender(String errorMessage, int respondId) {

    }
}
