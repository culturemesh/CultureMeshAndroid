package org.codethechange.culturemesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.codethechange.culturemesh.models.Event;
import org.codethechange.culturemesh.models.FeedItem;
import org.codethechange.culturemesh.models.Post;

import java.util.ArrayList;

import static org.codethechange.culturemesh.API.SELECTED_USER;

/**
 * This fragment lists the the events a user is subscribed to. It is used in ViewProfileActivity.
 */
public class ListUserEventsFragment extends Fragment implements RVAdapter.OnItemClickListener {

    /**
     * Scrollable list of events.
     */
    RecyclerView rv;

    /**
     * Text field that displays {@link R.string#no_events} if there are no events to display
     */
    TextView emptyText;

    /**
     * Queue for asynchronous tasks
     */
    RequestQueue queue;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListUserEventsFragment newInstance(long selUser) {
        ListUserEventsFragment fragment = new ListUserEventsFragment();
        Bundle args = new Bundle();
        args.putLong(SELECTED_USER, selUser);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Setup the user interface to display the list of events and populate that list with the
     * result of calling {@link API.Get#userEvents(RequestQueue, long, String, Response.Listener)}.
     * @param inflater Inflates the user interface specified in {@link R.layout#rv_container}
     * @param container Parent of the generated hierarchy of user interface elements
     * @param savedInstanceState Saved state to restore
     * @return Inflated user interface
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(getActivity());
        // Get the intent, verify the action and get the query
        View root = inflater.inflate(R.layout.rv_container, container, false);
        rv = root.findViewById(R.id.rv);
        //Say it's empty.
        ArrayList<FeedItem> posts = new ArrayList<>();
        final RVAdapter adapter = new RVAdapter(posts, this, getActivity());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        emptyText = root.findViewById(R.id.empty_text);
        emptyText.setText(getResources().getString(R.string.loading));
        API.Get.userEvents(queue, getArguments().getLong(SELECTED_USER, -1), API.HOSTING, new Response.Listener<NetworkResponse<ArrayList<Event>>>() {
            @Override
            public void onResponse(NetworkResponse<ArrayList<Event>> response) {
                if (response.fail()) {
                    response.showErrorDialog(getActivity());
                    emptyText.setText(getResources().getString(R.string.no_events));
                } else {
                    adapter.getNetPosts().addAll(response.getPayload());
                    adapter.notifyDataSetChanged();
                    if (rv.getAdapter().getItemCount() > 0) {
                        //Hide empty text.
                        emptyText.setVisibility(View.GONE);
                    } else {
                        emptyText.setText(getResources().getString(R.string.no_events));
                    }
                }
            }
        });
        return root;

    }

    /**
     * When an item is clicked, if it is a {@link Post}, the user is sent to a screen to view the
     * post in more detail, including comments. If the item is an {@link Event}, no action is taken.
     * @param item The item that was clicked
     */
    @Override
    public void onItemClick(FeedItem item) {
        Intent intent = new Intent(getActivity(), SpecificPostActivity.class);
        long id;
        try {
            id = ((Post) item).id;
            intent.putExtra("postID", id);
            intent.putExtra("networkID", ((Post) item).network.id);
            getActivity().startActivity(intent);
        } catch(ClassCastException e) {
            //I don't think we have commenting support for events??
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "Cannot open post", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This ensures that we are canceling all network requests if the user is leaving this activity.
     * We use a RequestFilter that accepts all requests (meaning it cancels all requests)
     */
    @Override
    public void onStop() {
        super.onStop();
        if (queue != null)
            queue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
    }

}


