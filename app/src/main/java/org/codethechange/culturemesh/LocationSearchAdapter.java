package org.codethechange.culturemesh;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import java.util.ArrayList;
import android.widget.Filter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Drew Gregory (drewgreg@stanford.edu) on 11/7/17.
 */

public class LocationSearchAdapter extends ArrayAdapter<String> implements Filterable {

    private ArrayList<String> locations;
    private ArrayList<String> filteredLocations;
    private LocationFilter filter;
    private Context context;


    /**
     * Initialize context variables
     * @param context application context
     * @param resource int resource layout id
     * @param locations string list of locations
     */
    LocationSearchAdapter(@NonNull Context context, @LayoutRes int resource,
                                 @NonNull List<String> locations) {
        //TODO: Use Set instead of ArrayList for total list.
        //TODO: Ignore case for filter.
        super(context, resource, locations);
        this.context = context;
        this.locations = new ArrayList<>(locations);
        filteredLocations = new ArrayList<>(locations);
        getFilter();
    }

    /**
     * Initialize context variables without a starting list
     * @param context application context
     * @param resource int resource layout id
     */
    LocationSearchAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
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
        Log.i("Hello", filteredLocations.get(position).toString());
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


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.network_list_item, parent,
                    false);
        }
        TextView locationName = convertView.findViewById(R.id.location_language_name_list_view);
        locationName.setText(filteredLocations.get(position));
        //TODO: Set number of people.
        return convertView;
    }

    private class LocationFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<String> tempList = new ArrayList<>();
            if (constraint!=null && constraint.length()>0) {
                for (String location : locations) {
                    if (location.toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        //Log.i("Comparison", location + " contains? " + constraint );
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
            Log.i("Results" , results.values.toString());
            filteredLocations = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }

}
