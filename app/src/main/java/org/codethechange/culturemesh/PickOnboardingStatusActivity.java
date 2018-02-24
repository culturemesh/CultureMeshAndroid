package org.codethechange.culturemesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PickOnboardingStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_onboarding_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void tapReturning(View v) {
        Intent start = new Intent(getApplicationContext(), TimelineActivity.class);
        startActivity(start);
    }

    public void tapNew(View v) {
        Intent start = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(start);
    }

    public void tapNoNetworks(View v) {
        Intent start = new Intent(getApplicationContext(), ExploreBubblesOpenGLActivity.class);
        startActivity(start);
    }

}
