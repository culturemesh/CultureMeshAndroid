.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: java.io Serializable

Network
=======

.. java:package:: com.culturemesh.android.models
   :noindex:

.. java:type:: public class Network implements Serializable, Postable

   This class stores all the information related to a network. It is fully expanded, meaning that its instance fields like \ :java:ref:`Network.nearLocation`\  store expanded objects (i.e. \ :java:ref:`Place`\ , not the stripped-down forms for database storage.

Fields
------
fromLocation
^^^^^^^^^^^^

.. java:field:: public Place fromLocation
   :outertype: Network

   Where users of the network are from. Must be specified if the network is location-based.

id
^^

.. java:field:: public long id
   :outertype: Network

   ID of network. Must always be specified.

language
^^^^^^^^

.. java:field:: public Language language
   :outertype: Network

   What language the users of the network speak. Must be specified if the network is language- based.

nearLocation
^^^^^^^^^^^^

.. java:field:: public Place nearLocation
   :outertype: Network

   The current location of users in the network. Must always be specified.

Constructors
------------
Network
^^^^^^^

.. java:constructor:: public Network(Place nearLocation, Place fromLocation, long id)
   :outertype: Network

   Create a location-based network from the provided objects

   :param nearLocation: Where the network's users currently reside
   :param fromLocation: Where the network's users are all from
   :param id: ID of the network

Network
^^^^^^^

.. java:constructor:: public Network(Place nearLocation, Language lang, long id)
   :outertype: Network

   Create a language-based network from the provided objects

   :param nearLocation: Where the network's users currently reside
   :param lang: What language the network's users all speak
   :param id: ID of the network

Methods
-------
getDatabaseNetwork
^^^^^^^^^^^^^^^^^^

.. java:method:: public DatabaseNetwork getDatabaseNetwork()
   :outertype: Network

   Get a \ :java:ref:`DatabaseNetwork`\  with the IDs stored by the \ :java:ref:`Network`\  from which the method is called.

   :return: The \ :java:ref:`DatabaseNetwork`\  associated with this \ :java:ref:`Network`\

getPostJson
^^^^^^^^^^^

.. java:method:: @Override public JSONObject getPostJson() throws JSONException
   :outertype: Network

   Generate a JSON representation of the object suitable for use in POST requests. Wrapper for \ :java:ref:`Network.toJSON()`\ .

   :throws JSONException: May be thrown if something that should be a value in the JSON is not a valid value in the JSON format.
   :return: JSON that can be passed to the server in the body of a POST request

   **See also:** :java:ref:`Network.toJSON();`

isLanguageBased
^^^^^^^^^^^^^^^

.. java:method:: public boolean isLanguageBased()
   :outertype: Network

   Check whether this network is of people who speak the same language

   :return: \ ``true``\  if the network is defined in terms of language, \ ``false``\  otherwise

isLocationBased
^^^^^^^^^^^^^^^

.. java:method:: public boolean isLocationBased()
   :outertype: Network

   Check whether this network is of people who come from the same place

   :return: \ ``true``\  if the network is defined by where members are from, \ ``false``\  otherwise

toJSON
^^^^^^

.. java:method:: public JSONObject toJSON() throws JSONException
   :outertype: Network

   Generate a JSON describing the object. The JSON will conform to the following format:

   .. parsed-literal::

      {
                   "id_city_cur": 0,
                   "city_cur": "string",
                   "id_region_cur": 0,
                   "region_cur": "string",
                   "id_country_cur": 0,
                   "country_cur": "string",
                   "id_city_origin": 0,
                   "city_origin": "string",
                   "id_region_origin": 0,
                   "region_origin": "string",
                   "id_country_origin": 0,
                   "country_origin": "string",
                   "id_language_origin": 0,
                   "language_origin": "string",
                   "network_class": "string"
                }

   where missing IDs are passed as \ :java:ref:`Location.NOWHERE`\ . This format is suitable for submission to the server using the \ ``/network/new``\  POST endpoint.

   :throws JSONException: Unclear when this would be thrown
   :return: JSON representation of the object

toString
^^^^^^^^

.. java:method:: public String toString()
   :outertype: Network

   Represent the object as a string suitable for debugging, but not for display to user.

   :return: String representation of the form \ ``Class[var=value, var=value, var=value, ...]``\

