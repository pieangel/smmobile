package signalmaster.com.smmobile.position;

import signalmaster.com.smmobile.symbol.SmSymbol;

public class SmPosition {
    public String createdDate;
    public String createTime;
    // 종목코드
    public String symbolCode;
    // 펀드 이름
    public String fundName;
    // 계좌 번호
    public String accountNo;
    // 포지션 타입 : 매도 / 매수
    public SmPositionType positionType = SmPositionType.None;
    // 잔고
    public int openQty;
    // 수수료
    public double feeCount;
    // 매매손익
    public double tradePL;
    // 평가손익
    public double openPL;
    // 현재가
    public double curPrice;
    // 평균가
    public double avgPrice;

    public String getPositionKey() {
        return (accountNo + ":" + symbolCode);
    }

    public static String makePositionKey(String account_no, String symbol_code) {
        return (account_no + ":" + symbol_code);
    }

    public void updateOpenPL() {
        openPL = SmTotalPositionManager.getOpenProfit(this);
    }
}
