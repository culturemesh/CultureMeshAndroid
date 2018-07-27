package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Ignore;

import java.util.List;

// TODO: Rewrite FeedItem docs
/**
 * Created by drewgregory on 1/18/18.
 * Superclass for Posts and Events so we can use Polymorphism for feeds.
 */

public class FeedItem {
    /**
     * This list of PostReplies will be where we store the comments for each post.
     */
    @Ignore
    public List<PostReply> comments;

}
