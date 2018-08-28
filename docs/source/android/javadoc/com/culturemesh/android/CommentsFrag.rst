.. java:import:: android.content Context

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.net Uri

.. java:import:: android.os Bundle

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.support.v7.app AppCompatActivity

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget Toast

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: com.culturemesh.android.models Network

.. java:import:: com.culturemesh.android.models Post

.. java:import:: com.culturemesh.android.models PostReply

.. java:import:: java.util ArrayList

CommentsFrag
============

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public class CommentsFrag extends Fragment

   Fragment for displaying comments to posts

Fields
------
settings
^^^^^^^^

.. java:field::  SharedPreferences settings
   :outertype: CommentsFrag

   The app's shared settings that store user info and preferences

Methods
-------
onAttach
^^^^^^^^

.. java:method:: @Override public void onAttach(Context context)
   :outertype: CommentsFrag

   {@inheritDoc}

   :param context: {@inheritDoc}

onCreate
^^^^^^^^

.. java:method:: @Override public void onCreate(Bundle savedInstanceState)
   :outertype: CommentsFrag

   Initialize references to \ :java:ref:`CommentsFrag.queue`\  and \ :java:ref:`CommentsFrag.settings`\ .

   :param savedInstanceState:

onCreateView
^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: CommentsFrag

   Populate the activity with UI elements

   :param inflater: Inflates the xml \ :java:ref:`R.layout.fragment_comments`\  into the displayed UI
   :param container: TODO: What is this?
   :param savedInstanceState: Saved state that can be restored. Not used.
   :return: The inflated view produced by \ ``inflater``\

onDetach
^^^^^^^^

.. java:method:: @Override public void onDetach()
   :outertype: CommentsFrag

   {@inheritDoc}

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: CommentsFrag

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

