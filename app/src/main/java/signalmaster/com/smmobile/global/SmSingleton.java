package signalmaster.com.smmobile.global;

import java.io.Serializable;

public class SmSingleton implements Serializable {

    private static volatile SmSingleton sSoleInstance;

    //private constructor.
    private SmSingleton(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmSingleton getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmSingleton.class) {
                if (sSoleInstance == null) sSoleInstance = new SmSingleton();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmSingleton readResolve() {
        return getInstance();
    }
}