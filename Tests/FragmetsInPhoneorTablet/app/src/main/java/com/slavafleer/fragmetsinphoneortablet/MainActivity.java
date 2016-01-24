package com.slavafleer.fragmetsinphoneortablet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListFragment.Callbacks {

    String deviceType;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceType = (String) findViewById(R.id.linearLayoutRoot).getTag();

        Toast.makeText(this, "Running on " + deviceType, Toast.LENGTH_LONG).show();

        // Fragments Initialising
        // List Fragment
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentListViewItemsContainer, new ListFragment())
                .commit();

        // Item Position Fragment
        if (deviceType.equals("tablet")) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentItemPositionContainer, new ItemPositionFragment())
                    .commit();
        }
    }

    // Runs each time when a list item was clicked in ListFragment
    @Override
    public void onItemClick(int itemPosition) {
        Toast.makeText(this, "Clicked on " + itemPosition, Toast.LENGTH_SHORT).show();

        // Run if device is tablet
        if (deviceType.equals("tablet")) {

            ItemPositionFragment itemPositionFragment = new ItemPositionFragment();

            Bundle bundle = new Bundle();
            bundle.putInt("position", itemPosition);
            itemPositionFragment.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();

            Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentItemPositionContainer);

            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();


            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentItemPositionContainer, itemPositionFragment)
                    .commit();

        } else { // If device is phone

            // Open a new activity and send data
            Intent intent = new Intent(this, ItemPositionActivity.class);
            intent.putExtra("position", itemPosition);
            startActivity(intent);
        }
    }
}
