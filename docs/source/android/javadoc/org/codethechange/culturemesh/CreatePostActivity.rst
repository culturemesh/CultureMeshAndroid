.. java:import:: android.content SharedPreferences

.. java:import:: android.os Bundle

.. java:import:: android.support.v7.app AppCompatActivity

.. java:import:: android.support.v7.widget Toolbar

.. java:import:: android.text SpannableStringBuilder

.. java:import:: android.text.method LinkMovementMethod

.. java:import:: android.util SparseArray

.. java:import:: android.util SparseBooleanArray

.. java:import:: android.view Menu

.. java:import:: android.view MenuInflater

.. java:import:: android.view MenuItem

.. java:import:: android.view View

.. java:import:: android.widget Button

.. java:import:: android.widget ProgressBar

.. java:import:: android.widget TextView

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: org.codethechange.culturemesh.models Network

.. java:import:: org.codethechange.culturemesh.models Post

.. java:import:: java.util Date

CreatePostActivity
==================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class CreatePostActivity extends AppCompatActivity implements FormatManager.IconUpdateListener

   Creates screen the user can use to create a new \ :java:ref:`Post`\

Fields
------
content
^^^^^^^

.. java:field::  ListenableEditText content
   :outertype: CreatePostActivity

   Field the user uses to type the body of their \ :java:ref:`Post`\

formatManager
^^^^^^^^^^^^^

.. java:field::  FormatManager formatManager
   :outertype: CreatePostActivity

   Handles markup of the body text

menuItems
^^^^^^^^^

.. java:field::  SparseArray<MenuItem> menuItems
   :outertype: CreatePostActivity

   All the items in the formatting menu

networkLabel
^^^^^^^^^^^^

.. java:field::  TextView networkLabel
   :outertype: CreatePostActivity

   Displays the \ :java:ref:`Network`\  the user's \ :java:ref:`Post`\  will be added to

progressBar
^^^^^^^^^^^

.. java:field::  ProgressBar progressBar
   :outertype: CreatePostActivity

   Displays progress as the post is being sent over the network

Methods
-------
onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: CreatePostActivity

   Create the screen from \ :java:ref:`R.layout.activity_create_post`\ , fill \ :java:ref:`CreatePostActivity.networkLabel`\  with a description of the \ :java:ref:`Network`\  from \ :java:ref:`API.Get.network(RequestQueue,long,Response.Listener)`\ , setup \ :java:ref:`CreatePostActivity.formatManager`\ , and link a listener to the submission button that sends the \ :java:ref:`Post`\  using \ :java:ref:`API.Post.post(RequestQueue,Post,SharedPreferences,Response.Listener)`\

   :param savedInstanceState: {@inheritDoc}

onCreateOptionsMenu
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onCreateOptionsMenu(Menu menu)
   :outertype: CreatePostActivity

   Populate the options menu with controls to make text bold, italic, or a link

   :param menu: Menu to populate with options
   :return: Always returns \ ``true``\

onOptionsItemSelected
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public boolean onOptionsItemSelected(MenuItem item)
   :outertype: CreatePostActivity

   This function handles what happens when our format toggle buttons are clicked. We want to update the content formatting when this happens as well with Spannables. Check out https://stackoverflow.com/questions/10828182/spannablestringbuilder-to-create-string-with-multiple-fonts-text-sizes-etc-examp for more info.

   :param item: the MenuItem that was tapped.

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: CreatePostActivity

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

updateIconToggles
^^^^^^^^^^^^^^^^^

.. java:method:: public void updateIconToggles(SparseBooleanArray formTogState, SparseArray<int[]> toggleIcons)
   :outertype: CreatePostActivity

   This fancy function uses our SparseArray's to concisely iterate over our toggle icons and update their colors - white if untoggled, black if toggled.

