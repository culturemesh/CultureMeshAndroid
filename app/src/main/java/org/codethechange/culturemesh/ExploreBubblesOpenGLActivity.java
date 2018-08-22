package org.codethechange.culturemesh;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.codethechange.bubblepicker.BubblePickerListener;
import org.codethechange.bubblepicker.adapter.BubblePickerAdapter;
import org.codethechange.bubblepicker.model.BubbleGradient;
import org.codethechange.bubblepicker.model.PickerItem;
import org.codethechange.bubblepicker.model.PickerItemSize;
import org.codethechange.bubblepicker.rendering.BubblePicker;
import org.codethechange.culturemesh.models.FromLocation;
import org.codethechange.culturemesh.models.Language;
import org.codethechange.culturemesh.models.Location;
import org.codethechange.culturemesh.models.NearLocation;
import org.codethechange.culturemesh.models.Network;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Display moving bubbles which show suggested networks for the user to join
 */
public class ExploreBubblesOpenGLActivity extends DrawerActivity {
    /**
     * The custom view that displays locations/languages as bubbles.
     */
    BubblePicker picker;

    /**
     * The text view responsible for guiding the user with the interface
     */
    TextView title;

    /**
     * The smaller text view responsible for clarifying  the title text.
     */
    TextView subTitle;

    /**
     * The even smaller view that will explain to the user which hint to do.
     */
    TextView hintText;

    /**
     * A mapping from the title of the bubble (Location#getShortName()) to the location object.
     */
    HashMap<String, Location> locations;

    /**
     * A mapping from the title of the bubble (Location#getShortName()) to the language object.
     */
    HashMap<String, Language> languages;

    NearLocation selectedNearLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_bubbles_open_gl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = findViewById(R.id.titleTextView);
        subTitle = findViewById(R.id.subtitleTextView);
        hintText = findViewById(R.id.hintTextView);
        RequestQueue queue = Volley.newRequestQueue(ExploreBubblesOpenGLActivity.this);
        picker = findViewById(R.id.picker);
        picker.setVisibility(View.GONE);
        locations = new HashMap<>();
        languages = new HashMap<>();
        setAdapter();
        API.Get.topTen(queue, new Response.Listener<NetworkResponse<ArrayList<Network>>>() {
            @Override
            public void onResponse(NetworkResponse<ArrayList<Network>> response) {
                if (response.fail()) {
                    response.showErrorDialog(ExploreBubblesOpenGLActivity.this);
                } else {
                    showBubbles(response.getPayload());
                }

            }
        });




    }

    private void showBubbles(ArrayList<Network> payload) {
        for (Network net: payload) {
            if (net.isLocationBased()) {
                locations.put(net.fromLocation.getShortName(), net.fromLocation);
            } else {
                languages.put(net.language.name, net.language);
            }
            locations.put(net.nearLocation.getShortName(), net.nearLocation);
        }
        // This is our way of refreshing.
        setAdapter();
        picker.onPause();
        picker.onResume();
        picker.setVisibility(View.VISIBLE);

    }

    private void setAdapter() {
        final TypedArray colors = getResources().obtainTypedArray(R.array.colors);
        final TypedArray images = getResources().obtainTypedArray(R.array.images);
        //Convert key sets into list of strings.
        final ArrayList<String> titles = new ArrayList<>();
        titles.addAll(locations.keySet());
        titles.addAll(languages.keySet());
        picker.setAdapter(new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.size();
            }

            @NotNull
            @Override
            public PickerItem getItem(int position) {
                Log.i("getItem", "Getting item at position " + position);
                PickerItem item = new PickerItem();
                item.setTitle(titles.get(position));
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                item.setTextColor(ContextCompat.getColor(ExploreBubblesOpenGLActivity.this, android.R.color.white));
                //item.setBackgroundImage(ContextCompat.getDrawable(ExploreBubblesOpenGLActivity.this, images.getResourceId(position, 0)));
                if (((position * 2) % 8) > 3) {
                    item.setSize(PickerItemSize.LARGE);
                }

                if (((position * 2) % 8) < 2) {
                    item.setSize(PickerItemSize.SMALL);
                }
                return item;
            }
        });


        colors.recycle();
        images.recycle();

        picker.setBubbleSize(70);
        picker.setBackground(0xFFFFFF);
        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {
                if (selectedNearLocation == null) {
                    // First thing they select is their near location.
                    if (locations.containsKey(item.getTitle())) {
                        selectedNearLocation = locations.get(item.getTitle()).getNearLocation();
                        title.setText(item.getTitle());
                        subTitle.setText(getResources().getString(R.string.from_location_clarify));
                    } else {
                       // They selected a language instead, which is invalid as a near location.
                       // Let's notify them of their error via a snack bar.
                       item.setSelected(false);
                       Snackbar.make(picker, getResources().getString(R.string.invalid_near_location),
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    // They have selected their second component! Let's create this network.
                    if (locations.containsKey(item.getTitle())) {
                        // From location.
                        FromLocation fromLoc = locations.get(item.getTitle()).getFromLocation();
                        API.Get.netFromFromAndNear(queue, fromLoc, selectedNearLocation, new Response.Listener<NetworkResponse<Network>>() {
                            @Override
                            public void onResponse(NetworkResponse<Network> response) {
                                if (response.fail()) {
                                    response.showErrorDialog(ExploreBubblesOpenGLActivity.this);
                                } else {
                                    visitNetwork(response.getPayload().id);
                                }
                            }
                        });
                    } else {
                        // Language
                        Language language = languages.get(item.getTitle());
                        API.Get.netFromLangAndNear(queue, language, selectedNearLocation, new Response.Listener<NetworkResponse<Network>>() {
                            @Override
                            public void onResponse(NetworkResponse<Network> response) {
                                if (response.fail()) {
                                    response.showErrorDialog(ExploreBubblesOpenGLActivity.this);
                                } else {
                                    visitNetwork(response.getPayload().id);
                                }
                            }
                        });
                    }

                }
            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {

            }
        });
    }


    /**
     * Navigates to TimelineActivity to view the selected network.
     * @param id id of network.
     */
    void visitNetwork(long id) {
        getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE).edit()
                .putLong(API.SELECTED_NETWORK, id).apply();
        startActivity(new Intent(ExploreBubblesOpenGLActivity.this, TimelineActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        picker.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        picker.onPause();
    }
}
