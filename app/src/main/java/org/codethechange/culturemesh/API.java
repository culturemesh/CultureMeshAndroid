package org.codethechange.culturemesh;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.codethechange.culturemesh.data.CMDatabase;
import org.codethechange.culturemesh.data.CityDao;
import org.codethechange.culturemesh.data.CountryDao;
import org.codethechange.culturemesh.data.EventDao;
import org.codethechange.culturemesh.data.EventSubscription;
import org.codethechange.culturemesh.data.EventSubscriptionDao;
import org.codethechange.culturemesh.data.LanguageDao;
import org.codethechange.culturemesh.data.NetworkDao;
import org.codethechange.culturemesh.data.NetworkSubscription;
import org.codethechange.culturemesh.data.NetworkSubscriptionDao;
import org.codethechange.culturemesh.data.PostDao;
import org.codethechange.culturemesh.data.PostReplyDao;
import org.codethechange.culturemesh.data.RegionDao;
import org.codethechange.culturemesh.data.UserDao;
import org.codethechange.culturemesh.models.City;
import org.codethechange.culturemesh.models.Country;
import org.codethechange.culturemesh.models.DatabaseNetwork;
import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.FromLocation;
import org.codethechange.culturemesh.models.Language;
import org.codethechange.culturemesh.models.Location;
import org.codethechange.culturemesh.models.NearLocation;
import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.Place;
import org.codethechange.culturemesh.models.Point;
import org.codethechange.culturemesh.models.Post;
import org.codethechange.culturemesh.models.PostReply;
import org.codethechange.culturemesh.models.Region;
import org.codethechange.culturemesh.models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
TODO: USE ALARMS FOR UPDATING DATA ON SUBSCRIBED NETWORKS
TODO: Figure out how we can handle trying to update data.
TODO: Figure out alternative to id's other than longs and ints, which cannot represent all numbers. (Maybe just use strings?)
     - Perhaps check if it comes from subscribed network, if not do network request instead of cache?
 */

/**
 * This API serves as the interface between the rest of the app and both the local database and the
 * CultureMesh servers. When another part of the app needs to request information, it calls API
 * methods to obtain it. Similarly, API methods should be used to store, send, and update
 * information. The API then handles searching local caches for the information, requesting it from
 * the CultureMesh servers, and updating the local cache. The local cache allows for offline access
 * to limited information.
 *
 * For simplicity, we store the id's of other model objects in the database, not the objects
 * themselves. Thus, when we return these objects, we need to instantiate them with the methods
 * provided in this class.
 *
 * IMPORTANT: If you want to use this class in your activity, make sure you run API.loadAppDatabase()
 * at the beginning of onPreExecute()/doInBackground(), and API.closeDatabase() in onPostExecute().
 * The app will crash otherwise.
 *
 */

class API {
    static final String SETTINGS_IDENTIFIER = "acmsi";
    static final String PERSONAL_NETWORKS = "pernet";
    static final String SELECTED_NETWORK = "selnet";
    final static String SELECTED_USER="seluser";
    final static String FIRST_TIME = "firsttime";
    static final boolean NO_JOINED_NETWORKS = false;
    static final String CURRENT_USER = "curruser";
    static final String API_URL_BASE = "https://www.culturemesh.com/api-dev/v1/";
    static CMDatabase mDb;
    //reqCounter to ensure that we don't close the database while another thread is using it.
    static int reqCounter;

    /**
     * Add users to the database by parsing the JSON stored in {@code rawDummy}. In case of any
     * errors, the stack trace is printed to the console.
     */
    static void addUsers(){
        String rawDummy = "\n" +
                "[\n" +
                "  {\n" +
                "    \"username\": \"boonekathryn\",\n" +
                "    \"fp_code\": \"IDK\",\n" +
                "    \"confirmed\": false,\n" +
                "    \"user_id\": 1,\n" +
                "    \"firstName\": \"Jonathan\",\n" +
                "    \"act_code\": \"IDK\",\n" +
                "    \"lastName\": \"Simpson\",\n" +
                "    \"about_me\": \"Adipisci ad molestiae vel fugit dolor in. Dolore ipsa libero. Doloremque dolor itaque enim. Saepe nam odit.\\nSimilique commodi ex quam quae vel in rerum. Esse nesciunt sunt sed magnam nihil.\",\n" +
                "    \"img_link\": \"https://lorempixel.com/200/200/\",\n" +
                "    \"role\": 1,\n" +
                "    \"gender\": \"female\",\n" +
                "    \"last_login\": \"2017-11-21 13:21:47\",\n" +
                "    \"registerDate\": \"2017-02-21 11:53:30\",\n" +
                "    \"email\": \"kristina00@reynolds.com\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"username\": \"williamosborne\",\n" +
                "    \"fp_code\": \"IDK\",\n" +
                "    \"confirmed\": true,\n" +
                "    \"user_id\": 2,\n" +
                "    \"firstName\": \"Abigail\",\n" +
                "    \"act_code\": \"IDK\",\n" +
                "    \"lastName\": \"Long\",\n" +
                "    \"about_me\": \"Doloremque maiores veritatis neque itaque earum molestias. Ab quos reprehenderit suscipit qui repellat maxime natus. Animi sit necessitatibus dicta magnam.\",\n" +
                "    \"img_link\": \"https://dummyimage.com/882x818\",\n" +
                "    \"role\": 1,\n" +
                "    \"gender\": \"female\",\n" +
                "    \"last_login\": \"2017-11-25 08:22:44\",\n" +
                "    \"registerDate\": \"2017-11-09 00:46:13\",\n" +
                "    \"email\": \"williamsjade@watkins.com\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"username\": \"rscott\",\n" +
                "    \"fp_code\": \"IDK\",\n" +
                "    \"confirmed\": false,\n" +
                "    \"user_id\": 3,\n" +
                "    \"firstName\": \"Emily\",\n" +
                "    \"act_code\": \"IDK\",\n" +
                "    \"lastName\": \"Miller\",\n" +
                "    \"about_me\": \"Quae iste eum labore. Harum in deleniti.\\nMagni distinctio non repellendus accusantium accusantium corporis. Cum provident perspiciatis molestias dolore voluptate reiciendis.\",\n" +
                "    \"img_link\": \"https://picsum.photos/400\",\n" +
                "    \"role\": 1,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"last_login\": \"2017-11-23 15:31:51\",\n" +
                "    \"registerDate\": \"2015-08-09 13:56:37\",\n" +
                "    \"email\": \"camposjohn@weber.com\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"username\": \"ihudson\",\n" +
                "    \"fp_code\": \"IDK\",\n" +
                "    \"confirmed\": false,\n" +
                "    \"user_id\": 4,\n" +
                "    \"firstName\": \"Mindy\",\n" +
                "    \"act_code\": \"IDK\",\n" +
                "    \"lastName\": \"Beltran\",\n" +
                "    \"about_me\": \"Eos libero eveniet sit tempore accusantium. Eveniet voluptates quos excepturi.\\nSaepe ut exercitationem sunt porro laborum. Doloremque quas assumenda cumque tenetur distinctio quis nobis.\",\n" +
                "    \"img_link\": \"https://picsum.photos/200\",\n" +
                "    \"role\": 1,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"last_login\": \"2017-11-21 07:32:34\",\n" +
                "    \"registerDate\": \"2016-05-08 21:44:02\",\n" +
                "    \"email\": \"nicholas32@hotmail.com\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"username\": \"oellis\",\n" +
                "    \"fp_code\": \"IDK\",\n" +
                "    \"confirmed\": false,\n" +
                "    \"user_id\": 5,\n" +
                "    \"firstName\": \"Kristin\",\n" +
                "    \"act_code\": \"IDK\",\n" +
                "    \"lastName\": \"Carey\",\n" +
                "    \"about_me\": \"Aut tenetur fugiat voluptas tenetur eos ducimus. Aut officiis aliquam ab voluptatem.\\nIpsum et aperiam totam voluptas voluptas. Dolor nulla voluptatem molestiae.\",\n" +
                "    \"img_link\": \"https://picsum.photos/400\",\n" +
                "    \"role\": 1,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"last_login\": \"2017-11-21 03:03:05\",\n" +
                "    \"registerDate\": \"2015-06-26 12:19:38\",\n" +
                "    \"email\": \"christopher18@allen.com\"\n" +
                "  }\n" +
                "]";
        try {
            UserDao uDAo = mDb.userDao();
            JSONArray usersJSON = new JSONArray(rawDummy);
            for (int i = 0; i < usersJSON.length(); i++) {
                JSONObject userJSON = usersJSON.getJSONObject(i);
                User user = new User(userJSON.getLong("user_id"), userJSON.getString("firstName"),
                        userJSON.getString("lastName"), userJSON.getString("email"),
                        userJSON.getString("username"), userJSON.getString("img_link"),
                        userJSON.getString("about_me"));
                uDAo.addUser(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add networks to the database by parsing the JSON stored in {@code rawDummy}. In case of any
     * errors, the stack trace is printed to the console. The JSON is split apart into the JSON
     * fragments for each city. Each of those JSONs are then passed to a {@link City}
     * constructor (specifically {@link DatabaseNetwork#DatabaseNetwork(JSONObject)}), which
     * extracts the necessary information and initializes itself. Those objects are then added to
     * the database via {@link NetworkDao}.
     */
    static void addNetworks() {
        String rawDummy = "[\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"location_cur\": {\n" +
                "        \"country_id\": 1,\n" +
                "        \"region_id\": 1,\n" +
                "        \"city_id\": 2\n" +
                "      },\n" +
                "      \"location_origin\": {\n" +
                "        \"country_id\": 1,\n" +
                "        \"region_id\": 2,\n" +
                "        \"city_id\": 3\n" +
                "      },\n" +
                "      \"language_origin\": {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"entish\",\n" +
                "        \"num_speakers\": 10,\n" +
                "        \"added\": 0\n" +
                "      },\n" +
                "      \"network_class\": 1,\n" +
                "      \"date_added\": \"1990-11-23 15:31:51\",\n" +
                "      \"img_link\": \"img1.png\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"location_cur\": {\n" +
                "        \"country_id\": 2,\n" +
                "        \"region_id\": 4,\n" +
                "        \"city_id\": 6\n" +
                "      },\n" +
                "      \"location_origin\": {\n" +
                "        \"country_id\": 1,\n" +
                "        \"region_id\": 1,\n" +
                "        \"city_id\": 1\n" +
                "      },\n" +
                "      \"language_origin\": {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"valarin\",\n" +
                "        \"num_speakers\": 1000,\n" +
                "        \"added\": 0\n" +
                "      },\n" +
                "      \"network_class\": 0,\n" +
                "      \"date_added\": \"1995-11-23 15:31:51\",\n" +
                "      \"img_link\": \"img2.png\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"location_cur\": {\n" +
                "        \"country_id\": 2,\n" +
                "        \"region_id\": null,\n" +
                "        \"city_id\": null\n" +
                "      },\n" +
                "      \"location_origin\": {\n" +
                "        \"country_id\": 1,\n" +
                "        \"region_id\": null,\n" +
                "        \"city_id\": 1\n" +
                "      },\n" +
                "      \"language_origin\": {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"valarin\",\n" +
                "        \"num_speakers\": 1000,\n" +
                "        \"added\": 0\n" +
                "      },\n" +
                "      \"network_class\": 0,\n" +
                "      \"date_added\": \"1995-11-23 15:31:51\",\n" +
                "      \"img_link\": \"img2.png\"\n" +
                "    }\n" +
                "]\n";
        try {
            NetworkDao nDAo = mDb.networkDao();
            JSONArray usersJSON = new JSONArray(rawDummy);
            for (int i = 0; i < usersJSON.length(); i++) {
                Log.i("API.addNetworks()", "Start adding network number (not ID) " + i +
                        " from JSON to Dao");
                JSONObject netJSON = usersJSON.getJSONObject(i);
                DatabaseNetwork dn = new DatabaseNetwork(netJSON);
                Log.i("API.addNetworks()", "Adding network " + dn + " to Dao");
                nDAo.insertNetworks(dn);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add regions to the database by parsing the JSON stored in {@code rawDummy}. In case of any
     * errors, the stack trace is printed to the console. The JSON is split apart into the JSON
     * fragments for each region. Each of those JSONs are then passed to a {@link Region}
     * constructor (specifically {@link Region#Region(JSONObject)}), which extracts the necessary
     * information and initializes itself. Those objects are then added to the database via
     * {@link RegionDao}.
     */
    static void addRegions(){
        String rawDummy = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"north\",\n" +
                "    \"latitude\": 5000.4321,\n" +
                "    \"longitude\": 1000.1234,\n" +
                "    \"country_id\": 1,\n" +
                "    \"country_name\": \"corneria\",\n" +
                "    \"population\": 400000,\n" +
                "    \"feature_code\": \"string\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"south\",\n" +
                "    \"latitude\": 4000.4321,\n" +
                "    \"longitude\": 1000.1234,\n" +
                "    \"country_id\": 1,\n" +
                "    \"country_name\": \"corneria\",\n" +
                "    \"population\": 600000,\n" +
                "    \"feature_code\": \"string\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"east\",\n" +
                "    \"latitude\": 50.100,\n" +
                "    \"longitude\": 250.200,\n" +
                "    \"country_id\": 2,\n" +
                "    \"country_name\": \"rohan\",\n" +
                "    \"population\": 300,\n" +
                "    \"feature_code\": \"idk\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 4,\n" +
                "    \"name\": \"west\",\n" +
                "    \"latitude\": 150.100,\n" +
                "    \"longitude\": 150.200,\n" +
                "    \"country_id\": 2,\n" +
                "    \"country_name\": \"rohan\",\n" +
                "    \"population\": 50,\n" +
                "    \"feature_code\": \"idk\"\n" +
                "  }\n" +
                "]";
        RegionDao rDao = mDb.regionDao();
        try {
            JSONArray regionsJSON = new JSONArray(rawDummy);
            for (int i = 0; i < regionsJSON.length(); i++) {
                JSONObject regionJSON = regionsJSON.getJSONObject(i);
                Region region = new Region(regionJSON);
                rDao.insertRegions(region);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add cities to the database by parsing the JSON stored in {@code rawDummy}. In case of any
     * errors, the stack trace is printed to the console. The JSON is split apart into the JSON
     * fragments for each city. Each of those JSONs are then passed to a {@link City}
     * constructor (specifically {@link City#City(JSONObject)}), which extracts the necessary
     * information and initializes itself. Those objects are then added to the database via
     * {@link CityDao}.
     */
    static void addCities() {
        String rawDummy = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"City A\",\n" +
                "    \"latitude\": 1.1,\n" +
                "    \"longitude\": 2.2,\n" +
                "    \"region_id\": 1,\n" +
                "    \"region_name\": \"north\",\n" +
                "    \"country_id\": 1,\n" +
                "    \"country_name\": \"corneria\",\n" +
                "    \"population\": 350000,\n" +
                "    \"feature_code\": \"idk\",\n" +
                "    \"tweet_terms\": \"idk\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"City B\",\n" +
                "    \"latitude\": 3.3,\n" +
                "    \"longitude\": 4.4,\n" +
                "    \"region_id\": 1,\n" +
                "    \"region_name\": \"north\",\n" +
                "    \"country_id\": 1,\n" +
                "    \"country_name\": \"corneria\",\n" +
                "    \"population\": 100000,\n" +
                "    \"feature_code\": \"idk\",\n" +
                "    \"tweet_terms\": \"idk\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"City C\",\n" +
                "    \"latitude\": 5.5,\n" +
                "    \"longitude\": 6.6,\n" +
                "    \"region_id\": 2,\n" +
                "    \"region_name\": \"south\",\n" +
                "    \"country_id\": 1,\n" +
                "    \"country_name\": \"corneria\",\n" +
                "    \"population\": 20000,\n" +
                "    \"feature_code\": \"idk\",\n" +
                "    \"tweet_terms\": \"idk\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 4,\n" +
                "    \"name\": \"City D\",\n" +
                "    \"latitude\": 7.7,\n" +
                "    \"longitude\": 8.8,\n" +
                "    \"region_id\": 3,\n" +
                "    \"region_name\": \"east\",\n" +
                "    \"country_id\": 2,\n" +
                "    \"country_name\": \"rohan\",\n" +
                "    \"population\": 280,\n" +
                "    \"feature_code\": \"idk\",\n" +
                "    \"tweet_terms\": \"idk\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 5,\n" +
                "    \"name\": \"City E\",\n" +
                "    \"latitude\": 9.9,\n" +
                "    \"longitude\": 10.10,\n" +
                "    \"region_id\": 4,\n" +
                "    \"region_name\": \"west\",\n" +
                "    \"country_id\": 2,\n" +
                "    \"country_name\": \"rohan\",\n" +
                "    \"population\": 20,\n" +
                "    \"feature_code\": \"idk\",\n" +
                "    \"tweet_terms\": \"idk\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 6,\n" +
                "    \"name\": \"City F\",\n" +
                "    \"latitude\": 11.11,\n" +
                "    \"longitude\": 12.12,\n" +
                "    \"region_id\": 4,\n" +
                "    \"region_name\": \"west\",\n" +
                "    \"country_id\": 2,\n" +
                "    \"country_name\": \"rohan\",\n" +
                "    \"population\": 25,\n" +
                "    \"feature_code\": \"idk\",\n" +
                "    \"tweet_terms\": \"idk\"\n" +
                "  }\n" +
                "]";
        CityDao cDao = mDb.cityDao();
        try {
            JSONArray citiesJSON = new JSONArray(rawDummy);
            for (int i = 0; i < citiesJSON.length(); i++) {
                JSONObject cityJSON = citiesJSON.getJSONObject(i);
                City city = new City(cityJSON);
                cDao.insertCities(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add countries to the database by parsing the JSON stored in {@code rawDummy}. In case of any
     * errors, the stack trace is printed to the console. The JSON is split apart into the JSON
     * fragments for each country. Each of those JSONs are then passed to a {@link Country}
     * constructor (specifically {@link Country#Country(JSONObject)}), which extracts the necessary
     * information and initializes itself. Those objects are then added to the database via
     * {@link CountryDao}.
     */
    static void addCountries() {
        String rawDummy = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"iso_a2\": 0,\n" +
                "    \"name\": \"corneria\",\n" +
                "    \"latitude\": 4321.4321,\n" +
                "    \"longitude\": 1234.1234,\n" +
                "    \"population\": 1000000,\n" +
                "    \"feature_code\": \"idk\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"iso_a2\": 0,\n" +
                "    \"name\": \"rohan\",\n" +
                "    \"latitude\": 100.100,\n" +
                "    \"longitude\": 200.200,\n" +
                "    \"population\": 350,\n" +
                "    \"feature_code\": \"idk\"\n" +
                "  }\n" +
                "]";
        CountryDao cDao = mDb.countryDao();
        try {
            JSONArray countriesJSON = new JSONArray(rawDummy);
            for (int i = 0; i < countriesJSON.length(); i++) {
                JSONObject countryJSON = countriesJSON.getJSONObject(i);
                Country country = new Country(countryJSON);
                cDao.insertCountries(country);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add posts to the database by parsing the JSON stored in {@code rawDummy}. In case of any
     * errors, the stack trace is printed to the console. The JSON is interpreted, and its values
     * are used to instantiate {@link Post} objects. Those objects are then added to the database via
     * {@link PostDao}.
     */
    static void addPosts(){
        String rawDummy = "[\n" +
                "  {\n" +
                "    \"user_id\": 4,\n" +
                "    \"post_text\": \"Ex excepturi quos vero nesciunt autem. Ipsum voluptates quaerat rerum praesentium modi.\\nEos culpa fuga maxime atque exercitationem nemo. Repellendus officiis et. Explicabo eveniet quibusdam magnam minima.\",\n" +
                "    \"network_id\": 1,\n" +
                "    \"img_link\": \"https://www.lorempixel.com/370/965\",\n" +
                "    \"vid_link\": \"https://dummyimage.com/803x720\",\n" +
                "    \"post_date\": \"2017-02-12 08:53:43\",\n" +
                "    \"post_class\": 0,\n" +
                "    \"id\": 1,\n" +
                "    \"post_original\": \"Not sure what this field is\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"user_id\": 3,\n" +
                "    \"post_text\": \"Minus cumque corrupti porro natus tenetur delectus illum. Amet aut molestias eaque autem ea odio.\\nAsperiores sed officia. Similique accusantium facilis sed. Eligendi tempora nisi sint tempora incidunt perferendis.\",\n" +
                "    \"network_id\": 1,\n" +
                "    \"img_link\": \"https://www.lorempixel.com/556/586\",\n" +
                "    \"vid_link\": \"https://dummyimage.com/909x765\",\n" +
                "    \"post_date\": \"2017-02-01 05:49:35\",\n" +
                "    \"post_class\": 0,\n" +
                "    \"id\": 2,\n" +
                "    \"post_original\": \"Not sure what this field is\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"user_id\": 1,\n" +
                "    \"post_text\": \"Veritatis illum occaecati est error magni nesciunt. Voluptate cum odio voluptatum quasi natus. Illo vel tempora pariatur tempore.\",\n" +
                "    \"network_id\": 0,\n" +
                "    \"img_link\": \"https://dummyimage.com/503x995\",\n" +
                "    \"vid_link\": \"https://dummyimage.com/796x497\",\n" +
                "    \"post_date\": \"2017-04-03 18:27:27\",\n" +
                "    \"post_class\": 0,\n" +
                "    \"id\": 3,\n" +
                "    \"post_original\": \"Not sure what this field is\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"user_id\": 3,\n" +
                "    \"post_text\": \"Dolorem ad ducimus laboriosam veritatis id quam rerum. Nostrum voluptatum mollitia modi.\\nVoluptas aut mollitia in perferendis blanditiis eaque eius. Recusandae similique ratione perspiciatis assumenda.\",\n" +
                "    \"network_id\": 1,\n" +
                "    \"img_link\": \"https://placeholdit.imgix.net/~text?txtsize=55&txt=917x558&w=917&h=558\",\n" +
                "    \"vid_link\": \"https://www.lorempixel.com/1016/295\",\n" +
                "    \"post_date\": \"2016-08-29 15:27:28\",\n" +
                "    \"post_class\": 0,\n" +
                "    \"id\": 4,\n" +
                "    \"post_original\": \"Not sure what this field is\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"user_id\": 4,\n" +
                "    \"post_text\": \"Ab voluptates omnis unde voluptas. Molestiae ipsam quis sapiente.\\nProvident illum consectetur deserunt. Nisi vero minus non corrupti impedit.\\nEaque dolor facilis iusto excepturi non. Sunt possimus modi animi.\",\n" +
                "    \"network_id\": 0,\n" +
                "    \"img_link\": \"https://www.lorempixel.com/231/204\",\n" +
                "    \"vid_link\": \"https://dummyimage.com/720x577\",\n" +
                "    \"post_date\": \"2017-07-29 18:52:43\",\n" +
                "    \"post_class\": 0,\n" +
                "    \"id\": 5,\n" +
                "    \"post_original\": \"Not sure what this field is\"\n" +
                "  }\n" +
                "]";
        PostDao pDao = mDb.postDao();
        try {
            JSONArray postsJSON = new JSONArray(rawDummy);
            for (int i = 0; i < postsJSON.length(); i++) {
                JSONObject postJSON = postsJSON.getJSONObject(i);
                Log.i("Network Id\'s", postJSON.getInt("network_id") + "");
                org.codethechange.culturemesh.models.Post post = new org.codethechange.culturemesh.models.Post(postJSON.getInt("id"), postJSON.getInt("user_id"),
                        postJSON.getInt("network_id"),postJSON.getString("post_text"),
                        postJSON.getString("img_link"),postJSON.getString("vid_link"),
                        postJSON.getString("post_date"));
                pDao.insertPosts(post);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add events to the database by parsing the JSON stored in {@code rawDummy}. In case of any
     * errors, the stack trace is printed to the console. The JSON is interpreted, and its values
     * are used to instantiate {@link Event} objects. Those objects are then added to the database via
     * {@link EventDao}.
     */
    static void addEvents() {
        String rawDummy = "[\n" +
                "  {\n" +
                "    \"description\": \"Ad officia impedit necessitatibus. Explicabo consequuntur commodi.\\nId id nostrum doloremque ab minus magnam. Ipsa placeat quasi dolores libero laboriosam.\\nNam tenetur ullam eius officia. Asperiores maiores soluta.\",\n" +
                "    \"title\": \"Centralized motivating encoding\",\n" +
                "    \"network_id\": 0,\n" +
                "    \"date_created\": \"2017-10-13 01:49:21\",\n" +
                "    \"address_1\": \"157 Stacy Drive\\nMercerfort, IA 59281\",\n" +
                "    \"address_2\": \"PSC 0398, Box 9876\\nAPO AE 17620\",\n" +
                "    \"event_date\": \"2017-11-03 17:28:51\",\n" +
                "    \"host_id\": 5,\n" +
                "    \"id\": 1,\n" +
                "    \"location\": [\n" +
                "      \"Uzbekistan\",\n" +
                "      \"Dunnmouth\",\n" +
                "      \"Chavezbury\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"description\": \"Laudantium sequi quisquam necessitatibus fugit eligendi. Rem blanditiis quibusdam molestias. Quis voluptate consequatur magnam nemo est magnam explicabo. A ipsam ipsum esse id quos.\",\n" +
                "    \"title\": \"Reverse-engineered 6th Generation\",\n" +
                "    \"network_id\": 1,\n" +
                "    \"date_created\": \"2017-09-09 17:12:57\",\n" +
                "    \"address_1\": \"1709 Fuller Freeway\\nChungland, PR 67499-3841\",\n" +
                "    \"address_2\": \"916 David Green\\nLake Adamville, MA 66822\",\n" +
                "    \"event_date\": \"2017-11-16 04:38:29\",\n" +
                "    \"host_id\": 2,\n" +
                "    \"id\": 2,\n" +
                "    \"location\": [\n" +
                "      \"Malaysia\",\n" +
                "      \"New John\",\n" +
                "      \"Kyliefort\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"description\": \"Doloremque natus cupiditate ratione sint eveniet. Vitae provident sapiente adipisci.\\nEt inventore quis quos deleniti numquam. Voluptate ipsam totam quas. Ea minima consequuntur consequuntur quaerat facere.\",\n" +
                "    \"title\": \"Adaptive fault-tolerant hardware\",\n" +
                "    \"network_id\": 1,\n" +
                "    \"date_created\": \"2017-10-11 01:13:33\",\n" +
                "    \"address_1\": \"7874 Bowman Port Suite 466\\nPattonhaven, PR 63278-5184\",\n" +
                "    \"address_2\": \"7436 William Village\\nRichardchester, NM 28208\",\n" +
                "    \"event_date\": \"2017-11-10 22:35:03\",\n" +
                "    \"host_id\": 3,\n" +
                "    \"id\": 3,\n" +
                "    \"location\": [\n" +
                "      \"Macao\",\n" +
                "      \"South Jillian\",\n" +
                "      \"New Jenniferfort\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"description\": \"Expedita non nam minima aperiam explicabo. Dicta sunt incidunt optio quae. Quam quibusdam dolorum voluptate corrupti ullam sequi. Amet at repellat iusto fuga voluptates aliquam.\",\n" +
                "    \"title\": \"Quality-focused asynchronous Graphic Interface\",\n" +
                "    \"network_id\": 0,\n" +
                "    \"date_created\": \"2017-11-10 17:48:56\",\n" +
                "    \"address_1\": \"28452 Rivera Pike\\nGambletown, SC 33593-8719\",\n" +
                "    \"address_2\": \"0314 Escobar Burgs\\nLake Bradburgh, CO 06768\",\n" +
                "    \"event_date\": \"2017-11-04 21:46:16\",\n" +
                "    \"host_id\": 5,\n" +
                "    \"id\": 4,\n" +
                "    \"location\": [\n" +
                "      \"Norfolk Island\",\n" +
                "      \"New Tara\",\n" +
                "      \"Johnton\"\n" +
                "    ]\n" +
                "  }\n" +
                "]";
        EventDao cDao = mDb.eventDao();
        try {
            JSONArray eventsJSON = new JSONArray(rawDummy);
            for (int i = 0; i < eventsJSON.length(); i++) {
                JSONObject eventJSON = eventsJSON.getJSONObject(i);
                Event event = new Event(eventJSON.getLong("id"), eventJSON.getLong("network_id"), eventJSON.getString("title"), eventJSON.getString("description"),
                        eventJSON.getString("date_created"), eventJSON.getLong("host_id"),
                        eventJSON.getString("address_1") + eventJSON.getString("address_2"));
                cDao.addEvent(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Store user subscriptions to networks in the database by adding {@link NetworkSubscription}
     * objects to the {@link NetworkSubscriptionDao}
     */
    static void subscribeUsers() {
        NetworkSubscription networkSubscription1 = new NetworkSubscription(3,1);
        NetworkSubscription networkSubscription2 = new NetworkSubscription(1,1);
        NetworkSubscription networkSubscription3 = new NetworkSubscription(2,1);
        NetworkSubscription networkSubscription4 = new NetworkSubscription(4,1);
        NetworkSubscription networkSubscription5 = new NetworkSubscription(4,0);
        NetworkSubscriptionDao netSubDao = mDb.networkSubscriptionDao();
        netSubDao.insertSubscriptions(networkSubscription1, networkSubscription2, networkSubscription3,
                networkSubscription4, networkSubscription5);
    }

    /**
     * Add replies to the database by parsing the JSON stored in {@code rawDummy}. In case of any
     * errors, the stack trace is printed to the console. The JSON is interpreted, and its values
     * are used to instantiate {@link PostReply} objects. Those objects are then added to the database via
     * {@link PostReplyDao}.
     */
    static void addReplies() {
        String rawDummy = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"parent_id\": 1,\n" +
                "    \"user_id\": 2,\n" +
                "    \"network_id\": 1,\n" +
                "    \"reply_date\": \"2017-03-12 08:53:43\",\n" +
                "    \"reply_text\": \"Test reply 1.\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"parent_id\": 1,\n" +
                "    \"user_id\": 3,\n" +
                "    \"network_id\": 1,\n" +
                "    \"reply_date\": \"2017-03-13 08:53:43\",\n" +
                "    \"reply_text\": \"Test reply 2.\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3,\n" +
                "    \"parent_id\": 3,\n" +
                "    \"user_id\": 4,\n" +
                "    \"network_id\": 0,\n" +
                "    \"reply_date\": \"2017-04-04 18:27:27\",\n" +
                "    \"reply_text\": \"Test reply 3.\"\n" +
                "  }\n" +
                "]";
        PostReplyDao postReplyDao = mDb.postReplyDao();
        try {
            JSONArray pRJSON = new JSONArray(rawDummy);
            for (int i = 0; i < pRJSON.length(); i++) {
                JSONObject pRObj = pRJSON.getJSONObject(i);
                PostReply pr = new PostReply(pRObj.getLong("id"), pRObj.getLong("parent_id"),
                        pRObj.getLong("user_id"), pRObj.getLong("network_id"),
                        pRObj.getString("reply_date"), pRObj.getString("reply_text"));
                postReplyDao.insertPostReplies(pr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * The protocol for GET requests is as follows...
     *      1. Check if cache has relevant data. If so, return it.
     *      2. Send network request to update data.
     */
    static class Get {

        /**
         * Get a {@link User} object from it's ID
         * @param id ID of user to find
         * @return If such a user was found, it will be the payload. Otherwise, the request will be
         * marked as failed.
         */
        static void user(RequestQueue queue, long id, final Response.Listener<NetworkResponse<User>> listener) {
            JsonObjectRequest authReq = new JsonObjectRequest(Request.Method.GET,
                    API_URL_BASE + "user/" + id + "?" + getCredentials(),
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    try {
                        //make User object out of user JSON.
                        User user = new User(res);
                        listener.onResponse(new NetworkResponse<>(false, user));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponse(new NetworkResponse<User>(true, null));
                }
            });
            queue.add(authReq);
        }


        static void networkUserCount(RequestQueue queue, long id, final Response.Listener<NetworkResponse<Long>> listener) {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, API_URL_BASE + "network/" + id + "/user_count?" + getCredentials(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Long count = response.getLong("user_count");
                        listener.onResponse(new NetworkResponse<Long>(false, count));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onResponse(new NetworkResponse<Long>(true, R.string.network_error));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponse(new NetworkResponse<Long>(true, R.string.network_error));
                }
            });
            queue.add(req);
        }

        /**
         * Get the networks a user belongs to
         * @param queue RequestQueue to which the asynchronous job will be added
         * @param id ID of the user whose networks will be fetched
         * @param listener Listener whose {@link com.android.volley.Response.Listener#onResponse(Object)}
         *                 is called with a {@link NetworkResponse} of an {@link ArrayList} of
         *                 {@link Network}s
         */
        static void userNetworks(final RequestQueue queue, final long id,
                                 final Response.Listener<NetworkResponse<ArrayList<Network>>> listener) {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE + "user/" +
                    id + "/networks?" + getCredentials(), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray res) {
                    final ArrayList<Network> nets = new ArrayList<>();
                    final AtomicInteger counter = new AtomicInteger();
                    counter.set(0);
                    final int numNets = res.length();
                    for (int i = 0; i < res.length(); i ++) {
                        try {
                            final DatabaseNetwork dnet = new DatabaseNetwork((JSONObject) res.get(i));
                            expandDatabaseNetwork(queue, dnet, new Response.Listener<NetworkResponse<Network>>() {
                                @Override
                                public void onResponse(NetworkResponse<Network> res) {
                                    if (!res.fail()) {
                                        Network net = res.getPayload();
                                        nets.add(net);
                                        counter.incrementAndGet();
                                        if (counter.get() == numNets) {
                                            listener.onResponse(new NetworkResponse<>(nets));
                                        }
                                    } else {
                                        Log.e("API.Get.userNetworks", "Error expanding " +
                                                dnet);
                                        listener.onResponse(new NetworkResponse<ArrayList<Network>>(
                                                true, res.getMessageID()));
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("API.Get.userNetworks", "Error parsing " + i + "th network of user " + id);
                            /*
                            Right now, this takes a "hard-fail" approach and returns a failed
                            NetworkResponse object whenever any JSON parsing error occurs. This may
                            not be the best approach.
                             */
                            listener.onResponse(new NetworkResponse<ArrayList<Network>>(true, R.string.network_error));
                            return;
                        }
                    }
                    listener.onResponse(new NetworkResponse<>(nets));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int messageID = processNetworkError("API.Get.userNetworks",
                            "ErrorListener for id=" + id, error);
                    listener.onResponse(new NetworkResponse<ArrayList<Network>>(true, messageID));
                }
            });
            queue.add(req);
        }

        /**
         * Get the {@link org.codethechange.culturemesh.models.Post}s a {@link User} has made. This
         * is done by asking {@link PostDao} for all posts with the user's ID, as performed by
         * {@link PostDao#getUserPosts(long)}.
         * @param id ID of the {@link User} whose {@link org.codethechange.culturemesh.models.Post}s
         *           are being requested
         * @return List of the {@link org.codethechange.culturemesh.models.Post}s the user has made
         */
        // TODO: When will we ever use this? Perhaps viewing a user profile?

        /**
         * Get the {@link org.codethechange.culturemesh.models.Post}s a {@link User} has made.
         * @param queue The {@link RequestQueue} that will house the network requests.
         * @param id The id of the {@link User}.
         * @param listener The listener that the UI will call when the request is finished.
         */
        static void userPosts(final RequestQueue queue, long id, final Response.Listener<NetworkResponse<ArrayList<org.codethechange.culturemesh.models.Post>>> listener) {
            /* OLD DB CODE: PostDao pDao = mDb.postDao();
            List<org.codethechange.culturemesh.models.Post> posts = pDao.getUserPosts(id);
            instantiatePosts(posts);*/
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE + "user/" +
                    id + "/posts?" + getCredentials(), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    final ArrayList<org.codethechange.culturemesh.models.Post> posts = new ArrayList<>();
                    // Here's the tricky part. We need to fetch user information for each post,
                    // but we only want to call the listener once, after all the requests are
                    // finished.
                    // Let's make a counter (numReqFin) for the number of requests finished. This will be
                    // a wrapper so that we can pass it by reference.
                    final AtomicInteger numReqFin = new AtomicInteger();
                    numReqFin.set(0);
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject res = (JSONObject) response.get(i);
                            posts.add(new org.codethechange.culturemesh.models.Post(res.getInt("id"), res.getInt("id_user"),
                                    res.getInt("id_network"), res.getString("post_text"),
                                    res.getString("img_link"), res.getString("vid_link"),
                                    res.getString("post_date")));
                            // Next, we will call instantiate postUser, but we will have a special
                            // listener.
                            instantiatePostUser(queue, posts.get(i), new Response.Listener<org.codethechange.culturemesh.models.Post>() {
                                @Override
                                public void onResponse(org.codethechange.culturemesh.models.Post response) {
                                    // Update the numReqFin counter that we have another finished post
                                    // object.
                                    if (numReqFin.addAndGet(1) == posts.size()) {
                                        // We finished!! Call the listener at last.
                                        listener.onResponse(new NetworkResponse<ArrayList<org.codethechange.culturemesh.models.Post>>(false, posts));
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(req);
        }

        /**
         * Get the {@link Event}s a {@link User} is subscribed to. This is done by searching for
         * {@link EventSubscription}s with the user's ID (via
         * {@link EventSubscriptionDao#getUserEventSubscriptions(long)}) and then inflating each
         * event from it's ID into a full {@link Event} object using {@link API.Get#event(long)}.
         * @param id ID of the {@link User} whose events are being searched for
         * @return List of {@link Event}s to which the user is subscribed
         */
        static NetworkResponse<ArrayList<Event>> userEvents(long id) {
            //TODO: Check for event subscriptions with network request.
            EventSubscriptionDao eSDao = mDb.eventSubscriptionDao();
            List<Long> eventIds = eSDao.getUserEventSubscriptions(id);
            ArrayList<Event> events = new ArrayList<>();
            for (Long eId : eventIds) {
                NetworkResponse res = event(eId);
                if (!res.fail()) {
                    events.add((Event) res.getPayload());
                }
            }
            return new NetworkResponse<>(events);
        }

        /**
         * Get the {@link Network} corresponding to the provided ID
         * @param queue Queue to which the asynchronous task to get the {@link Network} will be added
         * @param id ID of the {@link Network} to get
         * @param callback Listener whose {@link com.android.volley.Response.Listener#onResponse(Object)}
         *                 is called with the {@link NetworkResponse} created by the query.
         */
        static void network(final RequestQueue queue, final long id, final Response.Listener<NetworkResponse<Network>> callback) {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                    API_URL_BASE + "network/" + id + "?" + getCredentials(), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject res) {
                            try {
                                final DatabaseNetwork dbNet = new DatabaseNetwork(res);
                                expandDatabaseNetwork(queue, dbNet, new Response.Listener<NetworkResponse<Network>>() {
                                    @Override
                                    public void onResponse(NetworkResponse<Network> res) {
                                        if (!res.fail()) {
                                            Network net = res.getPayload();
                                            callback.onResponse(new NetworkResponse<>(net));
                                        } else {
                                            Log.e("API.Get.network", "Error expanding " + dbNet);
                                            callback.onResponse(new NetworkResponse<Network>(true, res.getMessageID()));
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                Log.e("API.Get.network", "Could not parse JSON of network: " + id);
                                e.printStackTrace();
                                callback.onResponse(new NetworkResponse<Network>(true));
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int messageID = processNetworkError("API.Get.network",
                            "ErrorListener for id=" + id, error);
                    callback.onResponse(new NetworkResponse<Network>(true, messageID));
                }
            });
            queue.add(req);
        }

        /**
         * Get the {@link org.codethechange.culturemesh.models.Post}s of a {@link Network}
         * @param queue Queue to which the asynchronous task will be added
         * @param id ID of the {@link Network} whose
         * {@link org.codethechange.culturemesh.models.Post}s will be returned
         * @param listener Listener whose {@link com.android.volley.Response.Listener#onResponse(Object)}
         *                 is called with the {@link NetworkResponse} created by the query.
         */
        static void networkPosts(final RequestQueue queue, final long id, final Response.Listener<NetworkResponse<List<org.codethechange.culturemesh.models.Post>>> listener) {
            /* TODO: Add caching capability.
            PostDao pDao = mDb.postDao();
            List<org.codethechange.culturemesh.models.Post> posts = pDao.getNetworkPosts((int) id);
            instantiatePosts(posts);*/
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE + "network/"
                    + id + "/posts?" + getCredentials(), null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    final ArrayList<org.codethechange.culturemesh.models.Post> posts = new ArrayList<>();
                    // Here's the tricky part. We need to fetch user information for each post,
                    // but we only want to call the listener once, after all the requests are
                    // finished.
                    // Let's make a counter (numReqFin) for the number of requests finished. This will be
                    // a wrapper so that we can pass it by reference.
                    final AtomicInteger numReqFin = new AtomicInteger();
                    numReqFin.set(0);
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject res = (JSONObject) response.get(i);
                            posts.add(new org.codethechange.culturemesh.models.Post(res.getInt("id"), res.getInt("id_user"),
                                    res.getInt("id_network"), res.getString("post_text"),
                                    res.getString("img_link"), res.getString("vid_link"),
                                    res.getString("post_date")));
                            // Next, we will call instantiate postUser, but we will have a special
                            // listener.
                            instantiatePostUser(queue, posts.get(i), new Response.Listener<org.codethechange.culturemesh.models.Post>() {
                                @Override
                                public void onResponse(org.codethechange.culturemesh.models.Post response) {
                                    // Update the numReqFin counter that we have another finished post
                                    // object.
                                    if (numReqFin.addAndGet(1) == posts.size()) {
                                        // We finished!! Call the listener at last.
                                        listener.onResponse(new NetworkResponse<List<org.codethechange.culturemesh.models.Post>>(false, posts));
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onResponse(new NetworkResponse<List<org.codethechange.culturemesh.models.Post>>(true));
                            Log.e("API.Get.networkPosts", "Could not parse JSON of post: " + e.getMessage());
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int messageID = processNetworkError("API.Get.networkPosts",
                            "ErrorListener for id=" + id, error);
                    listener.onResponse(new NetworkResponse<List<org.codethechange.culturemesh.models.Post>>(true, messageID));
                }
            });
            queue.add(req);
        }

        /**
         * Get the {@link Event}s corresponding to a {@link Network}
         * @param queue Queue to which the asynchronous task will be added
         * @param id ID of the {@link Network} whose {@link Event}s will be fetched
         * @param listener Listener whose {@link com.android.volley.Response.Listener#onResponse(Object)}
         *                 is called with the {@link NetworkResponse} created by the query.
         */
        static void networkEvents(final RequestQueue queue, final long id,
                                                          final Response.Listener<NetworkResponse<List<Event>>> listener) {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE + "network/" +
                    id + "/events?" + getCredentials(), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray res) {
                    ArrayList<Event> events = new ArrayList<>();
                    try {
                        for (int i = 0; i < res.length(); i++) {
                            Event e = new Event((JSONObject) res.get(i));
                            events.add(e);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onResponse(new NetworkResponse<List<Event>>(true));
                        Log.e("API.Get.networkEvents", "Could not parse JSON of event: " + e.getMessage());
                        return;
                    }
                    listener.onResponse(new NetworkResponse<List<Event>>(events));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int messageID = processNetworkError("API.Get.networkEvents",
                            "ErrorListener with id=" + id, error);
                    listener.onResponse(new NetworkResponse<List<Event>>(true, messageID));
                }
            });
            queue.add(req);
        }

        static void networkUsers(RequestQueue queue, final long id, final Response.Listener<NetworkResponse<ArrayList<User>>> listener) {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE + "network/" +
                    id + "/users?" + getCredentials(), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<User> users = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            User user = new User((JSONObject) response.get(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onResponse(new NetworkResponse<ArrayList<User>>(true));
                            Log.e("API.Get.networkEvents", "Could not parse JSON of event: " + e.getMessage());
                            return;
                        }
                    }
                    listener.onResponse(new NetworkResponse<ArrayList<User>>(false, users));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int messageID = processNetworkError("API.Get.networkEvents",
                            "ErrorListener with id=" + id, error);
                    listener.onResponse(new NetworkResponse<ArrayList<User>>(true, messageID));
                }
            });
            queue.add(req);
        }

        /**
         * IMPORTANT: GUIDE TO NETWORK REQUESTS
         * EXAMPLE NETWORK REQUEST CALL -- IMPORTANT!!
         * The format for API method calls will mimic more of a callback. We are basically
         * abstracting out doInBackground in the API methods now. View the Response.Listener<> as
         * the new onPostExecute() for ASync Tasks.
         * Notice that we now pass the Activity's RequestQueue for EVERY method call as the first
         * parameter. I made the id # 100 only so you can see a valid post id (1 is null). It should be postID.
         * Also notice that we are not handling caching or working with the database AT ALL.
         * We'll try to tackle that later.
         *
         * Link: https://developer.android.com/training/volley/simple
         *
         * Migration Workflow:
         * - Figure out how to do network request independent of Android client. First, look at the
         * swagger documentation by going to https://editor.swagger.io/ and copying and pasting
         * the code from https://github.com/alanefl/culturemesh-api/blob/master/spec_swagger.yaml.
         * Notice that you will have to prefix each of your endpoints with "https://www.culturemesh.com/api-dev/v1"
         * Also notice that you will have to suffix each of your endpoints with a key parameter:
         * "key=" + Credentials.APIKey (off of source control, check Slack channel for file to
         * manually import into your project)
         * - Test that you can do the request properly on your own. For most GET requests, you can
         * test within your own browser, or you can Postman [https://www.getpostman.com/]
         * (which I personally recommend, esp. if you need a JSON request body i.e. POST requests)
         * - Write the new API method with this signature:
         * API.[GET/POST/PUT].[method_name] ([RequestQueue], [original params], [Response.Listener<NetworkResponse<[Object_You_Want_To_Return]>>])
         * - The general format will be making a request. They will either be a JsonObjectRequest
         * (if you get an object returned from API) or JsonArrayRequest (if you get array of
         * json objects returned from API). Follow this example for the parameters. The meat of the
         * task will be in the Response.Listener<> parameter for the constructor.
         * - In this listener, you will have to convert the JSON object into our Java objects. Make
         * sure you handle errors with JSON formats. If you get stuck on this part, make sure your keys
         * conform to the actual keys returned on your manual requests tests with Postman.
         * - If the API returns an ERROR status code (somewhere in the 400's), the Response.ErrorListener()
         * will be called. I still call the passed callback function, but set NetworkResponse's 'fail'
         * param to true.
         * - Sometimes you will need to have multiple requests. For example, we need to get user data
         * for each post, but we only get user id's from the first post request. Thus, just nest
         * another request inside the listener of the first one if you need data from the first to pass
         * into the second (i.e. id_user from post to get user)
         */
        static void post(final RequestQueue queue, long id, final Response.Listener<NetworkResponse<org.codethechange.culturemesh.models.Post>> callback) {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                    API_URL_BASE + "post/" + id + "?" + getCredentials(),
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    org.codethechange.culturemesh.models.Post post = null;
                    try {
                        //Make post object out of JSON object.
                        post = new org.codethechange.culturemesh.models.Post(res.getInt("id"), res.getInt("id_user"),
                                res.getInt("id_network"), res.getString("post_text"),
                                res.getString("img_link"), res.getString("vid_link"),
                                res.getString("post_date"));
                        // Now, get author.
                        // For generalizing the logic when getting multiple posts, we will have to
                        // add the post to an ArrayList to pass it onto instantiate postUsers
                        instantiatePostUser(queue, post, new Response.Listener<org.codethechange.culturemesh.models.Post>() {
                            @Override
                            public void onResponse(org.codethechange.culturemesh.models.Post response) {
                                callback.onResponse(new NetworkResponse<org.codethechange.culturemesh.models.Post>(response == null, response));
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onResponse(new NetworkResponse<org.codethechange.culturemesh.models.Post>(true, null));
                    }
            });
            queue.add(req);
        }

        static NetworkResponse<Event> event(long id) {
            EventDao eDao = mDb.eventDao();
            Event event = eDao.getEvent(id);
            return new NetworkResponse<>(event == null, event);
        }

        /**
         * Fetch the comments of a post.
         * @param queue The {@link RequestQueue} to house the network requests.
         * @param id the id of the post that we want comments for.
         * @param listener the listener that we will call when the request is finished.
         */
        static void postReplies(final RequestQueue queue, long id, final Response.Listener<NetworkResponse<ArrayList<PostReply>>> listener){
            /*PostReplyDao dao = mDb.postReplyDao();
            List<PostReply> replies = dao.getPostReplies(id);
            instantiatePostReplies(replies);*/
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE + "post/" +
                    id + "/replies?"+ getCredentials(), null, new Response.Listener<JSONArray>(){

                @Override
                public void onResponse(JSONArray response) {
                    final ArrayList<PostReply> comments = new ArrayList<>();
                    // Here's the tricky part. We need to fetch user information for each post,
                    // but we only want to call the listener once, after all the requests are
                    // finished.
                    // Let's make a counter (numReqFin) for the number of requests finished. This will be
                    // a wrapper so that we can pass it by reference.
                    final AtomicInteger numReqFin = new AtomicInteger();
                    numReqFin.set(0);
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject res = (JSONObject) response.get(i);
                            comments.add(new PostReply(res));
                            // Next, we will call instantiate postUser, but we will have a special
                            // listener.
                            instantiatePostReplyUser(queue, comments.get(i), new Response.Listener<PostReply>() {
                                @Override
                                public void onResponse(PostReply response) {
                                    // Update the numReqFin counter that we have another finished post
                                    // object.

                                    if (numReqFin.addAndGet(1) == comments.size()) {
                                        // We finished!! Call the listener at last.
                                        listener.onResponse(new NetworkResponse<ArrayList<PostReply>>(false, comments));
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            listener.onResponse(new NetworkResponse<ArrayList<PostReply>>(true, R.string.network_error));
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(req);
        }

        static NetworkResponse<List<Place>> autocompletePlace(String text) {
            // TODO: Take argument for maximum number of locations to return?
            List<Place> locations = new ArrayList<>();
            //Get any related cities, countries, or regions.
            CityDao cityDao = mDb.cityDao();
            locations.addAll(cityDao.autoCompleteCities(text));
            RegionDao regionDao = mDb.regionDao();
            locations.addAll(regionDao.autoCompleteRegions(text));
            CountryDao countryDao = mDb.countryDao();
            locations.addAll(countryDao.autoCompleteCountries(text));
            return new NetworkResponse<>(locations == null, locations);
        }

        static NetworkResponse<List<Language>> autocompleteLanguage(String text) {
            // TODO: Take argument for maximum number of languages to return?
            // TODO: Use Database for autocompleteLanguage and use instead of dummy data
            List<Language> matches = new ArrayList<>();
            matches.add(new Language(0, "Sample Language 0", 10));
            return new NetworkResponse(matches);
        }

        /**
         * Get the {@link Network} that has the provided {@link Language} and {@link NearLocation}
         * @param queue Queue to which the asynchronous task will be added
         * @param lang {@link Language} of the {@link Network} to find
         * @param near {@link NearLocation} of the {@link Network} to find
         * @param listener Listener whose {@link com.android.volley.Response.Listener#onResponse(Object)}
         *                 is called with the {@link NetworkResponse} created by the query.
         */
        static void netFromLangAndNear(final RequestQueue queue, Language lang, NearLocation near,
                                  final Response.Listener<NetworkResponse<Network>> listener) {
            netFromTwoParams(queue, "near_location", near.urlParam(), "language",
                    lang.urlParam(), listener);
        }

        /**
         * Get the {@link Network} that has the provided {@link FromLocation} and {@link NearLocation}
         * @param queue Queue to which the asynchronous task will be added
         * @param from {@link FromLocation} of the {@link Network} to find
         * @param near {@link NearLocation} of the {@link Network} to find
         * @param listener Listener whose {@link com.android.volley.Response.Listener#onResponse(Object)}
         *                 is called with the {@link NetworkResponse} created by the query.
         */
        static void netFromFromAndNear(final RequestQueue queue, FromLocation from, NearLocation near,
                                       final Response.Listener<NetworkResponse<Network>> listener) {
            netFromTwoParams(queue, "near_location", near.urlParam(), "from_location",
                    from.urlParam(), listener);
        }

        /**
         * Get the {@link Network} that is defined by the two parameters provided
         * @param queue Queue to which the asynchronous task will be added
         * @param key1 Key for a parameter defining the {@link Network}
         * @param val1 Value associated with {@code key1}
         * @param key2 Key for a parameter defining the {@link Network}
         * @param val2 Value associated with {@code key2}
         * @param listener Listener whose {@link com.android.volley.Response.Listener#onResponse(Object)}
         *                 is called with the {@link NetworkResponse} created by the query.
         */
        private static void netFromTwoParams(final RequestQueue queue, final String key1, final String val1,
                                             final String key2, final String val2,
                                             final Response.Listener<NetworkResponse<Network>> listener) {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE +
                    "network/networks?" + key1 + "=" + val1 + "&" + key2 + "=" + val2 + "&" +
                    getCredentials(), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray res) {
                    try {
                        if (res.length() == 0) {
                            // No network was found
                            listener.onResponse(new NetworkResponse<Network>(true, R.string.noNetworkExist));
                        } else if (res.length() > 1) {
                            listener.onResponse(new NetworkResponse<Network>(true));
                            Log.e("API.Get.netFromTwoParam", "Multiple networks matched this: " +
                                    key1 + ":" + val1 + "," + key2 + ":" + val2);
                        }
                        final DatabaseNetwork dnet = new DatabaseNetwork((JSONObject) res.get(0));
                        expandDatabaseNetwork(queue, dnet, new Response.Listener<NetworkResponse<Network>>() {
                            @Override
                            public void onResponse(NetworkResponse<Network> res) {
                                if (!res.fail()) {
                                    Network net = res.getPayload();
                                    listener.onResponse(new NetworkResponse<>(net));
                                } else {
                                    Log.e("API.Get.netFromTwoparam", "Failure expanding DatabaseNetwork " + dnet);
                                    listener.onResponse(new NetworkResponse<Network>(true, res.getMessageID()));
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onResponse(new NetworkResponse<Network>(true));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int messageID = processNetworkError("API.Get.netFromTwoParam",
                            "ErrorListener from [" + key1 + ":" + val1 + "," + key2 + ":" +
                                    val2 + "]", error);
                    listener.onResponse(new NetworkResponse<Network>(true, messageID));
                }
            });
            queue.add(req);
        }

        /**
         * Get the {@link Language} that has the provided ID
         * @param queue Queue to which the asynchronous task will be added
         * @param id ID of the {@link Language} to find. Must be unique, and the same ID must be
         *           used throughout.
         * @param listener Listener whose {@link com.android.volley.Response.Listener#onResponse(Object)}
         *                 is called with the {@link NetworkResponse} created by the query.
         */
        static void language(final RequestQueue queue, final long id,
                             final Response.Listener<NetworkResponse<Language>> listener) {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, API_URL_BASE + "language/" +
                    id + "?" + getCredentials(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    try {
                        Language lang = new Language(res);
                        listener.onResponse(new NetworkResponse<>(lang));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onResponse(new NetworkResponse<Language>(true));
                        Log.e("API.Get.language", "Failure parsing JSON for ID=" + id);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int messageID = processNetworkError("API.Get.language",
                            "ErrorListener", error);
                    listener.onResponse(new NetworkResponse<Language>(true, messageID));
                }
            });
            queue.add(req);
        }

        /**
         * The API will return Post JSON Objects with id's for the user. Often, we will want to get
         * the user information associated with a post, such as the name and profile picture. This
         * method allows us to instantiate this user information for each post.
         * @param queue The Volley RequestQueue object that handles all the request queueing.
         * @param post An already instantiated Post object that has a null author field but a defined
         *             userId field.
         * @param listener the UI listener that will be called when we complete the task at hand.
         */
        static void instantiatePostUser(RequestQueue queue, final org.codethechange.culturemesh.models.Post post,
                                         final Response.Listener<org.codethechange.culturemesh.models.Post> listener) {
            JsonObjectRequest authReq = new JsonObjectRequest(Request.Method.GET,
                    "https://www.culturemesh.com/api-dev/v1/user/" + post.userId + "?" + getCredentials(),
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    try {
                        //make User object out of user JSON.
                        post.author = new User(res.getInt("id"),
                                res.getString("first_name"),
                                res.getString("last_name"),
                                res.getString("email"), res.getString("username"),
                                "https://www.culturemesh.com/user_images/" + res.getString("img_link"),
                                res.getString("about_me"));
                        listener.onResponse(post);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponse(null);
                }
            });
            queue.add(authReq);

        }

        /**
         * The API will return Post JSON Objects with id's for the user. Often, we will want to get
         * the user information associated with a post, such as the name and profile picture. This
         * method allows us to instantiate this user information for each post.
         * @param queue The Volley RequestQueue object that handles all the request queueing.
         * @param comment An already instantiated PostReply object that has a null author field but a defined
         *             userId field.
         * @param listener the UI listener that will be called when we complete the task at hand.
         */
        static void instantiatePostReplyUser(RequestQueue queue, final PostReply comment,
                                        final Response.Listener<PostReply> listener) {
            JsonObjectRequest authReq = new JsonObjectRequest(Request.Method.GET,
                    "https://www.culturemesh.com/api-dev/v1/user/" + comment.userId + "?" + getCredentials(),
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    try {
                        //make User object out of user JSON.
                        comment.author = new User(res.getInt("id"),
                                res.getString("first_name"),
                                res.getString("last_name"),
                                res.getString("email"), res.getString("username"),
                                "https://www.culturemesh.com/user_images/" + res.getString("img_link"),
                                res.getString("about_me"));
                        listener.onResponse(comment);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponse(comment);
                }
            });
            queue.add(authReq);

        }
    }



    static class Post {
        /*
            TODO: During production time, we will send it off to server (as well as update locally?).
         */
        static NetworkResponse<EventSubscription> addUserToEvent(long userId, long eventId) {
            EventSubscriptionDao eSDao = mDb.eventSubscriptionDao();
            EventSubscription es = new EventSubscription(userId, eventId);
            eSDao.insertSubscriptions(es);
            return new NetworkResponse<>(es);
        }

        static NetworkResponse addUserToNetwork(long userId, long networkId) {
            NetworkSubscriptionDao nSDao = mDb.networkSubscriptionDao();
            NetworkSubscription ns = new NetworkSubscription(userId, networkId);
            nSDao.insertSubscriptions(ns);
            return new NetworkResponse<>(ns);
        }

        static NetworkResponse removeUserFromNetwork(long userId, long networkId) {
            NetworkSubscriptionDao nSDao = mDb.networkSubscriptionDao();
            NetworkSubscription ns = new NetworkSubscription(userId, networkId);
            nSDao.deleteNetworkSubscriptions(ns);
            return new NetworkResponse<>(ns);
        }

        static NetworkResponse user(User user) {
            UserDao uDao = mDb.userDao();
            uDao.addUser(user);
            return new NetworkResponse<>(user);
        }

        static NetworkResponse network(Network network) {
            NetworkDao nDao = mDb.networkDao();
            nDao.insertNetworks(network.getDatabaseNetwork());
            return new NetworkResponse<>(network);
        }

        static void post(final RequestQueue queue, org.codethechange.culturemesh.models.Post post,
                                    Response.Listener<String> success,
                                    Response.ErrorListener fail) {
            /** TODO: For caching
             * PostDao pDao = mDb.postDao();
            pDao.insertPosts(post);
            */
            StringRequest req = new StringRequest(Request.Method.POST, API_URL_BASE + "post/new?" +
                    getCredentials(), success, fail);
            queue.add(req);
        }

        static NetworkResponse reply(PostReply comment) {
            PostReplyDao prDao = mDb.postReplyDao();
            prDao.insertPostReplies(comment);
            return new NetworkResponse(false, comment);
        }

        static NetworkResponse event(Event event) {
            EventDao eDao = mDb.eventDao();
            eDao.addEvent(event);
            return new NetworkResponse<>(false, event);
        }
    }

    static class Put {
        static NetworkResponse<User> user(User user) {
            UserDao uDao = mDb.userDao();
            uDao.addUser(user);
            return new NetworkResponse<User>(user == null, user);
        }

        static NetworkResponse event(Event event) {
            return new NetworkResponse();
        }
    }

    /**
     * {@link DatabaseNetwork}s do not store all the information associated with a {@link Network}.
     * Instead, a {@link DatabaseNetwork} is like a bundle of pointers to the parts of the
     * {@link Network} it represents. This method "expands" a {@link DatabaseNetwork} into a
     * {@link Network} by resolving those "pointers" and combining the results. The objects referred
     * to by IDs in the {@link DatabaseNetwork} are fetched with API calls and re-bundled into a
     * {@link Network}. See the documentation for {@link Location} for more information on this
     * inheritance hierarchy.
     * @param queue Queue to which the asynchronous task will be added
     * @param dn {@link DatabaseNetwork} to expand into a {@link Network}
     * @param listener Listener whose {@link com.android.volley.Response.Listener#onResponse(Object)}
     *                 is called with the {@link NetworkResponse} created by the query.
     */
    private static void expandDatabaseNetwork(final RequestQueue queue, final DatabaseNetwork dn,
                                                 final Response.Listener<NetworkResponse<Network>> listener) {
        locationToPlace(queue, dn.nearLocation, new Response.Listener<NetworkResponse<Place>>() {
            @Override
            public void onResponse(NetworkResponse<Place> res) {
                if (!res.fail()) {
                    final Place near = res.getPayload();
                    Log.i("API.expandDBNetwork", "Converted the DatabaseNetwork " + dn + " to" +
                            " the Place " + near + " for the near location");
                    if (dn.isLanguageBased()) {
                        Log.i("API.expandDBNetwork", "The DatabaseNetwork " + dn + " is language based");
                        Get.language(queue, dn.languageId, new Response.Listener<NetworkResponse<Language>>() {
                            @Override
                            public void onResponse(NetworkResponse<Language> res) {
                                if (!res.fail()) {
                                    Language lang = res.getPayload();
                                    Log.i("API.expandDBNetwork", "Converted the ID " + dn.languageId + " to" +
                                            " the Langauge " + lang + " for the language spoken");
                                    Network net = new Network(near, lang, dn.id);
                                    Log.i("API.expandDBNetwork", "Expanded the DatabaseNetwork " + dn + " to " +
                                            "the Network " + net);
                                    listener.onResponse(new NetworkResponse<>(net));
                                } else {
                                    listener.onResponse(new NetworkResponse<Network>(true,
                                            res.getMessageID()));
                                    Log.e("API.expandDBNetwork", "Failure expanding Language " +
                                            dn.languageId + " from ID");
                                }
                            }
                        });
                    } else {
                        Log.i("API.expandDBNetwork", "The DatabaseNetwork " + dn + " is location based");
                        locationToPlace(queue, dn.fromLocation, new Response.Listener<NetworkResponse<Place>>() {
                            @Override
                            public void onResponse(NetworkResponse<Place> res) {
                                if (!res.fail()) {
                                    Place from = res.getPayload();
                                    Log.i("API.expandDBNetwork", "Converted the FromLocation " + dn.fromLocation + " to" +
                                            " the Place " + from + " for the from location.");
                                    Network net = new Network(near, from, dn.id);
                                    Log.i("API.expandDBNetwork", "Expanded the DatabaseNetwork " + dn + " to " +
                                            "the Network " + net);
                                    listener.onResponse(new NetworkResponse<>(net));
                                } else {
                                    listener.onResponse(new NetworkResponse<Network>(true,
                                            res.getMessageID()));
                                    Log.e("API.expandDBNetwork", "Failure expanding FromLocation " +
                                            dn.fromLocation + " from ID");
                                }
                            }
                        });
                    }
                } else {
                    listener.onResponse(new NetworkResponse<Network>(true, res.getMessageID()));
                    Log.e("API.expandDBNetwork", "Failure expanding NearLocation " +
                            dn.nearLocation + " from ID");
                }
            }
        });
    }

    /**
     * {@link Location}s do not store all the information associated with a {@link Place}.
     * Instead, a {@link Location} is like a bundle of pointers to the parts of the
     * {@link Place} it represents. This method "expands" a {@link Location} into a
     * {@link Place} by resolving those "pointers" and combining the results. The objects referred
     * to by IDs in the {@link Location} are fetched with API calls and re-bundled into a
     * {@link Place}. See the documentation for {@link Location} for more information on this
     * inheritance hierarchy.
     * @param queue Queue to which the asynchronous task will be added
     * @param loc {@link Location} to expand into a {@link Place}
     * @param listener Listener whose {@link com.android.volley.Response.Listener#onResponse(Object)}
     *                 is called with the {@link NetworkResponse} created by the query.
     */
    private static void locationToPlace(final RequestQueue queue, final Location loc,
                                         final Response.Listener<NetworkResponse<Place>> listener) {
        String category;
        long id;
        if (loc.getType() == Location.CITY) {
            Log.i("API.locationToPlace", "Location " + loc + " is a city");
            category = "cities";
            id = loc.getCityId();
        } else if (loc.getType() == Location.REGION) {
            Log.i("API.locationToPlace", "Location " + loc + " is a region");
            category = "regions";
            id = loc.getRegionId();
        } else {
            Log.i("API.locationToPlace", "Location " + loc + " is a country");
            category = "countries";
            id = loc.getCountryId();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, API_URL_BASE +
                "location/" + category + "/" + id + "?" + getCredentials(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        Place p;
                        try {
                            if (loc.getType() == Location.CITY) {
                                p = new City(res);
                            } else if (loc.getType() == Location.REGION) {
                                p = new Region(res);
                            } else {
                                p = new Country(res);
                            }
                            listener.onResponse(new NetworkResponse<>(p));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onResponse(new NetworkResponse<Place>(true));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int messageID = processNetworkError("API.locationToPlace",
                        "ErrorResponse for loc=" + loc, error);
                listener.onResponse(new NetworkResponse<Place>(true, messageID));
            }
        });
        queue.add(req);
    }

    public static void loadAppDatabase(Context context) {
        reqCounter++;
        if (mDb == null) {
            mDb = Room.databaseBuilder(context.getApplicationContext(), CMDatabase.class, "cmdatabase").
                    fallbackToDestructiveMigration().build();
        }
    }

    static void closeDatabase() {
        if (--reqCounter <= 0) {
            mDb.close();
            mDb = null;
        }
    }

    /**
     * Process errors that could be returned in the form of a {@link VolleyError} the Response.Listener
     * @param tag Tag to include in log messages
     * @param task Description of the task being attempted. This will be included in log entries.
     * @param error The error returned
     * @return The resource ID of the error message that should be displayed to the user
     */
    private static int processNetworkError(String tag, String task, VolleyError error) {
        error.printStackTrace();
        if (error instanceof ServerError) {
            Log.e(tag, task + ": A ServerError occurred with code " + error.networkResponse.statusCode);
            return R.string.noConnection;
        } else if (error instanceof NetworkError) {
            // NoConnectionError is a subclass of NetworkError
            Log.e(tag, task + ": A NetworkError occurred.");
            return R.string.noConnection;
        } else if (error instanceof AuthFailureError) {
            Log.e(tag, task + ": An AuthFailureError occurred.");
        } else if (error instanceof ParseError) {
            Log.e(tag, task + ": A ParseError occurred.");
        } else if (error instanceof TimeoutError) {
            Log.e(tag, task + ": A TimeoutError occurred.");
            return R.string.timeout;
        }

        return R.string.genericFail;
    }

    /**
     * Use this method to append our credentials to our server requests. For now, we are using a
     * static API key. In the future, we are going to want to pass session tokens.
     * @return credentials string to be appended to request url as a param.
     */
    static String getCredentials(){
        return "key=" + Credentials.APIKey;
    }

    //TODO: Try to revive this helper method, or delete it.
    static void instantiateComponents(RequestQueue queue, final ArrayList list, final Response.Listener UICallback, InstantiationListener listener){

        // Here's the tricky part. We need to fetch information for each element,
        // but we only want to call the listener once, after all the requests are
        // finished.
        // Let's make a counter (numReqFin) for the number of requests finished. This will be
        // a wrapper so that we can pass it by reference.
        final AtomicInteger numReqFin = new AtomicInteger();
        numReqFin.set(0);
        Log.i("Do I make it in here?", "????");
        for (int i = 0; i < list.size(); i++) {
                // Next, we will call instantiate postUser, but we will have a special
                // listener.
                listener.instantiateComponent(queue, list.get(i), new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        // Update the numReqFin counter that we have another finished post
                        // object.
                        Log.i("Checking out this id", numReqFin.get() + " " + list.size());
                        if (numReqFin.addAndGet(1) == list.size()) {
                            // We finished!! Call the listener at last.
                            UICallback.onResponse(new NetworkResponse(false, list));
                        }
                    }
                });
        }
    }

    interface InstantiationListener {
        public void instantiateComponent(RequestQueue queue, Object obj, Response.Listener listener);
    }

}
