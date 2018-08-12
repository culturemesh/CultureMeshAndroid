package org.codethechange.culturemesh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Superclass for all Activities that have a navigation drawer
 */
public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The inflated user interface for the activity with the drawer
     */
    protected DrawerLayout fullLayout;

    /**
     * Parent for the drawer activity
     */
    protected FrameLayout frameLayout;

    /**
     * User interface for the drawer itself
     */
    protected DrawerLayout mDrawerLayout;

    /**
     * Toggles whether the drawer is visible
     */
    protected ActionBarDrawerToggle mDrawerToggle;

    /**
     * The {@link User}'s current {@link Network}s
     */
    protected SparseArray<Network> subscribedNetworks;

    /**
     * IDs of the {@link Network}s the current {@link User} is subscribed to
     */
    protected Set<Long> subscribedNetworkIds;

    /**
     * The navigation view
     */
    NavigationView navView;

    /**
     * ID of the current {@link User}
     */
    protected long currentUser;
    protected Toolbar mToolbar;

    /**
     * Reference to the current activity
     */
    Activity thisActivity = this;

    /**
     * Queue for asynchronous tasks
     */
    RequestQueue queue;

    /**
     * Interface for classes that have actions that must wait until after the list of subscribed
     * {@link Network}s has been populated. Subclasses can use this list instead of making another
     * API call.
     */
    public interface WaitForSubscribedList {
        void onSubscribeListFinish();
    }

    /**
     * Create the drawer from {@link R.layout#activity_drawer}, which has parent with ID
     * {@link R.id#drawer_frame}. Populate the drawer with data from the current {@link User}
     * and their {@link Network}s.
     * @param layoutResID ID for the layout file to inflate
     */
    @Override
    public void setContentView(int layoutResID) {
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer, null);
        frameLayout = fullLayout.findViewById(R.id.drawer_frame);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(fullLayout);
        queue = Volley.newRequestQueue(getApplicationContext());
        //All drawer activities must have a toolbar with id "action_bar!"
        mToolbar = (Toolbar) findViewById(R.id.action_bar);
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
        final SharedPreferences settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
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
            API.Get.user(queue, currentUser, new Response.Listener<NetworkResponse<User>>() {
                @Override
                public void onResponse(NetworkResponse<User> res) {
                    //Update
                    if (res.fail()) {
                        res.showErrorDialog(DrawerActivity.this);
                    } else {
                        User user = res.getPayload();
                        TextView userName = navView.getHeaderView(0).findViewById(R.id.full_name);
                        userName.setText(user.username);
                        TextView email = navView.getHeaderView(0).findViewById(R.id.user_email);
                        String emailString = settings.getString(API.USER_EMAIL, null);
                        if (emailString == null) {
                            emailString = getString(R.string.missingEmail);
                            email.setText(emailString);
                            NetworkResponse.genErrorDialog(DrawerActivity.this,
                                    R.string.authenticationError);
                        } else {
                            email.setText(emailString);
                        }
                        ImageView profilePic = navView.getHeaderView(0).findViewById(R.id.user_icon);
                        Picasso.with(getApplicationContext()).load(user.imgURL).into(profilePic);
                    }
                }
            });
            //Now, load user subscriptions (networks) to display in the navigation drawer.
            API.Get.userNetworks(queue, currentUser, new Response.Listener<NetworkResponse<ArrayList<Network>>>() {
                @Override
                public void onResponse(NetworkResponse<ArrayList<Network>> res) {
                    if (res.fail()) {
                        res.showErrorDialog(DrawerActivity.this);
                    } else {
                        subscribedNetworks = new SparseArray<Network>();
                        //Instantiate map with key -> menu view id, value -> network.
                        for (Network net : res.getPayload()) {
                            Log.i("DrawerActivity", "Found that User with ID " + currentUser
                                    + " is subscribed to network: " + net);
                            subscribedNetworkIds.add(net.id);
                            int viewId = View.generateViewId();
                            subscribedNetworks.put(viewId, net);
                        }
                        Menu navMenu = navView.getMenu();
                        MenuItem item = navMenu.getItem(2); //Your Networks subItem
                        SubMenu netMenu = item.getSubMenu();
                        for (int i = 0; i < subscribedNetworks.size(); i++) {
                            int id = subscribedNetworks.keyAt(i);
                            Network net = subscribedNetworks.get(id);
                            String name = "";
                            if (net.isLocationBased()) {
                                name = getResources().getString(R.string.from) + " " +
                                        net.fromLocation.getShortName() + " " +
                                        getResources().getString(R.string.near) + " " +
                                        net.nearLocation.getShortName();
                            } else {
                                name = net.language.toString() + " " +
                                        getResources().getString(R.string.speakers_in) + " " +
                                        net.nearLocation.getListableName();
                            }
                            SpannableStringBuilder sb = new SpannableStringBuilder(name);
                            sb.setSpan(new RelativeSizeSpan(.8f), 0, sb.length(), 0);
                            netMenu.add(Menu.NONE, id, 0, sb);
                        }
                        navView.setNavigationItemSelectedListener(DrawerActivity.this);
                        if (thisActivity instanceof WaitForSubscribedList) {
                            Log.i("DrawerActivity", "calling onSubscribeListFinish");
                            ((WaitForSubscribedList) thisActivity).onSubscribeListFinish();
                        }
                    }
                }
            });
        }
    }

    /**
     * {@inheritDoc}
     * Also syncs the state of {@link DrawerActivity#mDrawerToggle}
     * @param savedInstanceState {@inheritDoc}
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    /**
     * {@inheritDoc}
     * Also updates the configuration of the drawer toggle by calling
     * {@link DrawerActivity#mDrawerToggle#onConfigurationChanged(Configuration)} with the provided
     * parameter.
     * @param newConfig {@inheritDoc}
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Handle navigation items the user selects. If they select a {@link Network}, they are sent
     * to {@link TimelineActivity} after the selected network is set as their chosen one. Otherwise,
     * the appropriate activity is launched based on the option they select.
     * @param item Item the user selected.
     * @return Always returns {@code true}
     */
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


    /**
     * This ensures that we are canceling all network requests if the user is leaving this activity.
     * We use a RequestFilter that accepts all requests (meaning it cancels all requests)
     */
    @Override
    public void onStop() {
        super.onStop();
        if (queue != null)
            queue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
    }



}
