.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

FromLocation
============

.. java:package:: com.culturemesh.models
   :noindex:

.. java:type:: public class FromLocation extends DatabaseLocation

   Wrapper for \ :java:ref:`DatabaseLocation`\  that is for From locations. See the documentation for \ :java:ref:`DatabaseLocation`\  for information as to why this redundancy is necessary. All of these instance fields will be stored in the local cached database.

Fields
------
CITY_ID_KEY
^^^^^^^^^^^

.. java:field:: public static final String CITY_ID_KEY
   :outertype: FromLocation

   Constant that holds the JSON key whose value will be the ID of the city (\ :java:ref:`City.cityId`\ ) in communications with the server.

   **See also:** :java:ref:`Location.Location(JSONObject,String,String,String)`

COUNTRY_ID_KEY
^^^^^^^^^^^^^^

.. java:field:: public static final String COUNTRY_ID_KEY
   :outertype: FromLocation

   Constant that holds the JSON key whose value will be the ID of the country (\ :java:ref:`Country.countryId`\ ) in communications with the server.

   **See also:** :java:ref:`Location.Location(JSONObject,String,String,String)`

REGION_ID_KEY
^^^^^^^^^^^^^

.. java:field:: public static final String REGION_ID_KEY
   :outertype: FromLocation

   Constant that holds the JSON key whose value will be the ID of the region (\ :java:ref:`Region.regionId`\ ) in communications with the server.

   **See also:** :java:ref:`Location.Location(JSONObject,String,String,String)`

from_city_id
^^^^^^^^^^^^

.. java:field:: public long from_city_id
   :outertype: FromLocation

   Mirrors the \ :java:ref:`Location.cityId`\  in \ :java:ref:`Location`\  to avoid collisions in the database

   **See also:** :java:ref:`DatabaseLocation`

from_country_id
^^^^^^^^^^^^^^^

.. java:field:: public long from_country_id
   :outertype: FromLocation

   Mirrors the \ :java:ref:`Location.countryId`\  in \ :java:ref:`Location`\  to avoid collisions in the database

   **See also:** :java:ref:`DatabaseLocation`

from_region_id
^^^^^^^^^^^^^^

.. java:field:: public long from_region_id
   :outertype: FromLocation

   Mirrors the \ :java:ref:`Location.regionId`\  in \ :java:ref:`Location`\  to avoid collisions in the database

   **See also:** :java:ref:`DatabaseLocation`

Constructors
------------
FromLocation
^^^^^^^^^^^^

.. java:constructor:: public FromLocation(long cityId, long regionId, long countryId)
   :outertype: FromLocation

   Initialize instance fields with provided parameters

   :param cityId: ID of city
   :param regionId: ID of region
   :param countryId: ID of country

FromLocation
^^^^^^^^^^^^

.. java:constructor:: public FromLocation(JSONObject json) throws JSONException
   :outertype: FromLocation

   Initializes instance fields by passing JSON to \ :java:ref:`Location.Location(JSONObject,String,String,String)`\  and then initializing instance fields using \ :java:ref:`FromLocation.initialize()`\

   :param json: JSON object describing the location
   :throws JSONException: May be thrown in response to improperly formatted JSON

FromLocation
^^^^^^^^^^^^

.. java:constructor:: @Deprecated public FromLocation(JSONObject json, boolean distinguisher) throws JSONException
   :outertype: FromLocation

   Initializes instance fields by passing JSON to \ :java:ref:`Location.Location(JSONObject)`\  )} and then initializing instance fields using \ :java:ref:`FromLocation.initialize()`\

   :param json: JSON object describing the location
   :throws JSONException: May be thrown in response to improperly formatted JSON

FromLocation
^^^^^^^^^^^^

.. java:constructor:: public FromLocation()
   :outertype: FromLocation

   Empty constructor for database use only. This should never be called by our code.

