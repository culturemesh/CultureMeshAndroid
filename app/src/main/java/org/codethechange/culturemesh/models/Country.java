package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by drewgregory on 2/23/18.
 */
@Entity
public class Country extends Place {
    // TODO: What is isoA2?
    public long isoA2;
    private String name;

    public Country(long id, String name, Point latLng, long population, String featureCode) {
        super(id, Location.NOWHERE, Location.NOWHERE, latLng, population, featureCode);
        this.name = name;
    }

    public Country(JSONObject json) throws JSONException {
        super(json);
        this.name = json.getString("name");
    }

    public String getListableName() {
        return name;
    }

    public String getName() {
        return name;
    }
}
