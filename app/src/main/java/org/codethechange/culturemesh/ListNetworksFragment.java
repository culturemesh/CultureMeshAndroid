package org.codethechange.culturemesh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.Post;
import org.codethechange.culturemesh.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Drew Gregory on 03/27/18.
 */
public class ListNetworksFragment extends Fragment implements  NetworkSummaryAdapter.OnNetworkTapListener {
    View root;
    RecyclerView rv;
    TextView emptyText;
    final static String SELECTED_USER="seluser";
    final static String FIRST_TIME = "firsttime";
    RequestQueue queue;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListNetworksFragment newInstance(long selUser) {
        ListNetworksFragment fragment = new ListNetworksFragment();
        Bundle args = new Bundle();
        args.putLong(SELECTED_USER, selUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the intent, verify the action and get the query
        root = inflater.inflate(R.layout.rv_container, container, false);
        rv = root.findViewById(R.id.rv);
        //Say it's empty.
        ArrayList<Network> networks = new ArrayList<>();
        HashMap<String, Integer> counts = new HashMap<>();
        HashMap<String, Integer> users = new HashMap<>();
        final NetworkSummaryAdapter adapter = new NetworkSummaryAdapter(networks, counts, users, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        emptyText = root.findViewById(R.id.empty_text);
        emptyText.setText(getResources().getString(R.string.no_networks));
        //Fetch Data off UI thread.
        API.Get.userNetworks(queue, getArguments().getLong(SELECTED_USER, -1), new Response.Listener<NetworkResponse<ArrayList<Network>>>() {
            @Override
            public void onResponse(NetworkResponse<ArrayList<Network>> response) {
                // Cool! Now, for each network, we need to find the number of posts and the
                // number of users.
                if (!response.fail()) {
                    ArrayList<Network> nets = response.getPayload();
                    adapter.getNetworks().addAll(nets);
                    if (rv.getAdapter().getItemCount() > 0) {
                        //Hide empty text.
                        emptyText.setVisibility(View.GONE);
                    }
                    for (final Network net : nets) {
                        //TODO: size() is limited to int....
                        API.Get.networkUsers(queue, net.id, new Response.Listener<NetworkResponse<ArrayList<User>>>() {
                            @Override
                            public void onResponse(NetworkResponse<ArrayList<User>> response) {
                                if (!response.fail()) {
                                    /* getUserCounts() returns HashMap<network_id, user_count> */
                                    // This prevents possibility that the user counts are added in
                                    // wrong order.
                                    adapter.getUserCounts().put(net.id + "", response.getPayload().size());
                                } else {
                                    adapter.getUserCounts().put(net.id + "", 0);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                        API.Get.networkPosts(queue, net.id, new Response.Listener<NetworkResponse<List<Post>>>() {
                            @Override
                            public void onResponse(NetworkResponse<List<Post>> response) {
                                if (!response.fail()) {
                                    adapter.getPostCounts().put(net.id + "", response.getPayload().size());
                                } else {
                                    adapter.getPostCounts().put(net.id + "", 0);
                                }
                            }
                        });
                    }
                }
            }
        });
        return root;

    }


    /**
     * This is the onClick() passed to NetworkSummaryAdapter. Thus, this is executed when the user
     * taps on of the network card views.
     * We want to view the tapped network in TimelineActivity.
     * @param v the CardView.
     * @param network The Network
     */
    @Override
    public void onItemClick(View v, Network network) {
        //View Network in TimelineActivity
        //Commit selected network id to sharedPrefs.
        SharedPreferences prefs = getActivity().getSharedPreferences(API.SETTINGS_IDENTIFIER, Context.MODE_PRIVATE);
        prefs.edit().putLong(API.SELECTED_NETWORK, network.id).apply();
        Intent viewNetwork = new Intent(getActivity(), TimelineActivity.class);
        getActivity().startActivity(viewNetwork);
        getActivity().finish();
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
