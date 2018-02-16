package org.codethechange.culturemesh.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Drew Gregory on 2/16/18.
 */

public class PostDbHelper extends SQLiteOpenHelper  {
    private static final String DATABASE_NAME = "posts.db";
    private static final int DATABASE_VERSION = 1;

    public PostDbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + PostDbContract.PostDbEntry.TABLE_NAME + " (" +
                PostDbContract.PostDbEntry._ID + " INTEGER PRIMARY KEY, " +
                PostDbContract.PostDbEntry.COLUMN_NAME_IMG_LINK + " TEXT, " +
                PostDbContract.PostDbEntry.COLUMN_NAME_NETWORK_ID + " INTEGER, " +
                PostDbContract.PostDbEntry.COLUMN_NAME_POST_CLASS + " INTEGER, " +
                PostDbContract.PostDbEntry.COLUMN_NAME_POST_DATE + " TIMESTAMP, " +
                PostDbContract.PostDbEntry.COLUMN_NAME_POST_ORIGINAL + " TEXT, " +
                PostDbContract.PostDbEntry.COLUMN_NAME_TEXT + " TEXT, " +
                PostDbContract.PostDbEntry.COLUMN_NAME_USER_ID + " INTEGER, " +
                PostDbContract.PostDbEntry.COLUMN_NAME_VID_LINK + " TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}
