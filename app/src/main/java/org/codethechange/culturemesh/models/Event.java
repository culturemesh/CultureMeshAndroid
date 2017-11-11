package org.codethechange.culturemesh.models;

import java.util.Date;

/**
 * Created by nathaniel on 11/10/17.
 */

public class Event {
    private String title;
    private String description;
    private Date timeOfEvent;
    private User author;
    private String address;
    private Language lang;

    public Event(String title, String description, Date timeOfEvent, User author, String address, Language lang) {
        this.title = title;
        this.description = description;
        this.timeOfEvent = timeOfEvent;
        this.author = author;
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

    public Date getTimeOfEvent() {
        return timeOfEvent;
    }

    public void setTimeOfEvent(Date timeOfEvent) {
        this.timeOfEvent = timeOfEvent;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
