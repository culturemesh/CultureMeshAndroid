.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Delete

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: java.util List

NetworkSubscriptionDao
======================

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface NetworkSubscriptionDao

   Created by Drew Gregory on 2/19/18. This database will allow us to get a list of users subscribed to a network and a list of networks that a user is subscribed to.

Methods
-------
deleteNetworkSubscriptions
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Delete public void deleteNetworkSubscriptions(NetworkSubscription... networkSubscriptions)
   :outertype: NetworkSubscriptionDao

getNetworkUsers
^^^^^^^^^^^^^^^

.. java:method:: @Query public List<Long> getNetworkUsers(long networkId)
   :outertype: NetworkSubscriptionDao

getUserNetworks
^^^^^^^^^^^^^^^

.. java:method:: @Query public List<Long> getUserNetworks(long userId)
   :outertype: NetworkSubscriptionDao

insertSubscriptions
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Insert public void insertSubscriptions(NetworkSubscription... subs)
   :outertype: NetworkSubscriptionDao

