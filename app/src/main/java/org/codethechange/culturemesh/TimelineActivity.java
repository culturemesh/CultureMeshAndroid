package org.codethechange.culturemesh;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

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
    private RequestQueue queue;
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
        queue = Volley.newRequestQueue(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    protected void createNoNetwork() {
        Intent startExplore = new Intent(getApplicationContext(), ExploreBubblesOpenGLActivity.class);
        startActivity(startExplore);
        finish();
    }

    protected void createDefaultNetwork() {
        //Now, load network data for the header bar.
        API.Get.network(queue, selectedNetwork, new Response.Listener<NetworkResponse<Network>>() {
            @Override
            public void onResponse(NetworkResponse<Network> response) {
                if(!response.fail()) {
                    Network network = response.getPayload();
                    //Update from location/language
                    if (network.isLocationBased()) {
                        fromLocation.setText(network.fromLocation.getShortName());
                    } else {
                        fromLocation.setText(network.language.name);
                    }
                    //Update near location
                    nearLocation.setText(network.nearLocation.getShortName());
                } else {
                    response.showErrorDialog(getApplicationContext());
                }
            }
        });
        //We also need the user data for the population textview and and slide view
        API.Get.networkUserCount(queue, selectedNetwork, new Response.Listener<NetworkResponse<Long>>() {
            @Override
            public void onResponse(NetworkResponse<Long> response) {
                if (!response.fail()) {
                    //Manipulate string of number to have magnitude suffix (K,M,etc.) for population text
                    population.setText(FormatManager.abbreviateNumber(response.getPayload()));
                } else {
                    response.showErrorDialog(getApplicationContext());
                }
            }
        });
        View.OnClickListener showUsersListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bsFrag = new ViewUsersModalSheetFragment();
                Bundle args = new Bundle();
                args.putString(ViewUsersModalSheetFragment.NETWORK_ID, selectedNetwork + "");
                bsFrag.setArguments(args);
                bsFrag.show(getSupportFragmentManager(), "ViewUsersModalSheet");
            }
        };
        population.setOnClickListener(showUsersListener);
        findViewById(R.id.population_button).setOnClickListener(showUsersListener);

        //We also just need a list of users for our modal fragment.


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
        final AtomicBoolean loadingMoreFeedItems = new AtomicBoolean();
        loadingMoreFeedItems.set(false);
        //set up postsRV
        postsRV = findViewById(R.id.postsRV);
        mLayoutManager = (LinearLayoutManager) postsRV.getLayoutManager();
        //check if at end of posts
        postsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int pastVisiblesItems, visibleItemCount, totalItemCount;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // Required to override, but nothing here.
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1) && newState == SCROLL_STATE_IDLE
                        && !loadingMoreFeedItems.get()) {
                    //Temporarily disable scroll listener until we fetch new feed items.
                    loadingMoreFeedItems.set(true);
                    FragmentManager fm = getSupportFragmentManager();
                    PostsFrag frag = (PostsFrag) fm.findFragmentById(R.id.posts_fragment);
                    frag.fetchNewPage(new Response.Listener<Void>() {
                        @Override
                        public void onResponse(Void response) {
                            loadingMoreFeedItems.set(false);
                        }
                    });
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

    //Or rather, use built-in SwipeRefreshLayout; commented out portions were to be used with inner gesture class
    public void onSwipeRefresh() {
        //Restart activity to refresh posts.
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    //Returns true upon successful retrieval, returns false if issue/no connection
    public boolean refreshPosts() { //probably public? if we want to refresh from outside, if that's possible/needed
        boolean success = true;
        //TODO: re-call loading posts. this must be done asynchronously.

        swipeRefreshLayout.setRefreshing(false);
        //TODO: if server/connection error, success = false;

        //TODO: when done, animate old posts fading away and new posts then fading in
        return success;
    }


    @Override
    protected void onStart() {
        super.onStart();

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
            Button joinNetwork = findViewById(R.id.join_network_button);
            joinNetwork.setVisibility(View.GONE);
        } else {
            //The user has not joined this network yet. We should hide the write post/events buttons
            //and show the join network button.
            Button joinNetwork = findViewById(R.id.join_network_button);
            joinNetwork.setVisibility(View.VISIBLE);
            joinNetwork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //We need to subscribe this user!
                    API.Post.addUserToNetwork(queue, currentUser, selectedNetwork, new Response.Listener<NetworkResponse<Void>>() {
                        @Override
                        public void onResponse(NetworkResponse<Void> response) {
                            if (response.fail()) {
                                response.showErrorDialog(TimelineActivity.this);
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
                    });
                }
            });
        }
    }

    /**
     * This dialog allows us to filter out native/twitter posts from the feed
     */
    public static class FilterDialogFragment extends DialogFragment {
        boolean[] filterSettings = {true, true};

        @Override
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            filterSettings[0] = settings.getBoolean(FILTER_CHOICE_NATIVE, true);
            filterSettings[1] = settings.getBoolean(FILTER_CHOICE_EVENTS, true);
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
                            editor.putBoolean(FILTER_CHOICE_EVENTS, filterSettings[1]);
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
}


