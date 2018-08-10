.. java:import:: android.arch.persistence.room Embedded

.. java:import:: android.arch.persistence.room Entity

.. java:import:: android.arch.persistence.room PrimaryKey

.. java:import:: org.codethechange.culturemesh Listable

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: java.io Serializable

Place
=====

.. java:package:: org.codethechange.culturemesh.models
   :noindex:

.. java:type:: @Entity public abstract class Place extends Location implements Listable, Serializable

   A \ ``Place``\  is a \ :java:ref:`Location`\  with more information. While a \ :java:ref:`Location`\  stores only city, region, and country IDs, \ ``Place``\  also stores the areas position (latitude and longitude), population, and feature code. \ ``Place``\  is abstract, and some examples of its subclasses are: \ :java:ref:`City`\ , \ :java:ref:`Region`\ , and \ :java:ref:`Country`\ . Created by Drew Gregory on 2/23/18. This is the superclass for cities, regions, and countries.

Fields
------
NOWHERE
^^^^^^^

.. java:field:: protected static final String NOWHERE
   :outertype: Place

   The \ ``NOWHERE``\  constant is used internally by this hierarchy as the name of a location's city, region, or country when that geographic identifier is not specified. For example, Washington D.C. has no state (i.e. region), so its region might be stored as \ ``NOWHERE``\ . \ **This should never be used by clients.**\  Instead, creating such places should be done through provided constructors or methods.

featureCode
^^^^^^^^^^^

.. java:field:: public String featureCode
   :outertype: Place

   Feature code, which is a string describing the type of place represented (e.g. a capital, a religiously important area, an abandoned populated area). See http://www.geonames.org/export/codes.html for more examples.

id
^^

.. java:field:: @PrimaryKey public long id
   :outertype: Place

   The ID to be used by a database to identify this object. It is set using \ :java:ref:`Place.getDatabaseId()`\ . See that method's documentation for more information. Crucially \ **it is NOT guaranteed to be unique.**\

latLng
^^^^^^

.. java:field:: @Embedded public Point latLng
   :outertype: Place

   Latitude and longitude

population
^^^^^^^^^^

.. java:field:: public long population
   :outertype: Place

   The population of the described area. This is for display under the "people" icon when areas are listed.

Constructors
------------
Place
^^^^^

.. java:constructor:: public Place(long countryId, long regionId, long cityId, Point latLng, long population, String featureCode)
   :outertype: Place

   Initialize instance fields with provided parameters. Also calls \ :java:ref:`Location.Location(long,long,long)`\  with the provided IDs Postcondition: \ :java:ref:`Place.id`\  is initialized using \ :java:ref:`Place.getDatabaseId()`\

   :param countryId: ID of country
   :param regionId: ID of region
   :param cityId: ID of city
   :param latLng: Coordinates (latitude and longitude) of location
   :param population: Population of location
   :param featureCode: Feature code of location

Place
^^^^^

.. java:constructor:: public Place(JSONObject json) throws JSONException
   :outertype: Place

   Initializes ID instance fields using the provided JSON object The following keys must be present and are used to fill the relevant instance fields: \ ``latitude``\ , \ ``longitude``\ , \ ``population``\ , \ ``feature_code``\ . In addition, the JSON object is passed to \ :java:ref:`Location.Location(JSONObject)`\ . See its documentation for details on its requirements. \ :java:ref:`Place.id`\  is initialized using \ :java:ref:`Place.getDatabaseId()`\ . Precondition: The JSON must be validly formatted, with examples in \ :java:ref:`org.codethechange.culturemesh.API`\

   :param json: JSON object to extract initializing information from
   :throws JSONException: May be thrown for invalidly formatted JSON object

Place
^^^^^

.. java:constructor:: public Place()
   :outertype: Place

   Empty constructor for database use only. This should never be called by our code.

Methods
-------
abbreviateForListing
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static String abbreviateForListing(String toAbbreviate)
   :outertype: Place

   Abbreviate the provided string by truncating it enough so that, after adding \ :java:ref:`Listable.ellipses`\ , the string is \ :java:ref:`Listable.MAX_CHARS`\  characters long. If the string is already shorter than \ :java:ref:`Listable.MAX_CHARS`\ , it is returned unchanged.

   :param toAbbreviate: String whose abbreviated form will be returned
   :return: Abbreviated form of the string. Has a maximum length of \ :java:ref:`Listable.MAX_CHARS`\

getCityName
^^^^^^^^^^^

.. java:method:: public String getCityName()
   :outertype: Place

   Attempt to get the name of the \ :java:ref:`City`\  for this \ :java:ref:`Place`\ . May return \ :java:ref:`Place.NOWHERE`\ .

   :return: Name of the \ :java:ref:`City`\  if one is available, or \ :java:ref:`Place.NOWHERE`\  otherwise.

getCountryName
^^^^^^^^^^^^^^

.. java:method:: public String getCountryName()
   :outertype: Place

   Attempt to get the name of the \ :java:ref:`Country`\  for this \ :java:ref:`Place`\ . May return \ :java:ref:`Place.NOWHERE`\ .

   :return: Name of the \ :java:ref:`Country`\  if one is available, or \ :java:ref:`Place.NOWHERE`\  otherwise.

getFeatureCode
^^^^^^^^^^^^^^

.. java:method:: public String getFeatureCode()
   :outertype: Place

   Get the feature code describing the location. See http://www.geonames.org/export/codes.html for examples.

   :return: Location's feature code

getFullName
^^^^^^^^^^^

.. java:method:: public abstract String getFullName()
   :outertype: Place

   Subclasses are required to provide a method to generate their full, unambiguous name. For example, \ ``New York, New York, United States of America``\ .

   :return: Full, unambiguous name of place

getLatLng
^^^^^^^^^

.. java:method:: public Point getLatLng()
   :outertype: Place

   Get the coordinates of the location

   :return: Latitude and longitude of the location

getListableName
^^^^^^^^^^^^^^^

.. java:method:: public String getListableName()
   :outertype: Place

   Get a name suitable for display in listings of places, as required to implement \ :java:ref:`Listable`\ . This name is created by abbreviating the output of \ :java:ref:`Place.getFullName()`\  and adding \ :java:ref:`Listable.ellipses`\  such that the total length is a no longer than \ :java:ref:`Listable.MAX_CHARS`\

   :return: Name of Location suitable for display in UI lists. Has a maximum length of \ :java:ref:`Listable.MAX_CHARS`\ .

getNumUsers
^^^^^^^^^^^

.. java:method:: public long getNumUsers()
   :outertype: Place

   Get the number of users (population) to display in conjunction with the location

   :return: Population of the location

getPopulation
^^^^^^^^^^^^^

.. java:method:: public long getPopulation()
   :outertype: Place

   Get the population of the location

   :return: Location's population

getRegionName
^^^^^^^^^^^^^

.. java:method:: public String getRegionName()
   :outertype: Place

   Attempt to get the name of the \ :java:ref:`Region`\  for this \ :java:ref:`Place`\ . May return \ :java:ref:`Place.NOWHERE`\ .

   :return: Name of the \ :java:ref:`Region`\  if one is available, or \ :java:ref:`Place.NOWHERE`\  otherwise.

getShortName
^^^^^^^^^^^^

.. java:method:: public abstract String getShortName()
   :outertype: Place

   In the interest of space, we also want the abbreviated version of the location (just the city name for example)

   :return: Name of location suitable for header bar.

toString
^^^^^^^^

.. java:method:: public String toString()
   :outertype: Place

   Represent the object as a string suitable for debugging, but not for display to user.

   :return: String representation of the form \ ``Class[var=value, var=value, var=value, ...]``\

