package org.codethechange.culturemesh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.Volley;

import org.codethechange.culturemesh.models.Network;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Fragment for displaying lists of clickable networks
 */
public class ListNetworksFragment extends Fragment implements
        NetworkSummaryAdapter.OnNetworkTapListener {

    /**
     * Inflated user interface created by {@link ListNetworksFragment#onCreate(Bundle)}
     */
    View root;

    /**
     * Scrollable list of networks
     */
    RecyclerView rv;

    /**
     * Displays {@link R.string#no_networks} if there are no networks to display
     */
    TextView emptyText;

    /**
     * Key stored in the fragment's arguments and whose value is the ID of the user whose networks
     * are to be displayed.
     */
    final static String SELECTED_USER="seluser";

    /**
     * Queue for asynchronous tasks
     */
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

    /**
     * Setup the user interface to display the list of networks and populate that list with the
     * result of calling {@link API.Get#userNetworks(RequestQueue, long, Response.Listener)}.
     * @param inflater Inflates the user interface specified in {@link R.layout#rv_container}
     * @param container Parent of the generated hierarchy of user interface elements
     * @param savedInstanceState Saved state to restore
     * @return Inflated user interface
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the intent, verify the action and get the query
        root = inflater.inflate(R.layout.rv_container, container, false);
        rv = root.findViewById(R.id.rv);
        queue = Volley.newRequestQueue(getActivity());
        //Say it's empty.
        ArrayList<Network> networks = new ArrayList<>();
        HashMap<String, Integer> counts = new HashMap<>();
        HashMap<String, Integer> users = new HashMap<>();
        final NetworkSummaryAdapter adapter = new NetworkSummaryAdapter(networks, counts, users, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        emptyText = root.findViewById(R.id.empty_text);
        emptyText.setText(getResources().getString(R.string.loading));
        //Fetch Data off UI thread.
        API.Get.userNetworks(queue, getArguments().getLong(SELECTED_USER, -1),
                new Response.Listener<NetworkResponse<ArrayList<Network>>>() {
            @Override
            public void onResponse(NetworkResponse<ArrayList<Network>> response) {
                // Cool! Now, for each network, we need to find the number of posts and the
                // number of users.
                if (!response.fail()) {
                    ArrayList<Network> nets = response.getPayload();
                    if (nets.size() > 0) {
                        //Hide empty text.
                        emptyText.setVisibility(View.GONE);
                    } else {
                        emptyText.setText(getResources().getString(R.string.no_networks));
                    }
                    for (final Network net : nets) {
                        API.Get.networkUserCount(queue, net.id, new Response.Listener<NetworkResponse<Long>>() {
                            @Override
                            public void onResponse(NetworkResponse<Long> response) {
                                if (!response.fail()) {
                                    /* getUserCounts() returns HashMap<network_id, user_count> */
                                    // This prevents possibility that the user counts are added in
                                    // wrong order.
                                    adapter.getUserCounts().put(net.id + "", response.getPayload().intValue());
                                } else {
                                    response.showErrorDialog(getActivity());
                                    adapter.getUserCounts().put(net.id + "", 0);
                                }
                                checkAndAddNetwork(net);
                            }
                        });
                        API.Get.networkPostCount(queue, net.id, new Response.Listener<NetworkResponse<Long>>() {
                            @Override
                            public void onResponse(NetworkResponse<Long> response) {
                                if (!response.fail()) {
                                    adapter.getPostCounts().put(net.id + "", response.getPayload().intValue());
                                } else {
                                    response.showErrorDialog(getActivity());
                                    adapter.getPostCounts().put(net.id + "", 0);
                                }
                                checkAndAddNetwork(net);
                            }
                        });
                    }
                } else {
                    response.showErrorDialog(getActivity());
                }
            }
        });
        return root;

    }

    /**
     * We can only add networks to the NetworkSummaryAdapter when the usercounts and postcounts
     * values have been fetched. Therefore, this function checks if BOTH the user count data
     * and post count data has been fetched for this network and only then adds it to
     * the adapter when it is finished.
     * @param network
     */
    private void checkAndAddNetwork(Network network) {
        NetworkSummaryAdapter adapter = (NetworkSummaryAdapter) rv.getAdapter();
        if (adapter.getPostCounts().containsKey(network.id + "") && adapter.getUserCounts().containsKey(network.id + "")) {
            adapter.getNetworks().add(network);
            adapter.notifyDataSetChanged();
        }
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
