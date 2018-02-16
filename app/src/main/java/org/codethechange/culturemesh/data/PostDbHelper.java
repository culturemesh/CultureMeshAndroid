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
        //TODO: write SQL CMD
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
