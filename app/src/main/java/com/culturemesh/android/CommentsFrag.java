package com.culturemesh.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import com.culturemesh.android.models.Network;
import com.culturemesh.android.models.Post;
import com.culturemesh.android.models.PostReply;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Fragment for displaying comments to posts
 */
public class CommentsFrag extends Fragment {

    private RecyclerView mRecyclerView;
    private RVCommentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * Queue to which asynchronous tasks (mainly network requests) are added
     */
    private RequestQueue queue;

    /**
     * The app's shared settings that store user info and preferences
     */
    SharedPreferences settings;

    /**
     * Initialize references to {@link CommentsFrag#queue} and {@link CommentsFrag#settings}.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        settings = getActivity().getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
        queue = Volley.newRequestQueue(getContext());
        super.onCreate(savedInstanceState);
    }

    /**
     * Populate the activity with UI elements
     * @param inflater Inflates the xml {@link R.layout#fragment_comments} into the displayed UI
     * @param container TODO: What is this?
     * @param savedInstanceState Saved state that can be restored. Not used.
     * @return The inflated view produced by {@code inflater}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);

        mRecyclerView = rootView.findViewById(R.id.commentsRV);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent intent = getActivity().getIntent();
        long postID = intent.getLongExtra("postID", 0);
        API.Get.postReplies(queue, postID, new Response.Listener<NetworkResponse<ArrayList<PostReply>>>() {

            @Override
            public void onResponse(NetworkResponse<ArrayList<PostReply>> response) {
                mAdapter = new RVCommentAdapter(response.getPayload(), new RVCommentAdapter.OnItemClickListener() {
                    @Override
                    public void onCommentClick(PostReply comment) {
                        //Navigate to author of comment.
                        Intent viewUser = new Intent(getActivity(),ViewProfileActivity.class);
                        viewUser.putExtra(ViewProfileActivity.SELECTED_USER, comment.getAuthor().id);
                        startActivity(viewUser);
                    }
                }, getActivity().getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });
        return rootView;
    }

    /**
     * {@inheritDoc}
     * @param context {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach() {
        super.onDetach();
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
