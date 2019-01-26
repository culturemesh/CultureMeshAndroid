.. java:import:: android.content Context

.. java:import:: android.content SharedPreferences

.. java:import:: android.util Base64

.. java:import:: android.util Log

.. java:import:: com.culturemesh.android.models City

.. java:import:: com.culturemesh.android.models Country

.. java:import:: com.culturemesh.android.models DatabaseNetwork

.. java:import:: com.culturemesh.android.models Event

.. java:import:: com.culturemesh.android.models FromLocation

.. java:import:: com.culturemesh.android.models Language

.. java:import:: com.culturemesh.android.models Location

.. java:import:: com.culturemesh.android.models NearLocation

.. java:import:: com.culturemesh.android.models Network

.. java:import:: com.culturemesh.android.models Place

.. java:import:: com.culturemesh.android.models Post

.. java:import:: com.culturemesh.android.models PostReply

.. java:import:: com.culturemesh.android.models Postable

.. java:import:: com.culturemesh.android.models Putable

.. java:import:: com.culturemesh.android.models Region

.. java:import:: com.culturemesh.android.models User

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

API.Get
=======

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: static class Get
   :outertype: API

   The protocol for GET requests is as follows... 1. Check if cache has relevant data. If so, return it. 2. Send network request to update data.

Methods
-------
autocompleteLanguage
^^^^^^^^^^^^^^^^^^^^

.. java:method:: static void autocompleteLanguage(RequestQueue queue, String text, Response.Listener<NetworkResponse<List<Language>>> listener)
   :outertype: API.Get

   Get potential \ :java:ref:`Language`\ s that match a user's query text

   :param queue: Queue to which the asynchronous task will be added
   :param text: User's query text to get autocomplete results for
   :param listener: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with the \ :java:ref:`NetworkResponse`\  created by the query.

autocompletePlace
^^^^^^^^^^^^^^^^^

.. java:method:: static void autocompletePlace(RequestQueue queue, String text, Response.Listener<NetworkResponse<List<Location>>> listener)
   :outertype: API.Get

   Get potential \ :java:ref:`Location`\ s that match a user's query text

   :param queue: Queue to which the asynchronous task will be added
   :param text: User's query text to get autocomplete results for
   :param listener: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with the \ :java:ref:`NetworkResponse`\  created by the query.

instantiatePostReplyUser
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: static void instantiatePostReplyUser(RequestQueue queue, PostReply comment, Response.Listener<PostReply> listener)
   :outertype: API.Get

   The API will return Post JSON Objects with id's for the user. Often, we will want to get the user information associated with a post, such as the name and profile picture. This method allows us to instantiate this user information for each post.

   :param queue: The Volley RequestQueue object that handles all the request queueing.
   :param comment: An already instantiated PostReply object that has a null author field but a defined userId field.
   :param listener: the UI listener that will be called when we complete the task at hand.

instantiatePostUser
^^^^^^^^^^^^^^^^^^^

.. java:method:: static void instantiatePostUser(RequestQueue queue, com.culturemesh.android.models.Post post, Response.Listener<com.culturemesh.android.models.Post> listener)
   :outertype: API.Get

   The API will return Post JSON Objects with id's for the user. Often, we will want to get the user information associated with a post, such as the name and profile picture. This method allows us to instantiate this user information for each post.

   :param queue: The Volley RequestQueue object that handles all the request queueing.
   :param post: An already instantiated Post object that has a null author field but a defined userId field.
   :param listener: the UI listener that will be called when we complete the task at hand.

language
^^^^^^^^

.. java:method:: static void language(RequestQueue queue, long id, Response.Listener<NetworkResponse<Language>> listener)
   :outertype: API.Get

   Get the \ :java:ref:`Language`\  that has the provided ID

   :param queue: Queue to which the asynchronous task will be added
   :param id: ID of the \ :java:ref:`Language`\  to find. Must be unique, and the same ID must be used throughout.
   :param listener: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with the \ :java:ref:`NetworkResponse`\  created by the query.

loginToken
^^^^^^^^^^

.. java:method:: static void loginToken(RequestQueue queue, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Get

   Generically get a login token. If the token is fresh (less than \ :java:ref:`API.TOKEN_REFRESH`\  seconds have passed since the last token was retrieved the current token is simply supplied. Otherwise, an attempt is made to login with the token to get a new one. If this fails, the token has expired, and the user is directed to sign in again by the error dialog. If it succeeds, the new token is stored in place of the old one.

   :param queue: Queue to which the asynchronous task will be added
   :param listener: Listener whose onResponse method will be called when task completes

   **See also:** :java:ref:`NetworkResponse.genErrorDialog(Context,int,boolean,NetworkResponse.DialogTapListener)`, :java:ref:`API.LOGIN_TOKEN`, :java:ref:`API.TOKEN_RETRIEVED`

loginWithCred
^^^^^^^^^^^^^

.. java:method:: static void loginWithCred(RequestQueue queue, String email, String password, SharedPreferences settings, Response.Listener<NetworkResponse<LoginResponse>> listener)
   :outertype: API.Get

   Use a user's login credentials to login to the server. A user's credentials consist of the email address associated with their account and their password for the CultureMesh website. If the credentials are accepted by the server, the resulting LoginResponse will be stored in the \ :java:ref:`NetworkResponse`\ , which will not be in a failed state, and passed to the listener. If the credentials are rejected, the \ :java:ref:`NetworkResponse`\  will be in a failed state with an error message communicating the occurrence of an authentication failure and instructing the user to sign in again. After dismissing the error dialog, the \ :java:ref:`LoginActivity`\  will be launched.

   :param queue: Queue to which the asynchronous task will be added
   :param email: Email address that will serve as the username in the attempted login
   :param password: Password to use in the login attempt
   :param listener: Will be called with the \ :java:ref:`NetworkResponse`\  when the operation completes

   **See also:** :java:ref:`NetworkResponse.genErrorDialog(Context,int,boolean,NetworkResponse.DialogTapListener)`

loginWithToken
^^^^^^^^^^^^^^

.. java:method:: static void loginWithToken(RequestQueue queue, String token, SharedPreferences settings, Response.Listener<NetworkResponse<LoginResponse>> listener)
   :outertype: API.Get

   Same as \ :java:ref:`API.Get.loginWithCred(RequestQueue,String,String,SharedPreferences,Response.Listener)`\ , but a login token is used in place of the user's credentials.

   :param queue: Queue to which the asynchronous task will be added
   :param token: Login token to use to get another token
   :param listener: Will be called with the \ :java:ref:`NetworkResponse`\  when the operation completes

netFromFromAndNear
^^^^^^^^^^^^^^^^^^

.. java:method:: static void netFromFromAndNear(RequestQueue queue, FromLocation from, NearLocation near, Response.Listener<NetworkResponse<Network>> listener)
   :outertype: API.Get

   Get the \ :java:ref:`Network`\  that has the provided \ :java:ref:`FromLocation`\  and \ :java:ref:`NearLocation`\

   :param queue: Queue to which the asynchronous task will be added
   :param from: \ :java:ref:`FromLocation`\  of the \ :java:ref:`Network`\  to find
   :param near: \ :java:ref:`NearLocation`\  of the \ :java:ref:`Network`\  to find
   :param listener: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with the \ :java:ref:`NetworkResponse`\  created by the query.

netFromLangAndNear
^^^^^^^^^^^^^^^^^^

.. java:method:: static void netFromLangAndNear(RequestQueue queue, Language lang, NearLocation near, Response.Listener<NetworkResponse<Network>> listener)
   :outertype: API.Get

   Get the \ :java:ref:`Network`\  that has the provided \ :java:ref:`Language`\  and \ :java:ref:`NearLocation`\

   :param queue: Queue to which the asynchronous task will be added
   :param lang: \ :java:ref:`Language`\  of the \ :java:ref:`Network`\  to find
   :param near: \ :java:ref:`NearLocation`\  of the \ :java:ref:`Network`\  to find
   :param listener: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with the \ :java:ref:`NetworkResponse`\  created by the query.

network
^^^^^^^

.. java:method:: static void network(RequestQueue queue, long id, Response.Listener<NetworkResponse<Network>> callback)
   :outertype: API.Get

   Get the \ :java:ref:`Network`\  corresponding to the provided ID

   :param queue: Queue to which the asynchronous task to get the \ :java:ref:`Network`\  will be added
   :param id: ID of the \ :java:ref:`Network`\  to get
   :param callback: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with the \ :java:ref:`NetworkResponse`\  created by the query.

networkEvents
^^^^^^^^^^^^^

.. java:method:: static void networkEvents(RequestQueue queue, long id, String maxId, Response.Listener<NetworkResponse<List<Event>>> listener)
   :outertype: API.Get

   Get the \ :java:ref:`Event`\ s corresponding to a \ :java:ref:`Network`\

   :param queue: Queue to which the asynchronous task will be added
   :param id: ID of the \ :java:ref:`Network`\  whose \ :java:ref:`Event`\ s will be fetched
   :param listener: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with the \ :java:ref:`NetworkResponse`\  created by the query.

networkPostCount
^^^^^^^^^^^^^^^^

.. java:method:: static void networkPostCount(RequestQueue queue, long id, Response.Listener<NetworkResponse<Long>> listener)
   :outertype: API.Get

   Get the number of \ :java:ref:`com.culturemesh.android.models.Post`\ s that are currently on a \ :java:ref:`Network`\

   :param queue: Queue to which the asynchronous task will be added
   :param id: ID of the \ :java:ref:`Network`\  whose \ :java:ref:`com.culturemesh.android.models.Post`\  count will be retrieved
   :param listener: Listener whose \ :java:ref:`Response.Listener.onResponse(Object)`\  is called with a \ :java:ref:`NetworkResponse`\  that stores the result of the network request

networkPosts
^^^^^^^^^^^^

.. java:method:: static void networkPosts(RequestQueue queue, long id, String maxId, Response.Listener<NetworkResponse<List<com.culturemesh.android.models.Post>>> listener)
   :outertype: API.Get

   Get the \ :java:ref:`com.culturemesh.android.models.Post`\ s of a \ :java:ref:`Network`\

   :param queue: Queue to which the asynchronous task will be added
   :param id: ID of the \ :java:ref:`Network`\  whose \ :java:ref:`com.culturemesh.android.models.Post`\ s will be returned
   :param listener: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with the \ :java:ref:`NetworkResponse`\  created by the query.

networkUserCount
^^^^^^^^^^^^^^^^

.. java:method:: static void networkUserCount(RequestQueue queue, long id, Response.Listener<NetworkResponse<Long>> listener)
   :outertype: API.Get

   Get the number of \ :java:ref:`User`\ s who are currently members of a \ :java:ref:`Network`\

   :param queue: Queue to which the asynchronous task will be added
   :param id: ID of the \ :java:ref:`Network`\  whose \ :java:ref:`User`\  count will be retrieved
   :param listener: Listener whose \ :java:ref:`Response.Listener.onResponse(Object)`\  is called with a \ :java:ref:`NetworkResponse`\  that stores the result of the network request

networkUsers
^^^^^^^^^^^^

.. java:method:: static void networkUsers(RequestQueue queue, long id, Response.Listener<NetworkResponse<ArrayList<User>>> listener)
   :outertype: API.Get

   Get all the \ :java:ref:`User`\ s who are members of a \ :java:ref:`Network`\

   :param queue: Queue to which the asynchronous task will be added
   :param id: ID of the \ :java:ref:`Network`\  whose users will be fetched
   :param listener: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with the \ :java:ref:`NetworkResponse`\  created by the query.

post
^^^^

.. java:method:: static void post(RequestQueue queue, long id, Response.Listener<NetworkResponse<com.culturemesh.android.models.Post>> callback)
   :outertype: API.Get

   Get a \ :java:ref:`com.culturemesh.android.models.Post`\  from it's ID

   :param queue: Queue to which the asynchronous task will be added
   :param id: ID of the \ :java:ref:`com.culturemesh.android.models.Post`\  to retrieve
   :param callback: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with the \ :java:ref:`NetworkResponse`\  created by the query.

postReplies
^^^^^^^^^^^

.. java:method:: static void postReplies(RequestQueue queue, long id, Response.Listener<NetworkResponse<ArrayList<PostReply>>> listener)
   :outertype: API.Get

   Fetch the comments of a post.

   :param queue: The \ :java:ref:`RequestQueue`\  to house the network requests.
   :param id: the id of the post that we want comments for.
   :param listener: the listener that we will call when the request is finished.

topTen
^^^^^^

.. java:method:: static void topTen(RequestQueue queue, Response.Listener<NetworkResponse<ArrayList<Network>>> listener)
   :outertype: API.Get

   Fetches the ten \ :java:ref:`Network`\ s with the most subscribers.

   :param queue: Queue to which the asynchronous task will be added
   :param listener: Will be called with the \ :java:ref:`NetworkResponse`\  when the operation completes

user
^^^^

.. java:method:: static void user(RequestQueue queue, long id, Response.Listener<NetworkResponse<User>> listener)
   :outertype: API.Get

   Get a \ :java:ref:`User`\  object from it's ID

   :param id: ID of user to find
   :return: If such a user was found, it will be the payload. Otherwise, the request will be marked as failed.

userEvents
^^^^^^^^^^

.. java:method:: static void userEvents(RequestQueue queue, long id, String role, Response.Listener<NetworkResponse<ArrayList<Event>>> listener)
   :outertype: API.Get

   Get the \ :java:ref:`Event`\ s a \ :java:ref:`User`\  is subscribed to.

   :param queue: Queue to which the asynchronous task is added.
   :param id: ID of the \ :java:ref:`User`\  whose events are being searched for
   :param role: Either \ ``hosting``\  or \ ``attending``\
   :param listener: Listener whose \ ``onResponse``\  method is called with the results of the task

userEventsForNetwork
^^^^^^^^^^^^^^^^^^^^

.. java:method:: static void userEventsForNetwork(RequestQueue queue, SharedPreferences settings, long networkId, Response.Listener<NetworkResponse<ArrayList<Event>>> listener)
   :outertype: API.Get

   Get the \ :java:ref:`Event`\ s a \ :java:ref:`User`\  is subscribed to for a given \ :java:ref:`Network`\ .

   :param queue: Queue to which the asynchronous task is added.
   :param settings: SharedPreferences instance storing the token.
   :param networkId: the id of the \ :java:ref:`Network`\  of interest.
   :param listener: The response listener to be called when the request completes.

userID
^^^^^^

.. java:method:: static void userID(RequestQueue queue, String email, Response.Listener<NetworkResponse<Long>> listener)
   :outertype: API.Get

   Get the ID of a \ :java:ref:`User`\  from an email address. Errors are communicated via a failed \ :java:ref:`NetworkResponse`\ .

   :param queue: Queue to which the asynchronous task will be added
   :param email: Email of user whose ID to look up
   :param listener: Listener whose onResponse method is called when the task has completed

userNetworks
^^^^^^^^^^^^

.. java:method:: static void userNetworks(RequestQueue queue, long id, Response.Listener<NetworkResponse<ArrayList<Network>>> listener)
   :outertype: API.Get

   Get the networks a user belongs to

   :param queue: RequestQueue to which the asynchronous job will be added
   :param id: ID of the user whose networks will be fetched
   :param listener: Listener whose \ :java:ref:`com.android.volley.Response.Listener.onResponse(Object)`\  is called with a \ :java:ref:`NetworkResponse`\  of an \ :java:ref:`ArrayList`\  of \ :java:ref:`Network`\ s

userPosts
^^^^^^^^^

.. java:method:: static void userPosts(RequestQueue queue, long id, Response.Listener<NetworkResponse<ArrayList<com.culturemesh.android.models.Post>>> listener)
   :outertype: API.Get

   Get the \ :java:ref:`com.culturemesh.android.models.Post`\ s a \ :java:ref:`User`\  has made.

   :param queue: The \ :java:ref:`RequestQueue`\  that will house the network requests.
   :param id: The id of the \ :java:ref:`User`\ .
   :param listener: The listener that the UI will call when the request is finished.

