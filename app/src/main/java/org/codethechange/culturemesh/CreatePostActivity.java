package org.codethechange.culturemesh;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.codethechange.culturemesh.models.Network;
import org.codethechange.culturemesh.models.Post;
import org.codethechange.culturemesh.models.User;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CreatePostActivity extends AppCompatActivity implements
        ListenableEditText.onSelectionChangedListener {
    SparseBooleanArray formTogState;
    ListenableEditText content;
    SparseArray<int[]> toggleIcons;
    SparseArray<MenuItem> menuItems;
    Network network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        formTogState = new SparseBooleanArray();
        content = findViewById(R.id.postContent);
        content.setOnSelectionChangedListener(this);
        content.setMovementMethod(LinkMovementMethod.getInstance());
        int[] boldIcons = {R.drawable.ic_format_bold_white_24px,
                R.drawable.ic_format_bold_black_24px};
        toggleIcons = new SparseArray<int[]>();
        toggleIcons.put(R.id.bold, boldIcons);
        int[] italicIcons = {R.drawable.ic_format_italic_white_24px,
                R.drawable.ic_format_italic_black_24px};
        toggleIcons.put(R.id.italic, italicIcons);
        int[] linkIcons= {R.drawable.ic_insert_link_white_24px,
                R.drawable.ic_insert_link_black_24px};
        toggleIcons.put(R.id.insert_link, linkIcons);
        menuItems = new SparseArray<MenuItem>();
        //TODO: Random website says parcelable is better than serializable
        //http://www.techjini.com/blog/passing-objects-via-intent-in-android/
        network = (Network) getIntent().getSerializableExtra(TimelineActivity.BUNDLE_NETWORK);
        TextView networkLabel = findViewById(R.id.network_label);
        if (network.isLocationNetwork()) {
            networkLabel.setText(getResources().getString(R.string.from) + " " +
                    network.getFromLocation().shortName() + " " +
                    getResources().getString(R.string.near) + " " +
                    network.getNearLocation().shortName());
        } else {
            networkLabel.setText(network.getLang().toString() + " " +
                    getResources().getString(R.string.speakers_in).toString() + " " +
                    network.getNearLocation().shortName());
        }
        Button postButton = findViewById(R.id.create_post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Replace user with logged in user
                //TODO: Why do we have a title field??
                User user = API.Get.user(new BigInteger("11"));
                String contentHTML = Html.toHtml(content.getText());
                String datePosted = new Date().toString();
                API.Post.post(new Post(user, contentHTML, "",datePosted));
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_post, menu);
        menuItems.put(R.id.bold,menu.findItem(R.id.bold));
        menuItems.put(R.id.italic,menu.findItem(R.id.italic));
        menuItems.put(R.id.insert_link,menu.findItem(R.id.insert_link));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean prevToggle = formTogState.get(id, false);
        int sP = content.getSelectionStart(); //Start position of cursor
        int sE = content.getSelectionEnd(); //End position of cursor - same if no selection
        content.clearFocus();
        if (id != R.id.attach_image && sP >= 0 && sE >= 0) {
            SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
            switch (id) {
                case R.id.bold:
                    if (prevToggle) {
                        //Undo boldness.
                        item.setIcon(R.drawable.ic_format_bold_white_24px);
                        StyleSpan[] spans = sSB.getSpans(sP, sE, StyleSpan.class);
                        for (StyleSpan span : spans) {
                            if (span.getStyle() == Typeface.BOLD) {
                                sSB.removeSpan(span);
                            }
                        }
                    } else {
                        //Make text bold
                        StyleSpan style = new StyleSpan(Typeface.BOLD);
                        item.setIcon(R.drawable.ic_format_bold_black_24px);
                        sSB.setSpan(style, sP, sE, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    break;
                case R.id.italic:
                    if (prevToggle) {
                        item.setIcon(R.drawable.ic_format_italic_white_24px);
                        StyleSpan[] spans = sSB.getSpans(sP, sE, StyleSpan.class);
                        for (StyleSpan span : spans) {
                            if (span.getStyle() == Typeface.ITALIC) {
                                sSB.removeSpan(span);
                            }
                        }
                    } else {
                        StyleSpan style = new StyleSpan(Typeface.ITALIC);
                        item.setIcon(R.drawable.ic_format_italic_black_24px);
                        sSB.setSpan(style, sP, sE, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    break;
                case R.id.insert_link:
                    if (prevToggle) {
                        item.setIcon(R.drawable.ic_insert_link_white_24px);
                        URLSpan[] spans = sSB.getSpans(sP, sE, URLSpan.class);
                        for (URLSpan span : spans) {
                            sSB.removeSpan(span);
                        }
                    } else {
                        item.setIcon(R.drawable.ic_insert_link_black_24px);
                        String link = content.getText().toString().substring(sP, sE);
                        sSB.setSpan(new URLSpan(link), sP, sE, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    break;
            }
            //Strange phenomenon where underline spans from keyboard focus are created.
            //Let's remove them.
            UnderlineSpan[] spans = sSB.getSpans(0, sSB.length(), UnderlineSpan.class);
            for (UnderlineSpan span : spans) {
                sSB.removeSpan(span);
            }
            //Remove the keyboard cursor so we avoid that pesky underline bug.
            InputMethodManager inputManager =
                    (InputMethodManager) getApplicationContext().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null && this.getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow( this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }


            content.setText(sSB);
        }
        //TODO: Allow attach image functionality.

        formTogState.put(id, !prevToggle);
        Log.i("HTML", Html.toHtml(content.getText()));// to submit post HTML

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        //Update the state of the format buttons by checking what spans are inside the new selection
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        formTogState.clear();
        StyleSpan[] styleSpans = sSB.getSpans(selStart, selEnd, StyleSpan.class);
        for(StyleSpan span : styleSpans){
            if (!formTogState.get(R.id.bold, false) && span.getStyle() ==
                    Typeface.BOLD) {
                formTogState.put(R.id.bold, true);
            } else if (!formTogState.get(R.id.italic, false) && span.getStyle() ==
                    Typeface.ITALIC) {
                formTogState.put(R.id.italic, true);
            }
            if (formTogState.get(R.id.bold, false) && formTogState.get(R.id.italic,
                    false)) {
                break;
            }
        }
        URLSpan[] linkSpans = sSB.getSpans(selStart, selEnd, URLSpan.class);
        if (linkSpans.length > 0) {
            formTogState.put(R.id.insert_link, true);
        }
        updateIconToggles();
    }

    void updateIconToggles() {
        //toggleIcons -- Key is menuItem Id, value is array of drawable ids.
        // 0 index is untoggled (white), 1 index is toggled (black)
        //Use fancy conversion from boolean to int to make code more concise.
        for (int keyIndex = 0; keyIndex < toggleIcons.size(); keyIndex++) {
            int id = toggleIcons.keyAt(keyIndex);
            MenuItem item = menuItems.get(id);
            if (item != null) {
                int iconIndex = (formTogState.get(id, false)) ? 1 : 0;
                item.setIcon(toggleIcons.get(id)[iconIndex]);
            }
        }
    }
}
