package com.culturemesh.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Transparent {@link android.app.Activity} that is the default Activity. It is the one launched
 * when the app first starts, and it is the farthest back the "back" button (on the phone, not in
 * the app) can go before leaving the app. It redirects the user based on their onboarding and login
 * status.
 */
public class StartActivity extends AppCompatActivity {

    /**
     * Whenever this screen becomes "visible", immediately redirect the user to
     * {@link TimelineActivity} if they have a selected network and are logged in. If they are
     * logged-in without a selected network, redirect them to {@link ExploreBubblesOpenGLActivity}.
     * If they are logged-out, redirect them to {@link OnboardActivity}.
     */
    @Override
    protected void onResume() {
        super.onResume();
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
