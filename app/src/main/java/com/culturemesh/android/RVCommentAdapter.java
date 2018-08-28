package com.culturemesh.android;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.culturemesh.android.models.Event;
import com.culturemesh.android.models.FeedItem;
import com.culturemesh.android.models.Post;
import com.culturemesh.android.models.PostReply;

import java.util.List;

/**
 * Adapter that populates a UI list with comments
 */
public class RVCommentAdapter extends RecyclerView.Adapter<RVCommentAdapter.PostReplyViewHolder> {

    /**
     * Comments to show in the list
     */
    private List<PostReply> comments;

    /**
     * {@link Context} of the list where the comments are displayed
     */
    private Context context;

    /**
     * Holder for the parts of each {@link View} in the list
     */
    static class PostReplyViewHolder extends RecyclerView.ViewHolder {
        boolean reply = true;

        public boolean isPostReply() {
            return reply;
        }

        /**
         * The {@link View} to display a single list item
         */
        CardView cv;

        /**
         * Textual components of the display for a single list item
         */
        TextView personName, username, content, timestamp;

        /**
         * Image components of the display for a single list item
         */
        ImageView personPhoto, postTypePhoto;

        /**
         * Array of image components associated with a list item
         */
        ImageView[] images;

        /**
         * Layout within which the list item components are arranged
         */
        ConstraintLayout layout;

        //TODO: Add support for onClick by adding viewholder ConstraintLayout items for
        //TODO: event time and event place.

        /**
         * Instantiate instance fields with {@link View}s using {@link View#findViewById(int)}
         * @param itemView Item display whose fields are stored in instance fields
         */
        PostReplyViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_comment);
            personName = itemView.findViewById(R.id.person_name_comment);
            username = itemView.findViewById(R.id.username_comment);
            content = itemView.findViewById(R.id.content_comment);
            timestamp = itemView.findViewById(R.id.timestamp_comment);
            personPhoto = itemView.findViewById(R.id.person_photo_comment);
            postTypePhoto = itemView.findViewById(R.id.post_type_photo_comment);
            images = new ImageView[3];
            images[0] = itemView.findViewById(R.id.attachedImage1_comment);
            images[1] = itemView.findViewById(R.id.attachedImage2_comment);
            images[2] = itemView.findViewById(R.id.attachedImage3_comment);
            layout = itemView.findViewById(R.id.layout_comment);
        }

        /**
         * Attach a listener to an item in the displayed list
         * @param item Item in the list to bind the listener to
         * @param listener Listener to bind to the list item
         */
        public void bind(final PostReply item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCommentClick(item);
                }
            });
        }
    }

    /**
     * Listener to handle clicks on list items
     */
    private final OnItemClickListener listener;

    /**
     * Store parameters in instance fields
     * @param comments List of comments to display in scrollable list to user
     * @param listener Will be called whenever an item is clicked
     * @param context {@link Context} within which the list will be displayed
     */
    public RVCommentAdapter(List<PostReply> comments, OnItemClickListener listener, Context context) {
        this.comments = comments;
        this.listener = listener;
    }

    /**
     * Create a {@link PostReplyViewHolder} for {@code parent} with a {@link View} inflated
     * from {@link R.layout#comment_view}
     * @param parent {@link ViewGroup} within which to create the {@link PostReplyViewHolder}
     * @param viewType Not used
     * @return The {@link PostReplyViewHolder} associated with the inflated {@link View}
     */
    @Override
    public PostReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view, parent, false);
        return new PostReplyViewHolder(v);
    }

    /**
     * Fill in the fields of {@code pvh} with the information stored in the {@link PostReply} at
     * position {@code i} in the list of comments
     * @param pvh {@link View} in the list whose fields will be filled-in
     * @param i Index of {@link PostReply} in {@link RVCommentAdapter#comments} to use as the source
     *          of information to fill with
     */
    @Override
    public void onBindViewHolder(PostReplyViewHolder pvh, int i) {
        final PostReply comment = comments.get(i);
        //Check if post or event.
        try{
            String name = comment.author.getFirstName() + " " + comment.author.getLastName();
            pvh.personName.setText(name);
            pvh.content.setText(FormatManager.parseText(comment.replyText, "#FFFFFF"));
            pvh.postTypePhoto.setImageDrawable(null /* logic flow depending on post source */);
            pvh.timestamp.setText(comment.replyDate);
            pvh.username.setText(comment.author.getUsername());
            pvh.bind(comment, listener);
            Picasso.with(pvh.personPhoto.getContext()).load(comment.author.getImgURL()).
                    into(pvh.personPhoto);
            Log.i("Image Link", comment.author.getImgURL());
        } catch(ClassCastException e) {
            //error oh no
        }
    }

    /**
     * Interface implemented by any listener for item clicks
     */
    public interface OnItemClickListener {

        /**
         * Handles clicks on a list item
         * @param item Item in the list that was clicked
         */
        void onCommentClick(PostReply item);
        //add more custom click functions here e.g. long click
    }

    /**
     * Get the number of comments in the list
     * @return Number of comments in list
     */
    @Override
    public int getItemCount() {
        return comments.size();
    }
}
