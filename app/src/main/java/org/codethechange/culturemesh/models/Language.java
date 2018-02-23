package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigInteger;


/**
 * Created by nathaniel on 11/10/17.
 */

@Entity
public class Language implements Serializable {
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
}
