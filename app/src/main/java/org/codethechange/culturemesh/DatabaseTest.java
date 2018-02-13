package org.codethechange.culturemesh;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.codethechange.culturemesh.data.NetworkDbContract;
import org.codethechange.culturemesh.data.NetworkDbHelper;

import java.math.BigInteger;

public class DatabaseTest extends AppCompatActivity {

    SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NetworkDbHelper helper = new NetworkDbHelper(getApplicationContext(), null);
        /*mDb = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NetworkDbContract.NetworkDbEntry._ID, new Long(21342424));
        cv.put(NetworkDbContract.NetworkDbEntry.COLUMN_NAME_IMAGE_LINK, "link here");
        cv.put(NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LANGUAGE_ORIGIN_ID, new Long(1235));
        cv.put(NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LOCATION_CURR_CITY, new Long(3948));
        cv.put(NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LOCATION_CURR_COUNTRY, new Long(239));
        cv.put(NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LOCATION_CURR_REGION, new Long(589));
        cv.put(NetworkDbContract.NetworkDbEntry.COLUMN_NAME_NETWORK_CLASS, new Long(1));
        long id = mDb.insert(NetworkDbContract.NetworkDbEntry.TABLE_NAME, null, cv);
        Log.i("DatabaseId", id + "");*/

        mDb = helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NetworkDbContract.NetworkDbEntry._ID,
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_IMAGE_LINK,
                NetworkDbContract.NetworkDbEntry.COLUMN_NAME_LOCATION_CURR_REGION
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = NetworkDbContract.NetworkDbEntry.COLUMN_NAME_DATE_ADDED + " = ?";
        String[] selectionArgs = { "date_added" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                NetworkDbContract.NetworkDbEntry._ID + " DESC";

        Cursor cursor = mDb.query(
                NetworkDbContract.NetworkDbEntry.TABLE_NAME,                    // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        cursor.moveToFirst();
        Log.i("DB Read", cursor.getString(cursor.getColumnIndexOrThrow(NetworkDbContract.NetworkDbEntry.COLUMN_NAME_IMAGE_LINK)));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
