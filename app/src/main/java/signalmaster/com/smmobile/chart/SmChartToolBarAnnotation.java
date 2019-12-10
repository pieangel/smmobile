package signalmaster.com.smmobile.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.scichart.charting.visuals.annotations.CustomAnnotation;

import signalmaster.com.smmobile.position.SmPositionType;

public class SmChartToolBarAnnotation extends CustomAnnotation {
    public SmChartToolBarAnnotation(Context context) {
        super(context);
    }

    public SmChartToolBarAnnotation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmChartToolBarAnnotation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SmChartToolBarAnnotation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}


