Listable
========

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public interface Listable

   Interface for objects that need to be listed in the user interface.

Fields
------
MAX_CHARS
^^^^^^^^^

.. java:field::  int MAX_CHARS
   :outertype: Listable

ellipses
^^^^^^^^

.. java:field::  String ellipses
   :outertype: Listable

Methods
-------
getListableName
^^^^^^^^^^^^^^^

.. java:method::  String getListableName()
   :outertype: Listable

   Get a label (maximum of \ :java:ref:`Listable.MAX_CHARS`\  characters long) to display as an identifier for the object.

   :return: Displayable name for the object, which must be less than or equal to \ :java:ref:`Listable.MAX_CHARS`\  characters long

