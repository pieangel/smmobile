package signalmaster.com.smmobile.symbol;

import java.util.HashMap;

import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.market.SmCategory;

public class SmSymbol {
    private  HashMap<String, SmChartData> chartDataMap = new HashMap<>();
    public void addChartData(String dataKey, SmChartData chart_data) {
        chartDataMap.put(dataKey, chart_data);
    }

    public void removeChartData(String dataKey) {
        if (chartDataMap.containsKey(dataKey)) {
            chartDataMap.remove(dataKey);
        }
    }

    public String getFormat() {
        String format = "";
        try {
            format = "%.";
            format += Integer.toString(decimal);
            format += "f";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    public HashMap<String, SmChartData> getChartDataMap() {
        return chartDataMap;
    }

    public SmQuote quote = new SmQuote();
    public SmHoga hoga = new SmHoga();
    //종목코드
    public String code;
    //거래소
    public String exchangeCode;
    //품목 인덱스 코드
    public String indexCode;
    //품목 코드
    public String marketCode;
    // 거래소 번호
    public String exchangeNumber;
    //소수점 번호
    public String pdesz;
    //소수점정보2
    public String rdesz;
    //계약 크기
    public double contractSize;
    //틱 사이즈
    public double tickSize;
    //틱 값
    public double tickValue;
    //거래승수
    public int seungsu;
    //진법
    public String dispDigit;
    //Full 종목명
    public  String seriesNm;
    //Full 종목명 한글
    public  String seriesNmKr;
    //최근 월물, 주요종목표시
    public String nearSeq;
    public String marketType;
    //거래 가능여부
    public String statTp;
    //신규거래 제한일
    public String lockDate;
    //최초거래일
    public String tradeFrDate;
    //최종거래일
    public String tradeToDate;
    //만기일, 최종결제일
    public String exprDate;
    //잔존일수
    public String remnCnt;
    //호가방식
    public String hogaMthd;
    //상하한폭비율
    public String minmaxRt;
    //기준가
    public String baseP;
    //상한가
    public String maxP;
    //하한가
    public String minP;
    //신규주문 증거금
    public String trstMgn;
    //유지증거금
    public String mntMgn;
    //결제통화코드
    public String crcCd;
    //Base CRC CD
    public String baseCrcCd;
    //COUNTER CRC CD
    public String counterCrcCd;
    //PIP COST
    public String pipCost;
    //매수이자
    public String buyInt;
    //매도이자
    public String sellInt;
    //LOUND LOTS
    public String roundLots;
    //진법자리수
    public String scaleChiper;
    //소수점 정보(KTB 기준)
    public String decimalChiper;
    //전일 거래량량
    public String jnilVolume;

    public int atm;
    public int nearMonth;
    public String lastDate;

    public int decimal = 2;
    public String[] getSplitName(String compName) {
        String[] result = new String[2];
        result[0] = compName.substring(0, compName.length() - 8);
        result[1] = compName.substring(compName.length() - 8, compName.length());
        return result;
    }

    private SmCategory _category = null;

    public SmCategory get_category() {
        return _category;
    }
    public void set_category(SmCategory _category) {
        this._category = _category;
    }

}
