package org.codethechange.culturemesh;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.codethechange.bubblepicker.BubblePickerListener;
import org.codethechange.bubblepicker.adapter.BubblePickerAdapter;
import org.codethechange.bubblepicker.model.BubbleGradient;
import org.codethechange.bubblepicker.model.PickerItem;
import org.codethechange.bubblepicker.model.PickerItemSize;
import org.codethechange.bubblepicker.rendering.BubblePicker;
import org.codethechange.culturemesh.models.Network;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Display moving bubbles which show suggested networks for the user to join
 * TODO: Document this more fully
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

    ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_bubbles_open_gl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RequestQueue queue = Volley.newRequestQueue(ExploreBubblesOpenGLActivity.this);
        picker = findViewById(R.id.picker);
        picker.setVisibility(View.GONE);
        titles.add("something");
        titles.add("anotha");
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
            Log.i("showBubbles", "adding " + net.fromLocation.getListableName() + " " + net.nearLocation.getListableName());
            if (net.isLocationBased()) {
                titles.add(net.fromLocation.getListableName());
            }
            titles.add(net.nearLocation.getListableName());
        }
        // This our way of refres
        setAdapter();
        picker.onPause();
        picker.onResume();
        picker.setVisibility(View.VISIBLE);

    }

    private void setAdapter() {
        final TypedArray colors = getResources().obtainTypedArray(R.array.colors);
        final TypedArray images = getResources().obtainTypedArray(R.array.images);
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
        picker.setBackground(0x8BC34A);
        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {
                thisActivity.getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE).edit()
                        .putLong(API.SELECTED_NETWORK, 1).apply();
                startActivity(new Intent(thisActivity, TimelineActivity.class));
            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {

            }
        });
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
