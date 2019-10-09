package signalmaster.com.smmobile.order;

import java.util.HashMap;
import java.util.Map;

public enum SmOrderType {
    None(0),
    New(1),
    Modify(2),
    Cancel(3);

    private int value;
    private static Map map = new HashMap<>();

    private SmOrderType(int value) {
        this.value = value;
    }

    static {
        for (SmOrderType orderType : SmOrderType.values()) {
            map.put(orderType.value, orderType);
        }
    }

    public static SmOrderType valueOf(int orderType) {
        return (SmOrderType) map.get(orderType);
    }

    public int getValue() {
        return value;
    }
}
