package com.slavafleer.nearpois.db;

/**
 *  Database schema
 */
public class DB {

    public static final String NAME = "nearpois.db";
    public static final int VERSION = 1;

    public static class Results {

        public static final String TABLE_NAME = "Results";

        public static final String ID = "id"; // primary key
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
        public static final String VICINITY = "vicinity";
        public static final String DISTANCE_TEXT = "distanceText";
        public static final String DISTANCE_VALUE = "distanceValue";
        public static final String PLACE_ID = "place_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String PHOTO_REFERENCE = "photoReference";
        public static final String ICON_URL = "iconUrl";
        public static final String IS_OPEN = "getIsOpen";
        public static final String RATING = "rating";
        public static final String WALKING_DURATION_TEXT = "walkingDurationText";
        public static final String WALKING_DURATION_VALUE = "walkingDurationValue";
        public static final String DRIVING_DURATION_TEXT = "drivingDurationText";
        public static final String DRIVING_DURATION_VALUE = "drivingDurationValue";

        // Creation Statement
        public static final String CREATION_STATEMENT = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                ADDRESS + " TEXT, " +
                VICINITY + " TEXT, " +
                DISTANCE_TEXT + " TEXT, " +
                DISTANCE_VALUE + " INTEGER, " +
                PLACE_ID + " TEXT, " +
                LATITUDE + " REAL, " +
                LONGITUDE + " REAL, " +
                PHOTO_REFERENCE + " TEXT, " +
                ICON_URL + " TEXT, " +
                IS_OPEN + " TEXT, " +
                RATING + " TEXT, " +
                WALKING_DURATION_TEXT + " TEXT, " +
                WALKING_DURATION_VALUE + " REAL, " +
                DRIVING_DURATION_TEXT + " TEXT, " +
                DRIVING_DURATION_VALUE + " REAL " + ")";

        // Deletion Statement
        public static final String DELETION_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class Favorites {

        public static final String TABLE_NAME = "Favorites";

        public static final String ID = "id"; // primary key
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
        public static final String VICINITY = "vicinity";
        public static final String DISTANCE_TEXT = "distanceText";
        public static final String DISTANCE_VALUE = "distanceValue";
        public static final String PLACE_ID = "place_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String PHOTO_REFERENCE = "photoReference";
        public static final String ICON_URL = "iconUrl";
        public static final String IS_OPEN = "getIsOpen";
        public static final String RATING = "rating";
        public static final String WALKING_DURATION_TEXT = "walkingDurationText";
        public static final String WALKING_DURATION_VALUE = "walkingDurationValue";
        public static final String DRIVING_DURATION_TEXT = "drivingDurationText";
        public static final String DRIVING_DURATION_VALUE = "drivingDurationValue";

        // Creation Statement
        public static final String CREATION_STATEMENT = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                ADDRESS + " TEXT, " +
                VICINITY + " TEXT, " +
                DISTANCE_TEXT + " TEXT, " +
                DISTANCE_VALUE + " INTEGER, " +
                PLACE_ID + " TEXT, " +
                LATITUDE + " REAL, " +
                LONGITUDE + " REAL, " +
                PHOTO_REFERENCE + " TEXT, " +
                ICON_URL + " TEXT, " +
                IS_OPEN + " TEXT, " +
                RATING + " TEXT, " +
                WALKING_DURATION_TEXT + " TEXT, " +
                WALKING_DURATION_VALUE + " REAL, " +
                DRIVING_DURATION_TEXT + " TEXT, " +
                DRIVING_DURATION_VALUE + " REAL " + ")";

        // Deletion Statement
        public static final String DELETION_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
