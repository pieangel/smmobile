package signalmaster.com.smmobile;

import java.util.ArrayList;

public class RecyclerViewList {
    private ArrayList<String> _mainTitleList = new ArrayList<>();
    private ArrayList<String> _subTitleList = new ArrayList<>();

    public ArrayList<String> get_mainTitleList() {
        getTitleList();
        return _mainTitleList;
    }

    public ArrayList<String> get_subTitleList() {
        getSubTitleList();
        return _subTitleList;
    }

    private void getTitleList(){
        _mainTitleList.add("금일 변동");
        _mainTitleList.add("이전 종가");
        _mainTitleList.add("시가");
        _mainTitleList.add("거래량");
    }
    private void getSubTitleList(){
        _subTitleList.add("0 - 0");
        _subTitleList.add("0");
        _subTitleList.add("0");
        _subTitleList.add("N/A");
        /*_subTitleList.add("20,924.19 - 21,153.65");
        _subTitleList.add("20,972.71");
        _subTitleList.add("21,089.50");
        _subTitleList.add("N/A");*/
    }

    private ArrayList<String> _siseRecyclerViewList = new ArrayList<>();

    public ArrayList<String> get_siseRecyclerViewList() {
        getSiseRecyclerViewList();
        return _siseRecyclerViewList;
    }

    private void getSiseRecyclerViewList(){
        _siseRecyclerViewList.add("개요");
        _siseRecyclerViewList.add("구성 목록");
        _siseRecyclerViewList.add("기술적");
        _siseRecyclerViewList.add("과거 데이터");
        _siseRecyclerViewList.add("의견");
        _siseRecyclerViewList.add("차트");
    }

    private ArrayList<String> _lineSelectList = new ArrayList<>();

    public ArrayList<String> get_lineSelectList() {
        getLineSelectList();
        return _lineSelectList;
    }

    private void getLineSelectList(){
        _lineSelectList.add("수평선");
        _lineSelectList.add("수직선");
        //_lineSelectList.add("광선");
    }

    private ArrayList<String> orderTitleList = new ArrayList<>();

    public ArrayList<String> getOrderTitleList() {
        getOrderList();
        return orderTitleList;
    }

    private void getOrderList(){
        orderTitleList.add("구분");
        orderTitleList.add("잔고");
        orderTitleList.add("평균가");
        orderTitleList.add("현재가");
        orderTitleList.add("평가손익");
    }

    private ArrayList<String> orderTitleValueList = new ArrayList<>();

    public ArrayList<String> getOrderTitleValueList() {
        getOrderValueList();
        return orderTitleValueList;
    }

    private void getOrderValueList(){
        orderTitleValueList.add("");
        orderTitleValueList.add("");
        orderTitleValueList.add("");
        orderTitleValueList.add("");
        orderTitleValueList.add("");
        orderTitleValueList.add("");
    }

    private ArrayList<String> defaultOptionList = new ArrayList<>();
    public ArrayList<String> getDefaultOptionList() {
        defaultOption();
        return defaultOptionList;
    }
    private void defaultOption(){
        defaultOptionList.add("Option1");
        defaultOptionList.add("Option2");
        defaultOptionList.add("Option3");
        defaultOptionList.add("Option4");
    }

    private ArrayList<String> favoriteOptionList = new ArrayList<>();
    public ArrayList<String> getFavoriteOptionList() {
        favorOption();
        return favoriteOptionList;
    }
    private void favorOption(){
        favoriteOptionList.add("추가");
        favoriteOptionList.add("정렬");
        favoriteOptionList.add("삭제");
    }

    private ArrayList<String> mockOptionList = new ArrayList<>();
    public ArrayList<String> getMockOptionList() {
        mockOption();
        return mockOptionList;
    }
    private void mockOption(){
        mockOptionList.add("하단바 감추기");
        mockOptionList.add("계좌");
        mockOptionList.add("거래기록");
        mockOptionList.add("리포트");
        mockOptionList.add("포지션 목록");
    }

    private ArrayList<String> currentOptionList = new ArrayList<>();

    private void currentOption(){
        currentOptionList.add("현재가 옵션1");
        currentOptionList.add("현재가 옵션2");
        currentOptionList.add("현재가 옵션3");
    }


    private ArrayList<String> conclusionList = new ArrayList<>();

    public ArrayList<String> getConclusionList() {
        account();
        return conclusionList;
    }

    private void account(){
        conclusionList.add("종목");
        conclusionList.add("구분");
        conclusionList.add("체결가격");
        conclusionList.add("체결수량");
        conclusionList.add("체결시각");
    }

    private ArrayList<String> outStandingList = new ArrayList<>();

    public ArrayList<String> getOutStandingList() {
        record();
        return outStandingList;
    }

    private void record(){
        outStandingList.add("종목");
        outStandingList.add("구분");
        outStandingList.add("주문가격");
        outStandingList.add("주문수량");
        outStandingList.add("주문시각");
    }

    private ArrayList<String> profitList = new ArrayList<>();

    public ArrayList<String> getProfitList() {
        profit();
        return profitList;
    }

    private void profit(){
        profitList.add("종목");
        profitList.add("총손익");
        profitList.add("수수료");
        profitList.add("순손익");
    }

    private ArrayList<String> balanceList = new ArrayList<>();

    public ArrayList<String> getBalanceList() {
        balance();
        return balanceList;
    }

    private void balance(){
        balanceList.add("종목");
        balanceList.add("구분");
        balanceList.add("평균단가");
        balanceList.add("수량");
        balanceList.add("순손익");
    }

    private ArrayList<String> nonList = new ArrayList<>();

    public ArrayList<String> getNonList(){
        non();
        return  nonList;
    }

    private void non(){
        nonList.add("-");
        nonList.add("-");
        nonList.add("-");
        nonList.add("-");
        nonList.add("-");

    }

    private ArrayList<String> orderTitle = new ArrayList<>();

    public ArrayList<String> getOrderTitle(){
        order();
        return orderTitle;
    }

    private void order(){
        orderTitle.add("건수");
        orderTitle.add("매도");
        orderTitle.add("15:45:00");
        orderTitle.add("매수");
        orderTitle.add("건수");
    }

    private ArrayList<String> resultList = new ArrayList<>();

    public ArrayList<String> getResultList() {
        result();
        return resultList;
    }

    private void result(){
        resultList.add("922");
        resultList.add("5,601");
        resultList.add("229");
        resultList.add("5,372");
        resultList.add("808");
    }

    private ArrayList<String> blankList = new ArrayList<>();

    public ArrayList<String> getBlankList() {
        blank();
        return blankList;
    }

    public void blank(){
        blankList.add(" ");
        blankList.add(" ");
        blankList.add("직전");
        blankList.add(" ");
        blankList.add(" ");
    }

    //최근거래
    private ArrayList<String> recentTitle = new ArrayList<>();

    public ArrayList<String> getRecentTitle() {
        title();
        return recentTitle;
    }

    public void title(){
        recentTitle.add("오후 2:47");
        recentTitle.add("오후 2:46");
        recentTitle.add("오후 2:46");
        recentTitle.add("오후 2:44");
    }

    private ArrayList<String> recentContent = new ArrayList<>();

    public ArrayList<String> getRecentContent() {
        content();
        return recentContent;
    }

    public void content(){
        recentContent.add("Ethereum");
        recentContent.add("Ethereum");
        recentContent.add("Ethereum");
        recentContent.add("Ethereum");
    }

    private ArrayList<String> recentRevenue = new ArrayList<>();

    public ArrayList<String> getRecentRevenue() {
        revenue();
        return recentRevenue;
    }

    public void revenue(){
        recentRevenue.add("+$1.8");
        recentRevenue.add("+$1.8");
        recentRevenue.add("+$1.8");
        recentRevenue.add("+$1.79");
    }

    private ArrayList<String> recentInvest = new ArrayList<>();

    public ArrayList<String> getRecentInvest() {
        invest();
        return recentInvest;
    }

    public void invest(){
        recentInvest.add("$1");
        recentInvest.add("$10");
        recentInvest.add("$4");
        recentInvest.add("$5");
    }

    private ArrayList<String> typeSpinList = new ArrayList<>();

    public ArrayList<String> getTypeSpinList() {
        type();
        return typeSpinList;
    }

    public void type(){
        /*typeSpinList.add("모두");
        typeSpinList.add("매수");
        typeSpinList.add("매각");*/
        typeSpinList.add("1");
        typeSpinList.add("2");
        typeSpinList.add("3");
        typeSpinList.add("4");
        typeSpinList.add("5");

    }


    private ArrayList<String> _indexSelectList = new ArrayList<>();

    public ArrayList<String> get_indexSelectList() {
        getIndexList();
        return _indexSelectList;
    }

    private void getIndexList() {
        _indexSelectList.add("MA");
        _indexSelectList.add("스토캐스틱");
        _indexSelectList.add("MACD");
        _indexSelectList.add("RSI");
        _indexSelectList.add("CCI");
        _indexSelectList.add("볼린져밴드");
        _indexSelectList.add("파러볼릭 SAR");
        _indexSelectList.add("MOMENTUM");
        _indexSelectList.add("ATR");
    }

    private ArrayList<String> portfolioList = new ArrayList<>();

    public ArrayList<String> getPortfolioList() {
        getPortfolio();
        return portfolioList;
    }

    private void getPortfolio(){
        portfolioList.add("P1");
        portfolioList.add("P2");
        portfolioList.add("P3");
    }

    private ArrayList<String> myPortfolioList = new ArrayList<>();

    public ArrayList<String> getMyPortfolioList() {
        return myPortfolioList;
    }

    private ArrayList<String> rightPortList = new ArrayList<>();

    public ArrayList<String> getRightPortList() {
        portMenu();
        return rightPortList;
    }

    private void portMenu(){
        rightPortList.add("추가하기");
    }
}
