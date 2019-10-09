package signalmaster.com.smmobile.order;

import java.util.HashMap;
import java.util.Map;

public enum SmFilledCondition {
    None(0),
    Fas(1), //
    Fok(2), //
    Fak(3);

    private int value;
    private static Map map = new HashMap<>();

    private SmFilledCondition(int value) {
        this.value = value;
    }

    static {
        for (SmFilledCondition filledCondition : SmFilledCondition.values()) {
            map.put(filledCondition.value, filledCondition);
        }
    }

    public static SmFilledCondition valueOf(int filledCondition) {
        return (SmFilledCondition) map.get(filledCondition);
    }

    public int getValue() {
        return value;
    }
}
