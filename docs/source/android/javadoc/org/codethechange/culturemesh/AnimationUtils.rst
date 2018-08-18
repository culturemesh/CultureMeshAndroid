.. java:import:: android.animation Animator

.. java:import:: android.animation AnimatorListenerAdapter

.. java:import:: android.view View

AnimationUtils
==============

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class AnimationUtils

   This is a utility class to show the loading overlay for activities that require network requests to display their data.

Methods
-------
animateLoadingOverlay
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public static void animateLoadingOverlay(View view, int toVisibility, float toAlpha, int duration)
   :outertype: AnimationUtils

   Shows or hides loading overlay with smooth alpha transition. Sourced from https://stackoverflow.com/questions/18021148/display-a-loading-overlay-on-android-screen

   :param view: View to animate
   :param toVisibility: Visibility at the end of animation
   :param toAlpha: Alpha at the end of animation
   :param duration: Animation duration in ms

