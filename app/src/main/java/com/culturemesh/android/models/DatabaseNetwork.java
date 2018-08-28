package com.culturemesh.android.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is solely for storing the bare, ID-only form of a network in the database. After
 * being retrieved from the database or received from a network request, it should immediately be
 * used to create a {@link Network} object, with the additional information that comes with. Storing
 * only IDs in the database makes the {@link DatabaseNetwork#nearLocation},
 * {@link DatabaseNetwork#fromLocation} and {@link DatabaseNetwork#languageId} references pointers
 * to database entries with more information. This reduces the risk of conflicting information
 * and reduces the overhead of updating data in more than one spot in the database.
 */
@Entity
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
    public boolean isLanguageBased;

    /**
     * Empty constructor for database use only. This should never be called by our code.
     */
    public DatabaseNetwork() {

    }

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
     * <h1>If the key <code>location_cur</code> is present (old JSON version): </h1>
     * Initialize instance fields with the data in the provided JSON. The following keys are
     * mandatory and used: {@code location_cur}, whose value is expected to be a JSON describing
     * a {@link NearLocation} object and can be passed to
     * {@link NearLocation#NearLocation(JSONObject)}, and {@code network_class}, whose value is
     * expected to be either {@code 0}, indicating a location-based network, or {@code 1},
     * indicating a language-based network. If the network is language-based, they key
     * {@code language_origin} must exist with a value of a JSON object containing a key
     * {@code id} whose value is the ID of a {@link Language}. If the network is location-based,
     * the key {@code location_origin} must exist and have a value of a JSON object representing
     * a {@link FromLocation} that can be passed to {@link FromLocation#FromLocation(JSONObject)}.
     * <strong>NOTE: This JSON format is deprecated and should not be used if possible.</strong>
     * <h1>If the key <code>location_cur</code> is not present (new JSON version): </h1>
     * Initialize instance fields with the data in the provided JSON. The following keys are
     * mandatory and used: All keys required by {@link NearLocation#NearLocation(JSONObject)}
     * and the key {@code network_class}, whose value is expected to be either {@code _l}, indicating
     * a language-based network, or one of {@code cc}, {@code rc}, and {@code co}, indicating a
     * location-based network. If the network is language-based, the key
     * {@code id_language_origin} must exist with a value of the ID of a {@link Language}. If the
     * network is location-based, all keys required by {@link FromLocation#FromLocation(JSONObject)}
     * must be present.
     * @param json JSON object describing the network in terms of IDs
     * @throws JSONException May be thrown in response to improperly formatted JSON
     */
    public DatabaseNetwork(JSONObject json) throws JSONException {
        if (json.has("location_cur")) {
            // Old, documentation-based JSON format
            JSONObject nearJSON = json.getJSONObject("location_cur");
            nearLocation = new NearLocation(nearJSON, true);

            id = json.getLong("id");

            isLanguageBased = json.getInt("network_class") == 0;
            if (isLanguageBased) {
                JSONObject langJSON = json.getJSONObject("language_origin");
                languageId = langJSON.getLong("id");
            } else {
                JSONObject fromJSON = json.getJSONObject("location_origin");
                fromLocation = new FromLocation(fromJSON, true);
            }
        } else {
            // New JSON format that reflects actual API response
            nearLocation = new NearLocation(json);
            id = json.getLong("id");
            isLanguageBased = json.getString("network_class").equals("_l");
            if (isLanguageBased) {
                languageId = json.getLong("id_language_origin");
            } else {
                fromLocation = new FromLocation(json);
            }
        }

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

    /**
     * Represent the object as a string suitable for debugging, but not for display to user.
     * @return String representation of the form {@code Class[var=value, var=value, var=value, ...]}
     */
    public String toString() {
        return "DatabaseNetwork[id=" + id + ", nearLocation=" + nearLocation + ", fromLocation=" +
                fromLocation + ", languageId=" + languageId + ", isLanguageBased=" + isLanguageBased +
                "]";
    }
}
