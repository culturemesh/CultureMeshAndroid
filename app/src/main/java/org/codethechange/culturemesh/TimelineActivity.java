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
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SubMenu;
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
import android.widget.Toast;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.User;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dylan Grosz (dgrosz@stanford.edu) on 11/8/17.
 */
public class TimelineActivity extends DrawerActivity
        implements PostsFrag.OnFragmentInteractionListener {
private String basePath = "www.culturemesh.com/api/v1";
    final String FILTER_LABEL = "fl";
    final static String FILTER_CHOICE_NATIVE = "fcn";
    final static String FILTER_CHOICE_TWITTER = "fct";
    final static String BUNDLE_NETWORK = "bunnet";
    final static String SUBSCRIBED_NETWORKS = "subscrinets";
    static SharedPreferences settings;

    private FloatingActionButton create, createPost, createEvent;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView postsRV;
    private LinearLayoutManager mLayoutManager;
    private Animation open, close;
    private boolean isFABOpen;


    private Network network;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);

        /* //Set up Toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_header);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        */

        getSupportActionBar().setLogo(R.drawable.logo_header);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);


        //Choose selected network.
        //TODO: Create better default behavior for no selected networks.
        String selectedNetwork = settings.getString(API.SELECTED_NETWORK, "123456");
        BigInteger id = new BigInteger(selectedNetwork);
        network = API.Get.network(id);

        ArrayList<User> users = API.Get.networkUsers(id);

        //Update number of people.
        //TODO: Manipulate string of number to have magnitude suffix (K,M,etc.)
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

        //Load Animations for Floating Action Buttons
        open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

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
                cPA.putExtra(BUNDLE_NETWORK, network);
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

        swipeRefreshLayout = findViewById(R.id.postsRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeRefresh();
            }
        });

        //set up postsRV
        postsRV = findViewById(R.id.postsRV);
        mLayoutManager = (LinearLayoutManager) postsRV.getLayoutManager();

//        postsRV.addOnItemTouchListener(new RecyclerTouchListener(this,
//                postsRV, new PostClickListener() {
//            public void onClick(View view, final int position) {
//                PostViewHolder clickedPost = (PostViewHolder) postsRV.getLayoutManager().findViewByPosition(position);
//                Toast.makeText(TimelineActivity.this, "clicked at: " + position, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent();
//             //   intent.putExtra("post", /* check if this is allowed */ (Serializable) clickedPost);
//              //  startActivity(intent);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                //To ignore for now, but can also implement a new function for this later if we want
//            }
//        }));

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

//    class RVOnClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            int itemPos = postsRV.indexOfChild(v);
//        }
//    }
//
//
//    public static interface PostClickListener{
//        public void onClick(View view,int position);
//        public void onLongClick(View view,int position);
//    }
//
//    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
//
//        private PostClickListener clicklistener;
//        private GestureDetector gestureDetector;
//
//        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final PostClickListener clicklistener){
//            this.clicklistener=clicklistener;
//            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
//                    if(child!=null && clicklistener!=null){
//                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
//                    }
//                }
//            });
//        }
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//        }
//    }

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

    public void loadPosts(String result, String netPath, int toSkip) {
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * This dialog allows us to filter out native/twitter posts from the feed.
     * TODO: Add feature to filter out events.
     */
    public static class FilterDialogFragment extends DialogFragment {
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

    /**
     * This function controls the animation for the FloatingActionButtons.
     * When the user taps the pencil icon, two other floating action buttons rise into view - create
     * post and create event. The
     */
    void animateFAB() {
        int colorAccent = getResources().getColor(R.color.colorAccent);
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
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
                        "backgroundTint", colorAccent, primaryDark);
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
