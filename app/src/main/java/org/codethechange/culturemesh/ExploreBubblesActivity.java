package org.codethechange.culturemesh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.jawnnypoo.physicslayout.Physics;
import com.jawnnypoo.physicslayout.PhysicsFrameLayout;

import org.codethechange.culturemesh.models.Location;
import org.codethechange.culturemesh.models.Network;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExploreBubblesActivity extends AppCompatActivity {
    private static final String TAG = "TESTING";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.physics_layout) PhysicsFrameLayout physicsLayout;
    @BindView(R.id.add_view_button) View addViewButton;

    int networkIndex;
    ExploreBubblesActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_bubbles);
        ButterKnife.bind(this);
        toolbar.setTitle("Explore");
        toolbar.inflateMenu(R.menu.menu_find_network);
        physicsLayout.getPhysics().enablePhysics();
        physicsLayout.getPhysics().enableFling();

        final View circleView = findViewById(R.id.circle);
        addViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        physicsLayout.getPhysics().addOnPhysicsProcessedListener(new Physics.OnPhysicsProcessedListener() {
            @Override
            public void onPhysicsProcessed(Physics physics, World world) {
                Body body = physics.findBodyById(circleView.getId());
                if (body != null) {
                    body.applyAngularImpulse(0.001f);
                } else {
                    Log.e(TAG, "Cannot rotate, body was null");
                }
            }
        });

        physicsLayout.getPhysics().setOnBodyCreatedListener(new Physics.OnBodyCreatedListener() {
            @Override
            public void onBodyCreated(View view, Body body) {
                Log.d(TAG, "Body created for view " + view.getId());
            }
        });
    }

    public void addView(Network network) {
        // TODO: Add circle radius resizing based on populations.
        ExploreBubble bubble = new ExploreBubble(ExploreBubblesActivity.this, network);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.circlesize),
                getResources().getDimensionPixelSize(R.dimen.circlesize));
        bubble.setLayoutParams(llp);
        bubble.setId(networkIndex);
        physicsLayout.addView(bubble);
        networkIndex++;
    }
}

class ExploreBubblesActivityViewModel {
    ArrayList<Network> nearbyNetworks;
    NetworksFetcher fetcher;


    public ExploreBubblesActivityViewModel() {
        nearbyNetworks = fetcher.getNetworksFromLocation(new Location());
    }
}

interface NetworksFetcher {
    ArrayList<Network> getNetworksFromLocation(Location loc);
}