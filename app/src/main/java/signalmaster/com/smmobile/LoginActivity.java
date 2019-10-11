package signalmaster.com.smmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.account.SmAccount;
import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.global.SmGlobal;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.network.SmProtocolManager;
import signalmaster.com.smmobile.network.SmServiceManager;
import signalmaster.com.smmobile.userinfo.SmUserManager;

public class LoginActivity extends AppCompatActivity {

    ImageView backImg;
    Button signUpBtn,loginBtn;

    ProgressBar progressBar;
    TextView progressTxt;

    NavigationView navigationView;

    LoginActivity self = this;

    EditText idText;
    EditText pwdText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        progressBar = findViewById(R.id.progressBar);
        progressTxt = findViewById(R.id.progressTxt);

        backImg = findViewById(R.id.backImg);
        signUpBtn = findViewById(R.id.signUpBtn);
        loginBtn = findViewById(R.id.loginBtn);

        progressBar.setVisibility(View.GONE);
        progressTxt.setVisibility(View.GONE);

        idText = findViewById(R.id.emailInput);
        pwdText = findViewById(R.id.passwordInput);

        SharedPreferences s_pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.user_id = s_pref.getString("user_id", "");
        this.user_pwd = s_pref.getString("user_password", "");
        idText.setText(this.user_id);
        pwdText.setText(this.user_pwd);


        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmArgManager.getInstance().registerToMap("sign_up", "login_activity", self);
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = idText.getText().toString();
                user_pwd = pwdText.getText().toString();
                SharedPreferences s_pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit=s_pref.edit();
                edit.putString("user_id", user_id);
                edit.putString("user_password", user_pwd);
                edit.commit();
                SmUserManager.getInstance().addUserInfo(user_id, user_pwd);
                SmProtocolManager.getInstance().setAppState(SmGlobal.SmAppState.MainScreenLoaded);
                progressBar.setVisibility(View.VISIBLE);
                progressTxt.setVisibility(View.VISIBLE);
                startTimer();

            }
        });
    }

    public void onRegisteredUser(final String user_id, final String pwd) {
        SharedPreferences s_pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit=s_pref.edit();
        edit.putString("user_id", user_id);
        edit.putString("user_password", user_pwd);
        edit.commit();

        this.user_id = user_id;
        this.user_pwd = pwd;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                idText.setText(user_id);
                pwdText.setText(pwd);
            }
        });
    }

    Timer timer;
    TimerTask timerTask;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    private String user_id = "";

    private String user_pwd = "";

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //get the current timeStamp
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a", Locale.KOREA);
                        final String strDate = simpleDateFormat.format(calendar.getTime());

                        //show the toast
                        int duration = Toast.LENGTH_SHORT;
                        //Toast toast = Toast.makeText(getApplicationContext(), strDate, duration);
                        //toast.show();

                        SmProtocolManager protocolManager = SmProtocolManager.getInstance();
                        if (protocolManager.getAppState() == SmGlobal.SmAppState.MainScreenLoaded) {
                            progressTxt.setText("로그인 중입니다.");
                            requestLogin(user_id, user_pwd);
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
                            stoptimertask();
                            loginInfo();
                            finish();
                        }
                    }
                });
            }
        };
    }

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

    private boolean req_login = false;
    public void requestLogin(String id, String pwd) {
        if (req_login)
            return;
        req_login = true;
        SmServiceManager.getInstance().reqestLogin(id, pwd);
    }

    private boolean req_account_list = false;
    public void requestAccountList() {
        if (req_account_list)
            return;
        req_account_list = true;
        String user_id =  SmUserManager.getInstance().getDefaultUser().id;
        SmServiceManager.getInstance().requestAccountList(user_id);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void loginInfo(){
        SmArgManager smArgManager = SmArgManager.getInstance();
        navigationView = (NavigationView)smArgManager.getVal("nav_header","navigationView");

        if(navigationView != null) {
            SmAccountManager smAccountManager = SmAccountManager.getInstance();
            ArrayList<SmAccount> acList = smAccountManager.getAccountList();

            View nav_header_view = navigationView.getHeaderView(0);
            TextView nav_email = nav_header_view.findViewById(R.id.nav_email);
            TextView nav_total = nav_header_view.findViewById(R.id.nav_total);
            TextView nav_accountNo = nav_header_view.findViewById(R.id.nav_accountNo);

            nav_email.setText(user_id);
            if(acList.size() > 0) {
                nav_total.setText(Double.toString(acList.get(0).total_pl));
                nav_accountNo.setText(acList.get(0).accountNo);
            }
        }

    }

}
