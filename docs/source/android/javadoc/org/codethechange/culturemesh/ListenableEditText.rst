.. java:import:: android.content Context

.. java:import:: android.util AttributeSet

.. java:import:: android.widget EditText

ListenableEditText
==================

.. java:package:: com.culturemesh
   :noindex:

.. java:type:: public class ListenableEditText extends EditText

   This is a custom EditText that allows us to listen for changes in cursor position. \ :java:ref:`CreatePostActivity`\  uses this view so that the format toggle buttons can update their settings when a new near_region in the edit text is selected.

Fields
------
mListener
^^^^^^^^^

.. java:field::  onSelectionChangedListener mListener
   :outertype: ListenableEditText

Constructors
------------
ListenableEditText
^^^^^^^^^^^^^^^^^^

.. java:constructor:: public ListenableEditText(Context context)
   :outertype: ListenableEditText

   {@inheritDoc}

   :param context: {@inheritDoc}

ListenableEditText
^^^^^^^^^^^^^^^^^^

.. java:constructor:: public ListenableEditText(Context context, AttributeSet attrs)
   :outertype: ListenableEditText

   {@inheritDoc}

   :param context: {@inheritDoc}
   :param attrs: {@inheritDoc}

ListenableEditText
^^^^^^^^^^^^^^^^^^

.. java:constructor:: public ListenableEditText(Context context, AttributeSet attrs, int defStyleAttr)
   :outertype: ListenableEditText

   {@inheritDoc}

   :param context: {@inheritDoc}
   :param attrs: {@inheritDoc}
   :param defStyleAttr: {@inheritDoc}

Methods
-------
onSelectionChanged
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected void onSelectionChanged(int selStart, int selEnd)
   :outertype: ListenableEditText

   When the selection changes, if it is due to the user typing a character, \ :java:ref:`ListenableEditText.mListener.onSelectionChanged(int,int)`\  is called with the provided parameters. Otherwise, the superclass method \ :java:ref:`EditText.onSelectionChanged(int,int)`\  is called with the parameters.

   :param selStart: TODO: What is this?
   :param selEnd: TODO: What is this?

setOnSelectionChangedListener
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void setOnSelectionChangedListener(onSelectionChangedListener listener)
   :outertype: ListenableEditText

   Set the listener to the provided parameter

   :param listener: Listener to use when text selection changes

