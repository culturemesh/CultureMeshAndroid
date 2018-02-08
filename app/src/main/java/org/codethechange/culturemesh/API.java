package org.codethechange.culturemesh;

import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.Language;
import org.codethechange.culturemesh.models.Location;
import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.Point;
import org.codethechange.culturemesh.models.User;
import org.codethechange.culturemesh.models.Post;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Drew Gregory on 11/14/17.
 */

class API {
    static final String SETTINGS_IDENTIFIER = "acmsi";
    static final String PERSONAL_NETWORKS = "pernet";
    static final String SELECTED_NETWORK = "selnet";
    static final boolean NO_JOINED_NETWORKS = true;


    //TODO: REMOVE DUMMY GENERATORS
    static ArrayList<User> genUsers() {
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

            networks.add(new Network(genPosts(), genEvents(), possibleLocations[i++], possibleLocations[i]));
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
    }


    static class Get {
        static NetworkResponse<ArrayList<User>> users() {
            return new NetworkResponse<>(genUsers());
        }

        static NetworkResponse<User> user(BigInteger id) {
            return new NetworkResponse<>(genUsers().get(0));
        }

        static NetworkResponse<ArrayList<Network>> userNetworks(BigInteger id) {
            return new NetworkResponse<>(genNetworks());
        }

        static NetworkResponse<ArrayList<org.codethechange.culturemesh.models.Post>> userPosts(BigInteger id) {
            return new NetworkResponse<>(genPosts());
        }

        static NetworkResponse<ArrayList<Event>> userEvents(BigInteger id) {
            return new NetworkResponse<>( genEvents());
        }

        static NetworkResponse<ArrayList<Network>> networks() {
            return new NetworkResponse<>(genNetworks());
        }

        static NetworkResponse<Network> network(BigInteger id) {
            return new NetworkResponse<>(genNetworks().get(0));
        }

        static NetworkResponse<ArrayList<org.codethechange.culturemesh.models.Post>> networkPosts(BigInteger id) {
            return new NetworkResponse<>(genPosts());
        }

        static NetworkResponse<ArrayList<Event>> networkEvents(BigInteger id) {
            return new NetworkResponse<>(genEvents());
        }

        static NetworkResponse<ArrayList<User>> networkUsers(BigInteger id) {
            return new NetworkResponse<>(genUsers());
        }

        static NetworkResponse<org.codethechange.culturemesh.models.Post> post(BigInteger id) {
            return new NetworkResponse<>(genPosts().get(0));
        }

        static NetworkResponse<Event> event(BigInteger id) {
            return new NetworkResponse<>(genEvents().get(0));
        }

        static NetworkResponse<ArrayList<User>> eventAttendance(BigInteger id) {
            return new NetworkResponse<>(genUsers());
        }


    }

    static class Post {
        static NetworkResponse addUserToEvent(BigInteger userId, BigInteger eventId) {
            return new NetworkResponse();
        }

        static NetworkResponse addUserToNetwork(BigInteger userId, BigInteger networkId) {
            return new NetworkResponse();
        }

        static NetworkResponse user(User user) {
            return new NetworkResponse();
        }

        static NetworkResponse network(Network network) {
            return new NetworkResponse();
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

}
