package org.codethechange.culturemesh;

import android.content.Context;
import android.graphics.Color;
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

import java.util.List;

/**
 * Created by Dylan Grosz (dgrosz@stanford.edu) on 11/10/17.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PostViewHolder> {
    private List<FeedItem> netPosts;
    private Context context;

    static class PostViewHolder extends RecyclerView.ViewHolder {
        boolean post = true;

        public boolean isPost() {
            return post;
        }

        CardView cv;
        TextView personName, username, content, timestamp, eventTitle, eventTime, eventLocation,
                eventDescription, comment1Name, comment1Text, comment2Name, comment2Text,
                viewMoreComments;
        ImageView personPhoto, postTypePhoto;
        ImageView[] images;
        LinearLayout eventDetailsLL;
        TextView eventTime;
        TextView eventLocation;
        TextView eventDescription;

        ConstraintLayout layout;
        RelativeLayout comment1Layout, comment2Layout;

        //TODO: Add support for onClick by adding viewholder ConstraintLayout items for
        //TODO: event time and event place.

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
        }



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
        }

        public void bind(final FeedItem item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    private final OnItemClickListener listener;

    public RVAdapter(List<FeedItem> netPosts, OnItemClickListener listener, Context context) {
        this.netPosts = netPosts;
        this.context = context;
        this.listener = listener;

    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, parent, false);
        return new PostViewHolder(v);
    }


    @Override
    public void onBindViewHolder(PostViewHolder pvh, int i) {
        final FeedItem item = netPosts.get(i);
        //Check if post or event.
        try{
            Post post = (Post) item;
            if (!pvh.isPost()) {
                pvh.hideEventViews();
            }
            String name = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();
            pvh.personName.setText(name);
            pvh.content.setText(post.getContent());
            pvh.postTypePhoto.setImageDrawable(null /* logic flow depending on post source */);
            pvh.timestamp.setText(post.getDatePosted().toString());
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
            //TODO: Picasso isn't loading all the images. Figure that out.
            Picasso.with(pvh.personPhoto.getContext()).load(post.getAuthor().getImgURL()).
                    into(pvh.personPhoto);

            //Next, insert 2 preview comments if there are any.
            if (post.comments != null) {
                if (post.comments.size() >= 1) {
                    //We have at least one post to display! Let's display it.
                    PostReply comment1 = post.comments.get(0);
                    pvh.comment1Name.setText(comment1.author.getUsername());
                    pvh.comment1Text.setText(comment1.replyText);
                    pvh.comment1Layout.setVisibility(View.VISIBLE);
                    if (post.comments.size() >= 2) {
                        //Now let's display comment 2.
                        PostReply comment2 = post.comments.get(1);
                        pvh.comment2Name.setText(comment2.author.getUsername());
                        pvh.comment2Text.setText(comment2.replyText);
                        pvh.comment2Layout.setVisibility(View.VISIBLE);
                        if (post.comments.size() > 2) {
                            //Even more comemnts? The user will have to check them out
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
            }
            Log.i("Image Link", post.getAuthor().getImgURL());
        } catch(ClassCastException e) {
            //It's an event.
            Event event = (Event) item;
            if (pvh.isPost()) {
                //Let's make all post-related stuff gone.
                pvh.hidePostViews();
                pvh.personPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_event_white_24px));
            }
            pvh.eventTitle.setText(event.getTitle());
            pvh.eventLocation.setText(event.getAddress());
            pvh.eventDescription.setText(event.getDescription());
            //TODO: Format event time.
            pvh.eventTime.setText(event.getTimeOfEvent().toString());
            pvh.bind(item, listener);

        }

    }



    public interface OnItemClickListener {
        void onItemClick(FeedItem item);
        //add more custom click functions here e.g. long click
    }

    @Override
    public int getItemCount() {
        return netPosts.size();
    }
}
