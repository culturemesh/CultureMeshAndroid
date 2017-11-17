package org.codethechange.culturemesh;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        PostViewHolder pvh = new PostViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PostViewHolder pvh, int i) {
        pvh.personName.setText(netPosts.get(i).name);
        pvh.content.setText(netPosts.get(i).age);
        pvh.personPhoto.setImageResource(netPosts.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return netPosts.size();
    }
}
