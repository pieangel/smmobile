package signalmaster.com.smmobile.data;

import java.util.Date;

import signalmaster.com.smmobile.chart.SmChartType;

public class SmChartDataItem {
    public String getDataKey() {
        String key = symbolCode;
        key += ":";
        key += chartType.getValue();
        key += ":";
        key += cycle;
        return key;
    }
    public boolean periodic = false;
    public boolean isLast = false;
    public  String symbolCode;
    public  SmChartType chartType;
    public  Date date = null;
    public  double open;
    public  double high;
    public  double low;
    public  double close;
    public  long volume;
    public  int cycle;
}
