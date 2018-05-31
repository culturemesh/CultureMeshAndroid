package org.codethechange.culturemesh;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.PostReply;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Dylan Grosz (dgrosz@stanford.edu) on 3/26/18.
 */

public class CommentsFrag extends Fragment {

    private String basePath = "www.culturemesh.com/api/v1";

    private RecyclerView mRecyclerView;
    private RVCommentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RequestQueue queue;
    SharedPreferences settings;

    public CommentsFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        settings = getActivity().getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
        queue = Volley.newRequestQueue(getContext());
        super.onCreate(savedInstanceState);

    }

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

        //get User info, this will be from SavedInstances from login or account
        String fn = null;
        String ln = null;
        String email = null;
        String un = null;
        Network[] networks = null;

        SharedPreferences settings = getActivity().getSharedPreferences(API.SETTINGS_IDENTIFIER,
                Context.MODE_PRIVATE);

        Intent intent = getActivity().getIntent();
        long postID = intent.getLongExtra("postID", 0);
        API.Get.postReplies(queue, 88, new Response.Listener<NetworkResponse<ArrayList<PostReply>>>() {

            @Override
            public void onResponse(NetworkResponse<ArrayList<PostReply>> response) {
                mAdapter = new RVCommentAdapter(response.getPayload(), new RVCommentAdapter.OnItemClickListener() {
                    @Override
                    public void onCommentClick(PostReply comment) {
                        //to add comment click/long click functionality
                        Toast.makeText(getActivity(), "Comment by " + comment.author + " clicked!", Toast.LENGTH_LONG).show();
                    }
                }, getActivity().getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });
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

    private class LoadComments extends AsyncTask<Long,Void,ArrayList<PostReply>> {

        /**
         * This is the asynchronous part. It calls the client API, which can make network requests
         * and read from the cache database.
         * @param longs This should be the network id.
         * @return a collection of feed items to be displayed in the feed.
         */
        @Override
        protected ArrayList<PostReply> doInBackground(Long... longs) {
            API.loadAppDatabase(getActivity().getApplicationContext());

            //TODO: Consider error checking for when getPayload is null.
            //ArrayList<PostReply> comments = (ArrayList<PostReply>) API.Get.postReplies(longs[0]).getPayload();
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<PostReply> comments) {
            mAdapter = new RVCommentAdapter(comments, new RVCommentAdapter.OnItemClickListener() {
                @Override
                public void onCommentClick(PostReply comment) {
                     //to add comment click/long click functionality
                    Toast.makeText(getActivity(), "Comment by " + comment.author + " clicked!", Toast.LENGTH_LONG).show();
                }
            }, getActivity().getApplicationContext());
            mRecyclerView.setAdapter(mAdapter);
            getFragmentManager().beginTransaction()
                    .detach(CommentsFrag.this)
                    .attach(CommentsFrag.this)
                    .commit();
            API.closeDatabase();
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
