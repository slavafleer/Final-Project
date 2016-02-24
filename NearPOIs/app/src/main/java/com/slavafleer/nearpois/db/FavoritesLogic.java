package com.slavafleer.nearpois.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.slavafleer.nearpois.Poi;

import java.util.ArrayList;

// Logic Class that implements DB methods
public class FavoritesLogic extends BaseLogic {

    public FavoritesLogic(Activity activity) {
        super(activity);
    }

    // Add new POI to Favorites database.
    public long addPoi(Poi poi) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB.Favorites.NAME, poi.getName());
        contentValues.put(DB.Favorites.ADDRESS, poi.getAddress());
        contentValues.put(DB.Favorites.VICINITY, poi.getVicinity());
        contentValues.put(DB.Favorites.DISTANCE_TEXT, poi.getDistanceText());
        contentValues.put(DB.Favorites.DISTANCE_VALUE, poi.getDistanceValue());
        contentValues.put(DB.Favorites.PLACE_ID, poi.getPlace_id());
        contentValues.put(DB.Favorites.LATITUDE, poi.getLatitude());
        contentValues.put(DB.Favorites.LONGITUDE, poi.getLongitude());
        contentValues.put(DB.Favorites.PHOTO_REFERENCE, poi.getPhotoReference());
        contentValues.put(DB.Favorites.ICON_URL, poi.getIconUrl());
        contentValues.put(DB.Favorites.IS_OPEN, poi.getIsOpen());
        contentValues.put(DB.Favorites.RATING, poi.getRating());
        contentValues.put(DB.Favorites.WALKING_DURATION_TEXT, poi.getWalkingDurationText());
        contentValues.put(DB.Favorites.WALKING_DURATION_VALUE, poi.getWalkingDurationValue());
        contentValues.put(DB.Favorites.DRIVING_DURATION_TEXT, poi.getDrivingDurationText());
        contentValues.put(DB.Favorites.DRIVING_DURATION_VALUE, poi.getDrivingDurationValue());

        long createdId = dal.insert(DB.Favorites.TABLE_NAME, contentValues);

        return createdId;
    }

    // Update a POI from Favorites database.
    public long updatePoi(Poi poi) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB.Favorites.NAME, poi.getName());
        contentValues.put(DB.Favorites.ADDRESS, poi.getAddress());
        contentValues.put(DB.Favorites.VICINITY, poi.getVicinity());
        contentValues.put(DB.Favorites.DISTANCE_TEXT, poi.getDistanceText());
        contentValues.put(DB.Favorites.DISTANCE_VALUE, poi.getDistanceValue());
        contentValues.put(DB.Favorites.PLACE_ID, poi.getPlace_id());
        contentValues.put(DB.Favorites.LATITUDE, poi.getLatitude());
        contentValues.put(DB.Favorites.LONGITUDE, poi.getLongitude());
        contentValues.put(DB.Favorites.PHOTO_REFERENCE, poi.getPhotoReference());
        contentValues.put(DB.Favorites.ICON_URL, poi.getIconUrl());
        contentValues.put(DB.Favorites.IS_OPEN, poi.getIsOpen());
        contentValues.put(DB.Favorites.RATING, poi.getRating());
        contentValues.put(DB.Favorites.WALKING_DURATION_TEXT, poi.getWalkingDurationText());
        contentValues.put(DB.Favorites.WALKING_DURATION_VALUE, poi.getWalkingDurationValue());
        contentValues.put(DB.Favorites.DRIVING_DURATION_TEXT, poi.getDrivingDurationText());
        contentValues.put(DB.Favorites.DRIVING_DURATION_VALUE, poi.getDrivingDurationValue());

        String where = DB.Favorites.ID + "=" + poi.getId();

        long affectedRows = dal.update(DB.Favorites.TABLE_NAME, contentValues, where);

        return affectedRows;
    }

    // Delete a POI from Favorites database.
    public long deletePoi(Poi poi) {

        String where = DB.Favorites.ID + "=" + poi.getId();

        long affectedRows = dal.delete(DB.Favorites.TABLE_NAME, where);

        return affectedRows;
    }

    // Get pois from result database by definition (where).
    private ArrayList<Poi> getPois(String where) {

        ArrayList<Poi> pois = new ArrayList<>();

        Cursor cursor = dal.getTable(DB.Favorites.TABLE_NAME, null, where);

        while(cursor.moveToNext()) {

            long id = cursor.getLong(cursor.getColumnIndex(DB.Favorites.ID));
            String name = cursor.getString(cursor.getColumnIndex(DB.Favorites.NAME));
            String address = cursor.getString(cursor.getColumnIndex(DB.Favorites.ADDRESS));
            String vicinity = cursor.getString(cursor.getColumnIndex(DB.Favorites.VICINITY));
            String distanceText = cursor.getString(cursor.getColumnIndex(DB.Favorites.DISTANCE_TEXT));
            int distanceValue = cursor.getInt(cursor.getColumnIndex(DB.Favorites.DISTANCE_VALUE));
            String place_id = cursor.getString(cursor.getColumnIndex(DB.Favorites.PLACE_ID));
            double latitude = cursor.getDouble(cursor.getColumnIndex(DB.Favorites.LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndex(DB.Favorites.LONGITUDE));
            String photoReference = cursor.getString(cursor.getColumnIndex(DB.Favorites.PHOTO_REFERENCE));
            String iconUrl = cursor.getString(cursor.getColumnIndex(DB.Favorites.ICON_URL));
            String isOpen = cursor.getString(cursor.getColumnIndex(DB.Favorites.IS_OPEN));
            double rating = cursor.getDouble(cursor.getColumnIndex(DB.Favorites.RATING));
            String walkingDurationText = cursor.getString(cursor.getColumnIndex(DB.Favorites.WALKING_DURATION_TEXT));
            long walkingDurationValue = cursor.getLong(cursor.getColumnIndex(DB.Favorites.WALKING_DURATION_VALUE));
            String drivingDurationText = cursor.getString(cursor.getColumnIndex(DB.Favorites.DRIVING_DURATION_TEXT));
            long drivingDurationValue = cursor.getLong(cursor.getColumnIndex(DB.Favorites.DRIVING_DURATION_VALUE));

            Poi poi = new Poi(id, name, address, vicinity, distanceText, distanceValue, place_id, latitude,
                    longitude, photoReference, iconUrl, isOpen, rating, walkingDurationText,
                    walkingDurationValue, drivingDurationText, drivingDurationValue);

            pois.add(poi);
        }

        cursor.close();

        return pois;
    }

    // Get all pois from result database.
    public ArrayList<Poi> getAllPois() {
        return getPois(null);
    }

    // Delete all saved Favorites from DB
    public void deleteAllPois() {

        ArrayList<Poi> pois = getAllPois();

        for(Poi poi : pois) {
            deletePoi(poi);
        }
    }
}

