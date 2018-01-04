package org.codethechange.culturemesh;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.view.View.GONE;


import org.codethechange.culturemesh.models.User;

import java.util.ArrayList;

public class FindNetworkActivity extends AppCompatActivity {

    //TODO: Replace these with Location Objects.
    static String nearLocation;
    static String fromLocation;

    public final int REQUEST_NEW_NEAR_LOCATION = 1;

    //TODO: Replace dummy data with real data
    private static ArrayList<String> dummy = new ArrayList<String>();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /*
     * The {@link SearchManager} that will associate the SearchView with our search config.
     */
    private static SearchManager mSearchManager;

    private Button nearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_network);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.find_network_tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        //Set up search manager
        mSearchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);

        //Set near location button click listener
        nearButton = (Button) findViewById(R.id.near_button);
        nearButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open near activity to choose new near location.
                Intent chooseNewNear = new Intent(FindNetworkActivity.this,
                        ChooseNearLocationActivity.class);
                startActivityForResult(chooseNewNear, REQUEST_NEW_NEAR_LOCATION);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO: Implement how I receive data. Replace dummy stuff with legit stuff.
        nearLocation = data.getStringExtra(ChooseNearLocationActivity.CHOSEN_LOCATION);
        nearButton.setText(nearLocation);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        //TODO: Implement when change nearby location: also consider onResumeFragments()
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_network, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * The fragment for finding the from location.
     */
    public static class FindLocationFragment extends Fragment implements
            SearchView.OnQueryTextListener {


        private ListView searchList;
        private ArrayAdapter<String> adapter;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public FindLocationFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FindLocationFragment newInstance(int sectionNumber) {
            FindLocationFragment fragment = new FindLocationFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Get the intent, verify the action and get the query
            View rootView = inflater.inflate(R.layout.fragment_find_location, container,
                    false);
            SearchView searchView = rootView.findViewById(R.id.from_location_search_view);
            searchView.setOnQueryTextListener(this);
            searchView.setSearchableInfo(mSearchManager.
                    getSearchableInfo(getActivity().getComponentName()));
            searchList = rootView.findViewById(R.id.search_suggestions_list_view);
            //TODO: Remove dummy data
            //TODO: Use abstracted API interface.

            dummy.add("New York, New York, United States");
            dummy.add("Singapore");
            dummy.add("London, United Kingdom");
            dummy.add("Stanford, California, United States");

            adapter = new LocationSearchAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, dummy);
            searchList.setTextFilterEnabled(true);
            searchList.setAdapter(adapter);
            searchList.setOnItemClickListener(new ListView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final ConstraintLayout promptView = (ConstraintLayout)
                            getActivity().findViewById(R.id.prompt_join_network_view);
                    fromLocation = (String) parent.getItemAtPosition(position);
                    TextView fromTV = (TextView)
                            promptView.findViewById(R.id.prompt_from_location_text_view);
                    fromTV.setText(fromLocation.split(",")[0]);
                    TextView nearTV = (TextView)
                            promptView.findViewById(R.id.prompt_near_location_text_view);
                    nearTV.setText(nearLocation.split(",")[0]);
                    promptView.setVisibility(View.VISIBLE);
                    ImageButton cancel = (ImageButton)
                            promptView.findViewById(R.id.cancel_prompt_button);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            promptView.setVisibility(GONE);
                        }
                    });
                }
            });
            return rootView;

        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Toast.makeText(getActivity(), "Changing text", Toast.LENGTH_LONG).show();
            adapter.getFilter().filter(newText);
            if (searchList.getVisibility() == GONE) {
                searchList.setVisibility(View.VISIBLE);
            }
            //searchList.setAdapter(adapter);
            return true;
        }
    }


    /**
     * The fragment for finding language networks.
     */
    public static class FindLanguageFragment extends Fragment implements
            SearchView.OnQueryTextListener {


        private ListView searchList;
        private ArrayAdapter<String> adapter;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public FindLanguageFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FindLanguageFragment newInstance(int sectionNumber) {
            FindLanguageFragment fragment = new FindLanguageFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Get the intent, verify the action and get the query
            View rootView = inflater.inflate(R.layout.fragment_find_language, container,
                    false);
            ArrayList<String> dummyLanguages = new ArrayList<String>();
            SearchView searchView = rootView.findViewById(R.id.from_language_search_view);
            searchView.setOnQueryTextListener(this);
            searchView.setSearchableInfo(mSearchManager.
                    getSearchableInfo(getActivity().getComponentName()));
            searchList = rootView.findViewById(R.id.search_suggestions_list_view);
            //TODO: Remove dummy data
            //TODO: Use abstracted API interface.

            dummyLanguages.add("French");
            dummyLanguages.add("German");
            dummyLanguages.add("English");
            dummyLanguages.add("Cantonese");

            adapter = new LocationSearchAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, dummyLanguages);
            searchList.setTextFilterEnabled(true);
            searchList.setAdapter(adapter);
            searchList.setOnItemClickListener(new ListView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final ConstraintLayout promptView = (ConstraintLayout)
                            getActivity().findViewById(R.id.prompt_join_language_network_view);
                    String fromLanguage = (String) parent.getItemAtPosition(position);
                    TextView fromTV = (TextView)
                            promptView.findViewById(R.id.prompt_from_language_text_view);
                    fromTV.setText(fromLanguage.split(",")[0]);
                    TextView nearTV = (TextView)
                            promptView.findViewById(R.id.prompt_language_near_location_text_view);
                    nearTV.setText(nearLocation.split(",")[0]);
                    promptView.setVisibility(View.VISIBLE);
                    ImageButton cancel = (ImageButton)
                            promptView.findViewById(R.id.cancel_prompt_button);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            promptView.setVisibility(GONE);
                        }
                    });
                }
            });
            return rootView;

        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Toast.makeText(getActivity(), "Changing text", Toast.LENGTH_LONG).show();
            adapter.getFilter().filter(newText);
            if (searchList.getVisibility() == GONE) {
                searchList.setVisibility(View.VISIBLE);
            }
            //searchList.setAdapter(adapter);
            return true;
        }

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return FindLocationFragment.newInstance(0);
            }
            return FindLanguageFragment.newInstance(1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.tab_item_from);
                case 1:
                    return getResources().getString(R.string.tab_item_speaks);
            }
            return null;
        }
    }
}
