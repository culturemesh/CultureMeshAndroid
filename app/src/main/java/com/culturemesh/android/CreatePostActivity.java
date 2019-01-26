package com.culturemesh.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import com.culturemesh.android.models.Network;
import com.culturemesh.android.models.Post;

import java.util.Date;

/**
 * Creates screen the user can use to create a new {@link Post}
 */
public class CreatePostActivity extends AppCompatActivity implements
        FormatManager.IconUpdateListener {

    /**
     * Field the user uses to type the body of their {@link Post}
     */
    ListenableEditText content;

    /**
     * All the items in the formatting menu
     */
    SparseArray<MenuItem> menuItems;

    /**
     * Displays the {@link Network} the user's {@link Post} will be added to
     */
    TextView networkLabel;

    /**
     * Queue for asynchronous tasks
     */
    private RequestQueue queue;

    /**
     * Displays progress as the post is being sent over the network
     */
    ProgressBar progressBar;

    /**
     * Handles markup of the body text
     */
    FormatManager formatManager;

    /**
     * Create the screen from {@link R.layout#activity_create_post}, fill
     * {@link CreatePostActivity#networkLabel} with a description of the {@link Network}
     * from {@link API.Get#network(RequestQueue, long, Response.Listener)}, setup
     * {@link CreatePostActivity#formatManager}, and link a listener to the submission button that
     * sends the {@link Post} using
     * {@link API.Post#post(RequestQueue, Post, SharedPreferences, Response.Listener)}
     * @param savedInstanceState {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getApplicationContext());
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //This hashmap-like object helps us keep track of the settings of the format buttons.
        content = findViewById(R.id.postContent);
        networkLabel= findViewById(R.id.network_label);
        progressBar = findViewById(R.id.postPostProgressBar);
        //Allow links to redirect to browser.
        content.setMovementMethod(LinkMovementMethod.getInstance());

        final long networkId = getIntent().getLongExtra(TimelineActivity.BUNDLE_NETWORK, -1);
        //Load Network Data to update header text.
        API.Get.network(queue, networkId, new Response.Listener<NetworkResponse<Network>>() {
            @Override
            public void onResponse(NetworkResponse<Network> response) {
                if (!response.fail() && response.getPayload() != null) {
                    Network network = response.getPayload();
                    if (network.isLocationBased()) {
                        networkLabel.setText(getResources().getString(R.string.from) + " " +
                                network.fromLocation.getFullName() + " " +
                                getResources().getString(R.string.near) + " " +
                                network.nearLocation.getFullName());
                    } else {
                        networkLabel.setText(network.language.toString() + " " +
                                getResources().getString(R.string.speakers_in).toString() + " " +
                                network.nearLocation.getFullName());
                    }
                } else {
                    response.showErrorDialog(CreatePostActivity.this);
                }
            }
        });
        //Set up a hashmap-like object that makes updating the toggle settings concise.
        //Check out ListenableEditText.java for more info
        //Instantiate a hash-map like object that is also used for updating toggle settings.
        //This sparseArray will be updated with views during onCreateOptionsMenu.
        menuItems = new SparseArray<MenuItem>();
        formatManager = new FormatManager(content, this, R.id.comment_bold, R.id.comment_italic,
                R.id.comment_link);


        //Set onClick for Post button.
        Button postButton = findViewById(R.id.create_post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the HTML content!
                String contentHTML = formatManager.toString();
                String datePosted = new Date().toString();
                progressBar.setIndeterminate(true); // Only because cannot get status from API

                //TODO: Allow for attaching images/videos.
                // Note: id field doesn't matter.
                long userId = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE).getLong(API.CURRENT_USER, -1);
                Post newPost = new Post(-1, userId, networkId, contentHTML, "", "", datePosted );
                //Now let's send it off to the CultureMesh site!!
                API.Post.post(queue, newPost,
                        getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE),
                        new Response.Listener<NetworkResponse<String>>() {
                    @Override
                    public void onResponse(NetworkResponse<String> response) {
                        if (response.fail()) {
                            //Some error happened with the network request. We will need to alert the user.
                            response.showErrorDialog(CreatePostActivity.this);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            // Everything went well, so the activity will close.
                            finish();
                        }
                    }
                });
            }
        });


    }

    /**
     * Populate the options menu with controls to make text bold, italic, or a link
     * @param menu Menu to populate with options
     * @return Always returns {@code true}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_post, menu);
        menuItems.put(R.id.bold,menu.findItem(R.id.bold));
        menuItems.put(R.id.italic,menu.findItem(R.id.italic));
        menuItems.put(R.id.insert_link,menu.findItem(R.id.insert_link));
        return true;
    }

    /**
     * This function handles what happens when our format toggle buttons are clicked.
     * We want to update the content formatting when this happens as well with Spannables.
     * Check out https://stackoverflow.com/questions/10828182/spannablestringbuilder-to-create-string-with-multiple-fonts-text-sizes-etc-examp
     * for more info.
     * @param item the MenuItem that was tapped.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        switch (id) {
            case R.id.bold:
                formatManager.setBold();
                break;
            case R.id.italic:
                formatManager.setItalic();
                break;
            case R.id.insert_link:
                formatManager.setLink();
                break;
        }
        //TODO: Allow for attach image functionality.
        return super.onOptionsItemSelected(item);
    }



    /**
     * This fancy function uses our SparseArray's to concisely iterate over our toggle icons
     * and update their colors - white if untoggled, black if toggled.
     */
    public void updateIconToggles(SparseBooleanArray formTogState, SparseArray<int[]> toggleIcons) {
        //toggleIcons -- Key is menuItem Id, value is array of drawable ids.
        for (int keyIndex = 0; keyIndex < toggleIcons.size(); keyIndex++) {
            //Get id of toggle icon - key of toggleIcons SparseArray
            int id = toggleIcons.keyAt(keyIndex);
            //Get corresponding menuItem - value of menuItems SparseArray
            MenuItem item = menuItems.get(id);
            if (item != null) {
                //Get index of toggleIcon array for corresponding drawable id.
                //0 index is untoggled (false/white), 1 index is toggled (true/black)
                //Use fancy ternary statement from boolean to int to make code more concise.
                int iconIndex = (formTogState.get(id, false)) ? 1 : 0;
                //Update icon!
                item.setIcon(toggleIcons.get(id)[iconIndex]);
            }
        }
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
