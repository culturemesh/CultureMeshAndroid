package org.codethechange.culturemesh;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.codethechange.bubblepicker.BubblePickerListener;
import org.codethechange.bubblepicker.adapter.BubblePickerAdapter;
import org.codethechange.bubblepicker.model.BubbleGradient;
import org.codethechange.bubblepicker.model.PickerItem;
import org.codethechange.bubblepicker.model.PickerItemSize;
import org.codethechange.bubblepicker.rendering.BubblePicker;
import org.codethechange.culturemesh.models.Network;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by nathaniel on 12/27/18.
 *
 * ExploreBubblesOpenGLActivity - Explore View
 */

public class ExploreBubblesOpenGLActivity extends DrawerActivity {
    BubblePicker picker; // OpenGL Picker
    ArrayList<Network> networks; // Networks for picker to draw from

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_bubbles_open_gl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        picker = findViewById(R.id.picker); // Get Picker

        networks = API.Get.userNetworks(0).getPayload(); // Request networks from API

        final TypedArray colors = getResources().obtainTypedArray(R.array.colors); // Preset colors
        final TypedArray images = getResources().obtainTypedArray(R.array.images); // Type colors

        // Standard adapter code for OpenGL Picker
        picker.setAdapter(new BubblePickerAdapter() {

            // Total Count
            @Override
            public int getTotalCount() {
                return networks.size();
            }

            // Next Item based on index for bubble Picker
            @NotNull
            @Override
            public PickerItem getItem(int position) {
                // Create new PickerItem
                PickerItem item = new PickerItem();
                // Set title based on network origination
                item.setTitle(networks.get(position).fromLocation.from_country);
                // Set colors
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                // Set text colors
                item.setTextColor(ContextCompat.getColor(ExploreBubblesOpenGLActivity.this, android.R.color.white));
                // Set background image on tap
                //item.setBackgroundImage(ContextCompat.getDrawable(ExploreBubblesOpenGLActivity.this, images.getResourceId(position, 0)));

                // DUMMY CODE TO RESIZE BUBBLES PER NETWORK
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
        picker.setBackground(0x000000);
        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {
                for (int i = 0; i < networks.size(); i++) {
                    if (networks.get(i).fromLocation.from_country.equals(item.getTitle())) {
                        transitionToNetwork(networks.get(i)); // Transition to next.
                         return;
                    }
                }
                throw new NoSuchElementException("network not found :O");
            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {

            }
        });
    }

    private void transitionToNetwork(Network net) {
        Intent i = new Intent(getBaseContext(), TimelineActivity.class);
        i.putExtra("network", net); // Pass in network into intent.
        startActivity(i);
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
