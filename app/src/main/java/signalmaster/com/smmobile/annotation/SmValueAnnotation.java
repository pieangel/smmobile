package signalmaster.com.smmobile.annotation;

import android.content.Context;
import android.util.AttributeSet;

import com.scichart.charting.visuals.annotations.CustomAnnotation;

public class SmValueAnnotation extends CustomAnnotation {
    public SmValueAnnotation(Context context) {
        super(context);
    }

    public SmValueAnnotation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmValueAnnotation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SmValueAnnotation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private SmCurrentValueView valueView ;

    public void setValue(String value) {
        valueView.setValueText(value);
    }

    public SmCurrentValueView getValueView() {
        return valueView;
    }

    public void setValueView(SmCurrentValueView valueView) {
        this.valueView = valueView;
    }
}
