package org.codethechange.culturemesh.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Superclass for Locations that will be stored in the database. Since the instance field names
 * are used directly as column names in the database, a single class cannot be used for both
 * From and Near locations (the column names would conflict). Therefore, two separate classes,
 * {@code FromLocation} and {@code NearLocation} are used. They are nearly identical, however,
 * so this superclass holds methods common to both. It also imposes requirements on them to ensure
 * that those methods can function. The database will store the IDs of the city, region,
 * and country.
 */
public abstract class DatabaseLocation extends Location {

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

    public DatabaseLocation() {

    }

}
