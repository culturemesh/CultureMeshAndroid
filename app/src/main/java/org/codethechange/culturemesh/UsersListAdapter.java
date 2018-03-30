package org.codethechange.culturemesh;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.User;
import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;

/**
 * Created by Drew Gregory on 03/30/18
 * This Adapter is used for viewing the subscribed users of a network.
 */
public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder>{
    Context context;
    ArrayList<String> userNames, imageURLS;
    long[] userIds;

    public UsersListAdapter(Context context, @NonNull ArrayList<String> userNames,
                            @NonNull ArrayList<String> imageURLS, long[] userIds) {
        this.context = context;
        this.userNames = userNames;
        this.imageURLS = imageURLS;
        this.userIds = userIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UsersListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.fullName.setText(userNames.get(position));
        Picasso.with(holder.profilePicture.getContext()).load(imageURLS.get(position)).into(holder.profilePicture);
    }

    @Override
    public int getItemCount() {
        return Math.min(userNames.size(), Math.min(userIds.length, imageURLS.size()));
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView fullName;
        ImageView profilePicture;

        ViewHolder(View v) {
            super(v);
            profilePicture = v.findViewById(R.id.profile_picture);
            fullName = v.findViewById(R.id.full_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //We want to view the user in greater detail in ViewProfileActivity!
                    Intent intent = new Intent(context, ViewProfileActivity.class);
                    intent.putExtra(ViewProfileActivity.SELECTED_USER, userIds[getAdapterPosition()]);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
