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
        public static final String DISTANCE = "distance";
        public static final String PLACE_ID = "place_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String PHOTO_REFERENCE = "photoReference";
        public static final String ICON_URL = "iconUrl";
        public static final String IS_OPEN = "getIsOpen";
        public static final String RATING = "rating";

        // Creation Statement
        public static final String CREATION_STATEMENT = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                ADDRESS + " TEXT, " +
                VICINITY + " TEXT, " +
                DISTANCE + " REAL, " +
                PLACE_ID + " TEXT, " +
                LATITUDE + " REAL, " +
                LONGITUDE + " REAL, " +
                PHOTO_REFERENCE + " TEXT, " +
                ICON_URL + " TEXT, " +
                IS_OPEN +
                RATING + ")";

        // Deletion Statement
        public static final String DELETION_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
