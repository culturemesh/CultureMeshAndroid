.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: org.codethechange.culturemesh.models Language

LanguageDao
===========

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface LanguageDao

   Created by Drew Gregory on 2/22/18.

Methods
-------
getLanguage
^^^^^^^^^^^

.. java:method:: @Query public Language getLanguage(long id)
   :outertype: LanguageDao

insertLanguages
^^^^^^^^^^^^^^^

.. java:method:: @Insert public void insertLanguages(Language... Language)
   :outertype: LanguageDao

