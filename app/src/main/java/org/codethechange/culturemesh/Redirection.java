package org.codethechange.culturemesh;

/**
 * Created by cs on 3/28/18.
 */

public class Redirection {
    /**
     * When creating intents, extras with these keys can be used to pass information to the
     * Activity being launched regarding what to do when the Activity finishes
     */

    // The Class<?> of the Activity to launch when finishing
    static final String LAUNCH_ON_FINISH_EXTRA = "launchOnFinish";
    // A Bundle whose contents will be passed as extras via the Intent called on finishing
    static final String PASS_ON_FINISH_EXTRA = "passOnFinish";
}
