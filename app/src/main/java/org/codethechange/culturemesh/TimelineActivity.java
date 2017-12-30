package org.codethechange.culturemesh;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.User;

import java.math.BigInteger;
import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PostsFrag.OnFragmentInteractionListener {
private String basePath = "www.culturemesh.com/api/v1";
    final String FILTER_LABEL = "fl";
    final static String FILTER_CHOICE_NATIVE = "fcn";
    final static String FILTER_CHOICE_TWITTER = "fct";
    static SharedPreferences settings;
    
    private FloatingActionButton create, createPost, createEvent;
    private Animation open, close, backward, forward;
    private boolean isFABOpen;

    private Network network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
        //Choose selected network.
        //TODO: Create better default behavior for no selected networks.
        String selectedNetwork = settings.getString(API.SELECTED_NETWORK, "123456");
        BigInteger id = new BigInteger(selectedNetwork);
        network = API.Get.network(id);

        ArrayList<User> users = API.Get.networkUsers(id);

        //Update number of people.
        //TODO: Find string manipulation of number that adds magnitude suffix (K,M,etc.)
        //Update population number
        TextView population = findViewById(R.id.network_population);
        population.setText(String.format("%d",users.size()));
        //Update from location/language
        TextView fromLocation = findViewById(R.id.fromLocation);
        if (network.isLocationNetwork()) {
            fromLocation.setText(network.getFromLocation().shortName());
        } else {
            fromLocation.setText(network.getLang().getName());
        }
        //Update near location
        TextView nearLocation = findViewById(R.id.nearLocation);
        nearLocation.setText(network.getNearLocation().shortName());
        //TODO: Apply filter settings.
        ImageButton postsFilter = (ImageButton) findViewById(R.id.filter_feed);
        postsFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FilterDialogFragment().show(getFragmentManager(), FILTER_LABEL);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                //this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        //toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        //Load Animations for Floating Action Buttons
        open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

        //Setup FloatingActionButton Listeners
        create = findViewById(R.id.create);
        createPost = findViewById(R.id.create_post);
        createEvent = findViewById(R.id.create_event);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cPA = new Intent(getApplicationContext(), CreatePostActivity.class);
                startActivity(cPA);
                //TODO: Have fragment loading stuff be in start() method so feed updates.
            }
        });
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cEA = new Intent(getApplicationContext(), CreateEventActivity.class);
                startActivity(cEA);
            }
        });
        
    }

    @Override
    protected void onStart() {
        //Discuss how to pull from server, add in Ion functionality
        super.onStart();
        String network = ""; //to draw from explore/saved Instances
        String networkId = "";
        final String postPath = basePath + network + networkId + "/posts";
        final String eventPath = basePath + network + networkId + "/events";
        /*Ion.with(this)
                .load(postPath)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    public void onCompleted(Exception e, String result) {
                        loadPosts(result, postPath);
                    }
                });
        Ion.with(this)
                .load(eventPath)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    public void onCompleted(Exception e, String result) {
                        loadPosts(result, eventPath);
                    }
                });*/
    }

    public void loadPosts(String result, String netPath) {
        int loadSize = 10; //how many posts we load at once
        RecyclerView postRV = null;//(RecyclerView) findViewbyId
        if(/* filter includes posts */ true) {
            //build array out of post structs
            LinearLayout layout = null;//(GridLayout) getView().findViewById(R.id.LLayout); //makes sense with fragments
            for(int i = 0; i < loadSize; i++) {
                //create a cardview, build post information into it.
                //separately, create card_view.xml file
                layout.addView(null /*postCardView*/);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class FilterDialogFragment extends DialogFragment {
        //TODO: Get SharedPrefs for saved settings
        boolean[] filterSettings = {true, true};

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            filterSettings[0] = settings.getBoolean(FILTER_CHOICE_NATIVE, true);
            filterSettings[1] = settings.getBoolean(FILTER_CHOICE_TWITTER, true);
            builder.setTitle(getResources().getString(R.string.filter_posts))
                .setMultiChoiceItems(R.array.filter_choices, filterSettings,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                               filterSettings[which] = isChecked;
                            }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO: Save settings to sharedPrefs.
                        // User clicked OK, so save the results somewhere
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean(FILTER_CHOICE_NATIVE, filterSettings[0]);
                        editor.putBoolean(FILTER_CHOICE_TWITTER, filterSettings[1]);
                        editor.apply();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing.
                    }
                });
            return builder.create();
        }
    }

    void animateFAB() {
        int colorAccent = getResources().getColor(R.color.colorAccent);
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
        if (isFABOpen) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                ObjectAnimator changeColor = ObjectAnimator.ofInt(create, "backgroundTint", primaryDark, colorAccent);
                changeColor.setDuration(300);
                changeColor.setEvaluator(new ArgbEvaluator());
                changeColor.setInterpolator(new DecelerateInterpolator(2));
                changeColor.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        create.setBackgroundTintList(ColorStateList.valueOf(animatedValue));
                    }
                });
                changeColor.start();
            }

            createPost.startAnimation(close);
            createEvent.startAnimation(close);
            create.setImageDrawable(getResources().getDrawable(R.drawable.ic_create_white_24px));
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                ObjectAnimator changeColor = ObjectAnimator.ofInt(create, "backgroundTint", colorAccent, primaryDark);
                changeColor.setDuration(300);
                changeColor.setEvaluator(new ArgbEvaluator());
                changeColor.setInterpolator(new DecelerateInterpolator(2));
                changeColor.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        create.setBackgroundTintList(ColorStateList.valueOf(animatedValue));
                    }
                });
                changeColor.start();
            }
            //create.startAnimation(forward);
            createPost.startAnimation(open);
            createEvent.startAnimation(open);
            create.setImageDrawable(getResources().getDrawable(R.drawable.ic_cancel_white_24px));
        }
        isFABOpen = !isFABOpen;
    }


}
