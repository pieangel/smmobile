package signalmaster.com.smmobile.Util;

import java.util.HashMap;

public class SmArgumentMap {
    public HashMap<String, Object> argMap = new HashMap<>();
    public void AddArgument(String arg_key, Object argObj) {
        argMap.put(arg_key, argObj);
    }

    public  Object getVal(String arg_key) {
        if (argMap.containsKey(arg_key)) {
            return argMap.get(arg_key);
        }

        return  null;
    }
}
