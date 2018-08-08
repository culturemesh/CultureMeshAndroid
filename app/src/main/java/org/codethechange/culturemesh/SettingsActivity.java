package org.codethechange.culturemesh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Screen that displays the current user's profile and let's them update it
 */
public class SettingsActivity extends DrawerActivity implements NetworkSummaryAdapter.OnNetworkTapListener {

    RecyclerView rv;

    /**
     * Text field that displays {@link R.string#no_networks} if the user has not joined any
     * {@link Network}s
     */
    TextView emptyText;

    /**
     * Editable text fields that make up parts of the {@link User}'s profile
     */
    EditText bio, firstName, lastName, userName, email;

    /**
     * The field for the {@link User}'s profile picture
     */
    ImageView profilePicture;

    /**
     * Button for updating the {@link User}'s profile on the server with the one currently displayed
     */
    Button updateProfile;

    /**
     * The user whose profile is displayed and being edited
     */
    User user;

    /**
     * Queue for asynchronous tasks
     */
    RequestQueue queue;

    /**
     * Tag for log statements
     */
    private static final String TAG = SettingsActivity.class.getName();

    /**
     * The {@code requestCode} used if the user was asked to pick an image from their gallery.
     * @see SettingsActivity#onActivityResult(int, int, Intent)
     */
    private static final int PICK_IMAGE = 1;

    /**
     * Constant that clarifies that quality 100 means no compression.
     */
    final int MAX_QUALITY = 100;

    /**
     * The max number of pixels for an image given the image.
     * Each pixel is 8 bytes large (according to RGBA_F16), and a MB is 2^20 bytes
     */
    final long MAX_PIXELS = 1 << 18;

    /**
     * The maximum number of pixels allowed on a single side of an image
     */
    final double MAX_SIDE = Math.sqrt(MAX_PIXELS);

    /**
     * This function is overridden to handle image selection.
     * Inspiration from
     * http://www.tauntaunwonton.com/blog/2015/1/21/simple-posting-of-multipartform-data-from-android
     * @param requestCode PICK_IMAGE if we asked them to choose an image from the gallery.
     * @param resultCode {@inheritDoc}
     * @param data Hopefully, the URI.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            // GOAL: Upload image to server.
            // General process: make bitmap, write bytes to temporary file, and pass file as param
            // to ion's multipart/form-data framework.

            Uri imageURI = data.getData();
            try {
                final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageURI);
                final Bitmap finalBitmap = resizeBitmap(bitmap);
                File appDirectory = getApplicationContext().getCacheDir();
                File imageFile = new File(appDirectory, user.username.substring(0,2)
                        + ".jpg");
                OutputStream os = new FileOutputStream(imageFile);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, MAX_QUALITY, os);
                os.flush();
                os.close();
                Ion.with(SettingsActivity.this).load(API.API_URL_BASE + "upload/image?"
                        + API.getCredentials())
                        .setMultipartFile("file", "image/*", imageFile)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                if (result != null) {
                                    NetworkResponse.genSuccessDialog(SettingsActivity.this,
                                            R.string.upload_sucess).show();
                                    // Update the user's picture with the returned URL.
                                    user.imgURL = result;
                                    Picasso.with(getApplicationContext()).load(user.imgURL).
                                            into(profilePicture);
                                } else {
                                    NetworkResponse.genErrorDialog(SettingsActivity.this,
                                            R.string.upload_failure).show();
                                }
                                bitmap.recycle();
                                finalBitmap.recycle();
                            }
                        });

            } catch (IOException e) {
                e.printStackTrace();
                NetworkResponse.genErrorDialog(SettingsActivity.this, R.string.upload_failure);
            }
        }
    }

    /**
     * Resizes bitmap to ensure that bitmap fits under 2 MB limit.
     * Goes to largest side and resets it to max length (sqrt(MAX_PIXELS))
     * @param bitmap
     */
    private Bitmap resizeBitmap(Bitmap bitmap) {
        double scalingFactor;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            scalingFactor = MAX_SIDE/bitmap.getWidth();
        } else {
            scalingFactor = MAX_SIDE/bitmap.getHeight();
        }
        if (scalingFactor > 1) {
            // We don't want to make it bigger, only smaller.
            return bitmap;
        }
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * scalingFactor),
                (int) (bitmap.getHeight() * scalingFactor), true);
    }

    /**
     * Setup the user interface with the layout defined in {@link R.layout#activity_settings}. Also
     * initialize instance fields for UI fields with the elements defined in the layout file. Fill
     * the fields with the current profile (fetched using
     * {@link API.Get#user(RequestQueue, long, Response.Listener)}). Link listeners to buttons
     * and the displays of {@link Network}s to handle interactions.
     * @param savedInstanceState {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
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
        profilePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(R.string.change_picture).setMessage(R.string.change_picture_prompt)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Start intent that functions as image picker. Once they choose
                                // an image, onActivityResult() will be called.
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.selectPicture)), PICK_IMAGE);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Canceled, so just dismiss automatically.
                            }
                        });
                builder.create().show();
            }
        });
        queue = Volley.newRequestQueue(getApplicationContext());
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(settings);
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
                    email.setText(settings.getString(API.USER_EMAIL, getString(R.string.missingEmail)));
                    Picasso.with(getApplicationContext()).load(user.getImgURL()).into(profilePicture);
                    Log.i(TAG, "User info loaded");
                    rv.getAdapter().notifyDataSetChanged();
                    Log.i(TAG, "Adapter notified of new user info");
                } else {
                    response.showErrorDialog(getApplicationContext());
                }
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        resetAdapter();
        ItemTouchHelper.SimpleCallback listener = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * {@inheritDoc}
             * @param recyclerView {@inheritDoc}
             * @param viewHolder {@inheritDoc}
             * @param target {@inheritDoc}
             * @return Always returns {@code false}
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            /**
             * Confirm the user wants to leave the {@link Network} swiped and, if they confirm they
             * do, remove them.
             * @param viewHolder
             * @param swipeDir
             */
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
                                API.Post.leaveNetwork(queue, networkID,
                                        getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE),
                                        new Response.Listener<NetworkResponse<String>>() {
                                    @Override
                                    public void onResponse(NetworkResponse<String> response) {
                                        rv.getAdapter().notifyDataSetChanged();
                                    }
                                });
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
                        rv.getAdapter().notifyDataSetChanged();
                    }
                });
                success.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(listener);
        itemTouchHelper.attachToRecyclerView(rv);


    }

    /**
     * Handle what happens when a user clicks on a {@link Network}. Right now, nothing is done.
     * @param v {@inheritDoc}
     * @param network {@inheritDoc}
     */
    @Override
    public void onItemClick(View v, Network network) {
        //TODO: Figure out what you want here. Perhaps view network?
    }

    /**
     * Reset the adapter by clearing it and then populating it with new information from
     * {@link API.Get#userNetworks(RequestQueue, long, Response.Listener)},
     * {@link API.Get#networkPostCount(RequestQueue, long, Response.Listener)}, and
     * {@link API.Get#networkUserCount(RequestQueue, long, Response.Listener)}.
     */
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
                    if (nets.size() > 0) {
                        //Hide empty text.
                        emptyText.setVisibility(View.GONE);
                    } else {
                        emptyText.setText(getResources().getString(R.string.no_networks));
                    }
                    for (final Network net : nets) {
                        Log.d(TAG, "Loaded network: " + net);
                        API.Get.networkUserCount(queue, net.id, new Response.Listener<NetworkResponse<Long>>() {
                            @Override
                            public void onResponse(NetworkResponse<Long> response) {
                                if (!response.fail()) {
                                    /* getUserCounts() returns HashMap<network_id, user_count> */
                                    // This prevents possibility that the user counts are added in
                                    // wrong order.
                                    adapter.getUserCounts().put(net.id + "", response.getPayload().intValue());
                                    if (adapter.getUserCounts().containsKey(net.id +"") &&
                                            adapter.getPostCounts().containsKey(net.id + "")) {
                                        //The network is ready to be added.
                                        adapter.getNetworks().add(net);
                                        adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    response.showErrorDialog(SettingsActivity.this);
                                    adapter.getUserCounts().put(net.id + "", 0);
                                }
                            }
                        });
                        API.Get.networkPostCount(queue, net.id, new Response.Listener<NetworkResponse<Long>>() {
                            @Override
                            public void onResponse(NetworkResponse<Long> response) {
                                if (!response.fail()) {
                                    adapter.getPostCounts().put(net.id + "", response.getPayload().intValue());
                                    if (adapter.getUserCounts().containsKey(net.id +"") &&
                                            adapter.getPostCounts().containsKey(net.id + "")) {
                                        //The network is ready to be added.
                                        adapter.getNetworks().add(net);
                                        adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    response.showErrorDialog(SettingsActivity.this);
                                    adapter.getPostCounts().put(net.id + "", 0);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    response.showErrorDialog(SettingsActivity.this);
                }
            }
        });
    }

    /**
     * Converts Uri into file path
     * Sourced from https://stackoverflow.com/questions/14054307/java-io-filenotfoundexception-in-android
     * @param uri uri taken from
     * @return
     */
    public String getPath(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
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

    /**
     * Updates user info via PUT request to server.
     * @param settings SharedPreferences instance to save email.
     */
    public void updateUser(SharedPreferences settings) {
        try {
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setBio(bio.getText().toString());
            user.setUsername(userName.getText().toString());
            SharedPreferences.Editor editor = settings.edit();
            String emailText = email.getText().toString();
            editor.putString(API.USER_EMAIL, emailText);
            editor.apply();
            API.Put.user(queue, user, emailText, getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE),
                    new Response.Listener<NetworkResponse<String>>() {
                        @Override
                        public void onResponse(NetworkResponse<String> response) {
                            if (response.fail()) {
                                response.showErrorDialog(SettingsActivity.this);
                            } else {
                                NetworkResponse.genSuccessDialog(SettingsActivity.this,
                                        R.string.updated_profile).show();
                            }
                        }
                    });
        } catch(NullPointerException e) {
            //TODO: User is null. We should handle that.
            e.printStackTrace();
        }
    }
}
