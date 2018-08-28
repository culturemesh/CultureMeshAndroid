package com.culturemesh.android;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import com.culturemesh.android.models.Location;

import java.util.List;

import static android.view.View.GONE;

/**
 * This screen let's the user choose where they live now. This is used by
 * {@link FindNetworkActivity} to restrict displayed networks to those with a {@code near} that
 * matches where the user lives.
 */
public class ChooseNearLocationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    /**
     * Identifier for the {@link Intent} whose value is the {@link Location} the user chose
     */
    public static final String CHOSEN_PLACE = "chosen_place";

    /**
     * Result code to signal via the {@link Intent} that the user successfully chose a
     * {@link Location}
     */
    public static final int RESULT_OK = 1;

    /**
     * Populates the list of results with autocomplete results
     */
    private SearchAdapter<Location> adapter;

    /**
     * List of autocomplete results
     */
    private ListView searchList;

    /**
     * The field into which the user enters their query
     */
    private SearchView searchView;

    /**
     * Queue of asynchronous tasks for network operations
     */
    private RequestQueue queue;

    /**
     * Setup the activity. Also initializes the {@link com.android.volley.RequestQueue}, the adapter
     * that populates the list of results, and the listener that handles clicks on items in the
     * results list
     * @param savedInstanceState Previous state that is passed through to superclass
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_near_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set up Search functionality.
        queue = Volley.newRequestQueue(getApplicationContext());
        SearchManager mSearchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.near_location_search_view);
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(mSearchManager.
                getSearchableInfo(getComponentName()));
        searchList = findViewById(R.id.near_location_search_results_list_view);

        adapter = new SearchAdapter<Location>(this, android.R.layout.simple_list_item_1,
                R.id.location_language_name_list_view);
        searchList.setTextFilterEnabled(true);
        searchList.setAdapter(adapter);
        searchList.setOnItemClickListener(new ListView.OnItemClickListener() {

            /**
             * When a location is selected, the activity is closed and the result is passed back
             * to the calling class via an {@link Intent}
             * @param parent Unused TODO: What is this?
             * @param view Unused TODO: What is this?
             * @param position Position of the selected item in the list. Used to identify which
             *                 place was chosen
             * @param id Unused TODO: What is this?
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location near = adapter.getItem(position);
                Intent result = new Intent();
                result.putExtra(CHOSEN_PLACE, near);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    /**
     * When the user submits their query, {@link ChooseNearLocationActivity#search()} is run to
     * populated the results with matching {@link Location}s
     * @param query User's query. Not used.
     * @return Always returns {@code true}
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        search();
        return true;
    }

    /**
     * Get the query present in the {@link ChooseNearLocationActivity#searchView} and pass it to the
     * server via {@link API.Get#autocompletePlace(RequestQueue, String, Response.Listener)} to get
     * a list of matching {@link Location}s. These are used to populate the
     * {@link ChooseNearLocationActivity#adapter}.
     */
    public void search() {
        String query = searchView.getQuery().toString();
        API.Get.autocompletePlace(queue, query, new Response.Listener<NetworkResponse<List<Location>>>() {
            @Override
            public void onResponse(NetworkResponse<List<Location>> response) {
                if (response.fail()) {
                    response.showErrorDialog(ChooseNearLocationActivity.this);
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

    /**
     * Whenever the query text changes, do nothing because sending network requests every time is
     * unnecessary.
     * @param newText The updated query text
     * @return Always returns {@code true}
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }
}
