package org.codethechange.culturemesh;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import org.codethechange.culturemesh.models.Event;
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
        //This hashmap-like object helps us keep track of the settings of the format buttons.
        formTogState = new SparseBooleanArray();
        content = findViewById(R.id.postContent);
        content.setOnSelectionChangedListener(this);
        //Allow links to redirect to browser.
        content.setMovementMethod(LinkMovementMethod.getInstance());

        //Set up a hashmap-like object that makes updating the toggle settings concise.
        //Check out ListenableEditText.java for more info
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

        //Instantiate a hash-map like object that is also used for updating toggle settings.
        //This sparseArray will be updated with views during onCreateOptionsMenu.
        menuItems = new SparseArray<MenuItem>();

        //Fetch the current network from Intent Bundle.
        //TODO: Random website says parcelable is better than serializable
        //http://www.techjini.com/blog/passing-objects-via-intent-in-android/
        network = (Network) getIntent().getSerializableExtra(TimelineActivity.BUNDLE_NETWORK);

        //Update text with network name.
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

        //Set onClick for Post button.
        Button postButton = findViewById(R.id.create_post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Replace user with logged in user
                //TODO: Why do we have a title field??
                User user = API.Get.user(new BigInteger("11"));
                String contentHTML = Html.toHtml(content.getText());
                String datePosted = new Date().toString();

                Post newPost = new Post(user, contentHTML, datePosted);
                new PostPost().execute(newPost);
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

    /**
     * This function handles what happens when our format toggle buttons are clicked.
     * We want to update the content formatting when this happens as well with Spannables.
     * Check out https://stackoverflow.com/questions/10828182/spannablestringbuilder-to-create-string-with-multiple-fonts-text-sizes-etc-examp
     * for more info.
     * @param item the MenuItem that was tapped.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean prevToggle = formTogState.get(id, false);
        int sP = content.getSelectionStart(); //Start position of cursor
        int sE = content.getSelectionEnd(); //End position of cursor - same if no selection
        content.clearFocus();
        if (id != R.id.attach_image && sP >= 0 && sE >= 0) { //Changing formatting
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
                        //Undo italics.
                        item.setIcon(R.drawable.ic_format_italic_white_24px);
                        StyleSpan[] spans = sSB.getSpans(sP, sE, StyleSpan.class);
                        for (StyleSpan span : spans) {
                            if (span.getStyle() == Typeface.ITALIC) {
                                sSB.removeSpan(span);
                            }
                        }
                    } else {
                        //Redo Italics
                        StyleSpan style = new StyleSpan(Typeface.ITALIC);
                        item.setIcon(R.drawable.ic_format_italic_black_24px);
                        sSB.setSpan(style, sP, sE, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    break;
                case R.id.insert_link:
                    if (prevToggle) {
                        //Undo link.
                        item.setIcon(R.drawable.ic_insert_link_white_24px);
                        URLSpan[] spans = sSB.getSpans(sP, sE, URLSpan.class);
                        for (URLSpan span : spans) {
                            sSB.removeSpan(span);
                        }
                    } else {
                        //Add link formatting.
                        item.setIcon(R.drawable.ic_insert_link_black_24px);
                        //Use selected text as url.
                        String url = content.getText().toString().substring(sP, sE);
                        sSB.setSpan(new URLSpan(url), sP, sE, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    break;
            }
            //Strange phenomenon where underline spans from keyboard focus are created.
            //Let's remove them.
            UnderlineSpan[] spans = sSB.getSpans(0, sSB.length(), UnderlineSpan.class);
            for (UnderlineSpan span : spans) {
                sSB.removeSpan(span);
            }
            //Also remove the keyboard cursor so we avoid that pesky underline bug.
            InputMethodManager inputManager =
                    (InputMethodManager) getApplicationContext().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null && this.getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow( this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
            //Update formatting for ListenableEditText with the SpannableStringBuilder.
            content.setText(sSB);
        }
        //TODO: Allow for attach image functionality.

        formTogState.put(id, !prevToggle);
        Log.i("HTML", Html.toHtml(content.getText()));// to submit post HTML

        return super.onOptionsItemSelected(item);
    }


    /**
     * This is the listener for when the cursor in the ListenableEditText is changed deliberately
     * by the user - ignoring when the user merely types in the content area and the cursor shifts
     * one position. We have to update the format toggle settings accordingly with the new position.
     * @param selStart The start cursor position.
     * @param selEnd The end cursor position: =selStart if no text is selected, >selStart if text
     *               is selected.
     */
    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        //Update the state of the format buttons by checking what spans are inside the new selection
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        formTogState.clear();
        StyleSpan[] styleSpans = sSB.getSpans(selStart, selEnd, StyleSpan.class);
        //Check what spans (formatting areas) are within the selected range.
        for(StyleSpan span : styleSpans){
            if (!formTogState.get(R.id.bold, false) && span.getStyle() ==
                    Typeface.BOLD) {
                //We have a bold span.
                formTogState.put(R.id.bold, true);
            } else if (!formTogState.get(R.id.italic, false) && span.getStyle() ==
                    Typeface.ITALIC) {
                //We have an italic span.
                formTogState.put(R.id.italic, true);
            }
            if (formTogState.get(R.id.bold, false) && formTogState.get(R.id.italic,
                    false)) {
                //If we already have both, then let's break out of the loop: we don't care about
                //additional bold/italics.
                break;
            }
        }
        URLSpan[] linkSpans = sSB.getSpans(selStart, selEnd, URLSpan.class);
        if (linkSpans.length > 0) {
            //We have a link, so let's update the SparseBooleanArray.
            formTogState.put(R.id.insert_link, true);
        }
        updateIconToggles();
    }

    /**
     * This fancy function uses our SparseArray's to concisely iterate over our toggle icons
     * and update their colors - white if untoggled, black if toggled.
     */
    void updateIconToggles() {
        //toggleIcons -- Key is menuItem Id, value is array of drawable ids.
        for (int keyIndex = 0; keyIndex < toggleIcons.size(); keyIndex++) {
            //Get id of toggle icon - key of toggleIcons SparseArray
            int id = toggleIcons.keyAt(keyIndex);
            //Get corresponding menuItem - value of menuItems SparseArray
            MenuItem item = menuItems.get(id);
            if (item != null) {
                //Get index of toggleIcon array for corresponding drawable id.
                //0 index is untoggled (false/white), 1 index is toggled (true/black)
                //Use fancy conversion from boolean to int to make code more concise.
                int iconIndex = (formTogState.get(id, false)) ? 1 : 0;
                //Update icon!
                item.setIcon(toggleIcons.get(id)[iconIndex]);
            }
        }
    }

    /**
     * AsyncTask class to handle network latency when POSTing post
     */
    private class PostPost extends AsyncTask<Post, Integer, Boolean> {

        ProgressBar progressBar;

        /**
         * In the background, POST the provided post
         * @param posts List of posts, the first of which will be POSTed
         * @return true if POSTing succeeded, false otherwise
         */
        @Override
        protected Boolean doInBackground(Post... posts) {
            API.Post.post(posts[0]);
            // TODO: Return false if POSTing fails
            return true;
        }

        /**
         * Makes the progress bar indeterminate
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.postPostProgressBar);
            progressBar.setIndeterminate(true); // Only because cannot get status from API
        }

        /**
         * Closes the activity and returns the user to the previous screen
         * @param aBoolean Status of doInBackground method that represents whether POSTing succeeded
         */
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            finish();
        }
    }
}
