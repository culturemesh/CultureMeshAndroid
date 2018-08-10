.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

Putable
=======

.. java:package:: org.codethechange.culturemesh.models
   :noindex:

.. java:type:: public interface Putable

   Classes that implement this interface can be sent in the bodies of requests sent using \ :java:ref:`org.codethechange.culturemesh.API.Put.model(RequestQueue,Putable,String,String,Response.Listener)`\ .

Methods
-------
getPutJson
^^^^^^^^^^

.. java:method::  JSONObject getPutJson() throws JSONException
   :outertype: Putable

   Generates a JSON representation of the object that can be used in PUT requests to the server. The exact format of the JSON depends upon the specifications of the server API. See the server's Swagger documentation for more.

   :throws JSONException: May be thrown if any of the values to include in the JSON are incompatible with the JSON format
   :return: JSON representation of the object suitable for inclusion in the bodies of PUT requests

