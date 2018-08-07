.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: org.codethechange.culturemesh.models Country

.. java:import:: org.codethechange.culturemesh.models Region

.. java:import:: java.util List

RegionDao
=========

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface RegionDao

   Created by Drew Gregory on 2/23/18.

Methods
-------
autoCompleteRegions
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Query public List<Region> autoCompleteRegions(String query)
   :outertype: RegionDao

getRegion
^^^^^^^^^

.. java:method:: @Query public Region getRegion(long id)
   :outertype: RegionDao

insertRegions
^^^^^^^^^^^^^

.. java:method:: @Insert public void insertRegions(Region... Region)
   :outertype: RegionDao

