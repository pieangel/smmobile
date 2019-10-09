package signalmaster.com.smmobile.crobo;

import android.support.v4.app.Fragment;

import java.io.Serializable;

public class SmCroboManager implements Serializable {

    private static volatile SmCroboManager sSoleInstance;

    public SmCroboManager() {
        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmCroboManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmCroboManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmCroboManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmCroboManager readResolve() {
        return getInstance();
    }


    public  void SaveFragment(int number){

        switch (number){
            case 0:
                break;
            case 1:
                break;
        }
    }


}
