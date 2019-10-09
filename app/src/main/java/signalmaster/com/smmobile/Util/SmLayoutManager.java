package signalmaster.com.smmobile.Util;

import java.io.Serializable;
import java.util.HashMap;

import signalmaster.com.smmobile.global.SmGlobal;
import signalmaster.com.smmobile.market.SmMarketInfo;
import signalmaster.com.smmobile.sise.SubListAdapter;
import signalmaster.com.smmobile.symbol.SmSymbol;

public class SmLayoutManager implements Serializable {

    private static volatile SmLayoutManager mMoleInstance;

    private HashMap<String, SmMarketInfo> _categoryMarketMap = new HashMap<>();
    private HashMap<String, Integer> _marketIndexMap = new HashMap<>();

    private SmLayoutManager() {
        //Prevent form the reflection api.
        if (mMoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmLayoutManager getInstance() {
        if (mMoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmLayoutManager.class) {
                if (mMoleInstance == null) mMoleInstance = new SmLayoutManager();
            }
        }
        return mMoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmLayoutManager readResolve() {
        return getInstance();
    }


    public HashMap<String, Object> _objectMap = new HashMap<>();
    public void register(String key, Object val) {
        _objectMap.put(key, val);
    }

    public void unregister(String key) {
        if (_objectMap.containsKey(key)) {
            _objectMap.remove(key);
        }
    }

    public Object getValue(String key) {
        if (_objectMap.containsKey(key)) {
            return _objectMap.get(key);
        }

        return null;
    }

    public void refreshSubListAdapter(SmSymbol symbol) {
        if (symbol == null)
            return;
        SubListAdapter adapter = (SubListAdapter)_objectMap.get("R.id.SubListAdapter");
        if (adapter != null) {
            adapter.refreshItem(symbol);
        }
    }

    private SmGlobal.SmAppState appState = SmGlobal.SmAppState.Init;

    public SmGlobal.SmAppState getAppState() {
        return appState;
    }

    public void setAppState(SmGlobal.SmAppState appState) {
        this.appState = appState;
    }
}
