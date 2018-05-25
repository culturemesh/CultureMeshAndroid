package org.codethechange.culturemesh.models;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * This object stores only the city, region, and country ID values, so it acts as a pointer to the
 * more detailed information for the location in each City, Region, and Country's database entries
 * or network information.
 *
 * No instance of this class should have {@code countryId}, {@code regionId}, and {@code cityId}
 * all equal to {@code NOWHERE}. This should only be possible by mis-using the JSON constructor
 * or by supplying {@code -1} as an ID. Neither should ever be done.
 *
 * <pre>
 * {@code
 *                       Location
 *                      (IDs only)
 *                      /        \
 *                     /          \
 *                    /            \
 *                   /              \
 *          Place (Abstract)    DatabaseLocation (Abstract)
 *           (Full Info)                   (IDs)
 *             /  |  \                   /      \
 *            /   |   \           NearLocation  FromLocation
 *       City  Region  Country  (Wrappers for DatabaseLocation)
 *    (Specific cases of Place)
 * }
 * </pre>
 */
public class Location implements Serializable {

    /**
     * These constants are used to identify the type of location being stored. See the documentation
     * for {@code getType} for more. {@code NOWHERE} is {@code protected} because it should never
     * be used by clients. It is only for subclasses to denote empty IDs. Creating locations with
     * empty IDs should be handled by subclass constructors or methods.
     */
    protected static final int NOWHERE = -1;
    public static final int COUNTRY = 0;
    public static final int REGION = 1;
    public static final int CITY = 2;

    /**
     * These instance fields store the IDs of the city, region, and country defining the location
     * They can be {@code private} because a plain {@code Location} object should not need to be
     * stored in the database.
     */
    public long countryId;
    public long regionId;
    public long cityId;

    /**
     * Initializes ID instance fields using the provided IDs
     * @param countryId ID of country
     * @param regionId ID of region
     * @param cityId ID of city
     */
    public Location(long countryId, long regionId, long cityId) {
        this.countryId = countryId;
        this.regionId = regionId;
        this.cityId = cityId;
    }

    /**
     * Initializes ID instance fields using the provided JSON object
     * If present, the values of the keys {@code city_id}, {@code region_id}, and {@code country_id}
     * will be used automatically. Depending on the presence of those keys, the value of the key
     * {@code id} will be used to fill the instance field for the JSON type. See {@code getJsonType}
     * for more.
     * Precondition: The JSON must be validly formatted, with examples in {@code API.java}
     * @param json JSON object containing the country, region, and city IDs
     * @throws JSONException May be thrown if the JSON is improperly formatted
     */
    public Location(JSONObject json) throws JSONException {

        cityId = NOWHERE;
        regionId = NOWHERE;
        countryId = NOWHERE;

        if (json.has("id") && ! json.isNull("id")) {
            int type = getJsonType(json);
            if (type == CITY) {
                this.cityId = json.getLong("id");
            } else if (type == REGION) {
                this.regionId = json.getLong("id");
            } else {
                this.countryId = json.getLong("id");
            }
        }

        if (json.has("city_id") && ! json.isNull("city_id")) {
            this.cityId = json.getLong("city_id");
        }
        if (json.has("region_id") && ! json.isNull("region_id")) {
            this.regionId = json.getLong("region_id");
        }
        if (json.has("country_id") && ! json.isNull("country_id")) {
            this.countryId = json.getLong("country_id");
        }
    }

    /**
     * Determine the type of location described by the JSON using the presence or absence of the
     * keys {@code region_id} and {@code country_id}. The JSON type is presumed to be the next most
     * specific type than the one of the most specific key found. For example, if {@code region_id}
     * is found, the type is {@code CITY}. If no key is present, the type is {@code COUNTRY}.
     * Precondition: The JSON must be validly formatted, with examples in {@code API.java}
     * @param json JSON object describing the location
     * @return {@code COUNTRY}, {@code REGION}, or {@code CITY} to describe the type of location
     */
    private int getJsonType(JSONObject json) {
        if (json.has("region_id")) {
            return CITY;
        } else if (json.has("country_id")) {
            return REGION;
        } else {
            return COUNTRY;
        }
    }

    /**
     * The most specific ID that is not {@code NOWHERE} determines the location's type, even if
     * more general IDs are {@code NOWHERE}. For example, if {@code regionId = 0} and
     * {@code countryId = cityId = NOWHERE}, the type would be {@code REGION}
     * @return Location's type as {@code CITY}, {@code REGION}, or {@code COUNTRY}
     */
    public int getType() {
        if (hasCityId()) {
            return CITY;
        } else if (hasRegionId()) {
            return REGION;
        } else {
            return COUNTRY;
        }
    }

    /**
     * Find the ID that should be used as the {@code PrimaryKey} for a database. It is the ID of the
     * most specific geographical descriptor with an ID that is not {@code NOWHERE}.
     * <strong>WARNING: The returned ID is NOT guaranteed to be unique</strong>
     * @return ID for use as {@code PrimaryKey} in a database
     */
    protected long getDatabaseId() {
        if (hasCityId()) {
            return cityId;
        } else if (hasRegionId()) {
            return regionId;
        } else {
            return countryId;
        }
    }

    /**
     * Check if the country ID is specified (i.e. not {@code NOWHERE})
     * @return {@code true} if the country ID is specified, {@code false} otherwise
     */
    public boolean hasCountryId() {
        return countryId != NOWHERE;
    }

    /**
     * Check if the region ID is specified (i.e. not {@code NOWHERE})
     * @return {@code true} if the region ID is specified, {@code false} otherwise
     */
    public boolean hasRegionId() {
        return regionId != NOWHERE;
    }

    /**
     * Check if the city ID is specified (i.e. not {@code NOWHERE})
     * @return {@code true} if the city ID is specified, {@code false} otherwise
     */
    public boolean hasCityId() {
        return cityId != NOWHERE;
    }

    /**
     * Getter for the country ID, which may return {@code NOWHERE}, so {@code hasCountryId} should
     * be used to check first
     * @return The country ID
     */
    public long getCountryId() {
        return countryId;
    }

    /**
     * Getter for the region ID, which may return {@code NOWHERE}, so {@code hasRegionId} should
     * be used to check first
     * @return The region ID
     */
    public long getRegionId() {
        return regionId;
    }

    /**
     * Getter for the city ID, which may return {@code NOWHERE}, so {@code hasCityId} should
     * be used to check first
     * @return The city ID
     */
    public long getCityId() {
        return cityId;
    }
}

