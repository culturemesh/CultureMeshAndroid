package org.codethechange.culturemesh;

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

import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.FeedItem;
import org.codethechange.culturemesh.models.Post;
import org.codethechange.culturemesh.models.PostReply;

import java.util.List;

/**
 * Created by Dylan Grosz (dgrosz@stanford.edu) on 11/10/17.
 */
public class RVCommentAdapter extends RecyclerView.Adapter<RVCommentAdapter.PostReplyViewHolder> {
    private List<PostReply> comments;
    private Context context;

    static class PostReplyViewHolder extends RecyclerView.ViewHolder {
        boolean reply = true;

        public boolean isPostReply() {
            return reply;
        }

        CardView cv;
        TextView personName, username, content, timestamp;
        ImageView personPhoto, postTypePhoto;
        ImageView[] images;

        ConstraintLayout layout;

        //TODO: Add support for onClick by adding viewholder ConstraintLayout items for
        //TODO: event time and event place.

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


        public void bind(final PostReply item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCommentClick(item);
                }
            });
        }

    }

    private final OnItemClickListener listener;

    public RVCommentAdapter(List<PostReply> comments, OnItemClickListener listener, Context context) {
        this.comments = comments;
        this.context = context;
        this.listener = listener;

    }

    @Override
    public PostReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view, parent, false);
        return new PostReplyViewHolder(v);
    }


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

            //TODO: Picasso isn't loading all the images. Figure that out.
            Picasso.with(pvh.personPhoto.getContext()).load(comment.author.getImgURL()).
                    into(pvh.personPhoto);
            Log.i("Image Link", comment.author.getImgURL());
        } catch(ClassCastException e) {
            //error oh no
        }

    }



    public interface OnItemClickListener {
        void onCommentClick(PostReply item);
        //add more custom click functions here e.g. long click
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
