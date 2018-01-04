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

import java.util.ArrayList;

import static android.view.View.GONE;

public class ChooseNearLocationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    LocationSearchAdapter adapter;
    ListView searchList;
    ArrayList<String> dummy;
    public static final String CHOSEN_LOCATION = "chosen_location";
    public final int RESULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_near_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Set up Search functionality.
        SearchManager mSearchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.near_location_search_view);
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(mSearchManager.
                getSearchableInfo(getComponentName()));
        searchList = (ListView) findViewById(R.id.near_location_search_results_list_view);
        //TODO:Remove dummy
        dummy = new ArrayList<String>();
        dummy.add("Marseille, Provence, France");
        dummy.add("London, Britain, United Kingdom");
        dummy.add("Chicago, Illinois, United States");
        adapter = new LocationSearchAdapter(this,
                android.R.layout.simple_list_item_1, dummy);
        searchList.setTextFilterEnabled(true);
        searchList.setAdapter(adapter);
        searchList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent result = new Intent();
                result.putExtra(CHOSEN_LOCATION, dummy.get(position));
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
