package com.slavafleer.nearpois;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * This fragment includes search input,
 * running default buttons for quick searching
 * and the list of results.
 */
public class PoisFragment extends Fragment {


    public PoisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pois, container, false);
    }

}
