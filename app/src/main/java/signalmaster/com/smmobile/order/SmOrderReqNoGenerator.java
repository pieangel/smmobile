package signalmaster.com.smmobile.order;

public class SmOrderReqNoGenerator {
    private static int id = 0;
    public static int getId() {
        return id++;
    }
}
