package org.codethechange.culturemesh.models;

import java.math.BigInteger;

import java.util.Date;

/**
 * Created by nathaniel on 11/10/17.
 */

public class Post {

    private BigInteger id;
    private User author;
    private String content;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }


    private String title;
    private Date datePosted;

    public Post(User author, String content, String title, Date datePosted) {
        this.author = author;
        this.content = content;
        this.title = title;
        this.datePosted = datePosted;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }
}
