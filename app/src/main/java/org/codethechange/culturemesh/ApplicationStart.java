package org.codethechange.culturemesh;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

public class ApplicationStart extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
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
