package org.codethechange.culturemesh;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * A {@link DrawerActivity} that displays legally required attributions for the open-source code we
 * use.
 */
public class Acknowledgements extends DrawerActivity {

    /**
     * Link the activity to its layout specified in {@link R.layout#activity_acknowledgements}
     * @param savedInstanceState {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acknowledgements);
    }
}
