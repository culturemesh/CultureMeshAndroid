.. java:import:: com.culturemesh.android Listable

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

Country
=======

.. java:package:: com.culturemesh.android.models
   :noindex:

.. java:type:: public class Country extends Place

   A \ :java:ref:`Country`\  is a specific kind of \ :java:ref:`Place`\  that stores the ID and name of a country. No instance field should ever be set to \ :java:ref:`Place.NOWHERE`\ .

Fields
------
isoA2
^^^^^

.. java:field:: public String isoA2
   :outertype: Country

   2-Letter ISO country code. This is not currently used.

name
^^^^

.. java:field:: public String name
   :outertype: Country

   Name of country

Constructors
------------
Country
^^^^^^^

.. java:constructor:: public Country(long id, String name, Point latLng, long population, String featureCode, String isoA2)
   :outertype: Country

   Initialize instance fields and those of superclass with provided parameters

   :param id: ID of country
   :param name: Name of country
   :param latLng: Latitude and longitude coordinates of the region
   :param population: Population of the region
   :param featureCode: Region's feature code
   :param isoA2: 2-Letter ISO country code

Country
^^^^^^^

.. java:constructor:: public Country(JSONObject json) throws JSONException
   :outertype: Country

   Initialize instance fields and those of superclass based on provided JSON It requires that the key \ ``name``\  exist, as its value will be used as the country's name

   :param json: JSON object describing the country to create
   :throws JSONException: May be thrown in response to invalid JSON object

Country
^^^^^^^

.. java:constructor:: public Country()
   :outertype: Country

   Empty constructor for database use only. This should never be called by our code.

Methods
-------
getFullName
^^^^^^^^^^^

.. java:method:: public String getFullName()
   :outertype: Country

   Get name of country, which is suitable for display in UI.

   :return: Name of country, abbreviated if necessary to have a maximum length of \ :java:ref:`Listable.MAX_CHARS`\ .

   **See also:** :java:ref:`Listable`

getName
^^^^^^^

.. java:method:: public String getName()
   :outertype: Country

   Get name of country

   :return: Name of country

getShortName
^^^^^^^^^^^^

.. java:method:: public String getShortName()
   :outertype: Country

   Now display just country name.

toString
^^^^^^^^

.. java:method:: public String toString()
   :outertype: Country

   Represent the object as a string suitable for debugging, but not for display to user.

   :return: String representation of the form \ ``Class[var=value, var=value, var=value, ...]``\

