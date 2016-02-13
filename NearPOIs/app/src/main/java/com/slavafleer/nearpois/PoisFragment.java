package com.slavafleer.nearpois;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slavafleer.nearpois.recyclerView.PoiAdapter;
import com.slavafleer.nearpois.recyclerView.QuickSearchAdapter;

import java.util.ArrayList;


/**
 * This fragment includes search input,
 * running default buttons for quick searching
 * and the list of results.
 */
public class PoisFragment extends Fragment {

    private RecyclerView recyclerViewPois;
    private RecyclerView recyclerViewQuickSearches;

    private Activity activity;

    public PoisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pois, container, false);

        activity = getActivity();

        recyclerViewPois = (RecyclerView) view.findViewById(R.id.recyclerViewPois);
        recyclerViewQuickSearches = (RecyclerView) view.findViewById(R.id.recyclerViewQuickSearches);

        // TODO: TESTING
        ArrayList<Poi> pois = new ArrayList<>();
        pois.add(new Poi("Place 1"));
        pois.add(new Poi("Place 2"));
        pois.add(new Poi("Place 3"));
        pois.add(new Poi("Place 4"));
        pois.add(new Poi("Place 5"));
        pois.add(new Poi("Place 6"));
        pois.add(new Poi("Place 7"));

        // Initialising Pois Recycler View.
        PoiAdapter poiAdapter = new PoiAdapter(activity, pois);
        recyclerViewPois.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewPois.setAdapter(poiAdapter);

        // Initialising Quick Search Array;
        String[] searchTypes = getResources().getStringArray(R.array.searchTypes);
        String[] imageNames = getResources().getStringArray(R.array.imageNames);

        ArrayList<QuickSearch> quickSearches = new ArrayList<>();
        for(int i = 0; i < searchTypes.length; i++) {
            quickSearches.add(new QuickSearch(activity, searchTypes[i], imageNames[i]));
        }

        // Initialising Quick Search Recycler View.
        QuickSearchAdapter quickSearchAdapter = new QuickSearchAdapter(activity, quickSearches);
        recyclerViewQuickSearches.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewQuickSearches.setAdapter(quickSearchAdapter);

        // Put Recycler in middle of "endless" icons.
        recyclerViewQuickSearches.scrollToPosition(
                Constants.NUMBER_OF_QUICK_SEARCH_ICONS * QuickSearchAdapter.LOOPS_AMMOUNT / 2);

        return view;
    }

}
