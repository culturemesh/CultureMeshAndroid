package org.codethechange.culturemesh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * This is a custom EditText that allows us to listen for changes in cursor position.
 * {@link CreatePostActivity} uses this view so that the format toggle buttons can update their
 * settings when a new near_region in the edit text is selected.
 */
public class ListenableEditText extends EditText {

    onSelectionChangedListener mListener = null;

    /**
     * {@inheritDoc}
     * @param context {@inheritDoc}
     */
    public ListenableEditText(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     * @param context {@inheritDoc}
     * @param attrs {@inheritDoc}
     */
    public ListenableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * {@inheritDoc}
     * @param context {@inheritDoc}
     * @param attrs {@inheritDoc}
     * @param defStyleAttr {@inheritDoc}
     */
    public ListenableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * When the selection changes, if it is due to the user typing a character,
     * {@link ListenableEditText#mListener#onSelectionChanged(int, int)} is called with the
     * provided parameters. Otherwise, the superclass method
     * {@link EditText#onSelectionChanged(int, int)} is called with the parameters.
     * @param selStart TODO: What is this?
     * @param selEnd TODO: What is this?
     */
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (selStart - 1 != selStart && mListener != null) {
            //That is just a shift when the user types a character
            mListener.onSelectionChanged(selStart, selEnd);
        }
        super.onSelectionChanged(selStart, selEnd);
    }

    /**
     * Interface that all listeners for {@link ListenableEditText#mListener} must satisfy.
     */
    public interface onSelectionChangedListener {
        void onSelectionChanged(int selStart, int selEnd);
    }

    /**
     * Set the listener to the provided parameter
     * @param listener Listener to use when text selection changes
     */
    public void setOnSelectionChangedListener(onSelectionChangedListener listener) {
        mListener = listener;
    }
}
