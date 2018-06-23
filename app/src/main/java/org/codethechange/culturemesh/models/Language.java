package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import org.codethechange.culturemesh.Listable;

import java.io.Serializable;


/**
 * Created by nathaniel on 11/10/17.
 */

@Entity
public class Language implements Serializable, Listable {
    @PrimaryKey
    public long language_id;

    public String name;

    public int numSpeakers;

    public Language(long id, String name, int numSpeakers) {
        this.name = name;
        this.numSpeakers = numSpeakers;
        this.language_id = id;
    }

    public Language() {

    }

    public String toString() {
        return name;
    }

    public long getNumUsers() {
        return numSpeakers;
    }

    public String getListableName() {
        return name;
    }

    public String urlParam() {
        return Uri.encode(name);
    }
}
