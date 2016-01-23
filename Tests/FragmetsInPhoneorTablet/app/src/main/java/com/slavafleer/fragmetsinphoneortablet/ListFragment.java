package com.slavafleer.fragmetsinphoneortablet;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    Callbacks callbacks;
    ListView listViewItems;

    ArrayList<String> arrayList;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        arrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            arrayList.add("Item Number " + i);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);

        listViewItems = (ListView) view.findViewById(R.id.listViewItems);

        listViewItems.setAdapter(arrayAdapter);

        callbacks = (Callbacks) getActivity();

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callbacks.onItemClick(position);
            }
        });

        return view;
    }

    public interface Callbacks {

        void onItemClick(int itemPosition);
    }

}
