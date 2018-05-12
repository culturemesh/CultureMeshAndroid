package org.codethechange.culturemesh.models;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by nathaniel on 11/10/17.
 */
public class Location implements Serializable {
    /**
     * When stored in the Database, we will store just the id's. The object returned from the API
     * will have the country, region, and city updated.
     */

    protected static final int NOWHERE = -1;
    public static final int COUNTRY = 0;
    public static final int REGION = 1;
    public static final int CITY = 2;

    private long countryId;
    private long regionId;
    private long cityId;

    public Location(long countryId, long regionId, long cityId) {
        this.countryId = countryId;
        this.regionId = regionId;
        this.cityId = cityId;
    }

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

    private int getJsonType(JSONObject json) throws JSONException {
        if (json.has("region_id")) {
            return CITY;
        } else if (json.has("country_id")) {
            return REGION;
        } else {
            return COUNTRY;
        }
    }

    public int getType() {
        if (hasCityId()) {
            return CITY;
        } else if (hasRegionId()) {
            return REGION;
        } else {
            return COUNTRY;
        }
    }

    protected long getDatabaseId() {
        if (hasCityId()) {
            return cityId;
        } else if (hasRegionId()) {
            return regionId;
        } else {
            return countryId;
        }
    }

    public boolean hasCountryId() {
        return countryId != -1;
    }

    public boolean hasRegionId() {
        return regionId != -1;
    }

    public boolean hasCityId() {
        return cityId != -1;
    }

    public long getCountryId() {
        return countryId;
    }

    public long getRegionId() {
        return regionId;
    }

    public long getCityId() {
        return cityId;
    }
}

