package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.codethechange.culturemesh.Listable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * A {@code Place} is a {@link Location} with more information. While a {@link Location} stores only
 * city, region, and country IDs, {@code Place} also stores the areas position (latitude and
 * longitude), population, and feature code. {@code Place} is abstract, and some examples of its
 * subclasses are: {@link City}, {@link Region}, and {@link Country}.
 */
@Entity
public abstract class Place extends Location implements Listable, Serializable {

    /**
     * The {@code NOWHERE} constant is used internally by this hierarchy as the name of a location's
     * city, region, or country when that geographic identifier is not specified. For example,
     * Washington D.C. has no state (i.e. region), so its region might be stored as {@code NOWHERE}.
     * <strong>This should never be used by clients.</strong> Instead, creating such places should
     * be done through provided constructors or methods.
     */
    protected static final String NOWHERE = "nowhere";

    /**
     * The ID to be used by a database to identify this object. It is set using
     * {@link Place#getDatabaseId()}. See that method's documentation for more information.
     * Crucially <strong>it is NOT guaranteed to be unique.</strong>
     */
    @PrimaryKey
    public long id;

    /**
     * Latitude and longitude
     */
    @Embedded
    public Point latLng;

    /**
     * The population of the described area. This is for display under the "people" icon when areas
     * are listed.
     */
    public long population;

    /**
     * Feature code, which is a string describing the type of place represented (e.g. a capital,
     * a religiously important area, an abandoned populated area). See
     * http://www.geonames.org/export/codes.html for more examples.
     */
    public String featureCode;

    /**
     * Initialize instance fields with provided parameters. Also calls
     * {@link Location#Location(long, long, long)} with the provided IDs
     * Postcondition: {@link Place#id} is initialized using {@link Place#getDatabaseId()}
     * @param countryId ID of country
     * @param regionId ID of region
     * @param cityId ID of city
     * @param latLng Coordinates (latitude and longitude) of location
     * @param population Population of location
     * @param featureCode Feature code of location
     */
    public Place(long countryId, long regionId, long cityId, Point latLng, long population,
                 String featureCode) {
        super(countryId, regionId, cityId);
        this.latLng = latLng;
        this.population = population;
        this.featureCode = featureCode;
        id = getDatabaseId();
    }

    /**
     * Initializes ID instance fields using the provided JSON object
     * The following keys must be present and are used to fill the relevant instance fields:
     * {@code latitude}, {@code longitude}, {@code population}, {@code feature_code}. In addition,
     * the JSON object is passed to {@link Location#Location(JSONObject)}. See its documentation
     * for details on its requirements. {@link Place#id} is initialized using
     * {@link Place#getDatabaseId()}.
     * Precondition: The JSON must be validly formatted, with examples in
     * {@link org.codethechange.culturemesh.API}
     * @param json JSON object to extract initializing information from
     * @throws JSONException May be thrown for invalidly formatted JSON object
     */
    public Place(JSONObject json) throws JSONException {

        super(json);

        Point p = new Point();
        p.latitude = json.getLong("latitude");
        p.longitude = json.getLong("longitude");

        population = json.getLong("population");
        featureCode = json.getString("feature_code");

        id = getDatabaseId();
    }

    /**
     * Empty constructor for database use only. This should never be called by our code.
     */
    public Place() {

    }

    /**
     * Get the number of users (population) to display in conjunction with the location
     * @return Population of the location
     */
    public long getNumUsers() {
        return population;
    }

    /**
     * Subclasses are required to provide a method to generate a name suitable for display
     * in listings of places, as required to implement {@link Listable}. This name should be
     * unambiguous.
     * @return Name of Location suitable for display in UI lists
     */
    public abstract String getListableName();

    /**
     * Get the coordinates of the location
     * @return Latitude and longitude of the location
     */
    public Point getLatLng() {
        return latLng;
    }

    /**
     * Get the population of the location
     * @return Location's population
     */
    public long getPopulation() {
        return population;
    }

    /**
     * Get the feature code describing the location. See http://www.geonames.org/export/codes.html
     * for examples.
     * @return Location's feature code
     */
    public String getFeatureCode() {
        return featureCode;
    }

    /**
     * Represent the object as a string suitable for debugging, but not for display to user.
     * @return String representation of the form {@code Class[var=value, var=value, var=value, ...]}
     */
    public String toString() {
        return "Place[id=" + id + ", latlng=" + latLng + ", population=" + population + ", " +
                "featureCode=" + featureCode + "super=" + super.toString() + "]";
    }
}
