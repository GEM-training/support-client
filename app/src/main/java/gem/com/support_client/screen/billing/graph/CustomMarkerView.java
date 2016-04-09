package gem.com.support_client.screen.billing.graph;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import gem.com.support_client.R;

/**
 * Created by huylv on 07-Mar-16.
 */

public class CustomMarkerView extends MarkerView {

    private TextView mContentTv;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        mContentTv = (TextView) findViewById(R.id.chart_content_tv);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mContentTv.setText("" + e.getVal()); // set the entry-value as the display text
    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }

}