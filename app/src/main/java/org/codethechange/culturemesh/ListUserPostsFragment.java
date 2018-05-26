package org.codethechange.culturemesh;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.codethechange.culturemesh.models.FeedItem;
import org.codethechange.culturemesh.models.Post;

import java.util.ArrayList;
import java.util.List;

import static org.codethechange.culturemesh.API.SELECTED_USER;

/**
 * Created by Drew Gregory on 03/29/18.
 */
public class ListUserPostsFragment extends Fragment implements RVAdapter.OnItemClickListener {
    View root;
    RecyclerView rv;
    TextView emptyText;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the intent, verify the action and get the query
        root = inflater.inflate(R.layout.rv_container, container, false);
        rv = root.findViewById(R.id.rv);
        //Say it's empty.
        ArrayList<FeedItem> posts = new ArrayList<>();
        RVAdapter adapter = new RVAdapter(posts, this, getActivity());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        emptyText = root.findViewById(R.id.empty_text);
        emptyText.setText(getResources().getString(R.string.no_posts));
        new ListUserPostsFragment.LoadUserPosts().execute(getArguments().getLong(SELECTED_USER, -1));
        return root;

    }

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

    class LoadUserPosts extends AsyncTask<Long, Void, Void> {

        @Override
        protected Void doInBackground(Long... longs) {
            long userId = longs[0];
            API.loadAppDatabase(getActivity());
            RVAdapter adapter = (RVAdapter) rv.getAdapter();

            List<Post> posts = API.Get.userPosts(userId).getPayload();
            for (Post post : posts) {
                post.comments = API.Get.postReplies(post.id).getPayload();
            }
            adapter.getNetPosts().addAll(posts);
            API.closeDatabase();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void v) {
            rv.getAdapter().notifyDataSetChanged();
            if (rv.getAdapter().getItemCount() > 0) {
                //Hide empty text.
                emptyText.setVisibility(View.GONE);
            }
        }
    }
}
