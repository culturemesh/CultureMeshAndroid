package org.codethechange.culturemesh.models;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.io.Serializable;
import java.math.BigInteger;

import java.util.Date;

/**
 * Created by nathaniel on 11/10/17.
 */
@Entity
public class Event extends FeedItem implements Serializable{

    public long id;

    public long networkId;

    public String title;

    public String description;

    public String timeOfEvent;

    public long authorId;

    public String address;

    @Embedded
    public Language lang;

    public Event(String title, String description, String timeOfEvent, User author, String address, Language lang) {
        this.title = title;
        this.description = description;
        this.timeOfEvent = timeOfEvent;
        this.authorId = author.id;
        this.address = address;
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeOfEvent() {
        return timeOfEvent;
    }

    public void setTimeOfEvent(String timeOfEvent) {
        this.timeOfEvent = timeOfEvent;
    }

    public long getAuthor() {
        return authorId;
    }

    public void setAuthor(User author) {
        this.authorId = author.id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }
}
