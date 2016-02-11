package com.slavafleer.nearpois;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.slavafleer.nearpois.recyclerView.PoiHolder;

public class MainActivity extends AppCompatActivity implements PoiHolder.ClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.poisContainer, new PoisFragment())
                .commit();

        // Hide soft keyboard on start.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
