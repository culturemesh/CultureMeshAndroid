.. java:import:: android.arch.persistence.room Entity

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

Region
======

.. java:package:: com.culturemesh.models
   :noindex:

.. java:type:: @Entity public class Region extends Place

   A \ :java:ref:`Region`\  is a specific kind of \ :java:ref:`Place`\  that stores the ID and name of a region. It can also store the name and ID of the region's country, but this is not mandatory. If any geographical descriptor (e.g. city, region, or country) is not specified, its name will be stored as \ :java:ref:`Place.NOWHERE`\ , but this constant should not be used by clients. Note that the \ ``region``\  descriptor is mandatory.

Fields
------
countryName
^^^^^^^^^^^

.. java:field:: public String countryName
   :outertype: Region

   Name of the country (may store \ :java:ref:`Place.NOWHERE`\

regionName
^^^^^^^^^^

.. java:field:: public String regionName
   :outertype: Region

   Name of the region (should always be specified and not as \ :java:ref:`Place.NOWHERE`\

Constructors
------------
Region
^^^^^^

.. java:constructor:: public Region(long regionId, long countryId, String regionName, String countryName, Point latLng, long population, String featureCode)
   :outertype: Region

   Initialize instance fields and those of superclass with provided parameters No parameters should be set to \ :java:ref:`Place.NOWHERE`\  or \ :java:ref:`Location.NOWHERE`\  For regions with explicitly specified countries

   :param regionId: ID of region
   :param countryId: ID of country
   :param regionName: Name of region
   :param countryName: Name of country
   :param latLng: Latitude and longitude coordinates of the region
   :param population: Population of the region
   :param featureCode: Region's feature code

Region
^^^^^^

.. java:constructor:: public Region(long regionId, String regionName, Point latLng, long population, String featureCode)
   :outertype: Region

   Initialize instance fields and those of superclass with provided parameters No parameters should be set to \ :java:ref:`Place.NOWHERE`\  or \ :java:ref:`Location.NOWHERE`\  For regions that have no specified country

   :param regionId: ID of region
   :param regionName: Name of region
   :param latLng: Latitude and longitude coordinates of the region
   :param population: Population of the region
   :param featureCode: Region's feature code

Region
^^^^^^

.. java:constructor:: public Region(JSONObject json) throws JSONException
   :outertype: Region

   Initialize instance fields and those of superclass based on provided JSON This class extracts the following fields, if they are present: \ ``country_name``\ . It requires that the key \ ``name``\  exist, as its value will be used as the region's name

   :param json: JSON object describing the region to create
   :throws JSONException: May be thrown in response to an invalidly formatted JSON object

Region
^^^^^^

.. java:constructor:: public Region()
   :outertype: Region

   Empty constructor for database use only. This should never be called by our code.

Methods
-------
getFullName
^^^^^^^^^^^

.. java:method:: public String getFullName()
   :outertype: Region

   Get a name for the region that lists all available geographic descriptor names. For example, \ ``Washington, D.C.``\  would be expressed as \ ``Washington, D.C., United States``\ , while \ ``San Francisco``\  would be expressed as \ ``San Francisco, California, United States``\ .

   :return: Name of city that includes all available geographic descriptors

getName
^^^^^^^

.. java:method:: public String getName()
   :outertype: Region

   Get the name of the region

   :return: Name of region

getShortName
^^^^^^^^^^^^

.. java:method:: public String getShortName()
   :outertype: Region

   Now display just region name.

toString
^^^^^^^^

.. java:method:: public String toString()
   :outertype: Region

   Represent the object as a string suitable for debugging, but not for display to user.

   :return: String representation of the form \ ``Class[var=value, var=value, var=value, ...]``\

