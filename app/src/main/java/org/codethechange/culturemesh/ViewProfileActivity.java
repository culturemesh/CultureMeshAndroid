package org.codethechange.culturemesh;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ViewProfileActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = findViewById(R.id.contributions_pager);
        PagerAdapter mPagerAdapter = new ContributionsPager(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout = findViewById(R.id.contributions_tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * This PagerAdapter returns the correct fragment based on which list the user wishes to see.
     * This could be seeing the list of networks the user is subscribed to, the list of posts
     * the user has written, or the list of events the user has attended.
     */
    class ContributionsPager extends FragmentPagerAdapter {

        ContributionsPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return null;
                case 1:
                    return null;
                default:

            }
            return null;
        }

        @Override
        public int getCount() {
            //We have three sections: Posts, Events, and Networks.
            return 3;
        }
    }

}
