package org.codethechange.culturemesh;

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

import org.codethechange.culturemesh.models.Location;
import org.codethechange.culturemesh.models.Place;

import java.util.List;

import static android.view.View.GONE;

public class ChooseNearLocationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String CHOSEN_PLACE = "chosen_place";
    public final int RESULT_OK = 1;

    private SearchAdapter<Location> adapter;
    private ListView searchList;
    private SearchView searchView;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_near_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }
}
