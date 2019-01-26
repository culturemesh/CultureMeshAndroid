package com.culturemesh.android.models;

import com.culturemesh.android.FormatManager;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a post made by a user in a network. A post is arbitrary, formatted text of the user's
 * choosing.
 */
public class Post extends FeedItem implements Serializable, Postable, Putable {
    /**
     * Uniquely identifies the post across all of CultureMesh
     */
    public long id;

    /**
     * Unique identifier for the user who created the post. This is used when only a reference to
     * the full {@link User} object is needed, e.g. when getting a post from the API. The rest of
     * the information associated with the user can be fetched later.
     */
    public long userId;

    /**
     * Unique identifier for the network the post was made in. This is used when only a reference to
     * the full {@link Network} object is needed, e.g. when getting a post from the API. The rest of
     * the information associated with the network can be fetched later.
     */
    public long networkId;

    /**
     * The body of the post. May be formatted.
     * @see FormatManager
     */
    public String content;

    /**
     * Link to an image, if available, that is associated with the post
     */
    public String imgLink;

    /**
     * Link to a video, if available, that is associated with the post
     * TODO: Handle multiple links?
     */
    public String vidLink;

    /**
     * Timestamp for when the post was created. Should conform to
     * {@code EEE, dd MMM yyyy kk:mm:ss z}
     */
    public String datePosted;

    /**
     * The {@link User} who created the post. This may not be present and have to be instantiated
     * from {@link Post#userId}. Currently, this is handled by
     * {@link com.culturemesh.android.API}
     */
    public User author;

    /**
     * The {@link Network} who created the post. This may not be present and have to be instantiated
     * from {@link Post#networkId}. Currently, this is handled by
     * {@link com.culturemesh.android.API}
     */
    public Network network;

    /**
     * Create a new post object from the provided parameters. The resulting object will not be
     * fully instantiated (e.g. {@link Post#author} and {@link Post#network} will be {@code null}.
     * @param id Uniquely identifies the post across all of CultureMesh
     * @param author ID of {@link User} who created the post
     * @param networkId ID of the {@link Network} in which the post was made
     * @param content Formatted text that composes the body of the post.
     *                @see FormatManager
     * @param imgLink Link to an image associated with the post. {@code null} if none associated.
     * @param vidLink Link to a video associated with the post. {@code null} if none associated
     * @param datePosted When the post was created. Must conform to
     *                   {@code EEE, dd MMM yyyy kk:mm:ss z}
     */
    public Post(long id, long author, long networkId, String content, String imgLink,
                String vidLink, String datePosted) {
        this.id = id;
        this.userId = author;
        this.content = content;
        this.imgLink = imgLink;
        this.vidLink = vidLink;
        this.datePosted = datePosted;
        this.networkId = networkId;
    }

    /**
     * Empty constructor for database
     */
    public Post(){

    }

    /**
     * Get the URL to the image associated with the post.
     * @return URL to associated image. If no image is associated, {@code null}
     */
    public String getImageLink() {
        return imgLink;
    }

    /**
     * Associate the image at the provided URL with the post. Replaces any existing image URL.
     * @param imgLink URL to the image to add to the post
     */
    public void setImageLink(String imgLink) {
        this.imgLink = imgLink;
    }

    /**
     * Get the URL to the video associated with the post.
     * @return URL to associated video. If no video is associated, {@code null}
     */
    public String getVideoLink() {
        return vidLink;
    }

    /**
     * Associate the video at the provided URL with the post. Replaces any existing video URL.
     * @param vidLink URL to the video to add to the post
     */
    public void setVideoLink(String vidLink) {
        this.vidLink = vidLink;
    }

    /**
     * Get the formatted text that makes up the body of the post.
     * @return Body of the post, which may be formatted.
     * @see FormatManager
     */
    public String getContent() {
        return content;
    }

    /**
     * Get when the post was created.
     * @return Timestamp of when post was created. Conforms to {@code EEE, dd MMM yyyy kk:mm:ss z}
     */
    public String getDatePosted() {
        return this.datePosted;
    }

    /**
     * Set the body of the post to the parameter provided.
     * @param content Formatted body of the post.
     *                @see FormatManager
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the timestamp for when the post was created.
     * @param datePosted When post was created. Conforms to {@code EEE, dd MMM yyyy kk:mm:ss z}
     */
    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    /**
     * Get the author of the post. <strong>Object must be fully instantiated, not just populated
     * with IDs</strong>
     * @return Author of the post
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Get the network of the post. <strong>Object must be fully instantiated, not just populated
     * with IDs</strong>
     * @return Network of the post
     */
    public Network getNetwork() {
        return network;
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
     * Generate a JSON describing the object. The JSON will conform to the following format:
     * <pre>
     *     {@code
     *          {
                    "id_user": 0,
                    "id_network": 0,
                    "post_text": "string",
                    "vid_link": "string",
                    "img_link": "string"
                }
     *     }
     * </pre>
     * The resulting object is suitable for use with the {@code /post/new} endpoint (PUT and POST).
     * @return JSON representation of the object
     * @throws JSONException Unclear when this would be thrown
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id_user", userId);
        json.put("id_network", networkId);
        json.put("post_text", content);
        json.put("vid_link", vidLink);
        json.put("img_link", imgLink);
        return json;
    }

    /**
     * Wrapper for {@link Post#toJSON()}
     */
    public JSONObject getPostJson() throws JSONException {
        return toJSON();
    }

    /**
     * Wrapper for {@link Post#toJSON()}
     */
    public JSONObject getPutJson() throws JSONException {
        return toJSON();
    }
}
