package signalmaster.com.smmobile.account;

import com.scichart.core.framework.SmartProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.isDigit;

public class SmAccountManager implements Serializable {
    private static volatile SmAccountManager sSoleInstance;
    //private constructor.
    private SmAccountManager(){
        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmAccountManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmAccountManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmAccountManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmAccountManager readResolve() {
        return getInstance();
    }

    private HashMap<String, SmAccount> accountHashMap = new HashMap<>();
    public SmAccount findAccount(String account_no) {
        if (accountHashMap.containsKey(account_no)) {
            return accountHashMap.get(account_no);
        }

        return null;
    }

    public ArrayList<String> getAccountNoList() {
        ArrayList<String> accountNoList = new ArrayList<>();
        for (Map.Entry<String, SmAccount> entry : accountHashMap.entrySet()) {
            accountNoList.add(entry.getKey());
        }

        return accountNoList;
    }

    public ArrayList<SmAccount> getAccountList() {
        ArrayList<SmAccount> accountNoList = new ArrayList<>();
        for (Map.Entry<String, SmAccount> entry : accountHashMap.entrySet()) {
            accountNoList.add(entry.getValue());
        }

        return accountNoList;
    }

    private SmAccount domesticAccount = null;
    private SmAccount abroadAccount = null;

    public void clearAccountList() {
        accountHashMap.clear();
    }

    public void AddAccount(SmAccount account) {
        if (account == null)
            return;
        if (account.acccountType == 0)
            abroadAccount = account;
        else
            domesticAccount = account;
        accountHashMap.put(account.accountNo, account);
    }

    public String getDefaultAccountNo(String symbolCode) {
        char second = symbolCode.charAt(1);
        if (isDigit(second)) {
            return  domesticAccount != null ? domesticAccount.accountNo : null;
        } else {
            return  abroadAccount != null ? abroadAccount.accountNo : null;
        }
    }

    public SmAccount getDefaultAccount(String symbolCode) {
        char second = symbolCode.charAt(1);
        if (isDigit(second)) {
            return  domesticAccount;
        } else {
            return  abroadAccount;
        }
    }
}
