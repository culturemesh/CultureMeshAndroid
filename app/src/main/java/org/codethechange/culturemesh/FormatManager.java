package org.codethechange.culturemesh;

import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

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
    
    private int boldIcon, italicIcon, linkIcon; 

    private IconUpdateListener mListener;

    FormatManager(ListenableEditText content,
                         IconUpdateListener listener, int boldIcon, int italicIcon, int linkIcon) {
        //TODO: Pass in ids of views for toggleIcons and formTogState....
        this.formTogState = new SparseBooleanArray();
        this.content = content;
        content.setOnSelectionChangedListener(this);
        mListener = listener;
        toggleIcons = new SparseArray<int[]>();
        int[] boldIcons = {R.drawable.ic_format_bold_white_24px,
                R.drawable.ic_format_bold_black_24px};
        toggleIcons.put(boldIcon, boldIcons);
        int[] italicIcons = {R.drawable.ic_format_italic_white_24px,
                R.drawable.ic_format_italic_black_24px};
        toggleIcons.put(italicIcon, italicIcons);
        int[] linkIcons= {R.drawable.ic_insert_link_white_24px,
                R.drawable.ic_insert_link_black_24px};
        toggleIcons.put(linkIcon, linkIcons);
        this.boldIcon = boldIcon;
        this.italicIcon = italicIcon;
        this.linkIcon = linkIcon;
    }

    /**
     * This method will set the currently selected text to bold.
     */
    void setBold(){
        int[] pos = getCursors();
        if (pos[START] < 0 || pos[END] < 0) return; //We can't reset spans if there is no start/end.
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        boolean prevToggle = formTogState.get(boldIcon, false);
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
        formTogState.put(boldIcon, !prevToggle);
        content.setText(sSB);
        mListener.updateIconToggles(formTogState, toggleIcons);
    }

    /**
     * This method will set the currently selected text to italic
     */
    void setItalic(){
        int[] pos = getCursors();
        if (pos[START] < 0 || pos[END] < 0) return; //We can't reset spans if there is no start/end.
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        boolean prevToggle = formTogState.get(italicIcon, false);
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
        formTogState.put(italicIcon, !prevToggle);
        content.setText(sSB);
        mListener.updateIconToggles(formTogState, toggleIcons);
    }

    /**
     * This method will set the currently selected text to a link.
     */
    void setLink(){
        int[] pos = getCursors();
        if (pos[START] < 0 || pos[END] < 0) return; //We can't reset spans if there is no start/end.
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        boolean prevToggle = formTogState.get(linkIcon, false);
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
        formTogState.put(linkIcon, !prevToggle);
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
            if (!formTogState.get(boldIcon, false) && span.getStyle() ==
                    Typeface.BOLD) {
                //We have a bold span.
                formTogState.put(boldIcon, true);
            } else if (!formTogState.get(italicIcon, false) && span.getStyle() ==
                    Typeface.ITALIC) {
                //We have an italic span.
                formTogState.put(italicIcon, true);
            }
            if (formTogState.get(boldIcon, false) && formTogState.get(italicIcon,
                    false)) {
                //If we already have both, then let's break out of the loop: we don't care about
                //additional bold/italics.
                break;
            }
        }
        URLSpan[] linkSpans = sSB.getSpans(selStart, selEnd, URLSpan.class);
        if (linkSpans.length > 0) {
            //We have a link, so let's update the SparseBooleanArray.
            formTogState.put(linkIcon, true);
        }
        mListener.updateIconToggles(formTogState, toggleIcons);
    }

    /**
     * Gets the EditText content in the desired tag format. See comment above.
     */
    public String toString() {
        SpannableStringBuilder sSB = new SpannableStringBuilder(content.getText());
        String html = Html.toHtml(content.getText());
        //We need to swap out the link tags from <a href=""></a> to <link></link>
        html = html.replaceAll("</a>", "</link>");
        html = html.replaceAll("<a[^<>]*>", "<link>");
        //Remove all tags that are not <link><b><i>
        html = html.replaceAll("<[^/bil][^<>]*>", "");
        html = html.replaceAll("</[^bil][^<>]*>", "");
        return html;
    }

    /**
     * This function converts the CultureMesh tags into a spannable string for textview.
     * @param formattedText should only have {@code <b></b>, <link></link>, <i></i>
     *                      or [b][/b][link][/link][i][/i]}
     * @param colorString the link color in RGB. Some text has different color backgrounds.
     * @return Spannable to be passed to TextView.
     */
    public static Spanned parseText(String formattedText, String colorString) {
        if (colorString == null || colorString.length() != 7) {
            //Use default color.
            colorString = "#E23824";
        }
        // Now we look for user inputted formatting from the server. We need to replace the [] with <>
        // because SpannableStringBuilder works well with tags.
        formattedText = formattedText.replaceAll("\\[b\\]","<b>");
        formattedText = formattedText.replaceAll("\\[/b\\]","</b>");
        formattedText = formattedText.replaceAll("\\[i\\]","<i>");
        formattedText = formattedText.replaceAll("\\[/i\\]","</i>");
        formattedText = formattedText.replaceAll("\\[link\\]","<link>");
        formattedText = formattedText.replaceAll("\\[/link\\]","</link>");
        //Replace <link> with <a>
        Log.i("Pre-format", formattedText);
        try {
            int cursor = 0;
            for (cursor = formattedText.indexOf("<link>", cursor); cursor != -1; cursor = formattedText.indexOf("<link>", cursor)) {
                //We need to replace <link> with <a href="">
                String before = formattedText.substring(0, cursor);
                //Cut out <link>
                formattedText = before + formattedText.substring(cursor + "<link>".length());
                String link = formattedText.substring(cursor, formattedText.indexOf("</link>",cursor));
                Log.i("BEFORE",before);
                Log.i("LINK",link);
                Log.i("AFTER", formattedText.substring(cursor));
                formattedText = before + "<font color='" + colorString + "'><a href=\"" + link + "\">" + formattedText.substring(cursor);
                Log.i("formattedTextB4rF",formattedText);
            }
        } catch(StringIndexOutOfBoundsException e) {
            //TODO: Do some error handling when having malformed text.
            Log.e("In formatManager", "Format Error");
            e.printStackTrace();

        }
        formattedText = formattedText.replace("</link>","</font></a>");
        Log.i("Post-parse",formattedText);
        return fromHtml(formattedText);
    }


    /**
     *
     * Different Android versions use different fromHtml method signatures.
     * Sourced from https://stackoverflow.com/questions/37904739/html-fromhtml-deprecated-in-android-n
     * @param html
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

    /**
     * In the interest of screen space and accessibility, we will format the number to have
     * a magnitude suffix instead of the exact number.
     * @param number exact number, in floating point if necessary.
     * @return Formatted String representing number magnitude (e.x. 100K)
     */
    public static String abbreviateNumber(long number) {
        //TODO: Manipulate string of number to have magnitude suffix (K,M,etc.)
        String abbrev = number + "";
        String suffix = "";
        if (number < 1000) {
            return abbrev + suffix;
        } else if (number < Math.pow(10,6)) {
            suffix = "K";
        } else if (number < Math.pow(10,9)) {
            suffix = "M";
        } else if (number < Math.pow(10,12)) {
            suffix = "B";
        } else {
            return "10^" + Math.log10(number);
        }
        abbrev = abbrev.substring(0,3);
        return abbrev + suffix;
    }

}


