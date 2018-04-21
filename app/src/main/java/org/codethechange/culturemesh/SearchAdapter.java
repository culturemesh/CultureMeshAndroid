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
import java.util.List;

public class SearchAdapter<T extends Searchable & Listable> extends ArrayAdapter<T> implements Filterable {
    private Context context;
    // R.id.location_language_name_list_view
    private int listViewID;
    private Filter filter;

    public SearchAdapter(@NonNull Context context, @LayoutRes int resource, int listViewID,
                         @NonNull List<T> items) {
        super(context, resource, items);
        this.context = context;
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
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.network_list_item, parent,
                    false);
        }
        TextView itemName = convertView.findViewById(listViewID);
        itemName.setText(getItem(position).getListableName());
        //TODO: Set number of people with .getNumUsers()
        return convertView;
    }
}
