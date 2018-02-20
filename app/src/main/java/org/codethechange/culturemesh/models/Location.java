package org.codethechange.culturemesh.models;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by nathaniel on 11/10/17.
 */

public class Location implements Serializable{
    public long location_id;
    public String country;
    public String region;
    public String city;

    //TODO: private Point[] points;

    public Location(String country, String region, String city, Point[] points) {
        this.country = country;
        this.region = region;
        this.city = city;
        //TODO: this.points = points
    }

    public Location() {
        this.country = "";
        this.region = "";
        this.city = "";
    }


    public String toString() {
        String string = "";
        string += country;
        String region  = this.region;
        if (region != null) {
            string += ", " + region;
        }
        if (city != null) {
            string += ", " + city;
        }
        return string;
    }

    public String shortName() {
        //We'll return the lowest level location.
        String city = this.city;
        if (city != null) return city;
        String region = this.region;
        if (region != null) return region;
        return this.country;


    }
}

