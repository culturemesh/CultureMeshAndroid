.. java:import:: android.content Intent

.. java:import:: android.content.res TypedArray

.. java:import:: android.os Bundle

.. java:import:: android.support.design.widget Snackbar

.. java:import:: android.support.v4.content ContextCompat

.. java:import:: android.support.v7.widget Toolbar

.. java:import:: android.util Log

.. java:import:: android.view View

.. java:import:: android.widget TextView

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: org.codethechange.bubblepicker BubblePickerListener

.. java:import:: org.codethechange.bubblepicker.adapter BubblePickerAdapter

.. java:import:: org.codethechange.bubblepicker.model BubbleGradient

.. java:import:: org.codethechange.bubblepicker.model PickerItem

.. java:import:: org.codethechange.bubblepicker.model PickerItemSize

.. java:import:: org.codethechange.bubblepicker.rendering BubblePicker

.. java:import:: org.codethechange.culturemesh.models FromLocation

.. java:import:: org.codethechange.culturemesh.models Language

.. java:import:: org.codethechange.culturemesh.models Location

.. java:import:: org.codethechange.culturemesh.models NearLocation

.. java:import:: org.codethechange.culturemesh.models Network

.. java:import:: org.jetbrains.annotations NotNull

.. java:import:: java.util ArrayList

.. java:import:: java.util HashMap

ExploreBubblesOpenGLActivity
============================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class ExploreBubblesOpenGLActivity extends DrawerActivity

   Display moving bubbles which show suggested networks for the user to join

Fields
------
hintText
^^^^^^^^

.. java:field::  TextView hintText
   :outertype: ExploreBubblesOpenGLActivity

   The even smaller view that will explain to the user which hint to do.

languages
^^^^^^^^^

.. java:field::  HashMap<String, Language> languages
   :outertype: ExploreBubblesOpenGLActivity

   A mapping from the title of the bubble (Location#getShortName()) to the language object.

locations
^^^^^^^^^

.. java:field::  HashMap<String, Location> locations
   :outertype: ExploreBubblesOpenGLActivity

   A mapping from the title of the bubble (Location#getShortName()) to the location object.

picker
^^^^^^

.. java:field::  BubblePicker picker
   :outertype: ExploreBubblesOpenGLActivity

   The custom view that displays locations/languages as bubbles.

selectedNearLocation
^^^^^^^^^^^^^^^^^^^^

.. java:field::  NearLocation selectedNearLocation
   :outertype: ExploreBubblesOpenGLActivity

subTitle
^^^^^^^^

.. java:field::  TextView subTitle
   :outertype: ExploreBubblesOpenGLActivity

   The smaller text view responsible for clarifying the title text.

title
^^^^^

.. java:field::  TextView title
   :outertype: ExploreBubblesOpenGLActivity

   The text view responsible for guiding the user with the interface

Methods
-------
onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: ExploreBubblesOpenGLActivity

onPause
^^^^^^^

.. java:method:: @Override protected void onPause()
   :outertype: ExploreBubblesOpenGLActivity

onResume
^^^^^^^^

.. java:method:: @Override protected void onResume()
   :outertype: ExploreBubblesOpenGLActivity

visitNetwork
^^^^^^^^^^^^

.. java:method::  void visitNetwork(long id)
   :outertype: ExploreBubblesOpenGLActivity

   Navigates to TimelineActivity to view the selected network.

   :param id: id of network.

