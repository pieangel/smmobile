package signalmaster.com.smmobile.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import signalmaster.com.smmobile.chart.SmChartType;

// 차트 데이터 클래스
// 이 차트 데이터를 사용하고 있는 차트 프래그먼트 목록을 가지고 있다.
// 차트 데이터가 업데이트 되면 이 데이터를 사용하고 있는 모든 데이터를 업데이트 한다.
public  class SmChartData {
    public String symbolCode;
    public SmChartType chartType;
    public int cycle;
    public int count;

    public String getDataKey() {
        String key = symbolCode;
        key += ":";
        key += chartType.getValue();
        key += ":";
        key += cycle;
        return key;
    }
    public SmChartData() {
    }

    private boolean init = false;

    public SortedMap<String, SmChartDataItem> getDataMap() {
        return dataMap;
    }

    private final SortedMap<String, SmChartDataItem> dataMap = new TreeMap<>();

    public SmChartDataItem add(String date_time, double o, double h, double l, double c, long v) {
        try {
            // 공백이 있으면 없앤다.
            date_time = date_time.trim();
            String format = "yyyyMMddHHmmss";
            if (chartType != SmChartType.MIN) {
                format = "yyyyMMdd";
                // 자릿수 초과할 때 초과하는 문자열은 없애 준다.
                if (date_time.length() > 8) {
                    date_time = date_time.substring(0, 8);
                }
            }
            SimpleDateFormat fmt = new SimpleDateFormat(format);
            Date date = fmt.parse(date_time);
            if (dataMap.containsKey(date_time)) {
                SmChartDataItem oldData = dataMap.get(date_time);
                oldData.date = date;
                oldData.open = o;
                oldData.high = h;
                oldData.low = l;
                oldData.close = c;
                oldData.volume = v;
                return oldData;
            }

            SmChartDataItem newData = new SmChartDataItem();
            newData.date = date;
            newData.open = o;
            newData.high = h;
            newData.low = l;
            newData.close = c;
            newData.volume = v;

            dataMap.put(date_time, newData);

            return newData;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public SmChartDataItem getLastData() {
        if (dataMap.size() == 0)
            return null;
        return  dataMap.get(dataMap.lastKey());
    }

    public LinkedList<Double> getOpen() {
        LinkedList<Double> valueList = new LinkedList<>();
        for(SmChartDataItem item : dataMap.values()) {
            valueList.add(item.open);
        }
        return valueList;
    }

    public LinkedList<Double> getHigh() {
        LinkedList<Double> valueList = new LinkedList<>();
        for(SmChartDataItem item : dataMap.values()) {
            valueList.add(item.high);
        }
        return valueList;
    }

    public LinkedList<Double> getLow() {
        LinkedList<Double> valueList = new LinkedList<>();
        for(SmChartDataItem item : dataMap.values()) {
            valueList.add(item.low);
        }
        return valueList;
    }

    public LinkedList<Double> getClose() {
        LinkedList<Double> valueList = new LinkedList<>();
        for(SmChartDataItem item : dataMap.values()) {
            valueList.add(item.close);
        }
        return valueList;
    }

    public LinkedList<Long> getVolume() {
        LinkedList<Long> valueList = new LinkedList<>();
        for(SmChartDataItem item : dataMap.values()) {
            valueList.add(item.volume);
        }
        return valueList;
    }

    public LinkedList<Date> getDate() {
        LinkedList<Date> valueList = new LinkedList<>();
        for(SmChartDataItem item : dataMap.values()) {
            valueList.add(item.date);
        }
        return valueList;
    }

    public boolean isIncluded(String date_time) {
        if (dataMap.size() == 0)
            return false;

        if (dataMap.containsKey(date_time)) {
            return true;
        }

        return false;
    }

    public boolean isUptodate() {
        if (dataMap.size() == 0)
            return false;

        String current_date = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        String lastKey = dataMap.lastKey();
        SmChartDataItem lastData = dataMap.get(lastKey);
        Date lastDate = lastData.date;
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String last_date = dateFormat.format(lastDate);
        if (current_date.compareTo(last_date) == 0)
            return true;
        return false;
    }

    public  void updateLastValue(double c) {
        if (dataMap.size() == 0 || c == 0.0)
            return ;
        String lastKey = dataMap.lastKey();
        SmChartDataItem lastData = dataMap.get(lastKey);

        double high = lastData.high;
        double low = lastData.low;
        if (c > high) {
            lastData.high = c;
        }
        if (c < low) {
            lastData.low = low;
        }
        lastData.close = c;
    }

    public SmChartDataItem updateData(String date_time, double o, double h, double l, double c, long v) {
        try {
            if (date_time.length() < 14)
                return null;


            if (dataMap.containsKey(date_time)) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = fmt.parse(date_time);

                SmChartDataItem oldData = dataMap.get(date_time);
                oldData.date = date;
                oldData.open = o;
                oldData.high = h;
                oldData.low = l;
                oldData.close = c;
                oldData.volume = v;
                return oldData;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getCount() {
        return dataMap.size();
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void clear() {
        dataMap.clear();
    }
}
