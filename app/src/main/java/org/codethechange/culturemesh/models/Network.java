package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Network implements Serializable {
    @PrimaryKey
    public long id;

    @Embedded
    public Location nearLocation;

    @Embedded
    public Location fromLocation;

    @Embedded
    public Language language;

    /**
     * networkClass is a boolean determining if a network is fromloc->nearLoc or language->nearLoc.
     * true: fromLoc->nearLoc
     * false: lang->nearLoc
     */
    public boolean networkClass;

    public Network(Location nearLocation, Location fromLocation, long id) {
        this.fromLocation = fromLocation;
        this.nearLocation = nearLocation;
        networkClass = true;
        this.id = id;
    }

    public Network(Location nearLocation, Language lang, long id) {
        this.language = lang;
        this.nearLocation = nearLocation;
        networkClass = false;
        this.id = id;
    }
}
