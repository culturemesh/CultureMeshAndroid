package org.codethechange.culturemesh.models;

/**
 * Created by Drew Gregory on 2/19/18.
 * Exact copy of Location, but used as Embedded Entity in SQLite Database.
 */

public class FromLocation {
    public long from_location_id;
    public String from_country;
    public String from_region;
    public String from_city;

    //TODO: private Point[] points;

    public FromLocation(String from_country, String from_region, String from_city, Point[] points) {
        this.from_country = from_country;
        this.from_region = from_region;
        this.from_city = from_city;
        //TODO: this.points = points
    }

    public FromLocation() {
        this.from_country = "";
        this.from_region = "";
        this.from_city = "";
    }


    public String toString() {
        String string = "";
        string += from_country;
        String region = this.from_region;
        if (region != null) {
            string += ", " + region;
        }
        if (from_city != null) {
            string += ", " + from_city;
        }
        return string;
    }

    public String shortName() {
        //We'll return the lowest level location.
        String city = this.from_city;
        if (city != null) return city;
        String region = this.from_region;
        if (region != null) return region;
        return this.from_country;


    }

}
