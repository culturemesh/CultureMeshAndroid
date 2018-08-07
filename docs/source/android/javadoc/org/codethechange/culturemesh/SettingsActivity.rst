.. java:import:: android.app AlertDialog

.. java:import:: android.content DialogInterface

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.database Cursor

.. java:import:: android.graphics Bitmap

.. java:import:: android.net Uri

.. java:import:: android.os Bundle

.. java:import:: android.provider MediaStore

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.support.v7.widget.helper ItemTouchHelper

.. java:import:: android.util Log

.. java:import:: android.view View

.. java:import:: android.widget Button

.. java:import:: android.widget EditText

.. java:import:: android.widget ImageView

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

Fields
------
MAX_PIXELS
^^^^^^^^^^

.. java:field:: final long MAX_PIXELS
   :outertype: SettingsActivity

MAX_QUALITY
^^^^^^^^^^^

.. java:field:: final int MAX_QUALITY
   :outertype: SettingsActivity

MAX_SIDE
^^^^^^^^

.. java:field:: final double MAX_SIDE
   :outertype: SettingsActivity

bio
^^^

.. java:field::  EditText bio
   :outertype: SettingsActivity

emptyText
^^^^^^^^^

.. java:field::  TextView emptyText
   :outertype: SettingsActivity

profilePicture
^^^^^^^^^^^^^^

.. java:field::  ImageView profilePicture
   :outertype: SettingsActivity

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: SettingsActivity

rv
^^

.. java:field::  RecyclerView rv
   :outertype: SettingsActivity

updateProfile
^^^^^^^^^^^^^

.. java:field::  Button updateProfile
   :outertype: SettingsActivity

user
^^^^

.. java:field::  User user
   :outertype: SettingsActivity

Methods
-------
getPath
^^^^^^^

.. java:method:: public String getPath(Uri uri)
   :outertype: SettingsActivity

   Converts Uri into file path Sourced from https://stackoverflow.com/questions/14054307/java-io-filenotfoundexception-in-android

   :param uri: uri taken from

onActivityResult
^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void onActivityResult(int requestCode, int resultCode, Intent data)
   :outertype: SettingsActivity

   This function is overridden to handle image selection. Inspiration from http://www.tauntaunwonton.com/blog/2015/1/21/simple-posting-of-multipartform-data-from-android

   :param requestCode: PICK_IMAGE if we asked them to choose an image from the gallery.
   :param resultCode:
   :param data: Hopefully, the URI.

onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: SettingsActivity

onItemClick
^^^^^^^^^^^

.. java:method:: @Override public void onItemClick(View v, Network network)
   :outertype: SettingsActivity

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: SettingsActivity

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

resetAdapter
^^^^^^^^^^^^

.. java:method::  void resetAdapter()
   :outertype: SettingsActivity

updateUser
^^^^^^^^^^

.. java:method:: public void updateUser(SharedPreferences settings)
   :outertype: SettingsActivity

   Updates user info via PUT request to server.

   :param settings: SharedPreferences instance to save email.

