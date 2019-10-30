package signalmaster.com.smmobile.global;

import java.io.Serializable;

import static java.lang.Character.isDigit;

public class SmConst implements Serializable {

    private static volatile SmConst sSoleInstance;

    //private constructor.
    private SmConst(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmConst getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmConst.class) {
                if (sSoleInstance == null) sSoleInstance = new SmConst();
            }
        }

        return sSoleInstance;
    }

    public static int ChartDataSize = 1500;
    public static int TempDataSize = 1500;
    // 해외 선물 수수료
    public static double AbroadFee = 4;
    // 국내 선물 수수료
    public static double DomesticFee = 1000;

    public static double getFee(String symbolCode) {
        char second = symbolCode.charAt(1);
        if (isDigit(second)) {
            return  DomesticFee;
        } else {
            return  AbroadFee;
        }
    }

    public static double getFee(int account_type) {
        if (account_type == 1) {
            return  DomesticFee;
        } else {
            return  AbroadFee;
        }
    }

    //Make singleton from serialize and deserialize operation.
    protected SmConst readResolve() {
        return getInstance();
    }
}