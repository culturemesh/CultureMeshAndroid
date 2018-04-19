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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter<T extends Searchable & Listable> extends ArrayAdapter<T> implements Filterable {
    private ArrayList<T> items;
    private ArrayList<T> filteredItems;
    private Context context;
    // R.id.location_language_name_list_view
    private int listViewID;
    private Filter filter;

    public SearchAdapter(@NonNull Context context, @LayoutRes int resource, int listViewID,
                         @NonNull List<T> items) {
        super(context, resource, items);
        this.context = context;
        this.items = new ArrayList<>(items);
        filteredItems = new ArrayList<>(items);
        this.listViewID = listViewID;
        getFilter();
    }

    /**
     * Initialize context variables without a starting list
     * @param context application context
     * @param resource int resource layout id
     */
    SearchAdapter(@NonNull Context context, @LayoutRes int resource, int listViewID) {
        super(context, resource);
        this.listViewID = listViewID;
    }

    @NonNull
    @Override
    public android.widget.Filter getFilter() {
        if (filter == null) {
            filter = new SearchAdapter.GenericFilter();
        }
        return filter;
    }

    @Override
    public T getItem(int position) {
        Log.i("Hello", filteredItems.get(position).toString());
        return filteredItems.get(position);
    }

    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {
        return filteredItems.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.network_list_item, parent,
                    false);
        }
        TextView itemName = convertView.findViewById(listViewID);
        itemName.setText(filteredItems.get(position).getListableName());
        //TODO: Set number of people with .getNumUsers()
        return convertView;
    }

    private class GenericFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<T> tempList = new ArrayList<>();
            if (constraint != null && constraint.length() > 0) {
                for (T item : items) {
                    if (item.matches(constraint)) {
                        tempList.add(item);
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
            filteredItems = (ArrayList<T>) results.values;
            notifyDataSetChanged();
        }
    }
}
