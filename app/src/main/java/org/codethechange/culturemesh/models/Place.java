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
public class Place extends Location implements Listable {
    @PrimaryKey
    public long databaseId;
    public String name;
    @Embedded
    public Point latLng;
    public long population;
    public String featureCode;

    public Place(long countryId, long regionId, long cityId, String name, Point latLng,
                 long population, String featureCode) {
        super(countryId, regionId, cityId);
        this.latLng = latLng;
        this.population = population;
        this.featureCode = featureCode;
        this.name = name;
        databaseId = getDatabaseId();
    }

    public long getNumUsers() {
        return population;
    }

    public String getListableName() {
        return name;
    }
}
