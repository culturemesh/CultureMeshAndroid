.. java:import:: android.content Context

.. java:import:: android.graphics Color

.. java:import:: android.support.constraint ConstraintLayout

.. java:import:: android.support.constraint ConstraintSet

.. java:import:: android.support.v7.widget CardView

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.util Log

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

RVCommentAdapter
================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class RVCommentAdapter extends RecyclerView.Adapter<RVCommentAdapter.PostReplyViewHolder>

   Adapter that populates a UI list with comments

Constructors
------------
RVCommentAdapter
^^^^^^^^^^^^^^^^

.. java:constructor:: public RVCommentAdapter(List<PostReply> comments, OnItemClickListener listener, Context context)
   :outertype: RVCommentAdapter

   Store parameters in instance fields

   :param comments: List of comments to display in scrollable list to user
   :param listener: Will be called whenever an item is clicked
   :param context: \ :java:ref:`Context`\  within which the list will be displayed

Methods
-------
getItemCount
^^^^^^^^^^^^

.. java:method:: @Override public int getItemCount()
   :outertype: RVCommentAdapter

   Get the number of comments in the list

   :return: Number of comments in list

onBindViewHolder
^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onBindViewHolder(PostReplyViewHolder pvh, int i)
   :outertype: RVCommentAdapter

   Fill in the fields of \ ``pvh``\  with the information stored in the \ :java:ref:`PostReply`\  at position \ ``i``\  in the list of comments

   :param pvh: \ :java:ref:`View`\  in the list whose fields will be filled-in
   :param i: Index of \ :java:ref:`PostReply`\  in \ :java:ref:`RVCommentAdapter.comments`\  to use as the source of information to fill with

onCreateViewHolder
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public PostReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
   :outertype: RVCommentAdapter

   Create a \ :java:ref:`PostReplyViewHolder`\  for \ ``parent``\  with a \ :java:ref:`View`\  inflated from \ :java:ref:`R.layout.comment_view`\

   :param parent: \ :java:ref:`ViewGroup`\  within which to create the \ :java:ref:`PostReplyViewHolder`\
   :param viewType: Not used
   :return: The \ :java:ref:`PostReplyViewHolder`\  associated with the inflated \ :java:ref:`View`\

