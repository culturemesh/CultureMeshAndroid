.. java:import:: android.content Intent

.. java:import:: android.os Bundle

.. java:import:: android.view View

AboutActivity
=============

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public class AboutActivity extends DrawerActivity

   Activity for displaying author attributions, copyright notices, and version information on an \ ``About``\  page

Methods
-------
onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: AboutActivity

   When the activity is created, it pulls what to display from \ :java:ref:`R.layout.activity_about`\ . It does not have a \ ``setSupportActionBar(toolbar)``\  call because that is handled by \ :java:ref:`DrawerActivity`\ . The toolbar MUST have an ID of \ ``action_bar``\ .

   :param savedInstanceState: Passed to superclass onCreate method

openLegal
^^^^^^^^^

.. java:method:: public void openLegal(View v)
   :outertype: AboutActivity

   Open \ :java:ref:`Acknowledgements`\  activity to display legally required attributions for the open-source libraries we use

   :param v: The \ :java:ref:`View`\  of the button clicked on to run this method. Not used.

