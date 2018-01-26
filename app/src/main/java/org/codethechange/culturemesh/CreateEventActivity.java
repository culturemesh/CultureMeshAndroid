package org.codethechange.culturemesh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.Language;
import org.codethechange.culturemesh.models.User;

import java.util.Calendar;
import java.util.Date;

public class CreateEventActivity extends AppCompatActivity {
// TODO: Have address search in google maps
    private DatePickerFragment dateFrag;
    private TimePickerFragment timeFrag;
    private EditText nameRef;
    private EditText addressRef;
    private EditText descriptionRef;

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

    public void createEvent(View v) {
        Calendar c = Calendar.getInstance();
        int year = dateFrag.getYear();
        int month = dateFrag.getMonth();
        int day = dateFrag.getDay();
        int hour = timeFrag.getHour();
        int minute = timeFrag.getMinute();
        c.set(year, month, day, hour, minute);

        Date date = c.getTime();
        String name = nameRef.getText().toString();
        String address = addressRef.getText().toString();
        String description = descriptionRef.getText().toString();
        // TODO: Let the user select a language (from a menu? arbitrarily?)
        Language lang = new Language("TempLanguage");
        // TODO: Get the User object for the current user
        User author = null;
        Event event = new Event(name, description, date, author, address, lang);
        API.Post.event(event);
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
        }

        public TimePicker getTimePicker() {
            return timePicker;
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }

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

            datePicker = view;
        }

        // SOURCE: https://stackoverflow.com/questions/27039393/android-dialogfragment-get-reference-to-date-picker
        public DatePicker getDatePicker() {
            return datePicker;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }

        public boolean isSet() {
            return isSet;
        }
    }

}
