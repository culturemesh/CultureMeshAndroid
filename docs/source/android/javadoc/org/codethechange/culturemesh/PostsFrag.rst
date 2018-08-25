.. java:import:: android.content Context

.. java:import:: android.content DialogInterface

.. java:import:: android.content Intent

.. java:import:: android.content SharedPreferences

.. java:import:: android.content.res Resources

.. java:import:: android.os Bundle

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.support.v7.app AlertDialog

.. java:import:: android.support.v7.app AppCompatActivity

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget TextView

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: org.codethechange.culturemesh.models Event

.. java:import:: org.codethechange.culturemesh.models FeedItem

.. java:import:: org.codethechange.culturemesh.models Post

.. java:import:: org.codethechange.culturemesh.models PostReply

.. java:import:: java.util ArrayList

.. java:import:: java.util List

PostsFrag
=========

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class PostsFrag extends Fragment

   Created by Dylan Grosz (dgrosz@stanford.edu) on 11/10/17.

Fields
------
maxEventId
^^^^^^^^^^

.. java:field::  String maxEventId
   :outertype: PostsFrag

maxPostId
^^^^^^^^^

.. java:field::  String maxPostId
   :outertype: PostsFrag

noPosts
^^^^^^^

.. java:field::  TextView noPosts
   :outertype: PostsFrag

   The textview that is shown if no feed items have been created for this network.

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: PostsFrag

selectedNetwork
^^^^^^^^^^^^^^^

.. java:field::  long selectedNetwork
   :outertype: PostsFrag

settings
^^^^^^^^

.. java:field::  SharedPreferences settings
   :outertype: PostsFrag

Methods
-------
fetchNewPage
^^^^^^^^^^^^

.. java:method:: public void fetchNewPage(Response.Listener<Void> listener)
   :outertype: PostsFrag

   If the user has exhausted the list of fetched posts/events, this will fetch another batch of posts.

   :param listener: the listener that will be called when we finish fetching the stuffs.

onAttach
^^^^^^^^

.. java:method:: @Override public void onAttach(Context context)
   :outertype: PostsFrag

   {@inheritDoc}

   :param context: {@inheritDoc}

onCreate
^^^^^^^^

.. java:method:: @Override public void onCreate(Bundle savedInstanceState)
   :outertype: PostsFrag

   {@inheritDoc} Also initialize \ :java:ref:`PostsFrag.settings`\  and \ :java:ref:`PostsFrag.queue`\

   :param savedInstanceState: {@inheritDoc}

onCreateView
^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: PostsFrag

   Create user interface and handle clicks on posts by launching \ :java:ref:`SpecificPostActivity`\ , which displays more detailed information.

   :param inflater: Inflates \ :java:ref:`R.layout.fragment_posts`\  into a full user interface that is a child of \ ``container``\
   :param container: Parent of created user interface
   :param savedInstanceState: Not used
   :return: Inflated user interface

onDetach
^^^^^^^^

.. java:method:: @Override public void onDetach()
   :outertype: PostsFrag

   {@inheritDoc}

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: PostsFrag

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

