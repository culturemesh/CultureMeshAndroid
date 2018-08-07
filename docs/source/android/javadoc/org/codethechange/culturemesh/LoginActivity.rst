.. java:import:: android.app Activity

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.os Bundle

.. java:import:: android.support.constraint ConstraintLayout

.. java:import:: android.support.constraint ConstraintSet

.. java:import:: android.support.v7.widget Toolbar

.. java:import:: android.util DisplayMetrics

.. java:import:: android.view View

.. java:import:: android.view.animation Animation

.. java:import:: android.view.animation Transformation

.. java:import:: android.widget Button

.. java:import:: android.widget EditText

.. java:import:: android.widget TextView

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: org.codethechange.culturemesh.models User

LoginActivity
=============

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class LoginActivity extends RedirectableAppCompatActivity

   Login screen that lets a user either sign in with email and password or create a new account

Fields
------
confirmPassword
^^^^^^^^^^^^^^^

.. java:field::  EditText confirmPassword
   :outertype: LoginActivity

   Reference to the text field for password confirmation

firstNameText
^^^^^^^^^^^^^

.. java:field::  EditText firstNameText
   :outertype: LoginActivity

   Reference to the text field for the user's first name

lastNameText
^^^^^^^^^^^^

.. java:field::  EditText lastNameText
   :outertype: LoginActivity

   Reference to the text field for the user's last name

needAccountText
^^^^^^^^^^^^^^^

.. java:field::  TextView needAccountText
   :outertype: LoginActivity

   Text field the user can click to toggle between creating an account and signing in

passwordText
^^^^^^^^^^^^

.. java:field::  EditText passwordText
   :outertype: LoginActivity

   Reference to the text field for the user's password

usernameText
^^^^^^^^^^^^

.. java:field::  EditText usernameText
   :outertype: LoginActivity

   Reference to the text field for the user's username

Methods
-------
isLoggedIn
^^^^^^^^^^

.. java:method:: public static boolean isLoggedIn(SharedPreferences settings)
   :outertype: LoginActivity

   Check whether any user is currently signed in

   :param settings: The app's shared settings, which store user preferences
   :return: \ ``true``\  if a user is signed in, \ ``false``\  otherwise

onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: LoginActivity

   Create the user interface from \ :java:ref:`R.layout.activity_login`\ . Also setup buttons to perform the associated actions, including log-ins with \ :java:ref:`API.Get.loginWithCred(RequestQueue,String,String,SharedPreferences,Response.Listener)`\  and account creation with \ :java:ref:`API.Post.user(RequestQueue,User,String,String,Response.Listener)`\ . Also sets up the animations to convert between signing in and creating an account.

   :param savedInstanceState: {@inheritDoc}

setLoggedIn
^^^^^^^^^^^

.. java:method:: public static void setLoggedIn(SharedPreferences settings, long userID, String email)
   :outertype: LoginActivity

   Largely for testing, this public method can be used to set which user is currently logged in This is useful for PickOnboardingStatusActivity because different login states correspond to different users. No logged-in user is signalled by a missing SharedPreferences entry.

   :param settings: The SharedPreferences storing user login state
   :param userID: ID of the user to make logged-in

setLoggedOut
^^^^^^^^^^^^

.. java:method:: public static void setLoggedOut(SharedPreferences settings)
   :outertype: LoginActivity

   Logout the currently logged-out user. If no user is logged in, nothing happens

   :param settings: The app's shared settings, which store user preferences

