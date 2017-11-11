package org.codethechange.culturemesh.models;

/**
 * Created by nathaniel on 11/10/17.
 */

public class Network {
    private Post[] posts;
    private Event[] events;
    private Location nearLocation;

    public Network(Post[] posts, Event[] events, Location nearLocation) {
        this.posts = posts;
        this.events = events;
        this.nearLocation = nearLocation;
    }

    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public Location getNearLocation() {
        return nearLocation;
    }

    public void setNearLocation(Location nearLocation) {
        this.nearLocation = nearLocation;
    }
}
