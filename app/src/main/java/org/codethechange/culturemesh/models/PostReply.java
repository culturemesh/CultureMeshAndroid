package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Drew Gregory on 3/4/18.
 */

@Entity(tableName = "postreply")
public class PostReply {
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

    public User getAuthor() {
        return author;
    }

    public PostReply() {

    }
}
