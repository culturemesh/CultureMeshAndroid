package org.codethechange.culturemesh;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Post;
import org.codethechange.culturemesh.models.PostReply;
import org.codethechange.culturemesh.models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.Date;
import java.util.List;

public class SpecificPostActivity extends AppCompatActivity implements FormatManager.IconUpdateListener {

    CardView cv;
    TextView personName;
    TextView username;
    TextView content;
    TextView timestamp;
    ImageView personPhoto;
    ImageView postTypePhoto;
    ImageView[] images;
    ListenableEditText commentField;
    Button postButton;
    ConstraintLayout writeReplyView;
    boolean editTextOpened = false;
    ImageButton boldButton, italicButton, linkButton;
    FormatManager formatManager;
    SparseArray<ImageButton> toggleButtons;
    ProgressBar progressBar;

    private RecyclerView commentsRV;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_post);
        Intent intent = getIntent();
        final long postID = intent.getLongExtra("postID", 0);
        final long networkID = intent.getLongExtra("networkID", 0);
        cv = findViewById(R.id.cv);
        Log.i("Before start the queue", "...");
        personName = findViewById(R.id.person_name);
        username = findViewById(R.id.username);
        content = findViewById(R.id.content);
        timestamp = findViewById(R.id.timestamp);
        personPhoto = findViewById(R.id.person_photo);
        postTypePhoto = findViewById(R.id.post_type_photo);
        images = new ImageView[3];
        images[0] = findViewById(R.id.attachedImage1);
        images[1] = findViewById(R.id.attachedImage2);
        images[2] = findViewById(R.id.attachedImage3);

        commentsRV = findViewById(R.id.commentsRV);
        mLayoutManager = (LinearLayoutManager) commentsRV.getLayoutManager();
        commentsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            fetchCommentsAtEnd(pastVisiblesItems);
                            loading = true;
                        }
                    }
                }
            }
        });


        commentField = findViewById(R.id.write_comment_text);
        boldButton = findViewById(R.id.comment_bold);
        italicButton = findViewById(R.id.comment_italic);
        linkButton = findViewById(R.id.comment_link);
        postButton = findViewById(R.id.post_comment_button);
        toggleButtons = new SparseArray<ImageButton>();
        toggleButtons.put(R.id.comment_bold, boldButton);
        toggleButtons.put(R.id.comment_italic, italicButton);
        toggleButtons.put(R.id.comment_link, linkButton);
        progressBar = findViewById(R.id.post_reply_progress);
        commentField.setOnFocusChangeListener( new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                  if (hasFocus) {
                      openEditTextView();
                  }
            }
        });
        commentField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditTextView();
            }
        });
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeEditTextView();
            }
        });
        writeReplyView = findViewById(R.id.write_reply_view);
        formatManager = new FormatManager(commentField, this, R.id.comment_bold,
                R.id.comment_italic, R.id.comment_link);
        boldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatManager.setBold();
            }
        });
        italicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatManager.setItalic();
            }
        });
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatManager.setLink();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Check if valid string (nonempty)
                String content = formatManager.toString();
                if (content.length() <= 0) {
                    Snackbar.make(v, getResources().getString(R.string.cannot_write_empty),
                            Snackbar.LENGTH_LONG).show();
                }
                //TODO: Submit Post Reply to API AsyncTask call.
                //TODO: Come up with valid id.
                //TODO: Come up with user id.
                PostReply pReply = new PostReply((int) (Math.random() * 10000), postID, 1, networkID,
                        new Date().toString(), content);
                new SubmitPostReply().execute(pReply);
            }
        });
        //For now, since I believe events cannot take comments, I don't think it is worth the user's
        //time to navigate to this activity with an event.
        Log.i("Before start the queue", "...");
        //TODO: Commented out AsyncTask: new loadPostReplies().execute(postID);
        API.Get.post(getApplicationContext(), postID, new Response.Listener<Post>() {
            @Override
            public void onResponse(Post response) {
                String name = response.getAuthor().getFirstName() + " " + response.getAuthor().getLastName();
                personName.setText(name);
                content.setText(response.getContent());
                postTypePhoto.setImageDrawable(null /* logic flow depending on post source */);
                timestamp.setText(response.getDatePosted());
                username.setText(response.getAuthor().getUsername());
                if (response.getImageLink() != null || response.getVideoLink() != null ) {
                    //TODO: Figure out how to display videos
                    //TODO: Figure out format for multiple pictures. Assuming separated by commas.
                    String[] links = response.getImageLink().split(",");
                    for (int j = 0;  j < links.length; j++) {
                        if (links[j] != null && links[j].length() > 0)
                            Picasso.with(images[j].getContext()).load(links[j]).into(images[j]);
                    }
                }
                Picasso.with(personPhoto.getContext()).load(response.getAuthor().getImgURL()).
                        into(personPhoto);
            }
        });
    }

    @Override
    public void updateIconToggles(SparseBooleanArray formTogState, SparseArray<int[]> toggleIcons) {
        //toggleIcons -- Key is menuItem Id, value is array of drawable ids.
        for (int keyIndex = 0; keyIndex < toggleIcons.size(); keyIndex++) {
            //Get id of toggle icon - key of toggleIcons SparseArray
            int id = toggleIcons.keyAt(keyIndex);
            //Get corresponding menuItem - value of menuItems SparseArray
            ImageButton toggleButton = toggleButtons.get(id);
            if (toggleButton != null) {
                //Get index of toggleIcon array for corresponding drawable id.
                //0 index is untoggled (false/white), 1 index is toggled (true/black)
                //Use fancy ternary statement from boolean to int to make code more concise.
                int iconIndex = (formTogState.get(id, false)) ? 1 : 0;
                //Update icon!
                toggleButton.setImageDrawable(getResources().getDrawable(toggleIcons.get(id)[iconIndex]));
            }
        }
    }


    public void fetchCommentsAtEnd(int currItem) {
        //TODO: @Dylan: this causes nullpointer because
        // this view is no where to be found...
        // findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        //TODO: load extra posts by loadSize amount

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: @Dylan: this causes nullpointer because
                // this view is no where to be found...
                //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        }, 1000);


    }

    class PostBundleWrapper {
        Post post;
        List<PostReply> replies;
    }

    public class loadPostReplies extends AsyncTask<Long, Void, PostBundleWrapper> {

        @Override
        protected PostBundleWrapper doInBackground(Long... longs) {
            API.loadAppDatabase(getApplicationContext());
            PostBundleWrapper wrapper = new PostBundleWrapper();
            //wrapper.post = API.Get.post(longs[0]).getPayload();
            wrapper.replies = API.Get.postReplies(longs[0]).getPayload();
            API.closeDatabase();
            return wrapper;
        }

        @Override
        protected void onPostExecute(final PostBundleWrapper postBundleWrapper) {
            Post post = postBundleWrapper.post;
            String name = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();
            personName.setText(name);
            content.setText(post.getContent());
            postTypePhoto.setImageDrawable(null /* logic flow depending on post source */);
            timestamp.setText(post.getDatePosted());
            username.setText(post.getAuthor().getUsername());
            if (post.getImageLink() != null || post.getVideoLink() != null ) {
                //TODO: Figure out how to display videos
                //TODO: Figure out format for multiple pictures. Assuming separated by commas.
                String[] links = post.getImageLink().split(",");
                for (int j = 0;  j < links.length; j++) {
                    if (links[j] != null && links[j].length() > 0)
                    Picasso.with(images[j].getContext()).load(links[j]).into(images[j]);
                }
            }
            Picasso.with(personPhoto.getContext()).load(post.getAuthor().getImgURL()).
                    into(personPhoto);

            //Now, allow redirect to ViewProfileActivity if username or profile pic is tapped.
            View.OnClickListener viewUserProfile = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent viewUser = new Intent(getApplicationContext(),ViewProfileActivity.class);
                    viewUser.putExtra(ViewProfileActivity.SELECTED_USER, postBundleWrapper.post.author.id);
                    startActivity(viewUser);
                }
            };
            personPhoto.setOnClickListener(viewUserProfile);
            username.setOnClickListener(viewUserProfile);
            personName.setOnClickListener(viewUserProfile);
            int r = getResources().getIdentifier("commentColor", "color", "org.codethechange.culturemesh");

      //      String[] comments = {"test comment 1", "test comment 2", "this is good content", "this is, uh, not good content",
      //              "this is a really long comment to see how comments will work if someone has a lot to say about someone's content, which is very (very) possible"};

      //      ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, comments);
      //      commentLV.setAdapter(adapter);
      //      adapter.notifyDataSetChanged();
        }
    }


    /**
     * This function animates the bottom view to expand up, allowing for a greater text field
     * as well as toggle buttons.
     */
    void openEditTextView() {
        if (!editTextOpened) {
            editTextOpened = true;
            final int oldPixelSize = getResources().getDimensionPixelSize(R.dimen.write_reply_close_height);
            final int newPixelSize = getResources().getDimensionPixelSize(R.dimen.write_reply_open_height);
            genResizeAnimation(oldPixelSize, newPixelSize, writeReplyView);
            boldButton.setVisibility(View.VISIBLE);
            italicButton.setVisibility(View.VISIBLE);
            linkButton.setVisibility(View.VISIBLE);
        }

    }

    /**
     * When the user selects out of the text field, the view will shrink back to its original
     * position.
     */
    void closeEditTextView() {
        if (editTextOpened) {
            Log.i("Specific  Post", "Closing Edit Text");
            editTextOpened = false;
            final int newPixelSize = getResources().getDimensionPixelSize(R.dimen.write_reply_close_height);
            final int oldPixelSize = getResources().getDimensionPixelSize(R.dimen.write_reply_open_height);
            genResizeAnimation(oldPixelSize, newPixelSize, writeReplyView);
            boldButton.setVisibility(View.GONE);
            italicButton.setVisibility(View.GONE);
            linkButton.setVisibility(View.GONE);
        }
    }

    /**
     *
     * This little helper handles the animation involved in changing the size of the write reply view.
     * @param oldSize start height, in pixels.
     * @param newSize final height, in pixels.
     * @param layout writeReplyView
     */
    void genResizeAnimation(final int oldSize, final int newSize, final ConstraintLayout layout){
        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                        layout.getLayoutParams();
                params.height = (int) (oldSize + (newSize - oldSize)*interpolatedTime);
                layout.setLayoutParams(params);
            }
        };
        anim.setDuration(500);
        layout.startAnimation(anim);
    }

    class SubmitPostReply extends AsyncTask<PostReply, Void, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(PostReply... postReplies) {
            API.loadAppDatabase(getApplicationContext());
            NetworkResponse res = API.Post.reply(postReplies[0]);
            API.closeDatabase();
            return res;
        }

        /**
         * Makes the progress bar indeterminate
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        /**
         * If POSTing succeeded: Closes the activity and returns the user to the previous screen
         * If POSTing failed: Displays error message and returns uer to composition screen
         * @param response Status of doInBackground method that represents whether POSTing succeeded
         */
        @Override
        protected void onPostExecute(NetworkResponse response) {
            super.onPostExecute(response);
            if (response.fail()) {
                response.showErrorDialog(SpecificPostActivity.this);
            } else {
                progressBar.setVisibility(View.GONE);
                commentField.setText("");
                closeEditTextView();
            }
        }
    }
}

