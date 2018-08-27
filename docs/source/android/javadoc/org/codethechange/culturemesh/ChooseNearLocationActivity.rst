.. java:import:: android.app SearchManager

.. java:import:: android.content Context

.. java:import:: android.content Intent

.. java:import:: android.os Bundle

.. java:import:: android.support.v7.app AppCompatActivity

.. java:import:: android.support.v7.widget Toolbar

.. java:import:: android.view View

.. java:import:: android.widget AdapterView

.. java:import:: android.widget ListView

.. java:import:: android.widget SearchView

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: com.culturemesh.models Location

.. java:import:: java.util List

ChooseNearLocationActivity
==========================

.. java:package:: com.culturemesh
   :noindex:

.. java:type:: public class ChooseNearLocationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener

   This screen let's the user choose where they live now. This is used by \ :java:ref:`FindNetworkActivity`\  to restrict displayed networks to those with a \ ``near``\  that matches where the user lives.

Fields
------
CHOSEN_PLACE
^^^^^^^^^^^^

.. java:field:: public static final String CHOSEN_PLACE
   :outertype: ChooseNearLocationActivity

   Identifier for the \ :java:ref:`Intent`\  whose value is the \ :java:ref:`Location`\  the user chose

RESULT_OK
^^^^^^^^^

.. java:field:: public static final int RESULT_OK
   :outertype: ChooseNearLocationActivity

   Result code to signal via the \ :java:ref:`Intent`\  that the user successfully chose a \ :java:ref:`Location`\

Methods
-------
onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: ChooseNearLocationActivity

   Setup the activity. Also initializes the \ :java:ref:`com.android.volley.RequestQueue`\ , the adapter that populates the list of results, and the listener that handles clicks on items in the results list

   :param savedInstanceState: Previous state that is passed through to superclass

onQueryTextChange
^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onQueryTextChange(String newText)
   :outertype: ChooseNearLocationActivity

   Whenever the query text changes, do nothing because sending network requests every time is unnecessary.

   :param newText: The updated query text
   :return: Always returns \ ``true``\

onQueryTextSubmit
^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onQueryTextSubmit(String query)
   :outertype: ChooseNearLocationActivity

   When the user submits their query, \ :java:ref:`ChooseNearLocationActivity.search()`\  is run to populated the results with matching \ :java:ref:`Location`\ s

   :param query: User's query. Not used.
   :return: Always returns \ ``true``\

search
^^^^^^

.. java:method:: public void search()
   :outertype: ChooseNearLocationActivity

   Get the query present in the \ :java:ref:`ChooseNearLocationActivity.searchView`\  and pass it to the server via \ :java:ref:`API.Get.autocompletePlace(RequestQueue,String,Response.Listener)`\  to get a list of matching \ :java:ref:`Location`\ s. These are used to populate the \ :java:ref:`ChooseNearLocationActivity.adapter`\ .

