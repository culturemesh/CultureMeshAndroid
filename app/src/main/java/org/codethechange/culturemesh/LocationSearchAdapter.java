package org.codethechange.culturemesh;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filterable;

import java.util.ArrayList;
import android.widget.Filter;

import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by Drew Gregory (drewgreg@stanford.edu) on 11/7/17.
 */

public class LocationSearchAdapter extends ArrayAdapter<String> implements Filterable {

    private ArrayList<String> locations;
    private ArrayList<String> filteredLocations;
    private LocationFilter filter;



    /**
     * Initialize context variables
     * @param context application context
     * @param resource int resource layout id
     * @param locations string list of locations
     */
    public LocationSearchAdapter(@NonNull Context context, @LayoutRes int resource,
                                 @NonNull List<String> locations) {
        //TODO: Use Set instead of ArrayList for total list.
        //TODO: Ignore case for filter.
        super(context, resource, locations);
        this.locations = new ArrayList<String>(locations);
        filteredLocations = (ArrayList<String>) locations;
        getFilter();
    }

    @NonNull
    @Override
    public android.widget.Filter getFilter() {
        if (filter == null) {
            filter = new LocationFilter();
        }
        return filter;
    }

    @Override
    public String getItem(int position) {
        Log.i("Hello", filteredLocations.get(position));
        return filteredLocations.get(position);
    }

    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {
        return filteredLocations.size();
    }

    private class LocationFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<String> tempList = new ArrayList<String>();
            if (constraint!=null && constraint.length()>0) {
                for (String location : locations) {
                    if (location.toLowerCase().contains(constraint)) {
                        tempList.add(location);
                    }
                }
            }
            filterResults.count = tempList.size();
            filterResults.values = tempList;
            return filterResults;
        }


        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredLocations = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }

}
