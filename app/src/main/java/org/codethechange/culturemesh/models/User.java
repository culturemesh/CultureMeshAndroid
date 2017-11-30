package org.codethechange.culturemesh.models;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by nathaniel on 11/10/17.
 */

public class User {

    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private ArrayList<Network> enrolledNetworks;

    public User(String firstName, String lastName, String email, String username, ArrayList<Network> enrolledNetworks) {
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

    public ArrayList<Network> getEnrolledNetworks() {
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

    public void setEnrolledNetworks(ArrayList<Network> enrolledNetworks) {
        this.enrolledNetworks = enrolledNetworks;
    }
}
