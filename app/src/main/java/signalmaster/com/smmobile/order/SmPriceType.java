package signalmaster.com.smmobile.order;

import java.util.HashMap;
import java.util.Map;

public enum SmPriceType {
    None(0),
    Price(1), // 지정가
    Market(2), // 시장가
    Condition(3), // 조건
    BestPrice(4);  // 최상위 유리가

    private int value;
    private static Map map = new HashMap<>();

    private SmPriceType(int value) {
        this.value = value;
    }

    static {
        for (SmPriceType priceType : SmPriceType.values()) {
            map.put(priceType.value, priceType);
        }
    }

    public static SmPriceType valueOf(int priceType) {
        return (SmPriceType) map.get(priceType);
    }

    public int getValue() {
        return value;
    }
}
