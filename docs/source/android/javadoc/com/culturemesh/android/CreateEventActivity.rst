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

.. java:import:: com.culturemesh.android.models Event

.. java:import:: java.text SimpleDateFormat

.. java:import:: java.util Calendar

.. java:import:: java.util Date

CreateEventActivity
===================

.. java:package:: com.culturemesh.android
   :noindex:

.. java:type:: public class CreateEventActivity extends AppCompatActivity

   Screen through which users can create an event in their currently selected network

Methods
-------
createEvent
^^^^^^^^^^^

.. java:method:: public void createEvent(View v)
   :outertype: CreateEventActivity

   Create an event based on the entered data after validating it

   :param v: The button that was clicked to create the event

isValid
^^^^^^^

.. java:method:: public boolean isValid()
   :outertype: CreateEventActivity

   Check whether the data entered by the user (if any) is valid and complete

   :return: true if the entered data is valid and complete, false otherwise

onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: CreateEventActivity

   Initialize activity with saved state

   :param savedInstanceState: State to use for initialization

showDatePickerDialog
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void showDatePickerDialog(View v)
   :outertype: CreateEventActivity

   Show the calendar dialog to let the user select the event date Intended to be called when the user presses button to set date

   :param v: The button that was clicked to show the date picker

showTimePickerDialog
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void showTimePickerDialog(View v)
   :outertype: CreateEventActivity

   Show the clock dialog to let the user select the event start time Intended to be called when user presses button to set time

   :param v: The button that was clicked to show the time picker

