.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: org.codethechange.culturemesh.models City

.. java:import:: org.codethechange.culturemesh.models Country

.. java:import:: java.util List

CityDao
=======

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface CityDao

   Created by Drew Gregory on 2/23/18.

Methods
-------
autoCompleteCities
^^^^^^^^^^^^^^^^^^

.. java:method:: @Query public List<City> autoCompleteCities(String query)
   :outertype: CityDao

getCity
^^^^^^^

.. java:method:: @Query public City getCity(long id)
   :outertype: CityDao

insertCities
^^^^^^^^^^^^

.. java:method:: @Insert public void insertCities(City... cities)
   :outertype: CityDao

