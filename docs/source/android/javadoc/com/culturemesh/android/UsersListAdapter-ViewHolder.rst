.. java:import:: android.content Context

.. java:import:: android.content Intent

.. java:import:: android.support.annotation NonNull

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget ImageView

.. java:import:: android.widget TextView

.. java:import:: com.squareup.picasso Picasso

.. java:import:: com.culturemesh.android.models User

.. java:import:: java.util ArrayList

UsersListAdapter.ViewHolder
===========================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type::  class ViewHolder extends RecyclerView.ViewHolder
   :outertype: UsersListAdapter

   Holder of UI elements that compose each element of the displayed list

Fields
------
fullName
^^^^^^^^

.. java:field::  TextView fullName
   :outertype: UsersListAdapter.ViewHolder

   \ :java:ref:`User`\ 's name

profilePicture
^^^^^^^^^^^^^^

.. java:field::  ImageView profilePicture
   :outertype: UsersListAdapter.ViewHolder

   \ :java:ref:`User`\ 's profile picture

Constructors
------------
ViewHolder
^^^^^^^^^^

.. java:constructor::  ViewHolder(View v)
   :outertype: UsersListAdapter.ViewHolder

   Initialize instance fields with fields in \ ``v``\  and set the listener for clicks to open a more detailed view of the profile in \ :java:ref:`ViewProfileActivity`\

   :param v: \ :java:ref:`View`\  to use to display the list item

