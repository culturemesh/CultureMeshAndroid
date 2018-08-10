.. java:import:: android.content Context

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.os Bundle

.. java:import:: android.support.constraint ConstraintLayout

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget TextView

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: org.codethechange.culturemesh.models Network

.. java:import:: java.util ArrayList

.. java:import:: java.util HashMap

ListNetworksFragment
====================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class ListNetworksFragment extends Fragment implements NetworkSummaryAdapter.OnNetworkTapListener

   Fragment for displaying lists of clickable networks

Fields
------
SELECTED_USER
^^^^^^^^^^^^^

.. java:field:: static final String SELECTED_USER
   :outertype: ListNetworksFragment

   Key stored in the fragment's arguments and whose value is the ID of the user whose networks are to be displayed.

emptyText
^^^^^^^^^

.. java:field::  TextView emptyText
   :outertype: ListNetworksFragment

   Displays \ :java:ref:`R.string.no_networks`\  if there are no networks to display

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: ListNetworksFragment

   Queue for asynchronous tasks

root
^^^^

.. java:field::  View root
   :outertype: ListNetworksFragment

   Inflated user interface created by \ :java:ref:`ListNetworksFragment.onCreate(Bundle)`\

rv
^^

.. java:field::  RecyclerView rv
   :outertype: ListNetworksFragment

   Scrollable list of networks

Methods
-------
newInstance
^^^^^^^^^^^

.. java:method:: public static ListNetworksFragment newInstance(long selUser)
   :outertype: ListNetworksFragment

   Returns a new instance of this fragment for the given section number.

onCreateView
^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: ListNetworksFragment

   Setup the user interface to display the list of networks and populate that list with the result of calling \ :java:ref:`API.Get.userNetworks(RequestQueue,long,Response.Listener)`\ .

   :param inflater: Inflates the user interface specified in \ :java:ref:`R.layout.rv_container`\
   :param container: Parent of the generated hierarchy of user interface elements
   :param savedInstanceState: Saved state to restore
   :return: Inflated user interface

onItemClick
^^^^^^^^^^^

.. java:method:: @Override public void onItemClick(View v, Network network)
   :outertype: ListNetworksFragment

   This is the onClick() passed to NetworkSummaryAdapter. Thus, this is executed when the user taps on of the network card views. We want to view the tapped network in TimelineActivity.

   :param v: the CardView.
   :param network: The Network

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: ListNetworksFragment

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

