.. CultureMesh Android documentation master file, created by
   sphinx-quickstart on Mon Jul 23 15:18:26 2018.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

=====================================
Documentation for CultureMesh Android
=====================================

------------
Introduction
------------

This is the developer documentation for the Android app for
`CultureMesh <https://culturemesh.com/>`_. This app remains in
development, so this documentation is geared toward developers, not
end users.

This documentation and the source code for the app were created by
`Stanford University's Code the Change <https://codethechange.stanford.edu/>`_
for CultureMesh.

.. toctree::
   :maxdepth: 2
   :caption: Contents:

   userFlow
   codeStructure
   codeRef
   contributing

---------------
Getting Started
---------------

Getting the Latest Code
=======================

1. Clone the
   `GitHub repository <https://github.com/DrewGregory/CultureMeshAndroid>`_
   to your local machine:

   .. code-block:: console

     $ git clone https://github.com/DrewGregory/CultureMeshAndroid

2. Switch to your desired branch. This will probably be either
   ``master``, which holds the most recent release, or ``develop``,
   which holds the current development version. For example:

   .. code-block:: console

     $ git checkout master

Missing Information
===================

For security reasons, some information is missing from the code repository:

* CultureMesh API Key: Stored in :java:ref:`Credentials`. The
  ``Credentials.java`` file must be created with the key in a public field
  ``APIKey``.
* Fabric API Key and Secret: Stored in ``app/fabric.properties``. See template
  below for the structure:

  .. code-block:: properties

    apiSecret=<API Secret>
    apiKey=<API Key>

  Fill in ``<API Secret>`` and ``<API Key>`` with the appropriate values.

Running the App
===============

Open the root of the repository in
`Android Studio <https://developer.android.com/studio/install>`_. Let
Android Studio index the repository, and let Gradle install dependencies.
Then run the app by clicking the play
button in the upper right. You may have to disable ``Instant Run``
in order to successfully use ``Fabric`` with the API key in
``app/fabric.properties``.

------------------
Indices and Tables
------------------

* :ref:`genindex`
* :ref:`search`
