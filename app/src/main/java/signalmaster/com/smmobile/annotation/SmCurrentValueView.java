package signalmaster.com.smmobile.annotation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

import signalmaster.com.smmobile.position.SmPositionType;

public class SmCurrentValueView extends View {
    private final Path path = new Path();
    private int width = 220;
    private int height = 80;
    private int arrowHeight = 30;
    private int bodyHeight = 90;
    private String valueText = "123456.7";
    private SmPositionType positionType = SmPositionType.None;
    private final Paint paintText = new Paint();
    private final Paint paintFill = new Paint();
    private final Paint paintStroke = new Paint();
    private int fillColor = Color.parseColor("#FF25669D");;
    private int strokeColor = Color.parseColor("#FF259CF6");;
    private int sellFillColor = Color.parseColor("#882D364A");
    private int sellStrokeColor = Color.parseColor("#AA464F63");
    private int buyFillColor = Color.parseColor("#882D364A");
    private int buyStrokeColor = Color.parseColor("#AA464F63");
    private int offset = 2;
    public SmCurrentValueView(Context context) {
        super(context);
        init();
    }

    public SmCurrentValueView(Context context, SmPositionType positionType, String value) {
        super(context);
        this.positionType = positionType;
        this.setValueText(value);
        init();
    }
    private void init() {
        paintFill.setStyle(Paint.Style.FILL);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(3);
        paintText.setStyle(Paint.Style.FILL);

        paintText.setAntiAlias(true);
        paintText.setTextSize(48);
        paintText.setTextAlign(Paint.Align.CENTER);
        if (this.positionType == SmPositionType.Sell) {
            paintText.setColor(Color.BLUE);
            paintFill.setColor(sellFillColor);
            paintStroke.setColor(sellStrokeColor);
            Typeface typeface = Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL);
            // Set the paint font
            paintText.setTypeface(typeface);
        }
        else if (this.positionType == SmPositionType.Buy) {
            paintText.setColor(Color.RED);
            paintFill.setColor(buyFillColor);
            paintStroke.setColor(buyStrokeColor);
            Typeface typeface = Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL);
            // Set the paint font
            paintText.setTypeface(typeface);
        }
        else {
            Typeface typeface = Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD);
            // Set the paint font
            paintText.setTypeface(typeface);

            paintText.setColor(Color.WHITE);
            paintFill.setColor(fillColor);
            paintStroke.setColor(strokeColor);
            width += 60;
        }


        setMinimumHeight(height);
        setMinimumWidth(width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Initialize a new RectF instance
        RectF rectF = new RectF(
                offset, // left
                offset, // top
                canvas.getWidth() - offset, // right
                canvas.getHeight() - offset // bottom
        );


        // Define the corners radius of rounded rectangle
        int cornersRadius = 40;

        canvas.drawRoundRect(
                rectF, // rect
                cornersRadius, // rx
                cornersRadius, // ry
                paintFill // Paint
        );
        // Finally, draw the rounded corners rectangle object on the canvas
        canvas.drawRoundRect(
                rectF, // rect
                cornersRadius, // rx
                cornersRadius, // ry
                paintStroke // Paint
        );

        Rect rectangle = new Rect();
        paintText.getTextBounds(
                getValueText(), // text
                0, // start
                getValueText().length(), // end
                rectangle // bounds
        );
        canvas.drawText(
                getValueText(), // Text to draw
                canvas.getWidth()/2, // x
                this.positionType == SmPositionType.Buy ? height / 2 + Math.abs(rectangle.height())/2 : height / 2 + Math.abs(rectangle.height())/2, // y
                paintText // Paint
        );
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }
}
