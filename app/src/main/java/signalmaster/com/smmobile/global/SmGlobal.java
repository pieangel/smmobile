package signalmaster.com.smmobile.global;

import java.io.Serializable;

public class SmGlobal implements Serializable {

    private static volatile SmGlobal sSoleInstance;

    private SmGlobal() {
        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmGlobal getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmGlobal.class) {
                if (sSoleInstance == null) sSoleInstance = new SmGlobal();
            }
        }

        return sSoleInstance;
    }

    public boolean isPageOpen;

    //Make singleton from serialize and deserialize operation.
    protected SmGlobal readResolve() {
        return getInstance();
    }

    public enum SmPositonType {
        None,
        Buy,
        Sell,
        ExitBuy,
        ExitSell
    }

    public enum SmTaskType
    {
        NoTask,
        AcceptedHistory, // 접수확인 내역
        FilledHistory, // 체결내역
        OutstandingHistory, // 잔고내역
        Outstanding, // 현재잔고
        CmeAcceptedHistory, // cme 접수확인 내역
        CmeFilledHistory, // cme 체결내역
        CmeOutstandingHistory, // cme 잔고내역
        CmeOutstanding, // cme 잔고
        CmeAsset, // cme 자산
        CmePureAsset, // cme 순자산
        Asset, // 자산
        Deposit, // 계좌 정보
        DailyProfitLoss, // 일일 손익
        FilledHistoryTable, // 체결 내역
        AccountProfitLoss, // 계좌별 손익
        SymbolCode, // 심볼 코드
        TradableCodeTable, // 거래가능 종목표
        ApiCustomerProfitLoss, // 고객 손익 현황
        ChartData, // 차트 데이터
        CurrentQuote, // 현재 시세
        DailyQuote, // 일별 시세
        TickQuote, // 틱 시세
        SecondQutoe, // 장외 시세
        SymbolMaster, // 심볼 마스터
        StockFutureSymbolMaster, // 증권선물 심볼 마스터
        IndustryMaster, // 산업별 마스터
        TaskComplete, // 작업 완료
        RecentMonthSymbolMaser, // 최근월 심볼 마스터
        OrderNew, // 신규주문
        OrderChange, // 정정 주문
        OrderCancel, // 취소 주문
        AccountFeeInfoStep1, // 계좌 정보
        AccountFeeInfoStep2 // 계좌 정보
    }


    public enum SmMainChartType {
        CandleStick,
        ClosePrice,
        TypicalPrice,
        MedianPrice,
        WeightedClose,
        OHLC

    }

    public enum SmSignalSource {
        // 알고리즘에서 오는 신호
        FromStrategy,
        // 인공지능에서 오는 신호
        FromAI
    }

    public enum SmSignalType {
        None,
        // 매수
        Buy,
        // 매도
        Sell,
        // 매수 청산
        ExitBuy,
        // 매도 청산
        ExitSell
    }

    public enum SmAppState {
        Init,
        SocketConnected,
        MarketDownloaded,
        MarketListMade,
        Loggedin,
        AccountListDownloaded,
        PositionListDownloaded,
        AcceptedListDownloaded,
        FilledListDownloaded,
        CategoryListDownloaded,
        SymbolListDownloaded,
        ReceivedMainchartData,
        ReceivedRecentSise,
        MainScreenLoaded,
        LoadingComplete
    }
}
