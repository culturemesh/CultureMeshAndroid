package com.culturemesh.android.models;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classes that implement this interface can be sent in the bodies of requests sent using
 * {@link com.culturemesh.android.API.Put#model(RequestQueue, Putable, String,
 * String, Response.Listener)}.
 */
public interface Putable {

    /**
     * Generates a JSON representation of the object that can be used in PUT requests to the server.
     * The exact format of the JSON depends upon the specifications of the server API. See the
     * server's Swagger documentation for more.
     * @return JSON representation of the object suitable for inclusion in the bodies of PUT requests
     * @throws JSONException May be thrown if any of the values to include in the JSON are
     * incompatible with the JSON format
     */
    JSONObject getPutJson() throws JSONException;
}
