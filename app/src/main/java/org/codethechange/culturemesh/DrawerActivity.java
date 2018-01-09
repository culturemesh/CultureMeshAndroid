package org.codethechange.culturemesh;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;

import org.codethechange.culturemesh.models.Network;

import java.util.HashSet;
import java.util.Set;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout fullLayout;
    protected FrameLayout frameLayout;
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;

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
        Network network = API.genNetworks().get(0);
        Menu navMenu = navView.getMenu();
        MenuItem item = navMenu.getItem(2); //Your Networks subItem
        Log.i("netMenu", item.getTitle().toString());
        SubMenu netMenu = item.getSubMenu();
        Set<String> dummySubNets = new HashSet<>();
        for (Network net : API.genNetworks()) {
            String name = "";
            if (net.isLocationNetwork()) {
                name = getResources().getString(R.string.from) + " " +
                        network.getFromLocation().shortName() + " " +
                        getResources().getString(R.string.near) + " " +
                        network.getNearLocation().shortName();
            } else {
                name = network.getLang().toString() + " " +
                        getResources().getString(R.string.speakers_in) + " " +
                        network.getNearLocation().shortName();
            }
            dummySubNets.add(name);
            dummySubNets.add("From Indonesia Near San Francisco");
            dummySubNets.add("From New York City Near Stanford");
        }
        Set<String> subNets = dummySubNets;
        //TODO: Replace Dummy Set of Strings with real subscribed networks.
        for (String name : subNets) {
            SpannableStringBuilder sb = new SpannableStringBuilder(name);
            sb.setSpan(new RelativeSizeSpan(.8f), 0, sb.length(), 0);
            netMenu.add(sb);
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
        Log.i("Menu Item", "Menu Item Clicked");
        if (id == R.id.nav_explore) {
            Intent startExplore = new Intent(getApplicationContext(), ExploreListActivity.class);
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
