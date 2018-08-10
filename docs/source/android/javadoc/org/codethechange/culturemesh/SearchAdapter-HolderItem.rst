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

.. java:import:: org.codethechange.culturemesh.models Language

.. java:import:: org.codethechange.culturemesh.models Location

.. java:import:: java.util ArrayList

.. java:import:: java.util Collection

.. java:import:: java.util List

SearchAdapter.HolderItem
========================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type::  class HolderItem
   :outertype: SearchAdapter

   Keeping views accessible saves calls to findViewById, which is a performance bottleneck. This is exactly why we have RecyclerView!

Fields
------
itemName
^^^^^^^^

.. java:field::  TextView itemName
   :outertype: SearchAdapter.HolderItem

numUsers
^^^^^^^^

.. java:field::  TextView numUsers
   :outertype: SearchAdapter.HolderItem

peopleIcon
^^^^^^^^^^

.. java:field::  ImageView peopleIcon
   :outertype: SearchAdapter.HolderItem

