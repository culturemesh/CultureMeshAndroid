package org.codethechange.culturemesh;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Post;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PostViewHolder> {
    private List<Post> netPosts;


    public static class PostViewHolder extends RecyclerView.ViewHolder implements org.codethechange.culturemesh.PostViewHolder {
        CardView cv;
        TextView personName;
        TextView username;
        TextView content;
        TextView timestamp;
        ImageView personPhoto;
        ImageView postTypePhoto;

        PostViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            username = (TextView) itemView.findViewById(R.id.username);
            content = (TextView) itemView.findViewById(R.id.content);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
            postTypePhoto = (ImageView) itemView.findViewById(R.id.post_type_photo);
        }
    }

    RVAdapter(List<Post> posts) {
        netPosts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_posts, parent, false);
        PostViewHolder pvh = new PostViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PostViewHolder pvh, int i) {
        Post post = netPosts.get(i);
        pvh.personName.setText(post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName());
        pvh.content.setText(post.getContent());
        pvh.postTypePhoto.setImageDrawable(null /* logic flow depending on post source */);
        pvh.timestamp.setText(post.getDatePosted().toString());
        pvh.username.setText(post.getAuthor().getUsername());
        Picasso.with(pvh.personPhoto.getContext()).load(post.getAuthor().getImgURL()).into(pvh.personPhoto);
        pvh.personPhoto.setImageResource(netPosts.get(i).getAuthor().); //user doesn't have image?
    }

    @Override
    public int getItemCount() {
        return netPosts.size();
    }
}
