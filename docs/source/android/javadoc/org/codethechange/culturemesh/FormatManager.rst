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

FormatManager
=============

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class FormatManager implements ListenableEditText.onSelectionChangedListener

   Created by Drew Gregory on 3/26/18. This class provides a little decomposition from CreatePost/SpeciticPostActivity in that it handles all the formatting involved in writing posts/post replies. The supported formatting is: - bold - italic - links This formatting is embedded in the SpannableStrings that EditTexts can produce and maintain. This manager will also handle the tedious tasks of updating the toggle icons and maintaining state. When the user is done formatting and wants to publish their post/post reply, call the toString(), which will convert the spannable to a string with the proper tags as specified by Ian Nottage: \ **Bold text**\  \ *Italic text*\  Link text

Fields
------
START
^^^^^

.. java:field:: final int START
   :outertype: FormatManager

toggleIcons
^^^^^^^^^^^

.. java:field:: @NonNull  SparseArray<int[]> toggleIcons
   :outertype: FormatManager

Constructors
------------
FormatManager
^^^^^^^^^^^^^

.. java:constructor::  FormatManager(ListenableEditText content, IconUpdateListener listener, int boldIcon, int italicIcon, int linkIcon)
   :outertype: FormatManager

Methods
-------
abbreviateNumber
^^^^^^^^^^^^^^^^

.. java:method:: public static String abbreviateNumber(long number)
   :outertype: FormatManager

   In the interest of screen space and accessibility, we will format the number to have a magnitude suffix instead of the exact number.

   :param number: exact number, in floating point if necessary.
   :return: Formatted String representing number magnitude (e.x. 100K)

fromHtml
^^^^^^^^

.. java:method:: @SuppressWarnings public static Spanned fromHtml(String html)
   :outertype: FormatManager

   Different Android versions use different fromHtml method signatures. Sourced from https://stackoverflow.com/questions/37904739/html-fromhtml-deprecated-in-android-n

   :param html:

onSelectionChanged
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public void onSelectionChanged(int selStart, int selEnd)
   :outertype: FormatManager

parseText
^^^^^^^^^

.. java:method:: public static Spanned parseText(String formattedText, String colorString)
   :outertype: FormatManager

   This function converts the CultureMesh tags into a spannable string for textview.

   :param formattedText: should only have \ ``<b></b>, <link></link>, <i></i> or [b][/b][link][/link][i][/i]``\
   :param colorString: the link color in RGB. Some text has different color backgrounds.
   :return: Spannable to be passed to TextView.

setBold
^^^^^^^

.. java:method::  void setBold()
   :outertype: FormatManager

   This method will set the currently selected text to bold.

setItalic
^^^^^^^^^

.. java:method::  void setItalic()
   :outertype: FormatManager

   This method will set the currently selected text to italic

setLink
^^^^^^^

.. java:method::  void setLink()
   :outertype: FormatManager

   This method will set the currently selected text to a link.

toString
^^^^^^^^

.. java:method:: public String toString()
   :outertype: FormatManager

   Gets the EditText content in the desired tag format. See comment above.

