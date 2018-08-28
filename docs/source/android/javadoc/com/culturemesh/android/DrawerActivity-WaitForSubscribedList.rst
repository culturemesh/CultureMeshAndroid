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

.. java:import:: com.culturemesh.android.models Network

.. java:import:: com.culturemesh.android.models User

.. java:import:: java.util ArrayList

.. java:import:: java.util HashSet

.. java:import:: java.util Set

DrawerActivity.WaitForSubscribedList
====================================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public interface WaitForSubscribedList
   :outertype: DrawerActivity

   Interface for classes that have actions that must wait until after the list of subscribed \ :java:ref:`Network`\ s has been populated. Subclasses can use this list instead of making another API call.

Methods
-------
onSubscribeListFinish
^^^^^^^^^^^^^^^^^^^^^

.. java:method::  void onSubscribeListFinish()
   :outertype: DrawerActivity.WaitForSubscribedList

