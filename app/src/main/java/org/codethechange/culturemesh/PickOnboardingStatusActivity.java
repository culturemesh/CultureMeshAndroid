package org.codethechange.culturemesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PickOnboardingStatusActivity extends AppCompatActivity {

    /**
     * When Activity created, display the layout file
     * @param savedInstanceState Saved state to restore
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_onboarding_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * If button for a returning user tapped, send user to their Timeline
     * @param v View the user tapped the button on
     */
    public void tapReturning(View v) {
        Intent start = new Intent(getApplicationContext(), TimelineActivity.class);
        startActivity(start);
    }

    /**
     * If button for a new user tapped, send user to the onboarding activity
     * @param v View the user tapped the button on
     */
    public void tapNew(View v) {
        Intent start = new Intent(getApplicationContext(), OnboardActivity.class);
        startActivity(start);
    }

    /**
     * If button for a returning user with no networks tapped, send user to explore networks
     * @param v View the user tapped the button on
     */
    public void tapNoNetworks(View v) {
        Intent start = new Intent(getApplicationContext(), ExploreBubblesOpenGLActivity.class);
        startActivity(start);
    }

}
