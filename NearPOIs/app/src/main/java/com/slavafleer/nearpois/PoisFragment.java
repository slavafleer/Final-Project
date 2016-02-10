package com.slavafleer.nearpois;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slavafleer.nearpois.recyclerView.PoiAdapter;

import java.util.ArrayList;


/**
 * This fragment includes search input,
 * running default buttons for quick searching
 * and the list of results.
 */
public class PoisFragment extends Fragment {

    private RecyclerView recyclerViewPois;

    public PoisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pois, container, false);

        recyclerViewPois = (RecyclerView) view.findViewById(R.id.recyclerViewPois);

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
        PoiAdapter poiAdapter = new PoiAdapter(getActivity(), pois);
        recyclerViewPois.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewPois.setAdapter(poiAdapter);

        return view;
    }

}
