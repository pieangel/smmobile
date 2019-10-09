package signalmaster.com.smmobile.order;

import java.util.HashMap;
import java.util.Map;

public enum SmOrderState {
    None(0), // 상태없음
    Ledger(1), // 원장 접수
    ConfirmNew(2), // 새로운 주문 확인
    ConfirmModify(3), // 정정주문 확인
    ConfirmCancel(4), // 취소주문 확인
    Accepted(5), // 접수확인
    Filled(6), // 체결확인
    RejectNew(7), // 새로운 주문 거부
    RejectModify(8), // 정정주문 거부
    RejectCancel(9), // 취소주문 거부
    Settled(10); // 주문 해소 - 상쇄된 주문

    private int value;
    private static Map map = new HashMap<>();

    private SmOrderState(int value) {
        this.value = value;
    }

    static {
        for (SmOrderState orderState : SmOrderState.values()) {
            map.put(orderState.value, orderState);
        }
    }

    public static SmOrderState valueOf(int orderState) {
        return (SmOrderState) map.get(orderState);
    }

    public int getValue() {
        return value;
    }
}
