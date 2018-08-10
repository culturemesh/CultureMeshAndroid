package org.codethechange.culturemesh;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * This serves as a landing page for when the app is started from scratch. It does some
 * initialization and then redirects the user to an appropriate activity.
 */
public class ApplicationStart extends Application {

    /**
     * Initialize Crashyltics and redirect user to {@link TimelineActivity} if they have a selected
     * network saved. If not, they are directed to {@link ExploreBubblesOpenGLActivity} to choose
     * a new network. If they aren't even signed in yet, they are sent to {@link OnboardActivity}.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        SharedPreferences settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
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
}
