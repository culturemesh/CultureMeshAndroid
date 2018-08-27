.. java:import:: android.content Intent

.. java:import:: android.support.v7.app AppCompatActivity

RedirectableAppCompatActivity
=============================

.. java:package:: com.culturemesh
   :noindex:

.. java:type:: public abstract class RedirectableAppCompatActivity extends AppCompatActivity

   Superclass for all classes that support redirection instructions from the activity they are launched from. For instance, if \ ``A``\  launches \ ``B``\ , which is a subclass of \ :java:ref:`RedirectableAppCompatActivity`\ , \ ``A``\  can give \ ``B``\  instructions to launch \ ``C``\  when it finishes. If instead \ ``Z``\  launches \ ``B``\ , it can give \ ``B``\  instructions to next launch \ ``X``\ .

Methods
-------
onDestroy
^^^^^^^^^

.. java:method:: @Override protected void onDestroy()
   :outertype: RedirectableAppCompatActivity

   {@inheritDoc} Also uses the extras in the launching \ :java:ref:`Intent`\  to decide which Activity to launch next

   **See also:** :java:ref:`Redirection`

