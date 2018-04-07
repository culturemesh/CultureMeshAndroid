package org.codethechange.culturemesh;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AboutActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        /*
        I cut out the setSupportActionBar(toolbar) stuff because
        DrawerActivity handles all of that for you (NOTE: The toolbar MUST have an id of
        action_bar.
        */
    }
}
