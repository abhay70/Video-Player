package com.example.test1.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rmadan on 5/12/2015.
 */
public class ChatDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BOFReader.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String LONG_TYPE = " LONG ";

    private static final String COMMA_SEP = ",";
    public int new_version = 0;
    public int old_version = 0;
    public ChatDBUtility chatDBUtility;
    public static final String SQL_CREATE_FAILED_URC_TABLE =
            "CREATE TABLE " + FeedReaderContract.DataList.TABLE_NAME + " (" +
                    FeedReaderContract.DataList.COLUMN_NAME_ID + INTEGER_TYPE  + "PRIMARY KEY" + COMMA_SEP +
                    FeedReaderContract.DataList.COLUMN_NAME_DESCRIPTION + TEXT_TYPE  + COMMA_SEP +
                    FeedReaderContract.DataList.COLUMN_NAME_THUMB + TEXT_TYPE  + COMMA_SEP +
                    FeedReaderContract.DataList.COLUMN_NAME_TITLE + TEXT_TYPE  + COMMA_SEP +
                    FeedReaderContract.DataList.COLUMN_NAME_POSITION + LONG_TYPE  + COMMA_SEP +
                    FeedReaderContract.DataList.COLUMN_NAME_CURRENT_WINDOW + INTEGER_TYPE  + COMMA_SEP +
                    FeedReaderContract.DataList.COLUMN_NAME_URL + TEXT_TYPE +
                    " )";



    public ChatDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {

        System.out.println(SQL_CREATE_FAILED_URC_TABLE);
        db.execSQL(SQL_CREATE_FAILED_URC_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over


    }






    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }









}

