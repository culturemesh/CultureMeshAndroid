package org.codethechange.culturemesh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.User;

import java.util.ArrayList;

public class SettingsActivity extends DrawerActivity implements NetworkSummaryAdapter.OnNetworkTapListener {

    RecyclerView rv;
    TextView emptyText, userName, email;
    EditText bio, firstName, lastName;
    ImageView profilePicture;
    Button updateProfile;
    User user;

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
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    user.firstName = firstName.getText().toString();
                    user.lastName = lastName.getText().toString();
                    user.aboutMe = bio.getText().toString();
                    new UpdateProfile().execute(user);
                } catch(NullPointerException e) {
                    //TODO: User is null. We should handle that.
                    e.printStackTrace();
                }

            }
        });
        new LoadUserInfo().execute(currentUser);
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
                                new LeaveNetwork().execute(networkID);
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
        //If the
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
            } else {
                emptyText.setText(getResources().getString(R.string.no_networks));
            }
        }
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

    class LoadUserInfo extends AsyncTask<Long, Void, User> {

        @Override
        protected User doInBackground(Long... longs) {
            API.loadAppDatabase(getApplicationContext());
            user = API.Get.user(longs[0]).getPayload();
            API.closeDatabase();
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            bio.setText(user.aboutMe);
            firstName.setText(user.firstName);
            lastName.setText(user.lastName);
            userName.setText(user.username);
            email.setText(user.email);
            Picasso.with(getApplicationContext()).load(user.getImgURL()).into(profilePicture);

        }
    }


    class UpdateProfile extends AsyncTask<User, Void, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(User... users) {
            API.loadAppDatabase(getApplicationContext());
            NetworkResponse res = API.Put.user(users[0]);
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
        ArrayList<Integer> counts = new ArrayList<>();
        ArrayList<Integer> users = new ArrayList<>();
        NetworkSummaryAdapter adapter = new NetworkSummaryAdapter(networks, counts, users, SettingsActivity.this);
        rv.setAdapter(adapter);
        //Fetch Data off UI thread.
        new LoadSubscribedNetworks().execute(currentUser);
    }
}