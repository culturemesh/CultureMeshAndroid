.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.os AsyncTask

.. java:import:: android.os Handler

.. java:import:: android.support.design.widget FloatingActionButton

.. java:import:: android.support.v4.app FragmentActivity

.. java:import:: android.support.constraint ConstraintLayout

.. java:import:: android.support.design.widget Snackbar

.. java:import:: android.support.v7.app AppCompatActivity

.. java:import:: android.os Bundle

.. java:import:: android.support.v7.widget CardView

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.util Log

.. java:import:: android.view LayoutInflater

.. java:import:: android.util Log

.. java:import:: android.util SparseArray

.. java:import:: android.util SparseBooleanArray

.. java:import:: android.view View

.. java:import:: android.view.animation Animation

.. java:import:: android.view.animation Transformation

.. java:import:: android.widget AdapterView

.. java:import:: android.widget ArrayAdapter

.. java:import:: android.widget Button

.. java:import:: android.widget ImageButton

.. java:import:: android.widget ImageView

.. java:import:: android.widget LinearLayout

.. java:import:: android.widget ListView

.. java:import:: android.widget ProgressBar

.. java:import:: android.widget TextView

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley VolleyError

.. java:import:: com.android.volley.toolbox JsonArrayRequest

.. java:import:: com.android.volley.toolbox JsonObjectRequest

.. java:import:: com.android.volley.toolbox JsonRequest

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: com.squareup.picasso Picasso

.. java:import:: org.codethechange.culturemesh.models Post

.. java:import:: org.codethechange.culturemesh.models PostReply

.. java:import:: org.codethechange.culturemesh.models User

.. java:import:: org.json JSONArray

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: org.w3c.dom Comment

.. java:import:: java.util ArrayList

.. java:import:: java.util Date

.. java:import:: java.util List

SpecificPostActivity
====================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class SpecificPostActivity extends AppCompatActivity implements FormatManager.IconUpdateListener

Fields
------
boldButton
^^^^^^^^^^

.. java:field::  ImageButton boldButton
   :outertype: SpecificPostActivity

commentField
^^^^^^^^^^^^

.. java:field::  ListenableEditText commentField
   :outertype: SpecificPostActivity

content
^^^^^^^

.. java:field::  TextView content
   :outertype: SpecificPostActivity

cv
^^

.. java:field::  CardView cv
   :outertype: SpecificPostActivity

editTextOpened
^^^^^^^^^^^^^^

.. java:field::  boolean editTextOpened
   :outertype: SpecificPostActivity

formatManager
^^^^^^^^^^^^^

.. java:field::  FormatManager formatManager
   :outertype: SpecificPostActivity

images
^^^^^^

.. java:field::  ImageView[] images
   :outertype: SpecificPostActivity

personName
^^^^^^^^^^

.. java:field::  TextView personName
   :outertype: SpecificPostActivity

personPhoto
^^^^^^^^^^^

.. java:field::  ImageView personPhoto
   :outertype: SpecificPostActivity

postButton
^^^^^^^^^^

.. java:field::  Button postButton
   :outertype: SpecificPostActivity

postTypePhoto
^^^^^^^^^^^^^

.. java:field::  ImageView postTypePhoto
   :outertype: SpecificPostActivity

progressBar
^^^^^^^^^^^

.. java:field::  ProgressBar progressBar
   :outertype: SpecificPostActivity

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: SpecificPostActivity

   IMPORTANT: GUIDE FOR NETWORK REQUESTS Every activity will have its own RequestQueue that it will pass on to EVERY API method call. The RequestQueue handles all the dirty work of multithreading and dispatching. neat!

timestamp
^^^^^^^^^

.. java:field::  TextView timestamp
   :outertype: SpecificPostActivity

toggleButtons
^^^^^^^^^^^^^

.. java:field::  SparseArray<ImageButton> toggleButtons
   :outertype: SpecificPostActivity

username
^^^^^^^^

.. java:field::  TextView username
   :outertype: SpecificPostActivity

writeReplyView
^^^^^^^^^^^^^^

.. java:field::  ConstraintLayout writeReplyView
   :outertype: SpecificPostActivity

Methods
-------
closeEditTextView
^^^^^^^^^^^^^^^^^

.. java:method::  void closeEditTextView()
   :outertype: SpecificPostActivity

   When the user selects out of the text field, the view will shrink back to its original position.

fetchCommentsAtEnd
^^^^^^^^^^^^^^^^^^

.. java:method:: public void fetchCommentsAtEnd(int currItem)
   :outertype: SpecificPostActivity

genResizeAnimation
^^^^^^^^^^^^^^^^^^

.. java:method::  void genResizeAnimation(int oldSize, int newSize, ConstraintLayout layout)
   :outertype: SpecificPostActivity

   This little helper handles the animation involved in changing the size of the write reply view.

   :param oldSize: start height, in pixels.
   :param newSize: final height, in pixels.
   :param layout: writeReplyView

onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: SpecificPostActivity

onStop
^^^^^^

.. java:method:: @Override protected void onStop()
   :outertype: SpecificPostActivity

   IMPORTANT: EXAMPLE GUIDE FOR NETWORK REQUESTS This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

openEditTextView
^^^^^^^^^^^^^^^^

.. java:method::  void openEditTextView()
   :outertype: SpecificPostActivity

   This function animates the bottom view to expand up, allowing for a greater text field as well as toggle buttons.

updateIconToggles
^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void updateIconToggles(SparseBooleanArray formTogState, SparseArray<int[]> toggleIcons)
   :outertype: SpecificPostActivity

