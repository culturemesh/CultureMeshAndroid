.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

DatabaseLocation
================

.. java:package:: com.culturemesh.models
   :noindex:

.. java:type:: public abstract class DatabaseLocation extends Location

   Superclass for Locations that will be stored in the database. Since the instance field names are used directly as column names in the database, a single class cannot be used for both From and Near locations (the column names would conflict). Therefore, two separate classes, \ ``FromLocation``\  and \ ``NearLocation``\  are used. They are nearly identical, however, so this superclass holds methods common to both. It also imposes requirements on them to ensure that those methods can function. The database will store the IDs of the city, region, and country.

Constructors
------------
DatabaseLocation
^^^^^^^^^^^^^^^^

.. java:constructor:: public DatabaseLocation(long countryId, long regionId, long cityId)
   :outertype: DatabaseLocation

   Constructor that passes all parameters to superclass constructor

   :param countryId: ID of country
   :param regionId: ID of region
   :param cityId: ID of city

DatabaseLocation
^^^^^^^^^^^^^^^^

.. java:constructor:: public DatabaseLocation(JSONObject json) throws JSONException
   :outertype: DatabaseLocation

   Constructor that passes all parameters to superclass constructor

   :param json: JSON object that defines the location. See superclass constructor documentation.
   :throws JSONException: May be thrown for improperly formatted JSON

DatabaseLocation
^^^^^^^^^^^^^^^^

.. java:constructor:: public DatabaseLocation(JSONObject json, String cityIdKey, String regionIdKey, String countryIdKey) throws JSONException
   :outertype: DatabaseLocation

   Passes all parameters, maintaining order, to \ :java:ref:`Location.Location(JSONObject,String,String,String)`\

   :param json:
   :param cityIdKey:
   :param regionIdKey:
   :param countryIdKey:
   :throws JSONException:

DatabaseLocation
^^^^^^^^^^^^^^^^

.. java:constructor:: public DatabaseLocation()
   :outertype: DatabaseLocation

   Empty constructor for database use only. This should never be called by our code.

