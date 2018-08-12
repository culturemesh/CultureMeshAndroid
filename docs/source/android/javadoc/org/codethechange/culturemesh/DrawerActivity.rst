.. java:import:: android.app Activity

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.content.res Configuration

.. java:import:: android.os Bundle

.. java:import:: android.support.design.widget NavigationView

.. java:import:: android.support.v4.view GravityCompat

.. java:import:: android.support.v4.widget DrawerLayout

.. java:import:: android.support.v7.app ActionBarDrawerToggle

.. java:import:: android.support.v7.app AppCompatActivity

.. java:import:: android.support.v7.widget Toolbar

.. java:import:: android.text SpannableStringBuilder

.. java:import:: android.text.style RelativeSizeSpan

.. java:import:: android.util Log

.. java:import:: android.util SparseArray

.. java:import:: android.view Menu

.. java:import:: android.view MenuItem

.. java:import:: android.view SubMenu

.. java:import:: android.view View

.. java:import:: android.widget Button

.. java:import:: android.widget FrameLayout

.. java:import:: android.widget ImageView

.. java:import:: android.widget TextView

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: com.squareup.picasso Picasso

.. java:import:: org.codethechange.culturemesh.models Network

.. java:import:: org.codethechange.culturemesh.models User

.. java:import:: java.util ArrayList

.. java:import:: java.util HashSet

.. java:import:: java.util Set

DrawerActivity
==============

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener

   Superclass for all Activities that have a navigation drawer

Fields
------
currentUser
^^^^^^^^^^^

.. java:field:: protected long currentUser
   :outertype: DrawerActivity

   ID of the current \ :java:ref:`User`\

frameLayout
^^^^^^^^^^^

.. java:field:: protected FrameLayout frameLayout
   :outertype: DrawerActivity

   Parent for the drawer activity

fullLayout
^^^^^^^^^^

.. java:field:: protected DrawerLayout fullLayout
   :outertype: DrawerActivity

   The inflated user interface for the activity with the drawer

mDrawerLayout
^^^^^^^^^^^^^

.. java:field:: protected DrawerLayout mDrawerLayout
   :outertype: DrawerActivity

   User interface for the drawer itself

mDrawerToggle
^^^^^^^^^^^^^

.. java:field:: protected ActionBarDrawerToggle mDrawerToggle
   :outertype: DrawerActivity

   Toggles whether the drawer is visible

mToolbar
^^^^^^^^

.. java:field:: protected Toolbar mToolbar
   :outertype: DrawerActivity

navView
^^^^^^^

.. java:field::  NavigationView navView
   :outertype: DrawerActivity

   The navigation view

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: DrawerActivity

   Queue for asynchronous tasks

subscribedNetworkIds
^^^^^^^^^^^^^^^^^^^^

.. java:field:: protected Set<Long> subscribedNetworkIds
   :outertype: DrawerActivity

   IDs of the \ :java:ref:`Network`\ s the current \ :java:ref:`User`\  is subscribed to

subscribedNetworks
^^^^^^^^^^^^^^^^^^

.. java:field:: protected SparseArray<Network> subscribedNetworks
   :outertype: DrawerActivity

   The \ :java:ref:`User`\ 's current \ :java:ref:`Network`\ s

thisActivity
^^^^^^^^^^^^

.. java:field::  Activity thisActivity
   :outertype: DrawerActivity

   Reference to the current activity

Methods
-------
onConfigurationChanged
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onConfigurationChanged(Configuration newConfig)
   :outertype: DrawerActivity

   {@inheritDoc} Also updates the configuration of the drawer toggle by calling \ :java:ref:`DrawerActivity.mDrawerToggle.onConfigurationChanged(Configuration)`\  with the provided parameter.

   :param newConfig: {@inheritDoc}

onNavigationItemSelected
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onNavigationItemSelected(MenuItem item)
   :outertype: DrawerActivity

   Handle navigation items the user selects. If they select a \ :java:ref:`Network`\ , they are sent to \ :java:ref:`TimelineActivity`\  after the selected network is set as their chosen one. Otherwise, the appropriate activity is launched based on the option they select.

   :param item: Item the user selected.
   :return: Always returns \ ``true``\

onPostCreate
^^^^^^^^^^^^

.. java:method:: @Override protected void onPostCreate(Bundle savedInstanceState)
   :outertype: DrawerActivity

   {@inheritDoc} Also syncs the state of \ :java:ref:`DrawerActivity.mDrawerToggle`\

   :param savedInstanceState: {@inheritDoc}

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: DrawerActivity

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

setContentView
^^^^^^^^^^^^^^

.. java:method:: @Override public void setContentView(int layoutResID)
   :outertype: DrawerActivity

   Create the drawer from \ :java:ref:`R.layout.activity_drawer`\ , which has parent with ID \ :java:ref:`R.id.drawer_frame`\ . Populate the drawer with data from the current \ :java:ref:`User`\  and their \ :java:ref:`Network`\ s.

   :param layoutResID: ID for the layout file to inflate

