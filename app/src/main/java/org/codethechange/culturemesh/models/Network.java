package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by nathaniel on 11/10/17.
 */

@Entity
public class Network implements Serializable{
    //TODO: Figure out Room deals with bigints.
    @PrimaryKey
    public long id;

    @Embedded
    public NearLocation nearLocation;

    @Embedded
    public FromLocation fromLocation;

    @Embedded
    public Language language;

    public boolean networkClass;
    //TODO: Possibly remove this field -- network object doesn't have posts field
    //private ArrayList<Post> posts;
    //private ArrayList<Event> events;

    public Network() {

    }
    public Network(NearLocation nearLocation,
                   FromLocation fromLocation, long id) {
        this.fromLocation = fromLocation;
        this.nearLocation = nearLocation;
        networkClass = true;
        this.id = id;
    }

    public Network(NearLocation nearLocation,
                   Language lang, long id) {
        this.language = lang;
        this.nearLocation = nearLocation;
        networkClass = false;
    }

    //No override of toString() because getResources() requires an activity
    //We want to use string xml resources for localization.
    //For more info, check out
    //https://developer.android.com/guide/topics/resources/providing-resources.html
}
