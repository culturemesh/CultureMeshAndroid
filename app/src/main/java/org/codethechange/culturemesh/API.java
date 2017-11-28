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

public class API {

    //TODO: REMOVE DUMMY GENERATORS
    static ArrayList<User> genUsers() {
        ArrayList<User> users = new ArrayList<User>();
        User user = new User("Bob","Smith","crazyskater@hotmail.com",
                "bobbysmithery", new ArrayList<Network>());
        users.add(user);
        User user2 = new User("Olivia","Brown","cter@hotmail.com",
                "obrown", new ArrayList<Network>());
        users.add(user2);
        User user3 = new User("Nate", "Lee", "nlee@yahoo.com",
                "nlee", new ArrayList<Network>());
        users.add(user3);
        return users;
    }

    static ArrayList<Network> genNetworks() {
        ArrayList<Network> networks = new ArrayList<Network>();
        for (int i = 0; i < 10; i++) {

            networks.add(new Network(genPosts(), genEvents(), new Location("United States", "California", "Stanford", new Point[0])));
        }
        return networks;
    }

    static ArrayList<org.codethechange.culturemesh.models.Post> genPosts() {
        ArrayList<org.codethechange.culturemesh.models.Post> posts = new ArrayList<org.codethechange.culturemesh.models.Post>();
        for (int i = 0; i < 10; i++) {
            posts.add(new org.codethechange.culturemesh.models.Post(genUsers().get(0), "lorem ipsum " + i, "Post " + i, new Date()));
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
        static ArrayList<User> users() {
            return genUsers();
        }

        static User user(BigInteger id) {
            return genUsers().get(0);
        }

        static ArrayList<Network> userNetworks(BigInteger id) {
            return genNetworks();
        }

        static ArrayList<org.codethechange.culturemesh.models.Post> userPosts(BigInteger id) {
            return genPosts();
        }

        static ArrayList<Event> userEvents(BigInteger id) {
            return genEvents();
        }

        static ArrayList<Network> networks() {
            return genNetworks();
        }

        static Network network(BigInteger id) {
            return genNetworks().get(0);
        }

        static ArrayList<org.codethechange.culturemesh.models.Post> networkPosts(BigInteger id) {
            return genPosts();
        }

        static ArrayList<Event> networkEvents(BigInteger id) {
            return genEvents();
        }

        static ArrayList<User> networkUsers(BigInteger id) {
            return genUsers();
        }

        static org.codethechange.culturemesh.models.Post post(BigInteger id) {
            return genPosts().get(0);
        }

        static Event event(BigInteger id) {
            return genEvents().get(0);
        }

        static ArrayList<User> eventAttendance(BigInteger id) {
            return genUsers();
        }


    }

    static class Post {
        static void addUserToEvent(BigInteger userId, BigInteger eventId) {

        }

        static void addUserToNetwork(BigInteger userId, BigInteger networkId) {

        }

        static void user(User user) {

        }

        static void network(Network network) {

        }

        static void post(org.codethechange.culturemesh.models.Post post) {

        }

        static void event(Event event) {

        }
    }

    static class Put {
        static void user(User user) {

        }

        static void event(Event event) {

        }

    }

}
