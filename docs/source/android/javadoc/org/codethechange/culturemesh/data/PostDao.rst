.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: org.codethechange.culturemesh.models Post

.. java:import:: java.util ArrayList

.. java:import:: java.util List

PostDao
=======

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface PostDao

   Created by Drew Gregory on 2/19/18.

Methods
-------
getNetworkPosts
^^^^^^^^^^^^^^^

.. java:method:: @Query public List<Post> getNetworkPosts(int id)
   :outertype: PostDao

getPost
^^^^^^^

.. java:method:: @Query public Post getPost(int pId)
   :outertype: PostDao

getUserPosts
^^^^^^^^^^^^

.. java:method:: @Query public List<Post> getUserPosts(long id)
   :outertype: PostDao

insertPosts
^^^^^^^^^^^

.. java:method:: @Insert public void insertPosts(Post... posts)
   :outertype: PostDao

