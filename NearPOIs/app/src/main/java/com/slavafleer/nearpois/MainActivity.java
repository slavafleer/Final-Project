package com.slavafleer.nearpois;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.slavafleer.nearpois.recyclerView.PoiHolder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PoiHolder.ClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

//    private ResultsLogic resultsLogic; // db business logic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        resultsLogic = new ResultsLogic(this);

        // TODO: for testing only, need to delete.
        //initTestResults();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.poisContainer, new PoisFragment())
                .commit();

        // Hide soft keyboard on start.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    // TODO: for testing only, need to delete.
    private void initTestResults() {

        ArrayList<Poi> pois = new ArrayList<>();
        pois.add(new Poi("Place 1"));
        pois.add(new Poi("Place 2"));
        pois.add(new Poi("Place 3"));
        pois.add(new Poi("Place 4"));
        pois.add(new Poi("Place 5"));
        pois.add(new Poi("Place 6"));
        pois.add(new Poi("Place 7"));

//        resultsLogic.open();
//        for(Poi poi : pois) {
//            resultsLogic.addPoi(poi);
//        }
//        resultsLogic.close();
    }

    // Do it on poi item data part clicked.
    @Override
    public void onDataClick(String name) {

        Log.i(TAG, name);
    }

    // Do it on poi item data part long clicked.
    @Override
    public void onDataLongClick(String name) {

        Log.i(TAG, name + " long touched.");
    }

    // Do it on poi item photo part clicked.
    @Override
    public void onPhotoClick(String name) {

        Log.i(TAG, "Photo of " + name);
    }
}
