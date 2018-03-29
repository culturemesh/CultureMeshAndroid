package org.codethechange.culturemesh;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.codethechange.culturemesh.models.Network;

import java.util.ArrayList;


/**
 * Created by Drew Gregory on 03/27/18.
 */
public class ListNetworksFragment extends Fragment {
    View root;
    RecyclerView rv;
    TextView emptyText;
    final static String SELECTED_USER="seluser";
    final static String FIRST_TIME = "firsttime";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListNetworksFragment newInstance(long selUser) {
        ListNetworksFragment fragment = new ListNetworksFragment();
        Bundle args = new Bundle();
        args.putLong(SELECTED_USER, selUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the intent, verify the action and get the query
        root = inflater.inflate(R.layout.rv_container, container, false);
        rv = root.findViewById(R.id.rv);
        //Say it's empty.
        ArrayList<Network> networks = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();
        ArrayList<Integer> users = new ArrayList<>();
        NetworkSummaryAdapter adapter = new NetworkSummaryAdapter(networks, counts, users);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        emptyText = root.findViewById(R.id.empty_text);
        emptyText.setText(getResources().getString(R.string.no_networks));
        if (getArguments().getBoolean(FIRST_TIME, true)) {
            //Fetch Data off UI thread.
            new LoadSubscribedNetworks().execute(getArguments().getLong(SELECTED_USER, -1));
        }
        return root;

    }

    class LoadSubscribedNetworks extends AsyncTask<Long, Void, Void> {

        @Override
        protected Void doInBackground(Long... longs) {
            long userId = longs[0];
            API.loadAppDatabase(getActivity());
            NetworkSummaryAdapter adapter = (NetworkSummaryAdapter) rv.getAdapter();
            ArrayList<Network> nets = API.Get.userNetworks(userId).getPayload();
            adapter.getNetworks().addAll(nets);
            for (Network net : nets) {
                //TODO: size() is limited to int....
                try {
                    adapter.getUserCounts().add(API.Get.networkUsers(net.id).getPayload().size());
                } catch(NullPointerException e) {
                   adapter.getUserCounts().add(0);
                }
                try {
                    adapter.getPostCounts().add(API.Get.networkPosts(net.id).getPayload().size());
                } catch(NullPointerException e) {
                    adapter.getPostCounts().add(0);
                }
            }

            API.closeDatabase();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void v) {
            rv.getAdapter().notifyDataSetChanged();
            if (rv.getAdapter().getItemCount() > 0) {
                //Hide empty text.
                emptyText.setVisibility(View.GONE);
            }
            getArguments().putBoolean(FIRST_TIME, false);
            getArguments().putLong(SELECTED_USER, getArguments().getLong(SELECTED_USER, -1));
        }
    }
}
