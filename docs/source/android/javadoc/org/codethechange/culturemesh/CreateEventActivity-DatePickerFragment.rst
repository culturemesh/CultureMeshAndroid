.. java:import:: android.app Activity

.. java:import:: android.app DatePickerDialog

.. java:import:: android.app Dialog

.. java:import:: android.content SharedPreferences

.. java:import:: android.support.v4.app DialogFragment

.. java:import:: android.app TimePickerDialog

.. java:import:: android.os Bundle

.. java:import:: android.support.v7.app AppCompatActivity

.. java:import:: android.support.v7.widget Toolbar

.. java:import:: android.text.format DateFormat

.. java:import:: android.view View

.. java:import:: android.widget DatePicker

.. java:import:: android.widget EditText

.. java:import:: android.widget ProgressBar

.. java:import:: android.widget TextView

.. java:import:: android.widget TimePicker

.. java:import:: com.android.volley RequestQueue

.. java:import:: com.android.volley Response

.. java:import:: com.android.volley.toolbox Volley

.. java:import:: org.codethechange.culturemesh.models Event

.. java:import:: java.text SimpleDateFormat

.. java:import:: java.util Calendar

.. java:import:: java.util Date

CreateEventActivity.DatePickerFragment
======================================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
   :outertype: CreateEventActivity

   DatePicker static class that handles operations of the time selection fragment

Methods
-------
getDatePicker
^^^^^^^^^^^^^

.. java:method:: public DatePicker getDatePicker()
   :outertype: CreateEventActivity.DatePickerFragment

   Get the DatePicker

   :return: The DatePicker

getDay
^^^^^^

.. java:method:: public int getDay()
   :outertype: CreateEventActivity.DatePickerFragment

   Get the selected day

   :return: The selected day of the month with the first day represented by 1

getMonth
^^^^^^^^

.. java:method:: public int getMonth()
   :outertype: CreateEventActivity.DatePickerFragment

   Get the selected month

   :return: The selected month as an integer with January as 0 and December as 11

getYear
^^^^^^^

.. java:method:: public int getYear()
   :outertype: CreateEventActivity.DatePickerFragment

   Get the selected year

   :return: The selected year (e.g. 2004 returns the integer 2004)

isSet
^^^^^

.. java:method:: public boolean isSet()
   :outertype: CreateEventActivity.DatePickerFragment

   Check whether the user has set a date

   :return: true if the user has set a date, false otherwise

onCreateDialog
^^^^^^^^^^^^^^

.. java:method:: @Override public Dialog onCreateDialog(Bundle savedInstanceState)
   :outertype: CreateEventActivity.DatePickerFragment

   Called when the fragment is created Sets the initial state of the calendar to the current date and returns the resulting DatePickerDialog to display

   :param savedInstanceState: Last saved state of fragment
   :return: DatePickerDialog to display to the user

onDateSet
^^^^^^^^^

.. java:method:: public void onDateSet(DatePicker view, int year, int month, int day)
   :outertype: CreateEventActivity.DatePickerFragment

   When user sets the date, show their choice in the eventDate textView

   :param view: The date picker shown via the fragment
   :param year: Year the user chose
   :param month: Month the user chose
   :param day: Day the user chose

