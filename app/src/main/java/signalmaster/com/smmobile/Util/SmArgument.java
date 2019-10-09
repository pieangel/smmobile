package signalmaster.com.smmobile.Util;

import java.util.HashMap;

public class SmArgument {
    public HashMap<String, Object> ArgMap = new HashMap<>();
    public void add(String key, Object val) {
        ArgMap.put(key, val);
    }
    public Object get(String key) {
        if (ArgMap.containsKey(key)) {
            return ArgMap.get(key);
        }

        return null;
    }
}
