package signalmaster.com.smmobile.annotation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import signalmaster.com.smmobile.position.SmPositionType;

public class SmOrderView  extends View {
    private final Path path = new Path();
    private final Paint paintFill = new Paint();
    private final Paint paintStroke = new Paint();
    private final Paint paintText = new Paint();
    private int fillColor = Color.parseColor("#FFFF0000");
    private int strokeColor = Color.parseColor("#FF990000");
    private int sellFillColor = Color.parseColor("#FF46962B");
    private int sellStrokeColor = Color.parseColor("#FF46962B");
    private int buyFillColor = Color.parseColor("#FFB14333");
    private int buyStrokeColor = Color.parseColor("#FFB14333");
    private int width = 220;
    private int height = 120;
    private int arrowHeight = 30;
    private int bodyHeight = 90;
    private String valueText = "123456.7";
    private SmPositionType positionType = SmPositionType.None;

    public SmOrderView(Context context) {
        super(context);
        init();
    }

    public SmOrderView(Context context, SmPositionType positionType, int fillColor, int strokeColor) {
        super(context);
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.positionType = positionType;
        init();
    }

    public SmOrderView(Context context, SmPositionType positionType) {
        super(context);
        this.positionType = positionType;
        init();
    }

    public SmOrderView(Context context, SmPositionType positionType, String value) {
        super(context);
        this.positionType = positionType;
        this.valueText = value;
        init();
    }

    public SmOrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintFill.setStyle(Paint.Style.FILL);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(3);
        paintText.setStyle(Paint.Style.FILL);

        int center = width / 2;
        if (this.positionType == SmPositionType.Sell) {
            paintText.setColor(Color.BLACK);
            paintFill.setColor(sellFillColor);
            paintStroke.setColor(sellStrokeColor);
            path.moveTo(center + width / 2, 0);
            path.lineTo(center + width / 2, bodyHeight);
            path.lineTo(center + width / 8, bodyHeight);
            path.lineTo(center, bodyHeight + arrowHeight);
            path.lineTo(center - width / 8, bodyHeight);
            path.lineTo(center - width / 2, bodyHeight);
            path.lineTo(center - width / 2, 0);
            path.lineTo(center + width / 2, 0);
        }
        else if (this.positionType == SmPositionType.Buy) {
            paintText.setColor(Color.WHITE);
            paintFill.setColor(buyFillColor);
            paintStroke.setColor(buyStrokeColor);
            path.moveTo(center, 0);
            path.lineTo(center + width / 8, arrowHeight);
            path.lineTo(center + width / 2, arrowHeight);
            path.lineTo(center + width / 2, arrowHeight + bodyHeight);
            path.lineTo(center - width / 2, arrowHeight + bodyHeight);
            path.lineTo(center - width / 2, arrowHeight);
            path.lineTo(center - width / 8, arrowHeight);
            path.lineTo(center, 0);
        }
        paintText.setAntiAlias(true);
        paintText.setTextSize(48);
        paintText.setTextAlign(Paint.Align.CENTER);
        Typeface typeface = Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD);
        // Set the paint font
        paintText.setTypeface(typeface);
        setMinimumHeight(height);
        setMinimumWidth(width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paintFill);
        canvas.drawPath(path, paintStroke);
        Rect rectangle = new Rect();
        paintText.getTextBounds(
                valueText, // text
                0, // start
                valueText.length(), // end
                rectangle // bounds
        );
        canvas.drawText(
                valueText, // Text to draw
                canvas.getWidth()/2, // x
                this.positionType == SmPositionType.Buy ? arrowHeight + bodyHeight / 2 + Math.abs(rectangle.height())/2 : bodyHeight / 2 + Math.abs(rectangle.height())/2, // y
                paintText // Paint
        );
    }

}
