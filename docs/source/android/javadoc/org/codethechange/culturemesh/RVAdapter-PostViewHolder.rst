.. java:import:: android.content Context

.. java:import:: android.support.constraint ConstraintLayout

.. java:import:: android.support.constraint ConstraintSet

.. java:import:: android.support.v7.widget CardView

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget ImageView

.. java:import:: android.widget LinearLayout

.. java:import:: android.widget RelativeLayout

.. java:import:: android.widget TextView

.. java:import:: com.squareup.picasso Picasso

.. java:import:: com.culturemesh.models Event

.. java:import:: com.culturemesh.models FeedItem

.. java:import:: com.culturemesh.models Post

.. java:import:: com.culturemesh.models PostReply

.. java:import:: java.util HashSet

.. java:import:: java.util List

.. java:import:: java.util Set

RVAdapter.PostViewHolder
========================

.. java:package:: com.culturemesh
   :noindex:

.. java:type:: static class PostViewHolder extends RecyclerView.ViewHolder
   :outertype: RVAdapter

   Stores the \ :java:ref:`View`\  elements of each item in the displayed list. Instances of this class are linked to objects in \ :java:ref:`RVAdapter.netPosts`\  by \ :java:ref:`RVAdapter.onBindViewHolder(PostViewHolder,int)`\ , which fills the fields with content from the object.

Fields
------
comment1Layout
^^^^^^^^^^^^^^

.. java:field::  RelativeLayout comment1Layout
   :outertype: RVAdapter.PostViewHolder

   Layout within which the two displayed comments are defined

cv
^^

.. java:field::  CardView cv
   :outertype: RVAdapter.PostViewHolder

   The \ :java:ref:`View`\  for the displayed list item

eventDescription
^^^^^^^^^^^^^^^^

.. java:field::  TextView eventDescription
   :outertype: RVAdapter.PostViewHolder

   Description of the \ :java:ref:`Event`\

eventDetailsLL
^^^^^^^^^^^^^^

.. java:field::  LinearLayout eventDetailsLL
   :outertype: RVAdapter.PostViewHolder

   Layout within which the details section of the displayed list item is defined

eventLocation
^^^^^^^^^^^^^

.. java:field::  TextView eventLocation
   :outertype: RVAdapter.PostViewHolder

   Where the \ :java:ref:`Event`\  will take place

eventTime
^^^^^^^^^

.. java:field::  TextView eventTime
   :outertype: RVAdapter.PostViewHolder

   Time of the \ :java:ref:`Event`\

images
^^^^^^

.. java:field::  ImageView[] images
   :outertype: RVAdapter.PostViewHolder

   Array of all image displays

layout
^^^^^^

.. java:field::  ConstraintLayout layout
   :outertype: RVAdapter.PostViewHolder

   Layout within which the displayed list item is defined

personName
^^^^^^^^^^

.. java:field::  TextView personName
   :outertype: RVAdapter.PostViewHolder

   Text fields for both \ :java:ref:`Post`\  and \ :java:ref:`Event`\  information

personPhoto
^^^^^^^^^^^

.. java:field::  ImageView personPhoto
   :outertype: RVAdapter.PostViewHolder

   Display images with the displayed list item

post
^^^^

.. java:field::  boolean post
   :outertype: RVAdapter.PostViewHolder

   Whether this instance is configured to display the information for a \ :java:ref:`Post`\  or for a \ :java:ref:`Event`\ . \ ``true``\  if it is for a \ :java:ref:`Post`\

Constructors
------------
PostViewHolder
^^^^^^^^^^^^^^

.. java:constructor::  PostViewHolder(View itemView)
   :outertype: RVAdapter.PostViewHolder

   Initialize instance fields by retrieving UI elements by their IDs in the provided \ :java:ref:`View`\

   :param itemView: Canvas upon which the displayed list item is built. Should already have the needed fields and other elements.

Methods
-------
bind
^^^^

.. java:method:: public void bind(FeedItem item, OnItemClickListener listener)
   :outertype: RVAdapter.PostViewHolder

   Set the displayed list item's listener that handles clicks to that of the provided listener

   :param item: The clicked-on item which will be passed to the listener's \ :java:ref:`OnItemClickListener.onItemClick(FeedItem)`\ method when the item is clicked
   :param listener: Listener to handle all clicks on items in the list

hideEventViews
^^^^^^^^^^^^^^

.. java:method::  void hideEventViews()
   :outertype: RVAdapter.PostViewHolder

   This instance will display the information from a \ :java:ref:`Post`\ , so hide all the fields that describe \ :java:ref:`Event`\ s

hidePostViews
^^^^^^^^^^^^^

.. java:method::  void hidePostViews()
   :outertype: RVAdapter.PostViewHolder

   This instance will display the information from a \ :java:ref:`Event`\ , so hide all the fields that describe \ :java:ref:`Post`\ s

isPost
^^^^^^

.. java:method:: public boolean isPost()
   :outertype: RVAdapter.PostViewHolder

   Check whether the instance is displaying information for a \ :java:ref:`Post`\  or a \ :java:ref:`Event`\

   :return: \ ``true``\  if displaying information for a \ :java:ref:`Post`\ . \ ``false``\  if for an \ :java:ref:`Event`\

