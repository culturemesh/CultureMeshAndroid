package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Ignore;

import java.util.List;

/**
 * Superclass for Posts and Events that mandates they both have a list of PostReply objects
 * that can be displayed in a feed.
 */

public class FeedItem {
    /**
     * This list of PostReplies will be where we store the comments for each post.
     */
    @Ignore
    public List<PostReply> comments;

}
