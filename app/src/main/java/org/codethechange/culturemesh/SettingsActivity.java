package org.codethechange.culturemesh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.Post;
import org.codethechange.culturemesh.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends DrawerActivity implements NetworkSummaryAdapter.OnNetworkTapListener {

    RecyclerView rv;
    TextView emptyText, userName, email;
    EditText bio, firstName, lastName;
    ImageView profilePicture;
    Button updateProfile;
    User user;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        rv = findViewById(R.id.rv);
        bio = findViewById(R.id.bio);
        firstName = findViewById(R.id.first_name_field);
        lastName = findViewById(R.id.last_name_field);
        emptyText = findViewById(R.id.empty_text);
        email = findViewById(R.id.email);
        userName = findViewById(R.id.user_name);
        profilePicture = findViewById(R.id.user_profile);
        updateProfile = findViewById(R.id.update_profile_button);
        queue = Volley.newRequestQueue(getApplicationContext());
        //TODO: Add ability to change profile picture.
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    user.firstName = firstName.getText().toString();
                    user.lastName = lastName.getText().toString();
                    user.aboutMe = bio.getText().toString();
                    //TOOD: Add support when we have API Support for PUT /user
                    // new UpdateProfile().execute(user);
                } catch(NullPointerException e) {
                    //TODO: User is null. We should handle that.
                    e.printStackTrace();
                }

            }
        });
        //Load User Info.
        API.Get.user(queue, currentUser, new Response.Listener<NetworkResponse<User>>() {
            @Override
            public void onResponse(NetworkResponse<User> response) {
                if (!response.fail()) {
                    user = response.getPayload();
                    bio.setText(user.aboutMe);
                    firstName.setText(user.firstName);
                    lastName.setText(user.lastName);
                    userName.setText(user.username);
                    email.setText(user.email);
                    Picasso.with(getApplicationContext()).load(user.getImgURL()).into(profilePicture);
                } else {
                    response.showErrorDialog(getApplicationContext());
                }
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        resetAdapter();
        ItemTouchHelper.SimpleCallback listener = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                AlertDialog success = new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle(R.string.are_you_sure)
                        .setMessage(R.string.leave_network_question)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Wow! We are removing this network! Sad..
                                long networkID = ((NetworkSummaryAdapter) rv.getAdapter()).getNetworks()
                                        .get(viewHolder.getAdapterPosition()).id;
                                //TODO: Support leaving network when we have the API support.
                                // API support. new LeaveNetwork().execute(networkID);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Nothing here.
                            }
                        })
                        .create();
                success.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //Even if we aren't changing anything, the swipe motion removes
                        //the item from the recycler. We need to include it again.
                        resetAdapter();
                    }
                });
                success.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(listener);
        itemTouchHelper.attachToRecyclerView(rv);


    }

    @Override
    public void onItemClick(View v, Network network) {
        //TODO: Figure out what you want here. Perhaps view network?
    }

    public class LeaveNetwork extends AsyncTask<Long, Void, Void> {

        @Override
        protected Void doInBackground(Long... longs) {
            API.loadAppDatabase(getApplicationContext());
            API.Post.removeUserFromNetwork(currentUser, longs[0]);
            API.closeDatabase();
            return null;
        }
    }

    class UpdateProfile extends AsyncTask<User, Void, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(User... users) {
            API.loadAppDatabase(getApplicationContext());
            NetworkResponse res = API.Put.user(user);
            API.closeDatabase();
            return res;
        }

        @Override
        protected void onPostExecute(NetworkResponse networkResponse) {
            if (networkResponse.fail()) {
                //Uh oh. We have a network error.
                networkResponse.showErrorDialog(SettingsActivity.this);
            } else {
                //Yay! We updated the user's settings.
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle(R.string.genericSuccess)
                        .setMessage(R.string.updated_profile)
                        .show();
            }
        }
    }

    void resetAdapter(){
        ArrayList<Network> networks = new ArrayList<>();
        HashMap<String, Integer> counts = new HashMap<>();
        HashMap<String, Integer> users = new HashMap<>();
        final NetworkSummaryAdapter adapter = new NetworkSummaryAdapter(networks, counts, users, SettingsActivity.this);
        rv.setAdapter(adapter);
        //Fetch Data off UI thread.
        API.Get.userNetworks(queue, currentUser, new Response.Listener<NetworkResponse<ArrayList<Network>>>() {
            @Override
            public void onResponse(NetworkResponse<ArrayList<Network>> response) {
                // Cool! Now, for each network, we need to find the number of posts and the
                // number of users.
                if (!response.fail()) {
                    ArrayList<Network> nets = response.getPayload();
                    adapter.getNetworks().addAll(nets);
                    rv.getAdapter().notifyDataSetChanged();
                    if (rv.getAdapter().getItemCount() > 0) {
                        //Hide empty text.
                        emptyText.setVisibility(View.GONE);
                    } else {
                        emptyText.setText(getResources().getString(R.string.no_networks));
                    }
                    for (final Network net : nets) {
                        API.Get.networkUserCount(queue, net.id, new Response.Listener<NetworkResponse<Long>>() {
                            @Override
                            public void onResponse(NetworkResponse<Long> response) {
                                if (!response.fail()) {
                                    /* getUserCounts() returns HashMap<network_id, user_count> */
                                    // This prevents possibility that the user counts are added in
                                    // wrong order.
                                    adapter.getUserCounts().put(net.id + "", response.getPayload().intValue());
                                } else {
                                    adapter.getUserCounts().put(net.id + "", 0);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                        API.Get.networkPostCount(queue, net.id, new Response.Listener<NetworkResponse<Long>>() {
                            @Override
                            public void onResponse(NetworkResponse<Long> response) {
                                if (!response.fail()) {
                                    adapter.getPostCounts().put(net.id + "", response.getPayload().intValue());
                                } else {
                                    adapter.getPostCounts().put(net.id + "", 0);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * This ensures that we are canceling all network requests if the user is leaving this activity.
     * We use a RequestFilter that accepts all requests (meaning it cancels all requests)
     */
    @Override
    public void onStop() {
        super.onStop();
        if (queue != null)
            queue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
    }
}
