package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by nathaniel on 11/10/17.
 */
@Entity
public class User implements Serializable{
    @PrimaryKey
    public long id;

    public String firstName;

    public String lastName;

    public String email;

    public String username;

    public String aboutMe;

    public int role;

    public String imgURL;

    public String getImgURL() {
        return imgURL;
    }


    public User(long id, String firstName, String lastName, String email, String username,
                String imgURL, String aboutMe) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.imgURL = imgURL;
        this.aboutMe = aboutMe;
    }

    public User(){

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
