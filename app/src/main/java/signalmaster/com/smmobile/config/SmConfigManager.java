package signalmaster.com.smmobile.config;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import signalmaster.com.smmobile.account.SmAccount;


public class SmConfigManager implements Serializable {
    private static volatile SmConfigManager sSoleInstance;
    //private constructor.
    private SmConfigManager(){
        //Prevent form the reflection api.
        if (sSoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmConfigManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmConfigManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmConfigManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmConfigManager readResolve() {
        return getInstance();
    }

}
