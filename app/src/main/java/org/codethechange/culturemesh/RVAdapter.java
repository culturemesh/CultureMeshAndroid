package org.codethechange.culturemesh;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.FeedItem;
import org.codethechange.culturemesh.models.Post;
import org.codethechange.culturemesh.models.PostReply;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adapter that provides the {@link Post}s and/or {@link Event}s of a
 * {@link org.codethechange.culturemesh.models.Network} to displayed, scrollable lists
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PostViewHolder> {

    /**
     * Get the items being represented as elements of the displayed list (not just the ones
     * currently visible).
     * @return Items represented as elements in the displayed list
     */
    public List<FeedItem> getNetPosts() {
        return netPosts;
    }

    /**
     * Get the events in this network that the user is attending, which affects
     * some aspects of the event UI.
     * @return a set of the ids of the events.
     */
    public Set<Long> getUserAttendingEvents() {
        return userAttendingEvents;
    }

    /**
     * This contains the events in this network that the user is attending, which affects
     * some aspects of the event UI.
     */
    private Set<Long> userAttendingEvents;

    /**
     * All of the items that are represented in the displayed list, including those not currently
     * visible due to scrolling.
     */
    private List<FeedItem> netPosts;

    /**
     * The {@link Context} in which the list is displayed
     */
    private Context context;

    /**
     * Stores the {@link View} elements of each item in the displayed list. Instances of this class
     * are linked to objects in {@link RVAdapter#netPosts} by
     * {@link RVAdapter#onBindViewHolder(PostViewHolder, int)}, which fills the fields with content
     * from the object.
     */
    static class PostViewHolder extends RecyclerView.ViewHolder {

        /**
         * Whether this instance is configured to display the information for a {@link Post} or for
         * a {@link Event}. {@code true} if it is for a {@link Post}
         */
        boolean post = true;

        /**
         * Check whether the instance is displaying information for a {@link Post} or a {@link Event}
         * @return {@code true} if displaying information for a {@link Post}. {@code false} if
         * for an {@link Event}
         */
        public boolean isPost() {
            return post;
        }

        /**
         * The {@link View} for the displayed list item
         */
        CardView cv;

        /**
         * Text fields for both {@link Post} and {@link Event} information
         */
        TextView personName, username, content, timestamp, eventTitle, comment1Name,
                 comment1Text, comment2Name, comment2Text, viewMoreComments, attending;

        /**
         * Display images with the displayed list item
         */
        ImageView personPhoto, postTypePhoto;

        /**
         * Array of all image displays
         */
        ImageView[] images;

        /**
         * Layout within which the details section of the displayed list item is defined
         */
        LinearLayout eventDetailsLL;

        /**
         * Time of the {@link Event}
         */
        TextView eventTime;

        /**
         * Where the {@link Event} will take place
         */
        TextView eventLocation;

        /**
         * Description of the {@link Event}
         */
        TextView eventDescription;

        /**
         * Layout within which the displayed list item is defined
         */
        ConstraintLayout layout;

        /**
         * Layout within which the two displayed comments are defined
         */
        RelativeLayout comment1Layout, comment2Layout;

        //TODO: Add support for onClick by adding viewholder ConstraintLayout items for
        //TODO: event time and event place.

        /**
         * Initialize instance fields by retrieving UI elements by their IDs in the provided
         * {@link View}
         * @param itemView Canvas upon which the displayed list item is built. Should already have
         *                 the needed fields and other elements.
         */
        PostViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            personName = itemView.findViewById(R.id.person_name);
            username = itemView.findViewById(R.id.username);
            content = itemView.findViewById(R.id.content);
            timestamp = itemView.findViewById(R.id.timestamp);
            personPhoto = itemView.findViewById(R.id.person_photo);
            postTypePhoto = itemView.findViewById(R.id.post_type_photo);
            images = new ImageView[3];
            images[0] = itemView.findViewById(R.id.attachedImage1);
            images[1] = itemView.findViewById(R.id.attachedImage2);
            images[2] = itemView.findViewById(R.id.attachedImage3);
            eventTitle = itemView.findViewById(R.id.event_title);
            eventDetailsLL = itemView.findViewById(R.id.event_details_linear_layout);
            eventTime = itemView.findViewById(R.id.event_time);
            eventLocation = itemView.findViewById(R.id.event_location);
            eventDescription = itemView.findViewById(R.id.event_description);
            layout = itemView.findViewById(R.id.layout);
            comment1Layout = itemView.findViewById(R.id.comment1_layout);
            comment2Layout = itemView.findViewById(R.id.comment2_layout);
            comment1Text = itemView.findViewById(R.id.comment1_text);
            comment1Name = itemView.findViewById(R.id.comment1_name);
            comment2Text = itemView.findViewById(R.id.comment2_text);
            comment2Name = itemView.findViewById(R.id.comment2_name);
            viewMoreComments = itemView.findViewById(R.id.view_more_comments);
            attending = itemView.findViewById(R.id.attending_string);
        }

        /**
         * This instance will display the information from a {@link Event}, so hide all the fields
         * that describe {@link Post}s
         */
        void hidePostViews() {
            ConstraintSet constraints = new ConstraintSet();
            constraints.clone(layout);
            constraints.connect(R.id.comment1_layout, ConstraintSet.TOP, R.id.event_description, ConstraintSet.BOTTOM);
            constraints.applyTo(layout);
            post = false;
            personName.setVisibility(View.GONE);
            username.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            timestamp.setVisibility(View.GONE);
            postTypePhoto.setVisibility(View.GONE);
            comment1Layout.setVisibility(View.GONE);
            comment2Layout.setVisibility(View.GONE);
            viewMoreComments.setVisibility(View.GONE);
            for (ImageView image : images) {
                image.setVisibility(View.GONE);
            }
            eventTitle.setVisibility(View.VISIBLE);
            eventDetailsLL.setVisibility(View.VISIBLE);
            eventTime.setVisibility(View.VISIBLE);
            eventLocation.setVisibility(View.VISIBLE);
            eventDescription.setVisibility(View.VISIBLE);

        }

        /**
         * This instance will display the information from a {@link Post}, so hide all the fields
         * that describe {@link Event}s
         */
        void hideEventViews()
        {
            ConstraintSet constraints = new ConstraintSet();
            constraints.clone(layout);
            constraints.connect(R.id.comment1_layout, ConstraintSet.TOP, R.id.timestamp, ConstraintSet.BOTTOM);
            constraints.applyTo(layout);
            post = true;
            eventTitle.setVisibility(View.GONE);
            eventDetailsLL.setVisibility(View.GONE);
            eventTime.setVisibility(View.GONE);
            eventLocation.setVisibility(View.GONE);
            eventDescription.setVisibility(View.GONE);
            for (ImageView image : images) {
                image.setVisibility(View.VISIBLE);
            }
            personName.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            content.setVisibility(View.VISIBLE);
            timestamp.setVisibility(View.VISIBLE);
            postTypePhoto.setVisibility(View.VISIBLE);
            viewMoreComments.setVisibility(View.VISIBLE);
            attending.setVisibility(View.GONE);
        }

        /**
         * Set the displayed list item's listener that handles clicks to that of the provided
         * listener
         * @param item The clicked-on item which will be passed to the listener's
         *             {@link OnItemClickListener#onItemClick(FeedItem)}method when the item is
         *             clicked
         * @param listener Listener to handle all clicks on items in the list
         */
        public void bind(final FeedItem item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    /**
     * The listener that will handle all clicks on items in the list
     */
    private final OnItemClickListener listener;

    /**
     * Initialize instance fields with provided parameters
     * @param netPosts List of objects to represent in the displayed list
     * @param listener Listener to handle clicks on list tiems
     * @param context {@link Context} in which the list will be displayed
     */
    public RVAdapter(List<FeedItem> netPosts, OnItemClickListener listener, Context context) {
        this.netPosts = netPosts;
        this.context = context;
        this.listener = listener;
        userAttendingEvents = new HashSet<Long>();
    }

    /**
     * Create a new {@link PostViewHolder} from a {@link View} created by inflating the layout
     * described by {@link R.layout#post_view}.
     * @param parent Parent for created {@link View} used to create {@link PostViewHolder}
     * @param viewType Not used
     * @return A new {@link PostViewHolder} for inclusion in the displayed list
     */
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, parent, false);
        return new PostViewHolder(v);
    }

    /**
     * Link the provided {@link PostViewHolder} to an object in the list {@link RVAdapter#netPosts},
     * which is used to fill the fields in the {@link PostViewHolder}
     * @param pvh Item in the displayed list whose fields to fill with information
     * @param i Index of object in {@link RVAdapter#netPosts} that will serve as the source of
     *          information to fill into the displayed list item
     */
    @Override
    public void onBindViewHolder(PostViewHolder pvh, int i) {
        final FeedItem item = netPosts.get(i);
        //Check if post or event.
        if (item instanceof Post) {

            Post post = (Post) item;
            if (!pvh.isPost()) {
                pvh.hideEventViews();
            }
            String name = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();
            pvh.personName.setText(name);
            pvh.content.setText(FormatManager.parseText(post.getContent(), "#4989c1"));
            pvh.postTypePhoto.setImageDrawable(null /* TODO: logic flow depending on post source */);
            pvh.timestamp.setText(post.getDatePosted());
            pvh.username.setText(post.getAuthor().getUsername());
            pvh.bind(item, listener);
            if (post.getImageLink() != null || post.getVideoLink() != null ) {
                //TODO: Figure out how to display videos
                //TODO: Figure out format for multiple pictures. Assuming separated by commas.
                String[] links = post.getImageLink().split(",");
                for (int j = 0;  j < links.length; j++) {
                    if (links[j].length() > 0) {
                        Picasso.with(pvh.images[j].getContext()).load(links[j]).into(pvh.images[j]);
                    }
                }
            }
            Picasso.with(pvh.personPhoto.getContext()).load(post.getAuthor().getImgURL()).
                    into(pvh.personPhoto);

            //Next, insert 2 preview comments if there are any.
            if (post.comments != null) {
                if (post.comments.size() >= 1) {
                    //We have at least one post to display! Let's display it.
                    PostReply comment1 = post.comments.get(0);
                    pvh.comment1Name.setText(comment1.author.getUsername());
                    pvh.comment1Text.setText(FormatManager.parseText(comment1.replyText, "#FFFFFF"));
                    pvh.comment1Layout.setVisibility(View.VISIBLE);
                    if (post.comments.size() >= 2) {
                        //Now let's display comment 2.
                        PostReply comment2 = post.comments.get(1);
                        pvh.comment2Name.setText(comment2.author.getUsername());
                        pvh.comment2Text.setText(FormatManager.parseText(comment2.replyText, "#FFFFFF"));
                        pvh.comment2Layout.setVisibility(View.VISIBLE);
                        if (post.comments.size() > 2) {
                            //Even more comments? The user will have to check them out
                            // in the DetailPostActivity.
                            pvh.viewMoreComments.setVisibility(View.VISIBLE);
                        }
                    } else {
                        pvh.comment2Layout.setVisibility(View.GONE);
                        pvh.viewMoreComments.setVisibility(View.GONE);
                    }
                } else {
                    pvh.comment1Layout.setVisibility(View.GONE);
                    pvh.comment2Layout.setVisibility(View.GONE);
                    pvh.viewMoreComments.setVisibility(View.GONE);
                }
            } else {
                pvh.comment1Layout.setVisibility(View.GONE);
                pvh.comment2Layout.setVisibility(View.GONE);
                pvh.viewMoreComments.setVisibility(View.GONE);
            }
        } else {
            //It's an event.
            Event event = (Event) item;
            if (pvh.isPost()) {
                //Let's make all post-related stuff gone.
                pvh.hidePostViews();
                // Check if user joined event.
                boolean isAttending = userAttendingEvents.contains(event.id);
                pvh.personPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_event_white_24px));
                if (isAttending) {
                    //Show attending text.
                    pvh.attending.setVisibility(View.VISIBLE);
                } else {
                    pvh.attending.setVisibility(View.GONE);
                }
            }
            pvh.eventTitle.setText(event.getTitle());
            pvh.eventLocation.setText(event.getAddress());
            pvh.eventDescription.setText(event.getDescription());
            pvh.eventTime.setText(event.getTimeOfEvent().toString());
            pvh.bind(item, listener);
        }
    }

    /**
     * Interface listeners for clicks on items must implement
     */
    public interface OnItemClickListener {

        /**
         * Handle a click on the provided item
         * @param item Item that was clicked on
         */
        void onItemClick(FeedItem item);
        //add more custom click functions here e.g. long click
    }

    /**
     * Get the number of items to display
     * @return Number of items in the list of items to display ({@link RVAdapter#netPosts})
     */
    @Override
    public int getItemCount() {
        return netPosts.size();
    }
}
