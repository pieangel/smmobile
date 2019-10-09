package signalmaster.com.smmobile.annotation;

//어노테이션 라인 그리기
public  class SmCustomAnnotation {


    /*//세로값만
    private  int vertical;





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





    private final Path path = new Path();
    private final Paint paintFill = new Paint();
    private final Paint paintStroke = new Paint();

    public SmCustomAnnotation(Context context) {
        super(context);
        init();
    }

    public SmCustomAnnotation(Context context, int arraowType, int fillColor, int strokeColor) {
        super(context);
        _arrowType = arraowType;
        _fillColor = fillColor;
        _strokeColor = strokeColor;
        init();
    }

    public SmCustomAnnotation(Context context, AttributeSet attrs) {
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
    }*/

}
