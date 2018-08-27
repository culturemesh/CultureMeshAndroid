.. java:import:: android.app Activity

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.graphics Point

.. java:import:: android.os Bundle

.. java:import:: android.view Display

.. java:import:: com.codemybrainsout.onboarder AhoyOnboarderActivity

.. java:import:: com.codemybrainsout.onboarder AhoyOnboarderCard

.. java:import:: java.util ArrayList

.. java:import:: java.util List

OnboardActivity
===============

.. java:package:: com.culturemesh
   :noindex:

.. java:type:: public class OnboardActivity extends AhoyOnboarderActivity

   Introduce user to the app through a series of informational screens that end with a button that redirects the user to a login page

Methods
-------
getFinishButtonTitle
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getFinishButtonTitle()
   :outertype: OnboardActivity

onActivityResult
^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void onActivityResult(int requestCode, int response, Intent data)
   :outertype: OnboardActivity

   After the user has logged in, this function is called to redirect user to new activity

   :param requestCode: Code that indicates what startActivityForResult call has finished
   :param response: Response from the completed call
   :param data: Data returned from the call

onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: OnboardActivity

   Generate onboarding pages and display them

   :param savedInstanceState: Previous state to restore from

onFinishButtonPressed
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onFinishButtonPressed()
   :outertype: OnboardActivity

   When finish button pressed at end of onboarding, send user to login page

