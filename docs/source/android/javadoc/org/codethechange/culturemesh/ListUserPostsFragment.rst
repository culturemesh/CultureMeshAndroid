.. java:import:: android.content Intent

.. java:import:: android.os Bundle

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget TextView

.. java:import:: android.widget Toast

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: com.squareup.picasso Picasso

.. java:import:: org.codethechange.culturemesh.models FeedItem

.. java:import:: org.codethechange.culturemesh.models Post

.. java:import:: java.util ArrayList

ListUserPostsFragment
=====================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class ListUserPostsFragment extends Fragment implements RVAdapter.OnItemClickListener

   Creates screen that displays the \ :java:ref:`Post`\ s a \ :java:ref:`org.codethechange.culturemesh.models.User`\  has made.

Fields
------
emptyText
^^^^^^^^^

.. java:field::  TextView emptyText
   :outertype: ListUserPostsFragment

   Displays \ :java:ref:`R.string.no_posts`\  if there are no \ :java:ref:`Post`\ s to display

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: ListUserPostsFragment

   Queue for asynchronous tasks

root
^^^^

.. java:field::  View root
   :outertype: ListUserPostsFragment

   The inflated user interface

rv
^^

.. java:field::  RecyclerView rv
   :outertype: ListUserPostsFragment

   Scrollable list of \ :java:ref:`Post`\ s

Methods
-------
newInstance
^^^^^^^^^^^

.. java:method:: public static ListUserPostsFragment newInstance(long selUser)
   :outertype: ListUserPostsFragment

   Returns a new instance of this fragment for the given section number.

onCreateView
^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: ListUserPostsFragment

   Create the user interface. Also populate the list of \ :java:ref:`Post`\ s with the result from \ :java:ref:`API.Get.userPosts(RequestQueue,long,Response.Listener)`\

   :param inflater: Inflates the user interface from \ :java:ref:`R.layout.rv_container`\  with the provided \ ``container``\  as the parent.
   :param container: Parent used by \ ``inflater``\
   :param savedInstanceState: Not used
   :return: The inflated user interface

onItemClick
^^^^^^^^^^^

.. java:method:: @Override public void onItemClick(FeedItem item)
   :outertype: ListUserPostsFragment

   When the user clicks on an item, redirect them to \ :java:ref:`SpecificPostActivity`\  where more details, including comments, are displayed.

   :param item: The clicked item.

onStop
^^^^^^

.. java:method:: @Override public void onStop()
   :outertype: ListUserPostsFragment

   This ensures that we are canceling all network requests if the user is leaving this activity. We use a RequestFilter that accepts all requests (meaning it cancels all requests)

