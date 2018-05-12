package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

/**
 * Created by drewgregory on 2/23/18.
 */
@Entity
public class Country extends Place {
    // TODO: What is isoA2?
    public long isoA2;
    public String name;

    public Country(long id, String name, Point latLng, long population, String featureCode) {
        super(id, Location.NOWHERE, Location.NOWHERE, latLng, population, featureCode);
        this.name = name;
    }

    public String getListableName() {
        return name;
    }

    public String getName() {
        return name;
    }
}
