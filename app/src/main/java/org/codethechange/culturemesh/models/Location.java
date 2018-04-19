package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.codethechange.culturemesh.Listable;
import org.codethechange.culturemesh.Searchable;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by nathaniel on 11/10/17.
 */
public class Location implements Serializable, Searchable, Listable {
    public long location_id;
    /**
     * When stored in the Database, we will store just the id's. The object returned from the API
     * will have the country, region, and city updated.
     */
    public long country_id;
    public long region_id;
    public long city_id;
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

    public boolean matches(CharSequence constraint) {
        return country.contains(constraint) || region.contains(constraint) || city.contains(constraint);
    }

    public int getNumUsers() {
        // TODO: Store number of members in Location or query API
        return 0;
    }

    public String getListableName() {
        return shortName();
    }
}

