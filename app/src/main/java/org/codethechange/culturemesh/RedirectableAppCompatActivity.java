package org.codethechange.culturemesh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cs on 3/28/18.
 */

public abstract class RedirectableAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            if (getIntent().hasExtra(Redirection.LAUNCH_ON_FINISH_EXTRA)) {
                Class<?> nextActivity = (Class) getIntent().getSerializableExtra(Redirection.LAUNCH_ON_FINISH_EXTRA);
                Intent next = new Intent(getApplicationContext(), nextActivity);
                if (getIntent().hasExtra(Redirection.PASS_ON_FINISH_EXTRA)) {
                    next.putExtras(getIntent().getBundleExtra(Redirection.PASS_ON_FINISH_EXTRA));
                }
            }
        }

    }
}
