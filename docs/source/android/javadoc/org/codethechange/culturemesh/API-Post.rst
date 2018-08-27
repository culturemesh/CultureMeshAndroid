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

.. java:import:: com.culturemesh.models City

.. java:import:: com.culturemesh.models Country

.. java:import:: com.culturemesh.models DatabaseNetwork

.. java:import:: com.culturemesh.models Event

.. java:import:: com.culturemesh.models FromLocation

.. java:import:: com.culturemesh.models Language

.. java:import:: com.culturemesh.models Location

.. java:import:: com.culturemesh.models NearLocation

.. java:import:: com.culturemesh.models Network

.. java:import:: com.culturemesh.models Place

.. java:import:: com.culturemesh.models PostReply

.. java:import:: com.culturemesh.models Postable

.. java:import:: com.culturemesh.models Putable

.. java:import:: com.culturemesh.models Region

.. java:import:: com.culturemesh.models User

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

.. java:package:: com.culturemesh
   :noindex:

.. java:type:: static class Post
   :outertype: API

Methods
-------
event
^^^^^

.. java:method:: static void event(RequestQueue queue, Event event, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   POST to the server a request, via \ ``/event/new``\ , to create a new \ :java:ref:`Event`\ . Success or failure status will be passed via a \ :java:ref:`NetworkResponse`\  to the listener.

   :param queue: Queue to which the asynchronous task will be added
   :param event: \ :java:ref:`Event`\  to create.
   :param listener: Listener whose onResponse method will be called when task completes

joinEvent
^^^^^^^^^

.. java:method:: static void joinEvent(RequestQueue queue, long eventId, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   Add a user to an existing event. This operation requires authentication, so the user must be logged in.

   :param queue: Queue to which the asynchronous task will be added
   :param settings: \ :java:ref:`SharedPreferences`\  instance so we can get the token.
   :param eventId: ID of the event to add the user to
   :param listener: Listener whose onResponse method will be called when the operation completes

joinNetwork
^^^^^^^^^^^

.. java:method:: static void joinNetwork(RequestQueue queue, long networkId, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   Add the current user to an existing network. This operation requires authentication, so the user must be logged in.

   :param queue: Queue to which the asynchronous task will be added
   :param networkId: ID of the network to add the user to
   :param listener: Listener whose onResponse method will be called when the operation completes

leaveEvent
^^^^^^^^^^

.. java:method:: static void leaveEvent(RequestQueue queue, long eventId, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   Removes user from event subscription listing.

   :param queue: Queue to which network request will be added.
   :param eventId: id of event to remove user from.
   :param settings: \ :java:ref:`SharedPreferences`\  instance that stores token.
   :param listener: Listener whose onResponse will be called when the operation completes.

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

.. java:method:: static void post(RequestQueue queue, com.culturemesh.models.Post post, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Post

   POST to the server a request, via \ ``/post/new``\ , to create a new \ :java:ref:`com.culturemesh.models.Post`\ . Success or failure status will be passed via a \ :java:ref:`NetworkResponse`\  to the listener.

   :param queue: Queue to which the asynchronous task will be added
   :param post: \ :java:ref:`com.culturemesh.models.Post`\  to create.
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

