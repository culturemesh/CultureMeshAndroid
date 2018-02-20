package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

import java.io.Serializable;
import java.math.BigInteger;

import java.util.Date;

/**
 * Created by nathaniel on 11/10/17.
 */
@Entity
public class Post extends FeedItem implements Serializable{

    public long id;
    public long author_id;

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getVidLink() {
        return vidLink;
    }

    public void setVidLink(String vidLink) {
        this.vidLink = vidLink;
    }

    public String content;
    public String imgLink;
    public String vidLink; //TODO: Handle multiple links?


    public String datePosted;

    public Post(long author, String content, String imgLink, String vidLink, String datePosted) {
        this.author_id = author;
        this.content = content;
        this.imgLink = imgLink;
        this.vidLink = vidLink;
        this.datePosted = datePosted;
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
        this.author_id = author;
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
}
