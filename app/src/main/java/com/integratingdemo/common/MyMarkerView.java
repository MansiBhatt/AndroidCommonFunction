
package com.integratingdemo.common;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.integratingdemo.R;

import java.util.ArrayList;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    ArrayList<String> namearrayFull;

    public MyMarkerView(Context context, ArrayList<String> namearrayFull) {
        super(context, R.layout.custom_marker_view);

        this.namearrayFull = namearrayFull;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

//            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
            Log.e("TAG", "Full name...." + namearrayFull.get((int) e.getX()));
            tvContent.setText(namearrayFull.get((int) e.getX()));
        } else {
            Log.e("TAG", "Full name...." + namearrayFull.get((int) e.getX()));
            tvContent.setText(namearrayFull.get((int) e.getX()));
            //  tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
        }
    }

    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }

    @Override
    public void draw(Canvas canvas, float posx, float posy) {
        // take offsets into consideration
        posx += getXOffset(posx);
        posy = 0;
        int pos_left = getResources().getInteger(R.integer.pos_left);
        int pos_right = getResources().getInteger(R.integer.pos_right);

        // AVOID OFFSCREEN
        if (posx < pos_left)
            posx = pos_left;
        if (posx > pos_right)
            posx = pos_right;

        // translate to the correct position and draw
        canvas.translate(posx, posy);
        draw(canvas);
        canvas.translate(-posx, -posy);
    }
}
