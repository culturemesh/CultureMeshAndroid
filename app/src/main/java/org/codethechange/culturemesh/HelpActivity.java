package org.codethechange.culturemesh;

/**
 * Show user onboarding screens again as help
 */
public class HelpActivity extends OnboardActivity {
    @Override
    public String getFinishButtonTitle() {
        return getString(R.string.logIn_signUp);
    }

    /**
     * When finish button pressed return user to previous page
     */
    @Override
    public void onFinishButtonPressed() {
        finish();
    }
}
