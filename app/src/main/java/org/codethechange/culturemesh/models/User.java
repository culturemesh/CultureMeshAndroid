package org.codethechange.culturemesh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Represents a CultureMesh user's public profile. Methods that require non-public data (e.g.
 * email or password) take that information in as parameters and do not store it after the method
 * completes.
 */
@Entity
public class User implements Serializable {

    public static final String CM_LOGO_URL = "https://www.culturemesh.com/images/cm_logo_blank_profile_lrg.png";
    public static final String DEFAULT_BIO = "";
    public static final String DEFAULT_GENDER = "";
    public static final String IMG_URL_PREFIX = "https://www.culturemesh.com/user_images/";

    private static final String TAG = User.class.getSimpleName();

    /**
     * The user's unique identifier, which identifies them across all of CultureMesh and is constant.
     * Not editable by user.
     */
    @PrimaryKey
    public long id;

    /**
     * TODO: What does a user's role represent?
     * This value seems to be {@code 0} for all users. Editable by user.
     */
    public int role;

    /**
     * User's display name that is publicly used to identify their posts, events, etc. Editable by
     * user. Must be unique across all of CultureMesh's users.
     */
    public String username;

    /**
     * User's first name. Editable by user, and may be pseudonymous.
     */
    public String firstName;

    /**
     * User's last name. Editable by user, and may be pseudonymous.
     */
    public String lastName;

    /**
     * User's gender. Editable by user.
     */
    public String gender;

    /**
     * Bio user has written about themselves. Editable by user.
     */
    public String aboutMe;

    /**
     * URL for the user's profile picture. Editable by user.
     */
    public String imgURL;

    /**
     * Create a new object, storing the provided parameters into the related instance fields.
     * @param id Uniquely identifies user across all of CultureMesh.
     * @param firstName User's first name (may be pseudonymous)
     * @param lastName User's last name (may be pseudonymous)
     * @param username The user's "display name" that will serve as their main public identifier.
     *                 Must be unique across all of CultureMesh's users.
     * @param imgURL URL suffix (after {@link User#IMG_URL_PREFIX} to the user's profile picture
     * @param aboutMe Short bio describing the user
     * @param gender User's self-identified gender
     */
    public User(long id, String firstName, String lastName, String username,
                String imgURL, String aboutMe, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        if (imgURL == null || imgURL.isEmpty()) {
            imgURL = CM_LOGO_URL;
        }
        this.imgURL = IMG_URL_PREFIX + imgURL;
        this.aboutMe = aboutMe;
        this.gender = gender;
    }

    /**
     * Create a new object, storing the provided parameters into the related instance fields.
     * Intended to be used when creating accounts, as {@code img_url}, {@code about_me}, and
     * {@code gender} are initialized to defaults as described in the constants for {@link User}.
     * @param id Uniquely identifies user across all of CultureMesh.
     * @param firstName User's first name (may be pseudonymous)
     * @param lastName User's last name (may be pseudonymous)
     * @param username The user's "display name" that will serve as their main public identifier.
     *                 Must be unique across all of CultureMesh's users.
     */
    public User(long id, String firstName, String lastName, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.imgURL = CM_LOGO_URL;
        this.aboutMe = DEFAULT_BIO;
        this.gender = DEFAULT_GENDER;
    }

    /**
     * Create a new user from a JSON that conforms to the following format:
     * <pre>
     *     {@code
     *        {
               "id": 0,
               "username": "string",
               "first_name": "string",
               "last_name": "string",
               "role": 0,
               "gender": "string",
               "about_me": "string",
               "img_link": "string",
              }
     *     }
     * </pre>
     * Other key-value pairs are acceptable, but will be ignored. Note that {@code img_link} does not
     * include the base {@link User#IMG_URL_PREFIX}. A missing, null, or empty {@code img_link} is
     * interpreted as an unset link, which {@link User#CM_LOGO_URL} is used for.
     * @param res JSON describing the user to create
     * @throws JSONException May be thrown in the case of an improperly structured JSON
     */
    public User(JSONObject res) throws JSONException{
        if (res.has("img_link") && !res.isNull("img_link") &&
                !res.getString("img_link").isEmpty()) {
            imgURL = IMG_URL_PREFIX + res.getString("img_link");
        } else {
            imgURL = CM_LOGO_URL;
        }
        id = res.getInt("id");
        firstName = res.getString("first_name");
        lastName = res.getString("last_name");
        username = res.getString("username");
        aboutMe = res.getString("about_me");
        gender = res.getString("gender");
    }

    /**
     * Empty constructor that does no initialization. For database use only.
     */
    public User() {

    }

    /**
     * Get the URL to the user's profile photo
     * @return URL that links to the user's profile photo
     */
    public String getImgURL() {
        return imgURL;
    }

    // TODO: First and last name are not fully international-compatible
    /**
     * Get the user's first name. May be pseudonymous.
     * @return User's potentially pseudonymous first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the user's last name. May be pseudonymous.
     * @return User's potentially pseudonymous last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the user's chosen display name, which should be used as their unique public identifier.
     * @return User's display name, which must be unique across all of CultureMesh's users.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the user's self-written bio (i.e. "about me" text)
     * @return User's description of themselves (i.e. their bio)
     */
    public String getBio() {
        return aboutMe;
    }

    /**
     * Set the URL for the user's profile photo
     * @param imgURL URL to the user's new profile photo
     */
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    /**
     * Set the user's first name
     * @param firstName New name to save as the user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Set the user's last name
     * @param lastName New name to save as the user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Set the user's display name, which must be unique across CultureMesh
     * @param username New display name to use for the user. Must be unique across all of
     *                 CultureMesh's users.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the text of the user's bio
     * @param bio New bio the user has chosen for themselves
     */
    public void setBio(String bio) {
        this.aboutMe = bio;
    }

    /**
     * Create a JSON representation of the object that conforms to the following format:
     * <pre>
     *     {@code
     *       {
               "id": 0,
               "username": "string",
               "first_name": "string",
               "last_name": "string",
               "role": 0,
               "gender": "string",
               "about_me": "string",
               "img_link": "string"
             }
     *     }
     * </pre>
     * This is intended to be the format used by the {@code /user/users} PUT endpoint.
     * Note that {@code img_link} does not
     * include the base {@link User#IMG_URL_PREFIX}. A missing, null, or empty {@code img_link} is
     * interpreted as an unset link, which {@link User#CM_LOGO_URL} is used for.
     * @return JSON representation of the object
     * @throws JSONException Unclear when this would be thrown
     */
    public JSONObject getPutJson(String email) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("username", username);
        json.put("first_name", firstName);
        json.put("last_name", lastName);
        json.put("role", role);
        json.put("gender", gender);
        json.put("about_me", aboutMe);
        if (imgURL.contains(IMG_URL_PREFIX)) {
            json.put("img_link", imgURL.replace(IMG_URL_PREFIX, ""));
            Log.i("removed URL PREFIX", imgURL.replace(IMG_URL_PREFIX, ""));

        } else {
            json.put("img_link", imgURL);
        }

        return json;
    }

    /**
     * Create a JSON representation of the object that conforms to the following format:
     * <pre>
     *     {@code
     *       {
                "username": "string",
                "password": "string",
                "first_name": "string",
                "last_name": "string",
                "email": "string",
                "role": 0,
                "img_link": "string",
                "about_me": "string",
                "gender": "string"
             }
     *     }
     * </pre>
     * This is intended to be the format used by the {@code /user/users} POST endpoint.
     * Note that {@code img_link} does not
     * include the base {@link User#IMG_URL_PREFIX}. A missing, null, or empty {@code img_link} is
     * interpreted as an unset link, which {@link User#CM_LOGO_URL} is used for.
     * @return JSON representation of the object
     * @throws JSONException Unclear when this would be thrown
     */
    public JSONObject getPostJson(String email, String password) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("email", email);
        json.put("password", password);
        json.put("first_name", firstName);
        json.put("last_name", lastName);
        json.put("role", role);
        json.put("about_me", aboutMe);
        json.put("gender", gender);
        Log.v(TAG, "Created new user with JSON: " + json.toString());
        return json;
    }
}
