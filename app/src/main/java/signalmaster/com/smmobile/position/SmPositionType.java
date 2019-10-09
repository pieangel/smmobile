package signalmaster.com.smmobile.position;

import java.util.HashMap;
import java.util.Map;

public enum SmPositionType {
    None(0),
    Buy(1),
    Sell(2),
    ExitBuy(3),
    ExitSell(4);

    private int value;
    private static Map map = new HashMap<>();

    private SmPositionType(int value) {
        this.value = value;
    }

    static {
        for (SmPositionType positionType : SmPositionType.values()) {
            map.put(positionType.value, positionType);
        }
    }

    public static SmPositionType valueOf(int positionType) {
        return (SmPositionType) map.get(positionType);
    }

    public int getValue() {
        return value;
    }
}
