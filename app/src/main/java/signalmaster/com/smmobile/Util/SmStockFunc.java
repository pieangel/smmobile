package signalmaster.com.smmobile.Util;

import java.util.LinkedList;

import signalmaster.com.smmobile.chart.SmStatistics;
import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;

public class SmStockFunc {
    public SmStockFunc() {}

    int annual = 247;

    public double CalcHistoricVolatility(double[] close, int index, int loopback) {
        double hs = 0.0f;
        // 이동평균 기간보다 작으면 계산 못하므로 그냥 반환
        if (index + 1 < loopback || index >= close.length)
            return hs;
        double[] log_array = new double[loopback];
        for(int i = 1 + index, j = 0; i <= loopback + index; i++, j++) {
            log_array[j] = Math.log(close[i] / close[i - 1]);
        }
        double std = SmStatistics.stddev(log_array);
        hs = std * Math.sqrt(annual) * 100;
        return hs;
    }

    public double CalcHistoricVolatility(LinkedList<Double> close, int index, int loopback) {

        double hs = 0.0f;
        // 이동평균 기간보다 작으면 계산 못하므로 그냥 반환
        if (index + 1 < loopback || (index + loopback) >= close.size())
            return hs;
        try {
            double[] log_array = new double[loopback];
            for(int i = 1 + index, j = 0; i < loopback + index; i++, j++) {
                log_array[j] = Math.log(close.get(i) / close.get(i - 1));
            }
            double std = SmStatistics.stddev(log_array);
            hs = std * Math.sqrt(annual) * 100;
        }
        catch (Exception e) {
            String message = e.getMessage();
        }
        return hs;
    }

    public double [] GetHistoricVolatility(double [] close, int loopback) {
        double[] hs = new double[close.length - loopback];
        for(int i = loopback; i < close.length; ++i) {
            hs[i] = CalcHistoricVolatility(close, i, loopback);
        }
        return hs;
    }

    public double [] GetHistoricVolatility(LinkedList<Double> close, int loopback) {
        double[] hs = new double[close.size()];
        try {
            for(int i = loopback; i < close.size(); ++i) {
                hs[i] = CalcHistoricVolatility(close, i, loopback);
            }
        }
        catch (Exception e) {
            String message = e.getMessage();
        }
        return hs;
    }

    public float getCorrelation(SmChartData data1, SmChartData data2) {
        float corr = 0.0f;
        SmSymbol symbol1 = SmSymbolManager.getInstance().findSymbol(data1.symbolCode);
        SmSymbol symbol2 = SmSymbolManager.getInstance().findSymbol(data2.symbolCode);
        int dec1 = symbol1.decimal;
        int dec2 = symbol2.decimal;

        LinkedList<Double> close1 = data1.getClose();
        LinkedList<Double> close2 = data2.getClose();

        for(int i = 0; i < close1.size(); ++i) {
            close1.set(i, close1.get(i) * Math.max(dec1, dec2));
            close2.set(i, close2.get(i) * Math.max(dec1, dec2));
        }

        corr = SmStatistics.correlationCoefficient(close1, close2, close1.size());

        return corr;
    }
}
