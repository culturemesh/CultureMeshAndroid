package org.codethechange.culturemesh.models;

/**
 * Created by nathaniel on 11/10/17.
 */

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Network[] enrolledNetworks;

    public User(String firstName, String lastName, String email, String username, Network[] enrolledNetworks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.enrolledNetworks = enrolledNetworks;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Network[] getEnrolledNetworks() {
        return enrolledNetworks;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnrolledNetworks(Network[] enrolledNetworks) {
        this.enrolledNetworks = enrolledNetworks;
    }
}
