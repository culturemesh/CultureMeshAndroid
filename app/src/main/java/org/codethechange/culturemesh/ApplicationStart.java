package org.codethechange.culturemesh;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * This serves as a landing page for when the app is started from scratch. It does some
 * initialization.
 */
public class ApplicationStart extends Application {

    /**
     * Initialize Crashyltics.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}
