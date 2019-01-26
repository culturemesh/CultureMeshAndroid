.. java:import:: java.util List

FeedItem
========

.. java:package:: com.culturemesh.android.models
   :noindex:

.. java:type:: public class FeedItem

   Superclass for Posts and Events that mandates they both have a list of PostReply objects that can be displayed in a feed.

Fields
------
comments
^^^^^^^^

.. java:field:: public List<PostReply> comments
   :outertype: FeedItem

   This list of PostReplies will be where we store the comments for each post.

