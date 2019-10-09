package signalmaster.com.smmobile.order;

import signalmaster.com.smmobile.position.SmPositionType;

public class SmOrder {
    // 주문요청 아이디
    public int requestId;
    // 주문 계좌 번호
    public String accountNo;
    // 주문 타입 : 신규, 정정, 취소
    public SmOrderType orderType;
    // 포지션 타입 : 매도, 매수
    public SmPositionType positionType;
    // 가격 타입 : 시장가, 지정가
    public SmPriceType priceType;
    // 종목 코드
    public String symbolCode;
    // 주문 가격
    public int orderPrice;
    // 주문 번호
    public int orderNo;
    // 주문 수량
    public int orderAmount;
    // 원 주문 번호
    public int oriOrderNo;
    // 체결 날짜
    public String filledDate;
    // 체결 시간
    public String filledTime;
    // 주문 날짜
    public String orderDate;
    // 주문 시간
    public String orderTime;
    // 체결 수량
    public int filledQty;
    // 체결 가격
    public int filledPrice;
    // 주문 상태
    public SmOrderState orderState;
    // 체결 조건
    public SmFilledCondition filledCondition;
    // 심볼 소수점 자리수
    public int symbolDecimal;
    // 주문 접수 안된 수량
    public int remainQty;
    // 전략이름
    public String strategyName;
    // 펀드 이름
    public String fundName;
    // 시스템 이름
    public String systemName;
}
