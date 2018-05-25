package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.PrimaryKey;

/**
 * This class is solely for storing the bare, ID-only form of a network in the database. After
 * being retrieved from the database or received from a network request, it should immediately be
 * used to create a {@link Network} object, with the additional information that comes with. Storing
 * only IDs in the database makes the {@link DatabaseNetwork#nearLocation},
 * {@link DatabaseNetwork#fromLocation} and {@link DatabaseNetwork#languageId} references pointers
 * to database entries with more information. This reduces the risk of conflicting information
 * and reduces the overhead of updating data in more than one spot in the database.
 */
public class DatabaseNetwork {
    /**
     * The network's ID. This is used as its unique identifier in the database.
     */
    @PrimaryKey
    public long id;

    /**
     * The location where the users of this network currently reside. It must not be null.
     */
    @Embedded
    public NearLocation nearLocation;

    /**
     * The location where the users of this network are from. It may be {@code null} to indicate
     * that no location is specified only if {@link DatabaseNetwork#isLanguageBased} is
     * {@code false}
     */
    @Embedded
    public FromLocation fromLocation;

    /**
     * The ID of the language the users of this network speak. It may be set to {@code -1} to
     * indicate no language being specified only if {@link DatabaseNetwork#isLanguageBased} is
     * {@code false}
     */
    public long languageId;

    /**
     * Denotes whether this network's <em>from</em> attribute is based on where an individual is
     * from or on what language they speak.
     *
     * {@code true}: Based on what language they speak
     *
     * {@code false}: Based on what location they are from
     */
    private boolean isLanguageBased;

    /**
     * Create a new {@link DatabaseNetwork} for a network of people who come from the same area
     * @param nearLocation Where the network's members currently reside
     * @param fromLocation Where the network's members are from
     * @param id ID for this network
     */
    public DatabaseNetwork(NearLocation nearLocation, FromLocation fromLocation, long id) {
        this.fromLocation = fromLocation;
        this.nearLocation = nearLocation;
        isLanguageBased = true;
        this.id = id;
    }

    /**
     * Create a new {@link DatabaseNetwork} for a network of people who speak the same language
     * @param nearLocation Where the network's members currently reside
     * @param langId ID for the language the network's members speak
     * @param id ID for this network
     */
    public DatabaseNetwork(NearLocation nearLocation, long langId, long id) {
        this.languageId = langId;
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
