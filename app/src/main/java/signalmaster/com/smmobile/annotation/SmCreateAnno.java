package signalmaster.com.smmobile.annotation;

import com.scichart.charting.visuals.annotations.TextAnnotation;

public class SmCreateAnno {
    private double _x;
    private double _y;

    // 0 : relative , 1 : absolute
    private int coorMode = 0;

    public int getCoorMode() {
        return coorMode;
    }

    public void setCoorMode(int coorMode) {
        this.coorMode = coorMode;
    }

    public double get_x() {
        return _x;
    }

    public void set_x(double _x) {
        this._x = _x;
    }

    public double get_y() {
        return _y;
    }

    public void set_y(double _y) {
        this._y = _y;
    }

    private TextAnnotation _cannotation = null;



    public TextAnnotation get_cannotation() {
        return _cannotation;
    }

    public void set_cannotation(TextAnnotation _cannotation) {
        this._cannotation = _cannotation;
    }
}
