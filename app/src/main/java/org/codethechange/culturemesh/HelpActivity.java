package org.codethechange.culturemesh;

/**
 * Show user onboarding screens again as help
 */
public class HelpActivity extends OnboardActivity {
    @Override
    public String getFinishButtonTitle() {
        return getString(R.string.exit);
    }

    /**
     * When finish button pressed return user to previous page
     */
    @Override
    public void onFinishButtonPressed() {
        finish();
        // TODO: BUG: Should return to drawer on Timeline Activity but launches PickOnboardingStatus
    }
}
