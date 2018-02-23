package org.codethechange.culturemesh.models;

/**
 * Created by Drew Gregory on 2/23/18.
 */

public class City extends Region {

    public long regionId;
    public String regionName;

    public City() {

    }

    public City(long id, String name, Point latLng, long pop, long countryId, String countryName,
                long regionId, String regionName) {
        super(id, name, latLng, pop, countryId, countryName);
        this.regionId = regionId;
        this.regionName = regionName;
    }
}
