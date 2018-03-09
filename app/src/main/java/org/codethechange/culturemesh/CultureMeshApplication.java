package org.codethechange.culturemesh;

import android.app.Application;

/**
 * Created by Drew Gregory on 2/23/18.
 */

public class CultureMeshApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        API.loadAppDatabase(getApplicationContext());
    }
}
