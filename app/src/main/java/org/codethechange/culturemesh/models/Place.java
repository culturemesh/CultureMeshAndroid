package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.codethechange.culturemesh.Listable;
import org.codethechange.culturemesh.Searchable;

/**
 * Created by Drew Gregory on 2/23/18.
 *
 * This is the superclass for cities, regions, and countries.
 */
@Entity
public class Place implements Listable, Searchable {
    @PrimaryKey
    public long id;
    public String name;
    @Embedded
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

    public boolean matches(CharSequence constraint) {
        return name.contains(constraint);
    }

    public int getNumUsers() {
        // TODO: Store number of members in Location or query API
        return 0;
    }

    public String getListableName() {
        return name;
    }
}
