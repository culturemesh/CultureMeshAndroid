package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

/**
 * Created by Drew Gregory on 2/23/18.
 */
@Entity
public class Region extends Place {

    private String countryName;
    private String regionName;

    public Region(long regionId, long countryId, String regionName, String countryName, Point latLng,
                  long population, String featureCode) {
        super(countryId, regionId, Location.NOWHERE, latLng, population, featureCode);
        this.countryName = countryName;
        this.regionName = regionName;
    }

    public Region(long regionId, String regionName, Point latLng, long population, String featureCode) {
        super(Location.NOWHERE, regionId, Location.NOWHERE, latLng, population, featureCode);
        this.regionName = regionName;
        this.countryName = Place.NOWHERE;
    }

    public String getListableName() {
        if (! countryName.equals(Place.NOWHERE)) {
            return regionName + ", " + countryName;
        } else {
            return regionName;
        }
    }
}
