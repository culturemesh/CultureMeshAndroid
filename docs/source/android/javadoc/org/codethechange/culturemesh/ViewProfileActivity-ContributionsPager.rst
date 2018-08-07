.. java:import:: android.os AsyncTask

.. java:import:: android.os Bundle

.. java:import:: android.support.design.widget TabLayout

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.support.v4.app FragmentManager

.. java:import:: android.support.v4.app FragmentStatePagerAdapter

.. java:import:: android.support.v4.view PagerAdapter

.. java:import:: android.support.v4.view ViewPager

.. java:import:: android.support.v7.app AppCompatActivity

.. java:import:: android.support.v7.widget Toolbar

.. java:import:: android.widget ImageView

.. java:import:: android.widget TextView

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: com.squareup.picasso Picasso

.. java:import:: org.codethechange.culturemesh.models User

ViewProfileActivity.ContributionsPager
======================================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type::  class ContributionsPager extends FragmentStatePagerAdapter
   :outertype: ViewProfileActivity

   This PagerAdapter returns the correct fragment based on which list the user wishes to see. This could be seeing the list of networks the user is subscribed to, the list of posts the user has written, or the list of events the user has attended.

Constructors
------------
ContributionsPager
^^^^^^^^^^^^^^^^^^

.. java:constructor::  ContributionsPager(FragmentManager fm)
   :outertype: ViewProfileActivity.ContributionsPager

Methods
-------
getCount
^^^^^^^^

.. java:method:: @Override public int getCount()
   :outertype: ViewProfileActivity.ContributionsPager

getItem
^^^^^^^

.. java:method:: @Override public Fragment getItem(int position)
   :outertype: ViewProfileActivity.ContributionsPager

getPageTitle
^^^^^^^^^^^^

.. java:method:: @Override public CharSequence getPageTitle(int position)
   :outertype: ViewProfileActivity.ContributionsPager

