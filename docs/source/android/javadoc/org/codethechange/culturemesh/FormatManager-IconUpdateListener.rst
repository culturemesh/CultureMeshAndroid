.. java:import:: android.graphics Typeface

.. java:import:: android.os Build

.. java:import:: android.support.annotation NonNull

.. java:import:: android.text Html

.. java:import:: android.text SpannableStringBuilder

.. java:import:: android.text Spanned

.. java:import:: android.text.style StyleSpan

.. java:import:: android.text.style URLSpan

.. java:import:: android.util Log

.. java:import:: android.util SparseArray

.. java:import:: android.util SparseBooleanArray

FormatManager.IconUpdateListener
================================

.. java:package:: com.culturemesh
   :noindex:

.. java:type:: public interface IconUpdateListener
   :outertype: FormatManager

Methods
-------
updateIconToggles
^^^^^^^^^^^^^^^^^

.. java:method::  void updateIconToggles(SparseBooleanArray formTogState, SparseArray<int[]> toggleIcons)
   :outertype: FormatManager.IconUpdateListener

   This method will require the parent activity to update the toggle button icons.

   :param formTogState: a SparseBooleanArray (HashMap) with int as key and boolean as value key: int id of toggleButton View we are using. value: true if toggled, false if not toggled.
   :param toggleIcons: a SparseArray (HashMap) with int as key and int[] as value. key: int id of toggleButton View we are using. value: int[0] being untoggled icon, int[1] being toggled icon.

