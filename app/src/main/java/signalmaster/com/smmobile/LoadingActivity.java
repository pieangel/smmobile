package signalmaster.com.smmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.chart.SmChartDataRequest;
import signalmaster.com.smmobile.chart.SmChartType;
import signalmaster.com.smmobile.data.SmChartDataManager;
import signalmaster.com.smmobile.global.SmConst;
import signalmaster.com.smmobile.global.SmGlobal;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.network.SmProtocolManager;
import signalmaster.com.smmobile.network.SmServiceManager;
import signalmaster.com.smmobile.network.SmSocketHandler;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.userinfo.SmUserManager;

public class LoadingActivity extends Activity {

    ProgressBar progressBar;
    TextView progressTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressTxt = (TextView)findViewById(R.id.progressTxt);

        createSocket();
        startTimer();
        //startLoading();
    }

    Timer timer;
    TimerTask timerTask;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 10, 1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //get the current timeStamp
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
                        final String strDate = simpleDateFormat.format(calendar.getTime());

                        //show the toast
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), strDate, duration);
                        //toast.show();

                        SmProtocolManager protocolManager = SmProtocolManager.getInstance();
                        if (protocolManager.getAppState() == SmGlobal.SmAppState.MainScreenLoaded) {
                            stoptimertask();
                            loadMainActivity();
                        }
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.SocketConnected) {
                            progressTxt.setText("시장 목록을 내려받고 있습니다.");
                            requestMarketList();
                        }else if (protocolManager.getAppState() == SmGlobal.SmAppState.CategoryListDownloaded) {
                            progressTxt.setText("종목 목록을 내려받고 있습니다.");
                            requestSymbolList();
                        }else if (protocolManager.getAppState() == SmGlobal.SmAppState.SymbolListDownloaded) {
                            progressTxt.setText("시세 정보를 내려받고 있습니다.");
                            requestAllRecentSise();
                        }else if (protocolManager.getAppState() == SmGlobal.SmAppState.ReceivedRecentSise) {
                            registerRealtimeSymbol();
                            SmMarketManager marketManager = SmMarketManager.getInstance();
                            marketManager.getFavoriteList();
                            stoptimertask();
                            progressTxt.setText("완료");
                            loadMainActivity();
                            //finish();
                        }
                        /*
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.SocketConnected) {
                            progressTxt.setText("로그인 중입니다.");
                            requestLogin();
                        }
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.Loggedin) {
                            progressTxt.setText("계좌 목록을 내려받고 있는 중입니다.");
                            requestAccountList();
                        }
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.AccountListDownloaded) {
                            progressTxt.setText("미체결 주문을 내려받고 있는 중입니다.");
                            requestAcceptedList();
                        }
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.AcceptedListDownloaded) {
                            progressTxt.setText("미결제 주문을 내려받고 있는 중입니다.");
                            requestFilledList();
                        }
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.FilledListDownloaded) {
                            progressTxt.setText("포지션 정보를 내려받고 있는 중입니다.");
                            requestPositionList();
                        }
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.PositionListDownloaded) {
                            progressTxt.setText("시장 목록을 내려받고 있습니다.");
                            requestMarketList();
                        }else if (protocolManager.getAppState() == SmGlobal.SmAppState.CategoryListDownloaded) {
                            progressTxt.setText("종목 목록을 내려받고 있습니다.");
                            requestSymbolList();
                        }else if (protocolManager.getAppState() == SmGlobal.SmAppState.SymbolListDownloaded) {
                            progressTxt.setText("시세 정보를 내려받고 있습니다.");
                            requestAllRecentSise();
                        }else if (protocolManager.getAppState() == SmGlobal.SmAppState.ReceivedRecentSise) {
                            registerRealtimeSymbol();
                            SmMarketManager marketManager = SmMarketManager.getInstance();
                            marketManager.getFavoriteList();
                            stoptimertask();
                            progressTxt.setText("완료");
                            loadMainActivity();
                            //finish();
                        }
                        */

                    }
                });
            }
        };
    }

    private boolean req_accepted_list = false;
    private void requestAcceptedList() {
        if (req_accepted_list)
            return;
        req_accepted_list = true;
        String user_id = SmUserManager.getInstance().getDefaultUser().id;
        String account_no = SmAccountManager.getInstance().getDefaultAccountNo();
        SmServiceManager.getInstance().requestAcceptedOrderList(user_id, account_no);
    }

    private boolean req_filled_list = false;
    private void requestFilledList() {
        if (req_filled_list)
            return;
        req_filled_list = true;
        String user_id = SmUserManager.getInstance().getDefaultUser().id;
        String account_no = SmAccountManager.getInstance().getDefaultAccountNo();
        SmServiceManager.getInstance().requestFilledOrderList(user_id, account_no);
    }

    private boolean req_position_list = false;
    private void requestPositionList() {
        if (req_position_list)
            return;
        req_position_list = true;
        String user_id = SmUserManager.getInstance().getDefaultUser().id;
        String account_no = SmAccountManager.getInstance().getDefaultAccountNo();
        SmServiceManager.getInstance().requestPositionList(user_id, account_no);
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void createSocket() {
        SmSocketHandler socketHandler = SmSocketHandler.getInstance();
        socketHandler.connect();
    }

    private boolean req_login = false;
    public void requestLogin() {
        if (req_login)
            return;
        req_login = true;
        SmServiceManager.getInstance().reqestLogin();
    }

    public void requestRegisterUser() {
        SmServiceManager serviceManager = SmServiceManager.getInstance();
        String user_id = "pieangel@naver.com";
        String pwd = "orion1";
        serviceManager.requestRegisterUser(user_id, pwd);
    }

    private boolean req_account_list = false;
    public void requestAccountList() {
        if (req_account_list)
            return;
        req_account_list = true;
        String user_id =  SmUserManager.getInstance().getDefaultUser().id;
        SmServiceManager.getInstance().requestAccountList(user_id);
    }
    private boolean req_market_list = false;
    public void requestMarketList() {
        if (req_market_list)
            return;
        req_market_list = true;

        SmServiceManager serviceManager = SmServiceManager.getInstance();
        serviceManager.requestMarketList();
    }

    private boolean req_symbol_list = false;

    public void requestSymbolList() {
        if (req_symbol_list)
            return;
        req_symbol_list = true;
        SmServiceManager serviceManager = SmServiceManager.getInstance();
        serviceManager.requestSymbolListByCategory();
    }

    private boolean req_main_chart_data = false;
    public void requestMainChartData() {
        if (req_main_chart_data)
            return;
        req_main_chart_data = true;
        initChartData();
        SmProtocolManager protocolManager = SmProtocolManager.getInstance();
        protocolManager.setAppState(SmGlobal.SmAppState.ReceivedMainchartData);
    }

    public void initChartData() {
        SmServiceManager svcMgr = SmServiceManager.getInstance();
        if (!svcMgr.isLoggedIn())
            return;

        SmMarketManager marketManager = SmMarketManager.getInstance();
        SmSymbol symbol = marketManager.getRecentMonthSymbol("지수", "HS");
        SmChartDataRequest req = new SmChartDataRequest();
        if (symbol != null)
            req.symbolCode = symbol.code;
        else
            req.symbolCode = "HSIN19";

        req.chartType = SmChartType.MIN;
        req.cycle = 1;
        req.count = SmConst.ChartDataSize;
        SmChartDataManager chartDataManager = SmChartDataManager.getInstance();
        String dataKey = chartDataManager.makeChartDataKey(req.symbolCode, SmChartType.MIN.ordinal(), req.cycle);
        chartDataManager.createChartData(req.symbolCode, req.chartType, req.cycle);
        //svcMgr.registerRealtimeSymbol(req.symbolCode);
        //svcMgr.requestChartData(req);
    }

    public void requestAllRecentSise() {
        SmServiceManager serviceManager = SmServiceManager.getInstance();
        serviceManager.requestAllRecentMonthSise();
        SmProtocolManager protocolManager = SmProtocolManager.getInstance();
        protocolManager.setAppState(SmGlobal.SmAppState.ReceivedRecentSise);
    }

    public void registerRealtimeSymbol() {
        SmServiceManager serviceManager = SmServiceManager.getInstance();
        serviceManager.registerAllRecentRealtimeSise();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

}
