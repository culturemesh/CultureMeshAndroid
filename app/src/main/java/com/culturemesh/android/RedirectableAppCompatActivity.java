package com.culturemesh.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Superclass for all classes that support redirection instructions from the activity they are
 * launched from. For instance, if {@code A} launches {@code B}, which is a subclass of
 * {@link RedirectableAppCompatActivity}, {@code A} can give {@code B} instructions to launch
 * {@code C} when it finishes. If instead {@code Z} launches {@code B}, it can give {@code B}
 * instructions to next launch {@code X}.
 */
public abstract class RedirectableAppCompatActivity extends AppCompatActivity {

    /**
     * {@inheritDoc}
     * Also uses the extras in the launching {@link Intent} to decide which Activity to launch next
     * @see Redirection
     */
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
