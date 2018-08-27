.. java:import:: android.arch.persistence.room Ignore

.. java:import:: java.util List

FeedItem
========

.. java:package:: com.culturemesh.models
   :noindex:

.. java:type:: public class FeedItem

   Superclass for Posts and Events that mandates they both have a list of PostReply objects that can be displayed in a feed.

Fields
------
comments
^^^^^^^^

.. java:field:: @Ignore public List<PostReply> comments
   :outertype: FeedItem

   This list of PostReplies will be where we store the comments for each post.

