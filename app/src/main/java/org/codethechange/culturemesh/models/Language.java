package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigInteger;


/**
 * Created by nathaniel on 11/10/17.
 */


public class Language implements Serializable {
    public long language_id;

    public String name;

    public int numSpeakers;

    public Language(String name) {
        this.name = name;
    }
}
