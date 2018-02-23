package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

/**
 * Created by drewgregory on 2/23/18.
 */
@Entity
public class Country extends Place {
    public long isoA2;

    public Country() {

    }

    public Country(long id, String name, Point latLng, long pop) {
        super(id, name, latLng, pop);
    }
}
