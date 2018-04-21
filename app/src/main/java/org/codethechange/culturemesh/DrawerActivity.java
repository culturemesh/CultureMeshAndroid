package org.codethechange.culturemesh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout fullLayout;
    protected FrameLayout frameLayout;
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected SparseArray<Network> subscribedNetworks;
    protected Set<Long> subscribedNetworkIds;
    NavigationView navView;
    protected long currentUser;
    Activity thisActivity = this;

    public interface WaitForSubscribedList {
        void onSubscribeListFinish();
    }

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
        navView = findViewById(R.id.nav_view);
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
        subscribedNetworkIds = new HashSet<>();
        SharedPreferences settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
        currentUser = settings.getLong(API.CURRENT_USER, -1);
        if (currentUser == -1) {
            //User is not signed in. Replace user info with sign in button
            Button button = navView.getHeaderView(0).findViewById(R.id.nav_user_sign_in_button);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Open LogInActivity
                    Intent logInIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(logInIntent);
                }
            });
        } else {
            //Load User info.
            new LoadUserInfo().execute(currentUser);
            new LoadUserSubscriptions().execute(currentUser);
        }
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //TODO: Handle navigation view item clicks here.
        int id = item.getItemId();
        Network subNet  = subscribedNetworks.get(id, null);
        if (subNet != null) {
            //The user tapped a subscribed network. We will now restart TimeLineActivity for that
            //network.
            //Pass network as data point for timelineactivity by putting it in sharedprefs.
            getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE).edit()
                    .putLong(API.SELECTED_NETWORK, subNet.id).apply();
            Intent toTimeline = new Intent(getApplicationContext(), TimelineActivity.class);
            startActivity(toTimeline);
            finish();
        }
        if (id == R.id.nav_explore) {
            Intent startExplore = new Intent(getApplicationContext(), ExploreBubblesOpenGLActivity.class);
            startActivity(startExplore);
        } else if (id == R.id.nav_join_network) {
            Intent startFindNet = new Intent(getApplicationContext(), FindNetworkActivity.class);
            startActivity(startFindNet);
        } else if (id == R.id.nav_about) {
            Intent startAbout = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(startAbout);
        } else if (id == R.id.nav_help) {
            Intent startHelp = new Intent(getApplicationContext(), HelpActivity.class);
            startActivity(startHelp);
        } else if (id == R.id.nav_settings) {
            Intent startHelp = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(startHelp);
        } else if (id == R.id.nav_logout) {
            SharedPreferences settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
            LoginActivity.setLoggedOut(settings);
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        finish();
        return true;
    }

    private class LoadUserSubscriptions extends AsyncTask<Long, Void, Void>{

        @Override
        protected Void doInBackground(Long... longs) {
            API.loadAppDatabase(getApplicationContext());
            List<Network> networks = API.Get.userNetworks(longs[0], 10).getPayload();
            subscribedNetworks = new SparseArray<Network>();

            //Instantiate map with key -> menu view id, value -> network.
            for (Network net : networks) {
                subscribedNetworkIds.add(net.id);
                int viewId = View.generateViewId();
                subscribedNetworks.put(viewId, net);
            }
            API.closeDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            Menu navMenu = navView.getMenu();
            MenuItem item = navMenu.getItem(2); //Your Networks subItem
            SubMenu netMenu = item.getSubMenu();
            for (int i = 0; i < subscribedNetworks.size(); i++) {
                int id = subscribedNetworks.keyAt(i);
                Network net = subscribedNetworks.get(id);
                String name = "";
                if (net.networkClass) {
                    name = getResources().getString(R.string.from) + " " +
                            net.fromLocation.shortName() + " " +
                            getResources().getString(R.string.near) + " " +
                            net.nearLocation.shortName();
                } else {
                    name = net.language.toString() + " " +
                            getResources().getString(R.string.speakers_in) + " " +
                            net.nearLocation.shortName();
                }
                SpannableStringBuilder sb = new SpannableStringBuilder(name);
                sb.setSpan(new RelativeSizeSpan(.8f), 0, sb.length(), 0);
                netMenu.add(Menu.NONE, id, 0, sb);
            }
            navView.setNavigationItemSelectedListener(DrawerActivity.this);
            Log.i("About to test", "for instance of waitforsubscribedlist");
            if (thisActivity instanceof WaitForSubscribedList) {
                Log.i("This happens", "Instance works!");
                ((WaitForSubscribedList) thisActivity).onSubscribeListFinish();
            }
        }
    }

    private class LoadUserInfo extends AsyncTask<Long, Void, NetworkResponse<User>> {

        @Override
        protected NetworkResponse<User> doInBackground(Long... longs) {
            API.loadAppDatabase(getApplicationContext());
            NetworkResponse<User> res = API.Get.user(longs[0]);
            API.closeDatabase();
            return res;
        }

        @Override
        protected void onPostExecute(NetworkResponse<User> res) {
            if (res.fail()) {
                res.showErrorDialog(DrawerActivity.this);
            } else {
                User user = res.getPayload();
                TextView userName = navView.getHeaderView(0).findViewById(R.id.full_name);
                userName.setText(user.username);
                TextView email = navView.getHeaderView(0).findViewById(R.id.user_email);
                email.setText(user.email);
                ImageView profilePic = navView.getHeaderView(0).findViewById(R.id.user_icon);
                Picasso.with(getApplicationContext()).load(user.imgURL).into(profilePic);
            }

        }
    }

}
