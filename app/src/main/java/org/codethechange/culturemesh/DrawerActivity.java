package org.codethechange.culturemesh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.codethechange.culturemesh.models.Network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout fullLayout;
    protected FrameLayout frameLayout;
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected SparseArray<Network> subscribedNetworks;
    final static String USER_PREFS = "userprefs";
    final static String USER_NAME = "username";

    @Override
    public void setContentView(int layoutResID) {
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer, null);
        frameLayout = fullLayout.findViewById(R.id.drawer_frame);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(fullLayout);

        //All drawer activities must have a toolbar with id "action_bar!"
        Toolbar mToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(mToolbar);
        //Set Up Navigation Drawer
        //Setup Navigation Drawer Layout
        mDrawerLayout= findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        NavigationView navView = findViewById(R.id.nav_view);
        SharedPreferences userPrefs = getSharedPreferences(USER_PREFS, MODE_PRIVATE);
        if (userPrefs.getString(USER_NAME, null) == null) {
            //User is not signed in. Replace user info with sign in button
            Button button = findViewById(R.id.nav_user_sign_in_button);

            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Open LogInActivity
                    Intent logInIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(logInIntent);
                }
            });

        }


        Network network = API.genNetworks().get(0);
        Menu navMenu = navView.getMenu();
        MenuItem item = navMenu.getItem(2); //Your Networks subItem
        Log.i("netMenu", item.getTitle().toString());
        SubMenu netMenu = item.getSubMenu();
        //Instantiate map with key -> menu view id, value -> network.
        subscribedNetworks = new SparseArray<Network>();
        //TODO: Replace dummy networks with real stuff.
        ArrayList<Network> networks = API.genNetworks();
        for (Network net : networks) {
            String name = "";
            if (net.isLocationNetwork()) {
                name = getResources().getString(R.string.from) + " " +
                        net.getFromLocation().shortName() + " " +
                        getResources().getString(R.string.near) + " " +
                        net.getNearLocation().shortName();
            } else {
                name = net.getLang().toString() + " " +
                        getResources().getString(R.string.speakers_in) + " " +
                        net.getNearLocation().shortName();
            }
            int viewId = View.generateViewId();
            subscribedNetworks.put(viewId, net);
            SpannableStringBuilder sb = new SpannableStringBuilder(name);
            sb.setSpan(new RelativeSizeSpan(.8f), 0, sb.length(), 0);
            netMenu.add(Menu.NONE, viewId, 0, sb);
        }
        navView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //TODO: Handle navigation view item clicks here.
        int id = item.getItemId();
        Network subNet  = subscribedNetworks.get(id, null);
        if (subNet != null) {
            //The user tapped a subscribed network. We will now restart TimeLineActivity for that
            //network.
            //TODO: Set up way to pass network as data point for timelineactivity.
            Intent toTimeline = new Intent(getApplicationContext(), TimelineActivity.class);
            startActivity(toTimeline);
        }
        if (id == R.id.nav_explore) {
            Intent startExplore = new Intent(getApplicationContext(), ExploreBubblesOpenGLActivity.class);
            startActivity(startExplore);
        } else if (id == R.id.nav_join_network) {
            Intent startFindNet = new Intent(getApplicationContext(), FindNetworkActivity.class);
            startActivity(startFindNet);
        } else if (id == R.id.nav_manage) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        finish();
        return true;
    }

}
