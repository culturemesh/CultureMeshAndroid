package org.codethechange.culturemesh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.codethechange.culturemesh.models.FeedItem;
import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.Post;
import org.codethechange.culturemesh.models.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//TODO: If no posts, show text view saying add a post!
public class PostsFrag extends Fragment {
    private String basePath = "www.culturemesh.com/api/v1";

    private RecyclerView mRecyclerView;
    private RVAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences settings;
    //To figure out params that would be passed in

    public PostsFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        settings = getActivity().getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);

        mRecyclerView = rootView.findViewById(R.id.postsRV);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //get User info, this will be from SavedInstances from login or account
        String fn = null;
        String ln = null;
        String email = null;
        String un = null;
        Network[] networks = null;

        SharedPreferences settings = getActivity().getSharedPreferences(API.SETTINGS_IDENTIFIER,
                Context.MODE_PRIVATE);
        BigInteger networkId = new BigInteger(settings.getString(API.SELECTED_NETWORK,
                "123456"));
        ArrayList<FeedItem> posts = new ArrayList<FeedItem>();
        for (Post post : API.genPosts()) {
            posts.add(post);
        }
        posts.add(API.genEvents().get(2));

        mAdapter = new RVAdapter(posts, new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FeedItem item) {
                Intent intent = new Intent(getActivity(), SpecificPostActivity.class);
                Post post = (Post) item; //to test
                BigInteger postID = post.getId();
                try {
                    intent.putExtra("postID", postID.toString());
                    getActivity().startActivity(intent);
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "Cannot open post", Toast.LENGTH_LONG).show();
                }
            }
        }, getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        //REDACTED AND MOVED/CHANGED IN loadPosts
        /*String network = ""; //to draw from explore/saved Instances
        String networkId = "";
        final String postPath = basePath + network + networkId + "/posts";
        final String eventPath = basePath + network + networkId + "/events";
        Ion.with(this)
                .load(postPath)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    public void onCompleted(Exception e, String result) {
                        loadPosts(result, postPath, rv, user);
                    }
                });
        Ion.with(this)
                .load(eventPath)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    public void onCompleted(Exception e, String result) {
                        loadPosts(result, eventPath, rv, user);
                    }
                });
        //TextView tx = rootView.findViewById();*/

        //Get network id
        long selectedNetwork = settings.getLong(API.SELECTED_NETWORK, 1);
        new LoadFeedItems().execute(selectedNetwork);
        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class LoadFeedItems extends AsyncTask<Long,Void,ArrayList<FeedItem>> {

        /**
         * This is the asynchronous part. It calls the client API, which can make network requests
         * and read from the cache database.
         * @param longs This should be the network id.
         * @return a collection of feed items to be displayed in the feed.
         */
        @Override
        protected ArrayList<FeedItem> doInBackground(Long... longs) {
            SharedPreferences settings = getActivity().getSharedPreferences(API.SETTINGS_IDENTIFIER,
                    MODE_PRIVATE);
            //We generalize posts/events to be feed items for polymorphism.
            //TODO: Consider error checking for when getPayload is null.
            ArrayList<FeedItem> feedItems = new ArrayList<FeedItem>();
            if (settings.getBoolean(TimelineActivity.FILTER_CHOICE_EVENTS, true)) {
                //If events aren't filtered out, add them to arraylist.
                feedItems.addAll(API.Get.networkEvents(longs[0]).getPayload());
            }
            if (settings.getBoolean(TimelineActivity.FILTER_CHOICE_NATIVE, true)) {
                //If posts aren't filtered out, add them to arraylist.
                //We also need to get the post replies.
                List<Post> posts = API.Get.networkPosts(longs[0]).getPayload();
                for (Post post : posts) {
                    post.comments = API.Get.postReplies(post.id).getPayload();
                }
                feedItems.addAll(posts);
            }
            //TODO: Add ability check out twitter posts.
            return feedItems;
        }

        @Override
        protected void onPostExecute(ArrayList<FeedItem> feedItems) {
            mAdapter = new RVAdapter(feedItems, getActivity().getApplicationContext());
            ArrayList<FeedItem> posts = new ArrayList<FeedItem>();
            for (Post post : API.genPosts()) {
                posts.add(post);
            }
            posts.add(API.genEvents().get(2));

            mAdapter = new RVAdapter(posts, new RVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(FeedItem item) {
                    Intent intent = new Intent(getActivity(), SpecificPostActivity.class);
                    Post post = (Post) item; //to test
                    BigInteger postID = post.getId();
                    try {
                        intent.putExtra("postID", postID.toString());
                        getActivity().startActivity(intent);
                    } catch (NullPointerException e) {
                        Toast.makeText(getActivity(), "Cannot open post", Toast.LENGTH_LONG).show();
                    }
                }
            }, getActivity().getApplicationContext());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setAdapter(mAdapter);
            getFragmentManager().beginTransaction()
                    .detach(PostsFrag.this)
                    .attach(PostsFrag.this)
                    .commit();
        }
    }


}
