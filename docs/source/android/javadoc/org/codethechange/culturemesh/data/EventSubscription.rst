.. java:import:: android.arch.persistence.room Entity

.. java:import:: android.arch.persistence.room PrimaryKey

EventSubscription
=================

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Entity public class EventSubscription

   Created by Drew Gregory on 2/19/18. This entity is to keep track of user subscriptions to events.

Fields
------
eventId
^^^^^^^

.. java:field:: public long eventId
   :outertype: EventSubscription

id
^^

.. java:field:: @PrimaryKey public long id
   :outertype: EventSubscription

userId
^^^^^^

.. java:field:: public long userId
   :outertype: EventSubscription

Constructors
------------
EventSubscription
^^^^^^^^^^^^^^^^^

.. java:constructor:: public EventSubscription()
   :outertype: EventSubscription

EventSubscription
^^^^^^^^^^^^^^^^^

.. java:constructor:: public EventSubscription(long userId, long eventId)
   :outertype: EventSubscription

