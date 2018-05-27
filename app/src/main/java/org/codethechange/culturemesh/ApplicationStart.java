package org.codethechange.culturemesh;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class ApplicationStart extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        SharedPreferences settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
        new TestDatabase().execute();
        if (LoginActivity.isLoggedIn(settings)) {
            if (settings.contains(API.SELECTED_NETWORK)) {
                Intent start = new Intent(getApplicationContext(), TimelineActivity.class);
                startActivity(start);
            } else {
                Intent start = new Intent(getApplicationContext(), ExploreBubblesOpenGLActivity.class);
                startActivity(start);
            }
        } else {
            Intent start = new Intent(getApplicationContext(), OnboardActivity.class);
            startActivity(start);
        }
    }

    //TODO: Run new TestDatabase().execute() once to load dummy data in!
    private class TestDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            API.loadAppDatabase(getApplicationContext());
            if (API.Get.network(1).getPayload() == null) {
                API.addReplies();
                API.addUsers();
                API.addCities();
                API.addCountries();
                API.addNetworks();
                API.addRegions();
                API.addPosts();
                API.addEvents();
                API.subscribeUsers();
            }
            API.closeDatabase();
            return null;
        }
    }
}
