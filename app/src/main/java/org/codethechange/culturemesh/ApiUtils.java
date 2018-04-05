package org.codethechange.culturemesh;

import android.os.AsyncTask;

import org.codethechange.culturemesh.models.Network;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by cs on 3/28/18.
 */

public class ApiUtils {

    private static final String CURRENT_USER_BUNDLE_ID = "currUser";
    private static final String APPLICATION_CONTEXT_BUNDLE_ID = "currUser";

    public static boolean hasJoinedNetwork(
            long currUser, AsyncTask<Long, Void, NetworkResponse<ArrayList<Network>>> task) {
        NetworkResponse<ArrayList<Network>> response = getJoinedNetworks(currUser, task);
        if (response.fail() || response.getPayload().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static NetworkResponse<ArrayList<Network>> getJoinedNetworks(
            long currUser, AsyncTask<Long, Void, NetworkResponse<ArrayList<Network>>> task) {
        task.execute(currUser);
        NetworkResponse<ArrayList<Network>> result;
        try {
            result = task.get();
        } catch (InterruptedException e) {
            result = new NetworkResponse<>(true);
        } catch (ExecutionException e) {
            result = new NetworkResponse<>(true);
        }
        return result;
    }
}
