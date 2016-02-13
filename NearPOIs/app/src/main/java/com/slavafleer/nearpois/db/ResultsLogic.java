package com.slavafleer.nearpois.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.slavafleer.nearpois.Poi;

import java.util.ArrayList;

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
        contentValues.put(DB.Results.DISTANCE, poi.getDistance());
        contentValues.put(DB.Results.PLACE_ID, poi.getPlace_id());
        contentValues.put(DB.Results.LATITUDE, poi.getLatitude());
        contentValues.put(DB.Results.LONGITUDE, poi.getLongitude());
        contentValues.put(DB.Results.PHOTO_REFERENCE, poi.getPhotoReference());
        contentValues.put(DB.Results.ICON_URL, poi.getIconUrl());

        long createdId = dal.insert(DB.Results.TABLE_NAME, contentValues);

        return createdId;
    }

    // Update a POI from results database.
    public long updatePoi(Poi poi) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DB.Results.NAME, poi.getName());
        contentValues.put(DB.Results.ADDRESS, poi.getAddress());
        contentValues.put(DB.Results.VICINITY, poi.getVicinity());
        contentValues.put(DB.Results.DISTANCE, poi.getDistance());
        contentValues.put(DB.Results.PLACE_ID, poi.getPlace_id());
        contentValues.put(DB.Results.LATITUDE, poi.getLatitude());
        contentValues.put(DB.Results.LONGITUDE, poi.getLongitude());
        contentValues.put(DB.Results.PHOTO_REFERENCE, poi.getPhotoReference());
        contentValues.put(DB.Results.ICON_URL, poi.getIconUrl());

        String where = DB.Results.ID + "=" + poi.getId();

        long affectedRows = dal.update(DB.Results.TABLE_NAME, contentValues, where);

        return affectedRows;
    }

    // Delete a POI from results database.
    public long deleteFriend(Poi poi) {

        String where = DB.Results.ID + "=" + poi.getId();

        long affectedRows = dal.delete(DB.Results.TABLE_NAME, where);

        return affectedRows;
    }

    // Get pois from result database by definition (where).
    private ArrayList<Poi> getResults(String where) {

        ArrayList<Poi> pois = new ArrayList<>();

        Cursor cursor = dal.getTable(DB.Results.TABLE_NAME, null, where);

        while(cursor.moveToNext()) {

            int idIndex = cursor.getColumnIndex(DB.Results.ID);
            long id = cursor.getLong(idIndex);
            String name = cursor.getString(cursor.getColumnIndex(DB.Results.NAME));
            String address = cursor.getString(cursor.getColumnIndex(DB.Results.ADDRESS));
            String vicinity = cursor.getString(cursor.getColumnIndex(DB.Results.VICINITY));
            double distance = cursor.getDouble(cursor.getColumnIndex(DB.Results.DISTANCE));
            String place_id = cursor.getString(cursor.getColumnIndex(DB.Results.PLACE_ID));
            float latitude = cursor.getFloat(cursor.getColumnIndex(DB.Results.LATITUDE));
            float longitude = cursor.getFloat(cursor.getColumnIndex(DB.Results.LONGITUDE));
            String photoReference = cursor.getString(cursor.getColumnIndex(DB.Results.PHOTO_REFERENCE));
            String iconUrl = cursor.getString(cursor.getColumnIndex(DB.Results.ICON_URL));

            Poi poi = new Poi(id, name, address, vicinity, distance, place_id, latitude,
                    longitude, photoReference, iconUrl);

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

