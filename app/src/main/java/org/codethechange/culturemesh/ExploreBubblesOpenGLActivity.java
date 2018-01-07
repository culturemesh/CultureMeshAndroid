package org.codethechange.culturemesh;

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
import org.jetbrains.annotations.NotNull;

/**
 * Created by nathaniel on 12/27/18.
 */

public class ExploreBubblesOpenGLActivity extends AppCompatActivity {
    BubblePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_bubbles_open_gl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        picker = findViewById(R.id.picker);

        final String[] titles = getResources().getStringArray(R.array.countries);
        final TypedArray colors = getResources().obtainTypedArray(R.array.colors);
        final TypedArray images = getResources().obtainTypedArray(R.array.images);

        picker.setAdapter(new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.length;
            }

            @NotNull
            @Override
            public PickerItem getItem(int position) {
                PickerItem item = new PickerItem();
                item.setTitle(titles[position]);
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                item.setTextColor(ContextCompat.getColor(ExploreBubblesOpenGLActivity.this, android.R.color.white));
                item.setBackgroundImage(ContextCompat.getDrawable(ExploreBubblesOpenGLActivity.this, images.getResourceId(position, 0)));
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
