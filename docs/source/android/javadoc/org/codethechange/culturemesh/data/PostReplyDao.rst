.. java:import:: android.arch.persistence.room Dao

.. java:import:: android.arch.persistence.room Insert

.. java:import:: android.arch.persistence.room OnConflictStrategy

.. java:import:: android.arch.persistence.room Query

.. java:import:: org.codethechange.culturemesh.models PostReply

.. java:import:: java.util List

PostReplyDao
============

.. java:package:: org.codethechange.culturemesh.data
   :noindex:

.. java:type:: @Dao public interface PostReplyDao

   Created by Drew Gregory on 3/4/18.

Methods
-------
getPostReplies
^^^^^^^^^^^^^^

.. java:method:: @Query public List<PostReply> getPostReplies(long pId)
   :outertype: PostReplyDao

getPostReply
^^^^^^^^^^^^

.. java:method:: @Query public PostReply getPostReply(long pId)
   :outertype: PostReplyDao

insertPostReplies
^^^^^^^^^^^^^^^^^

.. java:method:: @Insert public void insertPostReplies(PostReply... posts)
   :outertype: PostReplyDao

