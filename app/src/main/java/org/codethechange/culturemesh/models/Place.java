package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.codethechange.culturemesh.Listable;

/**
 * Created by Drew Gregory on 2/23/18.
 *
 * This is the superclass for cities, regions, and countries.
 */
@Entity
public abstract class Place extends Location implements Listable {

    protected static final String NOWHERE = "nowhere";

    @PrimaryKey
    public long databaseId;
    @Embedded
    public Point latLng;
    public long population;
    public String featureCode;

    public Place(long countryId, long regionId, long cityId, Point latLng, long population,
                 String featureCode) {
        super(countryId, regionId, cityId);
        this.latLng = latLng;
        this.population = population;
        this.featureCode = featureCode;
        databaseId = getDatabaseId();
    }

    public long getNumUsers() {
        return population;
    }

    public abstract String getListableName();
}
