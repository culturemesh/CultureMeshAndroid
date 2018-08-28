.. java:import:: android.content Intent

.. java:import:: android.os Bundle

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.util Log

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget TextView

.. java:import:: android.widget Toast

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: com.culturemesh.android.models Event

.. java:import:: com.culturemesh.android.models FeedItem

.. java:import:: com.culturemesh.android.models Post

.. java:import:: java.util ArrayList

ListUserEventsFragment
======================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public class ListUserEventsFragment extends Fragment implements RVAdapter.OnItemClickListener

   This fragment lists the the events a user is subscribed to. It is used in ViewProfileActivity.

Fields
------
emptyText
^^^^^^^^^

.. java:field::  TextView emptyText
   :outertype: ListUserEventsFragment

   Text field that displays \ :java:ref:`R.string.no_events`\  if there are no events to display

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: ListUserEventsFragment

   Queue for asynchronous tasks

rv
^^

.. java:field::  RecyclerView rv
   :outertype: ListUserEventsFragment

   Scrollable list of events.

Methods
-------
newInstance
^^^^^^^^^^^

.. java:method:: public static ListUserEventsFragment newInstance(long selUser)
   :outertype: ListUserEventsFragment

   Returns a new instance of this fragment for the given section number.

onCreateView
^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: ListUserEventsFragment

   Setup the user interface to display the list of events and populate that list with the result of calling \ :java:ref:`API.Get.userEvents(RequestQueue,long,String,Response.Listener)`\ .

   :param inflater: Inflates the user interface specified in \ :java:ref:`R.layout.rv_container`\
   :param container: Parent of the generated hierarchy of user interface elements
   :param savedInstanceState: Saved state to restore
   :return: Inflated user interface

onItemClick
^^^^^^^^^^^

.. java:method:: @Override public void onItemClick(FeedItem item)
   :outertype: ListUserEventsFragment

   When an item is clicked, if it is a \ :java:ref:`Post`\ , the user is sent to a screen to view the post in more detail, including comments. If the item is an \ :java:ref:`Event`\ , no action is taken.

   :param item: The item that was clicked

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: ListUserEventsFragment

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

