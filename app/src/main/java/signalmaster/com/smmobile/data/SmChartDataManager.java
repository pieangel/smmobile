package signalmaster.com.smmobile.data;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

import signalmaster.com.smmobile.chart.SmChart;
import signalmaster.com.smmobile.chart.SmChartDataRequest;
import signalmaster.com.smmobile.chart.SmChartType;
import signalmaster.com.smmobile.global.SmConst;
import signalmaster.com.smmobile.market.SmCategory;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.network.SmServiceManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;


public class SmChartDataManager implements Serializable {

    private static volatile SmChartDataManager sSoleInstance;
    private HashMap<String, SmChartData> _ChartDataMap = new HashMap<>();
    //private constructor.
    private SmChartDataManager(){
        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmChartDataManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmChartDataManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmChartDataManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmChartDataManager readResolve() {
        return getInstance();
    }

    public String makeChartDataKey(String sym_code, int chart_type, int cycle) {
        String dataKey = sym_code;
        dataKey += ":";
        dataKey += Integer.toString(chart_type);
        dataKey += ":";
        dataKey += Integer.toString(cycle);
        return dataKey;
    }

    HashMap<String, SmChartData> _chartDataMap = new HashMap<>();

    public SmChartData findChartData(String dataKey) {
        if (_chartDataMap.containsKey(dataKey)) {
            return _chartDataMap.get(dataKey);
        }
        return null;
    }

    public void removeChartData(String dataKey) {
        if (_chartDataMap.containsKey(dataKey)) {
            _chartDataMap.remove(dataKey);
        }
    }

    private void registerToSymbol(SmChartData chartData) {
        if (chartData == null)
            return;
        SmSymbolManager symbolManager = SmSymbolManager.getInstance();
        SmSymbol symbol = symbolManager.findSymbol(chartData.symbolCode);
        if (symbol == null)
            return;
        Log.d("TAG", "registerToSymbol:  >>>>>> " + symbol.code );
        symbol.addChartData(chartData.getDataKey(), chartData);
    }

    public SmChartData createChartData(String symbol_code, SmChartType chartType, int cycle) {
        String dataKey = makeChartDataKey(symbol_code, chartType.ordinal(), cycle);
        SmChartData chartData = findChartData(dataKey);
        if (chartData != null) {
            return chartData;
        }
        chartData = new SmChartData();
        chartData.symbolCode = symbol_code;
        chartData.chartType = chartType;
        chartData.cycle = cycle;
        registerToSymbol(chartData);
        _chartDataMap.put(dataKey, chartData);
        return chartData;
    }

    public void requestChartData(SmChartData chartData) {
        if (chartData == null)
            return;
        // 요청이 있으면 모든 데이터를 비운다.
        chartData.clear();
        // 차트 데이터를 다시 요청한다.
        SmChartDataRequest req = new SmChartDataRequest();
        req.symbolCode = chartData.symbolCode;
        req.chartType = chartData.chartType;
        req.cycle = chartData.cycle;
        req.count = SmConst.ChartDataSize;
        SmServiceManager svcMgr = SmServiceManager.getInstance();
        svcMgr.requestChartData(req);
    }
}
