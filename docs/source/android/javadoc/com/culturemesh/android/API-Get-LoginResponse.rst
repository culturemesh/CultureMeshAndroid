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

API.Get.LoginResponse
=====================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public static class LoginResponse
   :outertype: API.Get

   Bundle object to store responses from getting tokens, which yield \ :java:ref:`User`\ s, tokens, and emails.

Fields
------
email
^^^^^

.. java:field:: public String email
   :outertype: API.Get.LoginResponse

token
^^^^^

.. java:field:: public String token
   :outertype: API.Get.LoginResponse

user
^^^^

.. java:field:: public User user
   :outertype: API.Get.LoginResponse

Constructors
------------
LoginResponse
^^^^^^^^^^^^^

.. java:constructor:: public LoginResponse(User user, String token, String email)
   :outertype: API.Get.LoginResponse

   Store the provided parameters in the bundle object

   :param user: User object described by returned JSON
   :param token: Login token
   :param email: User's email address

