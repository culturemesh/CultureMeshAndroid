package org.codethechange.culturemesh.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Drew Gregory on 2/13/18.
 */

public class NetworkDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "networks.db";
    private static final int DATABASE_VERSION = 3;

    public NetworkDbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " +
                NetworkDbContract.NetworkDbEntry.TABLE_NAME + " (" +
                NetworkDbContract.NetworkDbEntry._ID + " INTEGER PRIMARY KEY, " +
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LANGUAGE_ORIGIN_ID + " INTEGER, " +
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LOCATION_CURR_CITY + " INTEGER, " +
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LOCATION_CURR_REGION + " INTEGER, " +
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LOCATION_CURR_COUNTRY + " INTEGER, " +
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LOCATION_ORIGIN_CITY + " INTEGER, " +
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LOCATION_ORIGIN_REGION + " INTEGER, " +
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LOCATION_ORIGIN_COUNTRY + "  INTEGER, " +
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_DATE_ADDED + " TEXT, " +
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_IMAGE_LINK + " TEXT, " +
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_NETWORK_CLASS + " INTEGER);";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NetworkDbContract.NetworkDbEntry.TABLE_NAME);
        onCreate(db);
    }
}
