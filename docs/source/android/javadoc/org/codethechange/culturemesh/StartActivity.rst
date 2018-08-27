.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.support.v7.app AppCompatActivity

StartActivity
=============

.. java:package:: com.culturemesh
   :noindex:

.. java:type:: public class StartActivity extends AppCompatActivity

   Transparent \ :java:ref:`android.app.Activity`\  that is the default Activity. It is the one launched when the app first starts, and it is the farthest back the "back" button (on the phone, not in the app) can go before leaving the app. It redirects the user based on their onboarding and login status.

Methods
-------
onResume
^^^^^^^^

.. java:method:: @Override protected void onResume()
   :outertype: StartActivity

   Whenever this screen becomes "visible", immediately redirect the user to \ :java:ref:`TimelineActivity`\  if they have a selected network and are logged in. If they are logged-in without a selected network, redirect them to \ :java:ref:`ExploreBubblesOpenGLActivity`\ . If they are logged-out, redirect them to \ :java:ref:`OnboardActivity`\ .

