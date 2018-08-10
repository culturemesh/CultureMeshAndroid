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

API.Post
========

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: static class Post
   :outertype: API

Methods
-------
addUserToEvent
^^^^^^^^^^^^^^

.. java:method:: static void addUserToEvent(RequestQueue queue, long userId, long eventId, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   Add a user to an existing event. This operation requires authentication, so the user must be logged in.

   :param queue: Queue to which the asynchronous task will be added
   :param userId: ID of the user to add to the event
   :param eventId: ID of the event to add the user to
   :param listener: Listener whose onResponse method will be called when the operation completes

event
^^^^^

.. java:method:: static void event(RequestQueue queue, Event event, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   POST to the server a request, via \ ``/event/new``\ , to create a new \ :java:ref:`Event`\ . Success or failure status will be passed via a \ :java:ref:`NetworkResponse`\  to the listener.

   :param queue: Queue to which the asynchronous task will be added
   :param event: \ :java:ref:`Event`\  to create.
   :param listener: Listener whose onResponse method will be called when task completes

joinNetwork
^^^^^^^^^^^

.. java:method:: static void joinNetwork(RequestQueue queue, long networkId, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   Add the current user to an existing network. This operation requires authentication, so the user must be logged in.

   :param queue: Queue to which the asynchronous task will be added
   :param networkId: ID of the network to add the user to
   :param listener: Listener whose onResponse method will be called when the operation completes

leaveNetwork
^^^^^^^^^^^^

.. java:method:: static void leaveNetwork(RequestQueue queue, long networkId, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   Remove the current user from a network. This operation requires authentication, so the user must be logged in. If the user is not in the specified network, no error is thrown.

   :param queue: Asynchronous task to which the request will be added
   :param networkId: ID of the network to remove the user from
   :param settings: Reference to the \ :java:ref:`SharedPreferences`\  storing the user's login token
   :param listener: Listener whose \ ``onResponse``\  method will be called when the operation completes

post
^^^^

.. java:method:: static void post(RequestQueue queue, org.codethechange.culturemesh.models.Post post, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   POST to the server a request, via \ ``/post/new``\ , to create a new \ :java:ref:`org.codethechange.culturemesh.models.Post`\ . Success or failure status will be passed via a \ :java:ref:`NetworkResponse`\  to the listener.

   :param queue: Queue to which the asynchronous task will be added
   :param post: \ :java:ref:`org.codethechange.culturemesh.models.Post`\  to create.
   :param listener: Listener whose onResponse method will be called when task completes

reply
^^^^^

.. java:method:: static void reply(RequestQueue queue, PostReply comment, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   POST to the server a request, via \ ``/post/{postId}/reply``\ , to create a new \ :java:ref:`PostReply`\ . Success or failure status will be passed via a \ :java:ref:`NetworkResponse`\  to the listener.

   :param queue: Queue to which the asynchronous task will be added
   :param comment: \ :java:ref:`PostReply`\  to create.
   :param listener: Listener whose onResponse method will be called when task completes

user
^^^^

.. java:method:: static void user(RequestQueue queue, User user, String email, String password, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   POST to the server a request, via \ ``/user/users``\ , to create a new user. Note that Success or failure status will be passed via a \ :java:ref:`NetworkResponse`\  to the listener.

   :param queue: Queue to which the asynchronous task will be added
   :param user: User to create. \ **Must have password set.**\
   :param email: User's email address
   :param listener: Listener whose onResponse method will be called when task completes

