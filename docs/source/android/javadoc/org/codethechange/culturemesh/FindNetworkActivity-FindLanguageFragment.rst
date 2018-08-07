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

.. java:import:: org.codethechange.culturemesh.models Language

.. java:import:: org.codethechange.culturemesh.models Location

.. java:import:: org.codethechange.culturemesh.models Network

FindNetworkActivity.FindLanguageFragment
========================================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public static class FindLanguageFragment extends Fragment implements SearchView.OnQueryTextListener
   :outertype: FindNetworkActivity

   The fragment for finding language networks.

Constructors
------------
FindLanguageFragment
^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public FindLanguageFragment()
   :outertype: FindNetworkActivity.FindLanguageFragment

   Empty constructor that does nothing.

Methods
-------
newInstance
^^^^^^^^^^^

.. java:method:: public static FindLanguageFragment newInstance(int sectionNumber)
   :outertype: FindNetworkActivity.FindLanguageFragment

   Returns a new instance of this fragment for the given section number.

onCreateView
^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: FindNetworkActivity.FindLanguageFragment

   Create the displayed fragment.

   :param inflater: Creates the user interface from \ :java:ref:`R.layout.fragment_find_language`\
   :param container: TODO: What is this?
   :param savedInstanceState: Previous state that is not used.
   :return: The inflated view to display.

onQueryTextChange
^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onQueryTextChange(String newText)
   :outertype: FindNetworkActivity.FindLanguageFragment

   When the query text changes, do nothing to avoid expensive API calls.

   :param newText: The updated query text.
   :return: Always returns \ ``true``\ .

onQueryTextSubmit
^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onQueryTextSubmit(String query)
   :outertype: FindNetworkActivity.FindLanguageFragment

   When the user submits a query, call \ :java:ref:`FindLanguageFragment.search()`\

   :param query: Query text that is discarded.
   :return: Always returns \ ``true``\

search
^^^^^^

.. java:method:: public void search()
   :outertype: FindNetworkActivity.FindLanguageFragment

   Use \ :java:ref:`API.Get.autocompleteLanguage(RequestQueue,String,Response.Listener)`\  to get autocomplete results for the user's query. Pass those results to \ :java:ref:`FindLanguageFragment.adapter`\ , which will then populate \ :java:ref:`FindLanguageFragment.searchList`\

