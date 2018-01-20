package org.codethechange.culturemesh;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.FeedItem;
import org.codethechange.culturemesh.models.Post;

import java.util.List;

//progress circle
//refresh posts


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PostViewHolder> {
    private List<FeedItem> netPosts;
    private Context context;

    static class PostViewHolder extends RecyclerView.ViewHolder implements org.codethechange.culturemesh.PostViewHolder {
        boolean post = true;

        public boolean isPost() {
            return post;
        }

        CardView cv;
        TextView personName;
        TextView username;
        TextView content;
        TextView timestamp;
        ImageView personPhoto;
        ImageView postTypePhoto;
        TextView eventTitle;
        LinearLayout eventDetailsLL;
        TextView eventTime;
        TextView eventLocation;
        TextView eventDescription;
        //TODO: Add support for onClick by adding viewholder ConstraintLayout items for
        //TODO: event time and event place.
        ImageView[] images;

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

        }

        void hidePostViews() {
            post = false;
            personName.setVisibility(View.GONE);
            username.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            timestamp.setVisibility(View.GONE);
            postTypePhoto.setVisibility(View.GONE);
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

        }

    }

    RVAdapter(List<FeedItem> posts, Context context) {
        netPosts = posts;
        this.context = context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostViewHolder pvh, int i) {
        FeedItem item = netPosts.get(i);
        //Check if post or event.
        try{
            Post post = (Post) item;
            if (!pvh.isPost()) {
                pvh.hideEventViews();
                pvh.cv.setCardBackgroundColor(context.getResources().
                        getColor(R.color.colorPrimaryDark));
            }
            String name = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();
            pvh.personName.setText(name);
            pvh.content.setText(post.getContent());
            pvh.postTypePhoto.setImageDrawable(null /* logic flow depending on post source */);
            pvh.timestamp.setText(post.getDatePosted().toString());
            pvh.username.setText(post.getAuthor().getUsername());
            if (post.getImageLink() != null || post.getVideoLink() != null ) {
                //TODO: Figure out how to display videos
                //TODO: Figure out format for multiple pictures. Assuming separated by commas.
                String[] links = post.getImageLink().split(",");
                for (int j = 0;  j < links.length; j++) {
                    Picasso.with(pvh.images[j].getContext()).load(links[j]).into(pvh.images[j]);
                }
            }
            Picasso.with(pvh.personPhoto.getContext()).load(post.getAuthor().getImgURL()).
                    into(pvh.personPhoto);
        } catch(ClassCastException e) {
            //It's an event.
            Event event = (Event) item;
            if (pvh.isPost()) {
                //Let's make all post-related stuff gone.
                pvh.hidePostViews();
                pvh.cv.setCardBackgroundColor(context.getResources().
                        getColor(R.color.colorAccent));
       //         pvh.personPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_event_white_24px));
            }
            pvh.eventTitle.setText(event.getTitle());
            pvh.eventLocation.setText(event.getAddress());
            pvh.eventDescription.setText(event.getDescription());
            //TODO: Format event time.
            pvh.eventTime.setText(event.getTimeOfEvent().toString());

        }
    }



    @Override
    public int getItemCount() {
        return netPosts.size();
    }
}
