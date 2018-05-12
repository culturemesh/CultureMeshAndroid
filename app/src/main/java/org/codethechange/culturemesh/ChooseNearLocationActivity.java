package org.codethechange.culturemesh;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import org.codethechange.culturemesh.models.Place;

import java.util.List;

import static android.view.View.GONE;

public class ChooseNearLocationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String CHOSEN_PLACE = "chosen_place";
    public final int RESULT_OK = 1;

    private SearchAdapter<Place> adapter;
    private ListView searchList;
    private SearchView searchView;

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
        searchView = findViewById(R.id.near_location_search_view);
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(mSearchManager.
                getSearchableInfo(getComponentName()));
        searchList = findViewById(R.id.near_location_search_results_list_view);

        adapter = new SearchAdapter<>(this, android.R.layout.simple_list_item_1,
                R.id.location_language_name_list_view);
        searchList.setTextFilterEnabled(true);
        searchList.setAdapter(adapter);
        searchList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place near = adapter.getItem(position);
                Intent result = new Intent();
                result.putExtra(CHOSEN_PLACE, near);
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        search();
        return true;
    }

    public void search() {
        String query = searchView.getQuery().toString();
        new searchPlaces().execute(query);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    // TODO: Remove this redundant code as it is copied from FindNetworkActivity
    // TODO: Should this AsyncTask be static?
    class searchPlaces extends AsyncTask<String, Void, NetworkResponse<List<Place>>> {
        @Override
        protected NetworkResponse<List<Place>> doInBackground(String... strings) {
            API.loadAppDatabase(getApplicationContext());
            NetworkResponse<List<Place>> response = API.Get.autocompletePlace(strings[0]);
            API.closeDatabase();
            return response;
        }

        @Override
        protected void onPostExecute(NetworkResponse<List<Place>> response) {
            super.onPostExecute(response);
            if (response.fail()) {
                response.showErrorDialog(getApplicationContext());
            } else {
                adapter.clear();
                adapter.addAll(response.getPayload());
                if (searchList.getVisibility() == GONE) {
                    searchList.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
