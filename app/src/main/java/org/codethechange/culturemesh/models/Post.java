package org.codethechange.culturemesh.models;

import java.io.Serializable;
import java.math.BigInteger;

import java.util.Date;

/**
 * Created by nathaniel on 11/10/17.
 */

public class Post implements Serializable{

    private BigInteger id;
    private User author;
    private String content;
    private String imgLink;
    private String vidLink;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    private String datePosted;

    public Post(User author, String content, String imgLink, String vidLink, String datePosted) {
        this.author = author;
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

    public Post(User author, String content, String datePosted) {
        this.author = author;
        this.content = content;
        this.datePosted = datePosted;
        this.imgLink = null;
        this.vidLink = null;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }
}
