package org.codethechange.culturemesh.models;

import android.support.annotation.NonNull;

import org.codethechange.culturemesh.Listable;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class DatabaseLocation extends Location implements Listable {
    public DatabaseLocation(long countryId, long regionId, long cityId) {
        super(countryId, regionId, cityId);
    }
    public DatabaseLocation(JSONObject json) throws JSONException {
        super(json);
    }

    @NonNull
    public abstract String getCountryName();
    @NonNull
    public abstract String getRegionName();
    @NonNull
    public abstract String getCityName();

    public String toString() {
        String string = "";
        string += getCountryName();
        String region = getRegionName();
        if (! region.equals(Place.NOWHERE)) {
            string += ", " + region;
        }
        String city = getCityName();
        if (! city.equals(Place.NOWHERE)) {
            string += ", " + city;
        }
        return string;
    }

    public String shortName() {
        if (! getCityName().equals(Place.NOWHERE)) {
            return getCityName();
        } else if (! getRegionName().equals(Place.NOWHERE)) {
            return getRegionName();
        } else {
            return getCountryName();
        }
    }

    public String getListableName() {
        return toString();
    }
}
