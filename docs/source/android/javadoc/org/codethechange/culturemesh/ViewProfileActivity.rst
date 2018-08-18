.. java:import:: android.os Bundle

.. java:import:: android.support.design.widget TabLayout

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.support.v4.app FragmentManager

.. java:import:: android.support.v4.app FragmentStatePagerAdapter

.. java:import:: android.support.v4.view PagerAdapter

.. java:import:: android.support.v4.view ViewPager

.. java:import:: android.support.v7.app AppCompatActivity

.. java:import:: android.support.v7.widget Toolbar

.. java:import:: android.view View

.. java:import:: android.widget FrameLayout

.. java:import:: android.widget ImageView

.. java:import:: android.widget TextView

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: com.squareup.picasso Picasso

.. java:import:: org.codethechange.culturemesh.models User

ViewProfileActivity
===================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class ViewProfileActivity extends AppCompatActivity

   Displays the profile of a user other than the currently-logged-in one

Fields
------
SELECTED_USER
^^^^^^^^^^^^^

.. java:field:: public static final String SELECTED_USER
   :outertype: ViewProfileActivity

   Key for extra in \ :java:ref:`android.content.Intent`\ s that specifies the user whose profile is to be displayed. This should be included in the intent that launches this activity.

loadingOverlay
^^^^^^^^^^^^^^

.. java:field::  FrameLayout loadingOverlay
   :outertype: ViewProfileActivity

mTabLayout
^^^^^^^^^^

.. java:field::  TabLayout mTabLayout
   :outertype: ViewProfileActivity

   Handles the tabs available in the interface and serves as the framework on which the rest of the UI elements are arranged.

mViewPager
^^^^^^^^^^

.. java:field::  ViewPager mViewPager
   :outertype: ViewProfileActivity

   Manages the variety of lists that could be displayed: networks, posts, and events

profilePic
^^^^^^^^^^

.. java:field::  ImageView profilePic
   :outertype: ViewProfileActivity

   Field for the displayed profile's photo

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: ViewProfileActivity

   Queue for asynchronous tasks

selUser
^^^^^^^

.. java:field::  long selUser
   :outertype: ViewProfileActivity

   ID of the \ :java:ref:`User`\  whose profile to display

userName
^^^^^^^^

.. java:field::  TextView userName
   :outertype: ViewProfileActivity

   Text fields for the displayed profile's display name, bio, and name

Methods
-------
onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: ViewProfileActivity

   Setup the user interface using the layout defined in \ :java:ref:`R.layout.activity_view_profile`\  and configure the various tabs. Initialize instance fields with the elements of the \ :java:ref:`android.view.View`\  created from the layout and fill the UI fields with the content of the profile using \ :java:ref:`API.Get.user(RequestQueue,long,Response.Listener)`\

   :param savedInstanceState: {@inheritDoc}

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: ViewProfileActivity

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

onSupportNavigateUp
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onSupportNavigateUp()
   :outertype: ViewProfileActivity

   This allows the user to hit the back button on the toolbar to go to the previous activity.

   :return: Always \ ``true``\

