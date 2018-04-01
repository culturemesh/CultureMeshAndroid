package org.codethechange.culturemesh;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.data.CMDatabase;
import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.FeedItem;
import org.codethechange.culturemesh.models.Post;
import org.codethechange.culturemesh.models.PostReply;

import java.math.BigInteger;
import java.util.List;

public class SpecificPostActivity extends AppCompatActivity {


    CardView cv;
    TextView personName;
    TextView username;
    TextView content;
    TextView timestamp;
    ImageView personPhoto;
    ImageView postTypePhoto;
    ImageView[] images;

    private RecyclerView commentsRV;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_post);

        Intent intent = getIntent();
        long postID = intent.getLongExtra("postID", 0);
        cv = findViewById(R.id.cv);
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


        //For now, since I believe events cannot take comments, I don't think it is worth the user's
        //time to navigate to this activity with an event.
        new loadPostReplies().execute(postID);

    }

    public void fetchCommentsAtEnd(int currItem) {
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

    class PostBundleWrapper {
        Post post;
        List<PostReply> replies;
    }

    public class loadPostReplies extends AsyncTask<Long, Void, PostBundleWrapper> {

        @Override
        protected PostBundleWrapper doInBackground(Long... longs) {
            API.loadAppDatabase(getApplicationContext());
            PostBundleWrapper wrapper = new PostBundleWrapper();
            wrapper.post = API.Get.post(longs[0]).getPayload();
            wrapper.replies = API.Get.postReplies(longs[0]).getPayload();
            return wrapper;
        }

        @Override
        protected void onPostExecute(PostBundleWrapper postBundleWrapper) {
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
                    Picasso.with(images[j].getContext()).load(links[j]).into(images[j]);
                }
            }
            Picasso.with(personPhoto.getContext()).load(post.getAuthor().getImgURL()).
                    into(personPhoto);
            FloatingActionButton FAB = findViewById(R.id.write_comment);
            FAB.setVisibility(View.VISIBLE);

      //      ListView commentLV = findViewById(R.id.commentList);
      //      int r = getResources().getIdentifier("colorPrimary", "color", "org.codethechange.culturemesh");
      //      commentLV.setBackgroundResource(r);

      //      String[] comments = {"test comment 1", "test comment 2", "this is good content", "this is, uh, not good content",
      //              "this is a really long comment to see how comments will work if someone has a lot to say about someone's content, which is very (very) possible"};

      //      ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, comments);
      //      commentLV.setAdapter(adapter);
      //      adapter.notifyDataSetChanged();
        }
    }
}

