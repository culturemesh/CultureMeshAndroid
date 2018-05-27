package org.codethechange.culturemesh;

import android.os.Bundle;

/**
 * Activity for displaying author attributions, copyright notices, and version information on an
 * {@code About} page
 */
public class AboutActivity extends DrawerActivity {

    /**
     * When the activity is created, it pulls what to display from {@link R.layout#activity_about}.
     * It does not have a {@code setSupportActionBar(toolbar)} call because that is handled by
     * {@link DrawerActivity}. The toolbar MUST have an ID of {@code action_bar}.
     * @param savedInstanceState Passed to superclass onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
