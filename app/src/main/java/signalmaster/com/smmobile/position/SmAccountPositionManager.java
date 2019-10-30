package signalmaster.com.smmobile.position;

import java.util.ArrayList;
import java.util.HashMap;

public class SmAccountPositionManager {
    private HashMap<String, SmPosition> positionHashMap = new HashMap<>();
    private String accountNo;

    public String getAccountNo() {
        return accountNo;
    }
    // 계좌에 속한 포지션 목록을 반환한다.
    public ArrayList<SmPosition> getPositionList() {
        ArrayList<SmPosition> positions = new ArrayList<>();
        for(SmPosition ele : positionHashMap.values()) {
            if (ele.openQty != 0)
                positions.add(ele);
        }
        return positions;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getOpenPL() {
        double openPL = 0.0;
        for(SmPosition ele : positionHashMap.values()) {
            openPL += ele.openPL;
        }

        return openPL;
    }

    public void addPosition(SmPosition position) {
        if (position == null)
            return;

        // 계좌 포지션 관리자에 포지션을 추가한다.
        positionHashMap.put(position.symbolCode, position);
    }

    public SmPosition findPosition(String symbolCode) {
        if (positionHashMap.containsKey(symbolCode)) {
            return positionHashMap.get(symbolCode);
        }

        return null;
    }
}
