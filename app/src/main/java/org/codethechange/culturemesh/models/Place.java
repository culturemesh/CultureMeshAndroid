package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Drew Gregory on 2/23/18.
 *
 * This is the superclass for cities, regions, and countries.
 */
@Entity
public class Place {
    @PrimaryKey
    public long id;
    public String name;
    public Point latLng;
    public long population;
    public String featureCode;

    public Place() {

    }

    public Place(long id, String name, Point latLng, long pop) {
        this.id = id;
        this.name = name;
        this.latLng = latLng;
        this.population = pop;
    }
}
