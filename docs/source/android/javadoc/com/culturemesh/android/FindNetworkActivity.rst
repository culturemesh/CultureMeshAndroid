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

FindNetworkActivity
===================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public class FindNetworkActivity extends DrawerActivity

Fields
------
REQUEST_NEW_NEAR_LOCATION
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: public final int REQUEST_NEW_NEAR_LOCATION
   :outertype: FindNetworkActivity

near
^^^^

.. java:field:: static Location near
   :outertype: FindNetworkActivity

   The user's chosen \ :java:ref:`Location`\  they are near

queue
^^^^^

.. java:field:: static RequestQueue queue
   :outertype: FindNetworkActivity

   Queue to hold asynchronous tasks

Methods
-------
onActivityResult
^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void onActivityResult(int requestCode, int resultCode, Intent data)
   :outertype: FindNetworkActivity

   When the user has chosen a near location using \ :java:ref:`ChooseNearLocationActivity`\ , this method is called by the \ :java:ref:`Intent`\  that launched the near location chooser with the result of the user's selection. If they did indeed choose a location, that location is saved and the button text is updated to reflect the location's name.

   :param requestCode: Status code that indicates a location was chosen if it equals \ :java:ref:`ChooseNearLocationActivity.RESULT_OK`\
   :param resultCode: {@inheritDoc}
   :param data: Passed to superclass, but the value associated with \ :java:ref:`ChooseNearLocationActivity.CHOSEN_PLACE`\ , which should be the location the user chose, is extracted if \ ``requestCode``\  indicates they made a choice

onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: FindNetworkActivity

   Setup the activity based on content specified in \ :java:ref:`R.layout.activity_find_network`\ . See code comments for details on implementation.

   :param savedInstanceState: Previous state that is passed to superclass.

onCreateOptionsMenu
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onCreateOptionsMenu(Menu menu)
   :outertype: FindNetworkActivity

   Inflate the menu; this adds items to the action bar if it is present.

   :param menu: Menu to create
   :return: Always returns \ ``true``\

onOptionsItemSelected
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onOptionsItemSelected(MenuItem item)
   :outertype: FindNetworkActivity

   Handles clicks to the action bar.

   :param item: {@inheritDoc}
   :return: \ ``true``\  if the item ID is that of \ :java:ref:`R.id.action_settings`\ . Otherwise, superclass \ ``onOptionsItemSelected``\  is called and the resulting value is returned.

onResume
^^^^^^^^

.. java:method:: @Override protected void onResume()
   :outertype: FindNetworkActivity

   {@inheritDoc}

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: FindNetworkActivity

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

