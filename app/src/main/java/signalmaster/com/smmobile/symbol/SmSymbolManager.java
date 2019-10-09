package signalmaster.com.smmobile.symbol;

import java.io.Serializable;
import java.util.HashMap;

public class SmSymbolManager implements Serializable {

    private static volatile SmSymbolManager sSoleInstance;

    //private constructor.
    private SmSymbolManager(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmSymbolManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmSymbolManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmSymbolManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmSymbolManager readResolve() {
        return getInstance();
    }

    private HashMap<String, SmSymbol> _symbolMap = new HashMap<>();

    public SmSymbol findSymbol(String symcode){
        if (_symbolMap.containsKey(symcode))
            return _symbolMap.get(symcode);
         else
             return null;
    }

    public SmSymbol addSymbol(SmSymbol sym) {
        if (sym == null)
            return null;
        _symbolMap.put(sym.code, sym);
        return sym;
    }

    public int getSymbolCount() {
        return _symbolMap.size();
    }
}