.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: java.util HashMap

NearLocation
============

.. java:package:: org.codethechange.culturemesh.models
   :noindex:

.. java:type:: public class NearLocation extends DatabaseLocation

   Wrapper for \ ``DatabaseLocation``\  that is for Near locations. See the documentation for \ ``DatabaseLocation``\  for information as to why this redundancy is necessary. All of these instance fields will be stored in the local cached database.

Fields
------
CITY_ID_KEY
^^^^^^^^^^^

.. java:field:: public static final String CITY_ID_KEY
   :outertype: NearLocation

   Constant that holds the JSON key whose value will be the ID of the city (\ :java:ref:`City.cityId`\ ) in communications with the server.

   **See also:** :java:ref:`Location.Location(JSONObject,String,String,String)`

COUNTRY_ID_KEY
^^^^^^^^^^^^^^

.. java:field:: public static final String COUNTRY_ID_KEY
   :outertype: NearLocation

   Constant that holds the JSON key whose value will be the ID of the country (\ :java:ref:`Country.countryId`\ ) in communications with the server.

   **See also:** :java:ref:`Location.Location(JSONObject,String,String,String)`

REGION_ID_KEY
^^^^^^^^^^^^^

.. java:field:: public static final String REGION_ID_KEY
   :outertype: NearLocation

   Constant that holds the JSON key whose value will be the ID of the region (\ :java:ref:`Region.regionId`\ ) in communications with the server.

   **See also:** :java:ref:`Location.Location(JSONObject,String,String,String)`

near_city_id
^^^^^^^^^^^^

.. java:field:: public long near_city_id
   :outertype: NearLocation

   Mirrors the \ :java:ref:`Location.cityId`\  in \ :java:ref:`Location`\  to avoid collisions in the database

   **See also:** :java:ref:`DatabaseLocation`

near_country_id
^^^^^^^^^^^^^^^

.. java:field:: public long near_country_id
   :outertype: NearLocation

   Mirrors the \ :java:ref:`Location.countryId`\  in \ :java:ref:`Location`\  to avoid collisions in the database

   **See also:** :java:ref:`DatabaseLocation`

near_region_id
^^^^^^^^^^^^^^

.. java:field:: public long near_region_id
   :outertype: NearLocation

   Mirrors the \ :java:ref:`Location.regionId`\  in \ :java:ref:`Location`\  to avoid collisions in the database

   **See also:** :java:ref:`DatabaseLocation`

Constructors
------------
NearLocation
^^^^^^^^^^^^

.. java:constructor:: public NearLocation(long cityId, long regionId, long countryId)
   :outertype: NearLocation

   Initialize instance fields with provided parameters

   :param cityId: ID of city
   :param regionId: ID of region
   :param countryId: ID of country

NearLocation
^^^^^^^^^^^^

.. java:constructor:: public NearLocation(JSONObject json) throws JSONException
   :outertype: NearLocation

   Initializes instance fields by passing JSON to \ :java:ref:`Location.Location(JSONObject,String,String,String)`\  and then initializing instance fields using \ :java:ref:`NearLocation.initialize()`\

   :param json: JSON object describing the location
   :throws JSONException: May be thrown in response to improperly formatted JSON

NearLocation
^^^^^^^^^^^^

.. java:constructor:: @Deprecated public NearLocation(JSONObject json, boolean distinguisher) throws JSONException
   :outertype: NearLocation

   Initializes instance fields by passing JSON to \ :java:ref:`Location.Location(JSONObject)`\  and then initializing instance fields using \ :java:ref:`NearLocation.initialize()`\

   :param json: JSON object describing the location
   :param distinguisher: Useless value used to distinguish from \ :java:ref:`NearLocation.NearLocation(JSONObject)`\
   :throws JSONException: May be thrown in response to improperly formatted JSON

NearLocation
^^^^^^^^^^^^

.. java:constructor:: public NearLocation()
   :outertype: NearLocation

   Empty constructor for database use only. This should never be called by our code.

