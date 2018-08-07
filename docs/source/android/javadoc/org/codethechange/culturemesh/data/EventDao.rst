.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: org.codethechange.culturemesh.models Event

.. java:import:: java.util List

EventDao
========

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface EventDao

   Created by Drew Gregory on 2/19/18.

Methods
-------
addEvent
^^^^^^^^

.. java:method:: @Insert public void addEvent(Event... events)
   :outertype: EventDao

getEvent
^^^^^^^^

.. java:method:: @Query public Event getEvent(long id)
   :outertype: EventDao

getNetworkEvents
^^^^^^^^^^^^^^^^

.. java:method:: @Query public List<Event> getNetworkEvents(long id)
   :outertype: EventDao

