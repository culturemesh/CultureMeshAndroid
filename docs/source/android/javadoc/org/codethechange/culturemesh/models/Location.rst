.. java:import:: android.arch.persistence.room Ignore

.. java:import:: android.net Uri

.. java:import:: android.util Log

.. java:import:: com.culturemesh Listable

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: java.io Serializable

Location
========

.. java:package:: com.culturemesh.models
   :noindex:

.. java:type:: public class Location implements Serializable, Listable

   This object stores only the city, region, and country ID values, so it acts as a pointer to the more detailed information for the location in each City, Region, and Country's database entries or network information. No instance of this class should have \ ``countryId``\ , \ ``regionId``\ , and \ ``cityId``\  all equal to \ ``NOWHERE``\ . This should only be possible by mis-using the JSON constructor or by supplying \ ``-1``\  as an ID. Neither should ever be done.

   .. parsed-literal::

      Location
                           (IDs only)
                           /        \
                          /          \
                         /            \
                        /              \
               Place (Abstract)    DatabaseLocation (Abstract)
                (Full Info)                   (IDs)
                  /  |  \                   /      \
                 /   |   \           NearLocation  FromLocation
            City  Region  Country  (Wrappers for DatabaseLocation)
         (Specific cases of Place)

Fields
------
CITY
^^^^

.. java:field:: public static final int CITY
   :outertype: Location

   Represents a type of \ ``Location``\  that has a city defined.

   **See also:** :java:ref:`Location.getType()`

COUNTRY
^^^^^^^

.. java:field:: public static final int COUNTRY
   :outertype: Location

   Represents a type of \ ``Location``\  that has only a country defined.

   **See also:** :java:ref:`Location.getType()`

NOWHERE
^^^^^^^

.. java:field:: protected static final int NOWHERE
   :outertype: Location

   These constants are used to identify the type of location being stored. See the documentation for \ ``getType``\  for more. \ ``NOWHERE``\  is \ ``protected``\  because it should never be used by clients. It is only for subclasses to denote empty IDs. Creating locations with empty IDs should be handled by subclass constructors or methods.

REGION
^^^^^^

.. java:field:: public static final int REGION
   :outertype: Location

   Represents a type of \ ``Location``\  that has a region defined but not a city.

   **See also:** :java:ref:`Location.getType()`

URL_NULL_ID
^^^^^^^^^^^

.. java:field:: public static final int URL_NULL_ID
   :outertype: Location

   The value to be transmitted to the API in place of a missing country, region, or city ID

cityId
^^^^^^

.. java:field:: @Ignore public long cityId
   :outertype: Location

countryId
^^^^^^^^^

.. java:field:: @Ignore public long countryId
   :outertype: Location

   These instance fields store the IDs of the city, region, and country defining the location They can be \ ``private``\  because a plain \ ``Location``\  object should not need to be stored in the database.

locationName
^^^^^^^^^^^^

.. java:field:: @Ignore public String locationName
   :outertype: Location

   This is is only used for other searching in \ :java:ref:`com.culturemesh.FindNetworkActivity`\ . Do not use this field anywhere else.

regionId
^^^^^^^^

.. java:field:: @Ignore public long regionId
   :outertype: Location

Constructors
------------
Location
^^^^^^^^

.. java:constructor:: public Location(long countryId, long regionId, long cityId)
   :outertype: Location

   Initializes ID instance fields using the provided IDs

   :param countryId: ID of country
   :param regionId: ID of region
   :param cityId: ID of city

Location
^^^^^^^^

.. java:constructor:: public Location(JSONObject json) throws JSONException
   :outertype: Location

   Initializes ID instance fields using the provided JSON object If present, the values of the keys \ ``city_id``\ , \ ``region_id``\ , and \ ``country_id``\  will be used automatically. Depending on the presence of those keys, the value of the key \ ``id``\  will be used to fill the instance field for the JSON type. See \ ``getJsonType``\  for more. This constructor is designed to be used when creating \ :java:ref:`Place`\ s. Precondition: The JSON must be validly formatted, with examples in \ ``API.java``\

   :param json: JSON object containing the country, region, and city IDs
   :throws JSONException: May be thrown if the JSON is improperly formatted

Location
^^^^^^^^

.. java:constructor:: public Location(JSONObject json, String cityIdKey, String regionIdKey, String countryIdKey) throws JSONException
   :outertype: Location

   Initializes ID instance fields using the provided JSON object. The keys extracted are provided as parameters, but those keys need not exist in the JSON. Any missing keys will be treated as if the location does not have such a geographic identifier. This may produce an invalid location, and the JSON is followed blindly. Precondition: JSON must describe a valid location

   :param json: JSON that describes the location to create
   :param cityIdKey: The key that, if present in the JSON, has a value of the ID of the city
   :param regionIdKey: The key that, if present in the JSON, has a value of the ID of the region
   :param countryIdKey: The key that, if present in the JSON, has a value of the ID of the country
   :throws JSONException: May be thrown in the case of an invalid JSON

Location
^^^^^^^^

.. java:constructor:: public Location()
   :outertype: Location

   Empty constructor for database use only. This should never be called by our code.

Methods
-------
getCityId
^^^^^^^^^

.. java:method:: public long getCityId()
   :outertype: Location

   Getter for the city ID, which may return \ ``NOWHERE``\ , so \ ``hasCityId``\  should be used to check first

   :return: The city ID

getCountryId
^^^^^^^^^^^^

.. java:method:: public long getCountryId()
   :outertype: Location

   Getter for the country ID, which may return \ ``NOWHERE``\ , so \ ``hasCountryId``\  should be used to check first

   :return: The country ID

getDatabaseId
^^^^^^^^^^^^^

.. java:method:: protected long getDatabaseId()
   :outertype: Location

   Find the ID that should be used as the \ ``PrimaryKey``\  for a database. It is the ID of the most specific geographical descriptor with an ID that is not \ ``NOWHERE``\ . \ **WARNING: The returned ID is NOT guaranteed to be unique**\

   :return: ID for use as \ ``PrimaryKey``\  in a database

getFromLocation
^^^^^^^^^^^^^^^

.. java:method:: public FromLocation getFromLocation()
   :outertype: Location

   Transform a \ :java:ref:`Location`\  into a \ :java:ref:`FromLocation`\

   :return: A \ :java:ref:`FromLocation`\  with the same IDs as the \ :java:ref:`Location`\  object whose method was called

getListableName
^^^^^^^^^^^^^^^

.. java:method:: @Override public String getListableName()
   :outertype: Location

   Get a UI-ready name for the Location

   :return: Name for the Location that is suitable for display to the user. Abbreviated to be a maximum of \ :java:ref:`Listable.MAX_CHARS`\  characters long.

getNearLocation
^^^^^^^^^^^^^^^

.. java:method:: public NearLocation getNearLocation()
   :outertype: Location

   Transform a \ :java:ref:`Location`\  into a \ :java:ref:`NearLocation`\

   :return: A \ :java:ref:`NearLocation`\  with the same IDs as the \ :java:ref:`Location`\  object whose method was called

getRegionId
^^^^^^^^^^^

.. java:method:: public long getRegionId()
   :outertype: Location

   Getter for the region ID, which may return \ ``NOWHERE``\ , so \ ``hasRegionId``\  should be used to check first

   :return: The region ID

getType
^^^^^^^

.. java:method:: public int getType()
   :outertype: Location

   The most specific ID that is not \ ``NOWHERE``\  determines the location's type, even if more general IDs are \ ``NOWHERE``\ . For example, if \ ``regionId = 0``\  and \ ``countryId = cityId = NOWHERE``\ , the type would be \ ``REGION``\

   :return: Location's type as \ ``CITY``\ , \ ``REGION``\ , or \ ``COUNTRY``\

hasCityId
^^^^^^^^^

.. java:method:: public boolean hasCityId()
   :outertype: Location

   Check if the city ID is specified (i.e. not \ ``NOWHERE``\ )

   :return: \ ``true``\  if the city ID is specified, \ ``false``\  otherwise

hasCountryId
^^^^^^^^^^^^

.. java:method:: public boolean hasCountryId()
   :outertype: Location

   Check if the country ID is specified (i.e. not \ ``NOWHERE``\ )

   :return: \ ``true``\  if the country ID is specified, \ ``false``\  otherwise

hasRegionId
^^^^^^^^^^^

.. java:method:: public boolean hasRegionId()
   :outertype: Location

   Check if the region ID is specified (i.e. not \ ``NOWHERE``\ )

   :return: \ ``true``\  if the region ID is specified, \ ``false``\  otherwise

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Location

   Represent the object as a string suitable for debugging, but not for display to user.

   :return: String representation of the form \ ``Class[var=value, var=value, var=value, ...]``\

urlParam
^^^^^^^^

.. java:method:: public String urlParam()
   :outertype: Location

   Represent the \ :java:ref:`Location`\  in a form suitable for use as the value of a key passed in a URL parameter to the API. Specifically, it returns the country, region, and city IDs separated by commas and in that order. The commas are escaped with the UTF-8 scheme and any missing IDs are replaced with the \ :java:ref:`Location.URL_NULL_ID`\  constant, which is understood by the API as signifying \ ``null``\ .

   :return: An API-compatible representation suitable for use as the value in a URL parameter

