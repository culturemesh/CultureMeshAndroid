package org.codethechange.culturemesh.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by nathaniel on 11/10/17.
 */

public class Network implements Serializable{
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }


    public boolean isLocationNetwork() {
        return classification;
    }

    public void setClassification(boolean classification) {
        this.classification = classification;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(Location fromLocation) {
        this.fromLocation = fromLocation;
    }

    private ArrayList<Post> posts;
    private ArrayList<Event> events;
    private Location nearLocation;
    private boolean classification;
    private Language lang;
    private Location fromLocation;

    public Network(ArrayList<Post> posts, ArrayList<Event> events, Location nearLocation,
                   Location fromLocation) {
        this.posts = posts;
        this.events = events;
        this.fromLocation = fromLocation;
        this.nearLocation = nearLocation;
        classification = true;
    }

    public Network(ArrayList<Post> posts, ArrayList<Event> events, Location nearLocation,
                   Language lang) {
        this.posts = posts;
        this.events = events;
        this.lang = lang;
        this.nearLocation = nearLocation;
        classification = false;
    }



    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public Location getNearLocation() {
        return nearLocation;
    }

    public void setNearLocation(Location nearLocation) {
        this.nearLocation = nearLocation;
    }

}
