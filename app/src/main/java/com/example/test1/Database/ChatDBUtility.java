package com.example.test1.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.test1.Model.DataResponse;

import java.util.ArrayList;

/**
 * Created by abhay on 09/03/2019.
 */
public class ChatDBUtility {

    public static ChatDBHelper chatDBHelper;

    public ChatDBHelper CreateChatDB(Context context)
    {
        if (chatDBHelper == null) {
            chatDBHelper = new ChatDBHelper(context);
        }

        return chatDBHelper;

    }


    public long AddToDataListDB(ChatDBHelper chatDBHelper, DataResponse dataResponse) {
        // Gets the data repository in write mode
        SQLiteDatabase db = chatDBHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(FeedReaderContract.DataList.COLUMN_NAME_ID, dataResponse.getId());
        values.put(FeedReaderContract.DataList.COLUMN_NAME_URL, dataResponse.getUrl());
        values.put(FeedReaderContract.DataList.COLUMN_NAME_CURRENT_WINDOW, 0);
        values.put(FeedReaderContract.DataList.COLUMN_NAME_DESCRIPTION, dataResponse.getDescription());
        values.put(FeedReaderContract.DataList.COLUMN_NAME_POSITION, 0);
        values.put(FeedReaderContract.DataList.COLUMN_NAME_THUMB, dataResponse.getThumb());
        values.put(FeedReaderContract.DataList.COLUMN_NAME_TITLE, dataResponse.getTitle());



        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedReaderContract.DataList.TABLE_NAME,
                null,
                values);
        return newRowId;
    }


    public ArrayList<DataResponse> GetDataList(ChatDBHelper DBHelper) {
        Cursor cursor = GetRowsDataListDB(DBHelper);

        ArrayList<DataResponse> dataResponse = new ArrayList<DataResponse>();
        DataResponse dataResponse1;

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            dataResponse1 = new DataResponse();
            dataResponse1.setCurrent_window(cursor.getInt(cursor.getColumnIndex(FeedReaderContract.DataList.COLUMN_NAME_CURRENT_WINDOW)));
            dataResponse1.setPosition(cursor.getLong(cursor.getColumnIndex(FeedReaderContract.DataList.COLUMN_NAME_POSITION)));
            dataResponse1.setId(cursor.getString(cursor.getColumnIndex(FeedReaderContract.DataList.COLUMN_NAME_ID)));
            dataResponse1.setDescription(cursor.getString(cursor.getColumnIndex(FeedReaderContract.DataList.COLUMN_NAME_DESCRIPTION)));
            dataResponse1.setThumb(cursor.getString(cursor.getColumnIndex(FeedReaderContract.DataList.COLUMN_NAME_THUMB)));
            dataResponse1.setTitle(cursor.getString(cursor.getColumnIndex(FeedReaderContract.DataList.COLUMN_NAME_TITLE)));
            dataResponse1.setUrl(cursor.getString(cursor.getColumnIndex(FeedReaderContract.DataList.COLUMN_NAME_URL)));



            dataResponse.add(dataResponse1);

            cursor.moveToNext();
        }


        cursor.close();
        return dataResponse;
    }


    Cursor GetRowsDataListDB(ChatDBHelper chatDBHelper) {
        SQLiteDatabase db = chatDBHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.DataList.COLUMN_NAME_URL,
                FeedReaderContract.DataList.COLUMN_NAME_POSITION,
                FeedReaderContract.DataList.COLUMN_NAME_TITLE,
                FeedReaderContract.DataList.COLUMN_NAME_THUMB,
                FeedReaderContract.DataList.COLUMN_NAME_DESCRIPTION,
                FeedReaderContract.DataList.COLUMN_NAME_ID,
                FeedReaderContract.DataList.COLUMN_NAME_CURRENT_WINDOW,


        };

        // How you want the results sorted in the resulting Cursor
        // String sortOrder =
        //
        String whereClause = "";


        Cursor c = db.query(
                FeedReaderContract.DataList.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        return c;
    }

    public void DeleteFromData(ChatDBHelper chatDBHelper) {
        SQLiteDatabase db = chatDBHelper.getWritableDatabase();
        db.execSQL("delete from  " + FeedReaderContract.DataList.TABLE_NAME );
    }

    public void UpdateTime(ChatDBHelper chatDBHelper, long poition, int window_state,String id) {

        SQLiteDatabase db = chatDBHelper.getWritableDatabase();


        String strSQL = "UPDATE " + FeedReaderContract.DataList.TABLE_NAME + " Set " + FeedReaderContract.DataList.COLUMN_NAME_POSITION + " = " + poition +
                " , "+FeedReaderContract.DataList.COLUMN_NAME_CURRENT_WINDOW +" = "+ window_state +
                " where " + FeedReaderContract.DataList.COLUMN_NAME_ID + " = '" + id +"'";



        db.execSQL(strSQL);


    }

}
