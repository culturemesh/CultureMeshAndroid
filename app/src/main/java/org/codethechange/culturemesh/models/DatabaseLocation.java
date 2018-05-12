package org.codethechange.culturemesh.models;

import android.support.annotation.NonNull;

import org.codethechange.culturemesh.Listable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Superclass for Locations that will be stored in the database. Since the instance field names
 * are used directly as column names in the database, a single class cannot be used for both
 * From and Near locations (the column names would conflict). Therefore, two separate classes,
 * {@code FromLocation} and {@code NearLocation} are used. They are nearly identical, however,
 * so this superclass holds methods common to both. It also imposes requirements on them to ensure
 * that those methods can function. The database will store the IDs and names of the city, region,
 * and country. It will also store the population of the described location.
 */
public abstract class DatabaseLocation extends Location implements Listable {

    /**
     * Constructor that passes all parameters to superclass constructor
     * @param countryId ID of country
     * @param regionId ID of region
     * @param cityId ID of city
     */
    public DatabaseLocation(long countryId, long regionId, long cityId) {
        super(countryId, regionId, cityId);
    }

    /**
     * Constructor that passes all parameters to superclass constructor
     * @param json JSON object that defines the location. See superclass constructor documentation.
     * @throws JSONException May be thrown for improperly formatted JSON
     */
    public DatabaseLocation(JSONObject json) throws JSONException {
        super(json);
    }

    /**
     * Requires that subclasses store the name of the country. If the country is unspecified,
     * the name should be set to the constant {@code Place.NOWHERE}
     * @return Country name
     */
    @NonNull
    public abstract String getCountryName();
    /**
     * Requires that subclasses store the name of the region. If the region is unspecified,
     * the name should be set to the constant {@code Place.NOWHERE}
     * @return Region name
     */
    @NonNull
    public abstract String getRegionName();
    /**
     * Requires that subclasses store the name of the city. If the city is unspecified,
     * the name should be set to the constant {@code Place.NOWHERE}
     * @return City name
     */
    @NonNull
    public abstract String getCityName();

    /**
     * Form a single name for the specified location using the format
     * {@code [city], [region], [country]}, with unspecified geographical areas excluded
     * @return Name of the location
     */
    public String toString() {
        String string = "";

        String city = getCityName();
        if (! city.equals(Place.NOWHERE)) {
            string += ", " + city;
        }

        String region = getRegionName();
        if (! region.equals(Place.NOWHERE)) {
            string += ", " + region;
        }

        String country = getCountryName();
        if (! country.equals(Place.NOWHERE)) {
            string += ", " + country;
        }

        return string.substring(2); // Remove initial comma and space
    }

    /**
     * Get the most specific name of the geographical areas defined by the Location. For example,
     * if the region and country are defined, the name of the region would be returned.
     * @return Most specific of the location's names
     */
    public String shortName() {
        if (! getCityName().equals(Place.NOWHERE)) {
            return getCityName();
        } else if (! getRegionName().equals(Place.NOWHERE)) {
            return getRegionName();
        } else {
            return getCountryName();
        }
    }

    /**
     * Get the name that should be displayed in UI lists (also used outside lists)
     * @return Name for UI
     */
    public String getListableName() {
        return toString();
    }
}
