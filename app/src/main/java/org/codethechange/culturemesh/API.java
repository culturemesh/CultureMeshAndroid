package org.codethechange.culturemesh;

import android.arch.persistence.room.Room;
import android.content.Context;

import org.codethechange.culturemesh.data.CMDatabase;
import org.codethechange.culturemesh.data.EventDao;
import org.codethechange.culturemesh.data.EventSubscription;
import org.codethechange.culturemesh.data.EventSubscriptionDao;
import org.codethechange.culturemesh.data.NetworkDao;
import org.codethechange.culturemesh.data.NetworkSubscription;
import org.codethechange.culturemesh.data.NetworkSubscriptionDao;
import org.codethechange.culturemesh.data.PostDao;
import org.codethechange.culturemesh.data.UserDao;
import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.FromLocation;
import org.codethechange.culturemesh.models.Language;
import org.codethechange.culturemesh.models.Location;
import org.codethechange.culturemesh.models.NearLocation;
import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.Point;
import org.codethechange.culturemesh.models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Drew Gregory on 11/14/17.
 */

class API {
    static final String SETTINGS_IDENTIFIER = "acmsi";
    static final String PERSONAL_NETWORKS = "pernet";
    static final String SELECTED_NETWORK = "selnet";
    static final boolean NO_JOINED_NETWORKS = false;
    static CMDatabase mDb;


    /**
     *This next section is code that parses JSON dummy data and adds it to the database. We can reuse
     * some of this code later.
     */
    void addUsers(){
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
                "    \"img_link\": \"https://www.lorempixel.com/720/691\",\n" +
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
                "    \"img_link\": \"https://dummyimage.com/900x38\",\n" +
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
                "    \"img_link\": \"https://www.lorempixel.com/273/913\",\n" +
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
                "    \"img_link\": \"https://placeholdit.imgix.net/~text?txtsize=55&txt=20x249&w=20&h=249\",\n" +
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
                User user = new User(userJSON.getLong("user_id"), userJSON.getString("firstName"),userJSON.getString("lastName"),
                        userJSON.getString("email"), userJSON.getString("username"), userJSON.getString("img_link"));
                uDAo.addUser(user);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void addNetworks() {
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
                "    }\n" +
                "]\n";
        try {
            NetworkDao nDAo = mDb.networkDao();
            JSONArray usersJSON = new JSONArray(rawDummy);
            for (int i = 0; i < usersJSON.length(); i++) {
                JSONObject netJSON = usersJSON.getJSONObject(i);
                Network network;
                JSONObject nearLocObj = netJSON.getJSONObject("location_cur");
                NearLocation nearLocation = new NearLocation(nearLocObj.getLong("city_id"),
                        nearLocObj.getLong("region_id"), nearLocObj.getLong("country_id"));
                if (netJSON.getInt("network_class") == 0) { //This means that it is a language?
                    JSONObject langJSON = netJSON.getJSONObject("language_origin");
                    Language lang = new Language(langJSON.getLong("id"),
                            langJSON.getString("name"), langJSON.getInt("num_speakers"));
                    network = new Network(nearLocation, lang, netJSON.getLong("id"));
                } else {//Location network.
                    JSONObject fromLocJSON = netJSON.getJSONObject("location_origin");
                    FromLocation fromLoc = new FromLocation(fromLocJSON.getLong("city_id"),
                            fromLocJSON.getLong("region_id"),fromLocJSON.getLong("country_id"));
                    network = new Network(nearLocation,fromLoc, netJSON.getLong("id"));
                }
                nDAo.insertNetworks(network);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //TODO: REMOVE DUMMY GENERATORS
    /*static ArrayList<User> genUsers() {
        ArrayList<User> users = new ArrayList<User>();
        User user = new User("Bob","Smith","crazyskater@hotmail.com",
                "bobbysmithery", new ArrayList<Network>(), "http://lorempixel.com/400/400/");
        users.add(user);
        User user2 = new User("Olivia","Brown","cter@hotmail.com",
                "obrown", new ArrayList<Network>(), "http://lorempixel.com/400/200/");
        users.add(user2);
        User user3 = new User("Nate", "Lee", "nlee@yahoo.com",
                "nlee", new ArrayList<Network>(), "http://lorempixel.com/200/200/");
        users.add(user3);
        User user4 = new User("Dylan","Grosz","something@gmail.com",
                "dgrosz", new ArrayList<Network>(), "http://lorempixel.com/200/200/");
        return users;
    }

    static ArrayList<Network> genNetworks() {
        ArrayList<Network> networks = new ArrayList<Network>();
        Location[] possibleLocations = {new Location("United States",
                "California", "Stanford", new Point[0]),
                new Location("United States","New York","New York City", new Point[0]),
                new Location("United States", "California", "Stanford", new Point[0]),
                new Location("United States","New York","White Plains", new Point[0]),
                new Location("United States", "California", "San Francisco", new Point[0]),
                new Location("United States","New York","Albany", new Point[0]),
                new Location("United States", "California", "Sacramento", new Point[0]),
                new Location("United States","New York","New York City", new Point[0]),
                new Location("United States","Maryland","Baltimore", new Point[0]),
                new Location("France","Provence","Aix-en-Provence", new Point[0])};
        for (int i = 0; i < 10; i++) {

           // networks.add(new Network(possibleLocations[i++], possibleLocations[i],122));
        }
        return networks;
    }

    static ArrayList<org.codethechange.culturemesh.models.Post> genPosts() {
        ArrayList<org.codethechange.culturemesh.models.Post> posts = new ArrayList<org.codethechange.culturemesh.models.Post>();
        for (int i = 0; i < 10; i++) {
            posts.add(new org.codethechange.culturemesh.models.Post(genUsers().get(i%3), "lorem ipsum " + i,  new Date().toString()));
        }
        return posts;
    }

    static ArrayList<Event> genEvents() {
        ArrayList<Event> events = new ArrayList<Event>();
        for (int i = 0; i < 10; i++) {
            events.add( new Event("event " + i, "lorem ipsum adsfa;lskd", new Date(), genUsers().get(0), "Stanford, CA", new Language("English")));
        }
        return events;
    }*/


    static class Get {
        /**
         * The protocol for GET requests is as follows...
         * 1. Check if cache has relevant data. If so, return it.
         * 2. Send network request to update data.
         */


        static NetworkResponse<User> user(long id) {
            UserDao uDao = mDb.userDao();
            User user = uDao.getUser(id);
            //TODO: Send network request.
            return new NetworkResponse<>(user == null, user);
        }

        static NetworkResponse<ArrayList<Network>> userNetworks(long id) {
            //TODO: Send network request for all subscriptions.
            NetworkSubscriptionDao nSDao  = mDb.networkSubscriptionDao();
            List<Long> netIds = nSDao.getUserNetworks(id);
            ArrayList<Network> nets = new ArrayList<>();
            for (Long netId : netIds ) {
                NetworkResponse res = network(netId);
                if (!res.fail()) {
                    nets.add((Network) res.getPayload());
                }
            }
            return new NetworkResponse<>(nets);
        }

        /*
            When will we ever use this? Perhaps viewing a user profile?
         */
        static NetworkResponse<List<org.codethechange.culturemesh.models.Post>> userPosts(long id) {
            PostDao pDao = mDb.postDao();
            List<org.codethechange.culturemesh.models.Post> posts = pDao.getUserPosts(id);
            return new NetworkResponse<>(posts);
        }

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

        static NetworkResponse<Network> network(long id) {
            //TODO: Send network request if not found.
            NetworkDao netDao = mDb.networkDao();
            Network net = netDao.getNetwork(id);
            return new NetworkResponse<>(net);
        }

        static NetworkResponse<List<org.codethechange.culturemesh.models.Post>> networkPosts(long id) {
            //TODO: Send network request.
            PostDao pDao = mDb.postDao();
            List<org.codethechange.culturemesh.models.Post> posts = pDao.getNetworkPosts(id);
            return new NetworkResponse<>(posts);
        }

        static NetworkResponse<List<Event>> networkEvents(long id) {
            //TODO:Send network request.... Applies to subsequent methods too.
            EventDao eDao = mDb.eventDao();
            List<Event> events = eDao.getNetworkEvents(id);
            return new NetworkResponse<>(events);
        }

        static NetworkResponse<ArrayList<User>> networkUsers(long id) {
            NetworkSubscriptionDao nSDao = mDb.networkSubscriptionDao();
            List<Long> userIds = nSDao.getNetworkUsers(id);
            ArrayList<User> users = new ArrayList<>();
            for (long uId: userIds) {
                NetworkResponse<User> res = user(uId);
                if (!res.fail()) {
                    users.add((User) res.getPayload());
                }
            }
            return new NetworkResponse<>(users);
        }

        static NetworkResponse<org.codethechange.culturemesh.models.Post> post(long id) {
            PostDao pDao = mDb.postDao();
            org.codethechange.culturemesh.models.Post post = pDao.getPost(id);
            return new NetworkResponse<>(post == null, post);
        }

        static NetworkResponse<Event> event(long id) {
            EventDao eDao = mDb.eventDao();
            Event event = eDao.getEvent(id);
            return new NetworkResponse<>(event == null, event);
        }

        static NetworkResponse<ArrayList<User>> eventAttendance(long id) {
            EventSubscriptionDao eSDao = mDb.eventSubscriptionDao();
            List<Long> uIds = eSDao.getEventUsers(id);
            ArrayList<User> users = new ArrayList<>();
            for (long uid : uIds) {
                NetworkResponse res = user(uid);
                if (!res.fail()) {
                    users.add((User) res.getPayload());
                }
            }
            return new NetworkResponse<>(users);
        }


    }

    static class Post {
        /*
            TODO: During production time, we will just send it off to server, not update locally.
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

        static NetworkResponse user(User user) {
            UserDao uDao = mDb.userDao();
            uDao.addUser(user);
            return new NetworkResponse<>(user);
        }

        static NetworkResponse network(Network network) {
            NetworkDao nDao = mDb.networkDao();
            nDao.insertNetworks(network);
            return new NetworkResponse<>(network);
        }

        static NetworkResponse post(org.codethechange.culturemesh.models.Post post) {
            return new NetworkResponse();
        }

        static NetworkResponse event(Event event) {
            return new NetworkResponse();
        }
    }

    static class Put {
        static NetworkResponse user(User user) {
            return new NetworkResponse();
        }

        static NetworkResponse event(Event event) {
            return new NetworkResponse();
        }

    }

    public static void loadAppDatabase(Context context) {
        if (mDb == null) {
            mDb = Room.databaseBuilder(context.getApplicationContext(), CMDatabase.class, "cmdatabase")
                    .build();
        }
    }

    static void closeDatabase(Context context) {
        mDb.close();
    }
}
