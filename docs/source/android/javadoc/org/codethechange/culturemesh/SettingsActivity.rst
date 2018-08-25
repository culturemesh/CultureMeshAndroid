.. java:import:: android.app AlertDialog

.. java:import:: android.content DialogInterface

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.database Cursor

.. java:import:: android.graphics Bitmap

.. java:import:: android.net Uri

.. java:import:: android.os Bundle

.. java:import:: android.provider MediaStore

.. java:import:: android.provider Settings

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.support.v7.widget.helper ItemTouchHelper

.. java:import:: android.util Log

.. java:import:: android.view View

.. java:import:: android.widget Button

.. java:import:: android.widget EditText

.. java:import:: android.widget FrameLayout

.. java:import:: android.widget ImageView

.. java:import:: android.widget ScrollView

.. java:import:: android.widget TextView

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: com.koushikdutta.async.future FutureCallback

.. java:import:: com.koushikdutta.ion Ion

.. java:import:: com.squareup.picasso Picasso

.. java:import:: org.codethechange.culturemesh.models Network

.. java:import:: org.codethechange.culturemesh.models User

.. java:import:: java.io File

.. java:import:: java.io FileOutputStream

.. java:import:: java.io IOException

.. java:import:: java.io OutputStream

.. java:import:: java.util ArrayList

.. java:import:: java.util HashMap

SettingsActivity
================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class SettingsActivity extends DrawerActivity implements NetworkSummaryAdapter.OnNetworkTapListener

   Screen that displays the current user's profile and let's them update it

Fields
------
MAX_PIXELS
^^^^^^^^^^

.. java:field:: final long MAX_PIXELS
   :outertype: SettingsActivity

   The max number of pixels for an image given the image. Each pixel is 8 bytes large (according to RGBA_F16), and a MB is 2^20 bytes

MAX_QUALITY
^^^^^^^^^^^

.. java:field:: final int MAX_QUALITY
   :outertype: SettingsActivity

   Constant that clarifies that quality 100 means no compression.

MAX_SIDE
^^^^^^^^

.. java:field:: final double MAX_SIDE
   :outertype: SettingsActivity

   The maximum number of pixels allowed on a single side of an image

bio
^^^

.. java:field::  EditText bio
   :outertype: SettingsActivity

   Editable text fields that make up parts of the \ :java:ref:`User`\ 's profile

emptyText
^^^^^^^^^

.. java:field::  TextView emptyText
   :outertype: SettingsActivity

   Text field that displays \ :java:ref:`R.string.no_networks`\  if the user has not joined any \ :java:ref:`Network`\ s

profilePicture
^^^^^^^^^^^^^^

.. java:field::  ImageView profilePicture
   :outertype: SettingsActivity

   The field for the \ :java:ref:`User`\ 's profile picture

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: SettingsActivity

   Queue for asynchronous tasks

rv
^^

.. java:field::  RecyclerView rv
   :outertype: SettingsActivity

scrollView
^^^^^^^^^^

.. java:field::  ScrollView scrollView
   :outertype: SettingsActivity

   The user whose profile is displayed and being edited

updateProfile
^^^^^^^^^^^^^

.. java:field::  Button updateProfile
   :outertype: SettingsActivity

   Button for updating the \ :java:ref:`User`\ 's profile on the server with the one currently displayed

user
^^^^

.. java:field::  User user
   :outertype: SettingsActivity

Methods
-------
onActivityResult
^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void onActivityResult(int requestCode, int resultCode, Intent data)
   :outertype: SettingsActivity

   This function is overridden to handle image selection. Inspiration from http://www.tauntaunwonton.com/blog/2015/1/21/simple-posting-of-multipartform-data-from-android

   :param requestCode: PICK_IMAGE if we asked them to choose an image from the gallery.
   :param resultCode: {@inheritDoc}
   :param data: Hopefully, the URI.

onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: SettingsActivity

   Setup the user interface with the layout defined in \ :java:ref:`R.layout.activity_settings`\ . Also initialize instance fields for UI fields with the elements defined in the layout file. Fill the fields with the current profile (fetched using \ :java:ref:`API.Get.user(RequestQueue,long,Response.Listener)`\ ). Link listeners to buttons and the displays of \ :java:ref:`Network`\ s to handle interactions.

   :param savedInstanceState: {@inheritDoc}

onItemClick
^^^^^^^^^^^

.. java:method:: @Override public void onItemClick(View v, Network network)
   :outertype: SettingsActivity

   Handle what happens when a user clicks on a \ :java:ref:`Network`\ . Right now, nothing is done.

   :param v: {@inheritDoc}
   :param network: {@inheritDoc}

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: SettingsActivity

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

resetAdapter
^^^^^^^^^^^^

.. java:method::  void resetAdapter()
   :outertype: SettingsActivity

   Reset the adapter by clearing it and then populating it with new information from \ :java:ref:`API.Get.userNetworks(RequestQueue,long,Response.Listener)`\ , \ :java:ref:`API.Get.networkPostCount(RequestQueue,long,Response.Listener)`\ , and \ :java:ref:`API.Get.networkUserCount(RequestQueue,long,Response.Listener)`\ .

updateUser
^^^^^^^^^^

.. java:method:: public void updateUser(SharedPreferences settings)
   :outertype: SettingsActivity

   Updates user info via PUT request to server.

   :param settings: SharedPreferences instance to save email.

