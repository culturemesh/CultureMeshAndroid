.. java:import:: android.content Context

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

.. java:import:: org.codethechange.culturemesh.models Event

.. java:import:: org.codethechange.culturemesh.models FeedItem

.. java:import:: org.codethechange.culturemesh.models Post

.. java:import:: org.codethechange.culturemesh.models PostReply

.. java:import:: java.util List

RVAdapter.PostViewHolder
========================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: static class PostViewHolder extends RecyclerView.ViewHolder
   :outertype: RVAdapter

Fields
------
comment1Layout
^^^^^^^^^^^^^^

.. java:field::  RelativeLayout comment1Layout
   :outertype: RVAdapter.PostViewHolder

cv
^^

.. java:field::  CardView cv
   :outertype: RVAdapter.PostViewHolder

eventDescription
^^^^^^^^^^^^^^^^

.. java:field::  TextView eventDescription
   :outertype: RVAdapter.PostViewHolder

eventDetailsLL
^^^^^^^^^^^^^^

.. java:field::  LinearLayout eventDetailsLL
   :outertype: RVAdapter.PostViewHolder

eventLocation
^^^^^^^^^^^^^

.. java:field::  TextView eventLocation
   :outertype: RVAdapter.PostViewHolder

eventTime
^^^^^^^^^

.. java:field::  TextView eventTime
   :outertype: RVAdapter.PostViewHolder

images
^^^^^^

.. java:field::  ImageView[] images
   :outertype: RVAdapter.PostViewHolder

layout
^^^^^^

.. java:field::  ConstraintLayout layout
   :outertype: RVAdapter.PostViewHolder

personName
^^^^^^^^^^

.. java:field::  TextView personName
   :outertype: RVAdapter.PostViewHolder

personPhoto
^^^^^^^^^^^

.. java:field::  ImageView personPhoto
   :outertype: RVAdapter.PostViewHolder

post
^^^^

.. java:field::  boolean post
   :outertype: RVAdapter.PostViewHolder

Constructors
------------
PostViewHolder
^^^^^^^^^^^^^^

.. java:constructor::  PostViewHolder(View itemView)
   :outertype: RVAdapter.PostViewHolder

Methods
-------
bind
^^^^

.. java:method:: public void bind(FeedItem item, OnItemClickListener listener)
   :outertype: RVAdapter.PostViewHolder

hideEventViews
^^^^^^^^^^^^^^

.. java:method::  void hideEventViews()
   :outertype: RVAdapter.PostViewHolder

hidePostViews
^^^^^^^^^^^^^

.. java:method::  void hidePostViews()
   :outertype: RVAdapter.PostViewHolder

isPost
^^^^^^

.. java:method:: public boolean isPost()
   :outertype: RVAdapter.PostViewHolder

