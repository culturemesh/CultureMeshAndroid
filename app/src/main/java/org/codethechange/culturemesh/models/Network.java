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

    /**
     * networkClass is a boolean determining if a network is fromloc->nearLoc or language->nearLoc.
     * true: fromLoc->nearLoc
     * false: lang->nearLoc
     */
    public boolean networkClass;

    /**
     * We don't have posts, events, and users fields because they are so large and often unnecessary.
     * Instead, pass the id into an API.Get.networkUsers(), API.Get.networkEvents(), or
     * or API.networkPosts() (asynchronously of course) to get these fields.
     * */

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
        this.id = id;
    }

    //No override of toString() because getResources() requires an activity
    //We want to use string xml resources for localization.
    //For more info, check out
    //https://developer.android.com/guide/topics/resources/providing-resources.html
}
