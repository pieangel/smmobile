package signalmaster.com.smmobile.order;

import java.util.Collections;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class SmOrderManager {
    protected SortedMap<Integer, SmOrder> totalOrderMap = new TreeMap<Integer, SmOrder>(Collections.reverseOrder());

    public SortedMap<Integer, SmOrder> getTotalOrderMap() {
        return totalOrderMap;
    }

    public HashMap<Integer, SmOrder> getFilledOrderMap() {
        return filledOrderMap;
    }

    protected HashMap<Integer, SmOrder> acceptedOrderMap = new HashMap<>();
    protected HashMap<Integer, SmOrder> filledOrderMap = new HashMap<>();
    protected HashMap<Integer, SmOrder> settledOrderMap = new HashMap<>();
    public SmOrder createOrder(int orderNo) {
        SmOrder order = findOrder(orderNo);
        if (order != null)
            return order;
        order = new SmOrder();
        order.orderNo = orderNo;
        totalOrderMap.put(orderNo, order);
        return order;
    }
    public SmOrder findOrder(int orderNo) {
        if (totalOrderMap.containsKey(orderNo))
            return totalOrderMap.get(orderNo);
        return null;
    }

    public void addOrder(SmOrder order) {
        if (order == null)
            return;
        SmOrder foundOrder = findOrder(order.orderNo);
        if (foundOrder == null) {
            totalOrderMap.put(order.orderNo, order);
        }
    }

    public void onOrder(SmOrder order) {
        if (order == null)
            return;
        addOrder(order);
    }

    public void onOrderAccepted(SmOrder order) {
        if (order == null)
            return;
        // 접수된 주문 목록에 추가한다.
        acceptedOrderMap.put(order.orderNo, order);
    }

    public void onOrderFilled(SmOrder order) {
        if (order == null)
            return;
        // 접수된 주문이 있으면 제거한다.
        if (acceptedOrderMap.containsKey(order.orderNo)) {
            acceptedOrderMap.remove(order.orderNo);
        }

        // 청산된 주문일 경우 먼저 체결 목록에서 제거해 준다.
        if (order.orderState == SmOrderState.Settled) {
            if (filledOrderMap.containsKey(order.orderNo)) {
                filledOrderMap.remove(order.orderNo);
            }
        }

        if (order.orderState == SmOrderState.Filled) {
            // 체결된 주문에 추가한다.
            filledOrderMap.put(order.orderNo, order);
        } else {
            // 청산된 주문에 추가한다.
            settledOrderMap.put(order.orderNo, order);
        }
    }

    public void clearOrders() {
        totalOrderMap.clear();
    }

    public void addAcceptedOrder(SmOrder order) {
        acceptedOrderMap.put(order.orderNo, order);
    }

    public void addFilledOrder(SmOrder order) {
        filledOrderMap.put(order.orderNo, order);
    }
}
