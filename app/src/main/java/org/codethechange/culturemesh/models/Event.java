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

    public String address;

    public Event(long id, long networkId, String title, String description, String timeOfEvent, long author, String address) {
        this.id = id;
        this.networkId = networkId;
        this.title = title;
        this.description = description;
        this.timeOfEvent = timeOfEvent;
        this.authorId = author;
        this.address = address;
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
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
     * Note that {@code date_created} is not used and may be omitted. {@code address_2} is optional
     * and used only if provided.
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

        address = json.getString("address_1");
        if (json.has("address_2") && json.getString("address_2") != null &&
                !json.getString("address_2").equals("null")) {
            address += "\n" + json.getString("address_2");
        }
        String cityString = json.getString("city");
        if (cityString == null || cityString.equals("null")) {
            cityString = "";
        }
        String regionString = json.getString("region");
        if (regionString == null || regionString.equals("null")) {
            regionString = "";
        } else {
            regionString = ", " + regionString;
        }
        String countryString = json.getString("country");
        if (countryString == null || countryString.equals("null")) {
            countryString = ", " + countryString;
        }
        address += "\n" + cityString + regionString + countryString;
    }

}
