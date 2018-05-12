package org.codethechange.culturemesh.models;

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

    /**
     * Name of the country specified by the location
     */
    public String near_country;

    /**
     * Name of the region specified by the location
     */
    public String near_region;

    /**
     * Name of the city specified by the location
     */
    public String near_city;

    /**
     * Population of the location
     */
    public long near_population;

    // TODO: Handle undefined geographical areas (e.g. no region defined)
    /**
     * Initialize instance fields with provided parameters
     * @param cityId ID of city
     * @param regionId ID of region
     * @param countryId ID of country
     * @param cityName Name of city
     * @param regionName Name of region
     * @param countryName Name of country
     * @param population Population of location
     */
    public NearLocation(long cityId, long regionId,long countryId, String cityName,
                        String regionName, String countryName, long population) {
        super(cityId, regionId, countryId);
        initialize(cityName, regionName, countryName, population);
    }

    /**
     * Initialize this class's instance fields based on those provided and those from superclass
     * methods. This is what keeps the instance fields in sync with those of Location.
     * @param cityName Name of city
     * @param regionName Name of region
     * @param countryName Name of country
     * @param population Population of location
     */
    private void initialize(String cityName, String regionName, String countryName, long population) {
        near_country_id = getCountryId();
        near_region_id = getRegionId();
        near_city_id = getCityId();

        near_country = countryName;
        near_region = regionName;
        near_city = cityName;

        near_population = population;
    }

    /**
     * Get the name of the city
     * @return City name
     */
    public String getCityName() {
        return near_city;
    }

    /**
     * Get the name of the region
     * @return Region name
     */
    public String getRegionName() {
        return near_region;
    }

    /**
     * Get the name of the country
     * @return Country name
     */
    public String getCountryName() {
        return near_country;
    }

    /**
     * Get the population of the location
     * @return Location population
     */
    public long getNumUsers() {
        return near_population;
    }

}
