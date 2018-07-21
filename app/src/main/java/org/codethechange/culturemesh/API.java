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

import java.io.UnsupportedEncodingException;
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
    static final String NO_MAX_PAGINATION = "-1"; // If you do not need a maximum id.
    static final String HOSTING = "hosting";
    static final long NEW_NETWORK = -2;
    static CMDatabase mDb;
    //reqCounter to ensure that we don't close the database while another thread is using it.
    static int reqCounter;

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

        static void networkPostCount(RequestQueue queue, long id, final Response.Listener<NetworkResponse<Long>> listener) {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, API_URL_BASE + "network/" + id + "/post_count?" + getCredentials(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Long count = null;
                    try {
                        count = response.getLong("post_count");
                        listener.onResponse(new NetworkResponse<Long>(false, count));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onResponse(new NetworkResponse<Long>(true, R.string.network_not_found));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponse(new NetworkResponse<Long>(true, R.string.error));
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
        static void userEvents(RequestQueue queue, long id, String role, final Response.Listener<NetworkResponse<ArrayList<org.codethechange.culturemesh.models.Event>>> listener) {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE + "user/" + id
                    + "/events?role=" + role + getCredentials(), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<Event> events = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            events.add(new Event((JSONObject) response.get(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listener.onResponse(new NetworkResponse<ArrayList<Event>>(false, events));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponse(new NetworkResponse<ArrayList<Event>>(true, R.string.invalid_format));
                }
            });
            queue.add(req);
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
        static void networkPosts(final RequestQueue queue, final long id, String maxId, final Response.Listener<NetworkResponse<List<org.codethechange.culturemesh.models.Post>>> listener) {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE + "network/"
                    + id + "/posts?" + getPagination(maxId + "") + "&count=10" +  getCredentials(), null, new Response.Listener<JSONArray>() {

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
        static void networkEvents(final RequestQueue queue, final long id, String maxId,
                                                          final Response.Listener<NetworkResponse<List<Event>>> listener) {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE + "network/" +
                    id + "/events?" + getPagination(maxId) + "&count=10" + getCredentials(), null, new Response.Listener<JSONArray>() {
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

        static void autocompletePlace(RequestQueue queue, String text, final Response.Listener<NetworkResponse<List<Location>>> listener) {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE +
                    "location/autocomplete?input_text=" + text + getCredentials(), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<Location> places = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            places.add(new Location(response.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onResponse(new NetworkResponse<List<Location>>(true, R.string.invalid_format));
                        }
                    }
                    listener.onResponse(new NetworkResponse<List<Location>>(false, places));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponse(new NetworkResponse<List<Location>>(true, R.string.network_error));
                }
            });
            queue.add(req);
        }

        static void autocompleteLanguage(RequestQueue queue, String text, final Response.Listener<NetworkResponse<List<Language>>> listener) {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, API_URL_BASE +
                    "language/autocomplete?input_text=" + text + getCredentials(), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<Language> places = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            places.add(new Language(response.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onResponse(new NetworkResponse<List<Language>>(true, R.string.invalid_format));
                        }
                    }
                    listener.onResponse(new NetworkResponse<List<Language>>(false, places));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponse(new NetworkResponse<List<Language>>(true, R.string.network_error));
                }
            });
            queue.add(req);
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
                            return;
                        } else if (res.length() > 1) {
                            listener.onResponse(new NetworkResponse<Network>(true));
                            Log.i("API.Get.netFromTwoParam", "Multiple networks matched this: " +
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

        static void addUserToNetwork(RequestQueue queue, long userId, long networkId, final Response.Listener<NetworkResponse<String>> listener) {
            StringRequest req = new StringRequest(Request.Method.POST, API_URL_BASE + "user/" + userId + "/addToNetwork/"
                    + networkId + "?" + getCredentials(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listener.onResponse(new NetworkResponse<String>(false, response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponse(new NetworkResponse<String>(true, R.string.error_joining_network));
                }
            });
            queue.add(req);
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

        static void post(final RequestQueue queue, final org.codethechange.culturemesh.models.Post post,
                         Response.Listener<String> success,
                         Response.ErrorListener fail) {
            StringRequest req = new StringRequest(Request.Method.POST, API_URL_BASE + "post/new?" +
                    getCredentials(), success, fail) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return post.toJSON() == null ? null :
                                post.toJSON().toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        return null;
                    }
                }

            };
            queue.add(req);
        }

        static void reply(RequestQueue queue, final PostReply comment, final Response.Listener<NetworkResponse<String>> listener) {
            StringRequest req = new StringRequest(Request.Method.POST, API_URL_BASE + "post/" +
                    comment.parentId + "/reply?" + getCredentials(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listener.onResponse(new NetworkResponse<String>(false, null));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponse(new NetworkResponse<String>(true, null));
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return comment.getJSON().toString().getBytes("utf-8");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            queue.add(req);
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
        return "&key=" + Credentials.APIKey;
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

    /**
     * Fetches query parameter string you need to add in to the request url.
     * @param id maximum id of item you want to fetch. Use API.NO_MAX_PAGINATION if you want no limit.
     * @return pagination query param to add to request url.
     */
    private static String getPagination(String id) {
        if (id.equals(API.NO_MAX_PAGINATION)) {
            return "";
        } else {
            return "&max_id=" + id;
        }
    }


    interface InstantiationListener {
        public void instantiateComponent(RequestQueue queue, Object obj, Response.Listener listener);
    }

}
