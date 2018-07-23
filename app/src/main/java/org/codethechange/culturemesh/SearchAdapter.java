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

public class SearchAdapter<T extends Listable> extends ArrayAdapter<T> implements Filterable {
    private Context context;
    private int listViewID;
    private Filter filter;
    private List<T> items;

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

    @Nullable
    @Override
    public T getItem(int position) {
        return items.get(position);
    }

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

    @Override
    public void addAll(@NonNull Collection<? extends T> collection) {
        super.addAll(collection);
        items.addAll(collection);
    }

    @Override
    public void clear() {
        super.clear();
        items = new ArrayList<>();
    }
}
