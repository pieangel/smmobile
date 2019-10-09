package signalmaster.com.smmobile.global;

import java.io.Serializable;

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

    //Make singleton from serialize and deserialize operation.
    protected SmConst readResolve() {
        return getInstance();
    }
}