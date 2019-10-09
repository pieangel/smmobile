package signalmaster.com.smmobile.market;

import java.util.ArrayList;

import signalmaster.com.smmobile.symbol.SmSymbol;

public class SmCategory {

    //상품군 - 금리, 통화 등등
    public String marketType;
    //거래소 코드
    public String exchangeCode;
    //시장 구분 코드
    public String marketCode;
    // 상품명 - 영문
    public String marketName;
    // 상품명 - 한글
    public String marketNameKr;

    //add
    // 거래소 이름
    public String exchangeName;
    //거래소 인덱스코드
    public String indexCode;
    //품목 구분 코드
    public String pmGubun;

    private ArrayList<SmSymbol> _symbolList = new ArrayList<>();

    public ArrayList<SmSymbol> get_symbolList() {
        return _symbolList;
    }

    public SmSymbol addSymbol(SmSymbol sym) {
        _symbolList.add(sym);
        return sym;
    }

    public SmSymbol getRecentSymbol() {
        if (_symbolList.size() == 0)
            return null;
        else
            return _symbolList.get(0);
    }

    public SmCategory() {
    }

    public int getIndexByName(String yearMonth) {
        int i = 0;
        for(SmSymbol symbol : _symbolList) {
            String fullName = symbol.seriesNmKr;
            if (fullName.contains(yearMonth) == true) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
