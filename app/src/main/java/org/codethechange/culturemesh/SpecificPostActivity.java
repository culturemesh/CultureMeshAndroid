package org.codethechange.culturemesh;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.FeedItem;
import org.codethechange.culturemesh.models.Post;

import java.math.BigInteger;

public class SpecificPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_post);

        Intent intent = getIntent();
        String strID = intent.getStringExtra("name");
        BigInteger postID = new BigInteger("0"); //new BigInteger(strID);
        FeedItem item = API.Get.post(postID); //re-get post (can also switch to passing data as a bundle for data persistence reasons)

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
        ImageView[] images;
        if(true /*no API error */) {

            cv = findViewById(R.id.cv);
            personName = findViewById(R.id.person_name);
            username = findViewById(R.id.username);
            content = findViewById(R.id.content);
            timestamp = findViewById(R.id.timestamp);
            personPhoto = findViewById(R.id.person_photo);
            postTypePhoto = findViewById(R.id.post_type_photo);
            images = new ImageView[3];
            images[0] = findViewById(R.id.attachedImage1);
            images[1] = findViewById(R.id.attachedImage2);
            images[2] = findViewById(R.id.attachedImage3);
            eventTitle = findViewById(R.id.event_title);
            eventDetailsLL = findViewById(R.id.event_details_linear_layout);
            eventTime = findViewById(R.id.event_time);
            eventLocation = findViewById(R.id.event_location);
            eventDescription = findViewById(R.id.event_description);

            if(true /*item.isPost()*/) {
                Post post = (Post) item;
                String name = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();
                personName.setText(name);
                content.setText(post.getContent());
                postTypePhoto.setImageDrawable(null /* logic flow depending on post source */);
                timestamp.setText(post.getDatePosted().toString());
                username.setText(post.getAuthor().getUsername());
                if (post.getImageLink() != null || post.getVideoLink() != null ) {
                    //TODO: Figure out how to display videos
                    //TODO: Figure out format for multiple pictures. Assuming separated by commas.
                    String[] links = post.getImageLink().split(",");
                    for (int j = 0;  j < links.length; j++) {
                        Picasso.with(images[j].getContext()).load(links[j]).into(images[j]);
                    }
                }
                Picasso.with(personPhoto.getContext()).load(post.getAuthor().getImgURL()).
                        into(personPhoto);

            } else {
                //It's an event.
                Event event = (Event) item;
                eventTitle.setText(event.getTitle());
                eventLocation.setText(event.getAddress());
                eventDescription.setText(event.getDescription());
                //TODO: Format event time.
                eventTime.setText(event.getTimeOfEvent().toString());
            }
        }

        FloatingActionButton FAB = findViewById(R.id.write_comment);
        FAB.setVisibility(View.VISIBLE);

        ListView commentLV = findViewById(R.id.commentList);
        commentLV.setBackgroundColor(Color.GREEN); //change to better color later
        String[] comments = {"test comment 1", "test comment 2", "this is good content", "this is, uh, not good content",
                "this is a really long comment to see how comments will work if someone has a lot to say about someone's content, which is very (very) possible"};
        //TODO populate ListView with comments

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
        commentLV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
