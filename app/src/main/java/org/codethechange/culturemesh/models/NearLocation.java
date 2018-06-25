package org.codethechange.culturemesh.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Wrapper for {@code DatabaseLocation} that is for Near locations. See the documentation for
 * {@code DatabaseLocation} for information as to why this redundancy is necessary. All of these
 * instance fields will be stored in the local cached database.
 */
public class NearLocation extends DatabaseLocation {

    /**
     * These instance fields mirror those in Location, but are needed for database storage
     */
    public long near_country_id;
    public long near_region_id;
    public long near_city_id;

    public static final String CITY_ID_KEY = "id_city_cur";
    public static final String REGION_ID_KEY = "id_region_cur";
    public static final String COUNTRY_ID_KEY = "id_country_cur";

    // TODO: Handle undefined geographical areas (e.g. no region defined)
    /**
     * Initialize instance fields with provided parameters
     * @param cityId ID of city
     * @param regionId ID of region
     * @param countryId ID of country
     */
    public NearLocation(long cityId, long regionId, long countryId) {
        super(cityId, regionId, countryId);
        initialize();
    }

    /**
     * Initializes instance fields by passing JSON to {@link Location#Location(JSONObject, String, String, String)}
     * and then initializing instance fields using {@link NearLocation#initialize()}
     * @param json JSON object describing the location
     * @throws JSONException May be thrown in response to improperly formatted JSON
     */
    public NearLocation(JSONObject json) throws JSONException {
        super(json, CITY_ID_KEY, REGION_ID_KEY, COUNTRY_ID_KEY);
        initialize();
    }

    /**
     * Initializes instance fields by passing JSON to {@link Location#Location(JSONObject)}
     * and then initializing instance fields using {@link NearLocation#initialize()}
     * @param json JSON object describing the location
     * @param distinguisher Useless value used to distinguish from {@link NearLocation#NearLocation(JSONObject)}
     * @throws JSONException May be thrown in response to improperly formatted JSON
     */
    @Deprecated
    public NearLocation(JSONObject json, boolean distinguisher) throws JSONException {
        super(json);
        initialize();
    }

    /**
     * Empty constructor for database use only. This should never be called by our code.
     */
    public NearLocation() {

    }

    /**
     * Initialize this class's instance fields based on those provided and those from superclass
     * methods. This is what keeps the instance fields in sync with those of Location.
     */
    private void initialize() {
        near_country_id = getCountryId();
        near_region_id = getRegionId();
        near_city_id = getCityId();
    }
}
