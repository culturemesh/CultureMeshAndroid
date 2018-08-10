package org.codethechange.culturemesh;

import android.content.Intent;

/**
 * Classes that extend this one can be sent information when launched regarding where the user
 * should be directed next.
 */
public class Redirection {

    /**
     * Key in {@link android.content.Intent}'s extras whose argument specifies the Class of the
     * Activity to launch when finishing
     * @see Intent#getExtras()
     */
    static final String LAUNCH_ON_FINISH_EXTRA = "launchOnFinish";

    /**
     * Key in {@link android.content.Intent}'s extras whose argument specifies a
     * {@link android.os.Bundle} whose contents will be passed as extras via the Intent called on
     * finishing
     */
    static final String PASS_ON_FINISH_EXTRA = "passOnFinish";
}
