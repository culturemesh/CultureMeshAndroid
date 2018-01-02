package org.codethechange.culturemesh;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Post;

import java.util.List;

//progress circle
//refresh posts


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PostViewHolder> {
    private List<Post> netPosts;


    static class PostViewHolder extends RecyclerView.ViewHolder implements org.codethechange.culturemesh.PostViewHolder {
        CardView cv;
        TextView personName;
        TextView username;
        TextView content;
        TextView timestamp;
        ImageView personPhoto;
        ImageView postTypePhoto;
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
        }
    }

    RVAdapter(List<Post> posts) {
        netPosts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostViewHolder pvh, int i) {
        Post post = netPosts.get(i);
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
                into(pvh.personPhoto); //set logic for if no img, or is this built-in?
    }

    @Override
    public int getItemCount() {
        return netPosts.size();
    }
}
