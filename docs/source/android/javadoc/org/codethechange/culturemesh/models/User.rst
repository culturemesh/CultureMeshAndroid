.. java:import:: android.arch.persistence.room Entity

.. java:import:: android.arch.persistence.room PrimaryKey

.. java:import:: android.util Log

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: java.io Serializable

User
====

.. java:package:: org.codethechange.culturemesh.models
   :noindex:

.. java:type:: @Entity public class User implements Serializable

   Represents a CultureMesh user's public profile. Methods that require non-public data (e.g. email or password) take that information in as parameters and do not store it after the method completes.

Fields
------
CM_LOGO_URL
^^^^^^^^^^^

.. java:field:: public static final String CM_LOGO_URL
   :outertype: User

DEFAULT_BIO
^^^^^^^^^^^

.. java:field:: public static final String DEFAULT_BIO
   :outertype: User

DEFAULT_GENDER
^^^^^^^^^^^^^^

.. java:field:: public static final String DEFAULT_GENDER
   :outertype: User

IMG_URL_PREFIX
^^^^^^^^^^^^^^

.. java:field:: public static final String IMG_URL_PREFIX
   :outertype: User

aboutMe
^^^^^^^

.. java:field:: public String aboutMe
   :outertype: User

   Bio user has written about themselves. Editable by user.

firstName
^^^^^^^^^

.. java:field:: public String firstName
   :outertype: User

   User's first name. Editable by user, and may be pseudonymous.

gender
^^^^^^

.. java:field:: public String gender
   :outertype: User

   User's gender. Editable by user.

id
^^

.. java:field:: @PrimaryKey public long id
   :outertype: User

   The user's unique identifier, which identifies them across all of CultureMesh and is constant. Not editable by user.

imgURL
^^^^^^

.. java:field:: public String imgURL
   :outertype: User

   URL for the user's profile picture. Editable by user.

lastName
^^^^^^^^

.. java:field:: public String lastName
   :outertype: User

   User's last name. Editable by user, and may be pseudonymous.

role
^^^^

.. java:field:: public int role
   :outertype: User

   TODO: What does a user's role represent? This value seems to be \ ``0``\  for all users. Editable by user.

username
^^^^^^^^

.. java:field:: public String username
   :outertype: User

   User's display name that is publicly used to identify their posts, events, etc. Editable by user. Must be unique across all of CultureMesh's users.

Constructors
------------
User
^^^^

.. java:constructor:: public User(long id, String firstName, String lastName, String username, String imgURL, String aboutMe, String gender)
   :outertype: User

   Create a new object, storing the provided parameters into the related instance fields.

   :param id: Uniquely identifies user across all of CultureMesh.
   :param firstName: User's first name (may be pseudonymous)
   :param lastName: User's last name (may be pseudonymous)
   :param username: The user's "display name" that will serve as their main public identifier. Must be unique across all of CultureMesh's users.
   :param imgURL: URL suffix (after \ :java:ref:`User.IMG_URL_PREFIX`\  to the user's profile picture
   :param aboutMe: Short bio describing the user
   :param gender: User's self-identified gender

User
^^^^

.. java:constructor:: public User(long id, String firstName, String lastName, String username)
   :outertype: User

   Create a new object, storing the provided parameters into the related instance fields. Intended to be used when creating accounts, as \ ``img_url``\ , \ ``about_me``\ , and \ ``gender``\  are initialized to defaults as described in the constants for \ :java:ref:`User`\ .

   :param id: Uniquely identifies user across all of CultureMesh.
   :param firstName: User's first name (may be pseudonymous)
   :param lastName: User's last name (may be pseudonymous)
   :param username: The user's "display name" that will serve as their main public identifier. Must be unique across all of CultureMesh's users.

User
^^^^

.. java:constructor:: public User(JSONObject res) throws JSONException
   :outertype: User

   Create a new user from a JSON that conforms to the following format:

   .. parsed-literal::

      {
                "id": 0,
                "username": "string",
                "first_name": "string",
                "last_name": "string",
                "role": 0,
                "gender": "string",
                "about_me": "string",
                "img_link": "string",
               }

   Other key-value pairs are acceptable, but will be ignored. Note that \ ``img_link``\  does not include the base \ :java:ref:`User.IMG_URL_PREFIX`\ . A missing, null, or empty \ ``img_link``\  is interpreted as an unset link, which \ :java:ref:`User.CM_LOGO_URL`\  is used for.

   :param res: JSON describing the user to create
   :throws JSONException: May be thrown in the case of an improperly structured JSON

User
^^^^

.. java:constructor:: public User()
   :outertype: User

   Empty constructor that does no initialization. For database use only.

Methods
-------
getBio
^^^^^^

.. java:method:: public String getBio()
   :outertype: User

   Get the user's self-written bio (i.e. "about me" text)

   :return: User's description of themselves (i.e. their bio)

getFirstName
^^^^^^^^^^^^

.. java:method:: public String getFirstName()
   :outertype: User

   Get the user's first name. May be pseudonymous.

   :return: User's potentially pseudonymous first name.

getImgURL
^^^^^^^^^

.. java:method:: public String getImgURL()
   :outertype: User

   Get the URL to the user's profile photo

   :return: URL that links to the user's profile photo

getLastName
^^^^^^^^^^^

.. java:method:: public String getLastName()
   :outertype: User

   Get the user's last name. May be pseudonymous.

   :return: User's potentially pseudonymous last name.

getPostJson
^^^^^^^^^^^

.. java:method:: public JSONObject getPostJson(String email, String password) throws JSONException
   :outertype: User

   Create a JSON representation of the object that conforms to the following format:

   .. parsed-literal::

      {
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

   This is intended to be the format used by the \ ``/user/users``\  POST endpoint. Note that \ ``img_link``\  does not include the base \ :java:ref:`User.IMG_URL_PREFIX`\ . A missing, null, or empty \ ``img_link``\  is interpreted as an unset link, which \ :java:ref:`User.CM_LOGO_URL`\  is used for.

   :throws JSONException: Unclear when this would be thrown
   :return: JSON representation of the object

getPutJson
^^^^^^^^^^

.. java:method:: public JSONObject getPutJson(String email) throws JSONException
   :outertype: User

   Create a JSON representation of the object that conforms to the following format:

   .. parsed-literal::

      {
                "id": 0,
                "username": "string",
                "first_name": "string",
                "last_name": "string",
                "role": 0,
                "gender": "string",
                "about_me": "string",
                "img_link": "string"
              }

   This is intended to be the format used by the \ ``/user/users``\  PUT endpoint. Note that \ ``img_link``\  does not include the base \ :java:ref:`User.IMG_URL_PREFIX`\ . A missing, null, or empty \ ``img_link``\  is interpreted as an unset link, which \ :java:ref:`User.CM_LOGO_URL`\  is used for.

   :throws JSONException: Unclear when this would be thrown
   :return: JSON representation of the object

getUsername
^^^^^^^^^^^

.. java:method:: public String getUsername()
   :outertype: User

   Get the user's chosen display name, which should be used as their unique public identifier.

   :return: User's display name, which must be unique across all of CultureMesh's users.

setBio
^^^^^^

.. java:method:: public void setBio(String bio)
   :outertype: User

   Set the text of the user's bio

   :param bio: New bio the user has chosen for themselves

setFirstName
^^^^^^^^^^^^

.. java:method:: public void setFirstName(String firstName)
   :outertype: User

   Set the user's first name

   :param firstName: New name to save as the user's first name

setImgURL
^^^^^^^^^

.. java:method:: public void setImgURL(String imgURL)
   :outertype: User

   Set the URL for the user's profile photo

   :param imgURL: URL to the user's new profile photo

setLastName
^^^^^^^^^^^

.. java:method:: public void setLastName(String lastName)
   :outertype: User

   Set the user's last name

   :param lastName: New name to save as the user's last name

setUsername
^^^^^^^^^^^

.. java:method:: public void setUsername(String username)
   :outertype: User

   Set the user's display name, which must be unique across CultureMesh

   :param username: New display name to use for the user. Must be unique across all of CultureMesh's users.

