package org.codethechange.culturemesh;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.MenuItem;

/**
 * Created by Drew Gregory on 3/26/18.
 *
 * This class provides a little decomposition from CreatePost/SpeciticPostActivity in that it handles
 * all the formatting involved in writinf posts/post replies. The supported formatting is:
 *  - bold
 *  - italic
 *  - links
 *  This formatting is embedded in the SpannableStrings that EditTexts can produce and maintain.
 *  This manager will also handle the tedious tasks of updating the toggle icons and maintaining state.
 *
 *  When the user is done formatting and wants to publish their post/post reply, call the toString(),
 *  which will convert the spannable to a string with the proper tags as specified by Ian Nottage:
 *  <b>Bold text </b> <i>Italic text</i> <link>Link text</link>
 */

public class FormatManager implements
        ListenableEditText.onSelectionChangedListener{

    public interface IconUpdateListener {
        /**
         * This method will require the parent activity to update the toggle button icons.
         * @param formTogState a SparseBooleanArray (HashMap) with int as key and boolean as value
         *                      key: int id of toggleButton View we are using.
         *                      value: true if toggled, false if not toggled.
         * @param toggleIcons a SparseArray (HashMap) with int as key and int[] as value.
         *                      key: int id of toggleButton View we are using.
         *                      value: int[0] being untoggled icon, int[1] being toggled icon.
         */
        void updateIconToggles(SparseBooleanArray formTogState, SparseArray<int[]> toggleIcons);
    }

    final int START = 0, END = 1;
    @NonNull
    private SparseBooleanArray formTogState;
    @NonNull
    private ListenableEditText content;
    @NonNull
    SparseArray<int[]> toggleIcons;

    private IconUpdateListener mListener;

    public FormatManager(ListenableEditText content,
                         IconUpdateListener listener) {
        //TODO: Pass in ids of views for toggleIcons and formTogState....
        this.formTogState = new SparseBooleanArray();
        this.content = content;
        content.setOnSelectionChangedListener(this);
        mListener = listener;
        toggleIcons = new SparseArray<int[]>();
        int[] boldIcons = {R.drawable.ic_format_bold_white_24px,
                R.drawable.ic_format_bold_black_24px};
        toggleIcons.put(R.id.comment_bold, boldIcons);
        int[] italicIcons = {R.drawable.ic_format_italic_white_24px,
                R.drawable.ic_format_italic_black_24px};
        toggleIcons.put(R.id.comment_italic, italicIcons);
        int[] linkIcons= {R.drawable.ic_insert_link_white_24px,
                R.drawable.ic_insert_link_black_24px};
        toggleIcons.put(R.id.comment_link, linkIcons);
    }

    /**
     * This method will set the currently selected text to bold.
     * @param id int id representing the toggle view.
     */
    void setBold(int id){
        int[] pos = getCursors();
        if (pos[START] < 0 || pos[END] < 0) return; //We can't reset spans if there is no start/end.
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        boolean prevToggle = formTogState.get(id, false);
        if (prevToggle) {
            //Undo boldness.
            StyleSpan[] spans = sSB.getSpans(pos[0], pos[1], StyleSpan.class);
            for (StyleSpan span : spans) {
                if (span.getStyle() == Typeface.BOLD) {
                    sSB.removeSpan(span);
                }
            }
        } else {
            //Make text bold
            StyleSpan style = new StyleSpan(Typeface.BOLD);
            sSB.setSpan(style, pos[START], pos[END], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        formTogState.put(id, !prevToggle);
        content.setText(sSB);
        mListener.updateIconToggles(formTogState, toggleIcons);
    }

    /**
     * This method will set the currently selected text to italic.
     * @param id int id representing the toggle view.
     */
    void setItalic(int id){
        int[] pos = getCursors();
        if (pos[START] < 0 || pos[END] < 0) return; //We can't reset spans if there is no start/end.
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        boolean prevToggle = formTogState.get(id, false);
        if (prevToggle) {
            //Undo italics.
            StyleSpan[] spans = sSB.getSpans(pos[START], pos[END], StyleSpan.class);
            for (StyleSpan span : spans) {
                if (span.getStyle() == Typeface.ITALIC) {
                    sSB.removeSpan(span);
                }
            }
        } else {
            //Redo Italics
            StyleSpan style = new StyleSpan(Typeface.ITALIC);
            sSB.setSpan(style, pos[0], pos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        formTogState.put(id, !prevToggle);
        content.setText(sSB);
        mListener.updateIconToggles(formTogState, toggleIcons);
    }

    /**
     * This method will set the currently selected text to a link.
     * @param id int id representing the toggle view.
     */
    void setLink(int id){
        int[] pos = getCursors();
        if (pos[START] < 0 || pos[END] < 0) return; //We can't reset spans if there is no start/end.
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        boolean prevToggle = formTogState.get(id, false);
        if (prevToggle) {
            //Undo link.
            URLSpan[] spans = sSB.getSpans(pos[START], pos[END], URLSpan.class);
            for (URLSpan span : spans) {
                sSB.removeSpan(span);
            }
        } else {
            //Add link formatting.
            //Use selected text as url.
            String url = content.getText().toString().substring(pos[START], pos[END]);
            sSB.setSpan(new URLSpan(url), pos[START], pos[END], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        formTogState.put(id, !prevToggle);
        content.setText(sSB);
        mListener.updateIconToggles(formTogState, toggleIcons);
    }

    private int[] getCursors() {
        int sP = content.getSelectionStart(); //Start position of cursor
        int sE = content.getSelectionEnd(); //End position of cursor - same if no selection
        content.clearFocus();
        int[] positions = {sP, sE};
        return positions;
    }


    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        //Update the state of the format buttons by checking what spans are inside the new selection
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        formTogState.clear();
        StyleSpan[] styleSpans = sSB.getSpans(selStart, selEnd, StyleSpan.class);
        //Check what spans (formatting areas) are within the selected range.
        for(StyleSpan span : styleSpans){
            if (!formTogState.get(R.id.comment_bold, false) && span.getStyle() ==
                    Typeface.BOLD) {
                //We have a bold span.
                formTogState.put(R.id.comment_bold, true);
            } else if (!formTogState.get(R.id.comment_italic, false) && span.getStyle() ==
                    Typeface.ITALIC) {
                //We have an italic span.
                formTogState.put(R.id.comment_italic, true);
            }
            if (formTogState.get(R.id.comment_bold, false) && formTogState.get(R.id.comment_italic,
                    false)) {
                //If we already have both, then let's break out of the loop: we don't care about
                //additional bold/italics.
                break;
            }
        }
        URLSpan[] linkSpans = sSB.getSpans(selStart, selEnd, URLSpan.class);
        if (linkSpans.length > 0) {
            //We have a link, so let's update the SparseBooleanArray.
            formTogState.put(R.id.comment_link, true);
        }
        mListener.updateIconToggles(formTogState, toggleIcons);
    }

    //TODO: Create toString()
    //TODO: Create static parser method to take string with formatting tags into spannable.
}
