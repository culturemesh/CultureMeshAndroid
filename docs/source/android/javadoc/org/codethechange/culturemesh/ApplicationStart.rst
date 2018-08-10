.. java:import:: android.app Application

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: com.crashlytics.android Crashlytics

.. java:import:: io.fabric.sdk.android Fabric

ApplicationStart
================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class ApplicationStart extends Application

   This serves as a landing page for when the app is started from scratch. It does some initialization and then redirects the user to an appropriate activity.

Methods
-------
onCreate
^^^^^^^^

.. java:method:: @Override public void onCreate()
   :outertype: ApplicationStart

   Initialize Crashyltics and redirect user to \ :java:ref:`TimelineActivity`\  if they have a selected network saved. If not, they are directed to \ :java:ref:`ExploreBubblesOpenGLActivity`\  to choose a new network. If they aren't even signed in yet, they are sent to \ :java:ref:`OnboardActivity`\ .

