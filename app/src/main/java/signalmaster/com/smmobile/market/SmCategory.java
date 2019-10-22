package signalmaster.com.smmobile.market;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import signalmaster.com.smmobile.symbol.SmSymbol;

import static java.lang.Character.isDigit;

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
        addToYearMonth(sym.code, sym);
        return sym;
    }

    public SmSymbol getNextSymbol() {
        int i = 0;
        for(Map.Entry<String, SmProductYearMonth> entry : yearMonthSortedMap.entrySet()) {
            String key = entry.getKey();
            SmProductYearMonth value = entry.getValue();
            if (i == 1) {
                ArrayList<SmSymbol> symbolArrayList = value.symbolList;
                if (symbolArrayList.size() > 0)
                    return symbolArrayList.get(0);
                else
                    return null;
            }
            i++;
        }

        return null;
    }

    public SmSymbol getRecentSymbol() {
        if (yearMonthSortedMap.size() == 0)
            return null;
        else {
           String firstKey = yearMonthSortedMap.firstKey();
           SmProductYearMonth ym = yearMonthSortedMap.get(firstKey);
           ArrayList<SmSymbol> symbolArrayList = ym.symbolList;
           if (symbolArrayList.size() == 0)
               return null;
           else {
               SmSymbol symbol = symbolArrayList.get(0);
               if (marketCode.compareTo("175") == 0) {
                   DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                   Date date = new Date();
                   String current_date = dateFormat.format(date);
                   if (current_date.compareTo(symbol.lastDate) >= 0) {
                       return getNextSymbol();
                   } else
                       return symbol;
               }
               return symbol;
           }
        }
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

    SortedMap<String, SmProductYearMonth> yearMonthSortedMap = new TreeMap<>();
    void addToYearMonth(String symbol_code, SmSymbol symbol) {
        if (symbol == null)
            return;

        if (isDigit(symbol_code.charAt(2))) { // 국내 상풍
            String product_code = symbol_code.substring(0, 3);
            String year_month = symbol_code.substring(3, 5);
            SmProductYearMonth ym = null;
            if (yearMonthSortedMap.containsKey(year_month)) {
                ym = yearMonthSortedMap.get(year_month);
            }
            else {
                ym = new SmProductYearMonth();
                ym.productCode = product_code;
                ym.yearMonthCode = year_month;
                yearMonthSortedMap.put(year_month, ym);
            }
            ym.symbolList.add(symbol);
        }
	else { // 해외 상품
            String product_code = symbol_code.substring(0, 2);
            String year = symbol_code.substring(symbol_code.length() - 2, symbol_code.length());
            String month = symbol_code.substring(symbol_code.length() - 3, symbol_code.length() - 2);
            String year_month = year + month;
            SmProductYearMonth ym = null;
            if (yearMonthSortedMap.containsKey(year_month)) {
                ym = yearMonthSortedMap.get(year_month);
            }
            else {
                ym = new SmProductYearMonth();
                ym.productCode = product_code;
                ym.yearMonthCode = year_month;
                yearMonthSortedMap.put(year_month, ym);
            }
            ym.symbolList.add(symbol);
        }
    }
}
