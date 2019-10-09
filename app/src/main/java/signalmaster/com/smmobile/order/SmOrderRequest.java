package signalmaster.com.smmobile.order;

import signalmaster.com.smmobile.position.SmPositionType;

public class SmOrderRequest {
    public String accountNo;
    public String password;
    public String symbolCode;
    public SmPositionType positionType;
    public SmPriceType priceType;
    public SmFilledCondition filledCondition;
    public int orderPrice;
    public int orderAmount;
    public int oriOrderNo = -1;
    public int requestId;
    public SmOrderType orderType;
    public String fundName;
    public String strategyName;
    public String symtemName;
}
