package com.culturemesh.android.models;

import com.culturemesh.android.Listable;

import android.arch.persistence.room.Entity;

import com.culturemesh.android.Listable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A {@link Country} is a specific kind of {@link Place} that stores the ID and name of a country.
 * No instance field should ever be set to {@link Place#NOWHERE}.
 */
@Entity
public class Country extends Place {
    // TODO: How does the isoA2 long get turned into 2-letter country codes?

    /**
     * 2-Letter ISO country code. This is not currently used.
     */
    public String isoA2;

    /**
     * Name of country
     */
    public String name;

    /**
     * Initialize instance fields and those of superclass with provided parameters
     * @param id ID of country
     * @param name Name of country
     * @param latLng Latitude and longitude coordinates of the region
     * @param population Population of the region
     * @param featureCode Region's feature code
     * @param isoA2 2-Letter ISO country code
     */
    public Country(long id, String name, Point latLng, long population, String featureCode, String isoA2) {
        super(id, Location.NOWHERE, Location.NOWHERE, latLng, population, featureCode);
        this.name = name;
        this.isoA2 = isoA2;
    }

    /**
     * Initialize instance fields and those of superclass based on provided JSON
     * It requires that the key {@code name} exist, as its value will be used as the country's name
     * @param json JSON object describing the country to create
     * @throws JSONException May be thrown in response to invalid JSON object
     */
    public Country(JSONObject json) throws JSONException {
        super(json);
        this.name = json.getString("name");
        this.isoA2 = json.getString("iso_a2");
    }

    /**
     * Empty constructor for database use only. This should never be called by our code.
     */
    public Country() {}


    /**
     * Get name of country, which is suitable for display in UI.
     * @see Listable
     * @return Name of country, abbreviated if necessary to have a maximum length of
     * {@link Listable#MAX_CHARS}.
     */
    public String getFullName() {
        return name;
    }

    /**
     * Now display just country name.
     * @return
     */
    public String getShortName(){
        return name;
    }


    /**
     * Get name of country
     * @return Name of country
     */
    public String getName() {
        return name;
    }

    /**
     * Represent the object as a string suitable for debugging, but not for display to user.
     * @return String representation of the form {@code Class[var=value, var=value, var=value, ...]}
     */
    public String toString() {
        return "Country[name=" + name + ", isoA2=" + isoA2 + ", super=" + super.toString() + "]";
    }
}
