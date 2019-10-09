package signalmaster.com.smmobile.annotation;

import com.scichart.charting.visuals.annotations.TextAnnotation;

public class SmJeePyo {
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

    private TextAnnotation _jannotation = null;



    public TextAnnotation get_jannotation() {
        return _jannotation;
    }

    public void set_jannotation(TextAnnotation _jannotation) {
        this._jannotation = _jannotation;
    }
}
