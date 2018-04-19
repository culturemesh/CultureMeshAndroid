package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.codethechange.culturemesh.Listable;
import org.codethechange.culturemesh.Searchable;

import java.io.Serializable;
import java.math.BigInteger;


/**
 * Created by nathaniel on 11/10/17.
 */

@Entity
public class Language implements Serializable, Listable, Searchable {
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

    public boolean matches(CharSequence constraint) {
        return name.contains(constraint);
    }

    public int getNumUsers() {
        return numSpeakers;
    }

    public String getListableName() {
        return name;
    }
}
