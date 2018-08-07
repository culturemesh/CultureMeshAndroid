package org.codethechange.culturemesh;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codethechange.culturemesh.models.Network;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This functions as the recyclerview adapter for the listview in ViewProfileActivity, where the user
 * can view other users' subscribed networks.
 */
public class NetworkSummaryAdapter extends RecyclerView.Adapter<NetworkSummaryAdapter.PostViewHolder>{

    /**
     * {@link Network}s to show in the list
     */
    private ArrayList<Network> networks;

    /**
     * Mappings from {@link Network#id} (as a {@link String}) to its counts of
     * {@link org.codethechange.culturemesh.models.Post}s and
     * {@link org.codethechange.culturemesh.models.User}s
     */
    private HashMap<String, Integer> postCounts, userCounts;

    /**
     * Interface for all listeners for clicks on list items
     */
    interface OnNetworkTapListener {
        void onItemClick(View v, Network network);
    }

    /**
     * Listener for clicks on items
     */
    private OnNetworkTapListener listener;

    /**
     * Get the list of {@link Network}s
     * @return List of {@link Network}s being shown in the list
     */
    public ArrayList<Network> getNetworks() {
        return networks;
    }

    /**
     * Get the mappings between {@link Network#id} (as a {@link String}) and the number of
     * {@link org.codethechange.culturemesh.models.Post}s in that network.
     * @return Mappings that relate {@link Network} ID to the number of
     * {@link org.codethechange.culturemesh.models.Post}s in the network
     */
    public HashMap<String, Integer> getPostCounts() {
        return postCounts;
    }

    /**
     * Get the mappings between {@link Network#id} (as a {@link String}) and the number of
     * {@link org.codethechange.culturemesh.models.User}s in that network.
     * @return Mappings that relate {@link Network} ID to the number of
     * {@link org.codethechange.culturemesh.models.User}s in the network
     */
    public HashMap<String, Integer> getUserCounts() {
        return userCounts;
    }

    /**
     * Initialize instance fields with parameters
     * @param networks List of {@link Network}s to display
     * @param postCounts Mapping from the ID of each {@link Network} to the number of
     *                   {@link org.codethechange.culturemesh.models.Post}s it contains
     * @param userCounts Mapping from the ID of each {@link Network} to the number of
     *                   {@link org.codethechange.culturemesh.models.User}s it contains
     * @param listener Listener to handle clicks on list items
     */
    NetworkSummaryAdapter(ArrayList<Network> networks, HashMap<String, Integer> postCounts,
                          HashMap<String, Integer> userCounts, OnNetworkTapListener listener) {
        this.networks = networks;
        this.postCounts = postCounts;
        this.userCounts = userCounts;
        this.listener = listener;
    }

    /**
     * Create a new {@link NetworkSummaryAdapter.PostViewHolder} from the {@link View} created
     * by inflating {@link R.layout#network_summary}
     * @param parent Parent for created {@link View} used to create the new
     *               {@link NetworkSummaryAdapter.PostViewHolder}
     * @param viewType Not used
     * @return ViewHolder that has been created using an inflated {@link View}
     */
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_summary, parent, false);
        return new NetworkSummaryAdapter.PostViewHolder(v);
    }

    /**
     * Fill the fields of {@code holder} with the information stored in the {@link Network} at
     * index {@code position} in {@link NetworkSummaryAdapter#networks}
     * @param holder ViewHolder whose fields to fill in
     * @param position Index of {@link Network} in {@link NetworkSummaryAdapter#networks} whose
     *                 information will be used to fill in the fields of {@code holder}
     */
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        if (position >= networks.size() || position >= postCounts.size()) return;
        final Network network = networks.get(position);
        if (network.isLocationBased()) {//fromLoc
            holder.fromLocation.setText(network.fromLocation.getListableName());
        } else {
            holder.fromLocation.setText(network.language.name);
        }
        holder.nearLocation.setText(network.nearLocation.getListableName());
        holder.postCount.setText(FormatManager.abbreviateNumber(postCounts.get(network.id + "")));
        Log.i("NetworkSummary", network.toString() + " " + userCounts.get(network.id + ""));
        holder.subscribedUserCount.setText(FormatManager.abbreviateNumber(userCounts.get(network.id + "")));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, network);
            }
        });
    }

    /**
     * Get the number of {@link Network}s are stored in the list
     * @return Number of items in the list
     */
    @Override
    public int getItemCount() {
        return networks.size();
    }

    /**
     * This ViewHolder is for network_summary, a CardView for networks.
     */
    class PostViewHolder extends  RecyclerView.ViewHolder{
        TextView fromLocation; //Could also store language.
        TextView nearLocation;
        TextView subscribedUserCount;
        TextView postCount;

        PostViewHolder(View itemView) {
            super(itemView);
            fromLocation = itemView.findViewById(R.id.from_location);
            nearLocation = itemView.findViewById(R.id.near_location);
            subscribedUserCount = itemView.findViewById(R.id.population_number);
            postCount = itemView.findViewById(R.id.posts_number);
        }
    }
}
