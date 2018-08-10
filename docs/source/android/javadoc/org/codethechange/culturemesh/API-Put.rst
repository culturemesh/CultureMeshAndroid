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

API.Put
=======

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: static class Put
   :outertype: API

Methods
-------
event
^^^^^

.. java:method:: static void event(RequestQueue queue, Event event, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Put

   PUT to the server a request, via \ ``/event/new``\ , to update an \ :java:ref:`Event`\ . Success or failure status will be passed via a \ :java:ref:`NetworkResponse`\  to the listener.

   :param queue: Queue to which the asynchronous task will be added
   :param event: Updated version of the \ :java:ref:`Event`\  to change
   :param listener: Listener whose onResponse method will be called when task completes

post
^^^^

.. java:method:: static void post(RequestQueue queue, org.codethechange.culturemesh.models.Post post, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Put

   PUT to the server, via \ ``/user/users``\ , a request to make changes a \ :java:ref:`org.codethechange.culturemesh.models.Post`\ . Success or failure status will be passed via a \ :java:ref:`NetworkResponse`\  to the listener.

   :param queue: Queue to which the asynchronous task will be added
   :param post: Updated version of the post to change
   :param listener: Listener whose onResponse method will be called when task completes

reply
^^^^^

.. java:method:: static void reply(RequestQueue queue, PostReply comment, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Put

   PUT to the server a request, via \ ``/post/{postId}/reply``\ , to update a \ :java:ref:`PostReply`\ . Success or failure status will be passed via a \ :java:ref:`NetworkResponse`\  to the listener.

   :param queue: Queue to which the asynchronous task will be added
   :param comment: Updated version of the \ :java:ref:`PostReply`\  to make changes to
   :param listener: Listener whose onResponse method will be called when task completes

user
^^^^

.. java:method:: static void user(RequestQueue queue, User user, String email, SharedPreferences settings, Response.Listener<NetworkResponse<String>> listener)
   :outertype: API.Put

   PUT to the server, via \ ``/user/users``\ , a request to make changes a \ :java:ref:`User`\ . Success or failure status will be passed via a \ :java:ref:`NetworkResponse`\  to the listener.

   :param queue: Queue to which the asynchronous task will be added
   :param user: Updated version of the user to change
   :param email: User's email address
   :param listener: Listener whose onResponse method will be called when task completes

