package signalmaster.com.smmobile.chart;

import android.content.Context;
import android.util.AttributeSet;

import com.scichart.charting.visuals.annotations.CustomAnnotation;

import signalmaster.com.smmobile.annotation.SmCurrentValueView;

public class SmProgressAnnotation extends CustomAnnotation {
    public SmProgressAnnotation(Context context) {
        super(context);
    }

    public SmProgressAnnotation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmProgressAnnotation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SmProgressAnnotation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private SmProgressBar valueView ;

    public SmProgressBar getValueView() {
        return valueView;
    }

    public void setValueView(SmProgressBar valueView) {
        this.valueView = valueView;
    }
}
