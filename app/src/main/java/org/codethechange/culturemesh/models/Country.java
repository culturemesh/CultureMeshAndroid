package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by drewgregory on 2/23/18.
 */
@Entity
public class Country extends Place {
    // TODO: How does the isoA2 long get turned into 2-letter country codes?
    public long isoA2;
    private String name;

    public Country(long id, String name, Point latLng, long population, String featureCode, long isoA2) {
        super(id, Location.NOWHERE, Location.NOWHERE, latLng, population, featureCode);
        this.name = name;
        this.isoA2 = isoA2;
    }

    public Country(JSONObject json) throws JSONException {
        super(json);
        this.name = json.getString("name");
        this.isoA2 = json.getLong("iso_a2");
    }

    public String getListableName() {
        return name;
    }

    public String getName() {
        return name;
    }
}
