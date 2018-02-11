package org.codethechange.culturemesh;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.Language;
import org.codethechange.culturemesh.models.User;

import java.util.Calendar;
import java.util.Date;

public class CreateEventActivity extends AppCompatActivity {
// TODO: Have address search in google maps (Requires API Key?)
    private DatePickerFragment dateFrag;
    private TimePickerFragment timeFrag;
    private TextView dateRef;
    private TextView timeRef;
    private EditText nameRef;
    private EditText addressRef;
    private EditText descriptionRef;

    private Activity myActivity = this;

    /**
     * Initialize activity with saved state
     * @param savedInstanceState State to use for initialization
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameRef = findViewById(R.id.eventName);
        addressRef = findViewById(R.id.eventAddress);
        descriptionRef = findViewById(R.id.eventDescription);
        dateRef = findViewById(R.id.eventDate);
        timeRef = findViewById(R.id.eventTime);
    }

    /**
     * Show the clock dialog to let the user select the event start time
     * Intended to be called when user presses button to set time
     * @param v The button that was clicked to show the time picker
     */
    public void showTimePickerDialog(View v) {
        timeFrag = new TimePickerFragment();
        timeFrag.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * Show the calendar dialog to let the user select the event date
     * Intended to be called when the user presses button to set date
     * @param v The button that was clicked to show the date picker
     */
    public void showDatePickerDialog(View v) {
        dateFrag = new DatePickerFragment();
        dateFrag.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Create an event based on the entered data after validating it
     * @param v The button that was clicked to create the event
     */
    public void createEvent(View v) {
        if (isValid()) {
            // Get date and time settings
            Calendar c = Calendar.getInstance();
            int year = dateFrag.getYear();
            int month = dateFrag.getMonth();
            int day = dateFrag.getDay();
            int hour = timeFrag.getHour();
            int minute = timeFrag.getMinute();
            c.set(year, month, day, hour, minute);

            // Declare variables for Event() arguments
            Date date = c.getTime();
            String name = nameRef.getText().toString();
            String address = addressRef.getText().toString();
            String description = descriptionRef.getText().toString();
            // TODO: Let the user select a language (from a menu? arbitrarily?)
            Language lang = new Language("TempLanguage");
            // TODO: Get the User object for the current user
            User author = null;

            // Create Event
            Event event = new Event(name, description, date, author, address, lang);
            // POST Event with AsyncTask
            new PostEvent().execute(event);
        }
    }

    /**
     * Check whether the data entered by the user (if any) is valid and complete
     * @return true if the entered data is valid and complete, false otherwise
     */
    public boolean isValid() {
        // SOURCE: https://stackoverflow.com/questions/18225365/show-error-on-the-tip-of-the-edit-text-android
        if (dateFrag == null || !dateFrag.isSet()) {
            dateRef.requestFocus();
            dateRef.setError(getString(R.string.createEvent_missingDate));
            return false;
        } else {
            // SOURCE: https://stackoverflow.com/questions/10206799/remove-error-from-edittext
            dateRef.setError(null);
        }

        if (timeFrag == null || !timeFrag.isSet()) {
            timeRef.requestFocus();
            timeRef.setError(getString(R.string.createEvent_missingTime));
            return false;
        } else {
            timeRef.setError(null);
        }

        if (nameRef.getText().toString().isEmpty()) {
            nameRef.setError(getString(R.string.createEvent_missingName));
            return false;
        } else if (descriptionRef.getText().toString().isEmpty()) {
            descriptionRef.setError(getString(R.string.createEvent_missingDescription));
            return false;
        } else if (addressRef.getText().toString().isEmpty()) {
            addressRef.setError(getString(R.string.createEvent_missingAddress));
            return false;
        } else
            return true;
    }

    /**
     * TimePicker static class that handles operations of the time selection fragment
     */
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        private TimePicker timePicker;
        private int hour;
        private int minute;
        private boolean isSet;

        /**
         * Called when the fragment is created
         * Sets the initial state of the clock to the current time and returns the resulting
         * TimePickerDialog to display
         * @param savedInstanceState Last saved state of fragment
         * @return TimePickerDialog to display
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            // Initially, record that user has not set time
            isSet = false;

            // Create a new instance of TimePickerDialog
            TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, hour, minute,
                                            DateFormat.is24HourFormat(getActivity()));
            return dialog;
        }

        /**
         * When user sets the time, show their choice in the eventTime textView
         * @param view The time picker shown via the fragment
         * @param inHour Hour the user set
         * @param inMin Minute the user set
         */
        public void onTimeSet(TimePicker view, int inHour, int inMin) {
            // Required because TimePicker.getHour() introduced after API 19
            hour = inHour;
            minute = inMin;
            // Checks what time format user has requested in their settings and follows that
            boolean is24Hr = DateFormat.is24HourFormat(getActivity());
            String amPm = "";
            if (!is24Hr) {
                if (inHour > 12) {
                    amPm = " PM";
                    inHour -= 12;
                } else {
                    amPm = " AM";
                }
            }
            String time = inHour + ":" + inMin + amPm;
            // SOURCE: https://stackoverflow.com/questions/26917564/set-textview-to-time-from-timepicker-android?rq=1
            // Get eventTime from the activity
            TextView textView = getActivity().findViewById(R.id.eventTime);
            // Set eventTime to show the selected time
            textView.setText(time);
            timePicker = view;

            isSet = true;
        }

        /**
         * Return the TimePicker
         * @return the TimePicker
         */
        public TimePicker getTimePicker() {
            return timePicker;
        }

        /**
         * Return the selected hour
         * @return The selected hour
         */
        public int getHour() {
            return hour;
        }

        /**
         * Return the selected minute
         * @return The selected minute
         */
        public int getMinute() {
            return minute;
        }

        /**
         * Check whether the user has set a time yet
         * @return true if the user has set the time, false otherwise
         */
        public boolean isSet() {
            return isSet;
        }
    }

    /**
     * DatePicker static class that handles operations of the time selection fragment
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private DatePicker datePicker;
        private int year;
        private int month;
        private int day;
        private boolean isSet;

        /**
         * Called when the fragment is created
         * Sets the initial state of the calendar to the current date and returns the resulting
         * DatePickerDialog to display
         * @param savedInstanceState Last saved state of fragment
         * @return DatePickerDialog to display to the user
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            // Initially, record that user has not chosen a date
            isSet = false;

            // Create a new instance of DatePickerDialog
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            return dialog;
        }

        /**
         * When user sets the date, show their choice in the eventDate textView
         * @param view The date picker shown via the fragment
         * @param year Year the user chose
         * @param month Month the user chose
         * @param day Day the user chose
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Create calendar from provided ints
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            // Create a DateFormat object that is locale-adjusted based on the current context
            java.text.DateFormat df = DateFormat.getDateFormat(getContext());
            // Use the DateFormat object to create a localized date from the calendar
            String localizedDate = df.format(c.getTime());
            // SOURCE: https://stackoverflow.com/questions/26917564/set-textview-to-time-from-timepicker-android?rq=1
            // Get eventDate textView from activity
            TextView textView = getActivity().findViewById(R.id.eventDate);
            // Set eventDate to show the selected date
            textView.setText(localizedDate);

            isSet = true;

            datePicker = view;
        }

        // SOURCE: https://stackoverflow.com/questions/27039393/android-dialogfragment-get-reference-to-date-picker

        /**
         * Get the DatePicker
         * @return The DatePicker
         */
        public DatePicker getDatePicker() {
            return datePicker;
        }

        /**
         * Get the selected year
         * @return The selected year (e.g. 2004 returns the integer 2004)
         */
        public int getYear() {
            return year;
        }

        /**
         * Get the selected month
         * @return The selected month as an integer with January as 0 and December as 11
         */
        public int getMonth() {
            return month;
        }

        /**
         * Get the selected day
         * @return The selected day of the month with the first day represented by 1
         */
        public int getDay() {
            return day;
        }

        /**
         * Check whether the user has set a date
         * @return true if the user has set a date, false otherwise
         */
        public boolean isSet() {
            return isSet;
        }
    }

    /**
     * AsyncTask class to handle network latency when POSTing event
     */
    private class PostEvent extends AsyncTask<Event, Integer, NetworkResponse> {

        ProgressBar progressBar;

        /**
         * In the background, POST an event
         * @param events Arbitrary number of events, the first of which will be POSTed
         * @return NetworkResponse containing the results of the network operation
         */
        @Override
        protected NetworkResponse doInBackground(Event... events) {
            return API.Post.event(events[0]);
        }

        /**
         * Over the course of the POST operation, provided progress updates (nonfunctional)
         * @param values Values indicating progress
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Uncomment when getting status of API
            // progressBar.setProgress(values[0]);
        }

        /**
         * Makes the progress bar indeterminate
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.eventPostProgressBar);
            progressBar.setIndeterminate(true); // Only because cannot get status from API
        }

        /**
         * If POSTing succeeded: Closes the activity and returns the user to the previous screen
         * If POSTing failed: Displays error dialog and returns user to composing screen
         * @param response Status of doInBackground method that represents whether POSTing succeeded
         */
        @Override
        protected void onPostExecute(NetworkResponse response) {
            super.onPostExecute(response);
            // SOURCE: https://stackoverflow.com/questions/4038479/android-go-back-to-previous-activity
            if (response.fail()) {
                response.showErrorDialog(myActivity);
                progressBar.setIndeterminate(false);
            } else {
                finish();
            }
        }
    }
}
