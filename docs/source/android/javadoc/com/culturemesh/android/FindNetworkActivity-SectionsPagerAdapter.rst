.. java:import:: android.app AlertDialog

.. java:import:: android.app SearchManager

.. java:import:: android.content Context

.. java:import:: android.content DialogInterface

.. java:import:: android.content Intent

.. java:import:: android.support.design.widget TabLayout

.. java:import:: android.support.v4.app Fragment

.. java:import:: android.support.v4.app FragmentManager

.. java:import:: android.support.v4.app FragmentPagerAdapter

.. java:import:: android.support.v4.view ViewPager

.. java:import:: android.os Bundle

.. java:import:: android.view LayoutInflater

.. java:import:: android.view Menu

.. java:import:: android.view MenuItem

.. java:import:: android.view View

.. java:import:: android.view ViewGroup

.. java:import:: android.widget AdapterView

.. java:import:: android.widget Button

.. java:import:: android.widget ListView

.. java:import:: android.widget SearchView

.. java:import:: com.android.volley Request

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: java.util List

.. java:import:: com.culturemesh.android.models Language

.. java:import:: com.culturemesh.android.models Location

.. java:import:: com.culturemesh.android.models Network

FindNetworkActivity.SectionsPagerAdapter
========================================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public class SectionsPagerAdapter extends FragmentPagerAdapter
   :outertype: FindNetworkActivity

   A \ :java:ref:`FragmentPagerAdapter`\  that returns a fragment corresponding to one of the two available tabs: \ ``From``\ , for location-based networks, and \ ``Speaks``\ , for language-based networks.

Constructors
------------
SectionsPagerAdapter
^^^^^^^^^^^^^^^^^^^^

.. java:constructor::  SectionsPagerAdapter(FragmentManager fm)
   :outertype: FindNetworkActivity.SectionsPagerAdapter

   {@inheritDoc}

   :param fm: {@inheritDoc}

Methods
-------
getCount
^^^^^^^^

.. java:method:: @Override public int getCount()
   :outertype: FindNetworkActivity.SectionsPagerAdapter

   Always returns \ ``2``\  because there are 2 tabs

   :return: Always \ ``2``\

getItem
^^^^^^^

.. java:method:: @Override public Fragment getItem(int position)
   :outertype: FindNetworkActivity.SectionsPagerAdapter

   Get the appropriate fragment depending on which tab is selected

   :param position: Either \ ``0``\ , for \ ``near``\  or \ ``1``\ , for \ ``speaks``\
   :return: \ :java:ref:`FindLocationFragment`\  for \ ``position=1``\ , \ :java:ref:`FindLanguageFragment`\  otherwise.

getPageTitle
^^^^^^^^^^^^

.. java:method:: @Override public CharSequence getPageTitle(int position)
   :outertype: FindNetworkActivity.SectionsPagerAdapter

   Get the titles for each tab

   :param position: Position of tab to get name of (\ ``0``\  or \ ``1``\ )
   :return: Reference to name of tab

