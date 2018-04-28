package org.codethechange.culturemesh;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.Random;

/**
 * Class to store responses after attempting networking tasks
 */
public class NetworkResponse<E> {
    private boolean fail;
    private int messageID;
    private E payload;

    // TODO: Remove this testing-only constructor
    /**
     * Constructor that randomly chooses a failure state and a generic message based on that state
     */
    public NetworkResponse() {
        Random r = new Random();
        fail = r.nextBoolean();
        if (fail)
            messageID = R.string.genericFail;
        else
            messageID = R.string.genericSuccess;
    }

    /**
     * Constructor that creates a generic message based on "inFail"
     * @param inFail Failure state provided by user (true if failed)
     */
    public NetworkResponse(boolean inFail) {
        fail = inFail;
        if (inFail)
            messageID = R.string.genericFail;
        else
            messageID = R.string.genericSuccess;
    }

    /**
     * Constructor that sets message and failures state based on arguments
     * @param inFail Failure state provided by user (true if failed)
     * @param inMessageID ID for string resource containing message
     */
    public NetworkResponse(int inFail, int inMessageID) {
        fail = inFail;
        messageID = inMessageID;
    }

    /**
     * Constructor that stores a payload and sets the failure state to false
     * @param inPayload Payload returned by networking request
     */
    public NetworkResponse(E inPayload) {
        payload = inPayload;
        fail = false;
    }

    /**
     * Constructor that both stores a payload and sets the failure state from parameters
     * @param inFail Whether or not the network operation failed
     * @param inPayload Payload returned by networking request
     */
    public NetworkResponse(boolean inFail, E inPayload) {
        payload = inPayload;
        fail = inFail;
        if (inFail)
            messageID = R.string.genericFail;
        else
            messageID = R.string.genericSuccess;
    }

    /**
     * Constructor that both stores a payload and sets the failure state from parameters
     * @param inFail Whether or not the network operation failed
     * @param inPayload Payload returned by networking request
     */
    public NetworkResponse(boolean inFail, E inPayload, int messageID) {
        payload = inPayload;
        fail = inFail;
        this.messageID = messageID;
    }


    /**
     * Check whether the network request failed
     * @return true if the request failed, false if it succeeded
     */
    public boolean fail() {
        return fail;
    }

    /**
     * Get the resource ID of the message to display to the user
     * @return Resource ID of message
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * Get an error dialog that can be displayed to show message from messageID to user
     * @param context Context upon which to display error dialog
     * @return Dialog that can be shown
     */
    public AlertDialog getErrorDialog(Context context) {
        // SOURCE: https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
        AlertDialog errDialog = new AlertDialog.Builder(context).create();
        errDialog.setTitle(context.getString(R.string.error));
        errDialog.setMessage(context.getString(messageID));
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        errDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(R.string.ok), listener);
        return errDialog;
    }

    /**
     * Show an error dialog that can be displayed to show message from messageID to user
     * @param context Context upon which to display error dialog
     */
    public void showErrorDialog(Context context) {
        AlertDialog errDialog = getErrorDialog(context);
        errDialog.show();
    }

    /**
     * Get the payload returned by the network operation
     * @return Payload returned by network operation
     */
    public E getPayload() {
        // TODO: This should throw an exception if payload is undefined
        return payload;
    }
}
