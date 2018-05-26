package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A {@link Region} is a specific kind of {@link Place} that stores the ID and name of a region. It
 * can also store the name and ID of the region's country, but this is not mandatory. If any
 * geographical descriptor (e.g. city, region, or country) is not specified, its name will be stored
 * as {@link Place#NOWHERE}, but this constant should not be used by clients. Note that the
 * {@code region} descriptor is mandatory.
 */
@Entity
public class Region extends Place {

    /**
     * Name of the country (may store {@link Place#NOWHERE}
     */
    public String countryName;

    /**
     * Name of the region (should always be specified and not as {@link Place#NOWHERE}
     */
    public String regionName;

    /**
     * Initialize instance fields and those of superclass with provided parameters
     * No parameters should be set to {@link Place#NOWHERE} or {@link Location#NOWHERE}
     * For regions with explicitly specified countries
     * @param regionId ID of region
     * @param countryId ID of country
     * @param regionName Name of region
     * @param countryName Name of country
     * @param latLng Latitude and longitude coordinates of the region
     * @param population Population of the region
     * @param featureCode Region's feature code
     */
    public Region(long regionId, long countryId, String regionName, String countryName, Point latLng,
                  long population, String featureCode) {
        super(countryId, regionId, Location.NOWHERE, latLng, population, featureCode);
        this.countryName = countryName;
        this.regionName = regionName;
    }

    /**
     * Initialize instance fields and those of superclass with provided parameters
     * No parameters should be set to {@link Place#NOWHERE} or {@link Location#NOWHERE}
     * For regions that have no specified country
     * @param regionId ID of region
     * @param regionName Name of region
     * @param latLng Latitude and longitude coordinates of the region
     * @param population Population of the region
     * @param featureCode Region's feature code
     */
    public Region(long regionId, String regionName, Point latLng, long population, String featureCode) {
        super(Location.NOWHERE, regionId, Location.NOWHERE, latLng, population, featureCode);
        this.regionName = regionName;
        this.countryName = Place.NOWHERE;
    }

    /**
     * Initialize instance fields and those of superclass based on provided JSON
     * This class extracts the following fields, if they are present: {@code country_name}.
     * It requires that the key {@code name} exist, as its value will be
     * used as the region's name
     * @param json JSON object describing the region to create
     * @throws JSONException May be thrown in response to an invalidly formatted JSON object
     */
    public Region(JSONObject json) throws JSONException {
        super(json);
        if (json.has("country_name") && ! json.isNull("country_name")) {
            countryName = json.getString("country_name");
        } else {
            countryName = Place.NOWHERE;
        }

        regionName = json.getString("name");
    }

    /**
     * Empty constructor for database use only. This should never be called by our code.
     */
    public Region() {

    }

    /**
     * Get a name for the region that lists all available geographic descriptor names. For example,
     * {@code Washington, D.C.} would be expressed as {@code Washington, D.C., United States}, while
     * {@code San Francisco} would be expressed as {@code San Francisco, California, United States}.
     * @return Name of city that includes all available geographic descriptors
     */
    public String getListableName() {
        if (! countryName.equals(Place.NOWHERE)) {
            return regionName + ", " + countryName;
        } else {
            return regionName;
        }
    }

    /**
     * Get the name of the region
     * @return Name of region
     */
    public String getName() {
        return regionName;
    }
}
