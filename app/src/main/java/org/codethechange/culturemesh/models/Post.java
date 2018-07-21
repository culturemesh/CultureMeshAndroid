package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nathaniel on 11/10/17.
 */
@Entity
public class Post extends FeedItem implements Serializable{
    @PrimaryKey
    public long id;
    //When saved in database, we use these.
    public long userId;
    public long networkId;

    public String content;
    public String imgLink;
    public String vidLink; //TODO: Handle multiple links?
    public String datePosted;

    //When instantiated, we use these
    @Ignore
    public User author;
    @Ignore
    public Network network;

    public User getAuthor() {
        return author;
    }

    public Network getNetwork() {
        return network;
    }

    public Post(long id, long author, long networkId, String content, String imgLink, String vidLink, String datePosted) {
        this.id = id;
        this.userId = author;
        this.content = content;
        this.imgLink = imgLink;
        this.vidLink = vidLink;
        this.datePosted = datePosted;
        this.networkId = networkId;
    }

    public String getImageLink() {
        return imgLink;
    }

    public void setImageLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getVideoLink() {
        return vidLink;
    }

    public void setVideoLink(String vidLink) {
        this.vidLink = vidLink;
    }

    public Post(long author, String content, String datePosted) {
        this.userId = author;
        this.content = content;
        this.datePosted = datePosted;
        this.imgLink = null;
        this.vidLink = null;
    }

    public String getContent() {
        return content;
    }

    public String getDatePosted() {
        return this.datePosted;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public Post(){

    }

    /**
     * Sometimes, we will want to get the time not just as a string
     * but as a Date object (i.e. for comparing time for sorting)
     * @return Date object based on datePosted string.
     */
    public Date getPostedTime() throws ParseException {
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss z", Locale.US);
        return df.parse(this.datePosted);
    }

    /**
     * Creates a bare (uninstantiated) {@link Post} from a JSON that conforms to the below format:
     * <pre>
     * {@code
     * {
         "id": 0,
         "id_user": 0,
         "id_network": 0,
         "post_date": "string",
         "post_text": "string",
         "post_class": 0,
         "post_original": "string",
         "vid_link": "string",
         "img_link": "string"
        }
       }
     </pre>
     * @param json JSON representation of the {@link Post} to construct
     * @throws JSONException May be thrown in response to an improperly formatted JSON
     */
    public Post(JSONObject json) throws JSONException {
        id = json.getLong("id");
        userId = json.getLong("id_user");
        networkId = json.getLong("id_network");
        content = json.getString("post_text");
        imgLink = json.getString("img_link");
        vidLink = json.getString("vid_link");
        datePosted = json.getString("post_date");
    }

    /**
     * Converts Post object to corresponding JSON object.
     * @return JSONObject according to format necessary for POST /user
     */
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id_user", userId);
            object.put("id_network", networkId);
            object.put("post_text", getContent());
            object.put("img_link", getImageLink());
            object.put("vid_link", getVideoLink());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
