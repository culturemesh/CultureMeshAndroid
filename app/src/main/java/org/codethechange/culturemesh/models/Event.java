package org.codethechange.culturemesh.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Describes an event like those shared in {@link Network}s
 */
@Entity
public class Event extends FeedItem implements Serializable, Putable, Postable {
    /**
     * A unique identifier for the event. This should be generated server-side.
     */
    @PrimaryKey
    public long id;

    /**
     * Unique identifier corresponding to the {@link Network} the {@link Event} is shared within
     */
    public long networkId;

    /**
     * User-generated title for the event. Generally short (one line).
     */
    public String title;

    /**
     * User-generated description of the event. May contain formatting from
     * {@link org.codethechange.culturemesh.FormatManager}.
     * @see org.codethechange.culturemesh.CreateEventActivity
     */
    public String description;

    /**
     * Date and time of the event which must strictly conform to {@code yyyy-MM-ddTHH:mm:ss.SSSZ}.
     * For example, {@code 2015-01-01T15:00:00.000Z} is an acceptable value.
     */
    public String timeOfEvent;

    /**
     * Unique identifier of the {@link User} who created the event
     */
    public long authorId;

    /**
     * First line of the address where the event is to take place. This value may be user-generated,
     * so it may not describe a valid address.
     */
    public String addressLine1;

    // TODO: Define empty address parts to be either empty strings or "null" (pick one)
    /**
     * Second line of the address where the event is to take place. Some addresses may not have
     * this value, in which case its value will be an empty string ({@code ""}).
     */
    public String addressLine2;

    /**
     * City portion of the address where the event is to take place. Some addresses may not have
     * this value, in which case its value will be an empty string ({@code ""}).
     */
    public String city;

    /**
     * Region portion of the address where the event is to take place. Some addresses may not have
     * this value, in which case its value will be an empty string ({@code ""}).
     */
    public String region;

    /**
     * Country portion of the address where the event is to take place. Some addresses may not have
     * this value, in which case its value will be an empty string ({@code ""}).
     */
    public String country;

    /**
     * Construct an Event object from the provided parameters.
     * @param id Unique identifier for the event
     * @param networkId Unique identifier for the {@link Network} the event is a part of
     * @param title User-generated title for the event
     * @param description User-generated description of the event
     * @param timeOfEvent Date and time of the event. Must strictly conform to the format
     *                    {@code yyyy-MM-ddTHH:mm:ss.SSSZ}.
     * @param author Unique identifier for the {@link User} creating the {@link Event}
     * @param addressLine1 Optional first line of the address. An empty string {@code ""} if absent.
     * @param addressLine2 Optional second line of the address. An empty string {@code ""} if absent.
     * @param city Optional city portion of the address. An empty string {@code ""} if absent.
     * @param region Optional region portion of the address. An empty string {@code ""} if absent.
     * @param country Optional country portion of the address. An empty string {@code ""} if absent.
     */
    public Event(long id, long networkId, String title, String description, String timeOfEvent,
                 long author, String addressLine1, String addressLine2, String city,
                 String region, String country) {
        this.id = id;
        this.networkId = networkId;
        this.title = title;
        this.description = description;
        this.timeOfEvent = timeOfEvent;
        this.authorId = author;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.region = region;
        this.country = country;

    }

    /**
     * Get the author-generated title for the {@link Event}
     * @return Title the {@link User} chose to describe the event
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the author-generated title for the {@link Event}
     * @param title Title the {@link User} chose to describe the event
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the author-generated description of the {@link Event}
     * @return Text the {@link User} wrote to describe the event
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the author-generated description of the {@link Event}
     * @param description Text the {@link User} wrote to describe the event
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the date and time of the event
     * @return Timestamp for the event, which will be formatted as {@code yyyy-MM-ddTHH:mm:ss.SSSZ}
     */
    public String getTimeOfEvent() {
        return timeOfEvent;
    }

    /**
     * Set the date and time of the event
     * @param timeOfEvent Timestamp for when the event will occur. Must strictly conform to
     *                    {@code yyyy-MM-ddTHH:mm:ss.SSSZ}.
     */
    public void setTimeOfEvent(String timeOfEvent) {
        this.timeOfEvent = timeOfEvent;
    }

    /**
     * Get the unique identifier of the {@link User} who created the event
     * @return Unique identifier of event author
     */
    public long getAuthor() {
        return authorId;
    }

    /**
     * Set the ID of the event's author. <bold>WARNING: The same ID must be used for a given
     * {@link User} across CultureMesh.</bold>
     * @param author Unique identifier of the {@link User} who created the event.
     */
    public void setAuthor(User author) {
        this.authorId = author.id;
    }

    /**
     * Generate a formatted form of the address for the event that is suitable for display to user.
     * @return UI-suitable form of the address where the event will take place. Address portions
     * (line1, line2, city, region, and country) are separated by commas, and missing portions are
     * excluded. Example: {@code 123 Any Street, New York, New York}. The address portions are
     * user-generated, so this String may not describe a valid address.
     */
    public String getAddress() {
        String address = addressLine1;

        String[] optional = {addressLine2, city, region, country};
        for (String s : optional) {
            if (s != null && !s.equals("") && !s.equals("null")) {
                address += ", " + s;
            }
        }
        return address;
    }

    /**
     * Empty constructor that does nothing to initialize any instance fields. <bold>For database
     * use only.</bold>
     */
    public Event () {

    }

    /**
     * Create a new Event object from a JSON representation that conforms to the following format:
     * <pre>
     *     {@code
     *      {
              "id": 0,
              "id_network": 0,
              "id_host": 0,
              "date_created": "string",
              "event_date": "2018-06-23T04:39:42.600Z",
              "title": "string",
              "address_1": "string",
              "address_2": "string",
              "country": "string",
              "city": "string",
              "region": "string",
              "description": "string"
             }
     *     }
     * </pre>
     * Note that {@code date_created} is not used and may be omitted. {@code address_2},
     * {@code city}, {@code region}, and {@code country} are optional and used only if provided.
     * @param json JSON representation of the {@link Event} to be created
     * @throws JSONException May be thrown if an improperly formatted JSON is provided
     */
    public Event(JSONObject json) throws JSONException {
        id = json.getLong("id");
        networkId = json.getLong("id_network");
        title = json.getString("title");
        description = json.getString("description");
        timeOfEvent = json.getString("event_date");
        authorId = json.getLong("id_host");
        addressLine1 = json.getString("address_1");
        if (json.has("address_2") && json.getString("address_2") != null &&
                !json.getString("address_2").equals("null")) {
            addressLine2 = json.getString("address_2");
        } else {
            addressLine2 = "";
        }
        if (json.has("city") && json.getString("city") != null &&
                !json.getString("city").equals("null")) {
            city = json.getString("city");
        } else {
            city = "";
        }
        if (json.has("region") && json.getString("region") != null &&
                !json.getString("region").equals("null")) {
            region = json.getString("region");
        } else {
            region = "";
        }
        if (json.has("country") && json.getString("country") != null &&
                !json.getString("country").equals("null")) {
            country = json.getString("country");
        } else {
            country = "";
        }
    }

    /**
     * Create a JSON representation of the object that conforms to the following format:
     * <pre>
     *     {@code
     *       {
                "id_network": 0,
                "id_host": 0,
                "event_date": "2018-07-21T15:10:30.838Z",
                "title": "string",
                "address_1": "string",
                "address_2": "string",
                "country": "string",
                "city": "string",
                "region": "string",
                "description": "string"
             }
           }
     * </pre>
     * This is intended to be the format used by the {@code /event/new} POST endpoint.
     * @return JSON representation of the object
     * @throws JSONException Unclear when this would be thrown
     */
    public JSONObject getPostJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id_network", networkId);
        json.put("id_host", authorId);
        json.put("event_date", timeOfEvent);
        json.put("title", title);
        json.put("address_1", addressLine1);
        json.put("address_2", addressLine2);
        json.put("country", country);
        json.put("city", city);
        json.put("region", region);
        json.put("description", description);
        return json;
    }

    /**
     * Create a JSON representation of the object that conforms to the following format:
     * <pre>
     *     {@code
     *       {
                 "id": 0,
                 "id_network": 0,
                 "id_host": 0,
                 "event_date": "2018-07-21T15:10:30.838Z",
                 "title": "string",
                 "address_1": "string",
                 "address_2": "string",
                 "country": "string",
                 "city": "string",
                 "region": "string",
                 "description": "string"
              }
           }
     * </pre>
     * This is intended to be the format used by the {@code /event/new} PUT endpoint.
     * @return JSON representation of the object
     * @throws JSONException Unclear when this would be thrown
     */
    public JSONObject getPutJson() throws JSONException {
        JSONObject json = getPostJson();
        json.put("id", id);
        return json;
    }
}
