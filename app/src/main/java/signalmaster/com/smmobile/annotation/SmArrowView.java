package signalmaster.com.smmobile.annotation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

//어노테이션 화살표 그리기
public  class SmArrowView extends View {

    private int _longWidth = 40;
    private int _shortWidth = 20;
    private int _header = 20;
    private int _tail = 20;
    private int _width = 40;
    private int _height = 40;

    public int get_longWidth() {
        return _longWidth;
    }

    public void set_longWidth(int _longWidth) {
        this._longWidth = _longWidth;
    }



    public int get_shortWidth() {
        return _shortWidth;
    }

    public void set_shortWidth(int _shortWidth) {
        this._shortWidth = _shortWidth;
    }



    public int get_header() {
        return _header;
    }

    public void set_header(int _header) {
        this._header = _header;
    }



    public int get_tail() {
        return _tail;
    }

    public void set_tail(int _tail) {
        this._tail = _tail;
    }



    public int get_fillColor() {
        return _fillColor;
    }

    public void set_fillColor(int _fillColor) {
        this._fillColor = _fillColor;
    }

    private int _fillColor = Color.parseColor("#FFFF0000");

    public int get_strokeColor() {
        return _strokeColor;
    }

    public void set_strokeColor(int _strokeColor) {
        this._strokeColor = _strokeColor;
    }

    private int _strokeColor = Color.parseColor("#FF990000");

    public int get_arrowType() {
        return _arrowType;
    }

    public void set_arrowType(int _arrowType) {
        this._arrowType = _arrowType;
    }

    // 0 : down, 1 : up, 2 : left, 3 : right
    private int _arrowType = 1;

    public int get_width() {
        return _width;
    }

    public void set_width(int _width) {
        this._width = _width;
    }



    public int get_height() {
        return _height;
    }

    public void set_height(int _height) {
        this._height = _height;
    }



    private final Path path = new Path();
    private final Paint paintFill = new Paint();
    private final Paint paintStroke = new Paint();

    public SmArrowView(Context context) {
        super(context);
        init();
    }

    public SmArrowView(Context context, int arraowType, int fillColor, int strokeColor) {
        super(context);
        _arrowType = arraowType;
        _fillColor = fillColor;
        _strokeColor = strokeColor;
        init();
    }

    public SmArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setColor(_fillColor);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(4);
        paintStroke.setColor(_strokeColor);
        int longWidth = _longWidth;
        int shortWidth = _shortWidth;
        int header = _header;
        int tail =  _tail;
        int center = longWidth / 2;

        if (_arrowType == 0) { // down
            path.moveTo(center + shortWidth / 2, 0);
            path.lineTo(center + shortWidth / 2, tail);
            path.lineTo(center + longWidth / 2, tail);
            path.lineTo(center + 0, header + tail);
            path.lineTo(center + (-longWidth / 2), tail);
            path.lineTo(center + (-shortWidth / 2), tail);
            path.lineTo(center + (-shortWidth / 2), 0);
            path.lineTo(center + shortWidth / 2, 0);
        } else if (_arrowType == 1) { // up
            path.moveTo(center, 0);
            path.lineTo(longWidth, _header);
            path.lineTo(center + shortWidth / 2, _header);
            path.lineTo(center + shortWidth / 2, header + tail);
            path.lineTo(center + (-shortWidth / 2), header + tail);
            path.lineTo(center + (-shortWidth / 2), _header);
            path.lineTo(center + (-longWidth / 2), _header);
            path.lineTo(center, 0);
        }
        setMinimumHeight(_height);
        setMinimumWidth(_width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paintFill);
        canvas.drawPath(path, paintStroke);
    }

}