package org.codethechange.culturemesh;

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
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Type;
import java.util.HashMap;

public class CreatePostActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    SparseBooleanArray formTogState;
    EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        formTogState = new SparseBooleanArray();
        content = findViewById(R.id.postContent);
        content.setOnFocusChangeListener(this);
        content.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_post, menu);
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
            //Strange phenomenon where random underline spans are created. Let's remove them.
            UnderlineSpan[] spans = sSB.getSpans(0, sSB.length(), UnderlineSpan.class);
            for (UnderlineSpan span : spans) {
                sSB.removeSpan(span);
            }
            content.setText(sSB);
        }



        //TODO: Actually save/update edittext formatting with toggles.

        //TODO: Allow attach image functionality.

        formTogState.put(id, !prevToggle);
        Log.i("HTML", Html.toHtml(content.getText()));// to submit post HTML

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.i("Focus","Focused changed to " + hasFocus);
    }





}
