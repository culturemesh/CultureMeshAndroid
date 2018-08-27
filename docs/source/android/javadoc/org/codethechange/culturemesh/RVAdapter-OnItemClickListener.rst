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

RVAdapter.OnItemClickListener
=============================

.. java:package:: com.culturemesh
   :noindex:

.. java:type:: public interface OnItemClickListener
   :outertype: RVAdapter

   Interface listeners for clicks on items must implement

Methods
-------
onItemClick
^^^^^^^^^^^

.. java:method::  void onItemClick(FeedItem item)
   :outertype: RVAdapter.OnItemClickListener

   Handle a click on the provided item

   :param item: Item that was clicked on

