package com.example.kasey.mymappapp.data;

import android.provider.BaseColumns;

/**
 * Created by Kasey on 11/29/2016.
 */

public final class ShoppingListContract {
    private ShoppingListContract(){}

    public static class ItemEntry implements BaseColumns{
        public static final String TABLE_NAME = "items";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRIORITY = "priority";

        /* constants for priority*/
        public static final boolean PRIORITY_HIGH = true;
        public static final boolean PRIORITY_LOW = false;

    }
    public static class PlaceEntry implements BaseColumns{
        public static final String TABLE_NAME = "places";
        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LATITUDE= "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_ADDRESS_LINE_1 = "addressLine1";
        public static final String COLUMN_ADDRESS_LINE_2 = "addressLine2";


    }
}
