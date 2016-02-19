package com.slavafleer.nearpois;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.slavafleer.nearpois.db.ResultsLogic;
import com.slavafleer.nearpois.recyclerView.PoiAdapter;
import com.slavafleer.nearpois.recyclerView.QuickSearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * This fragment includes search input,
 * running default buttons for quick searching
 * and the list of results.
 */
public class PoisFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = PoisFragment.class.getSimpleName();

    private static final int REQUEST_POIS = 1;

    private RecyclerView recyclerViewPois;
    private RecyclerView recyclerViewQuickSearches;

    private PoiAdapter poiAdapter;

    private Activity activity;

    private ResultsLogic resultsLogic; // db business logic

    private ArrayList<Poi> pois = new ArrayList<>();

    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private double clientLatitude;
    private double clientLongitude;

    private EditText editTextSearchText;
    private ImageView imageViewFind;
    private ImageView imageViewFindAll;

    //    private String type = "food";
    private String type;

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

        buildGoogleApiClient();

        editTextSearchText = (EditText) view.findViewById(R.id.editTextSearchText);

        // Find Button On Click
        imageViewFind = (ImageView) view.findViewById(R.id.imageViewFind);
        imageViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPois();
            }
        });

        // FindAll Button On Click
        imageViewFindAll = (ImageView) view.findViewById(R.id.imageViewFindAll);
        imageViewFindAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPois();
            }
        });

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

    // Create poi request and send it to API.
    private void findPois() {
        try {
            String encodedSearchText = java.net.URLEncoder.encode(editTextSearchText.getText().toString().trim(), "UTF-8");

            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                    clientLatitude + "," + clientLongitude + "&name=" + encodedSearchText +
                    "&rankby=distance&key=" + Constants.ACCESS_KEY_GOOGLE_PLACE_API;

            Log.i(TAG, url.toString());

            JsonObjectRequest jsonRequest = new JsonObjectRequest(
                    Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {

                // On receive json, update pois array.
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String status = response.getString(Constants.KEY_STATUS);

                        if (status.equals(Constants.VALUE_OK)) {
                            pois.clear();
                            JSONArray results = response.getJSONArray(Constants.KEY_RESULTS);
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject oneResult = results.getJSONObject(i);
                                String name = oneResult.getString(Constants.KEY_NAME);
                                String vicinity = oneResult.getString(Constants.KEY_VICINITY);
                                String place_id = oneResult.getString(Constants.KEY_PLACE_ID);
                                JSONObject geometry = oneResult.getJSONObject(Constants.KEY_GEOMETRY);
                                JSONObject location = geometry.getJSONObject(Constants.KEY_LOCATION);
                                String latitude = location.getString(Constants.KEY_LATITUDE);
                                String longitude = location.getString(Constants.KEY_LONGITUDE);
                                String photo_reference = null;
                                if (oneResult.has(Constants.KEY_PHOTOS)) {
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

                    }catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, error.getMessage());
                }
            });

            Volley.newRequestQueue(activity).add(jsonRequest);

        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    // Build Google Api Client
    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    // For Google Location API
    // Runs when a GoogleApiClient object successfully connects.
    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (lastLocation != null) {
            clientLatitude = lastLocation.getLatitude();
            clientLongitude = lastLocation.getLongitude();
            Log.i(TAG, "Client location: " + clientLatitude + ", " + clientLongitude);
        } else {
            Log.i(TAG, "Client location is not found.");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        // The connection to Google Play services was lost for some reason.
        // We call connect() to attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // Refer to the javadoc for ConnectionResult to see what error codes
        // might be returned in onConnectionFailed.
        Log.i(TAG, "Connection failed: ErrorCode: " + connectionResult.getErrorCode());
    }
}
