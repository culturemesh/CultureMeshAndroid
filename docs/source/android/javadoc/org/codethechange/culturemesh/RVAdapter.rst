.. java:import:: android.content Context

.. java:import:: android.support.constraint ConstraintLayout

.. java:import:: android.support.constraint ConstraintSet

.. java:import:: android.support.v7.widget CardView

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget ImageView

.. java:import:: android.widget LinearLayout

.. java:import:: android.widget RelativeLayout

.. java:import:: android.widget TextView

.. java:import:: com.squareup.picasso Picasso

.. java:import:: org.codethechange.culturemesh.models Event

.. java:import:: org.codethechange.culturemesh.models FeedItem

.. java:import:: org.codethechange.culturemesh.models Post

.. java:import:: org.codethechange.culturemesh.models PostReply

.. java:import:: java.util List

RVAdapter
=========

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PostViewHolder>

   Adapter that provides the \ :java:ref:`Post`\ s and/or \ :java:ref:`Event`\ s of a \ :java:ref:`org.codethechange.culturemesh.models.Network`\  to displayed, scrollable lists

Constructors
------------
RVAdapter
^^^^^^^^^

.. java:constructor:: public RVAdapter(List<FeedItem> netPosts, OnItemClickListener listener, Context context)
   :outertype: RVAdapter

   Initialize instance fields with provided parameters

   :param netPosts: List of objects to represent in the displayed list
   :param listener: Listener to handle clicks on list tiems
   :param context: \ :java:ref:`Context`\  in which the list will be displayed

Methods
-------
getItemCount
^^^^^^^^^^^^

.. java:method:: @Override public int getItemCount()
   :outertype: RVAdapter

   Get the number of items to display

   :return: Number of items in the list of items to display (\ :java:ref:`RVAdapter.netPosts`\ )

getNetPosts
^^^^^^^^^^^

.. java:method:: public List<FeedItem> getNetPosts()
   :outertype: RVAdapter

   Get the items being represented as elements of the displayed list (not just the ones currently visible).

   :return: Items represented as elements in the displayed list

onBindViewHolder
^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onBindViewHolder(PostViewHolder pvh, int i)
   :outertype: RVAdapter

   Link the provided \ :java:ref:`PostViewHolder`\  to an object in the list \ :java:ref:`RVAdapter.netPosts`\ , which is used to fill the fields in the \ :java:ref:`PostViewHolder`\

   :param pvh: Item in the displayed list whose fields to fill with information
   :param i: Index of object in \ :java:ref:`RVAdapter.netPosts`\  that will serve as the source of information to fill into the displayed list item

onCreateViewHolder
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
   :outertype: RVAdapter

   Create a new \ :java:ref:`PostViewHolder`\  from a \ :java:ref:`View`\  created by inflating the layout described by \ :java:ref:`R.layout.post_view`\ .

   :param parent: Parent for created \ :java:ref:`View`\  used to create \ :java:ref:`PostViewHolder`\
   :param viewType: Not used
   :return: A new \ :java:ref:`PostViewHolder`\  for inclusion in the displayed list

