.. java:import:: android.content Context

.. java:import:: android.support.annotation LayoutRes

.. java:import:: android.support.annotation NonNull

.. java:import:: android.support.annotation Nullable

.. java:import:: android.util Log

.. java:import:: android.view LayoutInflater

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget ArrayAdapter

.. java:import:: android.widget Filter

.. java:import:: android.widget Filterable

.. java:import:: android.widget ImageView

.. java:import:: android.widget TextView

.. java:import:: com.culturemesh.android.models Language

.. java:import:: com.culturemesh.android.models Location

.. java:import:: java.util ArrayList

.. java:import:: java.util Collection

.. java:import:: java.util List

SearchAdapter
=============

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public class SearchAdapter<T extends Listable> extends ArrayAdapter<T> implements Filterable

   Populates a displayed list with items

   :param <T>: Type of item to put in the list

Constructors
------------
SearchAdapter
^^^^^^^^^^^^^

.. java:constructor:: public SearchAdapter(Context context, int resource, int listViewID, List<T> items)
   :outertype: SearchAdapter

   Initialize instance fields with provided parameters

   :param context: {@inheritDoc}
   :param resource: {@inheritDoc}
   :param listViewID: Identifier for list the adapter will populate
   :param items: {@inheritDoc}

SearchAdapter
^^^^^^^^^^^^^

.. java:constructor::  SearchAdapter(Context context, int resource, int listViewID)
   :outertype: SearchAdapter

   Initialize context variables without a starting list

   :param context: application context
   :param resource: int resource layout id

Methods
-------
addAll
^^^^^^

.. java:method:: @Override public void addAll(Collection<? extends T> collection)
   :outertype: SearchAdapter

   Add all items in a \ :java:ref:`Collection`\  to the list of items the adapter displays in the list

   :param collection: Items to add to the list

clear
^^^^^

.. java:method:: @Override public void clear()
   :outertype: SearchAdapter

   Clears the list of all items

getItem
^^^^^^^

.. java:method:: @Nullable @Override public T getItem(int position)
   :outertype: SearchAdapter

   Get the item associated with the list entry at a certain position

   :param position: Position of list item
   :return: The object represented at the specified position

getView
^^^^^^^

.. java:method:: @NonNull @Override public View getView(int position, View convertView, ViewGroup parent)
   :outertype: SearchAdapter

   Get a \ :java:ref:`View`\  for the list

   :param position: Position of list element to get the \ :java:ref:`View`\  for
   :param convertView: \ :java:ref:`View`\  inflated from \ :java:ref:`R.layout.network_list_item`\  that will represent the list entry
   :param parent: Parent of the created \ :java:ref:`View`\
   :return: Inflated \ :java:ref:`View`\  for an element of the list

