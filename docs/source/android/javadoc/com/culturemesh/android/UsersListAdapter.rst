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

UsersListAdapter
================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder>

   This Adapter is used for viewing the subscribed users of a network.

Fields
------
context
^^^^^^^

.. java:field::  Context context
   :outertype: UsersListAdapter

   \ :java:ref:`Context`\  in which the list is being displayed

Constructors
------------
UsersListAdapter
^^^^^^^^^^^^^^^^

.. java:constructor:: public UsersListAdapter(Context context, ArrayList<User> users)
   :outertype: UsersListAdapter

   Create a new object by instantiating instance fields with parameters

   :param context: \ :java:ref:`Context`\  in which the list is displayed
   :param users: List of \ :java:ref:`User`\ s to display in the list

Methods
-------
getItemCount
^^^^^^^^^^^^

.. java:method:: @Override public int getItemCount()
   :outertype: UsersListAdapter

   Get the number of items in the list of objects to display

   :return: Number of items in list to display

getUsers
^^^^^^^^

.. java:method:: public ArrayList<User> getUsers()
   :outertype: UsersListAdapter

   Get the list of objects to display

   :return: List of objects represented in list

onBindViewHolder
^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onBindViewHolder(ViewHolder holder, int position)
   :outertype: UsersListAdapter

   Fill the name and profile picture fields of \ ``holder``\  with the contents of an item in \ :java:ref:`UsersListAdapter.users`\ .

   :param holder: \ :java:ref:`ViewHolder`\  whose fields to fill with information
   :param position: Index of item in list of users to use as source of information for filling

onCreateViewHolder
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
   :outertype: UsersListAdapter

   Create a new \ :java:ref:`UsersListAdapter.ViewHolder`\  from a \ :java:ref:`View`\  inflated from \ :java:ref:`R.layout.user_list_item`\  and with parent \ ``parent``\

   :param parent: Parent for the \ :java:ref:`View`\  used to create the new \ :java:ref:`UsersListAdapter`\
   :param viewType: Not used.
   :return: The created \ :java:ref:`UsersListAdapter.ViewHolder`\

