package com.culturemesh.android;

import com.culturemesh.android.models.Location;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(RobolectricTestRunner.class)
public class LocationTests {

    @Test
    public void constructor_jsonCountry_initializedCountryWithId() throws JSONException {
        /*
        {
            "id": 78
        }
         */
        JSONObject json = new JSONObject("{\"id\": 78}");
        Location loc = new Location(json);
        assertThat(loc.getCountryId(), is((long) 78));
        assertThat(loc.getType(), is(Location.COUNTRY));
    }

    @Test
    public void constructor_jsonNamedCountry_initializedCountryWithIdAndNonNullName() throws
            JSONException {
        /*
        {
            "id": 78
            "name": "someCountry"
        }
         */
        JSONObject json = new JSONObject("{\"id\": 78, \"name\": \"someCountry\"}");
        Location loc = new Location(json);
        assertThat(loc.getCountryId(), is((long) 78));
        assertThat(loc.getListableName(), notNullValue());
        assertThat(loc.getType(), is(Location.COUNTRY));
    }

    @Test
    public void constructor_jsonRegion_initializedRegionWithId() throws JSONException {
        /*
        {
            "id": 89
            "region_id": 10038
        }
         */
        JSONObject json = new JSONObject("{\"id\": 89, \"country_id\": 10038}");
        Location loc = new Location(json);
        assertThat(loc.getRegionId(), is((long) 89));
        assertThat(loc.getCountryId(), is((long) 10038));
        assertThat(loc.getType(), is(Location.REGION));
    }
}
