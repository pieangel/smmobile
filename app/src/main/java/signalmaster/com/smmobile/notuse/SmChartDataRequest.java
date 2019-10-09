package signalmaster.com.smmobile.notuse;

import signalmaster.com.smmobile.global.SmGlobal;

public class SmChartDataRequest {
    SmGlobal.SmMainChartType mainChartType = SmGlobal.SmMainChartType.CandleStick;
    String msgid;
    String productCode;
    //SmChartType chartType;
    int cycle;
    int count;
    String currency;
    String exchange;
    int next;
    int reqKey;
    // 갭보정 0 : 사용안함, 1 : 사용함
    int seq;
    String dataKey;
    //SmChartRequestType type;
    int timerDuration;
    boolean requestAll = true;
    int refCount;
    String keyvalue;
    boolean domestic;
    int colorIndex = 0;
    String initial;
    int decimal = 2;
}
