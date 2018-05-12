package org.codethechange.culturemesh.models;

/**
 * Created by Drew Gregory on 2/19/18.
 * Exact copy of Location, but used as Embedded Entity in SQLite Database.
 */

public class NearLocation extends DatabaseLocation {
    /**
     * When stored in the Database, we will store just the id's. The object returned near the API
     * will have the country, region, and city updated.
     * The default value for country, region, city is 0.
     */
    public long near_country_id;
    public long near_region_id;
    public long near_city_id;

    public String near_country;
    public String near_region;
    public String near_city;

    public long near_population;

    public NearLocation(long cityId, long regionId,long countryId, long population) {
        super(cityId, regionId, countryId);
        initialize(population);
    }

    private void initialize(long population) {
        near_country_id = getCountryId();
        near_region_id = getRegionId();
        near_city_id = getCityId();

        near_country = getCountryName();
        near_region = getRegionName();
        near_city = getCityName();

        near_population = population;
    }

    public String getCityName() {
        return near_city;
    }

    public String getRegionName() {
        return near_region;
    }

    public String getCountryName() {
        return near_country;
    }

    public long getNumUsers() {
        return near_population;
    }

}
