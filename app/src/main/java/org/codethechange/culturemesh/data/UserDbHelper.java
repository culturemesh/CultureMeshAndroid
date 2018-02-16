package org.codethechange.culturemesh.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Drew Gregory on 2/15/18.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public UserDbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE" +
                UserDbContract.UserDbEntry._ID + " INTEGER PRIMARY KEY, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_ABOUT_ME + " TEXT, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_ACT_CODE + " TEXT, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_CONFIRMED + " INTEGER, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_EMAIL + " TEXT, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_FIRST_NAME + " TEXT, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_FP_CODE + " TEXT, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_GENDER + " TEXT, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_IMAGE_LINK + " TEXT, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_LAST_LOGIN + " TEXT, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_IMAGE_LINK + " TEXT, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_LAST_NAME + " TEXT, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_REGISTER_DATE + " TIMESTAMP, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_ROLE + " INTEGER, " +
                UserDbContract.UserDbEntry.COLUMN_NAME_USERNAME + " TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDbContract.UserDbEntry.TABLE_NAME);
        onCreate(db);
    }
}
