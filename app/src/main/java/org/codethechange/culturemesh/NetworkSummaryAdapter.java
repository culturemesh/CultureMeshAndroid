package org.codethechange.culturemesh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codethechange.culturemesh.models.Network;

import java.util.ArrayList;

/**
 * Created by Drew Gregory on 03/28/18.
 * This functions as the recyclerview adapter for the listview in ViewProfileActivity, where the user
 * can view other users' subscribed networks.
 */
public class NetworkSummaryAdapter extends RecyclerView.Adapter<NetworkSummaryAdapter.PostViewHolder>{
    private ArrayList<Network> networks;
    private ArrayList<Integer> postCounts, userCounts;
    interface OnNetworkTapListener {
        void onItemClick(View v, Network network);
    }
    private OnNetworkTapListener listener;
    public ArrayList<Network> getNetworks() {
        return networks;
    }

    public ArrayList<Integer> getPostCounts() {
        return postCounts;
    }

    public ArrayList<Integer> getUserCounts() {
        return userCounts;
    }

    NetworkSummaryAdapter(ArrayList<Network> networks, ArrayList<Integer> postCounts,
                          ArrayList<Integer> userCounts, OnNetworkTapListener listener) {
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
        final Network network = networks.get(position);
        if (network.networkClass) {//fromLoc
            holder.fromLocation.setText(network.fromLocation.shortName());
        } else {
            holder.fromLocation.setText(network.language.name);
        }
        holder.nearLocation.setText(network.nearLocation.shortName());
        holder.postCount.setText(FormatManager.abbreviateNumber(postCounts.get(position)));
        holder.subscribedUserCount.setText(FormatManager.abbreviateNumber(userCounts.get(position)));
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
