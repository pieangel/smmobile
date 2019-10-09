package signalmaster.com.smmobile.order;

import java.util.HashMap;

public class SmAccountOrderManager extends SmOrderManager {
    private String accountNo;
    private HashMap<String, SmSymbolOrderManager> symbolOrderManagerHashMap = new HashMap<>();
    // 심볼 주문관리자를 찾아봐서 없으면 생성하고, 있으면 바로 주문관리자를 반환한다.
    public SmSymbolOrderManager findAddOrderManager(String symbolCode) {
        if (symbolOrderManagerHashMap.containsKey(symbolCode)) {
            return symbolOrderManagerHashMap.get(symbolCode);
        }
        SmSymbolOrderManager symbolOrderManager = new SmSymbolOrderManager();
        symbolOrderManager.setSymbolCode(symbolCode);
        symbolOrderManagerHashMap.put(symbolCode, symbolOrderManager);
        return symbolOrderManager;
    }

    @Override
    public void onOrder(SmOrder order) {
        if (order == null)
            return;
        SmSymbolOrderManager symbolOrderManager = findAddOrderManager(order.symbolCode);
        // 심볼 주문 관리자를 호출해 준다.
        symbolOrderManager.onOrder(order);
        // 계좌 자체에 대한 주문을 관리한다.
        super.onOrder(order);
    }

    @Override
    public void onOrderAccepted(SmOrder order) {
        if (order == null)
            return;
        SmSymbolOrderManager symbolOrderManager = findAddOrderManager(order.symbolCode);
        symbolOrderManager.onOrderAccepted(order);
        super.onOrderAccepted(order);
    }

    @Override
    public void onOrderFilled(SmOrder order) {
        if (order == null)
            return;
        SmSymbolOrderManager symbolOrderManager = findAddOrderManager(order.symbolCode);
        symbolOrderManager.onOrderFilled(order);
        super.onOrderFilled(order);
    }

    @Override
    public void addAcceptedOrder(SmOrder order) {
        SmSymbolOrderManager symbolOrderManager = findAddOrderManager(order.symbolCode);
        // 계좌를 찾아 주문을 추가해 준다.
        symbolOrderManager.addAcceptedOrder(order);
        super.addAcceptedOrder(order);
    }

    @Override
    public void addFilledOrder(SmOrder order) {
        SmSymbolOrderManager symbolOrderManager = findAddOrderManager(order.symbolCode);
        // 계좌를 찾아 주문을 추가해 준다.
        symbolOrderManager.addFilledOrder(order);
        super.addFilledOrder(order);
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
