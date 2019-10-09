package signalmaster.com.smmobile.position;

import java.util.HashMap;

public class SmSymbolPositionManager {
    private HashMap<String, SmPosition> positionHashMap = new HashMap<>();
    private String symbolCode;

    public String getSymbolCode() {
        return symbolCode;
    }

    public void setSymbolCode(String symbolCode) {
        this.symbolCode = symbolCode;
    }

}
