package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Drew Gregory on 3/4/18.
 */
// TODO: Document PostReply
@Entity(tableName = "postreply")
public class PostReply implements Postable, Putable{
    @PrimaryKey
    public long id;

    public long parentId;

    public long userId;

    public long networkId;

    public String replyDate;

    public String replyText;

    @Ignore
    public User author;

    public PostReply(long id, long parentId, long userId, long networkId, String replyDate, String replyText) {
        this.id = id;
        this.parentId = parentId;
        this.userId = userId;
        this.networkId = networkId;
        this.replyDate = replyDate;
        this.replyText = replyText;
    }

    public PostReply(JSONObject replyObj) throws JSONException {
        this.id = replyObj.getInt("id");
        this.parentId = replyObj.getInt("id_parent");
        this.networkId = replyObj.getInt("id_network");
        this.replyDate = replyObj.getString("reply_date");
        this.replyText = replyObj.getString("reply_text");
        this.userId = replyObj.getInt("id_user");
    }
    public User getAuthor() {
        return author;
    }

    public PostReply() {

    }

    /**
     * Generate a JSON describing the object. The JSON will conform to the following format:
     * <pre>
     *     {@code
     *          {
                    "id_parent": 0,
                    "id_user": 0,
                    "id_network": 0,
                    "reply_text": "string"
                }
     *     }
     * </pre>
     * The resulting object is suitable for use with the {@code /post/{postId}/reply} POST or PUT
     * endpoints.
     * @return JSON representation of the object
     * @throws JSONException Unclear when this would be thrown
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("id_parent" , parentId);
        obj.put("id_user", userId);
        obj.put("id_network", networkId);
        obj.put("reply_text", replyText);
        return obj;
    }

    public JSONObject getPostJson() throws JSONException {
        return toJSON();
    }

    public JSONObject getPutJson() throws JSONException {
        return toJSON();
    }
}
