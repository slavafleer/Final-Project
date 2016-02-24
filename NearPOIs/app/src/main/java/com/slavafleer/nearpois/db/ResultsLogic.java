package com.slavafleer.nearpois.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.slavafleer.nearpois.Poi;

import java.util.ArrayList;

// Logic Class that implements DB methods
public class ResultsLogic extends BaseLogic {

    public ResultsLogic(Activity activity) {
        super(activity);
    }

    // Add new POI to results database.
    public long addPoi(Poi poi) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB.Results.NAME, poi.getName());
        contentValues.put(DB.Results.ADDRESS, poi.getAddress());
        contentValues.put(DB.Results.VICINITY, poi.getVicinity());
        contentValues.put(DB.Results.DISTANCE_TEXT, poi.getDistanceText());
        contentValues.put(DB.Results.DISTANCE_VALUE, poi.getDistanceValue());
        contentValues.put(DB.Results.PLACE_ID, poi.getPlace_id());
        contentValues.put(DB.Results.LATITUDE, poi.getLatitude());
        contentValues.put(DB.Results.LONGITUDE, poi.getLongitude());
        contentValues.put(DB.Results.PHOTO_REFERENCE, poi.getPhotoReference());
        contentValues.put(DB.Results.ICON_URL, poi.getIconUrl());
        contentValues.put(DB.Results.IS_OPEN, poi.getIsOpen());
        contentValues.put(DB.Results.RATING, poi.getRating());
        contentValues.put(DB.Results.WALKING_DURATION_TEXT, poi.getWalkingDurationText());
        contentValues.put(DB.Results.WALKING_DURATION_VALUE, poi.getWalkingDurationValue());
        contentValues.put(DB.Results.DRIVING_DURATION_TEXT, poi.getDrivingDurationText());
        contentValues.put(DB.Results.DRIVING_DURATION_VALUE, poi.getDrivingDurationValue());

        long createdId = dal.insert(DB.Results.TABLE_NAME, contentValues);

        return createdId;
    }

    // Update a POI from results database.
    public long updatePoi(Poi poi) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB.Results.NAME, poi.getName());
        contentValues.put(DB.Results.ADDRESS, poi.getAddress());
        contentValues.put(DB.Results.VICINITY, poi.getVicinity());
        contentValues.put(DB.Results.DISTANCE_TEXT, poi.getDistanceText());
        contentValues.put(DB.Results.DISTANCE_VALUE, poi.getDistanceValue());
        contentValues.put(DB.Results.PLACE_ID, poi.getPlace_id());
        contentValues.put(DB.Results.LATITUDE, poi.getLatitude());
        contentValues.put(DB.Results.LONGITUDE, poi.getLongitude());
        contentValues.put(DB.Results.PHOTO_REFERENCE, poi.getPhotoReference());
        contentValues.put(DB.Results.ICON_URL, poi.getIconUrl());
        contentValues.put(DB.Results.IS_OPEN, poi.getIsOpen());
        contentValues.put(DB.Results.RATING, poi.getRating());
        contentValues.put(DB.Results.WALKING_DURATION_TEXT, poi.getWalkingDurationText());
        contentValues.put(DB.Results.WALKING_DURATION_VALUE, poi.getWalkingDurationValue());
        contentValues.put(DB.Results.DRIVING_DURATION_TEXT, poi.getDrivingDurationText());
        contentValues.put(DB.Results.DRIVING_DURATION_VALUE, poi.getDrivingDurationValue());

        String where = DB.Results.ID + "=" + poi.getId();

        long affectedRows = dal.update(DB.Results.TABLE_NAME, contentValues, where);

        return affectedRows;
    }

    // Delete a POI from results database.
    public long deletePoi(Poi poi) {

        String where = DB.Results.ID + "=" + poi.getId();

        long affectedRows = dal.delete(DB.Results.TABLE_NAME, where);

        return affectedRows;
    }

    // Get pois from result database by definition (where).
    private ArrayList<Poi> getResults(String where) {

        ArrayList<Poi> pois = new ArrayList<>();

        Cursor cursor = dal.getTable(DB.Results.TABLE_NAME, null, where);

        while(cursor.moveToNext()) {

            long id = cursor.getLong(cursor.getColumnIndex(DB.Results.ID));
            String name = cursor.getString(cursor.getColumnIndex(DB.Results.NAME));
            String address = cursor.getString(cursor.getColumnIndex(DB.Results.ADDRESS));
            String vicinity = cursor.getString(cursor.getColumnIndex(DB.Results.VICINITY));
            String distanceText = cursor.getString(cursor.getColumnIndex(DB.Results.DISTANCE_TEXT));
            int distanceValue = cursor.getInt(cursor.getColumnIndex(DB.Results.DISTANCE_VALUE));
            String place_id = cursor.getString(cursor.getColumnIndex(DB.Results.PLACE_ID));
            double latitude = cursor.getDouble(cursor.getColumnIndex(DB.Results.LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndex(DB.Results.LONGITUDE));
            String photoReference = cursor.getString(cursor.getColumnIndex(DB.Results.PHOTO_REFERENCE));
            String iconUrl = cursor.getString(cursor.getColumnIndex(DB.Results.ICON_URL));
            String isOpen = cursor.getString(cursor.getColumnIndex(DB.Results.IS_OPEN));
            double rating = cursor.getDouble(cursor.getColumnIndex(DB.Results.RATING));
            String walkingDurationText = cursor.getString(cursor.getColumnIndex(DB.Results.WALKING_DURATION_TEXT));
            long walkingDurationValue = cursor.getLong(cursor.getColumnIndex(DB.Results.WALKING_DURATION_VALUE));
            String drivingDurationText = cursor.getString(cursor.getColumnIndex(DB.Results.DRIVING_DURATION_TEXT));
            long drivingDurationValue = cursor.getLong(cursor.getColumnIndex(DB.Results.DRIVING_DURATION_VALUE));

            Poi poi = new Poi(id, name, address, vicinity, distanceText, distanceValue, place_id, latitude,
                    longitude, photoReference, iconUrl, isOpen, rating, walkingDurationText,
                    walkingDurationValue, drivingDurationText, drivingDurationValue);

            pois.add(poi);
        }

        cursor.close();

        return pois;
    }

    // Get all pois from result database.
    public ArrayList<Poi> getAllResults() {
        return getResults(null);
    }

}

