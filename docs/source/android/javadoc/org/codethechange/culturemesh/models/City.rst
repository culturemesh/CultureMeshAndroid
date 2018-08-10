.. java:import:: android.arch.persistence.room Entity

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

City
====

.. java:package:: org.codethechange.culturemesh.models
   :noindex:

.. java:type:: @Entity public class City extends Place

   A \ :java:ref:`City`\  is a specific kind of \ :java:ref:`Place`\  that stores the ID and name of a city. It can also store the names and IDs of the city's country and region, but this is not mandatory. If any geographical descriptor (e.g. city, region, or country) is not specified, its name will be stored as \ :java:ref:`Place.NOWHERE`\ , but this constant should not be used by clients. Note that the \ ``city``\  descriptor is mandatory.

Fields
------
cityName
^^^^^^^^

.. java:field:: public String cityName
   :outertype: City

   Name of city

countryName
^^^^^^^^^^^

.. java:field:: public String countryName
   :outertype: City

   Name of country.

regionName
^^^^^^^^^^

.. java:field:: public String regionName
   :outertype: City

   Name of region

Constructors
------------
City
^^^^

.. java:constructor:: public City(long cityId, long regionId, long countryId, String cityName, String regionName, String countryName, Point latLng, long population, String featureCode)
   :outertype: City

   Initialize instance fields and instance fields of superclasses based on provided arguments For creating cities that have city, region, and country all specified.

   :param cityId: ID of city
   :param regionId: ID of city's region
   :param countryId: ID of country's region
   :param cityName: Name of city
   :param regionName: Name of region city lies within
   :param countryName: Name of country city lies within
   :param latLng: Latitude and longitude coordinates of city
   :param population: Population of the city
   :param featureCode: Feature code of the city

City
^^^^

.. java:constructor:: public City(long cityId, long regionId, String cityName, String regionName, Point latLng, long population, String featureCode)
   :outertype: City

   Initialize instance fields and instance fields of superclasses based on provided arguments. For creating cities that have no country descriptor, but do have specified regions.

   :param cityId: ID of city
   :param regionId: ID of city's region
   :param cityName: Name of city
   :param regionName: Name of region city lies within
   :param latLng: Latitude and longitude coordinates of city
   :param population: Population of the city
   :param featureCode: Feature code of the city

City
^^^^

.. java:constructor:: public City(long cityId, String cityName, Point latLng, long population, String featureCode)
   :outertype: City

   Initialize instance fields and instance fields of superclasses based on provided arguments For creating cities that have no region nor country descriptor

   :param cityId: ID of city
   :param cityName: Name of city
   :param latLng: Latitude and longitude coordinates of city
   :param population: Population of the city
   :param featureCode: Feature code of the city

City
^^^^

.. java:constructor:: public City(JSONObject json) throws JSONException
   :outertype: City

   Initialize instance fields and those of superclass based on provided JSON This class extracts the following fields, if they are present: \ ``country_name``\  and \ ``region_name``\ . It requires that the key \ ``name``\  exist, as its value will be used as the City's name

   :param json: JSON object describing the city to create
   :throws JSONException: May be thrown in response to an invalidly formatted JSON object

City
^^^^

.. java:constructor:: public City()
   :outertype: City

   Empty constructor for database use only. This should never be called by our code.

Methods
-------
getFullName
^^^^^^^^^^^

.. java:method:: public String getFullName()
   :outertype: City

   Get a name for the city that lists all available geographic descriptor names. For example, \ ``Washington, D.C.``\  would be expressed as \ ``Washington, D.C., United States``\ , while \ ``San Francisco``\  would be expressed as \ ``San Francisco, California, United States``\ .

   :return: Name of city that includes all available geographic descriptors

getName
^^^^^^^

.. java:method:: public String getName()
   :outertype: City

   Get the name of the city

   :return: City name

getShortName
^^^^^^^^^^^^

.. java:method:: public String getShortName()
   :outertype: City

   Now display just city name.

newOnlyMissingRegion
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static City newOnlyMissingRegion(long cityId, long countryId, String cityName, String countryName, Point latLng, long population, String featureCode)
   :outertype: City

   Return \ :java:ref:`City`\  object with fields initialized with provided parameters For creating cities that are only missing the region descriptor This unusual pseudo-constructor is required to avoid ambiguity between constructors

   :param cityId: ID of city
   :param countryId: ID of country's region
   :param cityName: Name of city
   :param countryName: Name of country city lies within
   :param latLng: Latitude and longitude coordinates of city
   :param population: Population of the city
   :param featureCode: Feature code of the city
   :return: City object that has been initialized

toString
^^^^^^^^

.. java:method:: public String toString()
   :outertype: City

   Represent the object as a string suitable for debugging, but not for display to user.

   :return: String representation of the form \ ``Class[var=value, var=value, var=value, ...]``\

