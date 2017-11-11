package org.codethechange.culturemesh.models;

/**
 * Created by nathaniel on 11/10/17.
 */

public class LocNetwork extends Network {
    private Location fromLocation;

    public LocNetwork(Post[] posts, Event[] events, Location nearLocation, Location fromLocation) {
        super(posts, events, nearLocation);
        this.fromLocation = fromLocation;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(Location fromLocation) {
        this.fromLocation = fromLocation;
    }
}
