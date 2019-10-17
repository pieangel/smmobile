package signalmaster.com.smmobile.position;

import java.util.HashMap;

public class SmAccountPositionManager {
    private HashMap<String, SmPosition> positionHashMap = new HashMap<>();
    private String accountNo;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public double getFee() {
        double sum = 0.0;
        for(SmPosition ele : positionHashMap.values()) {
            sum += ele.fee;
        }
        return sum;
    }
}
