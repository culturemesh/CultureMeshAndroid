.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

Postable
========

.. java:package:: com.culturemesh.android.models
   :noindex:

.. java:type:: public interface Postable

   Classes that implement this interface can be sent in the bodies of requests sent using \ :java:ref:`com.culturemesh.android.API.Post.model(RequestQueue,Postable,String,String,Response.Listener)`\ .

Methods
-------
getPostJson
^^^^^^^^^^^

.. java:method::  JSONObject getPostJson() throws JSONException
   :outertype: Postable

   Generates a JSON representation of the object that can be used in POST requests to the server. The exact format of the JSON depends upon the specifications of the server API. See the server's Swagger documentation for more.

   :throws JSONException: May be thrown if any of the values to include in the JSON are incompatible with the JSON format
   :return: JSON representation of the object suitable for inclusion in the bodies of POST requests

