.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Delete

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: java.util List

EventSubscriptionDao
====================

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface EventSubscriptionDao

   Created by Drew Gregory on 2/19/18.

Methods
-------
getEventUsers
^^^^^^^^^^^^^

.. java:method:: @Query public List<Long> getEventUsers(long eventId)
   :outertype: EventSubscriptionDao

getUserEventSubscriptions
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Query public List<Long> getUserEventSubscriptions(long userId)
   :outertype: EventSubscriptionDao

insertSubscriptions
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Insert public void insertSubscriptions(EventSubscription... subs)
   :outertype: EventSubscriptionDao

unsubscribeFromEvent
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Delete public void unsubscribeFromEvent(EventSubscription... eventSubscriptions)
   :outertype: EventSubscriptionDao

