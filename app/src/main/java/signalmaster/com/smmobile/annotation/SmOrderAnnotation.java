package signalmaster.com.smmobile.annotation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;

import com.scichart.charting.visuals.annotations.HorizontalLineAnnotation;
import com.scichart.core.framework.SmartPropertyInteger;
import com.scichart.drawing.utility.CohenSutherlandLineClipper;
import com.scichart.drawing.utility.ColorUtil;

public class SmOrderAnnotation extends HorizontalLineAnnotation {
    protected final SmartPropertyInteger horizontalGravityProperty = new SmartPropertyInteger(new SmartPropertyInteger.IPropertyChangeListener() {
        @Override
        public void onPropertyChanged(int oldValue, int newValue) {

        }
    }, Gravity.FILL_HORIZONTAL);

    // size of left and right annotation labels
    // used to prevent intersection of line and annotation labels
    private int leftWidth, rightWidth;
    private final Paint orderPaint = new Paint();
    private final CohenSutherlandLineClipper b = new CohenSutherlandLineClipper(new RectF());
    private final Path orderPath = new Path();
    public SmOrderAnnotation(Context context) {
        super(context);
    }

    public SmOrderAnnotation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmOrderAnnotation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SmOrderAnnotation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void internalDraw(Canvas canvas, PointF lineStart, PointF lineEnd) {

        if(lineStart.x < lineEnd.x) {
            lineStart.x += leftWidth;
            lineEnd.x -= rightWidth;
        } else {
            lineEnd.x += leftWidth;
            lineStart.x -= rightWidth;
        }

        orderPaint.setColor(ColorUtil.Orange);


        //this.setLayoutParams(new ViewGroup.LayoutParams(getWidth(), getHeight()));
        float garo = annotationCoordinates.pt1.x ;
        float sero = annotationCoordinates.pt1.y ;

            /*path.moveTo(0, 0);
            path.lineTo(sero/2, 0);
            path.lineTo(sero/2,garo);
            path.lineTo(sero*2,garo);
            path.lineTo(sero*2,0);
            path.lineTo(sero/2,0);
            path.lineTo(sero/2,garo/2);
            path.moveTo(sero*2,garo/2);
            path.lineTo(sero*10,garo/2);*/



        super.internalDraw(canvas, lineStart, lineEnd);

    }

}
