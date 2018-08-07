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

CreateEventActivity.TimePickerFragment
======================================

.. java:package:: org.codethechange.culturemesh
   :noindex:

.. java:type:: public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
   :outertype: CreateEventActivity

   TimePicker static class that handles operations of the time selection fragment

Methods
-------
getHour
^^^^^^^

.. java:method:: public int getHour()
   :outertype: CreateEventActivity.TimePickerFragment

   Return the selected hour

   :return: The selected hour

getMinute
^^^^^^^^^

.. java:method:: public int getMinute()
   :outertype: CreateEventActivity.TimePickerFragment

   Return the selected minute

   :return: The selected minute

getTimePicker
^^^^^^^^^^^^^

.. java:method:: public TimePicker getTimePicker()
   :outertype: CreateEventActivity.TimePickerFragment

   Return the TimePicker

   :return: the TimePicker

isSet
^^^^^

.. java:method:: public boolean isSet()
   :outertype: CreateEventActivity.TimePickerFragment

   Check whether the user has set a time yet

   :return: true if the user has set the time, false otherwise

onCreateDialog
^^^^^^^^^^^^^^

.. java:method:: @Override public Dialog onCreateDialog(Bundle savedInstanceState)
   :outertype: CreateEventActivity.TimePickerFragment

   Called when the fragment is created Sets the initial state of the clock to the current time and returns the resulting TimePickerDialog to display

   :param savedInstanceState: Last saved state of fragment
   :return: TimePickerDialog to display

onTimeSet
^^^^^^^^^

.. java:method:: public void onTimeSet(TimePicker view, int inHour, int inMin)
   :outertype: CreateEventActivity.TimePickerFragment

   When user sets the time, show their choice in the eventTime textView

   :param view: The time picker shown via the fragment
   :param inHour: Hour the user set
   :param inMin: Minute the user set

