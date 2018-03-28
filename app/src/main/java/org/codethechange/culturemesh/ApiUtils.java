package org.codethechange.culturemesh;

import android.os.AsyncTask;

import org.codethechange.culturemesh.models.Network;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by cs on 3/28/18.
 */

public class ApiUtils {
    public static NetworkResponse<ArrayList<Network>> getJoinedNetworks(long currUser) {
        AsyncTask<Long, Void, NetworkResponse<ArrayList<Network>>> task = new checkJoinedNetworks();
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

    private static class checkJoinedNetworks extends AsyncTask<Long, Void, NetworkResponse<ArrayList<Network>>> {
        @Override
        protected NetworkResponse<ArrayList<Network>> doInBackground(Long... longs) {
            long currUser = longs[0];
            NetworkResponse<ArrayList<Network>> responseNetworks = API.Get.userNetworks(currUser);
            return responseNetworks;
        }

        @Override
        protected void onPostExecute(NetworkResponse<ArrayList<Network>> arrayListNetworkResponse) {
            super.onPostExecute(arrayListNetworkResponse);
        }
    }
}
