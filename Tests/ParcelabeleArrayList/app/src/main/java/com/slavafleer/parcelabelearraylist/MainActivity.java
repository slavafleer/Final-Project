package com.slavafleer.parcelabelearraylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String KEY_DATA = "data";
    private ArrayList<Data> dataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            dataArrayList = savedInstanceState.getParcelableArrayList(KEY_DATA);
            Log.v(TAG, "Loaded current data from savedInstanceState.");
        } else {
            for(int i = 0; i < 10; i++) {
                dataArrayList.add(new Data(i, "Name " + i + 1, "Street " + i + 1));
            }
            Log.v(TAG, "Loaded new data but initialising.");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(KEY_DATA, dataArrayList);
    }

    public void button_onClick(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
