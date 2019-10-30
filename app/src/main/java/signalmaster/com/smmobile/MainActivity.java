package signalmaster.com.smmobile;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.scichart.extensions.builders.SciChartBuilder;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import signalmaster.com.smmobile.Adapter.MainbarAdapter;
import signalmaster.com.smmobile.OrderUI.PositionListActivity;
import signalmaster.com.smmobile.OrderUI.TradeRecordActivity;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.account.AccountActivity;
import signalmaster.com.smmobile.crobo.SmCroboFragment;
import signalmaster.com.smmobile.crobo.SmCroboLevelFragment;
import signalmaster.com.smmobile.crobo.SmCroboQuestionFragment;
import signalmaster.com.smmobile.expert.SmExpertFragment;
import signalmaster.com.smmobile.favorite.SmFavoriteFragment;
import signalmaster.com.smmobile.fund.SmFundFragment;
import signalmaster.com.smmobile.global.SmConst;
import signalmaster.com.smmobile.global.SmGlobal;
import signalmaster.com.smmobile.login.LoginActivity;
import signalmaster.com.smmobile.market.SmCategory;
import signalmaster.com.smmobile.market.SmMarket;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.market.SmMarketReader;
import signalmaster.com.smmobile.mock.SmPrChartFragment;
import signalmaster.com.smmobile.network.SmProtocolManager;
import signalmaster.com.smmobile.network.SmServiceManager;
import signalmaster.com.smmobile.network.SmSocketHandler;
import signalmaster.com.smmobile.order.SmTotalOrderManager;
import signalmaster.com.smmobile.popup.NumberPickerDialog;
import signalmaster.com.smmobile.portfolio.SmPortfolioFragment;
import signalmaster.com.smmobile.sise.SubListAdapter;
import signalmaster.com.smmobile.sise.SubbarAdapter;
import signalmaster.com.smmobile.autoorder.SmAutoFragment;
import signalmaster.com.smmobile.sise.SmCurrentFragment;
import signalmaster.com.smmobile.symbol.SmSymbol;


public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    TextView feesTxt, pushTxt, orderSymSelectTxt;
    TextView input_order_amount_text, push_order_amount_text, input_abroad_fee_text, push_abroad_fee_text, input_domestic_fee_text, push_domestic_fee_text;
    Button symSelectBtn, addFavoriteBtn, loginBtn, symExpertSelectBtn;
    ImageView indexSelectImg, chartSelectImg, lineSelectImg, mockMenuImg, recentImg, recentBackImg, optionListImg;

    LinearLayout siseTitle, favoriteTitle, mockTitle, autoOrderTitle, croboTitle, portTitle, productionTitle, expertTitle;
    LinearLayout feesLayout, bottom_icon_layout;

    Toolbar toolbar;

    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;

    BubbleSeekBar bubbleSeekBar;

    private BackPressCloseHandler backPressCloseHandler;

    boolean isPageOpen = false;
    Animation translateLeftAnim;
    Animation translateRightAnim;
    LinearLayout slidingPanel, recentTitle;
    ImageView settingImg;


    SmCurrentFragment smCurrentFragment;
    SmFavoriteFragment smFavoriteFragment;
    SmPrChartFragment smPrChartFragment;
    SmAutoFragment smAutoFragment;
    SmCroboFragment smCroboFragment;
    SmPortfolioFragment smPortfolioFragment;
    SmFundFragment smFundFragment;
    SmExpertFragment smExpertFragment;

    //SmCroboStartFragment smCroboStartFragment;
    SmCroboQuestionFragment smCroboQuestionFragment;
    //SmCroboResultFragment smCroboResultFragment;
    SmCroboLevelFragment smCroboLevelFragment;

    public static Context context;
    private HashMap<Integer, Fragment> _mainFragmentMap = new HashMap<>();
    private MainbarAdapter _mainbarAdapter = null;
    private SubbarAdapter _subbarAdapter = null;
    private SubListAdapter _subListAdapter = null;

    //add
    SmMarket smMarket = new SmMarket();
    SmMarketManager smMarketManager = SmMarketManager.getInstance();
    ArrayList<SmMarket> _marketList = (smMarketManager.get_marketList());
    SmMainbarList mainbarArrayList = new SmMainbarList();
    ArrayList<String> _mainList = (mainbarArrayList.get_mainList());

    //right option recycler view add
    private MainRightOptionAdapter rightOptionAdapter = new MainRightOptionAdapter();
    private RecentAdapter recentAdapter = new RecentAdapter();
    RecyclerView recyclerView, recyclerView_right, recentRecyclerView;
    RecyclerViewList recyclerViewList = new RecyclerViewList();
    ArrayList<String> defaultOptionList = recyclerViewList.getDefaultOptionList();
    ArrayList<String> favoriteOptionList = recyclerViewList.getFavoriteOptionList();
    ArrayList<String> mockOptionList = recyclerViewList.getMockOptionList();
    ArrayList<String> portfolioOptionList = recyclerViewList.getRightPortList();
    private boolean stateHide = false;


    private SmGlobal.SmAppState appState = SmGlobal.SmAppState.Init;

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
                        if (protocolManager.getAppState() == SmGlobal.SmAppState.Init)
                            requestLogin();
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.Loggedin)
                            requestMarketList();
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.CategoryListDownloaded)
                            requestSymbolList();
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.SymbolListDownloaded)
                            requestMainChartData();
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.ReceivedMainchartData)
                            getMain();
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.MainScreenLoaded)
                            requestAllRecentSise();
                        else if (protocolManager.getAppState() == SmGlobal.SmAppState.ReceivedRecentSise) {
                            registerRealtimeSymbol();
                            SmMarketManager marketManager = SmMarketManager.getInstance();
                            marketManager.getFavoriteList();
                            stoptimertask();
                        }

                    }
                });
            }
        };
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

    public void initChartData() {

    }


    private SmOptionFragment _optionFragment = new SmOptionFragment();
    HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();
    private int selectedPosition = 0;

    /*protected  void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("selectedPosition",selectedPosition);
    }*/

    /*@Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState){
        super.onSaveInstanceState(outState, outPersistentState);
    }*/

    /*@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedPosition = savedInstanceState.getInt("selectedPosition");
    }*/

   /* @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }*/

    public void setSelectPosition() {

        SmArgManager smArgManager = SmArgManager.getInstance();
        selectedPosition = (int)smArgManager.getVal("positionBack","position");
        SharedPreferences sf = getSharedPreferences("selectedBottom", MODE_PRIVATE);
        selectedPosition = sf.getInt("selectedPosition", 0);
        //smArgManager.registerToMap("postionBack", "position", selectedPosition);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*SharedPreferences sf = getSharedPreferences("selectedBottom", MODE_PRIVATE);
        selectedPosition = sf.getInt("selectedPosition", 0);*/

        backPressCloseHandler = new BackPressCloseHandler(this);

        //add
        /*Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);*/

        SmProtocolManager protocolManager = SmProtocolManager.getInstance();
        protocolManager.setAppState(SmGlobal.SmAppState.Init);

        if (savedInstanceState == null) {
            smCurrentFragment = SmCurrentFragment.newInstance();
            smFavoriteFragment = SmFavoriteFragment.newInstance();
            smPrChartFragment = SmPrChartFragment.newInstance();
            smAutoFragment = SmAutoFragment.newInstance();
            smCroboFragment = SmCroboFragment.newInstance();
            smPortfolioFragment = SmPortfolioFragment.newInstance();
            smFundFragment = SmFundFragment.newInstance();
            smExpertFragment = SmExpertFragment.newInstance();

            //smCroboStartFragment = SmCroboStartFragment.newInstance();
            //smCroboQuestionFragment = SmCroboQuestionFragment.newInstance();
            //smCroboResultFragment = SmCroboResultFragment.newInstance();
            //smCroboLevelFragment = SmCroboLevelFragment.newInstance();

            fragmentHashMap.put(0, smCurrentFragment);
            fragmentHashMap.put(1, smFavoriteFragment);
            fragmentHashMap.put(2, smPrChartFragment);
            fragmentHashMap.put(3, smAutoFragment);
            fragmentHashMap.put(4, smCroboFragment);
            fragmentHashMap.put(5, smPortfolioFragment);
            fragmentHashMap.put(6, smFundFragment);
            fragmentHashMap.put(7, smExpertFragment);

        }


        context = this;

        SciChartBuilder.init(this);
        setUpSciChartLicense();

        getMain();

        //startTimer();
        /*lvNavList = (ListView) findViewById(R.id.lv_activity_main_nav_list);

        lvNavList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.open, R.string.close);
        setTitle("");
        dlDrawer.setDrawerListener(dtToggle);

        setSupportActionBar(toolbar);

        //액션바 = 모양
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_gray_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        SmArgManager smArgManager = SmArgManager.getInstance();
        smArgManager.registerToMap("nav_header","navigationView",navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                dlDrawer.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_trade:
                        _mainbarAdapter.selectedPosition = 3;
                        _mainbarAdapter.notifyDataSetChanged();
                        onClickItem(3);
                        break;

                    case R.id.navigation_item_record:
                        //거래기록
                        Intent intent1 = new Intent(MainActivity.this, TradeRecordActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.navigation_item_account:
                        //계좌
                        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item_report:
                        //리포트
                        Intent intent2 = new Intent(MainActivity.this, IncomeChartActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });




        /*lvNavList_right = (ListView)findViewById(R.id.lvNavList_right);
        //현재가 옵션 초기 세팅
        lvNavList_right.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,optionItem));
        lvNavList_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:

                }
            }
        });*/



        recentTitle = (LinearLayout) findViewById(R.id.recentTitle);
        recentRecyclerView = (RecyclerView) findViewById(R.id.recentRecyclerView);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recentAdapter = new RecentAdapter();
        recentRecyclerView.setAdapter(recentAdapter);
        recentAdapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            // 거래 기록을 눌렀을 때 이벤트 처리
            @Override
            public void onItemClick(View v, int position) {
                int i = 0;
                i = i + 1;
            }
        });


        slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
        slidingPanel.bringToFront();
        settingImg = (ImageView) findViewById(R.id.settingImg);

        settingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPageOpen) {
                    slidingPanel.bringToFront();
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                } else {
                    slidingPanel.bringToFront();
                    slidingPanel.startAnimation(translateLeftAnim);
                    slidingPanel.setVisibility(View.VISIBLE);
                }
            }
        });


        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        translateLeftAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                settingImg.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isPageOpen) {
                    slidingPanel.setVisibility(View.INVISIBLE);
                    //button.setText("열기");
                    isPageOpen = false;
                } else {
                    // button.setText("닫기");
                    isPageOpen = true;
                }
                settingImg.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

        translateRightAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                settingImg.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isPageOpen) {
                    slidingPanel.setVisibility(View.INVISIBLE);
                    // button.setText("열기");
                    isPageOpen = false;
                } else {
                    // button.setText("닫기");
                    isPageOpen = true;
                }
                settingImg.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        feesLayout = (LinearLayout) findViewById(R.id.feesLayout);
        bottom_icon_layout = (LinearLayout) findViewById(R.id.bottom_icon_layout);

        //error
        /*loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });*/

        View header_view = navigationView.getHeaderView(0);
        loginBtn = (Button) header_view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        /*onClickItem(selectedPosition);
        _mainbarAdapter.notifyDataSetChanged();*/

        //onStop 에서 저장한 값 불러오기
        SharedPreferences sf = getSharedPreferences("selectedBottom", MODE_PRIVATE);
        selectedPosition = sf.getInt("selectedPosition", 0);

        //어뎁터에 저장되어있는 값 삽입
        _mainbarAdapter.selectedPosition = selectedPosition;
        //onClickItemBackUp(selectedPosition);
        onClickItem(selectedPosition);

        //rightOption 위치 값
        recyclerView_right = (RecyclerView) findViewById(R.id.recyclerView_right);
        recyclerView_right.setLayoutManager(new LinearLayoutManager(this));

        /*ArrayList<String> list = new ArrayList<>();
        changeRightList(list,selectedPosition);
        rightOptionAdapter = new MainRightOptionAdapter(list);
        recyclerView_right.setAdapter(rightOptionAdapter);*/

        recyclerView_right = (RecyclerView) findViewById(R.id.recyclerView_right);
        recyclerView_right.setLayoutManager(new LinearLayoutManager(this));
        changeOptionType(selectedPosition);

        /*rightOptionAdapter.setOnItemClickListener(new MainRightOptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });*/

    }
    //onCreate End


    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.loginBtn: {
                //do somthing
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);

                break;
            }
        }
        //close navigation drawer
        //dlDrawer.closeDrawer(GravityCompat.START);
        dlDrawer.closeDrawers();
        return true;
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //툴바 생성
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    boolean drawerState = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                if (drawerState == false) {
                    dlDrawer.openDrawer(GravityCompat.START);
                    drawerState = true;
                } else {
                    dlDrawer.closeDrawers();
                    drawerState = false;
                }
                return true;

            /*case R.id.action_settings:
                return true;*/
        }
        /*if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("selectedBottom", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("selectedPosition", selectedPosition);
        editor.apply();
    }

    @Override
    protected  void onStart(){
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    //홈키 눌렀을때 작동 -> 그 다음 onPause 로 이동
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }


    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /*private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }*/


    protected void displaySmCurrentFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (smCurrentFragment.isAdded()) {
            ft.show(smCurrentFragment);
        } else {
            ft.add(R.id.mainListContainer, smCurrentFragment);
        }
        if (smFavoriteFragment.isAdded()) {
            ft.hide(smFavoriteFragment);
        }
        if (smPrChartFragment.isAdded()) {
            ft.hide(smPrChartFragment);
        }
        if (smAutoFragment.isAdded()) {
            ft.hide(smAutoFragment);
        }
        if (smCroboFragment.isAdded()) {
            ft.hide(smCroboFragment);
        }
        if (smPortfolioFragment.isAdded()) {
            ft.hide(smPortfolioFragment);
        }
        if (smFundFragment.isAdded()) {
            ft.hide(smFundFragment);
        }
        if (smExpertFragment.isAdded()) {
            ft.hide(smExpertFragment);
        }
        ft.commit();

    }

    protected void displaySmFavoriteFragment() {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        if (smFavoriteFragment.isAdded()) {
            ft.show(smFavoriteFragment);
        } else {
            ft.add(R.id.mainListContainer, smFavoriteFragment);
        }
        if (smCurrentFragment.isAdded()) {
            ft.hide(smCurrentFragment);
        }
        if (smPrChartFragment.isAdded()) {
            ft.hide(smPrChartFragment);
        }
        if (smAutoFragment.isAdded()) {
            ft.hide(smAutoFragment);
        }
        if (smCroboFragment.isAdded()) {
            ft.hide(smCroboFragment);
        }
        if (smPortfolioFragment.isAdded()) {
            ft.hide(smPortfolioFragment);
        }
        if (smFundFragment.isAdded()) {
            ft.hide(smFundFragment);
        }
        if (smExpertFragment.isAdded()) {
            ft.hide(smExpertFragment);
        }
        ft.commit();
    }

    protected void displaySmPrChartFragment() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pr_port_chart, null);
        FrameLayout mainChartContainer = (FrameLayout) view.findViewById(R.id.mainChartContainer);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (smPrChartFragment.isAdded()) {
            ft.show(smPrChartFragment);
            // 여기서 저장된 심볼로 바꾸어 준다.
            SmSymbol smSymbol = SmTotalOrderManager.getInstance().getOrderSymbol(getApplicationContext());
            symSelectBtn = (Button) findViewById(R.id.symSelectBtn);
            symSelectBtn.setText(smSymbol.seriesNmKr);
            smPrChartFragment.onChangeSymbol(smSymbol);
        } else {
            ft.add(R.id.mainListContainer, smPrChartFragment);
            // 여기서 저장된 심볼로 바꾸어 준다.
            SmSymbol smSymbol = SmTotalOrderManager.getInstance().getOrderSymbol(getApplicationContext());
            symSelectBtn = (Button) findViewById(R.id.symSelectBtn);
            symSelectBtn.setText(smSymbol.seriesNmKr);
            smPrChartFragment.onChangeSymbol(smSymbol);
        }
        if (smCurrentFragment.isAdded()) {
            ft.hide(smCurrentFragment);
        }
        if (smFavoriteFragment.isAdded()) {
            ft.hide(smFavoriteFragment);
        }
        if (smAutoFragment.isAdded()) {
            ft.hide(smAutoFragment);
        }
        if (smCroboFragment.isAdded()) {
            ft.hide(smCroboFragment);
        }
        if (smPortfolioFragment.isAdded()) {
            ft.hide(smPortfolioFragment);
        }
        if (smFundFragment.isAdded()) {
            ft.hide(smFundFragment);
        }
        if (smExpertFragment.isAdded()) {
            ft.remove(smExpertFragment);
        }
        ft.commit();
    }

    protected void displaySmAutoFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (smAutoFragment.isAdded()) {
            ft.show(smAutoFragment);
            // 여기서 심볼을 바꾸어 준다.
            SmSymbol smSymbol = SmTotalOrderManager.getInstance().getOrderSymbol(getApplicationContext());
            orderSymSelectTxt = (TextView) findViewById(R.id.orderSymSelectTxt);
            orderSymSelectTxt.setText(smSymbol.seriesNmKr);
            smAutoFragment.setSymbol(smSymbol);
        } else {
            ft.add(R.id.mainListContainer, smAutoFragment);
            // 여기서 심볼을 바꾸어 준다.
            SmSymbol smSymbol = SmTotalOrderManager.getInstance().getOrderSymbol(getApplicationContext());
            orderSymSelectTxt = (TextView) findViewById(R.id.orderSymSelectTxt);
            orderSymSelectTxt.setText(smSymbol.seriesNmKr);
            smAutoFragment.setSymbol(smSymbol);
        }
        if (smCurrentFragment.isAdded()) {
            ft.hide(smCurrentFragment);
        }
        if (smFavoriteFragment.isAdded()) {
            ft.hide(smFavoriteFragment);
        }
        if (smPrChartFragment.isAdded()) {
            ft.hide(smPrChartFragment);
        }
        if (smCroboFragment.isAdded()) {
            ft.hide(smCroboFragment);
        }
        if (smPortfolioFragment.isAdded()) {
            ft.hide(smPortfolioFragment);
        }
        if (smFundFragment.isAdded()) {
            ft.hide(smFundFragment);
        }
        if (smExpertFragment.isAdded()) {
            ft.hide(smExpertFragment);
        }
        ft.commit();
    }

    protected void displaySmCroboFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (smCroboFragment.isAdded()) {
            ft.show(smCroboFragment);
        } else {
            //smCroboFragment add 되어있지 않아서 계속 add 시킨다 .
            ft.add(R.id.mainListContainer, smCroboFragment);
        }
        if (smCurrentFragment.isAdded()) {
            ft.hide(smCurrentFragment);
        }
        if (smFavoriteFragment.isAdded()) {
            ft.hide(smFavoriteFragment);
        }
        if (smPrChartFragment.isAdded()) {
            ft.hide(smPrChartFragment);
        }
        if (smAutoFragment.isAdded()) {
            ft.hide(smAutoFragment);
        }
        if (smPortfolioFragment.isAdded()) {
            ft.hide(smPortfolioFragment);
        }
        if (smFundFragment.isAdded()) {
            ft.hide(smFundFragment);
        }
        if (smExpertFragment.isAdded()) {
            ft.hide(smExpertFragment);
        }
        ft.commit();
    }

    protected void displaySmPortfolioFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (smPortfolioFragment.isAdded()) {
            ft.show(smPortfolioFragment);
        } else {
            ft.add(R.id.mainListContainer, smPortfolioFragment);
        }
        if (smCurrentFragment.isAdded()) {
            ft.hide(smCurrentFragment);
        }
        if (smFavoriteFragment.isAdded()) {
            ft.hide(smFavoriteFragment);
        }
        if (smPrChartFragment.isAdded()) {
            ft.hide(smPrChartFragment);
        }
        if (smAutoFragment.isAdded()) {
            ft.hide(smAutoFragment);
        }
        if (smCroboFragment.isAdded()) {
            ft.hide(smCroboFragment);
        }
        if (smFundFragment.isAdded()) {
            ft.hide(smFundFragment);
        }
        if (smExpertFragment.isAdded()) {
            ft.hide(smExpertFragment);
        }
        ft.commit();
    }

    protected void displaySmFundFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (smFundFragment.isAdded()) {
            ft.show(smFundFragment);
        } else {
            ft.add(R.id.mainListContainer, smFundFragment);
        }
        if (smCurrentFragment.isAdded()) {
            ft.hide(smCurrentFragment);
        }
        if (smFavoriteFragment.isAdded()) {
            ft.hide(smFavoriteFragment);
        }
        if (smPrChartFragment.isAdded()) {
            ft.hide(smPrChartFragment);
        }
        if (smAutoFragment.isAdded()) {
            ft.hide(smAutoFragment);
        }
        if (smCroboFragment.isAdded()) {
            ft.hide(smCroboFragment);
        }
        if (smPortfolioFragment.isAdded()) {
            ft.hide(smPortfolioFragment);
        }
        if (smExpertFragment.isAdded()) {
            ft.hide(smExpertFragment);
        }
        ft.commit();
    }

    protected void displaySmExpertFragment() {

        /*LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sm_expert_fragment,null);
        FrameLayout mainChartContainer = (FrameLayout)view.findViewById(R.id.mainChartContainer);*/

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pr_port_chart, null);
        FrameLayout mainChartContainer = (FrameLayout) view.findViewById(R.id.mainChartContainer);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (smExpertFragment.isAdded()) {
            ft.show(smExpertFragment);
        } else {
            ft.add(R.id.mainListContainer, smExpertFragment);
        }
        if (smCurrentFragment.isAdded()) {
            ft.hide(smCurrentFragment);
        }
        if (smFavoriteFragment.isAdded()) {
            ft.hide(smFavoriteFragment);
        }
        if (smPrChartFragment.isAdded()) {
            //smPrChartFragment.hideChartFragment();
            ft.remove(smPrChartFragment);
        }
        if (smAutoFragment.isAdded()) {
            ft.hide(smAutoFragment);
        }
        if (smCroboFragment.isAdded()) {
            ft.hide(smCroboFragment);
        }
        if (smPortfolioFragment.isAdded()) {
            ft.hide(smPortfolioFragment);
        }
        if (smFundFragment.isAdded()) {
            ft.hide(smFundFragment);
        }
        ft.commit();
    }


    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        Toast.makeText(this,
                "selected number " + numberPicker.getValue(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        createSocket();
    }

    public void createSocket() {
        SmSocketHandler socketHandler = SmSocketHandler.getInstance();
        socketHandler.connect();
    }

    private boolean requestMarketFile = false;

    public void downloadMarketFile() {
        if (!requestMarketFile) {
            SmMarketReader marketReader = SmMarketReader.getInstance();
            marketReader.downloadMKFile(this);
            requestMarketFile = true;
        } else {
            SmMarketReader marketReader = SmMarketReader.getInstance();
            if (marketReader.isDownloadComplete())
                appState = SmGlobal.SmAppState.MarketDownloaded;
        }
    }

    public void makeMarketList() {

        SmMarketReader smMarketReader = SmMarketReader.getInstance();
        try {
            smMarketReader.readMarketFile(this);
            smMarketReader.readPmFile(this);
            smMarketReader.readJmFile(this);
            SmMarketManager mrktMgr = SmMarketManager.getInstance();
            int i = 0;
            appState = SmGlobal.SmAppState.MarketListMade;
        } catch (Exception e) {
            e.printStackTrace();
            appState = SmGlobal.SmAppState.MarketListMade;
        }
    }

    public void requestLogin() {
        SmServiceManager svcMgr = SmServiceManager.getInstance();
        if (!svcMgr.isLoggedIn())
            svcMgr.reqestLogin();
    }

    public void requestMainChartData() {
        initChartData();
        SmProtocolManager protocolManager = SmProtocolManager.getInstance();
        protocolManager.setAppState(SmGlobal.SmAppState.ReceivedMainchartData);
    }

    private boolean marketListRequested = false;

    public void requestMarketList() {
        if (marketListRequested)
            return;

        SmServiceManager serviceManager = SmServiceManager.getInstance();
        serviceManager.requestMarketList();
        marketListRequested = true;
    }

    private boolean symbolListRequested = false;

    public void requestSymbolList() {
        if (symbolListRequested)
            return;

        SmServiceManager serviceManager = SmServiceManager.getInstance();
        serviceManager.requestSymbolListByCategory();
        symbolListRequested = true;
    }

    public void showNumberPicker(View view) {
        NumberPickerDialog newFragment = new NumberPickerDialog();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "time picker");
    }


    private void getMain() {

        //Title Add..
        siseTitle = (LinearLayout) findViewById(R.id.siseTitle);
        favoriteTitle = (LinearLayout) findViewById(R.id.favoriteTitle);
        mockTitle = (LinearLayout) findViewById(R.id.mockTitle);
        autoOrderTitle = (LinearLayout) findViewById(R.id.autoOrderTitle);
        croboTitle = (LinearLayout) findViewById(R.id.croboTitle);
        portTitle = (LinearLayout) findViewById(R.id.portTitle);
        productionTitle = (LinearLayout) findViewById(R.id.productionTitle);
        expertTitle = (LinearLayout) findViewById(R.id.expertTitle);

        recyclerView = findViewById(R.id.SmMainMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        _mainbarAdapter = new MainbarAdapter(_mainList);
        recyclerView.setAdapter(_mainbarAdapter);


        SmProtocolManager protocolManager = SmProtocolManager.getInstance();
        protocolManager.setAppState(SmGlobal.SmAppState.MainScreenLoaded);

        if (_marketList.size() > 0) {
            SmMarket mrkt = _marketList.get(0);

            ArrayList<SmCategory> catList = mrkt.get_categoryList();
            for (SmCategory cat : catList) {
                ArrayList<SmSymbol> symList = cat.get_symbolList();
                if (symList.size() > 0) {
                    SmSymbol sym = symList.get(0);
                    String name = sym.seriesNmKr;
                    name = name + "ss";
                }
            }
            //displaySmCurrentFragment();
        }

        //초기화면 설정
        /*Fragment smCurrentFragment = new SmCurrentFragment();
        this.setDefaultFragment(smCurrentFragment);*/
        /*SmCurrentFragment smCurrentFragment = new SmCurrentFragment();
        _mainFragmentMap.put(0, smCurrentFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainListContainer, smCurrentFragment);
        //fragmentTransaction.replace(R.id.SmSubMenu,smSubbarFragment);
        fragmentTransaction.commit();*/
        SmSymbol smSymbol = SmTotalOrderManager.getInstance().getOrderSymbol(getApplicationContext());
        symSelectBtn = (Button) findViewById(R.id.symSelectBtn);
        symSelectBtn.setText(smSymbol.seriesNmKr);
        symSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition == 2) { // 모의 매매 종목 선택
                    SmPrChartFragment fragment = (SmPrChartFragment) fragmentHashMap.get(selectedPosition);
                    fragment.changeSymbol(symSelectBtn);
                }
            }
        });

        orderSymSelectTxt = (TextView) findViewById(R.id.orderSymSelectTxt);
        orderSymSelectTxt.setText(smSymbol.seriesNmKr);
        orderSymSelectTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition == 3) { // 주문 종목 선택
                    SmAutoFragment fragment = (SmAutoFragment) fragmentHashMap.get(selectedPosition);
                    fragment.changeSymbol(orderSymSelectTxt);
                }
            }
        });

        chartSelectImg = (ImageView) findViewById(R.id.chartSelectImg);
        chartSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 여기서 차트 타입 선택 창을 띄운다.
                if (selectedPosition == 2) {
                    SmPrChartFragment fragment = (SmPrChartFragment) fragmentHashMap.get(selectedPosition);
                    fragment.changeChartType();
                }

            }
        });

        indexSelectImg = (ImageView) findViewById(R.id.indexSelectImg);
        indexSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition == 2) {
                    SmPrChartFragment fragment = (SmPrChartFragment) fragmentHashMap.get(selectedPosition);
                    fragment.showIndicator();
                }
            }
        });

        lineSelectImg = (ImageView) findViewById(R.id.lineSelectImg);
        lineSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPosition == 2){
                    SmPrChartFragment fragment = (SmPrChartFragment)fragmentHashMap.get(selectedPosition);
                    fragment.showLine();
                }

            }
        });

        addFavoriteBtn = (Button) findViewById(R.id.addFavoriteBtn);
        addFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition == 1) {
                    SmFavoriteFragment fragment = (SmFavoriteFragment) fragmentHashMap.get(selectedPosition);
                    fragment.changeSymbol();
                }
                /*Intent intent = new Intent(MainActivity.this, SmSymbolSelector.class);
                startActivity(intent);*/
                /*SmArgManager argManager = SmArgManager.getInstance();
                argManager.registerToMap("symbol_selector_popup","source_fragment", this);*/
            }
        });

        symExpertSelectBtn = (Button) findViewById(R.id.symExpertSelectBtn);
        symExpertSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition == 7) {
                    SmExpertFragment fragment = (SmExpertFragment) fragmentHashMap.get(selectedPosition);
                    //fragment.changeSymbol();
                }
            }
        });

        input_order_amount_text = (TextView) findViewById(R.id.input_order_amount_text);

        SharedPreferences s_pref= context.getSharedPreferences("order_info", Context.MODE_PRIVATE);
        String  order_count = s_pref.getString("order_amount", "");
        // 주문 갯수를 로드한다.
        if (order_count != null && order_count.length() > 0) {
            SmTotalOrderManager.defaultOrderAmount = Integer.parseInt(order_count);
        } else {
            order_count = "1";
        }
        input_order_amount_text.setText(order_count);
        input_order_amount_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumberInputActivity.class);
                startActivity(intent);
                SmArgManager argManager = SmArgManager.getInstance();
                //argManager.registerToMap("fees", "MainActivity", MainActivity.this);
                argManager.registerToMap("input", "input_order_amount_text", input_order_amount_text);
                argManager.registerToMap("input", "order_amount_value", input_order_amount_text.getText().toString());

                /*Intent intent = new Intent(MainActivity.this,NumberInputActivity.class);
                intent.putExtra("fees_value",feesTxt.getText().toString());
                startActivity(intent);*/
            }
        });

        push_order_amount_text = (TextView) findViewById(R.id.push_order_amount_text);
        push_order_amount_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order_count = Integer.parseInt(input_order_amount_text.getText().toString());
                SmTotalOrderManager.getInstance().defaultOrderAmount = order_count;
                SharedPreferences s_pref= getSharedPreferences("order_info", MODE_PRIVATE);
                SharedPreferences.Editor edit=s_pref.edit();
                edit.putString("order_amount", input_order_amount_text.getText().toString());
                edit.commit();
                Toast.makeText(MainActivity.this, "주문갯수가 " + input_order_amount_text.getText() + "로 변경되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

        String  abroad_fee = s_pref.getString("abroad_fee", "4");
        // 주문 갯수를 로드한다.
        if (abroad_fee != null && abroad_fee.length() > 0) {
            SmConst.AbroadFee = Integer.parseInt(abroad_fee);
        } else {
            abroad_fee = "4";
        }

        input_abroad_fee_text = (TextView) findViewById(R.id.input_abroad_fee_text);
        input_abroad_fee_text.setText(abroad_fee);
        input_abroad_fee_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumberInputActivity.class);
                startActivity(intent);
                SmArgManager argManager = SmArgManager.getInstance();
                //argManager.registerToMap("fees", "MainActivity", MainActivity.this);
                argManager.registerToMap("input", "input_abroad_fee_text", input_abroad_fee_text);
                argManager.registerToMap("input", "abroad_fee_value", input_abroad_fee_text.getText().toString());

                /*Intent intent = new Intent(MainActivity.this,NumberInputActivity.class);
                intent.putExtra("fees_value",feesTxt.getText().toString());
                startActivity(intent);*/
            }
        });

        push_abroad_fee_text = (TextView) findViewById(R.id.push_abroad_fee_text);
        push_abroad_fee_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order_count = Integer.parseInt(input_abroad_fee_text.getText().toString());
                SmConst.AbroadFee = order_count;
                SharedPreferences s_pref= getSharedPreferences("order_info", MODE_PRIVATE);
                SharedPreferences.Editor edit=s_pref.edit();
                edit.putString("abroad_fee", input_abroad_fee_text.getText().toString());
                edit.commit();
                Toast.makeText(MainActivity.this, "해외선물수수료가 " + input_abroad_fee_text.getText() + "로 변경되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

        String  domestic_fee = s_pref.getString("domestic_fee", "1000");
        // 주문 갯수를 로드한다.
        if (domestic_fee != null && domestic_fee.length() > 0) {
            SmConst.DomesticFee = Integer.parseInt(domestic_fee);
        } else {
            domestic_fee = "1000";
        }

        input_domestic_fee_text = (TextView) findViewById(R.id.input_domestic_fee_text);
        input_domestic_fee_text.setText(domestic_fee);
        input_domestic_fee_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumberInputActivity.class);
                startActivity(intent);
                SmArgManager argManager = SmArgManager.getInstance();
                //argManager.registerToMap("fees", "MainActivity", MainActivity.this);
                argManager.registerToMap("input", "input_domestic_fee_text", input_domestic_fee_text);
                argManager.registerToMap("input", "domestic_fee_value", input_domestic_fee_text.getText().toString());

                /*Intent intent = new Intent(MainActivity.this,NumberInputActivity.class);
                intent.putExtra("fees_value",feesTxt.getText().toString());
                startActivity(intent);*/
            }
        });

        push_domestic_fee_text = (TextView) findViewById(R.id.push_abroad_fee_text);
        push_domestic_fee_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order_count = Integer.parseInt(input_domestic_fee_text.getText().toString());
                SmConst.DomesticFee = order_count;
                SharedPreferences s_pref= getSharedPreferences("order_info", MODE_PRIVATE);
                SharedPreferences.Editor edit=s_pref.edit();
                edit.putString("domestic_fee", input_domestic_fee_text.getText().toString());
                edit.commit();
                Toast.makeText(MainActivity.this, "국내선물수수료가 " + input_domestic_fee_text.getText() + "로 변경되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

        //right option 하단 버튼 세개
        recentImg = (ImageView) findViewById(R.id.recentImg);
        recentImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //기존 모든 메뉴를 gone시키고 최근 거래 리사이클러를 보여준다
                recentTitle.setVisibility(View.VISIBLE);
                recyclerView_right.setVisibility(View.GONE);
                recentRecyclerView.setVisibility(View.VISIBLE);
                feesLayout.setVisibility(View.GONE);

                recentImg.setBackgroundColor(Color.parseColor("#2292E6"));
                optionListImg.setBackgroundColor(Color.parseColor("#2B3652"));

            }
        });

        optionListImg = (ImageView) findViewById(R.id.optionListImg);
        //default 선택
        optionListImg.setBackgroundColor(Color.parseColor("#2292E6"));
        optionListImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentTitle.setVisibility(View.GONE);
                recyclerView_right.setVisibility(View.VISIBLE);
                recentRecyclerView.setVisibility(View.GONE);
                feesLayout.setVisibility(View.VISIBLE);

                recentImg.setBackgroundColor(Color.parseColor("#2B3652"));
                optionListImg.setBackgroundColor(Color.parseColor("#2292E6"));
            }
        });


        //recentTitle
        /*recentBackImg = (ImageView) findViewById(R.id.recentBackImg);
        recentBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentTitle.setVisibility(View.GONE);
                recyclerView_right.setVisibility(View.VISIBLE);
                recentRecyclerView.setVisibility(View.GONE);
                feesLayout.setVisibility(View.VISIBLE);
            }
        });*/


    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }

        *//*switch (item.getItemId()) {
            case R.id.first:
                Toast.makeText(this, "첫 번째 옵션입니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.second:
                Toast.makeText(this, "두 번째 옵션입니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.third:
                Toast.makeText(this, "세 번째 옵션입니다.", Toast.LENGTH_SHORT).show();
                break;
        }*//*
        //return true;
        return super.onOptionsItemSelected(item);
    }*/

    //add side Drawer Override
    /*@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item){


        return super.onOptionsItemSelected(item);
    }*/

    //add side Drawer
    /*private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 0:
                    //setTitle("SMobile");
                    siseTitle.setVisibility(View.VISIBLE);
                    favoriteTitle.setVisibility(View.INVISIBLE);
                    mockTitle.setVisibility(View.INVISIBLE);
                    autoOrderTitle.setVisibility(View.INVISIBLE);
                    croboTitle.setVisibility(View.INVISIBLE);
                    portTitle.setVisibility(View.INVISIBLE);
                    productionTitle.setVisibility(View.INVISIBLE);
                    expertTitle.setVisibility(View.INVISIBLE);
                    displaySmCurrentFragment();
                    break;
                case 1:
                    //setTitle("관심 종목");
                    siseTitle.setVisibility(View.INVISIBLE);
                    favoriteTitle.setVisibility(View.VISIBLE);
                    mockTitle.setVisibility(View.INVISIBLE);
                    autoOrderTitle.setVisibility(View.INVISIBLE);
                    croboTitle.setVisibility(View.INVISIBLE);
                    portTitle.setVisibility(View.INVISIBLE);
                    productionTitle.setVisibility(View.INVISIBLE);
                    expertTitle.setVisibility(View.INVISIBLE);
                    displaySmFavoriteFragment();
                    break;
                case 2:
                    //setTitle("모의 매매");
                    siseTitle.setVisibility(View.INVISIBLE);
                    favoriteTitle.setVisibility(View.INVISIBLE);
                    mockTitle.setVisibility(View.VISIBLE);
                    autoOrderTitle.setVisibility(View.INVISIBLE);
                    croboTitle.setVisibility(View.INVISIBLE);
                    portTitle.setVisibility(View.INVISIBLE);
                    productionTitle.setVisibility(View.INVISIBLE);
                    expertTitle.setVisibility(View.INVISIBLE);
                    displaySmPrChartFragment();
                    break;
                case 3:
                    //setTitle("자동주문");
                    siseTitle.setVisibility(View.INVISIBLE);
                    favoriteTitle.setVisibility(View.INVISIBLE);
                    mockTitle.setVisibility(View.INVISIBLE);
                    autoOrderTitle.setVisibility(View.VISIBLE);
                    croboTitle.setVisibility(View.INVISIBLE);
                    portTitle.setVisibility(View.INVISIBLE);
                    productionTitle.setVisibility(View.INVISIBLE);
                    expertTitle.setVisibility(View.INVISIBLE);
                    displaySmAutoFragment();
                    break;
                case 4:
                    //setTitle("C로보");
                    siseTitle.setVisibility(View.INVISIBLE);
                    favoriteTitle.setVisibility(View.INVISIBLE);
                    mockTitle.setVisibility(View.INVISIBLE);
                    autoOrderTitle.setVisibility(View.INVISIBLE);
                    croboTitle.setVisibility(View.VISIBLE);
                    portTitle.setVisibility(View.INVISIBLE);
                    productionTitle.setVisibility(View.INVISIBLE);
                    expertTitle.setVisibility(View.INVISIBLE);
                    displaySmCroboFragment();
                    break;
                case 5:
                    //setTitle("포트폴리오");
                    siseTitle.setVisibility(View.INVISIBLE);
                    favoriteTitle.setVisibility(View.INVISIBLE);
                    mockTitle.setVisibility(View.INVISIBLE);
                    autoOrderTitle.setVisibility(View.INVISIBLE);
                    croboTitle.setVisibility(View.INVISIBLE);
                    portTitle.setVisibility(View.VISIBLE);
                    productionTitle.setVisibility(View.INVISIBLE);
                    expertTitle.setVisibility(View.INVISIBLE);
                    displaySmPortfolioFragment();
                    break;
                case 6:
                    //setTitle("상품");
                    siseTitle.setVisibility(View.INVISIBLE);
                    favoriteTitle.setVisibility(View.INVISIBLE);
                    mockTitle.setVisibility(View.INVISIBLE);
                    autoOrderTitle.setVisibility(View.INVISIBLE);
                    croboTitle.setVisibility(View.INVISIBLE);
                    portTitle.setVisibility(View.INVISIBLE);
                    productionTitle.setVisibility(View.VISIBLE);
                    expertTitle.setVisibility(View.INVISIBLE);
                    displaySmFundFragment();
                    break;
                case 7:
                    //setTitle("전문가클럽");
                    siseTitle.setVisibility(View.INVISIBLE);
                    favoriteTitle.setVisibility(View.INVISIBLE);
                    mockTitle.setVisibility(View.INVISIBLE);
                    autoOrderTitle.setVisibility(View.INVISIBLE);
                    croboTitle.setVisibility(View.INVISIBLE);
                    portTitle.setVisibility(View.INVISIBLE);
                    productionTitle.setVisibility(View.INVISIBLE);
                    expertTitle.setVisibility(View.VISIBLE);
                    displaySmExpertFragment();
                    break;
            }
            dlDrawer.closeDrawer(lvNavList);
        }
    }*/


    private void setUpSciChartLicense() {
        try {
            com.scichart.charting.visuals.SciChartSurface.setRuntimeLicenseKey(
                    "<LicenseContract>" +
                            "<Customer>Signal Master</Customer>" +
                            "<OrderId>ABT190415-9572-80110</OrderId>" +
                            "<LicenseCount>1</LicenseCount>" +
                            "<IsTrialLicense>false</IsTrialLicense>" +
                            " <SupportExpires>04/14/2020 00:00:00</SupportExpires>" +
                            " <ProductCode>SC-IOS-ANDROID-2D-ENTERPRISE-SRC</ProductCode>" +
                            "<KeyCode>827b6998e9f182adb4247cd45e54e4e8387271ef1f06d73b9152eb35f068754036cbe8f5a51a7448e6f8d758f3155284e93b68a30d64eaa8224580f8bc93a5d4d83b07f5b280fd32608232966f75d268bc98f29dee7876f60ad547a2c61da45505693e705231f06ee4d529fa19b230c3c211741619c5fa1a64439fa84ef4cd31d85062363306e8a0048907081012b5d09da4a427fe7ba4ec2aa21e514f2912f5b4748f456e4d27e8f868e28ab58580a6fbd9b8526db36253c0</KeyCode>" +
                            "</LicenseContract>"
            );
        } catch (Exception e) {
            Log.e("SciChart", "Error when setting the license", e);
        }
    }

    public void onClickMarketItem(int position) {
        SmCurrentFragment frg = (SmCurrentFragment) _mainFragmentMap.get(0);
        if (frg != null)
            frg.onClickItem(position);
    }

    /*public void changeRightList(int position){
        onClickItem(position);
    }*/


    //하단 리사이클러뷰 클릭 이벤트
    public void onClickItem(int position) {

        SmArgManager smArgManager = SmArgManager.getInstance();
        //bubbleSeekBar = (BubbleSeekBar)smCroboFragment.getView().findViewById(R.id.seekbar);
        switch (position) {
            case 0:
                //setTitle("현재가");
                siseTitle.setVisibility(View.VISIBLE);
                favoriteTitle.setVisibility(View.GONE);
                mockTitle.setVisibility(View.GONE);
                autoOrderTitle.setVisibility(View.GONE);
                croboTitle.setVisibility(View.GONE);
                portTitle.setVisibility(View.GONE);
                productionTitle.setVisibility(View.GONE);
                expertTitle.setVisibility(View.GONE);
                displaySmCurrentFragment();

                if (isPageOpen) {
                    slidingPanel.bringToFront();
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                }

                bottom_icon_layout.setVisibility(View.GONE);
                feesLayout.setVisibility(View.GONE);

                smCroboQuestionFragment = (SmCroboQuestionFragment)smArgManager.getVal("questionFragment","fragment");
                smCroboLevelFragment = (SmCroboLevelFragment)smArgManager.getVal("survey","levelFragment");

                if(smCroboQuestionFragment == null || smCroboLevelFragment == null){
                    break;
                } else {
                    smCroboQuestionFragment.seekBar.setVisibility(View.GONE);
                    smCroboLevelFragment.seekBar.setVisibility(View.GONE);
                }
                /*SmCroboFragment smCroboFragment = (SmCroboFragment)getSupportFragmentManager().findFragmentById(R.id.mainListContainer);
                smCroboFragment.*/

                break;
            case 1:
                //setTitle("관심 종목");
                siseTitle.setVisibility(View.GONE);
                favoriteTitle.setVisibility(View.VISIBLE);
                //addFavoriteBtn.bringToFront();
                mockTitle.setVisibility(View.GONE);
                autoOrderTitle.setVisibility(View.GONE);
                croboTitle.setVisibility(View.GONE);
                portTitle.setVisibility(View.GONE);
                productionTitle.setVisibility(View.GONE);
                expertTitle.setVisibility(View.GONE);
                displaySmFavoriteFragment();

                if (isPageOpen) {
                    slidingPanel.bringToFront();
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                }

                bottom_icon_layout.setVisibility(View.GONE);
                feesLayout.setVisibility(View.GONE);
                smCroboQuestionFragment = (SmCroboQuestionFragment)smArgManager.getVal("questionFragment","fragment");
                smCroboLevelFragment = (SmCroboLevelFragment)smArgManager.getVal("survey","levelFragment");

                if(smCroboQuestionFragment == null || smCroboLevelFragment == null){
                    break;
                } else {
                    smCroboQuestionFragment.seekBar.setVisibility(View.GONE);
                    smCroboLevelFragment.seekBar.setVisibility(View.GONE);
                }

                // bubbleSeekBar.setVisibility(View.GONE);

                break;
            case 2:
                //setTitle("모의 매매");
                siseTitle.setVisibility(View.GONE);
                favoriteTitle.setVisibility(View.GONE);
                mockTitle.setVisibility(View.VISIBLE);
                /*symSelectBtn.bringToFront();
                chartSelectImg.bringToFront();
                indexSelectImg.bringToFront();
                lineSelectImg.bringToFront();*/
                autoOrderTitle.setVisibility(View.GONE);
                croboTitle.setVisibility(View.GONE);
                portTitle.setVisibility(View.GONE);
                productionTitle.setVisibility(View.GONE);
                expertTitle.setVisibility(View.GONE);
                displaySmPrChartFragment();

                if (isPageOpen) {
                    slidingPanel.bringToFront();
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                }


                bottom_icon_layout.setVisibility(View.VISIBLE);
                feesLayout.setVisibility(View.VISIBLE);
                smCroboQuestionFragment = (SmCroboQuestionFragment)smArgManager.getVal("questionFragment","fragment");
                smCroboLevelFragment = (SmCroboLevelFragment)smArgManager.getVal("survey","levelFragment");

                if(smCroboQuestionFragment == null || smCroboLevelFragment == null){
                    break;
                } else {
                    smCroboQuestionFragment.seekBar.setVisibility(View.GONE);
                    smCroboLevelFragment.seekBar.setVisibility(View.GONE);
                }

                // bubbleSeekBar.setVisibility(View.GONE);

                break;
            case 3:
                //setTitle("주문");
                siseTitle.setVisibility(View.GONE);
                favoriteTitle.setVisibility(View.GONE);
                mockTitle.setVisibility(View.GONE);
                autoOrderTitle.setVisibility(View.VISIBLE);
                //orderSymSelectTxt.bringToFront();
                croboTitle.setVisibility(View.GONE);
                portTitle.setVisibility(View.GONE);
                productionTitle.setVisibility(View.GONE);
                expertTitle.setVisibility(View.GONE);
                displaySmAutoFragment();

                if (isPageOpen) {
                    slidingPanel.bringToFront();
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                }

                bottom_icon_layout.setVisibility(View.VISIBLE);
                feesLayout.setVisibility(View.VISIBLE);
                smCroboQuestionFragment = (SmCroboQuestionFragment)smArgManager.getVal("questionFragment","fragment");
                smCroboLevelFragment = (SmCroboLevelFragment)smArgManager.getVal("survey","levelFragment");

                if(smCroboQuestionFragment == null || smCroboLevelFragment == null){
                    break;
                } else {
                    smCroboQuestionFragment.seekBar.setVisibility(View.GONE);
                    smCroboLevelFragment.seekBar.setVisibility(View.GONE);
                }
                // bubbleSeekBar.setVisibility(View.GONE);

                break;
            case 4:
                //setTitle("C로보");
                siseTitle.setVisibility(View.GONE);
                favoriteTitle.setVisibility(View.GONE);
                mockTitle.setVisibility(View.GONE);
                autoOrderTitle.setVisibility(View.GONE);
                croboTitle.setVisibility(View.VISIBLE);
                portTitle.setVisibility(View.GONE);
                productionTitle.setVisibility(View.GONE);
                expertTitle.setVisibility(View.GONE);
                displaySmCroboFragment();

                if (isPageOpen) {
                    slidingPanel.bringToFront();
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                }

                bottom_icon_layout.setVisibility(View.GONE);
                feesLayout.setVisibility(View.GONE);

                // bubbleSeekBar.setVisibility(View.VISIBLE);
                smCroboQuestionFragment = (SmCroboQuestionFragment)smArgManager.getVal("questionFragment","fragment");
                smCroboLevelFragment = (SmCroboLevelFragment)smArgManager.getVal("survey","levelFragment");

                if(smCroboQuestionFragment == null || smCroboLevelFragment == null){
                    break;
                } else {
                    smCroboQuestionFragment.seekBar.setVisibility(View.VISIBLE);
                    smCroboLevelFragment.seekBar.setVisibility(View.VISIBLE);
                }

                break;
            case 5:
                //setTitle("포트폴리오");
                siseTitle.setVisibility(View.GONE);
                favoriteTitle.setVisibility(View.GONE);
                mockTitle.setVisibility(View.GONE);
                autoOrderTitle.setVisibility(View.GONE);
                croboTitle.setVisibility(View.GONE);
                portTitle.setVisibility(View.VISIBLE);
                productionTitle.setVisibility(View.GONE);
                expertTitle.setVisibility(View.GONE);
                displaySmPortfolioFragment();

                if (isPageOpen) {
                    slidingPanel.bringToFront();
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                }

                bottom_icon_layout.setVisibility(View.GONE);
                feesLayout.setVisibility(View.GONE);
                smCroboQuestionFragment = (SmCroboQuestionFragment)smArgManager.getVal("questionFragment","fragment");
                smCroboLevelFragment = (SmCroboLevelFragment)smArgManager.getVal("survey","levelFragment");

                if(smCroboQuestionFragment == null || smCroboLevelFragment == null){
                    break;
                } else {
                    smCroboQuestionFragment.seekBar.setVisibility(View.GONE);
                    smCroboLevelFragment.seekBar.setVisibility(View.GONE);
                }

                //  bubbleSeekBar.setVisibility(View.GONE);

                break;
            case 6:
                //setTitle("상품");
                siseTitle.setVisibility(View.GONE);
                favoriteTitle.setVisibility(View.GONE);
                mockTitle.setVisibility(View.GONE);
                autoOrderTitle.setVisibility(View.GONE);
                croboTitle.setVisibility(View.GONE);
                portTitle.setVisibility(View.GONE);
                productionTitle.setVisibility(View.VISIBLE);
                expertTitle.setVisibility(View.GONE);
                displaySmFundFragment();

                if (isPageOpen) {
                    slidingPanel.bringToFront();
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                }

                bottom_icon_layout.setVisibility(View.GONE);
                feesLayout.setVisibility(View.GONE);
                smCroboQuestionFragment = (SmCroboQuestionFragment)smArgManager.getVal("questionFragment","fragment");
                smCroboLevelFragment = (SmCroboLevelFragment)smArgManager.getVal("survey","levelFragment");

                if(smCroboQuestionFragment == null || smCroboLevelFragment == null){
                    break;
                } else {
                    smCroboQuestionFragment.seekBar.setVisibility(View.GONE);
                    smCroboLevelFragment.seekBar.setVisibility(View.GONE);
                }
                // bubbleSeekBar.setVisibility(View.GONE);

                break;
            case 7:
                //setTitle("전문가클럽");
                siseTitle.setVisibility(View.GONE);
                favoriteTitle.setVisibility(View.GONE);
                mockTitle.setVisibility(View.GONE);
                autoOrderTitle.setVisibility(View.GONE);
                croboTitle.setVisibility(View.GONE);
                portTitle.setVisibility(View.GONE);
                productionTitle.setVisibility(View.GONE);
                expertTitle.setVisibility(View.VISIBLE);
                displaySmExpertFragment();

                if (isPageOpen) {
                    slidingPanel.bringToFront();
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                }

                bottom_icon_layout.setVisibility(View.GONE);
                feesLayout.setVisibility(View.GONE);
                smCroboQuestionFragment = (SmCroboQuestionFragment)smArgManager.getVal("questionFragment","fragment");
                smCroboLevelFragment = (SmCroboLevelFragment)smArgManager.getVal("survey","levelFragment");

                if(smCroboQuestionFragment == null || smCroboLevelFragment == null){
                    break;
                } else {
                    smCroboQuestionFragment.seekBar.setVisibility(View.GONE);
                    smCroboLevelFragment.seekBar.setVisibility(View.GONE);
                }
                //  bubbleSeekBar.setVisibility(View.GONE);

                break;
        }
        dlDrawer.closeDrawers();
        drawerState = false;
        selectedPosition = position;
    }


    private void setDefaultFragment(Fragment defaultFragment) {
        this.replaceFragment(defaultFragment);
    }

    public void replaceFragment(Fragment destFragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        /*for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }*/
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainListContainer, destFragment);
        fragmentTransaction.commit();
    }

    public void changeOptionType(int position) {

        switch (position) {
            case 0:
                rightOptionAdapter = new MainRightOptionAdapter(defaultOptionList);
                recyclerView_right.setAdapter(rightOptionAdapter);
                rightOptionAdapter.setOnItemClickListener(new MainRightOptionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                    }
                });

                break;
            case 1:
                rightOptionAdapter = new MainRightOptionAdapter(favoriteOptionList);
                recyclerView_right.setAdapter(rightOptionAdapter);
                rightOptionAdapter.setOnItemClickListener(new MainRightOptionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                    }
                });


                break;
                //모의매매
            case 2:
                rightOptionAdapter = new MainRightOptionAdapter(mockOptionList);
                recyclerView_right.setAdapter(rightOptionAdapter);
                rightOptionAdapter.setOnItemClickListener(new MainRightOptionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        switch (position) {
                            case 0:
                                //메인바 감추기
                                if (stateHide == false) {
                                    recyclerView.setVisibility(View.GONE);
                                    stateHide = true;
                                    mockOptionList.set(0, "하단바 보이기");
                                    rightOptionAdapter.notifyDataSetChanged();
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    stateHide = false;
                                    mockOptionList.set(0, "하단바 감추기");
                                    rightOptionAdapter.notifyDataSetChanged();
                                }

                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);
                                break;

                            case 1:
                                //계좌
                                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                                startActivity(intent);

                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);

                                break;
                            case 2:
                                //거래기록
                                Intent intent1 = new Intent(MainActivity.this, TradeRecordActivity.class);
                                startActivity(intent1);

                                SmTotalOrderManager.getInstance().requestOrderList();

                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);

                                break;
                            case 3:
                                //리포트
                                Intent intent2 = new Intent(MainActivity.this, IncomeChartActivity.class);
                                startActivity(intent2);

                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);

                            case 4:
                                //포지션 목록
                                Intent intent3 = new Intent(MainActivity.this, PositionListActivity.class);
                                SmArgManager smArgManager = SmArgManager.getInstance();
                                SmPrChartFragment fragment = (SmPrChartFragment)fragmentHashMap.get(2);
                                smArgManager.registerToMap("positionList","prFragment",fragment);
                                smArgManager.registerToMap("positionList","mockTxt",symSelectBtn);
                                startActivity(intent3);
                                SmTotalOrderManager.getInstance().requestPositionList();
                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);

                                break;
                        }
                    }
                });

                break;
                //주문
            case 3:
                rightOptionAdapter = new MainRightOptionAdapter(mockOptionList);
                recyclerView_right.setAdapter(rightOptionAdapter);
                rightOptionAdapter.setOnItemClickListener(new MainRightOptionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        switch (position) {
                            case 0:
                                //메인바 감추기
                                if (stateHide == false) {
                                    recyclerView.setVisibility(View.GONE);
                                    stateHide = true;
                                    mockOptionList.set(0, "하단바 보이기");
                                    rightOptionAdapter.notifyDataSetChanged();
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    stateHide = false;
                                    mockOptionList.set(0, "하단바 감추기");
                                    rightOptionAdapter.notifyDataSetChanged();
                                }

                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);
                                break;

                            case 1:
                                //계좌
                                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                                startActivity(intent);

                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);

                                break;
                            case 2:
                                //거래기록
                                Intent intent1 = new Intent(MainActivity.this, TradeRecordActivity.class);
                                startActivity(intent1);

                                SmTotalOrderManager.getInstance().requestOrderList();

                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);

                                break;
                            case 3:
                                //리포트
                                Intent intent2 = new Intent(MainActivity.this, IncomeChartActivity.class);
                                startActivity(intent2);

                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);

                                break;
                            case 4:
                                //포지션 목록
                                Intent intent3 = new Intent(MainActivity.this,PositionListActivity.class);
                                SmArgManager smArgManager = SmArgManager.getInstance();
                                SmAutoFragment fragment = (SmAutoFragment)fragmentHashMap.get(3);
                                smArgManager.registerToMap("positionList","auto_fragment",fragment);
                                smArgManager.registerToMap("positionList","orderTxt",orderSymSelectTxt);
                                startActivity(intent3);

                                SmTotalOrderManager.getInstance().requestPositionList();

                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);

                                break;
                        }
                    }
                });

                break;
            case 4:
                rightOptionAdapter = new MainRightOptionAdapter(defaultOptionList);
                recyclerView_right.setAdapter(rightOptionAdapter);
                rightOptionAdapter.setOnItemClickListener(new MainRightOptionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        switch(position){
                            case 0:

                                break;
                        }

                    }
                });

                break;
                //포트폴리오
            case 5:
                rightOptionAdapter = new MainRightOptionAdapter(portfolioOptionList);
                recyclerView_right.setAdapter(rightOptionAdapter);
                rightOptionAdapter.setOnItemClickListener(new MainRightOptionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        switch (position){
                            case 0:
                                /*Intent intent = new Intent(MainActivity.this, PortfolioDialog.class);
                                startActivity(intent);*/

                                if(selectedPosition == 5){
                                    SmPortfolioFragment fragment = (SmPortfolioFragment)fragmentHashMap.get(selectedPosition);
                                    if(fragment != null)
                                        fragment.openDialog();
                                }

                                slidingPanel.startAnimation(translateRightAnim);
                                slidingPanel.setVisibility(View.GONE);

                                break;
                        }

                        slidingPanel.startAnimation(translateRightAnim);
                        slidingPanel.setVisibility(View.GONE);

                    }
                });

                break;
            case 6:
                rightOptionAdapter = new MainRightOptionAdapter(defaultOptionList);
                recyclerView_right.setAdapter(rightOptionAdapter);
                rightOptionAdapter.setOnItemClickListener(new MainRightOptionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                    }
                });

                break;
            case 7:
                rightOptionAdapter = new MainRightOptionAdapter(defaultOptionList);
                recyclerView_right.setAdapter(rightOptionAdapter);
                rightOptionAdapter.setOnItemClickListener(new MainRightOptionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                    }
                });

                break;
        }

    }

}
