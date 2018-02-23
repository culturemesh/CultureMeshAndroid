package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

/**
 * Created by Drew Gregory on 2/23/18.
 */
@Entity
public class Region extends Place{
    public long countryId;
    public String countryName;

    public Region(){

    }

    public Region(long id, String name, Point latLng, long pop, long countryId, String countryName) {
        super(id, name, latLng, pop);
        this.countryId = countryId;
        this.countryName = countryName;
    }
}
