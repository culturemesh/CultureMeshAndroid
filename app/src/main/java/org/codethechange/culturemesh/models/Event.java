package org.codethechange.culturemesh.models;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigInteger;

import java.util.Date;

/**
 * Created by nathaniel on 11/10/17.
 */
@Entity
public class Event extends FeedItem implements Serializable{
    @PrimaryKey
    public long id;
    public long networkId;
    public String title;
    public String description;
    public String timeOfEvent;
    public long authorId;
    public String addressLine1;
    public String addressLine2;
    public String city;
    public String region;
    public String country;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeOfEvent() {
        return timeOfEvent;
    }

    public void setTimeOfEvent(String timeOfEvent) {
        this.timeOfEvent = timeOfEvent;
    }

    public long getAuthor() {
        return authorId;
    }

    public void setAuthor(User author) {
        this.authorId = author.id;
    }

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
        }
        if (json.has("city") && json.getString("city") != null &&
                !json.getString("city").equals("null")) {
            city = json.getString("city");
        }
        if (json.has("region") && json.getString("region") != null &&
                !json.getString("region").equals("null")) {
            region = json.getString("region");
        }
        if (json.has("country") && json.getString("country") != null &&
                !json.getString("country").equals("null")) {
            country = json.getString("country");
        }
    }
}
