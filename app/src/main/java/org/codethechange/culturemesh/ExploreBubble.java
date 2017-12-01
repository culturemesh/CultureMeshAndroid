package org.codethechange.culturemesh;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.pavlospt.CircleView;

import org.codethechange.culturemesh.models.LangNetwork;
import org.codethechange.culturemesh.models.LocNetwork;
import org.codethechange.culturemesh.models.Network;

import java.util.Random;

/**
 * Created by nathaniel on 12/1/17.
 */

class ExploreBubble extends CircleView {
    public ExploreBubble(Context context) {
        super(context);
    }

    public ExploreBubble(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExploreBubble(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setNetwork(Network network) {
        this.network = network;
        if (network instanceof LocNetwork) {
            LocNetwork n = (LocNetwork) network;
            this.setTitleText(n.getFromLocation().getCity());
        } else if (network instanceof LangNetwork) {
            LangNetwork n = (LangNetwork) network;
            this.setTitleText(n.getLang().getName());
        }
    }

    Network network;

    public ExploreBubble(Context context, Network network) {
        super(context);
        setNetwork(network);
        setAppearance();
    }

    public ExploreBubble(Context context, AttributeSet attrs, Network network) {
        super(context, attrs);
        setNetwork(network);
        setAppearance();
    }

    public ExploreBubble(Context context, AttributeSet attrs, int defStyle, Network network) {
        super(context, attrs, defStyle);
        setNetwork(network);
        setAppearance();
    }

    private void setAppearance() {
        Color color = generateRandomColor();
        this.setBackgroundColor(color.toArgb());
        this.setTitleColor(shouldUseDarkText(color) ? Color.BLACK: Color.WHITE);
    }

    static boolean shouldUseDarkText(Color color) {
        return (color.red() * 0.299 + color.green()*0.587 + color.blue()*0.114) > 186;
    }

    static Color generateRandomColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return Color.valueOf(r, g, b);
    }
}
