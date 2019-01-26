.. java:import:: android.app Application

.. java:import:: com.crashlytics.android Crashlytics

.. java:import:: io.fabric.sdk.android Fabric

ApplicationStart
================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public class ApplicationStart extends Application

   This serves as a landing page for when the app is started from scratch. It does some initialization.

Methods
-------
onCreate
^^^^^^^^

.. java:method:: @Override public void onCreate()
   :outertype: ApplicationStart

   Initialize Crashyltics.

