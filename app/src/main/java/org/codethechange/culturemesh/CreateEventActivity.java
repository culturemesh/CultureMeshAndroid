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
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity {

    // TODO: Add comment to explain CreateEventActivity onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Show the clock dialog to let the user select the event start time
     * Intended to be called when user presses button to set time
     * @param v TODO: Add comment to explain CreateEventActivity.showTimePickerDialog's parameter
     */
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * Show the calendar dialog to let the user select the event date
     * Intended to be called when the user presses button to set date
     * @param v TODO: Add comment to explain CreateEventActivity.showDatePickerDialog's parameter
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * TimePicker static class that handles operations of the time selection fragment
     */
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        /**
         * Called when the fragment is created
         * Sets the initial state of the clock to the current time and returns the resulting
         * TimePickerDialog to display
         * @param savedInstanceState TODO: Comment to explain CreateEventActivity.TimePickerFragment.onCreateDialog's parameter
         * @return TimePickerDialog to display
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        /**
         * When user sets the time, show their choice in the eventTime textView
         * @param view TODO: Comment to explain view parameter to CreateEventActivity.TimePickerFragment.onTimeSet
         * @param hourOfDay Hour the user set
         * @param minute Minute the user set
         */
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO: Locale adjustments for time (e.g. AM/PM vs 24-hr)
            String time = hourOfDay + ":" + minute;
            // SOURCE: https://stackoverflow.com/questions/26917564/set-textview-to-time-from-timepicker-android?rq=1
            // Get eventTime from the activity
            TextView textView = getActivity().findViewById(R.id.eventTime);
            // Set eventTime to show the selected time
            textView.setText(time);
        }
    }

    /**
     * DatePicker static class that handles operations of the time selection fragment
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        /**
         * Called when the fragment is created
         * Sets the initial state of the calendar to the current date and returns the resulting
         * DatePickerDialog to display
         * @param savedInstanceState TODO: Comment to explain CreateEventActivity.TimePickerFragment.onCreateDialog's parameter
         * @return DatePickerDialog to display to the user
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        /**
         * When user sets the date, show their choice in the eventDate textView
         * @param view TODO: Comment to explain view parameter to CreateEventActivity.DatePickerFragment.onDateSet
         * @param year Year the user chose
         * @param month Month the user chose
         * @param day Day the user chose
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // TODO: Locale adjustments for date
            month ++;   // It seems months start at 0
            String date = month + "/" + day + "/" + year;
            // SOURCE: https://stackoverflow.com/questions/26917564/set-textview-to-time-from-timepicker-android?rq=1
            // Get eventDate textView from activity
            TextView textView = getActivity().findViewById(R.id.eventDate);
            // Set eventDate to show the selected date
            textView.setText(date);
        }
    }

}
