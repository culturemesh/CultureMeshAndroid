package org.codethechange.culturemesh;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import static android.view.View.GONE;
import java.util.List;

import org.codethechange.culturemesh.models.Language;
import org.codethechange.culturemesh.models.Location;
import org.codethechange.culturemesh.models.NearLocation;
import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.Place;

public class FindNetworkActivity extends DrawerActivity {

    static Location near;
    public final int REQUEST_NEW_NEAR_LOCATION = 1;

    static RequestQueue queue;

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

    /**
     * The {@link SearchManager} that will associate the SearchView with our search config.
     */
    private static SearchManager mSearchManager;

    private Button nearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_network);
        queue = Volley.newRequestQueue(this);
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
        if (resultCode == ChooseNearLocationActivity.RESULT_OK) {
            near = (Location) data.getSerializableExtra(ChooseNearLocationActivity.CHOSEN_PLACE);
            nearButton.setText(near.getListableName());
        }
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
        private SearchAdapter<Location> adapter;
        private SearchView searchView;

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

            searchView = rootView.findViewById(R.id.from_location_search_view);
            searchView.setOnQueryTextListener(this);
            searchView.setSearchableInfo(mSearchManager.
                    getSearchableInfo(getActivity().getComponentName()));
            searchList = rootView.findViewById(R.id.search_suggestions_list_view);
            //TODO: Remove dummy data
            //TODO: Use abstracted API interface.

            adapter = new SearchAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, R.id.location_language_name_list_view);
            searchList.setTextFilterEnabled(true);
            searchList.setAdapter(adapter);
            searchList.setOnItemClickListener(new ListView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Location from = adapter.getItem(position);
                    if (near == null) {
                        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                        dialog.setTitle(getContext().getString(R.string.error));
                        dialog.setMessage(getContext().getString(R.string.no_near_loc));
                        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, getContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss()
;                            }
                        });
                        dialog.show();
                        return;
                    }
                    API.Get.netFromFromAndNear(queue, from.getFromLocation(), near.getNearLocation(),
                            new Response.Listener<NetworkResponse<Network>>() {
                        @Override
                        public void onResponse(NetworkResponse<Network> response) {
                            // TODO: If network doesn't exist, offer to create it
                            if (response.fail()) {
                                response.showErrorDialog(getContext());
                            } else {
                                getActivity().getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE).edit()
                                        .putLong(API.SELECTED_NETWORK, response.getPayload().id).apply();
                                startActivity(new Intent(getActivity(), TimelineActivity.class));
                            }
                        }
                    });
                }
            });
            return rootView;

        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            search();
            return true;
        }

        public void search() {
            String query = searchView.getQuery().toString();
            API.Get.autocompletePlace(queue, query, new Response.Listener<NetworkResponse<List<Location>>>() {
                @Override
                public void onResponse(NetworkResponse<List<Location>> response) {
                    if (response.fail()) {
                        response.showErrorDialog(getContext());
                    } else {
                        adapter.clear();
                        adapter.addAll(response.getPayload());
                        if (searchList.getVisibility() == GONE) {
                            searchList.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }


    }


    /**
     * The fragment for finding language networks.
     */
    public static class FindLanguageFragment extends Fragment implements
            SearchView.OnQueryTextListener {


        private ListView searchList;
        private SearchAdapter<Language> adapter;
        private SearchView searchView;

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
            searchView = rootView.findViewById(R.id.from_language_search_view);
            searchView.setOnQueryTextListener(this);
            searchView.setSearchableInfo(mSearchManager.
                    getSearchableInfo(getActivity().getComponentName()));
            searchList = rootView.findViewById(R.id.search_suggestions_list_view);
            //TODO: Use abstracted API interface.

            adapter = new SearchAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                    R.id.location_language_name_list_view);

            searchList.setTextFilterEnabled(true);
            searchList.setAdapter(adapter);
            searchList.setOnItemClickListener(new ListView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (near == null) {
                        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                        dialog.setTitle(getContext().getString(R.string.error));
                        dialog.setMessage(getContext().getString(R.string.no_near_loc));
                        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, getContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        return;
                    }
                    Language l = adapter.getItem(position);
                    API.Get.netFromLangAndNear(queue, l, near.getNearLocation(), new Response.Listener<NetworkResponse<Network>>() {
                        @Override
                        public void onResponse(NetworkResponse<Network> response) {
                            // TODO: If network doesn't exist, offer to create it
                            if (response.fail()) {
                                response.showErrorDialog(getContext());
                            } else {
                                getActivity().getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE).edit()
                                        .putLong(API.SELECTED_NETWORK, response.getPayload().id).apply();
                                startActivity(new Intent(getActivity(), TimelineActivity.class));
                            }
                        }
                    });
                }
            });
            return rootView;

        }

        public void search() {
            String query = searchView.getQuery().toString();
            API.Get.autocompleteLanguage(queue, query, new Response.Listener<NetworkResponse<List<Language>>>() {
                @Override
                public void onResponse(NetworkResponse<List<Language>> response) {
                    if (response.fail()) {
                        response.showErrorDialog(getActivity());
                    } else {
                        adapter.clear();
                        adapter.addAll(response.getPayload());
                        if (searchList.getVisibility() == GONE) {
                            searchList.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            search();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
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

    /**
     * This ensures that we are canceling all network requests if the user is leaving this activity.
     * We use a RequestFilter that accepts all requests (meaning it cancels all requests)
     */
    @Override
    public void onStop() {
        super.onStop();
        if (queue != null)
            queue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
    }

}
