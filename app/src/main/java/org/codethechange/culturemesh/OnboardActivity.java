package org.codethechange.culturemesh;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class OnboardActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Move strings to string resources
        // TODO: Document
        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(makeCard("Welcome", "Welcome to CultureMesh!", R.drawable.logo_header, 1000, 1000));
        pages.add(makeCard("Introduction", "Join networks to learn about events and people near you.", R.drawable.ic_people_outline_white_24px));
        pages.add(makeCard("Start", "Let's to get Started!", R.drawable.ic_arrow_forward_white_24px));

        setGradientBackground();

        setFinishButtonTitle("Log In or Sign Up");
        showNavigationControls(true);

        setOnboardPages(pages);
    }

    private AhoyOnboarderCard makeCard(String title, String description, int iconID) {
        AhoyOnboarderCard card = new AhoyOnboarderCard(title, description, iconID);
        card.setBackgroundColor(R.color.black_transparent);
        card.setTitleColor(R.color.white);
        card.setDescriptionColor(R.color.grey_200);
        return card;
    }

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

    @Override
    public void onFinishButtonPressed() {
        Intent start = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(start);
    }

}
