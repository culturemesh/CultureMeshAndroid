package org.codethechange.culturemesh;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.codethechange.culturemesh.models.NearLocation;

import java.util.ArrayList;

import static android.view.View.GONE;

public class ChooseNearLocationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchAdapter<NearLocation> adapter;
    ListView searchList;
    ArrayList<NearLocation> nearLocations;
    public static final String CHOSEN_CITY = "chosen_city";
    public static final String CHOSEN_REGION = "chosen_region";
    public static final String CHOSEN_COUNTRY = "chosen_country";
    public final int RESULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_near_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Set up Search functionality.
        SearchManager mSearchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.near_location_search_view);
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(mSearchManager.
                getSearchableInfo(getComponentName()));
        searchList = (ListView) findViewById(R.id.near_location_search_results_list_view);
        nearLocations = new ArrayList<>();
        // TODO: Remove these dummy locations and replace with querying API when user submits (see FindNetworkActivity)
        nearLocations.add(new NearLocation(1,1,1));
        nearLocations.add(new NearLocation(2,2,2));
        adapter = new SearchAdapter<>(this, android.R.layout.simple_list_item_1,
                R.id.location_language_name_list_view, nearLocations);
        searchList.setTextFilterEnabled(true);
        searchList.setAdapter(adapter);
        searchList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NearLocation near = nearLocations.get(position);
                Intent result = new Intent();
                result.putExtra(CHOSEN_CITY, near.near_city_id);
                result.putExtra(CHOSEN_REGION, near.near_region_id);
                result.putExtra(CHOSEN_COUNTRY, near.near_country_id);
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(this, "Changing text", Toast.LENGTH_LONG).show();
        adapter.getFilter().filter(newText);
        //searchList.setAdapter(adapter);
        return true;
    }
}
