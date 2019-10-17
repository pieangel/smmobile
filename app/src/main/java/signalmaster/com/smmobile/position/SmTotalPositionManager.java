package signalmaster.com.smmobile.position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.data.SmChartDataService;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;

public class SmTotalPositionManager implements Serializable {
    private static volatile SmTotalPositionManager sSoleInstance;
    //private constructor.
    private SmTotalPositionManager(){
        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }
    public static SmTotalPositionManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmTotalPositionManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmTotalPositionManager();
            }
        }
        return sSoleInstance;
    }
    //Make singleton from serialize and deserialize operation.
    protected SmTotalPositionManager readResolve() {
        return getInstance();
    }

    public HashMap<String, SmPosition> getPositionHashMap() {
        return positionHashMap;
    }

    public ArrayList<SmPosition> getPositionList() {
        ArrayList<SmPosition> positions = new ArrayList<>();
        for(SmPosition ele : positionHashMap.values()) {
            if (ele.openQty != 0)
                positions.add(ele);
        }
        return positions;
    }

    public int DefaultOrderNum = 1;

    private HashMap<String, SmPosition> positionHashMap = new HashMap<>();

    private HashMap<String, SmAccountPositionManager> accountPositionManagerHashMap = new HashMap<>();

    public SmPosition findAddPosition(String account_no, String symbol_code) {
        SmPosition position = findPosition(account_no, symbol_code);
        if (position != null)
            return position;
        position = makePosition(account_no, symbol_code);
        addPostion(position);
        return position;
    }

    public ArrayList<SmPosition> getPositionList(String symbolCode) {
        ArrayList<SmPosition> positionArrayList = new ArrayList<>();
        SmAccountManager accountManager = SmAccountManager.getInstance();
        ArrayList<String> accountNoList = accountManager.getAccountNoList();
        for(String accountNo : accountNoList) {
            SmAccountPositionManager accountPositionManager = findAddAccountPositionManager(accountNo);
            if (accountManager != null) {
                SmPosition position = accountPositionManager.findPosition(symbolCode);
                if (position != null)
                    positionArrayList.add(position);
            }
        }

        return positionArrayList;
    }

    private SmPosition makePosition(String account_no, String symbol_code) {
        SmPosition position = new SmPosition();
        position.accountNo = account_no;
        position.symbolCode = symbol_code;
        return position;
    }

    public SmPosition findPosition(String account_no, String symbol_code) {
        String key = SmPosition.makePositionKey(account_no, symbol_code);
        if (positionHashMap.containsKey(key)) {
            return positionHashMap.get(key);
        }
        return null;
    }

    // 포지션을 추가한다.
    private void addPostion(SmPosition position) {
        if (position == null)
            return;
        SmAccountPositionManager accountPositionManager = findAddAccountPositionManager(position.accountNo);
        // 계좌에 포지션을 추가한다.
        accountPositionManager.addPosition(position);
        String key = SmPosition.makePositionKey(position.accountNo, position.symbolCode);
        positionHashMap.put(key, position);
    }

    public SmAccountPositionManager findAddAccountPositionManager(String account_no) {
        SmAccountPositionManager accountPositionManager = findAccountPositonManager(account_no);
        if (accountPositionManager != null)
            return accountPositionManager;
        accountPositionManager = createAccountPositionManager(account_no);
        accountPositionManagerHashMap.put(account_no, accountPositionManager);
        return accountPositionManager;
    }

    public SmAccountPositionManager findAccountPositonManager(String account_no) {
        if (accountPositionManagerHashMap.containsKey(account_no))
            return accountPositionManagerHashMap.get(account_no);
        return null;
    }

    private SmAccountPositionManager createAccountPositionManager(String account_no) {
        SmAccountPositionManager accountPositionManager = new SmAccountPositionManager();
        accountPositionManager.setAccountNo(account_no);
        return accountPositionManager;
    }

    public static double getOpenProfit(SmPosition position) {
        if (position == null)
            return 0.0;
        SmSymbolManager symbolManager = SmSymbolManager.getInstance();
        SmSymbol symbol = symbolManager.findSymbol(position.symbolCode);
        if (symbol == null)
            return 0.0;
        double div = Math.pow(10, symbol.decimal);
        double curClose = (symbol.quote.C / div);
        position.curPrice = curClose;
        return position.openQty * (curClose - position.avgPrice) * symbol.seungsu;
    }

    public void updatePosition(SmSymbol symbol) {
        if (symbol == null)
            return;
        ArrayList<SmPosition> positionArrayList = getPositionList(symbol.code);
        if (positionArrayList.size() == 0)
            return;
        // 여기서 모든 포지션을 업데이트 해준다.
        for(SmPosition position : positionArrayList) {
            position.updateOpenPL();
        }
    }

    public void onUpdateSise(SmSymbol symbol) {
        if (symbol == null)
            return;
        ArrayList<SmPosition> positionArrayList = getPositionList(symbol.code);
        if (positionArrayList.size() == 0)
            return;
        // 여기서 모든 포지션을 업데이트 해준다.
        for(SmPosition position : positionArrayList) {
            position.updateOpenPL();
            SmChartDataService.getInstance().onUpdatePosition(position);
        }
    }

    public SmPosition getDefaultPositon(String symbolCode) {
        ArrayList<SmPosition> positionArrayList = getPositionList(symbolCode);
        if (positionArrayList.size() == 0)
            return null;

        return positionArrayList.get(0);
    }
}
