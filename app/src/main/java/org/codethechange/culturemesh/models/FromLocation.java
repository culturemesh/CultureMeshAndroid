package org.codethechange.culturemesh.models;

import org.codethechange.culturemesh.Listable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Drew Gregory on 2/19/18.
 * Exact copy of Location, but used as Embedded Entity in SQLite Database.
 */

public class FromLocation extends DatabaseLocation {
    /**
     * When stored in the Database, we will store just the id's. The object returned from the API
     * will have the country, region, and city updated.
     * The default value for country, region, city is 0.
     */
    public long from_country_id;
    public long from_region_id;
    public long from_city_id;

    public String from_country;
    public String from_region;
    public String from_city;

    public long from_population;

    public FromLocation(long cityId, long regionId,long countryId, String cityName,
                        String regionName, String countryName, long population) {
        super(cityId, regionId, countryId);
        initialize(cityName, regionName, countryName, population);
    }

    private void initialize(String cityName, String regionName, String countryName, long population) {
        from_country_id = getCountryId();
        from_region_id = getRegionId();
        from_city_id = getCityId();

        from_country = countryName;
        from_region = regionName;
        from_city = cityName;

        from_population = population;
    }

    public String getCityName() {
        return from_city;
    }

    public String getRegionName() {
        return from_region;
    }

    public String getCountryName() {
        return from_country;
    }

    public long getNumUsers() {
        return from_population;
    }

}
