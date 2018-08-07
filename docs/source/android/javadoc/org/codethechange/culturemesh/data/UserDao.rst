.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: org.codethechange.culturemesh.models User

UserDao
=======

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface UserDao

   Created by Drew Gregory on 2/19/18.

Methods
-------
addUser
^^^^^^^

.. java:method:: @Insert public void addUser(User user)
   :outertype: UserDao

getUser
^^^^^^^

.. java:method:: @Query public User getUser(long id)
   :outertype: UserDao

