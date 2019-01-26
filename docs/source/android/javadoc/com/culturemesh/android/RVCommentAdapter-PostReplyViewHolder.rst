.. java:import:: android.content Context

.. java:import:: android.graphics Color

.. java:import:: android.support.constraint ConstraintLayout

.. java:import:: android.support.constraint ConstraintSet

.. java:import:: android.support.v7.widget CardView

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.util Log

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget ImageView

.. java:import:: android.widget LinearLayout

.. java:import:: android.widget RelativeLayout

.. java:import:: android.widget TextView

.. java:import:: com.squareup.picasso Picasso

.. java:import:: com.culturemesh.android.models Event

.. java:import:: com.culturemesh.android.models FeedItem

.. java:import:: com.culturemesh.android.models Post

.. java:import:: com.culturemesh.android.models PostReply

.. java:import:: java.util List

RVCommentAdapter.PostReplyViewHolder
====================================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: static class PostReplyViewHolder extends RecyclerView.ViewHolder
   :outertype: RVCommentAdapter

   Holder for the parts of each \ :java:ref:`View`\  in the list

Fields
------
cv
^^

.. java:field::  CardView cv
   :outertype: RVCommentAdapter.PostReplyViewHolder

   The \ :java:ref:`View`\  to display a single list item

images
^^^^^^

.. java:field::  ImageView[] images
   :outertype: RVCommentAdapter.PostReplyViewHolder

   Array of image components associated with a list item

layout
^^^^^^

.. java:field::  ConstraintLayout layout
   :outertype: RVCommentAdapter.PostReplyViewHolder

   Layout within which the list item components are arranged

personName
^^^^^^^^^^

.. java:field::  TextView personName
   :outertype: RVCommentAdapter.PostReplyViewHolder

   Textual components of the display for a single list item

personPhoto
^^^^^^^^^^^

.. java:field::  ImageView personPhoto
   :outertype: RVCommentAdapter.PostReplyViewHolder

   Image components of the display for a single list item

reply
^^^^^

.. java:field::  boolean reply
   :outertype: RVCommentAdapter.PostReplyViewHolder

Constructors
------------
PostReplyViewHolder
^^^^^^^^^^^^^^^^^^^

.. java:constructor::  PostReplyViewHolder(View itemView)
   :outertype: RVCommentAdapter.PostReplyViewHolder

   Instantiate instance fields with \ :java:ref:`View`\ s using \ :java:ref:`View.findViewById(int)`\

   :param itemView: Item display whose fields are stored in instance fields

Methods
-------
bind
^^^^

.. java:method:: public void bind(PostReply item, OnItemClickListener listener)
   :outertype: RVCommentAdapter.PostReplyViewHolder

   Attach a listener to an item in the displayed list

   :param item: Item in the list to bind the listener to
   :param listener: Listener to bind to the list item

isPostReply
^^^^^^^^^^^

.. java:method:: public boolean isPostReply()
   :outertype: RVCommentAdapter.PostReplyViewHolder

