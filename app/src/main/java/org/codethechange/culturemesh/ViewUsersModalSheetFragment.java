package org.codethechange.culturemesh;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.codethechange.culturemesh.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created By Drew Gregory on 03/30/18.
 * This shows the subscribed users in the network using a modal bottom sheet
 * https://material.io/guidelines/components/bottom-sheets.html#bottom-sheets-modal-bottom-sheets
 * Also, inspiration from the following blog posts:
 *  - https://android-developers.googleblog.com/2016/02/android-support-library-232.html
 *  - https://code.tutsplus.com/articles/how-to-use-bottom-sheets-with-the-design-support-library--cms-26031
 */
public class ViewUsersModalSheetFragment extends BottomSheetDialogFragment{

    /**
     * Keys for values passed as arguments to the fragment
     */
    public static final String NETWORK_ID = "id";

    /**
     * Queue for asynchronous tasks
     */
    RequestQueue queue;

    /**
     * Handles interactions with the list of subscribed users
     */
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback =
            new BottomSheetBehavior.BottomSheetCallback() {

                /**
                 * If the {@code newState} is equal to {@link BottomSheetBehavior#STATE_HIDDEN},
                 * dismiss.
                 * @param bottomSheet
                 * @param newState
                 */
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

                /**
                 * Does nothing
                 * @param bottomSheet {@inheritDoc}
                 * @param slideOffset {@inheritDoc}
                 */
        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    /**
     * Create and configure {@link View} from {@link R.layout#rv_container}. Populate the fields
     * in that {@link View} with the result of
     * {@link API.Get#networkUsers(RequestQueue, long, Response.Listener)}
     * @param dialog {@link Dialog} whose contents will be set using the {@link View} inflated from
     *                             {@link R.layout#rv_container}
     * @param style Not used
     */
    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.rv_container, null);
        dialog.setContentView(contentView);
        contentView.setBackgroundColor(getResources().getColor(R.color.white));
        queue = Volley.newRequestQueue(getActivity());
        //This view originally has it's width and height to match_parent... let's programmatically
        //set its height.
        FrameLayout.LayoutParams fragParams = (FrameLayout.LayoutParams) contentView.getLayoutParams();
        Point screenDimens = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(screenDimens);
        fragParams.height = (int) (screenDimens.y * .7);
        contentView.setLayoutParams(fragParams);
        RecyclerView rv = contentView.findViewById(R.id.rv);
        rv.setNestedScrollingEnabled(false);
        final UsersListAdapter adapter = new UsersListAdapter(getActivity(),new ArrayList<User>());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        final TextView emptyText = contentView.findViewById(R.id.empty_text);
        emptyText.setText(getResources().getString(R.string.loading));
        Log.i("ViewUsers", "in Fragment on Create View");
        Log.i("SEL NETWORK", Long.parseLong(getArguments().getString(NETWORK_ID)) + "");
        API.Get.networkUsers(queue, Long.parseLong(getArguments().getString(NETWORK_ID)), new Response.Listener<NetworkResponse<ArrayList<User>>>() {
            @Override
            public void onResponse(NetworkResponse<ArrayList<User>> response) {
                if (response.fail()) {
                    response.showErrorDialog(getActivity());
                } else {
                    if (response.getPayload().size() == 0) {
                        emptyText.setText(getResources().getString(R.string.no_users));
                    } else {
                        adapter.getUsers().addAll(response.getPayload());
                        adapter.notifyDataSetChanged();
                        emptyText.setVisibility(View.GONE);
                    }

                }
            }
        });
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }
}
