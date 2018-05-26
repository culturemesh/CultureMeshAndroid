package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A {@link City} is a specific kind of {@link Place} that stores the ID and name of a city. It can
 * also store the names and IDs of the city's country and region, but this is not mandatory. If any
 * geographical descriptor (e.g. city, region, or country) is not specified, its name will be stored
 * as {@link Place#NOWHERE}, but this constant should not be used by clients. Note that the
 * {@code city} descriptor is mandatory.
 */
@Entity
public class City extends Place {

    /**
     * Name of country.
     */
    public String countryName;

    /**
     * Name of region
     */
    public String regionName;

    /**
     * Name of city
     */
    public String cityName;

    /**
     * Initialize instance fields and instance fields of superclasses based on provided arguments
     * For creating cities that have city, region, and country all specified.
     * @param cityId ID of city
     * @param regionId ID of city's region
     * @param countryId ID of country's region
     * @param cityName Name of city
     * @param regionName Name of region city lies within
     * @param countryName Name of country city lies within
     * @param latLng Latitude and longitude coordinates of city
     * @param population Population of the city
     * @param featureCode Feature code of the city
     */
    public City(long cityId, long regionId, long countryId, String cityName, String regionName,
                String countryName, Point latLng, long population, String featureCode) {
        super(countryId, regionId, cityId, latLng, population, featureCode);
        this.cityName = cityName;
        this.regionName = regionName;
        this.countryName = countryName;
    }

    /**
     * Initialize instance fields and instance fields of superclasses based on provided arguments.
     * For creating cities that have no country descriptor, but do have specified regions.
     * @param cityId ID of city
     * @param regionId ID of city's region
     * @param cityName Name of city
     * @param regionName Name of region city lies within
     * @param latLng Latitude and longitude coordinates of city
     * @param population Population of the city
     * @param featureCode Feature code of the city
     */
    public City(long cityId, long regionId, String cityName, String regionName,
                Point latLng, long population, String featureCode) {
        super(Location.NOWHERE, regionId, cityId, latLng, population, featureCode);
        this.cityName = cityName;
        this.regionName = regionName;
        this.countryName = Place.NOWHERE;
    }

    /**
     * Initialize instance fields and instance fields of superclasses based on provided arguments
     * For creating cities that have no region nor country descriptor
     * @param cityId ID of city
     * @param cityName Name of city
     * @param latLng Latitude and longitude coordinates of city
     * @param population Population of the city
     * @param featureCode Feature code of the city
     */
    public City(long cityId, String cityName, Point latLng, long population, String featureCode) {
        super(Location.NOWHERE, Location.NOWHERE, cityId, latLng, population, featureCode);
        this.cityName = cityName;
        this.regionName = Place.NOWHERE;
        this.countryName = Place.NOWHERE;
    }

    /**
     * Initialize instance fields and those of superclass based on provided JSON
     * This class extracts the following fields, if they are present: {@code country_name} and
     * {@code region_name}. It requires that the key {@code name} exist, as its value will be
     * used as the City's name
     * @param json JSON object describing the city to create
     * @throws JSONException May be thrown in response to an invalidly formatted JSON object
     */
    public City(JSONObject json) throws JSONException {
        super(json);

        if (json.has("country_name") && ! json.isNull("country_name")) {
            countryName = json.getString("country_name");
        } else {
            countryName = Place.NOWHERE;
        }

        if (json.has("region_name") && ! json.isNull("region_name")) {
            regionName = json.getString("region_name");
        } else {
            regionName = Place.NOWHERE;
        }

        cityName = json.getString("name");
    }

    /**
     * Empty constructor for database use only. This should never be called by our code.
     */
    public City() {

    }

    /**
     * Return {@link City} object with fields initialized with provided parameters
     * For creating cities that are only missing the region descriptor
     * This unusual pseudo-constructor is required to avoid ambiguity between constructors
     * @param cityId ID of city
     * @param countryId ID of country's region
     * @param cityName Name of city
     * @param countryName Name of country city lies within
     * @param latLng Latitude and longitude coordinates of city
     * @param population Population of the city
     * @param featureCode Feature code of the city
     * @return City object that has been initialized
     */
    public static City newOnlyMissingRegion(long cityId, long countryId, String cityName,
                                            String countryName, Point latLng, long population,
                                            String featureCode) {
        City c = new City(cityId, Location.NOWHERE, countryId, cityName, Place.NOWHERE, countryName,
                latLng, population, featureCode);
        return c;
    }

    /**
     * Get a name for the city that lists all available geographic descriptor names. For example,
     * {@code Washington, D.C.} would be expressed as {@code Washington, D.C., United States}, while
     * {@code San Francisco} would be expressed as {@code San Francisco, California, United States}.
     * @return Name of city that includes all available geographic descriptors
     */
    public String getListableName() {
        String listable = cityName;
        if (! regionName.equals(Place.NOWHERE)) {
            listable += ", " + regionName;
        }
        if (! regionName.equals(Place.NOWHERE)) {
            listable += ", " + countryName;
        }
        return listable;
    }

    /**
     * Get the name of the city
     * @return City name
     */
    public String getName() {
        return cityName;
    }
}
