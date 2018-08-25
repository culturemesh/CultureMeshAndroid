.. java:import:: android.content Context

.. java:import:: android.content SharedPreferences

.. java:import:: android.util Base64

.. java:import:: android.util Log

.. java:import:: com.android.volley AuthFailureError

.. java:import:: com.android.volley NetworkError

.. java:import:: com.android.volley ParseError

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley ServerError

.. java:import:: com.android.volley TimeoutError

.. java:import:: com.android.volley VolleyError

.. java:import:: com.android.volley.toolbox JsonArrayRequest

.. java:import:: com.android.volley.toolbox JsonObjectRequest

.. java:import:: com.android.volley.toolbox StringRequest

.. java:import:: org.codethechange.culturemesh.models City

.. java:import:: org.codethechange.culturemesh.models Country

.. java:import:: org.codethechange.culturemesh.models DatabaseNetwork

.. java:import:: org.codethechange.culturemesh.models Event

.. java:import:: org.codethechange.culturemesh.models FromLocation

.. java:import:: org.codethechange.culturemesh.models Language

.. java:import:: org.codethechange.culturemesh.models Location

.. java:import:: org.codethechange.culturemesh.models NearLocation

.. java:import:: org.codethechange.culturemesh.models Network

.. java:import:: org.codethechange.culturemesh.models Place

.. java:import:: org.codethechange.culturemesh.models PostReply

.. java:import:: org.codethechange.culturemesh.models Postable

.. java:import:: org.codethechange.culturemesh.models Putable

.. java:import:: org.codethechange.culturemesh.models Region

.. java:import:: org.codethechange.culturemesh.models User

.. java:import:: org.json JSONArray

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: java.io UnsupportedEncodingException

.. java:import:: java.nio.charset StandardCharsets

.. java:import:: java.util ArrayList

.. java:import:: java.util Date

.. java:import:: java.util HashMap

.. java:import:: java.util List

.. java:import:: java.util Map

.. java:import:: java.util.concurrent.atomic AtomicInteger

API
===

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type::  class API

   This API serves as the interface between the rest of the app and the CultureMesh servers. When another part of the app needs to request information, it calls API methods to obtain it. Similarly, API methods should be used to store, send, and update information. The API then handles requesting it from the CultureMesh servers.

Fields
------
API_URL_BASE
^^^^^^^^^^^^

.. java:field:: static final String API_URL_BASE
   :outertype: API

   Base of the URL all API endpoints use. For example, the \ ``/token``\  endpoint has the URL \ ``API_URL_BASE + "/token"``\ .

CURRENT_USER
^^^^^^^^^^^^

.. java:field:: static final String CURRENT_USER
   :outertype: API

   Identifier for the currently-signed-in user's ID. If no user is signed-in, this key should be removed from the preferences Example: \ ``settings.getLong(API.CURRENT_USER, -1)``\ .

FEED_ITEM_COUNT_SIZE
^^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String FEED_ITEM_COUNT_SIZE
   :outertype: API

   The number of items (e.g. \ :java:ref:`org.codethechange.culturemesh.models.Post`\ s or \ :java:ref:`Event`\ s to fetch with each paginated request

HOSTING
^^^^^^^

.. java:field:: static final String HOSTING
   :outertype: API

LOGIN_TOKEN
^^^^^^^^^^^

.. java:field:: static final String LOGIN_TOKEN
   :outertype: API

   Settings identifier for the currently cached login token for the user. May be missing or expired. Expiration is tracked using \ :java:ref:`API.TOKEN_REFRESH`\ .

NO_MAX_PAGINATION
^^^^^^^^^^^^^^^^^

.. java:field:: static final String NO_MAX_PAGINATION
   :outertype: API

SELECTED_NETWORK
^^^^^^^^^^^^^^^^

.. java:field:: static final String SELECTED_NETWORK
   :outertype: API

   Identifier for the user's currently selected \ :java:ref:`Network`\ . This is used to save the network the user was last viewing so that network can be re-opened when the user navigates back. Example: \ ``settings.getLong(API.SELECTED_NETWORK, -1)``\ .

SELECTED_USER
^^^^^^^^^^^^^

.. java:field:: static final String SELECTED_USER
   :outertype: API

   The SharedPreferences key for communicating to ViewProfileActivity which user we are viewing.

SETTINGS_IDENTIFIER
^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String SETTINGS_IDENTIFIER
   :outertype: API

   Identifier for the app's shared preferences. Example: \ ``SharedPreferences settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE)``\

TOKEN_REFRESH
^^^^^^^^^^^^^

.. java:field:: static final int TOKEN_REFRESH
   :outertype: API

   Number of milliseconds to use a login token before refreshing it. Note that this is not how long the token is valid, just how often to refresh it. Refresh time must be shorter than the validity time.

   **See also:** :java:ref:`API.LOGIN_TOKEN`

TOKEN_RETRIEVED
^^^^^^^^^^^^^^^

.. java:field:: static final String TOKEN_RETRIEVED
   :outertype: API

   Settings identifier for when the current login token was retrieved. Stored as the number of milliseconds since the epoch.

   **See also:** :java:ref:`API.LOGIN_TOKEN`

USER_EMAIL
^^^^^^^^^^

.. java:field:: static final String USER_EMAIL
   :outertype: API

   Identifier for the currently-signed-in user's email. If no user is signed-in, this key should be removed from the preferences Example: \ ``settings.getLong(API.USER_EMAIL, -1)``\ .

Methods
-------
genBasicAuth
^^^^^^^^^^^^

.. java:method:: static String genBasicAuth(String email, String password)
   :outertype: API

   Generate from a username/email and password the string to put in the header of a request as the value of the \ ``Authorization``\  token in order to perform Basic Authentication. For example: \ ``headers.put("Authorization", genBasicAuth(email, password))``\ . A login token can be used if it is passed as the \ ``email``\ , in which case the \ ``password``\  is ignored by the server.

   :param email: Email or username of account to login as; can also be a login token
   :param password: Password to login with
   :return: Value that should be passed in the header as the value of \ ``Authorization``\

genBasicAuth
^^^^^^^^^^^^

.. java:method:: static String genBasicAuth(String token)
   :outertype: API

   Generate from a login token the string to put in the header of a request as the value of the \ ``Authorization``\  token in order to perform Basic Authentication. For example: \ ``headers.put("Authorization", genBasicAuth(token))``\ .

   :param token: Login token to authenticate to server
   :return: Value that should be passed in the header as the value of \ ``Authorization``\

getCredentials
^^^^^^^^^^^^^^

.. java:method:: static String getCredentials()
   :outertype: API

   Use this method to append our credentials to our server requests. For now, we are using a static API key. In the future, we are going to want to pass session tokens.

   :return: credentials string to be appended to request url as a param.

