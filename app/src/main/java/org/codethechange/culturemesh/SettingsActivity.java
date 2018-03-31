package org.codethechange.culturemesh;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.codethechange.culturemesh.models.Network;

import java.util.ArrayList;

public class SettingsActivity extends DrawerActivity implements NetworkSummaryAdapter.OnNetworkTapListener {

    RecyclerView rv;
    TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        rv = findViewById(R.id.rv);

        emptyText = findViewById(R.id.empty_text);
        ArrayList<Network> networks = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();
        ArrayList<Integer> users = new ArrayList<>();
        NetworkSummaryAdapter adapter = new NetworkSummaryAdapter(networks, counts, users, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        emptyText.setText(getResources().getString(R.string.no_networks));
        //Fetch Data off UI thread.
        new LoadSubscribedNetworks().execute(currentUser);

    }

    @Override
    public void onItemClick(View v, Network network) {

    }

    class LoadSubscribedNetworks extends AsyncTask<Long, Void, Void> {

        @Override
        protected Void doInBackground(Long... longs) {
            long userId = longs[0];
            API.loadAppDatabase(SettingsActivity.this);
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
        }
    }

}
