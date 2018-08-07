.. java:import:: android.arch.persistence.room Room

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

.. java:import:: com.android.volley VolleyLog

.. java:import:: com.android.volley.toolbox JsonArrayRequest

.. java:import:: com.android.volley.toolbox JsonObjectRequest

.. java:import:: com.android.volley.toolbox StringRequest

.. java:import:: org.codethechange.culturemesh.data CMDatabase

.. java:import:: org.codethechange.culturemesh.data EventDao

.. java:import:: org.codethechange.culturemesh.data EventSubscription

.. java:import:: org.codethechange.culturemesh.data EventSubscriptionDao

.. java:import:: org.codethechange.culturemesh.data NetworkSubscription

.. java:import:: org.codethechange.culturemesh.data NetworkSubscriptionDao

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

.. java:import:: java.io ByteArrayOutputStream

.. java:import:: java.io IOException

.. java:import:: java.io UnsupportedEncodingException

.. java:import:: java.nio.charset StandardCharsets

.. java:import:: java.util ArrayList

.. java:import:: java.util Calendar

.. java:import:: java.util Date

.. java:import:: java.util HashMap

.. java:import:: java.util List

.. java:import:: java.util Map

.. java:import:: java.util.concurrent.atomic AtomicInteger

API.InstantiationListener
=========================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type::  interface InstantiationListener
   :outertype: API

Methods
-------
instantiateComponent
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void instantiateComponent(RequestQueue queue, Object obj, Response.Listener listener)
   :outertype: API.InstantiationListener

