package org.codethechange.culturemesh.models;

/**
 * Created by nathaniel on 11/10/17.
 */

public class Point {
    private int latitude;
    private int longitude;

    public Point(int latitude) {
        this.latitude = latitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
}
