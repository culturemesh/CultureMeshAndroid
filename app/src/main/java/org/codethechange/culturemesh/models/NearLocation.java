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
     * Initialize this class's instance fields based on those provided and those from superclass
     * methods. This is what keeps the instance fields in sync with those of Location.
     */
    private void initialize() {
        near_country_id = getCountryId();
        near_region_id = getRegionId();
        near_city_id = getCityId();
    }
}
