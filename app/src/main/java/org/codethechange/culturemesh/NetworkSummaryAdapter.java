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
 * Created by Drew Gregory on 03/28/18.
 * This functions as the recyclerview adapter for the listview in ViewProfileActivity, where the user
 * can view other users' subscribed networks.
 */
public class NetworkSummaryAdapter extends RecyclerView.Adapter<NetworkSummaryAdapter.PostViewHolder>{
    private ArrayList<Network> networks;
    private HashMap<String, Integer> postCounts, userCounts;
    interface OnNetworkTapListener {
        void onItemClick(View v, Network network);
    }
    private OnNetworkTapListener listener;
    public ArrayList<Network> getNetworks() {
        return networks;
    }

    public HashMap<String, Integer> getPostCounts() {
        return postCounts;
    }

    public HashMap<String, Integer> getUserCounts() {
        return userCounts;
    }

    NetworkSummaryAdapter(ArrayList<Network> networks, HashMap<String, Integer> postCounts,
                          HashMap<String, Integer> userCounts, OnNetworkTapListener listener) {
        this.networks = networks;
        this.postCounts = postCounts;
        this.userCounts = userCounts;
        this.listener = listener;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_summary, parent, false);
        return new NetworkSummaryAdapter.PostViewHolder(v);
    }

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
