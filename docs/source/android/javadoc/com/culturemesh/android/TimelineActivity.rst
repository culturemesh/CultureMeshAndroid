.. java:import:: android.animation ArgbEvaluator

.. java:import:: android.animation ObjectAnimator

.. java:import:: android.animation ValueAnimator

.. java:import:: android.annotation SuppressLint

.. java:import:: android.app AlertDialog

.. java:import:: android.app Dialog

.. java:import:: android.app DialogFragment

.. java:import:: android.content DialogInterface

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.content.res ColorStateList

.. java:import:: android.os Bundle

.. java:import:: android.os Handler

.. java:import:: android.support.design.widget BottomSheetDialogFragment

.. java:import:: android.support.design.widget FloatingActionButton

.. java:import:: android.support.v4.app FragmentManager

.. java:import:: android.support.v4.widget SwipeRefreshLayout

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.view View

.. java:import:: android.support.v4.view GravityCompat

.. java:import:: android.support.v4.widget DrawerLayout

.. java:import:: android.view Menu

.. java:import:: android.view MenuItem

.. java:import:: android.view.animation Animation

.. java:import:: android.view.animation AnimationUtils

.. java:import:: android.view.animation DecelerateInterpolator

.. java:import:: android.widget Button

.. java:import:: android.widget FrameLayout

.. java:import:: android.widget ImageButton

.. java:import:: android.widget RelativeLayout

.. java:import:: android.widget TextView

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: com.culturemesh.android.models Network

.. java:import:: java.util.concurrent.atomic AtomicBoolean

TimelineActivity
================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public class TimelineActivity extends DrawerActivity implements DrawerActivity.WaitForSubscribedList

   Show a feed of \ :java:ref:`com.culturemesh.android.models.Post`\ s and \ :java:ref:`com.culturemesh.android.models.Event`\ s for the currently selected \ :java:ref:`Network`\

Fields
------
BUNDLE_NETWORK
^^^^^^^^^^^^^^

.. java:field:: static final String BUNDLE_NETWORK
   :outertype: TimelineActivity

   The tag for showing that we're passing in the network to a new activity.

FILTER_CHOICE_EVENTS
^^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String FILTER_CHOICE_EVENTS
   :outertype: TimelineActivity

   The key in SharedPreferences for determining whether to display events in the feed.

FILTER_CHOICE_NATIVE
^^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String FILTER_CHOICE_NATIVE
   :outertype: TimelineActivity

   The key in SharedPreferences for determining whether to display posts in the feed.

FILTER_LABEL
^^^^^^^^^^^^

.. java:field:: final String FILTER_LABEL
   :outertype: TimelineActivity

   The tag for FragmentManager to know we're opening the filter dialog.

joinNetwork
^^^^^^^^^^^

.. java:field::  Button joinNetwork
   :outertype: TimelineActivity

   The button that is shown if the user isn't subscribed to thus network. If they tap it, they join the network!

settings
^^^^^^^^

.. java:field:: static SharedPreferences settings
   :outertype: TimelineActivity

   The app's preferences

Methods
-------
animateFAB
^^^^^^^^^^

.. java:method::  void animateFAB()
   :outertype: TimelineActivity

   This function controls the animation for the FloatingActionButtons. When the user taps the pencil icon, two other floating action buttons rise into view - create post and create event. The

createDefaultNetwork
^^^^^^^^^^^^^^^^^^^^

.. java:method:: protected void createDefaultNetwork()
   :outertype: TimelineActivity

   Use API methods to fetch details of the user's selected network. Then setup activity to display that network's feed.

createNoNetwork
^^^^^^^^^^^^^^^

.. java:method:: protected void createNoNetwork()
   :outertype: TimelineActivity

   If the user has no selected network, direct them to \ :java:ref:`ExploreBubblesOpenGLActivity`\

onBackPressed
^^^^^^^^^^^^^

.. java:method:: @Override public void onBackPressed()
   :outertype: TimelineActivity

   Handle the back button being pressed. If the drawer is open, close it. If the user has scrolled down the feed, return it to the start. Otherwise, go back to the previous activity.

onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: TimelineActivity

   Setup user interface using layout defined in \ :java:ref:`R.layout.activity_timeline`\  and initialize instance fields with that layout's fields (elements)

   :param savedInstanceState: {@inheritDoc}

onCreateOptionsMenu
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onCreateOptionsMenu(Menu menu)
   :outertype: TimelineActivity

   Inflate \ ``menu``\  from \ :java:ref:`R.menu.timeline`\

   :param menu: \ :java:ref:`Menu`\  to inflate
   :return: Always returns \ ``true``\

onOptionsItemSelected
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onOptionsItemSelected(MenuItem item)
   :outertype: TimelineActivity

   {@inheritDoc}

   :param item: {@inheritDoc}
   :return: If \ ``item``\  is selected or if it has the same ID as \ :java:ref:`R.id.action_settings`\ , return \ ``true``\ . Otherwise, return the result of \ :java:ref:`DrawerActivity.onOptionsItemSelected(MenuItem)`\  with parameter \ ``item``\

onStart
^^^^^^^

.. java:method:: @Override protected void onStart()
   :outertype: TimelineActivity

   Check if user has selected a network to view, regardless of whether the user is subscribed to any networks yet. Previously, we checked if the user joined a network, and instead navigate the user to ExploreBubbles. This is not ideal because if a user wants to check out a network before joining one, then they will be unable to view the network. Also calls \ :java:ref:`DrawerActivity.onStart()`\

onSubscribeListFinish
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onSubscribeListFinish()
   :outertype: TimelineActivity

   If the user is subscribed to the network, they are able to write posts and events. If the user is not subscribed to the network, there should be a pretty button for them that encourages the user to join the network. This control flow relies on checking if the user is subscribed to a network or not, which requires an instantiated subscribedNetworkIds set in DrawerActivity. This set is instantiated off the UI thread, so we need to wait until that thread completes. Thus, this function is called by DrawerActivity after the network thread completes.

onSwipeRefresh
^^^^^^^^^^^^^^

.. java:method:: public void onSwipeRefresh()
   :outertype: TimelineActivity

   Restart activity to refresh the feed

