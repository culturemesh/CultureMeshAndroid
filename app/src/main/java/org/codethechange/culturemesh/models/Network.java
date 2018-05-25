package org.codethechange.culturemesh.models;

import java.io.Serializable;

public class Network implements Serializable {
    public long id;

    public Place nearLocation;

    public Place fromLocation;

    public Language language;

    /**
     * Denotes whether this network's <em>from</em> attribute is based on where an individual is
     * from or on what language they speak.
     *
     * {@code true}: Based on what language they speak
     *
     * {@code false}: Based on what location they are from
     */
    private boolean isLanguageBased;

    public Network(Place nearLocation, Place fromLocation, long id) {
        this.fromLocation = fromLocation;
        this.nearLocation = nearLocation;
        isLanguageBased = true;
        this.id = id;
    }

    public Network(Place nearLocation, Language lang, long id) {
        this.language = lang;
        this.nearLocation = nearLocation;
        isLanguageBased = false;
        this.id = id;
    }

    /**
     * Check whether this network is of people who speak the same language
     * @return {@code true} if the network is defined in terms of language, {@code false} otherwise
     */
    public boolean isLanguageBased() {
        return isLanguageBased;
    }

    /**
     * Check whether this network is of people who come from the same place
     * @return {@code true} if the network is defined by where members are from, {@code false}
     * otherwise
     */
    public boolean isLocationBased() {
        return ! isLanguageBased;
    }
}
