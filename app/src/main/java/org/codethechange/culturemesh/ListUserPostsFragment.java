package org.codethechange.culturemesh;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.codethechange.culturemesh.models.FeedItem;
import org.codethechange.culturemesh.models.Post;

import java.util.ArrayList;

import static org.codethechange.culturemesh.API.SELECTED_USER;

/**
 * Creates screen that displays the {@link Post}s a {@link org.codethechange.culturemesh.models.User}
 * has made.
 */
public class ListUserPostsFragment extends Fragment implements RVAdapter.OnItemClickListener {

    /**
     * The inflated user interface
     */
    View root;

    /**
     * Scrollable list of {@link Post}s
     */
    RecyclerView rv;

    /**
     * Displays {@link R.string#no_posts} if there are no {@link Post}s to display
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
    public static ListUserPostsFragment newInstance(long selUser) {
        ListUserPostsFragment fragment = new ListUserPostsFragment();
        Bundle args = new Bundle();
        args.putLong(SELECTED_USER, selUser);
        fragment.setArguments(args);
        return fragment;
    }

    /** Create the user interface.
     *  Also populate the list of {@link Post}s with the result from
     * {@link API.Get#userPosts(RequestQueue, long, Response.Listener)}
     * @param inflater Inflates the user interface from {@link R.layout#rv_container} with the
     *                 provided {@code container} as the parent.
     * @param container Parent used by {@code inflater}
     * @param savedInstanceState Not used
     * @return The inflated user interface
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the intent, verify the action and get the query
        root = inflater.inflate(R.layout.rv_container, container, false);
        rv = root.findViewById(R.id.rv);
        //Say it's empty.
        ArrayList<FeedItem> posts = new ArrayList<>();
        queue = Volley.newRequestQueue(getActivity());
        final RVAdapter adapter = new RVAdapter(posts, this, getActivity());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        emptyText = root.findViewById(R.id.empty_text);
        emptyText.setText(getResources().getString(R.string.loading));
        API.Get.userPosts(queue, getArguments().getLong(SELECTED_USER, -1), new Response.Listener<NetworkResponse<ArrayList<Post>>>() {

            @Override
            public void onResponse(NetworkResponse<ArrayList<Post>> response) {
                if (!response.fail()) {
                    //Add the posts to the feed.
                    adapter.getNetPosts().addAll(response.getPayload());
                    //However, they don't have their comments yet. Let's add their comments.
                    rv.getAdapter().notifyDataSetChanged();
                    if (rv.getAdapter().getItemCount() > 0) {
                        //Hide empty text.
                        emptyText.setVisibility(View.GONE);
                    } else {
                        emptyText.setText(getResources().getString(R.string.no_posts));
                    }
                } else {
                    response.showErrorDialog(getActivity());
                }
            }
        });
        return root;
    }

    /**
     * When the user clicks on an item, redirect them to {@link SpecificPostActivity} where more
     * details, including comments, are displayed.
     * @param item The clicked item.
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
