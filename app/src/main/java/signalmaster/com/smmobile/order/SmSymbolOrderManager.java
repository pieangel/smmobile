package signalmaster.com.smmobile.order;

public class SmSymbolOrderManager extends SmOrderManager {
    private String symbolCode;

    @Override
    public void onOrder(SmOrder order) {
        super.onOrder(order);
    }

    @Override
    public void onOrderAccepted(SmOrder order) {
        super.onOrderAccepted(order);
    }

    @Override
    public void onOrderFilled(SmOrder order) {
        super.onOrderFilled(order);
    }

    public String getSymbolCode() {
        return symbolCode;
    }

    public void setSymbolCode(String symbolCode) {
        this.symbolCode = symbolCode;
    }
}
