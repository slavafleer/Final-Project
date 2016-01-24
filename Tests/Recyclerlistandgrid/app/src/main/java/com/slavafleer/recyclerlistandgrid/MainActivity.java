package com.slavafleer.recyclerlistandgrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<PointOfInterest> pointsOfInterest;

    private RecyclerView recyclerViewPointsOfInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewPointsOfInterest = (RecyclerView) findViewById(R.id.recyclerViewPointsOfInterest);

        pointsOfInterest = new ArrayList<>();

        pointsOfInterest.add(new PointOfInterest(1, "Place 1", "Street 1", 1, null));
        pointsOfInterest.add(new PointOfInterest(2, "Place 2", "Street 2", 2, null));
        pointsOfInterest.add(new PointOfInterest(3, "Place 3", "Street 3", 3, null));
        pointsOfInterest.add(new PointOfInterest(4, "Place 4", "Street 4", 4, null));
        pointsOfInterest.add(new PointOfInterest(5, "Place 5", "Street 5", 5, null));
        pointsOfInterest.add(new PointOfInterest(6, "Place 6", "Street 6", 6, null));
        pointsOfInterest.add(new PointOfInterest(7, "Place 7", "Street 7", 7, null));
        pointsOfInterest.add(new PointOfInterest(8, "Place 8", "Street 8", 8, null));
        pointsOfInterest.add(new PointOfInterest(9, "Place 9", "Street 9", 9, null));
        pointsOfInterest.add(new PointOfInterest(10, "Place 10", "Street 10", 10, null));
        pointsOfInterest.add(new PointOfInterest(11, "Place 11", "Street 11", 11, null));
        pointsOfInterest.add(new PointOfInterest(1, "Place 1", "Street 1", 1, null));
        pointsOfInterest.add(new PointOfInterest(2, "Place 2", "Street 2", 2, null));
        pointsOfInterest.add(new PointOfInterest(3, "Place 3", "Street 3", 3, null));
        pointsOfInterest.add(new PointOfInterest(4, "Place 4", "Street 4", 4, null));
        pointsOfInterest.add(new PointOfInterest(5, "Place 5", "Street 5", 5, null));
        pointsOfInterest.add(new PointOfInterest(6, "Place 6", "Street 6", 6, null));
        pointsOfInterest.add(new PointOfInterest(7, "Place 7", "Street 7", 7, null));
        pointsOfInterest.add(new PointOfInterest(8, "Place 8", "Street 8", 8, null));
        pointsOfInterest.add(new PointOfInterest(9, "Place 9", "Street 9", 9, null));
        pointsOfInterest.add(new PointOfInterest(10, "Place 10", "Street 10", 10, null));
        pointsOfInterest.add(new PointOfInterest(11, "Place 11", "Street 11", 11, null));

        PointOfInterestAdapter adapter = new PointOfInterestAdapter(this, pointsOfInterest);
        recyclerViewPointsOfInterest.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPointsOfInterest.setAdapter(adapter);
    }
}
