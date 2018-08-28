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

TimelineActivity.FilterDialogFragment
=====================================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public static class FilterDialogFragment extends DialogFragment
   :outertype: TimelineActivity

   This dialog allows us to filter out native/twitter posts from the feed

Fields
------
filterSettings
^^^^^^^^^^^^^^

.. java:field::  boolean[] filterSettings
   :outertype: TimelineActivity.FilterDialogFragment

Methods
-------
onCreateDialog
^^^^^^^^^^^^^^

.. java:method:: @Override public Dialog onCreateDialog(Bundle savedInstanceState)
   :outertype: TimelineActivity.FilterDialogFragment

