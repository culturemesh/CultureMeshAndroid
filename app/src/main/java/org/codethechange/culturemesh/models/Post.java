package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigInteger;

import java.util.Date;

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

    public Post(int id, int author, int networkId, String content, String imgLink, String vidLink, String datePosted) {
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

    public Post(int author, String content, String datePosted) {
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
        return datePosted;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public Post(){

    }
}
