package com.culturemesh.android.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import com.culturemesh.android.Listable;

import com.culturemesh.android.Listable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


/**
 * Represents a language that may be spoken by users. It may be included as part of the definition
 * of a {@link Network} or as an attribute of a {@link User}, for example.
 */
@Entity
public class Language implements Serializable, Listable {

    /**
     * Unique identifier for the language and the {@code PrimaryKey} for databases
     */
    @PrimaryKey
    public long language_id;

    /**
     * Name of the language, as used by the API.
     */
    public String name;

    /**
     * The number of Culturemesh users who speak the language
     */
    public int numSpeakers;

    /**
     * Create a new {@link Language} object with the provided properties
     * @param id Unique identifier for the language. The same ID must be used everywhere
     * @param name Human-readable name of the language. This will be displayed to users. It must
     *             also be unique, as it is passed in API calls.
     * @param numSpeakers The number of Culturemesh users who speak the language
     */
    public Language(long id, String name, int numSpeakers) {
        this.name = name;
        this.numSpeakers = numSpeakers;
        this.language_id = id;
    }

    /**
     * Create a new {@link Language} from the JSON produced by an API call. The JSON must conform to
     * the following format:
     * <pre>
     *     {@code
     *       {
               "lang_id": 0,
               "name": "string",
               "num_speakers": 0,
               "added": 0
             }
           }
     * </pre>
     * Note that the {@code added} key is not used and therefore optional.
     * @param json JSON representation of the language to create.
     * @throws JSONException May be thrown for an improperly formatted JSON
     */
    public Language(JSONObject json) throws JSONException {
        language_id = json.getLong("id");
        name = json.getString("name");
        numSpeakers = json.getInt("num_speakers");
    }

    /**
     * Empty constructor solely for storing Language objects in a database.
     * <strong>Never use this!</strong>
     */
    public Language() {

    }

    /**
     * Convert the language to a unique string, its name
     * @return The name of the language
     */
    public String toString() {
        return name;
    }


    /**
     * Get the number of users who speak the language
     * @return Number of users who speak the language
     */
    public long getNumUsers() {
        return numSpeakers;
    }

    /**
     * Get a descriptive representation of the language suitable for display to user
     * @return Name of the language, abbreviated to be at most {@link Listable#MAX_CHARS} characters
     * long.
     */
    public String getListableName() {
        return Place.abbreviateForListing(name);
    }

    /**
     * Get a representation of the language suitable for passage in a URL for API calls
     * @return Name of the language encoded for inclusion in a URL
     */
    public String urlParam() {
        return Uri.encode(name);
    }
}
