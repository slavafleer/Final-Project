package com.slavafleer.fragmetsinphoneortablet;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemPositionFragment extends Fragment {

    TextView textViewItemPosition;

    public ItemPositionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_position, container, false);

        textViewItemPosition = (TextView) view.findViewById(R.id.textViewItemPosition);

        Bundle bundle = getArguments();

        if (bundle != null) {
            int position = bundle.getInt("position", -1);
            textViewItemPosition.setText("Item Position " + position);
        }

        return view;
    }

}
