package com.slavafleer.nearpois;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slavafleer.nearpois.asynkTask.SendQueryAsyncTask;
import com.slavafleer.nearpois.db.ResultsLogic;
import com.slavafleer.nearpois.recyclerView.PoiAdapter;
import com.slavafleer.nearpois.recyclerView.QuickSearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * This fragment includes search input,
 * running default buttons for quick searching
 * and the list of results.
 */
public class PoisFragment extends Fragment implements SendQueryAsyncTask.Callbacks {

    private static final String TAG = PoisFragment.class.getSimpleName();

    private RecyclerView recyclerViewPois;
    private RecyclerView recyclerViewQuickSearches;

    private PoiAdapter poiAdapter;

    private Activity activity;

    private ResultsLogic resultsLogic; // db business logic

    private ArrayList<Poi> pois = new ArrayList<>();

    public PoisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pois, container, false);

        activity = getActivity();
        resultsLogic = new ResultsLogic(activity);

        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=31.2589,34.7997&type=cafe&rankby=distance&key=AIzaSyBqZywUvsonHXo6gVpiI0p-ABQ9oRuYdJw");
            new SendQueryAsyncTask(this, 1).execute(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        recyclerViewPois = (RecyclerView) view.findViewById(R.id.recyclerViewPois);
        recyclerViewQuickSearches = (RecyclerView) view.findViewById(R.id.recyclerViewQuickSearches);

        // TODO: TESTING

//        resultsLogic.open();
//        pois = resultsLogic.getAllResults();
//        resultsLogic.close();


        // Initialising Pois Recycler View.
        poiAdapter = new PoiAdapter(activity, pois);
        recyclerViewPois.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewPois.setAdapter(poiAdapter);

        // Initialising Quick Search Array;
        String[] searchTypes = getResources().getStringArray(R.array.searchTypes);
        String[] imageNames = getResources().getStringArray(R.array.imageNames);

        ArrayList<QuickSearch> quickSearches = new ArrayList<>();
        for (int i = 0; i < searchTypes.length; i++) {
            quickSearches.add(new QuickSearch(activity, searchTypes[i], imageNames[i]));
        }

        // Initialising Quick Search Recycler View.
        QuickSearchAdapter quickSearchAdapter = new QuickSearchAdapter(activity, quickSearches);
        recyclerViewQuickSearches.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewQuickSearches.setAdapter(quickSearchAdapter);

        // Put Recycler in middle of "endless" icons.
        recyclerViewQuickSearches.scrollToPosition(
                Constants.NUMBER_OF_QUICK_SEARCH_ICONS * QuickSearchAdapter.LOOPS_AMOUNT / 2);

        return view;
    }

    // Interface Callbacks from SendQueryAsyncTask
    // Precede before asyncTask.
    @Override
    public void onAboutToStart() {

    }

    // When result received, send it to main thread.
    @Override
    public void onSuccess(String result, int respondKey) {

        switch (respondKey) {
            case 1: {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Log.v(TAG, result);
                    String status = jsonObject.getString(Constants.KEY_STATUS);

                    if (status.equals(Constants.VALUE_OK)) {
                        JSONArray results = jsonObject.getJSONArray(Constants.KEY_RESULTS);
                        for(int i = 0; i < results.length(); i++) {
                            JSONObject oneResult = results.getJSONObject(i);
                            String name = oneResult.getString(Constants.KEY_NAME);
                            String vicinity = oneResult.getString(Constants.KEY_VICINITY);
                            String place_id = oneResult.getString(Constants.KEY_PLACE_ID);
                            JSONObject geometry = oneResult.getJSONObject(Constants.KEY_GEOMETRY);
                            JSONObject location = geometry.getJSONObject(Constants.KEY_LOCATION);
                            String latitude = location.getString(Constants.KEY_LATITUDE);
                            String longitude = location.getString(Constants.KEY_LONGITUDE);
                            String photo_reference = null;
                            if(oneResult.has(Constants.KEY_PHOTOS)) {
                                JSONArray photos = oneResult.getJSONArray(Constants.KEY_PHOTOS);
                                JSONObject photo = photos.getJSONObject(0);
                                photo_reference = photo.getString(Constants.KEY_PHOTO_REFERENCE);
                            }
                            String iconUrl = oneResult.getString(Constants.KEY_ICON_URL);

                            pois.add(new Poi(name, vicinity, place_id, Float.parseFloat(latitude),
                                    Float.parseFloat(longitude), photo_reference, iconUrl));
                        }

                        poiAdapter.notifyDataSetChanged(); // update recycler
                    } else {
                        Log.e(TAG, status);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

    }

    // If error received, send it to main thread.
    @Override
    public void onError(String errorMessage, int respondKey) {

    }
}
