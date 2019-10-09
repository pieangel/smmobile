package signalmaster.com.smmobile;

import com.scichart.charting.visuals.annotations.TextAnnotation;

public class SmOption {
    private double _x;
    private double _value;

    // 0 : relative , 1 : absolute
    private int coorMode = 0;
    private TextAnnotation _oannotation = null;

    private String _text = null;

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public TextAnnotation get_oannotation() {
        return _oannotation;
    }

    public void set_oannotation(TextAnnotation _oannotation) {
        this._oannotation = _oannotation;
    }

    public double get_x() {
        return _x;
    }

    public void set_x(double _x) {
        this._x = _x;
    }

    public double get_value() {
        return _value;
    }

    public void set_value(double _value) {
        this._value = _value;
    }

    public int getCoorMode() {
        return coorMode;
    }

    public void setCoorMode(int coorMode) {
        this.coorMode = coorMode;
    }
}
