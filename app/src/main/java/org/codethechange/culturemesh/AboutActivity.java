package org.codethechange.culturemesh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    /**
     * Open {@link Acknowledgements} activity to display legally required attributions for the
     * open-source libraries we use
     * @param v The {@link View} of the button clicked on to run this method. Not used.
     */
    public void openLegal(View v) {
        Intent openLegal = new Intent(AboutActivity.this, Acknowledgements.class);
        startActivity(openLegal);
    }
}
