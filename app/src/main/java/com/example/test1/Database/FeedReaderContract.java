package com.example.test1.Database;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {
    }

    /* Inner class that defines the table contents */


    public static abstract class DataList implements BaseColumns {
        public static final String TABLE_NAME = "data_list";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_THUMB = "thumb";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CURRENT_WINDOW = "current_window";
        public static final String COLUMN_NAME_POSITION = "position";
    }




}