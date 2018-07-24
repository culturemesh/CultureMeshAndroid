package org.codethechange.culturemesh.models;

import org.json.JSONException;
import org.json.JSONObject;

public interface Postable {
    public abstract JSONObject getPostJson() throws JSONException;
}
