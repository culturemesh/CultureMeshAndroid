.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: org.codethechange.culturemesh.models Country

.. java:import:: java.util List

CountryDao
==========

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface CountryDao

   Created by Drew Gregory on 2/23/18.

Methods
-------
autoCompleteCountries
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Query public List<Country> autoCompleteCountries(String query)
   :outertype: CountryDao

getCountry
^^^^^^^^^^

.. java:method:: @Query public Country getCountry(long id)
   :outertype: CountryDao

insertCountries
^^^^^^^^^^^^^^^

.. java:method:: @Insert public void insertCountries(Country... countries)
   :outertype: CountryDao

