.. java:import:: android.arch.persistence.room Entity

.. java:import:: android.arch.persistence.room PrimaryKey

.. java:import:: android.support.annotation NonNull

.. java:import:: java.math BigInteger

NetworkSubscription
===================

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Entity public class NetworkSubscription

   Created by Drew Gregory on 2/19/18. This database will allow us to get a list of users subscribed to a network and a list of networks that a user is subscribed to.

Fields
------
id
^^

.. java:field:: @PrimaryKey @NonNull public String id
   :outertype: NetworkSubscription

networkId
^^^^^^^^^

.. java:field:: public long networkId
   :outertype: NetworkSubscription

userId
^^^^^^

.. java:field:: public long userId
   :outertype: NetworkSubscription

Constructors
------------
NetworkSubscription
^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public NetworkSubscription(long userId, long networkId)
   :outertype: NetworkSubscription

