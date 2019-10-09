package signalmaster.com.smmobile.chart;

import java.io.Serializable;
import java.util.HashMap;

public class SmChartManager implements Serializable {

    private static volatile SmChartManager sSoleInstance;

    //private constructor.
    private SmChartManager(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmChartManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmChartManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmChartManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmChartManager readResolve() {
        return getInstance();
    }



    private   HashMap<String, SmChart> _ChartMap = new HashMap<>();

    public HashMap<String, SmChart> get_ChartMap() {
        return _ChartMap;
    }

    public void set_ChartMap(HashMap<String, SmChart> _ChartMap) {
        this._ChartMap = _ChartMap;
    }

    public SmChart getChart(String key){
        if(_ChartMap.containsKey(key)){
            return _ChartMap.get(key);
        }
        return null;
    }

    public  void AddChart(String key, SmChart obj){
        _ChartMap.put(key, obj);
    }

    SmChart FindChart(String key) {
        if (_ChartMap.containsKey(key)) {
            return _ChartMap.get(key);
        }

        return null;
    }

}
