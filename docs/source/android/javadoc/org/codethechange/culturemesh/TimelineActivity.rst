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

.. java:import:: android.os AsyncTask

.. java:import:: android.os Bundle

.. java:import:: android.os Handler

.. java:import:: android.support.design.widget BottomSheetDialogFragment

.. java:import:: android.support.design.widget FloatingActionButton

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.support.v4.app FragmentManager

.. java:import:: android.support.v4.widget SwipeRefreshLayout

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.util Log

.. java:import:: android.view View

.. java:import:: android.support.v4.view GravityCompat

.. java:import:: android.support.v4.widget DrawerLayout

.. java:import:: android.view Menu

.. java:import:: android.view MenuItem

.. java:import:: android.view.animation Animation

.. java:import:: android.view.animation AnimationUtils

.. java:import:: android.view.animation DecelerateInterpolator

.. java:import:: android.widget Button

.. java:import:: android.widget ImageButton

.. java:import:: android.widget TextView

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: org.codethechange.culturemesh.models Network

.. java:import:: org.codethechange.culturemesh.models User

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util.concurrent.atomic AtomicBoolean

TimelineActivity
================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class TimelineActivity extends DrawerActivity implements DrawerActivity.WaitForSubscribedList

   Created by Dylan Grosz (dgrosz@stanford.edu) on 11/8/17.

Fields
------
BUNDLE_NETWORK
^^^^^^^^^^^^^^

.. java:field:: static final String BUNDLE_NETWORK
   :outertype: TimelineActivity

FILTER_CHOICE_EVENTS
^^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String FILTER_CHOICE_EVENTS
   :outertype: TimelineActivity

FILTER_CHOICE_NATIVE
^^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String FILTER_CHOICE_NATIVE
   :outertype: TimelineActivity

FILTER_CHOICE_TWITTER
^^^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String FILTER_CHOICE_TWITTER
   :outertype: TimelineActivity

FILTER_LABEL
^^^^^^^^^^^^

.. java:field:: final String FILTER_LABEL
   :outertype: TimelineActivity

SUBSCRIBED_NETWORKS
^^^^^^^^^^^^^^^^^^^

.. java:field:: static final String SUBSCRIBED_NETWORKS
   :outertype: TimelineActivity

settings
^^^^^^^^

.. java:field:: static SharedPreferences settings
   :outertype: TimelineActivity

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

createNoNetwork
^^^^^^^^^^^^^^^

.. java:method:: protected void createNoNetwork()
   :outertype: TimelineActivity

fetchPostsAtEnd
^^^^^^^^^^^^^^^

.. java:method:: public void fetchPostsAtEnd(int currItem)
   :outertype: TimelineActivity

onBackPressed
^^^^^^^^^^^^^

.. java:method:: @Override public void onBackPressed()
   :outertype: TimelineActivity

onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: TimelineActivity

onCreateOptionsMenu
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onCreateOptionsMenu(Menu menu)
   :outertype: TimelineActivity

onOptionsItemSelected
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onOptionsItemSelected(MenuItem item)
   :outertype: TimelineActivity

onStart
^^^^^^^

.. java:method:: @Override protected void onStart()
   :outertype: TimelineActivity

onSubscribeListFinish
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onSubscribeListFinish()
   :outertype: TimelineActivity

   If the user is subscribed to the network, they are able to write posts and events. If the user is not subscribed to the network, there should be a pretty button for them that encourages the user to join the network. This control flow relies on checking if the user is subscribed to a network or not, which requires an instantiated subscribedNetworkIds set in DrawerActivity. This set is instantiated off the UI thread, so we need to wait until that thread completes. Thus, this function is called by DrawerActivity after the network thread completes.

onSwipeRefresh
^^^^^^^^^^^^^^

.. java:method:: public void onSwipeRefresh()
   :outertype: TimelineActivity

refreshPosts
^^^^^^^^^^^^

.. java:method:: public boolean refreshPosts()
   :outertype: TimelineActivity

