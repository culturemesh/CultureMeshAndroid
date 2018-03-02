package org.codethechange.culturemesh;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Introduce user to the app through a series of informational screens that end with a button
 * that redirects the user to a login page
 */
public class OnboardActivity extends AhoyOnboarderActivity {

    /**
     * Generate onboarding pages and display them
     * @param savedInstanceState Previous state to restore from
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(makeCard(getString(R.string.welcome), getString(R.string.cultureMesh_description),
                R.drawable.logo_header, 1000, 1000));
        pages.add(makeCard(getString(R.string.networks), getString(R.string.networks_explain),
                R.drawable.ic_people_outline_white_24px, 1000, 1000));
        pages.add(makeCard(getString(R.string.ready_question), getString(R.string.lets_start),
                R.drawable.ic_public_black_24dp, 1000, 1000));

        setGradientBackground();

        setFinishButtonTitle(getString(R.string.logIn_signUp));
        showNavigationControls(true);

        setOnboardPages(pages);
    }

    /**
     * Create an onboarding screen for AhoyOnboarder
     * @param title Title to display on screen
     * @param description Description to display on screen
     * @param iconID Resource ID of the icon to display on the screen
     * @return Card to pass to AhoyOnboarder to display
     */
    private AhoyOnboarderCard makeCard(String title, String description, int iconID) {
        AhoyOnboarderCard card = new AhoyOnboarderCard(title, description, iconID);
        card.setBackgroundColor(R.color.black_transparent);
        card.setTitleColor(R.color.white);
        card.setDescriptionColor(R.color.grey_200);
        return card;
    }

    /**
     * Create an onboarding screen for AhoyOnboarder
     * @param title Title to display on screen
     * @param description Description to display on screen
     * @param iconID Resource ID of the icon to display on the screen
     * @param iconWidth Width of icon
     * @param iconHeight Height of icon
     * @return Card to pass to AhoyOnboarder to display
     */
    private AhoyOnboarderCard makeCard(String title, String description, int iconID, int iconWidth,
                                       int iconHeight) {
        AhoyOnboarderCard card = makeCard(title, description, iconID);

        // SOURCE: https://stackoverflow.com/questions/1016896/get-screen-dimensions-in-pixels
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int horizMargin = height / 2 - iconHeight;
        int vertMargin  = width  / 2 - iconWidth;

        card.setIconLayoutParams(iconWidth, iconHeight, horizMargin, vertMargin, vertMargin, horizMargin);
        return card;
    }

    /**
     * When finish button pressed at end of onboarding, send user to login page
     */
    @Override
    public void onFinishButtonPressed() {
        Intent start = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(start);
    }

}