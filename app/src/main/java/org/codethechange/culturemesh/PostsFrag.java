package org.codethechange.culturemesh;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.Post;
import org.codethechange.culturemesh.models.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostsFrag extends Fragment {
    private String basePath = "www.culturemesh.com/api/v1";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //To figure out params that would be passed in

    private OnFragmentInteractionListener mListener;

    public PostsFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);

        final RecyclerView rv = rootView.findViewById(R.id.postsRV);

        //mRecyclerView = (RecyclerView) activity.findViewById(R.id.postsRV);
        mRecyclerView = rv;
        Log.i("Recycler View Null?", ""+(rv == null));

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

        //final User user = new User(fn, ln, email, un, networks);
        User user = API.Get.user(new BigInteger("23")); //Replace with actual user id.
        Network network = API.Get.network(new BigInteger("33"));
        ArrayList<Post> posts = API.Get.networkPosts(network.getId());
        mAdapter = new RVAdapter(posts);
        mRecyclerView.setAdapter(mAdapter);
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
        return rootView;
    }

    private void loadPosts(String RESTresult, String path, RecyclerView rv, User user) {
        //discuss parsing REST data using path
        try {
            JSONObject postJSON = new JSONObject(RESTresult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] postData = RESTresult.split("," /*determine this*/);
        ArrayList<Post> posts = new ArrayList<Post>();
        for(String p : postData) {
            String content = null;
            String title = null;
            Date datePosted = new Date(); /* initialize with string version of date */
            Post post = new Post(user, content, title, datePosted);
            //instantiate post with the REST data, to discuss
            posts.add(post);
        }
        RVAdapter adapter = new RVAdapter(posts);
        rv.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
