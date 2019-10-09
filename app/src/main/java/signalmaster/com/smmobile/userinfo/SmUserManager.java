package signalmaster.com.smmobile.userinfo;

import java.io.Serializable;

public class SmUserManager implements Serializable {

    private static volatile SmUserManager sSoleInstance;

    //private constructor.
    private SmUserManager() {

        if (defaultUser == null) {
            createUserInfo();
        }
        //Prevent form the reflection api.
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmUserManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmUserManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmUserManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmUserManager readResolve() {
        return getInstance();
    }

    private SmUser defaultUser = null;
    public SmUser getDefaultUser() {
        return defaultUser;
    }

    public boolean isLoggedIn() {
        SmUser user = getDefaultUser();
        if (user == null)
            return false;
        return user.loggedIn;
    }

    public void setLoggedIn(boolean flag) {
        SmUser user = getDefaultUser();
        if (user == null)
            return ;
        user.loggedIn = flag;
    }
    public SmUser addUserInfo(String id, String pwd) {
        defaultUser = new SmUser();
        defaultUser.id = id;
        defaultUser.password = pwd;
        return defaultUser;
    }

    public SmUser createUserInfo() {
        defaultUser = new SmUser();
        defaultUser.id = "pieangel@naver.com";
        defaultUser.password = "orion1";
        defaultUser.cert = "12345678";
        defaultUser.name = "Jinsu Jo";

        return defaultUser;
    }
}
