package signalmaster.com.smmobile.chart;

public enum  SmChartType {
    NONE(0),
    TICK(1),
    MIN(2),
    DAY(3),
    WEEK(4),
    MON(5),
    YEAR(6);

    private int _value;

    SmChartType(int Value) {
        this._value = Value;
    }

    public int getValue() {
        return _value;
    }

    public static SmChartType fromInt(int i) {
        for (SmChartType b : SmChartType .values()) {
            if (b.getValue() == i) { return b; }
        }
        return null;
    }
}
