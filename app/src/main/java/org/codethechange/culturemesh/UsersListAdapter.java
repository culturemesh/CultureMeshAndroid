package org.codethechange.culturemesh;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.User;

import java.util.ArrayList;

/**
 * This Adapter is used for viewing the subscribed users of a network.
 */
public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder>{

    /**
     * {@link Context} in which the list is being displayed
     */
    Context context;

    /**
     * List of {@link User}s to display in the list
     */
    private ArrayList<User> users;

    /**
     * Create a new object by instantiating instance fields with parameters
     * @param context {@link Context} in which the list is displayed
     * @param users List of {@link User}s to display in the list
     */
    public UsersListAdapter(Context context, @NonNull ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    /**
     * Create a new {@link UsersListAdapter.ViewHolder} from a {@link View} inflated from
     * {@link R.layout#user_list_item} and with parent {@code parent}
     * @param parent Parent for the {@link View} used to create the new {@link UsersListAdapter}
     * @param viewType Not used.
     * @return The created {@link UsersListAdapter.ViewHolder}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UsersListAdapter.ViewHolder(v);
    }

    /**
     * Fill the name and profile picture fields of {@code holder} with the contents of an item in
     * {@link UsersListAdapter#users}.
     * @param holder {@link ViewHolder} whose fields to fill with information
     * @param position Index of item in list of users to use as source of information for filling
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.fullName.setText(getUsers().get(position).username);
        Picasso.with(holder.profilePicture.getContext()).load(getUsers().get(position).getImgURL()).
                into(holder.profilePicture);
    }

    /**
     * Get the number of items in the list of objects to display
     * @return Number of items in list to display
     */
    @Override
    public int getItemCount() {
        return getUsers().size();
    }

    /**
     * Get the list of objects to display
     * @return List of objects represented in list
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Holder of UI elements that compose each element of the displayed list
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * {@link User}'s name
         */
        TextView fullName;

        /**
         * {@link User}'s profile picture
         */
        ImageView profilePicture;

        /**
         * Initialize instance fields with fields in {@code v} and set the listener for clicks to
         * open a more detailed view of the profile in {@link ViewProfileActivity}
         * @param v {@link View} to use to display the list item
         */
        ViewHolder(View v) {
            super(v);
            profilePicture = v.findViewById(R.id.profile_picture);
            fullName = v.findViewById(R.id.full_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //We want to view the user in greater detail in ViewProfileActivity!
                    Intent intent = new Intent(context, ViewProfileActivity.class);
                    intent.putExtra(ViewProfileActivity.SELECTED_USER, users.get(getAdapterPosition()).id);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
