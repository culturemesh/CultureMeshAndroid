.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.util Log

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget TextView

.. java:import:: com.culturemesh.models Network

.. java:import:: java.util ArrayList

.. java:import:: java.util HashMap

NetworkSummaryAdapter
=====================

.. java:package:: com.culturemesh
   :noindex:

.. java:type:: public class NetworkSummaryAdapter extends RecyclerView.Adapter<NetworkSummaryAdapter.PostViewHolder>

   This functions as the recyclerview adapter for the listview in ViewProfileActivity, where the user can view other users' subscribed networks.

Constructors
------------
NetworkSummaryAdapter
^^^^^^^^^^^^^^^^^^^^^

.. java:constructor::  NetworkSummaryAdapter(ArrayList<Network> networks, HashMap<String, Integer> postCounts, HashMap<String, Integer> userCounts, OnNetworkTapListener listener)
   :outertype: NetworkSummaryAdapter

   Initialize instance fields with parameters

   :param networks: List of \ :java:ref:`Network`\ s to display
   :param postCounts: Mapping from the ID of each \ :java:ref:`Network`\  to the number of \ :java:ref:`com.culturemesh.models.Post`\ s it contains
   :param userCounts: Mapping from the ID of each \ :java:ref:`Network`\  to the number of \ :java:ref:`com.culturemesh.models.User`\ s it contains
   :param listener: Listener to handle clicks on list items

Methods
-------
getItemCount
^^^^^^^^^^^^

.. java:method:: @Override public int getItemCount()
   :outertype: NetworkSummaryAdapter

   Get the number of \ :java:ref:`Network`\ s that are stored in the list

   :return: Number of items in the list

getNetworks
^^^^^^^^^^^

.. java:method:: public ArrayList<Network> getNetworks()
   :outertype: NetworkSummaryAdapter

   Get the list of \ :java:ref:`Network`\ s

   :return: List of \ :java:ref:`Network`\ s being shown in the list

getPostCounts
^^^^^^^^^^^^^

.. java:method:: public HashMap<String, Integer> getPostCounts()
   :outertype: NetworkSummaryAdapter

   Get the mappings between \ :java:ref:`Network.id`\  (as a \ :java:ref:`String`\ ) and the number of \ :java:ref:`com.culturemesh.models.Post`\ s in that network.

   :return: Mappings that relate \ :java:ref:`Network`\  ID to the number of \ :java:ref:`com.culturemesh.models.Post`\ s in the network

getUserCounts
^^^^^^^^^^^^^

.. java:method:: public HashMap<String, Integer> getUserCounts()
   :outertype: NetworkSummaryAdapter

   Get the mappings between \ :java:ref:`Network.id`\  (as a \ :java:ref:`String`\ ) and the number of \ :java:ref:`com.culturemesh.models.User`\ s in that network.

   :return: Mappings that relate \ :java:ref:`Network`\  ID to the number of \ :java:ref:`com.culturemesh.models.User`\ s in the network

onBindViewHolder
^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onBindViewHolder(PostViewHolder holder, int position)
   :outertype: NetworkSummaryAdapter

   Fill the fields of \ ``holder``\  with the information stored in the \ :java:ref:`Network`\  at index \ ``position``\  in \ :java:ref:`NetworkSummaryAdapter.networks`\

   :param holder: ViewHolder whose fields to fill in
   :param position: Index of \ :java:ref:`Network`\  in \ :java:ref:`NetworkSummaryAdapter.networks`\  whose information will be used to fill in the fields of \ ``holder``\

onCreateViewHolder
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
   :outertype: NetworkSummaryAdapter

   Create a new \ :java:ref:`NetworkSummaryAdapter.PostViewHolder`\  from the \ :java:ref:`View`\  created by inflating \ :java:ref:`R.layout.network_summary`\

   :param parent: Parent for created \ :java:ref:`View`\  used to create the new \ :java:ref:`NetworkSummaryAdapter.PostViewHolder`\
   :param viewType: Not used
   :return: ViewHolder that has been created using an inflated \ :java:ref:`View`\

