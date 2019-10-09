package signalmaster.com.smmobile.data;

import com.scichart.core.common.Action1;

import java.util.HashMap;

import signalmaster.com.smmobile.order.SmOrder;
import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.symbol.SmSymbol;

public class SmChartDataService implements  ISmChartDataService {
    private static volatile SmChartDataService sSoleInstance;
    //private constructor.
    private SmChartDataService(){
        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmChartDataService getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmChartDataService.class) {
                if (sSoleInstance == null) sSoleInstance = new SmChartDataService();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmChartDataService readResolve() {
        return getInstance();
    }


    // 실시간 데이터 업데이트 감시자 맵
    private HashMap<String, IDataObserver> newDataObserverHashMap = new HashMap<>();
    // 주기 데이터 업데이트 감시자 맵
    private HashMap<String, IDataObserver> updateDataObserverHashMap = new HashMap<>();
    // 차트 데이터 도착 감시자 맵
    private HashMap<String, IDataObserver> chartDataObserverHashMap = new HashMap<>();

    private HashMap<String, IUpdateSymbolObserver> updateSymbolObserverHashMap = new HashMap<>();

    private HashMap<String, IUpdatePositionObserver> updatePositionObserverHashMap = new HashMap<>();

    private HashMap<String, IOrderObserver> orderObserverHashMap = new HashMap<>();

    private HashMap<String, IDataObserver> dataObserverHashMap = new HashMap<>();
    private HashMap<String, ISiseObserver> siseObserverHashMap = new HashMap<>();
    private HashMap<String, IHogaObserver> hogaObserverHashMap = new HashMap<>();

    // 주기데이터 업데이트 인터페이스
    public interface INewDataObserver {
        void onNewData(PriceBar data);
    }

    // 업데이트 데이터 업데이트 인터페이스
    public interface IUpdateDataObserver {
        void onUpdateData(PriceBar data);
    }

    // 차트 데이터 인터페이스
    public interface IChartDataObserver {
        void onChartData(SmChartData data);
    }

    // 심볼 정보 업데이트 인터페이스
    public interface IUpdateSymbolObserver {
        void onUpdateSymbol(SmSymbol symbol);
    }

    // 시세 업데이트 인터페이스
    public interface ISiseObserver {
        void onUpdateSise(SmSymbol symbol);
    }

    // 호가 업데이트 인터페이스
    public interface IHogaObserver {
        void onUpdateHoga(SmSymbol symbol);
    }

    // 차트데이터 인터페이스
    public interface IDataObserver {
        void onNewData(SmChartDataItem dataItem);
        void onUpdateData(SmChartDataItem dataItem);
    }

    // 잔고 이벤트 도착 인터페이스
    public interface IUpdatePositionObserver {
        void onUpdatePosition(SmPosition position);
    }

    // 주문 도착 인터페이스
    public interface IOrderObserver {
        void onOrder(SmOrder order);
    }

    public void subscribeSise(final  Action1<SmSymbol> callback, final  String key) {
        if (siseObserverHashMap.containsKey(key)) {
            siseObserverHashMap.remove(key);
        }
        ISiseObserver siseObserver = new ISiseObserver() {
            @Override
            public void onUpdateSise(SmSymbol symbol) {
                callback.execute(symbol);
            }
        };

        siseObserverHashMap.put(key, siseObserver);
    }

    public  void unsubscribeSise(String key) {
        if (siseObserverHashMap.containsKey(key)) {
            siseObserverHashMap.remove(key);
        }
    }

    public void subscribeHoga(final  Action1<SmSymbol> callback, final  String key) {
        if (hogaObserverHashMap.containsKey(key)) {
            hogaObserverHashMap.remove(key);
        }
        IHogaObserver hogaObserver = new IHogaObserver() {
            @Override
            public void onUpdateHoga(SmSymbol symbol) {
                callback.execute(symbol);
            }
        };

        hogaObserverHashMap.put(key, hogaObserver);
    }

    public  void unsubscribeHoga(String key) {
        if (hogaObserverHashMap.containsKey(key)) {
            hogaObserverHashMap.remove(key);
        }
    }

    public  void subscribeOrder(final Action1<SmOrder> callback, final  String object_key) {
        if (orderObserverHashMap.containsKey(object_key)) {
            orderObserverHashMap.remove(object_key);
        }

        IOrderObserver orderObserver = new IOrderObserver() {
            @Override
            public void onOrder(SmOrder order) {
                callback.execute(order);
            }
        };

        orderObserverHashMap.put(object_key, orderObserver);
    }

    // 포지션 업데이트 등록 함수
    public  void subscribePosition(final Action1<SmPosition> callback, final String object_key) {
        // 이미 추가되어 있는 경우는 추가하지 않는다.
        if (updatePositionObserverHashMap.containsKey(object_key)) {
            updatePositionObserverHashMap.remove(object_key);
        }

        IUpdatePositionObserver positionObserver = new IUpdatePositionObserver() {
            @Override
            public void onUpdatePosition(SmPosition position) {
                callback.execute(position);
            }
        };
        updatePositionObserverHashMap.put(object_key, positionObserver);
    }

    public void unsubscibePosition(String object_key) {
        if (updatePositionObserverHashMap.containsKey(object_key)) {
            updatePositionObserverHashMap.remove(object_key);
        }
    }

    // 차트 데이터 업데이트 될때 호출되는 콜백함수를 등록한다. 키는 심볼 코드이다.
    public  void subscribeUpdateData(final Action1<SmChartDataItem> callback, String id) {
        // 이미 추가되어 있는 경우는 추가하지 않는다.
        if (updateDataObserverHashMap.containsKey(id)) {
            updateDataObserverHashMap.remove(id);
        }
        // 업데이트 데이터 인터페이스를 생성한다.
        IDataObserver updateDataObserver = new IDataObserver() {
            @Override
            public void onUpdateData(SmChartDataItem symbolCode) {
                callback.execute(symbolCode);
            }

            @Override
            public void onNewData(SmChartDataItem symbolCode) {
                callback.execute(symbolCode);
            }
        };
        // 업데이트 데이터 맵에 넣어 준다.
        updateDataObserverHashMap.put(id, updateDataObserver);
    }

    public  void subscribeNewData(final Action1<SmChartDataItem> callback, String id) {
        // 이미 추가되어 있는 경우는 추가하지 않는다.
        if (newDataObserverHashMap.containsKey(id)) {
            newDataObserverHashMap.remove(id);
        }
        // 업데이트 데이터 인터페이스를 생성한다.
        IDataObserver newDataObserver = new IDataObserver() {
            @Override
            public void onUpdateData(SmChartDataItem dataItem) {
                callback.execute(dataItem);
            }

            @Override
            public void onNewData(SmChartDataItem dataItem) {
                callback.execute(dataItem);
            }
        };
        // 업데이트 데이터 맵에 넣어 준다.
        newDataObserverHashMap.put(id, newDataObserver);
    }
    // 차트 데이터 수신시 호출되는 콜백함수를 해제한다. 키는 차트데이터 식별 키이다.
    public  void unsubscribeChartData(String dataKey) {
        if (chartDataObserverHashMap.containsKey(dataKey)) {
            chartDataObserverHashMap.remove(dataKey);
        }
    }
    // 차트 데이터 업데이트 될때 호출되는 콜백함수를 해제한다. 키는 심볼 코드이다.
    public  void unsubscribePriceUpdate(String id) {
        if (updateDataObserverHashMap.containsKey(id)) {
            updateDataObserverHashMap.remove(id);
        }
    }

    public  void unsubscribePriceNew(String id) {
        if (newDataObserverHashMap.containsKey(id)) {
            newDataObserverHashMap.remove(id);
        }
    }

    synchronized public void onUpdateSise(SmSymbol symbol) {
        // 등록된 모든 콜백을 호출한다.
        for (ISiseObserver observer : siseObserverHashMap.values()) {
            observer.onUpdateSise(symbol);
        }
    }

    synchronized public void onUpdateHoga(SmSymbol symbol) {
        // 등록된 모든 콜백을 호출한다.
        for (IHogaObserver observer : hogaObserverHashMap.values()) {
            observer.onUpdateHoga(symbol);
        }
    }

    synchronized public void onNewData(SmChartDataItem dataItem) {
        // 등록된 모든 콜백을 호출한다.
        for (IDataObserver observer : newDataObserverHashMap.values()) {
            observer.onNewData(dataItem);
        }
    }

    synchronized public void onUpdateData(SmChartDataItem dataItem) {
        // 등록된 모든 콜백을 호출한다.
        for (IDataObserver observer : updateDataObserverHashMap.values()) {
            observer.onUpdateData(dataItem);
        }
    }

    synchronized public void onUpdatePosition(SmPosition position) {
        for(IUpdatePositionObserver observer : updatePositionObserverHashMap.values()) {
            observer.onUpdatePosition(position);
        }
    }

    synchronized public void onOrder(SmOrder order) {
        for(IOrderObserver orderObserver : orderObserverHashMap.values()) {
            orderObserver.onOrder(order);
        }
    }
}
