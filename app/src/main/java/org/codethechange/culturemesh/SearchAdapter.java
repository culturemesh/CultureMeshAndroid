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
import android.widget.ImageView;
import android.widget.TextView;

import org.codethechange.culturemesh.models.Language;
import org.codethechange.culturemesh.models.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Populates a displayed list with items
 * @param <T> Type of item to put in the list
 */
public class SearchAdapter<T extends Listable> extends ArrayAdapter<T> implements Filterable {

    /**
     * {@link Context} which holds the list
     */
    private Context context;

    /**
     * ID of the UI element that is the list
     */
    private int listViewID;

    /**
     * Filters list items based on queries
     */
    private Filter filter;

    /**
     * List of items to display
     */
    private List<T> items;

    /**
     * Initialize instance fields with provided parameters
     * @param context {@inheritDoc}
     * @param resource {@inheritDoc}
     * @param listViewID Identifier for list the adapter will populate
     * @param items {@inheritDoc}
     */
    public SearchAdapter(@NonNull Context context, @LayoutRes int resource, int listViewID,
                         @NonNull List<T> items) {
        super(context, resource, items);
        this.context = context;
        this.listViewID = listViewID;
        this.items = items;
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
        this.items = new ArrayList<T>();
    }

    /**
     * Keeping views accessible saves calls to findViewById, which is a performance bottleneck.
     * This is exactly why we have RecyclerView!
     */
    class HolderItem {
        TextView itemName;
        TextView numUsers;
        ImageView peopleIcon;
    }

    /**
     * Get the item associated with the list entry at a certain position
     * @param position Position of list item
     * @return The object represented at the specified position
     */
    @Nullable
    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    /**
     * Get a {@link View} for the list
     * @param position Position of list element to get the {@link View} for
     * @param convertView {@link View} inflated from {@link R.layout#network_list_item} that will
     *                                represent the list entry
     * @param parent Parent of the created {@link View}
     * @return Inflated {@link View} for an element of the list
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.network_list_item, parent,
                    false);
            HolderItem item = new HolderItem();
            item.itemName = convertView.findViewById(listViewID);
            item.peopleIcon = convertView.findViewById(R.id.network_list_view_people_icon);
            item.numUsers = convertView.findViewById(R.id.number_people);
            convertView.setTag(item);
        }
        HolderItem item = (HolderItem) convertView.getTag();
        try {
            Language lang = (Language) items.get(position);
            item.itemName.setText(lang.name);
            Log.i("Language info", lang.toString());
            item.numUsers.setText(FormatManager.abbreviateNumber(lang.numSpeakers));
        } catch(ClassCastException e) {
            Location loc = (Location) items.get(position);
            item.itemName.setText(loc.locationName);
            item.numUsers.setVisibility(View.GONE);
        }
        return convertView;
    }

    /**
     * Add all items in a {@link Collection} to the list of items the adapter displays in the list
     * @param collection Items to add to the list
     */
    @Override
    public void addAll(@NonNull Collection<? extends T> collection) {
        super.addAll(collection);
        items.addAll(collection);
    }

    /**
     * Clears the list of all items
     */
    @Override
    public void clear() {
        super.clear();
        items = new ArrayList<>();
    }
}
