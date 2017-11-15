package org.codethechange.culturemesh.models;

import java.util.ArrayList;

/**
 * Created by nathaniel on 11/10/17.
 */

public class Network {
    private ArrayList<Post> posts;
    private ArrayList<Event> events;
    private Location nearLocation;
    private boolean classification;
    private Language lang;
    private Location fromLocation;

    public Network(ArrayList<Post> posts, ArrayList<Event> events, Location nearLocation) {
        this.posts = posts;
        this.events = events;
        this.nearLocation = nearLocation;
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
