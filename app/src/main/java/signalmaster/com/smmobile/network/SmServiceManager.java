package signalmaster.com.smmobile.network;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import signalmaster.com.smmobile.chart.SmChartDataRequest;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.order.SmOrderRequest;
import signalmaster.com.smmobile.order.SmOrderType;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.userinfo.SmUser;
import signalmaster.com.smmobile.userinfo.SmUserManager;

public class SmServiceManager implements Serializable {

    private static volatile SmServiceManager sSoleInstance;

    //private constructor.
    private SmServiceManager() {

        //Prevent form the reflection api.
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmServiceManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmServiceManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmServiceManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmServiceManager readResolve() {
        return getInstance();
    }

    private boolean loggedIn = false;
    private boolean receivedCategoryList = false;
    private boolean receivedSymbolList = false;

    public void reqestLogin() {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();
            JSONObject login_info = new JSONObject();
            login_info.put("req_id", SmProtocol.req_login.ordinal());
            login_info.put("user_id",user.id);
            login_info.put("pwd",user.password);

            String msg = login_info.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);


        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void reqestLogin(String id, String pwd) {
        try {

            JSONObject login_info = new JSONObject();
            login_info.put("req_id", SmProtocol.req_login.ordinal());
            login_info.put("user_id",id);
            login_info.put("pwd",pwd);

            String msg = login_info.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);


        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestSiseData(String symbolCode) {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();

            JSONObject reqSiseData = new JSONObject();
            reqSiseData.put("req_id", SmProtocol.req_sise_data.ordinal());
            reqSiseData.put("user_id",user.id);
            reqSiseData.put("symbol_code",symbolCode);

            String msg = reqSiseData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public  void requestHogaData(String symbolCode) {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();

            JSONObject reqSiseData = new JSONObject();
            reqSiseData.put("req_id", SmProtocol.req_hoga_data.ordinal());
            reqSiseData.put("user_id",user.id);
            reqSiseData.put("symbol_code",symbolCode);

            String msg = reqSiseData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestChartData(SmChartDataRequest req) {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();
            JSONObject chartData = new JSONObject();
            chartData.put("req_id", SmProtocol.req_chart_data.ordinal());
            chartData.put("user_id", user.id);
            chartData.put("chart_type", req.chartType.getValue());
            chartData.put("symbol_code",req.symbolCode);
            chartData.put("cycle",req.cycle);
            chartData.put("count",req.count);
            chartData.put("chart_id", req.chart_id);
            String msg = chartData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void registerRealtimeSymbol(String symbolCode) {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();

            JSONObject reqSiseData = new JSONObject();
            reqSiseData.put("req_id", SmProtocol.req_register_symbol.ordinal());
            reqSiseData.put("user_id",user.id);
            reqSiseData.put("symbol_code",symbolCode);

            String msg = reqSiseData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void registerAllRecentRealtimeSise() {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();

            JSONObject reqSiseData = new JSONObject();
            reqSiseData.put("req_id", SmProtocol.req_register_recent_realtime_sise_all.ordinal());
            reqSiseData.put("user_id",user.id);

            String msg = reqSiseData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public  void registerCycleChartData(SmChartDataRequest req) {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();
            JSONObject chartData = new JSONObject();
            chartData.put("req_id", SmProtocol.req_register_chart_cycle_data.ordinal());
            chartData.put("user_id", user.id);
            chartData.put("chart_type", req.chartType.getValue());
            chartData.put("symbol_code",req.symbolCode);
            chartData.put("cycle",req.cycle);
            chartData.put("count",req.count);
            String msg = chartData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestLogout() {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();
            JSONObject user_info = new JSONObject();
            user_info.put("req_id", SmProtocol.req_logout.ordinal());
            user_info.put("id",user.id);
            user_info.put("pwd",user.password);

            String msg = user_info.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestAllRecentMonthSise() {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();

            JSONObject reqSiseData = new JSONObject();
            reqSiseData.put("req_id", SmProtocol.req_recent_sise_data_all.ordinal());
            reqSiseData.put("user_id",user.id);

            String msg = reqSiseData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestAllSise() {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();

            JSONObject reqSiseData = new JSONObject();
            reqSiseData.put("req_id", SmProtocol.req_sise_data_all.ordinal());
            reqSiseData.put("user_id",user.id);

            String msg = reqSiseData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestSymbolMaster(String symCode) {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();

            JSONObject reqSiseData = new JSONObject();
            reqSiseData.put("req_id", SmProtocol.req_symbol_master.ordinal());
            reqSiseData.put("user_id",user.id);
            reqSiseData.put("symbol_code",symCode);

            String msg = reqSiseData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestAllSymbolMaster() {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();

            JSONObject reqSiseData = new JSONObject();
            reqSiseData.put("req_id", SmProtocol.req_symbol_master_all.ordinal());
            reqSiseData.put("user_id",user.id);

            String msg = reqSiseData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestMarketList() {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();

            JSONObject reqSiseData = new JSONObject();
            reqSiseData.put("req_id", SmProtocol.req_market_list.ordinal());
            reqSiseData.put("user_id",user.id);

            String msg = reqSiseData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestSymbolListByCategory() {
        try {
            SmUser user = SmUserManager.getInstance().getDefaultUser();

            JSONObject reqSiseData = new JSONObject();
            reqSiseData.put("req_id", SmProtocol.req_symbol_list_by_category.ordinal());
            reqSiseData.put("user_id",user.id);

            String msg = reqSiseData.toString();

            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestOrder(SmOrderRequest request) {
        try {
            JSONObject reqOrder = new JSONObject();
            if (request.orderType == SmOrderType.New)
                reqOrder.put("req_id", SmProtocol.req_order_new.ordinal());
            else if (request.orderType == SmOrderType.Modify)
                reqOrder.put("req_id", SmProtocol.req_order_modify.ordinal());
            else if (request.orderType == SmOrderType.Cancel)
                reqOrder.put("req_id", SmProtocol.req_order_cancel.ordinal());
            else
                return;

            SmUser user = SmUserManager.getInstance().getDefaultUser();
            reqOrder.put("user_id",user.id);
            reqOrder.put("account_no", request.accountNo);
            reqOrder.put("password", request.password);
            reqOrder.put("symbol_code", request.symbolCode);
            reqOrder.put("position_type", request.positionType.ordinal());
            reqOrder.put("price_type", request.priceType.ordinal());
            reqOrder.put("filled_condition", request.filledCondition.ordinal());
            reqOrder.put("order_price", request.orderPrice);
            reqOrder.put("order_amount", request.orderAmount);
            reqOrder.put("ori_order_no",  request.oriOrderNo);
            reqOrder.put("request_id", request.requestId);
            reqOrder.put("order_type", request.orderType.ordinal());
            reqOrder.put("fund_name", request.fundName);
            reqOrder.put("system_name", request.symtemName);
            reqOrder.put("strategy_name", request.strategyName);
            String msg = reqOrder.toString();
            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestAccountList(String user_id) {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put("req_id", SmProtocol.req_account_list.ordinal());
            json_object.put("user_id",user_id);
            String msg = json_object.toString();
            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestAcceptedOrderList(String user_id, String account_no) {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put("req_id", SmProtocol.req_accepted_order_list.ordinal());
            json_object.put("user_id",user_id);
            json_object.put("account_no", account_no);
            String msg = json_object.toString();
            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestFilledOrderList(String user_id, String account_no) {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put("req_id", SmProtocol.req_filled_order_list.ordinal());
            json_object.put("user_id",user_id);
            json_object.put("account_no", account_no);
            String msg = json_object.toString();
            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestOrderList(String user_id, String account_no, int count) {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put("req_id", SmProtocol.req_order_list.ordinal());
            json_object.put("user_id",user_id);
            json_object.put("account_no", account_no);
            json_object.put("count", count);
            String msg = json_object.toString();
            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestOrderList(String date_time, String user_id, String account_no) {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put("req_id", SmProtocol.req_order_list.ordinal());
            json_object.put("user_id",user_id);
            json_object.put("account_no", account_no);
            json_object.put("request_date", date_time);
            String msg = json_object.toString();
            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestPositionList(String user_id, String account_no) {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put("req_id", SmProtocol.req_position_list.ordinal());
            json_object.put("user_id",user_id);
            json_object.put("account_no", account_no);
            String msg = json_object.toString();
            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void requestRegisterUser(String user_id, String pwd) {
        try {
            JSONObject reqOrder = new JSONObject();
            reqOrder.put("req_id", SmProtocol.req_register_user.ordinal());
            reqOrder.put("user_id",user_id);
            reqOrder.put("password", pwd);
            String msg = reqOrder.toString();
            SmSocketHandler socketHandler = SmSocketHandler.getInstance();
            socketHandler.sendMessage(msg);
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isReceivedCategoryList() {
        return receivedCategoryList;
    }

    public void setReceivedCategoryList(boolean receivedCategoryList) {
        this.receivedCategoryList = receivedCategoryList;
    }

    public boolean isReceivedSymbolList() {
        return receivedSymbolList;
    }

    public void setReceivedSymbolList(boolean receivedSymbolList) {
        this.receivedSymbolList = receivedSymbolList;
    }
}