.. java:import:: android.arch.persistence.room Entity

.. java:import:: android.arch.persistence.room Ignore

.. java:import:: android.arch.persistence.room PrimaryKey

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

PostReply
=========

.. java:package:: org.codethechange.culturemesh.models
   :noindex:

.. java:type:: @Entity public class PostReply implements Postable, Putable

   Created by Drew Gregory on 3/4/18.

Fields
------
author
^^^^^^

.. java:field:: @Ignore public User author
   :outertype: PostReply

id
^^

.. java:field:: @PrimaryKey public long id
   :outertype: PostReply

networkId
^^^^^^^^^

.. java:field:: public long networkId
   :outertype: PostReply

parentId
^^^^^^^^

.. java:field:: public long parentId
   :outertype: PostReply

replyDate
^^^^^^^^^

.. java:field:: public String replyDate
   :outertype: PostReply

replyText
^^^^^^^^^

.. java:field:: public String replyText
   :outertype: PostReply

userId
^^^^^^

.. java:field:: public long userId
   :outertype: PostReply

Constructors
------------
PostReply
^^^^^^^^^

.. java:constructor:: public PostReply(long id, long parentId, long userId, long networkId, String replyDate, String replyText)
   :outertype: PostReply

PostReply
^^^^^^^^^

.. java:constructor:: public PostReply(JSONObject replyObj) throws JSONException
   :outertype: PostReply

PostReply
^^^^^^^^^

.. java:constructor:: public PostReply()
   :outertype: PostReply

Methods
-------
getAuthor
^^^^^^^^^

.. java:method:: public User getAuthor()
   :outertype: PostReply

getPostJson
^^^^^^^^^^^

.. java:method:: public JSONObject getPostJson() throws JSONException
   :outertype: PostReply

getPutJson
^^^^^^^^^^

.. java:method:: public JSONObject getPutJson() throws JSONException
   :outertype: PostReply

toJSON
^^^^^^

.. java:method:: public JSONObject toJSON() throws JSONException
   :outertype: PostReply

   Generate a JSON describing the object. The JSON will conform to the following format:

   .. parsed-literal::

      {
                     "id_parent": 0,
                     "id_user": 0,
                     "id_network": 0,
                     "reply_text": "string"
                 }

   The resulting object is suitable for use with the \ ``/post/{postId}/reply``\  POST or PUT endpoints.

   :throws JSONException: Unclear when this would be thrown
   :return: JSON representation of the object

