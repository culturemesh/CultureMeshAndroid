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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.codethechange.culturemesh.models.User;

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

    public static final String USER_NAMES = "usernames", IMAGE_URLS = "imgurls", USER_IDS ="userids";

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.rv_container, null);
        dialog.setContentView(contentView);
        contentView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //This view originally has it's width and height to match_parent... let's programmatically
        //set its height.
        FrameLayout.LayoutParams fragParams = (FrameLayout.LayoutParams) contentView.getLayoutParams();
        Point screenDimens = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(screenDimens);
        fragParams.height = (int) (screenDimens.y * .7);
        contentView.setLayoutParams(fragParams);
        RecyclerView rv = contentView.findViewById(R.id.rv);
        long[] userIDs = getArguments().getLongArray(USER_IDS);
        RecyclerView.Adapter adapter = new UsersListAdapter(getActivity(),
                Objects.requireNonNull(getArguments().getStringArrayList(USER_NAMES)),
                Objects.requireNonNull(getArguments().getStringArrayList(IMAGE_URLS)),
                userIDs);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        TextView emptyText = contentView.findViewById(R.id.empty_text);
        if (userIDs.length == 0) {
            emptyText.setText(getResources().getString(R.string.no_users));
        } else {
            emptyText.setVisibility(View.GONE);
        }

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

    }
}
