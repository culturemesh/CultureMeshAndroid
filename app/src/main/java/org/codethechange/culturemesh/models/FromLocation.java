package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Ignore;

import org.codethechange.culturemesh.Listable;

/**
 * Created by Drew Gregory on 2/19/18.
 * Exact copy of Location, but used as Embedded Entity in SQLite Database.
 */

public class FromLocation implements Listable {
    /**
     * When stored in the Database, we will store just the id's. The object returned from the API
     * will have the country, region, and city updated.
     * The default value for country, region, city is 0.
     */
    public long from_country_id;
    public long from_region_id;
    public long from_city_id;

    @Ignore
    public String from_country;
    @Ignore
    public String from_region;
    @Ignore
    public String from_city;

    //TODO: private Point[] points;

    public FromLocation(){

    }

    public FromLocation(String from_country, String from_region, String from_city, Point[] points) {
        this.from_country = from_country;
        this.from_region = from_region;
        this.from_city = from_city;
        //TODO: this.points = points
    }

    public FromLocation(long cityId, long regionId,long countryId) {
        this.from_country = "";
        this.from_region = "";
        this.from_city = "";
        this.from_city_id = cityId;
        this.from_country_id = countryId;
        this.from_region_id = regionId;
    }


    public String toString() {
        if (this.from_city == null && this.from_region == null && this.from_country == null) {
            // TODO: Replace with API call to get names from IDs
            return this.from_city_id + "," + this.from_region_id + "," + this.from_country_id;
        }
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

    public String getListableName() {
        return shortName();
    }

    public int getNumUsers() {
        // TODO: Store number of members in Location or query API
        return 0;
    }

}
