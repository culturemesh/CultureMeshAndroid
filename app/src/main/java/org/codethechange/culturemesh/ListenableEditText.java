package org.codethechange.culturemesh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * TODO: document your custom view class.
 */
public class ListenableEditText extends EditText {

    onSelectionChangedListener mListener = null;

    int prevSelStart = 0;
    public ListenableEditText(Context context) {
        super(context);
    }

    public ListenableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListenableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (selStart - 1 != selStart && mListener != null) { //That is just a shift when the user types a character
            mListener.onSelectionChanged(selStart, selEnd);
        }
        super.onSelectionChanged(selStart, selEnd);
    }

    public interface onSelectionChangedListener {
        void onSelectionChanged(int selStart, int selEnd);
    }

    public void setOnSelectionChangedListener(onSelectionChangedListener listener) {
        mListener = listener;
    }

}
