package org.codethechange.culturemesh;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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

import static android.view.View.GONE;

/**
 * Created by Drew Gregory on 03/27/18.
 */
public class ListNetworksFragment extends Fragment {
    View root;
    RecyclerView rv;
    TextView emptyText;
    final static String SELECTED_USER="seluser";
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
        //Say it's empty.
        rv.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
        emptyText = root.findViewById(R.id.empty_text);
        emptyText.setText(getResources().getString(R.string.no_networks));
        //Fetch Data off UI thread.
        new LoadSubscribedNetworks().execute(savedInstanceState.getLong(SELECTED_USER, -1));
        return root;

    }

    class LoadSubscribedNetworks extends AsyncTask<Long, Void, NetworkSummaryAdapter> {

        @Override
        protected NetworkSummaryAdapter doInBackground(Long... longs) {
            long userId = longs[0];
            API.loadAppDatabase(getActivity());
            ArrayList<Network> nets = API.Get.userNetworks(userId).getPayload();
            ArrayList<Integer> userCounts = new ArrayList<>();
            ArrayList<Integer> postCounts = new ArrayList<>();
            for (Network net : nets) {
                //TODO: size() is limited to int....
                try {
                    userCounts.add(API.Get.networkUsers(net.id).getPayload().size());
                } catch(NullPointerException e) {
                    userCounts.add(0);
                }
                try {
                    postCounts.add(API.Get.networkPosts(net.id).getPayload().size());
                } catch(NullPointerException e) {
                    postCounts.add(0);
                }
            }

            API.closeDatabase();
            return new NetworkSummaryAdapter(nets, postCounts, userCounts);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(NetworkSummaryAdapter networkSummaryAdapter) {
            rv.setAdapter(networkSummaryAdapter);
            getFragmentManager().beginTransaction()
                    .detach(ListNetworksFragment.this)
                    .attach(ListNetworksFragment.this)
                    .commit();
            if (networkSummaryAdapter.getItemCount() > 0) {
                //Hide empty text.
                emptyText.setVisibility(View.GONE);
            }
        }
    }
}
