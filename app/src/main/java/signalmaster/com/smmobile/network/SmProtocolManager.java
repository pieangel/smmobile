package signalmaster.com.smmobile.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import signalmaster.com.smmobile.LoginActivity;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.account.SmAccount;
import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.chart.SmChartType;
import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.data.SmChartDataItem;
import signalmaster.com.smmobile.data.SmChartDataManager;
import signalmaster.com.smmobile.data.SmChartDataService;
import signalmaster.com.smmobile.global.SmGlobal;
import signalmaster.com.smmobile.market.SmCategory;
import signalmaster.com.smmobile.market.SmMarket;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.order.SmFilledCondition;
import signalmaster.com.smmobile.order.SmOrder;
import signalmaster.com.smmobile.order.SmOrderState;
import signalmaster.com.smmobile.order.SmOrderType;
import signalmaster.com.smmobile.order.SmPriceType;
import signalmaster.com.smmobile.order.SmTotalOrderManager;
import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.position.SmPositionType;
import signalmaster.com.smmobile.position.SmTotalPositionManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;
import signalmaster.com.smmobile.userinfo.SmUserManager;

enum SmProtocol {
    req_none,
    // 로그인 요청
    req_login,
    // 로그인 응답
    res_login,
    // 로그아웃 요청
    req_logout,
    // 로그아웃 응답
    res_logout,
    // 실시간 시세 등록 요청
    req_register_symbol,
    // 실시간 시세 등록 응답
    res_register_symbol,
    // 실시간 시세 해제 요청
    req_unregister_symbol,
    // 실시간 시세 해제 응답
    res_unregister_symbol,
    // 주기 차트 데이터 등록 요청
    req_register_chart_cycle_data,
    // 주기 차트 데이터 등록 응답
    res_register_chart_cycle_data,
    // 신규 주문 요청
    req_order_new,
    // 신규 주문 요청 응답
    res_order_new,
    // 정정 주문 요청
    req_order_modify,
    // 정정 주문 응답
    res_order_modify,
    // 취소 주문 요청
    req_order_cancel,
    // 취소 주문 응답
    res_order_cancel,
    // 주문 접수확인 응답
    res_order_accepted,
    // 주문 체결 응답
    res_order_filled,
    // 차트데이터 요청
    req_chart_data,
    // 차트 데이터 응답
    res_chart_data,
    // 시세 데이터 요청
    req_sise_data,
    // 시세 데이터 응답
    res_sise_data,
    // 실시간 호가 전송
    res_realtime_hoga,
    // 실시간 시세 전송
    res_realtime_sise,
    // 주기 차트 데이터 전송
    res_chart_cycle_data,
    // 주기 차트 데이터 해제 요청
    req_unregister_chart_cycle_data,
    // 주기 차트 데이터 해제 응답
    res_unregister_chart_cycle_data,
    // 호가데이터 요청
    req_hoga_data,
    // 호가데이터 응답
    res_hoga_data,
    // 심볼 마스트 요청
    req_symbol_master,
    // 심볼 마스터 응답
    res_symbol_master,
    // 모든 심볼 마스터 요청
    req_symbol_master_all,
    // 모든 종목 현시세 요청
    req_sise_data_all,
    // 최근월물 모든 현시세 요청
    req_recent_sise_data_all,
    // 최근월물 실시간 시세 등록 요청
    req_register_recent_realtime_sise_all,
    // 마켓 목록 요청
    req_market_list,
    // 마켓 목록 응답
    res_market_list,
    // 카테고리별 종목 리스트 요청
    req_symbol_list_by_category,
    // 종목 잔고 정보
    res_symbol_position,
    // 주문 관련 오류
    res_order_error,
    // 포지션 목록 요청
    req_position_list,
    // 포지션 목록 응답
    res_position_list,
    // 시세 소켓 등록 요청
    req_register_sise_socket,
    // 시세 소켓 등록 응답
    res_register_sise_socket,
    // 시세 차트에서 차트 데이터를 받았음을 알리고 클라이언트에게 전송을 종용한다.
    req_chart_data_resend,
    // 메인차트에서 차트 데이터 요청이 도착함
    req_chart_data_from_main_server,
    // 심볼 시세 업데이트 요청
    req_update_quote,
    // 심볼 호가 업데이트 요청
    req_update_hoga,
    // 차트데이터가 업데이트 되었음을 알린다.
    req_update_chart_data,
    // 사용자 등록을 요청한다.
    req_register_user,
    // 사용자 등록 요청에 대한 응답
    res_register_user,
    // 사용자 등록 해제 요청
    req_unregister_user,
    // 사용자 등록 해제 요청 응답
    res_unregister_user,
    // 계좌 목록 요청
    req_account_list,
    // 계좌 목록 응답
    res_account_list,
    // 차트 데이터 일대일 요청
    req_chart_data_onebyone,
    // 차트 데이터 일대일 응답
    res_chart_data_onebyone,
    // 접수확인 주문 목록 요청
    req_accepted_order_list,
    // 접수확인 주문 목록 응답
    res_accepted_order_list,
    // 체결 주문 목록 요청
    req_filled_order_list,
    // 체결 주문 목록 응답
    res_filled_order_list,
    // 주문 목록 요청
    req_order_list,
    // 주문 목록 응답
    res_order_list,
    // 차트 데이터를 하나씩 보낸다.
    req_chart_data_resend_onebyone,
    // 주기 데이터를 하나씩 보낸다.
    req_cycle_data_resend_onebyone,
    // 주문이 청산되었음을 보낸다.
    res_order_settled
}

public class SmProtocolManager implements Serializable {

    private static volatile SmProtocolManager sSoleInstance;

    //private constructor.
    private SmProtocolManager() {

        //Prevent form the reflection api.
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmProtocolManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmProtocolManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmProtocolManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmProtocolManager readResolve() {
        return getInstance();
    }

    private SmGlobal.SmAppState appState = SmGlobal.SmAppState.Init;

    public void OnMessage(String message) {
        try {
            JSONObject jsonObj = new JSONObject(message);
            //Log.d("TAG", "onMessage: " + message);
            int res_is = jsonObj.getInt("res_id");
            SmProtocol protocol = SmProtocol.values()[res_is];
            switch (protocol) {
                case res_login: // 로그인 응답
                    OnResLogin(jsonObj);
                    break;
                case res_logout: // 로그아웃 응답
                    OnResLogout(jsonObj);
                    break;
                case res_chart_data: // 차트 데이터 요청에 대한 응답 - 차트 데이터는 분할되어 온다.
                    OnResChartData(jsonObj);
                    break;
                case res_sise_data: // 시세 데이터 요청에 대한 응답
                case res_realtime_sise: // 실시간 시세 데이터 도착
                    OnResRealtimeSise(jsonObj);
                    break;
                case res_hoga_data: // 호가 데이터 요청에 대한 응답
                case res_realtime_hoga: // 실시간 호가 데이터 도착
                    OnResHogaData(jsonObj);
                    break;
                case res_chart_cycle_data: // 정기 차트 데이터 도착
                    OnResChartCycleData(jsonObj);
                    break;
                case res_symbol_master: // 심볼 마스터 정보 도착
                    OnResSymbolMaster(jsonObj);
                    break;
                case res_market_list:
                    OnResMarketList(jsonObj);
                    break;
                case res_order_new:
                case res_order_modify:
                case res_order_cancel:
                    onResOrder(jsonObj);
                    break;
                case res_order_accepted:
                    onOrderAccepted(jsonObj);
                    break;
                case res_order_filled:
                    onOrderFilled(jsonObj);
                    break;
                case res_symbol_position:
                    onResPosition(jsonObj);
                    break;
                case res_chart_data_onebyone:
                    onResChartDataOnebyone(jsonObj);
                    break;
                case res_account_list:
                    onResAccountList(jsonObj);
                    break;
                case res_accepted_order_list:
                    onResAcceptedList(jsonObj);
                    break;
                case res_filled_order_list:
                    onResFilledList(jsonObj);
                    break;
                case res_order_list:
                    onResOrderList(jsonObj);
                    break;
                case res_position_list:
                    onResPositionList(jsonObj);
                    break;
                case res_order_settled:
                    onResOrderSettled(jsonObj);
                    break;
                case res_register_user:
                    onRegisterUser(jsonObj);
                    break;
            }
        }
        catch (JSONException e) {
            Log.d("TAG", "onMessage: Error -> " + e.getMessage());
        }
    }

    private void onRegisterUser(JSONObject object) {
        if (object == null)
            return;
        try {
            String result_msg = object.getString("message");
            String user_id = object.getString("user_id");
            String password = object.getString("password");
            Log.d("TAG", "onResRegisterUser:  -> " + result_msg + user_id);
            LoginActivity loginActivity = (LoginActivity)SmArgManager.getInstance().getVal("sign_up", "login_activity");
            if (loginActivity != null) {
                loginActivity.onRegisteredUser(user_id, password);
            }
        }
        catch (JSONException e) {
            Log.d("TAG", "onResRegisterUser Exception:  -> " + e.getMessage());
        }
    }

    private int account_count = 0;
    private  void onResAccountList(JSONObject object) {
        if (object == null)
            return;
        try {
            SmAccountManager.getInstance().clearAccountList();
            int total_account_count = object.getInt("total_account_count");
            if (total_account_count == 0) {
                appState = SmGlobal.SmAppState.AccountListDownloaded;
                return;
            }
            JSONArray data_array = (JSONArray) object.get("account");
            for (int i=0; i < data_array.length(); i++) {
                JSONObject item = data_array.getJSONObject(i);
                String account_no = item.getString("account_no");
                String account_name = item.getString("account_name");
                String password = item.getString("password");
                double initial_balance = item.getDouble("initial_balance");
                double trade_profit_loss = item.getDouble("trade_profit_loss");
                double open_profit_loss = item.getDouble("open_profit_loss");
                Log.d("TAG", "account_no:  -> " + account_no);
                SmAccount account = new SmAccount();
                account.accountNo = account_no;
                account.accountName = account_name;
                account.password = password;
                account.inital_balance = initial_balance;
                account.trade_pl = trade_profit_loss;
                account.open_pl = open_profit_loss;
                SmAccountManager.getInstance().addAccount(account);
                account_count++;
                if (account_count == total_account_count) {
                    appState = SmGlobal.SmAppState.AccountListDownloaded;
                }
            }
        }
        catch (JSONException e) {
            Log.d("TAG", "onResAccountList Exception:  -> " + e.getMessage());
        }
    }
    private int order_count = 0;
    private void onResAcceptedList(JSONObject object) {
        if (object == null)
            return;
        try {
            int total_order_count = object.getInt("total_order_count");
            if (total_order_count == 0) {
                appState = SmGlobal.SmAppState.AcceptedListDownloaded;
                return;
            }

            JSONArray data_array = (JSONArray) object.get("order");
            for (int i=0; i < data_array.length(); i++) {
                JSONObject item = data_array.getJSONObject(i);
                SmOrder order = new SmOrder();
                order.accountNo = item.getString("account_no");
                order.orderType = SmOrderType.valueOf(item.getInt("order_type"));
                order.positionType = SmPositionType.valueOf(item.getInt("position_type"));
                order.priceType =  SmPriceType.valueOf(item.getInt("price_type"));
                order.symbolCode = item.getString("symbol_code");
                order.orderPrice = item.getInt("order_price");
                order.orderNo = item.getInt("order_no");
                order.orderAmount = item.getInt("order_amount");
                order.oriOrderNo = item.getInt("ori_order_no");
                order.filledDate = item.getString("filled_date");
                order.filledTime = item.getString("filled_time");
                order.orderDate = item.getString("order_date");
                order.orderTime =item.getString("order_time");
                order.filledQty = item.getInt("filled_qty");
                order.filledPrice = item.getInt("filled_price");
                order.orderState =  SmOrderState.valueOf(item.getInt("order_state"));
                order.filledCondition = SmFilledCondition.valueOf(item.getInt("filled_condition"));
                order.symbolDecimal = item.getInt("symbol_decimal");
                order.remainQty = item.getInt("remain_qty");
                order.strategyName = item.getString("strategy_name");
                order.systemName = item.getString("system_name");
                order.fundName = item.getString("fund_name");
                SmTotalOrderManager.getInstance().addAcceptedOrder(order);
                order_count++;
                if (order_count == total_order_count) {
                    appState = SmGlobal.SmAppState.AcceptedListDownloaded;
                }
            }
        }
        catch (JSONException e) {
            Log.d("TAG", "onResAcceptedList Exception:  -> " + e.getMessage());
        }
    }

    private void onResFilledList(JSONObject object) {
        if (object == null)
            return;
        try {
            int total_order_count = object.getInt("total_order_count");
            if (total_order_count == 0) {
                appState = SmGlobal.SmAppState.FilledListDownloaded;
                return;
            }
            JSONArray data_array = (JSONArray) object.get("order");
            for (int i=0; i < data_array.length(); i++) {
                JSONObject item = data_array.getJSONObject(i);
                SmOrder order = new SmOrder();
                order.accountNo = item.getString("account_no");
                order.orderType = SmOrderType.valueOf(item.getInt("order_type"));
                order.positionType = SmPositionType.valueOf(item.getInt("position_type"));
                order.priceType =  SmPriceType.valueOf(item.getInt("price_type"));
                order.symbolCode = item.getString("symbol_code");
                order.orderPrice = item.getInt("order_price");
                order.orderNo = item.getInt("order_no");
                order.orderAmount = item.getInt("order_amount");
                order.oriOrderNo = item.getInt("ori_order_no");
                order.filledDate = item.getString("filled_date");
                order.filledTime = item.getString("filled_time");
                order.orderDate = item.getString("order_date");
                order.orderTime =item.getString("order_time");
                order.filledQty = item.getInt("filled_qty");
                order.filledPrice = item.getInt("filled_price");
                order.orderState =  SmOrderState.valueOf(item.getInt("order_state"));
                order.filledCondition = SmFilledCondition.valueOf(item.getInt("filled_condition"));
                order.symbolDecimal = item.getInt("symbol_decimal");
                order.remainQty = item.getInt("remain_qty");
                order.strategyName = item.getString("strategy_name");
                order.systemName = item.getString("system_name");
                order.fundName = item.getString("fund_name");
                SmTotalOrderManager.getInstance().addFilledOrder(order);
                order_count++;
                if (order_count == total_order_count) {
                    appState = SmGlobal.SmAppState.FilledListDownloaded;
                }
            }
        }
        catch (JSONException e) {
            Log.d("TAG", "onResFilledList Exception:  -> " + e.getMessage());
        }
    }

    private void onResOrderList(JSONObject object) {
        if (object == null)
            return;
        try {
            int total_order_count = object.getInt("total_order_count");
            if (total_order_count == 0)
                return;
            JSONArray data_array = (JSONArray) object.get("order");
            for (int i=0; i < data_array.length(); i++) {
                JSONObject item = data_array.getJSONObject(i);
                SmOrder order = new SmOrder();
                order.accountNo = item.getString("account_no");
                order.orderType = SmOrderType.valueOf(item.getInt("order_type"));
                order.positionType = SmPositionType.valueOf(item.getInt("position_type"));
                order.priceType =  SmPriceType.valueOf(item.getInt("price_type"));
                order.symbolCode = item.getString("symbol_code");
                order.orderPrice = item.getInt("order_price");
                order.orderNo = item.getInt("order_no");
                order.orderAmount = item.getInt("order_amount");
                order.oriOrderNo = item.getInt("ori_order_no");
                order.filledDate = item.getString("filled_date");
                order.filledTime = item.getString("filled_time");
                order.orderDate = item.getString("order_date");
                order.orderTime =item.getString("order_time");
                order.filledQty = item.getInt("filled_qty");
                order.filledPrice = item.getInt("filled_price");
                order.orderState =  SmOrderState.valueOf(item.getInt("order_state"));
                order.filledCondition = SmFilledCondition.valueOf(item.getInt("filled_condition"));
                order.symbolDecimal = item.getInt("symbol_decimal");
                order.remainQty = item.getInt("remain_qty");
                order.strategyName = item.getString("strategy_name");
                order.systemName = item.getString("system_name");
                order.fundName = item.getString("fund_name");
                SmTotalOrderManager.getInstance().addOrder(order);
                order_count++;
            }
        }
        catch (JSONException e) {
            Log.d("TAG", "onResOrderList Exception:  -> " + e.getMessage());
        }
    }

    private int positon_count = 0;
    private void onResPositionList(JSONObject object) {
        if (object == null)
            return;
        try {
            int total_position_count = object.getInt("total_position_count");
            if (total_position_count == 0) {
                appState = SmGlobal.SmAppState.PositionListDownloaded;
                SmUserManager.getInstance().setLoggedIn(true);
                return;
            }
            JSONArray data_array = (JSONArray) object.get("position");
            for (int i=0; i < data_array.length(); i++) {
                JSONObject item = data_array.getJSONObject(i);
                String created_date = item.getString("created_date");
                String created_time = item.getString("created_time");

                String symbol_code = item.getString("symbol_code");
                String account_no = item.getString("account_no");
                int position_type = item.getInt("position_type");
                int open_qty = item.getInt("open_qty");
                double fee = item.getDouble("fee");
                double trade_pl = item.getDouble("trade_profitloss");
                double avg_price = item.getDouble("average_price");
                double current_price = item.getDouble("current_price");
                double open_pl = item.getDouble("open_profitloss");
                SmPosition position = SmTotalPositionManager.getInstance().findAddPosition(account_no, symbol_code);
                position.createdDate = created_date;
                position.createTime = created_time;
                position.positionType = SmPositionType.valueOf(position_type);
                position.openQty = open_qty;
                position.fee = fee;
                position.tradePL = trade_pl;
                position.avgPrice = avg_price;
                position.curPrice = current_price;
                position.openPL = open_pl;

                SmChartDataService chartDataService = SmChartDataService.getInstance();
                chartDataService.onUpdatePosition(position);

                positon_count++;

                if (positon_count == total_position_count) {
                    appState = SmGlobal.SmAppState.PositionListDownloaded;
                    SmUserManager.getInstance().setLoggedIn(true);
                }
            }
        }
        catch (JSONException e) {
            Log.d("TAG", "onResOrderList Exception:  -> " + e.getMessage());
        }
    }

    private void onResChartDataOnebyone(JSONObject object) {
        if (object == null)
            return;
        try {
            String symbol_code = object.getString("symbol_code");
            SmSymbol sym = SmSymbolManager.getInstance().findSymbol(symbol_code);
            if (sym == null) {
                return;
            }
            // 전체 데이터 갯수
            int total_count = object.getInt("total_count");
            // 현재 데이터 갯수
            int current_count = object.getInt("current_count");
            int cycle = object.getInt("cycle");
            int chart_type = object.getInt("chart_type");
            SmChartType chartType = SmChartType.fromInt(chart_type);
            if (chartType == null)
                return;
            // 최신데이터가 가정 먼저들어온다.
            String data_key = object.getString("data_key");
            SmChartData chart_data = SmChartDataManager.getInstance().findChartData(data_key);
            if (chart_data == null) {
                chart_data = SmChartDataManager.getInstance().createChartData(symbol_code, chartType, cycle);
            }

            String date_time = object.getString("date_time");
            int h = object.getInt("h");
            int l = object.getInt("l");
            int o = object.getInt("o");
            int c = object.getInt("c");
            int v = object.getInt("v");
            if (c == 0 || l == 0 || h == 0 || o == 0)
                return;
            double vh, vl, vo , vc;
            double div = Math.pow(10, sym.decimal);
            vh = (h / div);
            vl = (l / div);
            vo = (o / div);
            vc = (c / div);
            Log.d("TAG", "OnResChartData" + "  code" + symbol_code + " : " + date_time + " : " + h + " : " + l + " : " + o + " : " + c);
            boolean included = chart_data.isIncluded(date_time);
            if (included) {
                SmChartDataItem price = chart_data.updateData(date_time, vo, vh, vl, vc, v);
                if (price == null)
                    return;
                price.cycle = chart_data.cycle;
                price.symbolCode = chart_data.symbolCode;
                price.chartType = chart_data.chartType;
                if (current_count == total_count)
                    price.isLast = true;
                SmChartDataService chartDataService = SmChartDataService.getInstance();
                chartDataService.onUpdateData(price);
            }
            else {
                SmChartDataItem price = chart_data.add(date_time, vo, vh, vl, vc, v);
                if (price == null)
                    return;
                price.cycle = chart_data.cycle;
                price.symbolCode = chart_data.symbolCode;
                price.chartType = chart_data.chartType;
                if (current_count == total_count) {
                    price.isLast = true;
                    Log.d("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                }
                SmChartDataService chartDataService = SmChartDataService.getInstance();
                chartDataService.onNewData(price);
            }

        }
        catch (JSONException e) {
            Log.d("TAG", "OnResChartData Exception:  -> " + e.getMessage());
        }
    }

    private int rcvdCategoryCount = 0;
    private void OnResMarketList(JSONObject object) {
        if (object == null)
            return;
        try {
            String market_name = object.getString("market_name");
            int market_index = object.getInt("market_index");
            int total_market_count = object.getInt("total_market_count");
            int total_category_count = object.getInt("total_category_count");
            SmMarketManager marketManager = SmMarketManager.getInstance();
            SmMarket market = marketManager.addMarket(market_name, market_index);

            // getting phoneNumbers
            JSONArray data_array = (JSONArray) object.get("category");
            for (int i=0; i < data_array.length(); i++) {
                JSONObject item = data_array.getJSONObject(i);
                String code = item.getString("code");
                SmCategory category = market.addCategory(code);
                String name = item.getString("name_en");
                String name_kr = item.getString("name_kr");
                String exchange_name = item.getString("exchange_name");
                String exchange_code = item.getString("exchange_code");
                String cat_market_name = item.getString("market_name");
                Log.d("TAG", "category:  -> " + code + name_kr + exchange_name + exchange_code + cat_market_name);
                category.marketName = name;
                category.marketNameKr = name_kr;
                category.marketType = cat_market_name;
                category.exchangeCode = exchange_code;
                category.exchangeName = exchange_name;
                rcvdCategoryCount++;
                if (rcvdCategoryCount == total_category_count) {
                    appState = SmGlobal.SmAppState.CategoryListDownloaded;
                }
            }
            Log.d("TAG", "OnResMarketList:  -> " + market_name);
        }
        catch (JSONException e) {
            Log.d("TAG", "OnResMarketList Exception:  -> " + e.getMessage());
        }
    }

    private void OnResSymbolMaster(JSONObject object) {
        if (object == null)
            return;
        try {
            String symbol_code = object.getString("symbol_code");
            int total_symbol_count = object.getInt("total_symbol_count");
            String name_kr = object.getString("name_kr");
            String name_en = object.getString("name_en");
            int category_index = object.getInt("category_index");
            String category_code = object.getString("category_code");
            String market_name = object.getString("market_name");
            int decimal = object.getInt("decimal");
            double contract_unit = object.getDouble("contract_unit");
            int seungsu = object.getInt("seungsu");
            double tick_size = object.getDouble("tick_size");
            double tick_value = object.getDouble("tick_value");

            SmMarketManager smMarketManager = SmMarketManager.getInstance();
            SmSymbol symbol = new SmSymbol();
            symbol.code = symbol_code;
            symbol.seriesNmKr = name_kr;
            symbol.seriesNm = name_en;
            symbol.marketCode = category_code;
            symbol.decimal = decimal;
            symbol.contractSize = contract_unit;
            symbol.seungsu = seungsu;
            symbol.tickSize = tick_size;
            symbol.tickValue = tick_value;
            SmCategory curCat = smMarketManager.findCategory(market_name, category_code);
            symbol.set_category(curCat);
            symbol.marketType = curCat.marketType;
            curCat.addSymbol(symbol);
            SmSymbolManager symMgr = SmSymbolManager.getInstance();
            symMgr.addSymbol(symbol);

            if (market_name.compareTo("국내시장") == 0) {
                symbol.seriesNmKr = name_kr;
                symbol.seriesNm = name_en;
                symbol.marketCode = category_code;
                symbol.decimal = decimal;
                symbol.contractSize = contract_unit;
                symbol.seungsu = seungsu;
                symbol.tickSize = tick_size;
                symbol.tickValue = tick_value;
            }

            if (symMgr.getSymbolCount() == total_symbol_count) {
                SmMarketManager marketManager = SmMarketManager.getInstance();
                Log.d("TAG", "Received all symbols:  -> " + name_kr + market_name);
                appState = SmGlobal.SmAppState.SymbolListDownloaded;
            }
            Log.d("TAG", "OnResSymbolMaster:  -> " + name_kr + market_name);
        }
        catch (JSONException e) {
            Log.d("TAG", "OnResSymbolMaster Exception:  -> " + e.getMessage());
        }
    }

    private void OnResLogin(JSONObject object) {
        if (object == null)
            return;
        try {
            String result_msg = object.getString("result_msg");
            int result_code = object.getInt("result_code");
            Log.d("TAG", "OnResLogin:  -> " + result_code + result_code);

            SmServiceManager svcMgr = SmServiceManager.getInstance();
            svcMgr.setLoggedIn(true);
            appState = SmGlobal.SmAppState.Loggedin;
        }
        catch (JSONException e) {
            Log.d("TAG", "OnResLogin Exception:  -> " + e.getMessage());
        }
    }

    private void OnResLogout(JSONObject object) {
        if (object == null)
            return;
        try {
            String result_msg = object.getString("result_msg");
            int result_code = object.getInt("result_code");
            Log.d("TAG", "OnResLogout:  -> " + result_code + result_code);
        }
        catch (JSONException e) {
            Log.d("TAG", "OnResLogout Exception:  -> " + e.getMessage());
        }
    }

    private void OnResSiseData(JSONObject object) {
        if (object == null)
            return;
        try {
            String symbol_code = object.getString("symbol_code");
            int gap_from_preday = object.getInt("gap_from_preday");
            String sign_to_preday = object.getString("sign_to_preday");
            String ratio_to_preday = object.getString("ratio_to_preday");
            int open = object.getInt("open");
            int high = object.getInt("high");
            int low = object.getInt("low");
            int close = object.getInt("close");
            SmSymbolManager symMgr = SmSymbolManager.getInstance();
            SmSymbol sym = symMgr.findSymbol(symbol_code);
            if (sym != null) {
                sym.quote.O = open;
                sym.quote.H = high;
                sym.quote.L = low;
                sym.quote.C = close;
                sym.quote.gapToPreday = gap_from_preday;
                sym.quote.ratioToPrday = ratio_to_preday;
                sym.quote.sign_to_preday = sign_to_preday;
                // 여기서 이 심볼과 관련된 모든 차트데이터를 업데이트 해줘야 한다.
            }
            //Log.d("TAG", "OnResSiseData:  -> " + symbol_code );
        }
        catch (JSONException e) {
            Log.d("TAG", "OnResSiseData Exception:  -> " + e.getMessage());
        }
    }

    private void OnResChartData(JSONObject object) {
        if (object == null)
            return;
        try {
            // 최신데이터가 가정 먼저들어온다.
            String symbol_code = object.getString("symbol_code");
            int chart_type = object.getInt("chart_type");
            int cycle = object.getInt("cycle");
            int start_index = object.getInt("start_index");
            int end_index = object.getInt("end_index");
            int total_count = object.getInt("total_count");
            int cur_count = object.getInt("cur_count");
            String data_key = SmChartDataManager.getInstance().makeChartDataKey(symbol_code, chart_type, cycle);
            SmChartData chart_data = SmChartDataManager.getInstance().findChartData(data_key);
            if (chart_data == null) {
                chart_data = SmChartDataManager.getInstance().createChartData(symbol_code, SmChartType.fromInt(chart_type), cycle);
            }
            SmSymbolManager symMgr = SmSymbolManager.getInstance();
            SmSymbol sym = symMgr.findSymbol(symbol_code);
            if (sym == null) {
                return;
            }
            // getting phoneNumbers
            JSONArray data_array = (JSONArray) object.get("data");
            for (int i=0; i < data_array.length(); i++) {
                JSONObject item = data_array.getJSONObject(i);

                String date_time = item.getString("date_time");
                int h = item.getInt("high");
                int l = item.getInt("low");
                int o = item.getInt("open");
                int c = item.getInt("close");
                int v = item.getInt("volume");

                boolean included = chart_data.isIncluded(date_time);
                if (c == 0 || l == 0 || h == 0 || o == 0 ||included) {
                    continue;
                }
                else {
                    // 종가를 갱신해 준다.
                    sym.quote.C = c;
                    // 값을 변환해 차트에 추가해 준다.
                    double vh, vl, vo, vc;
                    double div = Math.pow(10, sym.decimal);
                    vh = (h / div);
                    vl = (l / div);
                    vo = (o / div);
                    vc = (c / div);
                    SmChartDataItem price = chart_data.add(date_time, vo, vh, vl, vc, v);
                    if (price == null)
                        continue;
                    price.cycle = chart_data.cycle;
                    price.symbolCode = chart_data.symbolCode;
                    price.chartType = chart_data.chartType;
                    if (end_index + 1 == total_count) {
                        price.isLast = true;
                    }
                    SmChartDataService chartDataService = SmChartDataService.getInstance();
                    chartDataService.onNewData(price);
                }
            }
        }
        catch (JSONException e) {
            Log.d("TAG", "OnResChartData Exception:  -> " + e.getMessage());
        }
    }

    private void OnResHogaData(JSONObject object) {
        if (object == null)
            return;
        try {
            String symbol_code = object.getString("symbol_code");
            SmSymbolManager symMgr = SmSymbolManager.getInstance();
            SmSymbol sym = symMgr.findSymbol(symbol_code);
            if (sym == null)
                return;

            String time = object.getString("time");

            String domestic_date = object.getString("domestic_date");
            String domestic_time = object.getString("domestic_time");
            int tot_buy_qty = object.getInt("tot_buy_qty");
            int tot_sell_qty = object.getInt("tot_sell_qty");
            int tot_buy_cnt = object.getInt("tot_buy_cnt");
            int tot_sell_cnt = object.getInt("tot_sell_cnt");
            sym.hoga.time = time;
            sym.hoga.domesticDate = domestic_date;
            sym.hoga.domesticTime = domestic_time;
            sym.hoga.totBuyCnt = tot_buy_cnt;
            sym.hoga.totBuyQty = tot_buy_qty;
            sym.hoga.totSellCnt = tot_sell_cnt;
            sym.hoga.totSellQty = tot_sell_qty;

            /*
            sym.hoga.hogaItem[0].buyPrice = object.getInt("buy_price1");
            sym.hoga.hogaItem[0].buyCnt =object.getInt("buy_cnt1");
            sym.hoga.hogaItem[0].buyQty = object.getInt("buy_qty1");
            sym.hoga.hogaItem[0].sellPrice = object.getInt("sell_price1");
            sym.hoga.hogaItem[0].sellCnt = object.getInt("sell_cnt1");
            sym.hoga.hogaItem[0].sellQty = object.getInt("sell_qty1");

            sym.hoga.hogaItem[1].buyPrice = object.getInt("buy_price2");
            sym.hoga.hogaItem[1].buyCnt =object.getInt("buy_cnt2");
            sym.hoga.hogaItem[1].buyQty = object.getInt("buy_qty2");
            sym.hoga.hogaItem[1].sellPrice = object.getInt("sell_price2");
            sym.hoga.hogaItem[1].sellCnt = object.getInt("sell_cnt2");
            sym.hoga.hogaItem[1].sellQty = object.getInt("sell_qty2");

            sym.hoga.hogaItem[2].buyPrice = object.getInt("buy_price3");
            sym.hoga.hogaItem[2].buyCnt =object.getInt("buy_cnt3");
            sym.hoga.hogaItem[2].buyQty = object.getInt("buy_qty3");
            sym.hoga.hogaItem[2].sellPrice = object.getInt("sell_price3");
            sym.hoga.hogaItem[2].sellCnt = object.getInt("sell_cnt3");
            sym.hoga.hogaItem[2].sellQty = object.getInt("sell_qty3");

            sym.hoga.hogaItem[3].buyPrice = object.getInt("buy_price4");
            sym.hoga.hogaItem[3].buyCnt =object.getInt("buy_cnt4");
            sym.hoga.hogaItem[3].buyQty = object.getInt("buy_qty4");
            sym.hoga.hogaItem[3].sellPrice = object.getInt("sell_price4");
            sym.hoga.hogaItem[3].sellCnt = object.getInt("sell_cnt4");
            sym.hoga.hogaItem[3].sellQty = object.getInt("sell_qty4");

            sym.hoga.hogaItem[4].buyPrice = object.getInt("buy_price5");
            sym.hoga.hogaItem[4].buyCnt =object.getInt("buy_cnt5");
            sym.hoga.hogaItem[4].buyQty = object.getInt("buy_qty5");
            sym.hoga.hogaItem[4].sellPrice = object.getInt("sell_price5");
            sym.hoga.hogaItem[4].sellCnt = object.getInt("sell_cnt5");
            sym.hoga.hogaItem[4].sellQty = object.getInt("sell_qty5");
            */


            JSONArray hoga_items = (JSONArray) object.get("hoga_items");
            for (int i=0; i < hoga_items.length(); i++) {
                JSONObject item = hoga_items.getJSONObject(i);
                int buy_price = item.getInt("buy_price");
                int buy_cnt = item.getInt("buy_cnt");
                int buy_qty = item.getInt("buy_qty");
                int sell_price = item.getInt("sell_price");
                int sell_cnt = item.getInt("sell_cnt");
                int sell_qty = item.getInt("sell_qty");
                sym.hoga.hogaItem[i].buyPrice = buy_price;
                sym.hoga.hogaItem[i].buyCnt = buy_cnt;
                sym.hoga.hogaItem[i].buyQty = buy_qty;
                sym.hoga.hogaItem[i].sellPrice = sell_price;
                sym.hoga.hogaItem[i].sellCnt = sell_cnt;
                sym.hoga.hogaItem[i].sellQty = sell_qty;
            }

            //Log.d("TAG", "OnResHogaData:  -> " + symbol_code);
            SmChartDataService chartDataService = SmChartDataService.getInstance();
            chartDataService.onUpdateHoga(sym);
        }
        catch (JSONException e) {
            Log.d("TAG", "OnResHogaData Exception:  -> " + e.getMessage());
        }
    }

    private void OnResRealtimeSise(JSONObject object) {
        if (object == null)
            return;
        try {
            String symbol_code = object.getString("symbol_code");
            int gap_from_preday = object.getInt("gap_from_preday");
            String sign_to_preday = object.getString("sign_to_preday");
            String ratio_to_preday = object.getString("ratio_to_preday");
            int open = object.getInt("open");
            int high = object.getInt("high");
            int low = object.getInt("low");
            int close = object.getInt("close");
            int acc_volume = object.getInt("acc_volume");
            if (close == 0.0)
                return;
            if (symbol_code.compareTo("105PA000") == 0) {
                high = 40;
            }
            SmSymbolManager symMgr = SmSymbolManager.getInstance();
            SmSymbol sym = symMgr.findSymbol(symbol_code);
            if (sym != null) {
                sym.quote.O = open;
                sym.quote.H = high;
                sym.quote.L = low;
                sym.quote.C = close;
                sym.quote.gapToPreday = gap_from_preday;
                sym.quote.ratioToPrday = ratio_to_preday;
                sym.quote.sign_to_preday = sign_to_preday;
                sym.quote.accVolume = acc_volume;
                double vc = 0.0;
                double div = Math.pow(10, sym.decimal);
                vc = (close / div);

                Log.d("TAG", "OnResRealtimeSise" + "  code" + symbol_code + " : " + vc);

                SmChartDataService chartDataService = SmChartDataService.getInstance();
                // 여기서 이 심볼과 관련된 모든 차트데이터를 업데이트 해줘야 한다.
                HashMap<String, SmChartData> chartDataHashMap = sym.getChartDataMap();
                for (Map.Entry<String, SmChartData> entry : chartDataHashMap.entrySet()) {
                    SmChartData chartData = entry.getValue();
                    // 관련된 차트를 하나씩 업데이트 해준다.
                    chartData.updateLastValue(vc);
                }
                // 시세 업데이트 콜백 호출
                chartDataService.onUpdateSise(sym);
                // 여기서 포지션에 따른 수익을 계산해 준다.
                SmTotalPositionManager totalPositionManager = SmTotalPositionManager.getInstance();
                // 포지션의 평가 손익을 갱신해 준다.
                totalPositionManager.onUpdateSise(sym);

                //Log.d("TAG", "OnResRealtimeSise:  -> " + symbol_code + " o: " + open + " h: "  + high + " l: " + low + " c: " + close);
            }
            //Log.d("TAG", "OnResRealtimeSise:  -> " + symbol_code );
            //Log.d("TAG", "OnResRealtimeSise:  -> " + symbol_code + " o: " + open + " h: "  + high + " l: " + low + " c: " + close);
        }
        catch (JSONException e) {
            Log.d("TAG", "OnResRealtimeSise Exception:  -> " + e.getMessage());
        }
    }

    private void OnResRealtimeHoga(JSONObject object) {
        if (object == null)
            return;
        try {
            String symbol_code = object.getString("symbol_code");
            SmSymbolManager symMgr = SmSymbolManager.getInstance();
            SmSymbol sym = symMgr.findSymbol(symbol_code);
            if (sym == null)
                return;

            String time = object.getString("time");

            String domestic_date = object.getString("domestic_date");
            String domestic_time = object.getString("domestic_time");
            int tot_buy_qty = object.getInt("tot_buy_qty");
            int tot_sell_qty = object.getInt("tot_sell_qty");
            int tot_buy_cnt = object.getInt("tot_buy_cnt");
            int tot_sell_cnt = object.getInt("tot_sell_cnt");
            sym.hoga.time = time;
            sym.hoga.domesticDate = domestic_date;
            sym.hoga.domesticTime = domestic_time;
            sym.hoga.totBuyCnt = tot_buy_cnt;
            sym.hoga.totBuyQty = tot_buy_qty;
            sym.hoga.totSellCnt = tot_sell_cnt;
            sym.hoga.totSellQty = tot_sell_qty;

            JSONArray hoga_items = (JSONArray) object.get("hoga_items");
            for (int i=0; i < hoga_items.length(); i++) {
                JSONObject item = hoga_items.getJSONObject(i);
                int buy_price = item.getInt("buy_price");
                int buy_cnt = item.getInt("buy_cnt");
                int buy_qty = item.getInt("buy_qty");
                int sell_price = item.getInt("sell_price");
                int sell_cnt = item.getInt("sell_cnt");
                int sell_qty = item.getInt("sell_qty");
                sym.hoga.hogaItem[i].buyPrice = buy_price;
                sym.hoga.hogaItem[i].buyCnt = buy_cnt;
                sym.hoga.hogaItem[i].buyQty = buy_qty;
                sym.hoga.hogaItem[i].sellPrice = sell_price;
                sym.hoga.hogaItem[i].sellCnt = sell_cnt;
                sym.hoga.hogaItem[i].sellQty = sell_qty;
            }
            Log.d("TAG", "OnResRealtimeHoga:  -> " + symbol_code + time);
        }
        catch (JSONException e) {
            Log.d("TAG", "OnResRealtimeHoga Exception:  -> " + e.getMessage());
        }
    }

    private void OnResChartCycleData(JSONObject object) {
        if (object == null)
            return;
        try {
            String symbol_code = object.getString("symbol_code");
            int chart_type = object.getInt("chart_type");
            int cycle = object.getInt("cycle");
            //int total_count = object.getInt("total_count");
            String data_key = SmChartDataManager.getInstance().makeChartDataKey(symbol_code, chart_type, cycle);
            SmChartData chart_data = SmChartDataManager.getInstance().findChartData(data_key);
            if (chart_data == null) {
                chart_data = SmChartDataManager.getInstance().createChartData(symbol_code, SmChartType.fromInt(chart_type), cycle);
            }

            SmSymbolManager symMgr = SmSymbolManager.getInstance();
            SmSymbol sym = symMgr.findSymbol(symbol_code);
            if (sym == null)
                return;

            String date_time = object.getString("date_time");
            int h = object.getInt("h");
            int l = object.getInt("l");
            int o = object.getInt("o");
            int c = object.getInt("c");
            int v = object.getInt("v");
            if (c == 0 || l == 0 || h == 0 || o == 0)
                return;
            double vh, vl, vo, vc;
            double div = Math.pow(10, sym.decimal);
            vh = (h / div);
            vl = (l / div);
            vo = (o / div);
            vc = (c / div);

            Log.d("TAG", "OnResChartCycleData" + "  code" + symbol_code + " : " + date_time + " : " + h + " : " + l + " : " + o + " : " + c);

            boolean included = chart_data.isIncluded(date_time);
            if (included) {
                SmChartDataItem price = chart_data.updateData(date_time, vo, vh, vl, vc, v);
                if (price == null)
                    return;
                price.cycle = chart_data.cycle;
                price.symbolCode = chart_data.symbolCode;
                price.chartType = chart_data.chartType;
                SmChartDataService chartDataService = SmChartDataService.getInstance();
                chartDataService.onUpdateData(price);
            }
            else {
                SmChartDataItem price = chart_data.add(date_time, vo, vh, vl, vc, v);
                if (price == null)
                    return;
                price.periodic = true;
                price.cycle = chart_data.cycle;
                price.symbolCode = chart_data.symbolCode;
                price.chartType = chart_data.chartType;
                SmChartDataService chartDataService = SmChartDataService.getInstance();
                chartDataService.onNewData(price);
            }
            Log.d("TAG", "OnResChartCycleData:  -> " + symbol_code + date_time);

        }
        catch (JSONException e) {
            Log.d("TAG", "OnResChartCycleData Exception:  -> " + e.getMessage());
        }
    }

    private void onResOrder(JSONObject object) {
        if (object == null)
            return;
        try {
            int request_id = object.getInt("request_id");
            String account_no = object.getString("account_no");
            int order_type = object.getInt("order_type");
            int position_type = object.getInt("position_type");
            int price_type = object.getInt("price_type");
            String symbol_code = object.getString("symbol_code");
            int order_price = object.getInt("order_price");
            int order_no = object.getInt("order_no");
            int order_amount = object.getInt("order_amount");
            int ori_order_no = object.getInt("ori_order_no");
            String filled_date = object.getString("filled_date");
            String filled_time = object.getString("filled_time");
            String order_date = object.getString("order_date");
            String order_time = object.getString("order_time");
            int filled_qty = object.getInt("filled_qty");
            int filled_price = object.getInt("filled_price");
            int order_state = object.getInt("order_state");
            int filled_condition = object.getInt("filled_condition");
            int symbol_decimal = object.getInt("symbol_decimal");
            int remain_qty = object.getInt("remain_qty");
            String strategy_name = object.getString("strategy_name");
            String system_name = object.getString("system_name");
            String fund_name = object.getString("fund_name");
            SmTotalOrderManager totalOrderManager = SmTotalOrderManager.getInstance();
            SmOrder order = totalOrderManager.createOrder(order_no);
            order.requestId = request_id;
            order.accountNo = account_no;
            order.orderType = SmOrderType.valueOf(order_type);
            order.orderState = SmOrderState.valueOf(order_state);
            order.positionType = SmPositionType.valueOf(position_type);
            order.priceType = SmPriceType.valueOf(price_type);
            order.symbolCode = symbol_code;
            order.orderAmount = order_amount;
            order.oriOrderNo = ori_order_no;
            order.orderPrice = order_price;
            order.filledDate = filled_date;
            order.filledTime = filled_time;
            order.orderDate = order_date;
            order.orderTime = order_time;
            order.filledQty = filled_qty;
            order.filledPrice = filled_price;
            order.filledCondition = SmFilledCondition.valueOf(filled_condition);
            order.symbolDecimal = symbol_decimal;
            order.remainQty = remain_qty;
            order.strategyName = strategy_name;
            order.systemName = system_name;
            order.fundName = fund_name;

            totalOrderManager.onOrder(order);

            SmChartDataService chartDataService = SmChartDataService.getInstance();
            chartDataService.onOrder(order);

            Log.d("TAG", "onResOrder:  -> " + symbol_code + account_no + order_state);
        }
        catch (JSONException e) {
            Log.d("TAG", "onResOrder Exception:  -> " + e.getMessage());
        }
    }

    private void onOrderAccepted(JSONObject object) {
        if (object == null)
            return;
        try {
            int request_id = object.getInt("request_id");
            String account_no = object.getString("account_no");
            int order_type = object.getInt("order_type");
            int position_type = object.getInt("position_type");
            int price_type = object.getInt("price_type");
            String symbol_code = object.getString("symbol_code");
            int order_price = object.getInt("order_price");
            int order_no = object.getInt("order_no");
            int order_amount = object.getInt("order_amount");
            int ori_order_no = object.getInt("ori_order_no");
            String filled_date = object.getString("filled_date");
            String filled_time = object.getString("filled_time");
            String order_date = object.getString("order_date");
            String order_time = object.getString("order_time");
            int filled_qty = object.getInt("filled_qty");
            int filled_price = object.getInt("filled_price");
            int order_state = object.getInt("order_state");
            int filled_condition = object.getInt("filled_condition");
            int symbol_decimal = object.getInt("symbol_decimal");
            int remain_qty = object.getInt("remain_qty");
            String strategy_name = object.getString("strategy_name");
            String system_name = object.getString("system_name");
            String fund_name = object.getString("fund_name");
            SmTotalOrderManager totalOrderManager = SmTotalOrderManager.getInstance();
            SmOrder order = totalOrderManager.createOrder(order_no);
            order.requestId = request_id;
            order.accountNo = account_no;
            order.orderType = SmOrderType.valueOf(order_type);
            order.orderState = SmOrderState.valueOf(order_state);
            order.positionType = SmPositionType.valueOf(position_type);
            order.priceType = SmPriceType.valueOf(price_type);
            order.symbolCode = symbol_code;
            order.orderAmount = order_amount;
            order.oriOrderNo = ori_order_no;
            order.orderPrice = order_price;
            order.filledDate = filled_date;
            order.filledTime = filled_time;
            order.orderDate = order_date;
            order.orderTime = order_time;
            order.filledQty = filled_qty;
            order.filledPrice = filled_price;
            order.filledCondition = SmFilledCondition.valueOf(filled_condition);
            order.symbolDecimal = symbol_decimal;
            order.remainQty = remain_qty;
            order.strategyName = strategy_name;
            order.systemName = system_name;
            order.fundName = fund_name;

            totalOrderManager.onOrderAccepted(order);

            SmChartDataService chartDataService = SmChartDataService.getInstance();
            chartDataService.onOrder(order);
        }
        catch (JSONException e) {
            Log.d("TAG", "onResOrder Exception:  -> " + e.getMessage());
        }
    }

    private void onOrderFilled(JSONObject object) {
        if (object == null)
            return;
        try {
            int request_id = object.getInt("request_id");
            String account_no = object.getString("account_no");
            int order_type = object.getInt("order_type");
            int position_type = object.getInt("position_type");
            int price_type = object.getInt("price_type");
            String symbol_code = object.getString("symbol_code");
            int order_price = object.getInt("order_price");
            int order_no = object.getInt("order_no");
            int order_amount = object.getInt("order_amount");
            int ori_order_no = object.getInt("ori_order_no");
            String filled_date = object.getString("filled_date");
            String filled_time = object.getString("filled_time");
            String order_date = object.getString("order_date");
            String order_time = object.getString("order_time");
            int filled_qty = object.getInt("filled_qty");
            int filled_price = object.getInt("filled_price");
            int order_state = object.getInt("order_state");
            int filled_condition = object.getInt("filled_condition");
            int symbol_decimal = object.getInt("symbol_decimal");
            int remain_qty = object.getInt("remain_qty");
            String strategy_name = object.getString("strategy_name");
            String system_name = object.getString("system_name");
            String fund_name = object.getString("fund_name");
            SmTotalOrderManager totalOrderManager = SmTotalOrderManager.getInstance();
            SmOrder order = totalOrderManager.createOrder(order_no);
            order.requestId = request_id;
            order.accountNo = account_no;
            order.orderType = SmOrderType.valueOf(order_type);
            order.orderState = SmOrderState.valueOf(order_state);
            order.positionType = SmPositionType.valueOf(position_type);
            order.priceType = SmPriceType.valueOf(price_type);
            order.symbolCode = symbol_code;
            order.orderAmount = order_amount;
            order.oriOrderNo = ori_order_no;
            order.orderPrice = order_price;
            order.filledDate = filled_date;
            order.filledTime = filled_time;
            order.orderDate = order_date;
            order.orderTime = order_time;
            order.filledQty = filled_qty;
            order.filledPrice = filled_price;
            order.filledCondition = SmFilledCondition.valueOf(filled_condition);
            order.symbolDecimal = symbol_decimal;
            order.remainQty = remain_qty;
            order.strategyName = strategy_name;
            order.systemName = system_name;
            order.fundName = fund_name;

            totalOrderManager.onOrderFilled(order);

            SmChartDataService chartDataService = SmChartDataService.getInstance();
            chartDataService.onOrder(order);
        }
        catch (JSONException e) {
            Log.d("TAG", "onResOrder Exception:  -> " + e.getMessage());
        }
    }

    // 주문이 청산되었을 때 들어 온다.
    private void onResOrderSettled(JSONObject object) {
        if (object == null)
            return;
        try {
            int request_id = object.getInt("request_id");
            String account_no = object.getString("account_no");
            int order_type = object.getInt("order_type");
            int position_type = object.getInt("position_type");
            int price_type = object.getInt("price_type");
            String symbol_code = object.getString("symbol_code");
            int order_price = object.getInt("order_price");
            int order_no = object.getInt("order_no");
            int order_amount = object.getInt("order_amount");
            int ori_order_no = object.getInt("ori_order_no");
            String filled_date = object.getString("filled_date");
            String filled_time = object.getString("filled_time");
            String order_date = object.getString("order_date");
            String order_time = object.getString("order_time");
            int filled_qty = object.getInt("filled_qty");
            int filled_price = object.getInt("filled_price");
            int order_state = object.getInt("order_state");
            int filled_condition = object.getInt("filled_condition");
            int symbol_decimal = object.getInt("symbol_decimal");
            int remain_qty = object.getInt("remain_qty");
            String strategy_name = object.getString("strategy_name");
            String system_name = object.getString("system_name");
            String fund_name = object.getString("fund_name");
            SmTotalOrderManager totalOrderManager = SmTotalOrderManager.getInstance();
            SmOrder order = totalOrderManager.createOrder(order_no);
            order.requestId = request_id;
            order.accountNo = account_no;
            order.orderType = SmOrderType.valueOf(order_type);
            order.orderState = SmOrderState.valueOf(order_state);
            order.positionType = SmPositionType.valueOf(position_type);
            order.priceType = SmPriceType.valueOf(price_type);
            order.symbolCode = symbol_code;
            order.orderAmount = order_amount;
            order.oriOrderNo = ori_order_no;
            order.orderPrice = order_price;
            order.filledDate = filled_date;
            order.filledTime = filled_time;
            order.orderDate = order_date;
            order.orderTime = order_time;
            order.filledQty = filled_qty;
            order.filledPrice = filled_price;
            order.filledCondition = SmFilledCondition.valueOf(filled_condition);
            order.symbolDecimal = symbol_decimal;
            order.remainQty = remain_qty;
            order.strategyName = strategy_name;
            order.systemName = system_name;
            order.fundName = fund_name;

            totalOrderManager.onOrderFilled(order);

            SmChartDataService chartDataService = SmChartDataService.getInstance();
            chartDataService.onOrder(order);
        }
        catch (JSONException e) {
            Log.d("TAG", "onResOrder Exception:  -> " + e.getMessage());
        }
    }

    private void onResPosition(JSONObject object) {
        if (object == null)
            return;
        try {
            String symbol_code = object.getString("symbol_code");
            String fund_name = object.getString("fund_name");
            String account_no = object.getString("account_no");
            int position_type = object.getInt("position_type");
            int open_qty = object.getInt("open_qty");
            double fee = object.getDouble("fee");
            double trade_pl = object.getDouble("trade_pl");
            double avg_price = object.getDouble("avg_price");
            double cur_price = object.getDouble("cur_price");
            double open_pl = object.getDouble("open_pl");
            SmTotalPositionManager totalPositionManager = SmTotalPositionManager.getInstance();
            SmPosition position = totalPositionManager.findAddPosition(account_no, symbol_code);
            position.fundName = fund_name;
            position.positionType = SmPositionType.valueOf(position_type);
            position.openQty = open_qty;
            position.fee = fee;
            position.tradePL = trade_pl;
            position.avgPrice = avg_price;
            position.curPrice = cur_price;
            position.openPL = open_pl;

            SmChartDataService chartDataService = SmChartDataService.getInstance();
            chartDataService.onUpdatePosition(position);
        }
        catch (JSONException e) {
            Log.d("TAG", "onResOrder Exception:  -> " + e.getMessage());
        }
    }

    private void onResOrderError(JSONObject object) {
        if (object == null)
            return;
        try {
            String symbol_code = object.getString("symbol_code");
            String fund_name = object.getString("fund_name");
            String account_no = object.getString("account_no");
            int request_id = object.getInt("request_id");
        }
        catch (JSONException e) {
            Log.d("TAG", "onResOrder Exception:  -> " + e.getMessage());
        }
    }

    public SmGlobal.SmAppState getAppState() {
        return appState;
    }

    public void setAppState(SmGlobal.SmAppState appState) {
        this.appState = appState;
    }
}
