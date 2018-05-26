package org.codethechange.culturemesh.models;

import java.io.Serializable;

/**
 * This class stores all the information related to a network. It is fully expanded, meaning that
 * its instance fields like {@link Network#nearLocation} store expanded objects (i.e. {@link Place},
 * not the stripped-down forms for database storage.
 */
public class Network implements Serializable {

    /**
     * ID of network. Must always be specified.
     */
    public long id;

    /**
     * The current location of users in the network. Must always be specified.
     */
    public Place nearLocation;

    /**
     * Where users of the network are from. Must be specified if the network is location-based.
     */
    public Place fromLocation;

    /**
     * What language the users of the network speak. Must be specified if the network is language-
     * based.
     */
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

    /**
     * Create a location-based network from the provided objects
     * @param nearLocation Where the network's users currently reside
     * @param fromLocation Where the network's users are all from
     * @param id ID of the network
     */
    public Network(Place nearLocation, Place fromLocation, long id) {
        this.fromLocation = fromLocation;
        this.nearLocation = nearLocation;
        isLanguageBased = true;
        this.id = id;
    }

    /**
     * Create a language-based network from the provided objects
     * @param nearLocation Where the network's users currently reside
     * @param lang What language the network's users all speak
     * @param id ID of the network
     */
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

    /**
     * Get a {@link DatabaseNetwork} with the IDs stored by the {@link Network} from which the
     * method is called.
     * @return The {@link DatabaseNetwork} associated with this {@link Network}
     */
    public DatabaseNetwork getDatabaseNetwork() {
        if (isLanguageBased()) {
            return new DatabaseNetwork(nearLocation.getNearLocation(), language.language_id, id);
        } else {
            return new DatabaseNetwork(nearLocation.getNearLocation(),
                    fromLocation.getFromLocation(), id);
        }
    }

    /**
     * Represent the object as a string suitable for debugging, but not for display to user.
     * @return String representation of the form {@code Class[var=value, var=value, var=value, ...]}
     */
    public String toString() {
        return "DatabaseNetwork[id=" + id + ", nearLocation=" + nearLocation + ", fromLocation=" +
                fromLocation + ", language=" + language + ", isLanguageBased=" + isLanguageBased +
                "]";
    }
}
