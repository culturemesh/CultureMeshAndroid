.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Delete

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: android.arch.persistence.room Update

.. java:import:: org.codethechange.culturemesh.models DatabaseNetwork

.. java:import:: java.util List

NetworkDao
==========

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface NetworkDao

   Created by Drew Gregory on 2/19/18.

Methods
-------
deleteNetworks
^^^^^^^^^^^^^^

.. java:method:: @Delete public void deleteNetworks(DatabaseNetwork... networks)
   :outertype: NetworkDao

getNetwork
^^^^^^^^^^

.. java:method:: @Query public List<DatabaseNetwork> getNetwork(long id)
   :outertype: NetworkDao

insertNetworks
^^^^^^^^^^^^^^

.. java:method:: @Insert public void insertNetworks(DatabaseNetwork... networks)
   :outertype: NetworkDao

netFromLangAndHome
^^^^^^^^^^^^^^^^^^

.. java:method:: @Query public DatabaseNetwork netFromLangAndHome(long langID, long nearCityID, long nearRegionID, long nearCountryID)
   :outertype: NetworkDao

netFromLocAndHome
^^^^^^^^^^^^^^^^^

.. java:method:: @Query public DatabaseNetwork netFromLocAndHome(long fromCityID, long fromRegionID, long fromCountryID, long nearCityID, long nearRegionID, long nearCountryID)
   :outertype: NetworkDao

updateNetworks
^^^^^^^^^^^^^^

.. java:method:: @Update public void updateNetworks(DatabaseNetwork... networks)
   :outertype: NetworkDao

