package signalmaster.com.smmobile.data;

import com.scichart.core.common.Action1;

import signalmaster.com.smmobile.order.SmOrder;
import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.symbol.SmSymbol;

public interface ISmChartDataService {

    void subscribeSise(final  Action1<SmSymbol> callback, final  String key);
    void subscribeHoga(final  Action1<SmSymbol> callback, final  String key);
    void subscribeOrder(final Action1<SmOrder> callback, final  String object_key);
    void subscribePosition(final Action1<SmPosition> callback, final String object_key);
    void subscribeUpdateData(final Action1<SmChartDataItem> callback, String id);
    void subscribeNewData(final Action1<SmChartDataItem> callback, String id);
}
