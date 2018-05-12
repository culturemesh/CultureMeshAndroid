package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Drew Gregory on 2/23/18.
 */
@Entity
public class City extends Place {

    private String countryName;
    private String regionName;
    private String cityName;

    public City(long cityId, long regionId, long countryId, String cityName, String regionName,
                String countryName, Point latLng, long population, String featureCode) {
        super(countryId, regionId, cityId, latLng, population, featureCode);
        this.cityName = cityName;
        this.regionName = regionName;
        this.countryName = countryName;
    }

    public City(long cityId, long regionId, String cityName, String regionName,
                Point latLng, long population, String featureCode) {
        super(Location.NOWHERE, regionId, cityId, latLng, population, featureCode);
        this.cityName = cityName;
        this.regionName = regionName;
        this.countryName = Place.NOWHERE;
    }

    public City(long cityId, String cityName, Point latLng, long population, String featureCode) {
        super(Location.NOWHERE, Location.NOWHERE, cityId, latLng, population, featureCode);
        this.cityName = cityName;
        this.regionName = Place.NOWHERE;
        this.countryName = Place.NOWHERE;
    }

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
    }

    public static City newOnlyMissingRegion(long cityId, long countryId, String cityName,
                                            String countryName, Point latLng, long population,
                                            String featureCode) {
        City c = new City(cityId, Location.NOWHERE, countryId, cityName, Place.NOWHERE, countryName,
                latLng, population, featureCode);
        return c;
    }

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

    public String getName() {
        return cityName;
    }
}
