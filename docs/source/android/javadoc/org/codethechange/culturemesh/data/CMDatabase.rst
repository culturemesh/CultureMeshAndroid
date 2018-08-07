.. java:import:: android.arch.persistence.room Database

.. java:import:: android.arch.persistence.room RoomDatabase

.. java:import:: org.codethechange.culturemesh.models City

.. java:import:: org.codethechange.culturemesh.models Country

.. java:import:: org.codethechange.culturemesh.models DatabaseNetwork

.. java:import:: org.codethechange.culturemesh.models Event

.. java:import:: org.codethechange.culturemesh.models Language

.. java:import:: org.codethechange.culturemesh.models Place

.. java:import:: org.codethechange.culturemesh.models Post

.. java:import:: org.codethechange.culturemesh.models PostReply

.. java:import:: org.codethechange.culturemesh.models Region

.. java:import:: org.codethechange.culturemesh.models User

CMDatabase
==========

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Database public abstract class CMDatabase extends RoomDatabase

   Created by Drew Gregory on 2/19/18. TODO: Check out LiveData: https://developer.android.com/reference/android/arch/lifecycle/LiveData.html

Methods
-------
cityDao
^^^^^^^

.. java:method:: public abstract CityDao cityDao()
   :outertype: CMDatabase

countryDao
^^^^^^^^^^

.. java:method:: public abstract CountryDao countryDao()
   :outertype: CMDatabase

eventDao
^^^^^^^^

.. java:method:: public abstract EventDao eventDao()
   :outertype: CMDatabase

eventSubscriptionDao
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public abstract EventSubscriptionDao eventSubscriptionDao()
   :outertype: CMDatabase

languageDao
^^^^^^^^^^^

.. java:method:: public abstract LanguageDao languageDao()
   :outertype: CMDatabase

networkDao
^^^^^^^^^^

.. java:method:: public abstract NetworkDao networkDao()
   :outertype: CMDatabase

networkSubscriptionDao
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public abstract NetworkSubscriptionDao networkSubscriptionDao()
   :outertype: CMDatabase

postDao
^^^^^^^

.. java:method:: public abstract PostDao postDao()
   :outertype: CMDatabase

postReplyDao
^^^^^^^^^^^^

.. java:method:: public abstract PostReplyDao postReplyDao()
   :outertype: CMDatabase

regionDao
^^^^^^^^^

.. java:method:: public abstract RegionDao regionDao()
   :outertype: CMDatabase

userDao
^^^^^^^

.. java:method:: public abstract UserDao userDao()
   :outertype: CMDatabase

