package signalmaster.com.smmobile.Util;

import java.io.Serializable;
import java.util.HashMap;

public class SmArgManager implements Serializable {

    private static volatile SmArgManager sSoleInstance;

    //private constructor.
    private SmArgManager() {

        //Prevent form the reflection api.
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmArgManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmArgManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmArgManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmArgManager readResolve() {
        return getInstance();
    }


    private  HashMap<String, SmArgument> _argMap = new HashMap<>();
    private  HashMap<String, SmArgumentMap> _argMapCollection = new HashMap<>();
    public void registerToMap(String argmap_key, String arg_key, Object val) {
        if (_argMapCollection.containsKey(argmap_key)) {
            SmArgumentMap argMap = _argMapCollection.get(argmap_key);
            argMap.AddArgument(arg_key, val);
        }
        else {
            SmArgumentMap argMap = new SmArgumentMap();
            argMap.AddArgument(arg_key, val);
            _argMapCollection.put(argmap_key, argMap);
        }
    }

    public void unregisterFromMap(String argmap_key) {
        if (_argMapCollection.containsKey(argmap_key)) {
            _argMapCollection.remove(argmap_key);
        }
    }

    public Object getVal(String argmap_key, String arg_key) {
        if (_argMapCollection.containsKey(argmap_key)) {
            SmArgumentMap argMap = _argMapCollection.get(argmap_key);
            return argMap.getVal(arg_key);
        } else
            return null;
    }
    public void register(String key, SmArgument val) {
        _argMap.put(key, val);
    }

    public void unregister(String key) {
        if (_argMap.containsKey(key)) {
            _argMap.remove(key);
        }
    }

    public SmArgument getValue(String key) {
        if (_argMap.containsKey(key)) {
            return _argMap.get(key);
        }

        return null;
    }
}
