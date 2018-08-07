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

.. java:import:: org.codethechange.culturemesh.models User

.. java:import:: java.util ArrayList

UsersListAdapter
================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder>

   Created by Drew Gregory on 03/30/18 This Adapter is used for viewing the subscribed users of a network.

Fields
------
context
^^^^^^^

.. java:field::  Context context
   :outertype: UsersListAdapter

Constructors
------------
UsersListAdapter
^^^^^^^^^^^^^^^^

.. java:constructor:: public UsersListAdapter(Context context, ArrayList<User> users)
   :outertype: UsersListAdapter

Methods
-------
getItemCount
^^^^^^^^^^^^

.. java:method:: @Override public int getItemCount()
   :outertype: UsersListAdapter

getUsers
^^^^^^^^

.. java:method:: public ArrayList<User> getUsers()
   :outertype: UsersListAdapter

onBindViewHolder
^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onBindViewHolder(ViewHolder holder, int position)
   :outertype: UsersListAdapter

onCreateViewHolder
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
   :outertype: UsersListAdapter

