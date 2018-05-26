package org.codethechange.culturemesh;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import org.codethechange.culturemesh.models.FromLocation;
import org.codethechange.culturemesh.models.NearLocation;
import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dylan Grosz (dgrosz@stanford.edu) on 11/8/17.
 */
public class TimelineActivity extends DrawerActivity implements DrawerActivity.WaitForSubscribedList{
    private String basePath = "www.culturemesh.com/api/v1";
    final String FILTER_LABEL = "fl";
    final static String FILTER_CHOICE_NATIVE = "fcn";
    final static String FILTER_CHOICE_TWITTER = "fct";
    final static String FILTER_CHOICE_EVENTS = "fce";
    final static String BUNDLE_NETWORK = "bunnet";
    final static String SUBSCRIBED_NETWORKS = "subscrinets";
    static SharedPreferences settings;

    private FloatingActionButton create, createPost, createEvent;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView postsRV;
    private LinearLayoutManager mLayoutManager;
    private Animation open, close;
    private boolean isFABOpen;
    private TextView population, fromLocation, nearLocation;
    private long selectedNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
        getSupportActionBar().setLogo(R.drawable.logo_header);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        population = findViewById(R.id.network_population);
        fromLocation = findViewById(R.id.fromLocation);
        nearLocation = findViewById(R.id.nearLocation);
        create = findViewById(R.id.create);
        createPost = findViewById(R.id.create_post);
        createEvent = findViewById(R.id.create_event);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    protected void createNoNetwork() {
        Intent startExplore = new Intent(getApplicationContext(), ExploreBubblesOpenGLActivity.class);
        startActivity(startExplore);
        finish();
    }

    protected void createDefaultNetwork() {
        new LoadNetworkData().execute(selectedNetwork);



        swipeRefreshLayout = findViewById(R.id.postsRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeRefresh();
            }
        });
        ImageButton postsFilter = (ImageButton) findViewById(R.id.filter_feed);
        postsFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FilterDialogFragment().show(getFragmentManager(), FILTER_LABEL);
            }
        });
        //set up postsRV
        postsRV = findViewById(R.id.postsRV);
        mLayoutManager = (LinearLayoutManager) postsRV.getLayoutManager();

        //check if at end of posts
        postsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean loading = true;
            int pastVisiblesItems, visibleItemCount, totalItemCount;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            Log.v("...", "Last Item");
                            fetchPostsAtEnd(pastVisiblesItems);
                            loading = true;
                        }
                    }
                }
            }
        });
    }

    public void fetchPostsAtEnd(int currItem) {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        //TODO: load extra posts by loadSize amount

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        }, 1000);


    }

    /* Can control refresh aesthetic (i.e. strength of swipe to trigger, direction, etc.) with this
    class GestureDetector extends android.view.GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 50;

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            try {
                if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH){
                    return false;
                }
                // down swipe
                else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    RecyclerView postsRV = getActivity().findViewById(R.id.postsRV);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) postsRV.getLayoutManager();
                    if(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) animateRefresh();
                }
            } catch (Exception e) {
                Log.d("gErr", "Gesture error");
            }
            return false;
        }
    }
*/

    //Or rather, use built-in SwipeRefreshLayout; commented out portions were to be used with inner gesture class
    public void onSwipeRefresh() {
        //RecyclerView postsRV = getActivity().findViewById(R.id.postsRV);
        //LinearLayoutManager linearLayoutManager = (LinearLayoutManager) postsRV.getLayoutManager();
        swipeRefreshLayout.setRefreshing(true);
        //if(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0)
        if(!refreshPosts()) Log.d("sErr", "Server/Connection error");
    }

    //Returns true upon successful retrieval, returns false if issue/no connection
    public boolean refreshPosts() { //probably public? if we want to refresh from outside, if that's possible/needed
        boolean success = true;

        //TODO: re-call loading posts. this must be done asynchronously.

        swipeRefreshLayout.setRefreshing(false);
        //TODO: if server/connection error, success = false;

        //TODO:     when done, animate old posts fading away and new posts then fading in
        return success;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //TODO: For first run, uncomment this: new TestDatabase().execute();
        new TestDatabase().execute();
        //Check if user has selected a network to view, regardless of whether the user is subscribed
        //to any networks yet. Previously, we checked if the user joined a network, and instead
        //navigate the user to ExploreBubbles. This is not ideal because if a user wants to check
        //out a network before joining one, then they will be unable to view the network.
        selectedNetwork = settings.getLong(API.SELECTED_NETWORK, -1);
        if (selectedNetwork != -1) {
            createDefaultNetwork();
        } else {
            createNoNetwork();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) postsRV.getLayoutManager();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(linearLayoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
            postsRV.smoothScrollToPosition(0);
            onSwipeRefresh(); //can choose whether or not to refresh later
        } else { //nothing to "undo", go back an activity
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

        if (super.mDrawerToggle.onOptionsItemSelected(item)) {
            Log.i("Toggle","Toggle selected!");
            return true;
        }
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * If the user is subscribed to the network, they are able to write posts and events. If
     * the user is not subscribed to the network, there should be a pretty button for them that
     * encourages the user to join the network.
     * This control flow relies on checking if the user is subscribed to a network or not, which
     * requires an instantiated subscribedNetworkIds set in DrawerActivity. This set is instantiated
     * off the UI thread, so we need to wait until that thread completes. Thus, this function is
     * called by DrawerActivity after the network thread completes.
     */
    @Override
    public void onSubscribeListFinish() {
        //Check if the user is subscribed or not this network.
        if (subscribedNetworkIds.contains(selectedNetwork)) {
            //We are subscribed! Thus, the user can write posts an events. Let's make sure they have
            //the right listeners.

            //Setup FloatingActionButton Listeners
            //Load Animations for Floating Action Buttons
            open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
            close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

            create.setVisibility(View.VISIBLE);
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
                    cPA.putExtra(BUNDLE_NETWORK, selectedNetwork);
                    startActivity(cPA);
                    //TODO: Have fragment post feed loading stuff be in start() method so feed updates
                    //when createPostActivity finishes.
                }
            });
            createEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cEA = new Intent(getApplicationContext(), CreateEventActivity.class);
                    startActivity(cEA);
                }
            });
        } else {
            //The user has not joined this network yet. We should hide the write post/events buttons
            //and show the join network button.
            Button joinNetwork = findViewById(R.id.join_network_button);
            joinNetwork.setVisibility(View.VISIBLE);
            joinNetwork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //We need to subscribe this user!
                    new JoinNetwork().execute(selectedNetwork);
                }
            });

        }
    }

    /**
     * This dialog allows us to filter out native/twitter posts from the feed
     */
    public static class FilterDialogFragment extends DialogFragment {
        boolean[] filterSettings = {true, true, true};

        @Override
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            filterSettings[0] = settings.getBoolean(FILTER_CHOICE_NATIVE, true);
            filterSettings[1] = settings.getBoolean(FILTER_CHOICE_TWITTER, true);
            filterSettings[2] = settings.getBoolean(FILTER_CHOICE_EVENTS, true);
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
                            // User clicked OK, so save the results somewhere
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean(FILTER_CHOICE_NATIVE, filterSettings[0]);
                            editor.putBoolean(FILTER_CHOICE_TWITTER, filterSettings[1]);
                            editor.putBoolean(FILTER_CHOICE_EVENTS, filterSettings[2]);
                            editor.apply();
                            //Refresh the fragment to apply new filter settings.
                            getActivity().recreate();
                            dismiss();
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

    //TODO: Run new TestDatabase().execute() once to load dummy data in!
    private class TestDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            API.loadAppDatabase(getApplicationContext());
            if (API.Get.network(1).getPayload() == null) {
                API.addReplies();
                API.addUsers();
                API.addCities();
                API.addCountries();
                API.addNetworks();
                API.addRegions();
                API.addPosts();
                API.addEvents();
                API.subscribeUsers();
                settings.edit().putLong(API.CURRENT_USER, 1).apply();
            }
            API.closeDatabase();
            return null;
        }
    }

    /**
     * This function controls the animation for the FloatingActionButtons.
     * When the user taps the pencil icon, two other floating action buttons rise into view - create
     * post and create event. The
     */
    void animateFAB() {
        int colorAccent = getResources().getColor(R.color.colorAccent);
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
        int primary = getResources().getColor(R.color.colorPrimary);
        if (isFABOpen) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                @SuppressLint("ObjectAnimatorBinding") ObjectAnimator changeColor = ObjectAnimator.ofInt(create,
                        "backgroundTint", primaryDark, colorAccent);
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
            //We don't want the hidden buttons to be clickable - that would be weird.
            createPost.setClickable(false);
            createEvent.setClickable(false);
            //Hide the buttons!
            createPost.startAnimation(close);
            createEvent.startAnimation(close);
            create.setImageDrawable(getResources().getDrawable(R.drawable.ic_create_white_24px));
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                @SuppressLint("ObjectAnimatorBinding") ObjectAnimator changeColor = ObjectAnimator.ofInt(create,
                        "backgroundTint", colorAccent, primary);
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
            //Activate their onclick listeners!
            createPost.setClickable(true);
            createEvent.setClickable(true);
            //Show the buttons!
            createPost.startAnimation(open);
            createEvent.startAnimation(open);
            create.setImageDrawable(getResources().getDrawable(R.drawable.ic_cancel_white_24px));
        }
        isFABOpen = !isFABOpen;
    }

    private class LoadNetworkData extends AsyncTask<Long, Void, NetUserWrapper> {
        @Override
        protected NetUserWrapper doInBackground(Long... longs) {
            API.loadAppDatabase(getApplicationContext());
            //TODO: Use NetworkResponse for error handling.
            NetUserWrapper wrap = new NetUserWrapper();
            wrap.network = API.Get.network(longs[0]).getPayload();
            List<User> users = API.Get.networkUsers(longs[0]).getPayload();
            /*The user information is only used for the ViewUsersModalSheetFragment.
            Fragments like default constructors, so parameters should be passed as an args bundle.
            Thus, since bundle arguments are either parcelable, serializable, or primitive data types,
            we will be passing in the relevant information for the recycler view (string username,
            string profile picture URL, and long user if for ViewProfileActivity) as separate arguments.
             */
            wrap.netUsernames = new ArrayList<>();
            wrap.netProfilePictures = new ArrayList<>();
            wrap.netUserIds = new long[users.size()];
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                wrap.netUsernames.add(user.firstName + " " + user.lastName);
                wrap.netProfilePictures.add(user.imgURL);
                wrap.netUserIds[i] = user.id;
            }
            API.closeDatabase();
            return wrap;
        }

        @Override
        protected void onPostExecute(final NetUserWrapper wrapper) {
            Network network = wrapper.network;
            if (network != null) {
                //Update population number
                //Manipulate string of number to have magnitude suffix (K,M,etc.)
                population.setText(FormatManager.abbreviateNumber(wrapper.netUserIds.length));
                //Update from location/language
                if (network.networkClass) {
                    fromLocation.setText(network.fromLocation.shortName());
                } else {
                    fromLocation.setText(network.language.name);
                }
                //Update near location
                nearLocation.setText(network.nearLocation.shortName());
                View.OnClickListener showUsersListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomSheetDialogFragment bsFrag = new ViewUsersModalSheetFragment();
                        Bundle args = new Bundle();
                        args.putStringArrayList(ViewUsersModalSheetFragment.USER_NAMES, wrapper.netUsernames);
                        args.putStringArrayList(ViewUsersModalSheetFragment.IMAGE_URLS, wrapper.netProfilePictures);
                        args.putLongArray(ViewUsersModalSheetFragment.USER_IDS, wrapper.netUserIds);
                        bsFrag.setArguments(args);
                        bsFrag.show(getSupportFragmentManager(), "ViewUsersModalSheet");
                    }
                };
                population.setOnClickListener(showUsersListener);
                findViewById(R.id.population_button).setOnClickListener(showUsersListener);
            }
        }
    }

    private class NetUserWrapper {
        Network network;
        ArrayList<String> netUsernames, netProfilePictures;
        long[] netUserIds;
    }


    class JoinNetwork extends AsyncTask<Long, Void, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(Long... longs) {
            API.loadAppDatabase(getApplicationContext());
            NetworkResponse response = API.Post.addUserToNetwork(currentUser, longs[0]);
            API.closeDatabase();
            return response;
        }

        @Override
        protected void onPostExecute(NetworkResponse networkResponse) {
            if (networkResponse.fail()) {
                networkResponse.showErrorDialog(TimelineActivity.this);
            } else {
                AlertDialog success = new AlertDialog.Builder(TimelineActivity.this)
                        .setTitle(R.string.genericSuccess)
                        .setMessage(R.string.join_success)
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                subscribedNetworkIds.add(selectedNetwork);
                                //Restart the activity.
                                Intent restart = new Intent(getApplicationContext(), TimelineActivity.class);
                                startActivity(restart);
                                finish();
                            }
                        })
                        .create();
                success.show();
            }
        }
    }
}


