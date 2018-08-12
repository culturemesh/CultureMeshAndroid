.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

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

.. java:import:: android.widget FrameLayout

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

   Displays a particular \ :java:ref:`Post`\  along with its comments (\ :java:ref:`PostReply`\ ). Also allows the user to add comments.

Fields
------
boldButton
^^^^^^^^^^

.. java:field::  ImageButton boldButton
   :outertype: SpecificPostActivity

   Buttons for inline markup of the text of the reply

commentField
^^^^^^^^^^^^

.. java:field::  ListenableEditText commentField
   :outertype: SpecificPostActivity

   Field for the user to enter a comment

content
^^^^^^^

.. java:field::  TextView content
   :outertype: SpecificPostActivity

   Body of the \ :java:ref:`Post`\

cv
^^

.. java:field::  CardView cv
   :outertype: SpecificPostActivity

   The \ :java:ref:`View`\  that holds the UI elements that make up the displayed \ :java:ref:`Post`\

editTextOpened
^^^^^^^^^^^^^^

.. java:field::  boolean editTextOpened
   :outertype: SpecificPostActivity

   Whether the "window" to write a reply is open. Starts off \ ``false``\

formatManager
^^^^^^^^^^^^^

.. java:field::  FormatManager formatManager
   :outertype: SpecificPostActivity

   Manages markup of the text of the reply

images
^^^^^^

.. java:field::  ImageView[] images
   :outertype: SpecificPostActivity

   Array of images associated with the \ :java:ref:`Post`\

loadingOverlay
^^^^^^^^^^^^^^

.. java:field::  FrameLayout loadingOverlay
   :outertype: SpecificPostActivity

personName
^^^^^^^^^^

.. java:field::  TextView personName
   :outertype: SpecificPostActivity

   Name of the creator of the \ :java:ref:`Post`\

personPhoto
^^^^^^^^^^^

.. java:field::  ImageView personPhoto
   :outertype: SpecificPostActivity

   Profile photo of the author of the \ :java:ref:`Post`\

postButton
^^^^^^^^^^

.. java:field::  Button postButton
   :outertype: SpecificPostActivity

   Button to submit a comment on the \ :java:ref:`Post`\

postTypePhoto
^^^^^^^^^^^^^

.. java:field::  ImageView postTypePhoto
   :outertype: SpecificPostActivity

   Other photo associated with the \ :java:ref:`Post`\

progressBar
^^^^^^^^^^^

.. java:field::  ProgressBar progressBar
   :outertype: SpecificPostActivity

   Progress bar for displaying the progress of network operations

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: SpecificPostActivity

   Queue for asynchronous tasks

timestamp
^^^^^^^^^

.. java:field::  TextView timestamp
   :outertype: SpecificPostActivity

   When the \ :java:ref:`Post`\  was created

toggleButtons
^^^^^^^^^^^^^

.. java:field::  SparseArray<ImageButton> toggleButtons
   :outertype: SpecificPostActivity

   Tracks whether the inline markup buttons have been toggled to "on"

username
^^^^^^^^

.. java:field::  TextView username
   :outertype: SpecificPostActivity

   Unique display name of the creator of the \ :java:ref:`Post`\

writeReplyView
^^^^^^^^^^^^^^

.. java:field::  ConstraintLayout writeReplyView
   :outertype: SpecificPostActivity

   Layout within which the compose reply UI elements are arranged

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

   Fetch the next comments after the bottom of the scrolling list has been reached

   :param currItem: Current item in the list

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

   Create the user interface from the layout defined by \ :java:ref:`R.layout.activity_specific_post`\ . Initialize instance fields with the UI elements defined in the layout. Setup listeners to handle loading more comments, clicks to post replies, and load the \ :java:ref:`Post`\  to display.

   :param savedInstanceState: {@inheritDoc}

onStop
^^^^^^

.. java:method:: @Override protected void onStop()
   :outertype: SpecificPostActivity

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

openEditTextView
^^^^^^^^^^^^^^^^

.. java:method::  void openEditTextView()
   :outertype: SpecificPostActivity

   This function animates the bottom view to expand up, allowing for a greater text field as well as toggle buttons.

updateIconToggles
^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void updateIconToggles(SparseBooleanArray formTogState, SparseArray<int[]> toggleIcons)
   :outertype: SpecificPostActivity

   Update whether an icon has been "toggled", or selected

   :param formTogState: a SparseBooleanArray (HashMap) with int as key and boolean as value key: int id of toggleButton View we are using. value: true if toggled, false if not toggled.
   :param toggleIcons: a SparseArray (HashMap) with int as key and int[] as value. key: int id of toggleButton View we are using.

