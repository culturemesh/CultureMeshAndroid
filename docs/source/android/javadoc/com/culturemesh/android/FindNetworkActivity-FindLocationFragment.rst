.. java:import:: android.app AlertDialog

.. java:import:: android.app SearchManager

.. java:import:: android.content Context

.. java:import:: android.content DialogInterface

.. java:import:: android.content Intent

.. java:import:: android.support.design.widget TabLayout

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.support.v4.app FragmentManager

.. java:import:: android.support.v4.app FragmentPagerAdapter

.. java:import:: android.support.v4.view ViewPager

.. java:import:: android.os Bundle

.. java:import:: android.view LayoutInflater

.. java:import:: android.view Menu

.. java:import:: android.view MenuItem

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget AdapterView

.. java:import:: android.widget Button

.. java:import:: android.widget ListView

.. java:import:: android.widget SearchView

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: java.util List

.. java:import:: com.culturemesh.android.models Language

.. java:import:: com.culturemesh.android.models Location

.. java:import:: com.culturemesh.android.models Network

FindNetworkActivity.FindLocationFragment
========================================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public static class FindLocationFragment extends Fragment implements SearchView.OnQueryTextListener
   :outertype: FindNetworkActivity

   The fragment for finding the from location.

Constructors
------------
FindLocationFragment
^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public FindLocationFragment()
   :outertype: FindNetworkActivity.FindLocationFragment

   Empty constructor that does nothing.

Methods
-------
newInstance
^^^^^^^^^^^

.. java:method:: public static FindLocationFragment newInstance(int sectionNumber)
   :outertype: FindNetworkActivity.FindLocationFragment

   Returns a new instance of this fragment for the given section number.

onCreateView
^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: FindNetworkActivity.FindLocationFragment

   Create the displayed fragment.

   :param inflater: Creates the user interface from \ :java:ref:`R.layout.fragment_find_location`\
   :param container: Parent container to attach inflated \ :java:ref:`View`\  to
   :param savedInstanceState: Previous state that is not used.
   :return: The inflated view to display.

onQueryTextChange
^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onQueryTextChange(String newText)
   :outertype: FindNetworkActivity.FindLocationFragment

   When the query text changes, do nothing to avoid expensive API calls.

   :param newText: The updated query text.
   :return: Always returns \ ``true``\ .

onQueryTextSubmit
^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onQueryTextSubmit(String query)
   :outertype: FindNetworkActivity.FindLocationFragment

   When the user submits a query, call \ :java:ref:`FindLocationFragment.search()`\

   :param query: Query text that is discarded.
   :return: Always returns \ ``true``\

search
^^^^^^

.. java:method:: public void search()
   :outertype: FindNetworkActivity.FindLocationFragment

   Use \ :java:ref:`API.Get.autocompletePlace(RequestQueue,String,Response.Listener)`\  to get autocomplete results for the user's query. Pass those results to \ :java:ref:`FindLocationFragment.adapter`\ , which will then populate \ :java:ref:`FindLocationFragment.searchList`\

