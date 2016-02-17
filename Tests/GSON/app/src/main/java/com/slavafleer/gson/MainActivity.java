package com.slavafleer.gson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
}
