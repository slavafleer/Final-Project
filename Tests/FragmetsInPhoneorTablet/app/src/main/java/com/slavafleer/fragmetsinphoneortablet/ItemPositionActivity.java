package com.slavafleer.fragmetsinphoneortablet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ItemPositionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_position);

        Intent intent = getIntent();

        int itemPosition = intent.getIntExtra("position", -1);

        ItemPositionFragment itemPositionFragment = new ItemPositionFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("position", itemPosition);
        itemPositionFragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentItemPositionContainer, itemPositionFragment)
                .commit();
    }
}
