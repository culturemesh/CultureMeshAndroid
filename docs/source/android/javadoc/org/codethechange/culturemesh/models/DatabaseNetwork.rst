.. java:import:: android.arch.persistence.room Embedded

.. java:import:: android.arch.persistence.room Entity

.. java:import:: android.arch.persistence.room PrimaryKey

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

DatabaseNetwork
===============

.. java:package:: com.culturemesh.models
   :noindex:

.. java:type:: @Entity public class DatabaseNetwork

   This class is solely for storing the bare, ID-only form of a network in the database. After being retrieved from the database or received from a network request, it should immediately be used to create a \ :java:ref:`Network`\  object, with the additional information that comes with. Storing only IDs in the database makes the \ :java:ref:`DatabaseNetwork.nearLocation`\ , \ :java:ref:`DatabaseNetwork.fromLocation`\  and \ :java:ref:`DatabaseNetwork.languageId`\  references pointers to database entries with more information. This reduces the risk of conflicting information and reduces the overhead of updating data in more than one spot in the database.

Fields
------
fromLocation
^^^^^^^^^^^^

.. java:field:: @Embedded public FromLocation fromLocation
   :outertype: DatabaseNetwork

   The location where the users of this network are from. It may be \ ``null``\  to indicate that no location is specified only if \ :java:ref:`DatabaseNetwork.isLanguageBased`\  is \ ``false``\

id
^^

.. java:field:: @PrimaryKey public long id
   :outertype: DatabaseNetwork

   The network's ID. This is used as its unique identifier in the database.

isLanguageBased
^^^^^^^^^^^^^^^

.. java:field:: public boolean isLanguageBased
   :outertype: DatabaseNetwork

   Denotes whether this network's \ *from*\  attribute is based on where an individual is from or on what language they speak. \ ``true``\ : Based on what language they speak \ ``false``\ : Based on what location they are from

languageId
^^^^^^^^^^

.. java:field:: public long languageId
   :outertype: DatabaseNetwork

   The ID of the language the users of this network speak. It may be set to \ ``-1``\  to indicate no language being specified only if \ :java:ref:`DatabaseNetwork.isLanguageBased`\  is \ ``false``\

nearLocation
^^^^^^^^^^^^

.. java:field:: @Embedded public NearLocation nearLocation
   :outertype: DatabaseNetwork

   The location where the users of this network currently reside. It must not be null.

Constructors
------------
DatabaseNetwork
^^^^^^^^^^^^^^^

.. java:constructor:: public DatabaseNetwork()
   :outertype: DatabaseNetwork

   Empty constructor for database use only. This should never be called by our code.

DatabaseNetwork
^^^^^^^^^^^^^^^

.. java:constructor:: public DatabaseNetwork(NearLocation nearLocation, FromLocation fromLocation, long id)
   :outertype: DatabaseNetwork

   Create a new \ :java:ref:`DatabaseNetwork`\  for a network of people who come from the same area

   :param nearLocation: Where the network's members currently reside
   :param fromLocation: Where the network's members are from
   :param id: ID for this network

DatabaseNetwork
^^^^^^^^^^^^^^^

.. java:constructor:: public DatabaseNetwork(NearLocation nearLocation, long langId, long id)
   :outertype: DatabaseNetwork

   Create a new \ :java:ref:`DatabaseNetwork`\  for a network of people who speak the same language

   :param nearLocation: Where the network's members currently reside
   :param langId: ID for the language the network's members speak
   :param id: ID for this network

DatabaseNetwork
^^^^^^^^^^^^^^^

.. java:constructor:: public DatabaseNetwork(JSONObject json) throws JSONException
   :outertype: DatabaseNetwork

   \ **If the key location_cur is present (old JSON version):**\  Initialize instance fields with the data in the provided JSON. The following keys are mandatory and used: \ ``location_cur``\ , whose value is expected to be a JSON describing a \ :java:ref:`NearLocation`\  object and can be passed to \ :java:ref:`NearLocation.NearLocation(JSONObject)`\ , and \ ``network_class``\ , whose value is expected to be either \ ``0``\ , indicating a location-based network, or \ ``1``\ , indicating a language-based network. If the network is language-based, they key \ ``language_origin``\  must exist with a value of a JSON object containing a key \ ``id``\  whose value is the ID of a \ :java:ref:`Language`\ . If the network is location-based, the key \ ``location_origin``\  must exist and have a value of a JSON object representing a \ :java:ref:`FromLocation`\  that can be passed to \ :java:ref:`FromLocation.FromLocation(JSONObject)`\ . \ **NOTE: This JSON format is deprecated and should not be used if possible.**\  \ **If the key location_cur is not present (new JSON version):**\  Initialize instance fields with the data in the provided JSON. The following keys are mandatory and used: All keys required by \ :java:ref:`NearLocation.NearLocation(JSONObject)`\  and the key \ ``network_class``\ , whose value is expected to be either \ ``_l``\ , indicating a language-based network, or one of \ ``cc``\ , \ ``rc``\ , and \ ``co``\ , indicating a location-based network. If the network is language-based, the key \ ``id_language_origin``\  must exist with a value of the ID of a \ :java:ref:`Language`\ . If the network is location-based, all keys required by \ :java:ref:`FromLocation.FromLocation(JSONObject)`\  must be present.

   :param json: JSON object describing the network in terms of IDs
   :throws JSONException: May be thrown in response to improperly formatted JSON

Methods
-------
isLanguageBased
^^^^^^^^^^^^^^^

.. java:method:: public boolean isLanguageBased()
   :outertype: DatabaseNetwork

   Check whether this network is of people who speak the same language

   :return: \ ``true``\  if the network is defined in terms of language, \ ``false``\  otherwise

isLocationBased
^^^^^^^^^^^^^^^

.. java:method:: public boolean isLocationBased()
   :outertype: DatabaseNetwork

   Check whether this network is of people who come from the same place

   :return: \ ``true``\  if the network is defined by where members are from, \ ``false``\  otherwise

toString
^^^^^^^^

.. java:method:: public String toString()
   :outertype: DatabaseNetwork

   Represent the object as a string suitable for debugging, but not for display to user.

   :return: String representation of the form \ ``Class[var=value, var=value, var=value, ...]``\

