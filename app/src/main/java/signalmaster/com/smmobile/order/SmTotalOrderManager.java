package signalmaster.com.smmobile.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.symbol.SmSymbol;

public class SmTotalOrderManager extends  SmOrderManager implements Serializable {
    private static volatile SmTotalOrderManager sSoleInstance;
    //private constructor.
    private SmTotalOrderManager(){
        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmTotalOrderManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmTotalOrderManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmTotalOrderManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmTotalOrderManager readResolve() {
        return getInstance();
    }

    private HashMap<String, SmAccountOrderManager> accountOrderManagerHashMap = new HashMap<>();

    public SmAccountOrderManager findAddOrderManager(String acountNo) {
        if (accountOrderManagerHashMap.containsKey(acountNo)) {
            return accountOrderManagerHashMap.get(acountNo);
        }

        SmAccountOrderManager accountOrderManager = new SmAccountOrderManager();
        accountOrderManager.setAccountNo(acountNo);
        accountOrderManagerHashMap.put(acountNo, accountOrderManager);
        return accountOrderManager;
    }

    // 모든 주문은 여기로 들어온다.
    @Override
    public void onOrder(SmOrder order) {
        if (order == null)
            return;
        SmAccountOrderManager accountOrderManager = findAddOrderManager(order.accountNo);
        // 계좌를 찾아 주문을 추가해 준다.
        accountOrderManager.onOrder(order);
        super.onOrder(order);
    }

    @Override
    public void onOrderAccepted(SmOrder order) {
        if (order == null)
            return;
        SmAccountOrderManager accountOrderManager = findAddOrderManager(order.accountNo);
        // 계좌를 찾아 주문을 추가해 준다.
        accountOrderManager.onOrderAccepted(order);
        super.onOrderAccepted(order);
    }

    @Override
    public void onOrderFilled(SmOrder order) {
        if (order == null)
            return;
        SmAccountOrderManager accountOrderManager = findAddOrderManager(order.accountNo);
        // 계좌를 찾아 주문을 추가해 준다.
        accountOrderManager.onOrderFilled(order);
        super.onOrderFilled(order);
    }

    @Override
    public void addAcceptedOrder(SmOrder order) {
        SmAccountOrderManager accountOrderManager = findAddOrderManager(order.accountNo);
        // 계좌를 찾아 주문을 추가해 준다.
        accountOrderManager.addAcceptedOrder(order);
        super.addAcceptedOrder(order);
    }

    @Override
    public void addFilledOrder(SmOrder order) {
        SmAccountOrderManager accountOrderManager = findAddOrderManager(order.accountNo);
        // 계좌를 찾아 주문을 추가해 준다.
        accountOrderManager.addFilledOrder(order);
        super.addFilledOrder(order);
    }

    public ArrayList<SmOrder> getFilledOrderList(String account_no, String symbol_code) {
        ArrayList<SmOrder> orderList = new ArrayList<>();
        for (Map.Entry mapElement : filledOrderMap.entrySet()) {
            SmOrder order = (SmOrder)mapElement.getValue();
            if (order.accountNo.compareTo(account_no) == 0 &&
                    order.symbolCode.compareTo(symbol_code) == 0) {
                orderList.add(order);
            }
        }
        return orderList;
    }
    public void initSymbol() {
        SmMarketManager marketManager = SmMarketManager.getInstance();
        final SmSymbol symbol = marketManager.getDefaultSymbol();
        setOrderSymbol(symbol);
    }

    private SmSymbol orderSymbol = null;

    public SmSymbol getOrderSymbol() {
        return orderSymbol;
    }

    public void setOrderSymbol(SmSymbol symbol) {
        this.orderSymbol = symbol;
    }
}
