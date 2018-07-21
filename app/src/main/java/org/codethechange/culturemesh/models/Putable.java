package org.codethechange.culturemesh.models;

import org.json.JSONException;
import org.json.JSONObject;

public interface Putable {
    public abstract JSONObject getPutJson() throws JSONException;
}
