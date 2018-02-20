package org.codethechange.culturemesh.models;

/**
 * Created by Drew Gregory on 2/19/18.
 * Exact copy of Location, but used as Embedded Entity in SQLite Database.
 */

public class NearLocation {
    public long near_location_id;
    public String near_country;
    public String near_region;
    public String near_city;

    //TODO: private Point[] points;

    public NearLocation(String near_country, String near_region, String near_city, Point[] points) {
        this.near_country = near_country;
        this.near_region = near_region;
        this.near_city = near_city;
        //TODO: this.points = points
    }

    public NearLocation() {
        this.near_country = "";
        this.near_region = "";
        this.near_city = "";
    }


    public String toString() {
        String string = "";
        string += near_country;
        String region = this.near_region;
        if (region != null) {
            string += ", " + region;
        }
        if (near_city != null) {
            string += ", " + near_city;
        }
        return string;
    }

    public String shortName() {
        //We'll return the lowest level location.
        String city = this.near_city;
        if (city != null) return city;
        String region = this.near_region;
        if (region != null) return region;
        return this.near_country;


    }

}
