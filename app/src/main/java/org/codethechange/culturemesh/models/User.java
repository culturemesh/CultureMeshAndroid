package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by nathaniel on 11/10/17.
 */
@Entity
public class User implements Serializable{

    public long id;

    public String firstName;

    public String lastName;

    public String email;

    public String username;

    public String aboutMe;

    public int role;

    public String imageLink;

    public String imgURL;

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public User(String firstName, String lastName, String email, String username, String imgURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.imgURL = imgURL;
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

}
