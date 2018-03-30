package org.codethechange.culturemesh;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Drew Gregory on 2/23/18.
 */

public class CultureMeshApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}
