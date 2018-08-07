.. java:import:: android.app Dialog

.. java:import:: android.content DialogInterface

.. java:import:: android.graphics Point

.. java:import:: android.os Bundle

.. java:import:: android.support.annotation NonNull

.. java:import:: android.support.constraint ConstraintLayout

.. java:import:: android.support.design.widget BottomSheetBehavior

.. java:import:: android.support.design.widget BottomSheetDialog

.. java:import:: android.support.design.widget BottomSheetDialogFragment

.. java:import:: android.support.design.widget CoordinatorLayout

.. java:import:: android.support.v7.app AlertDialog

.. java:import:: android.support.v7.widget LinearLayoutManager

.. java:import:: android.support.v7.widget RecyclerView

.. java:import:: android.util Log

.. java:import:: android.view View

.. java:import:: android.widget ArrayAdapter

.. java:import:: android.widget FrameLayout

.. java:import:: android.widget TextView

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: org.codethechange.culturemesh.models User

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util Objects

ViewUsersModalSheetFragment
===========================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class ViewUsersModalSheetFragment extends BottomSheetDialogFragment

   Created By Drew Gregory on 03/30/18. This shows the subscribed users in the network using a modal bottom sheet https://material.io/guidelines/components/bottom-sheets.html#bottom-sheets-modal-bottom-sheets Also, inspiration from the following blog posts: - https://android-developers.googleblog.com/2016/02/android-support-library-232.html - https://code.tutsplus.com/articles/how-to-use-bottom-sheets-with-the-design-support-library--cms-26031

Fields
------
USER_NAMES
^^^^^^^^^^

.. java:field:: public static final String USER_NAMES
   :outertype: ViewUsersModalSheetFragment

queue
^^^^^

.. java:field::  RequestQueue queue
   :outertype: ViewUsersModalSheetFragment

Methods
-------
setupDialog
^^^^^^^^^^^

.. java:method:: @Override public void setupDialog(Dialog dialog, int style)
   :outertype: ViewUsersModalSheetFragment

