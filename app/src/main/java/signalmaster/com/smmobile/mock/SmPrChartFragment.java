package signalmaster.com.smmobile.mock;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.scichart.core.common.Action1;

import java.io.Serializable;
import java.util.Currency;
import java.util.Locale;

import signalmaster.com.smmobile.ChartChangeActivity;
import signalmaster.com.smmobile.LineSelectActivity;
import signalmaster.com.smmobile.login.LoginActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.SmIndexSelector;
import signalmaster.com.smmobile.Util.SmLayoutManager;
import signalmaster.com.smmobile.account.SmAccount;
import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.autoorder.SmAutoFragment;
import signalmaster.com.smmobile.chart.SmChartType;
import signalmaster.com.smmobile.chart.SmSeriesType;
import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.data.SmChartDataItem;
import signalmaster.com.smmobile.data.SmChartDataManager;
import signalmaster.com.smmobile.data.SmChartDataService;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.network.SmServiceManager;
import signalmaster.com.smmobile.order.SmFilledCondition;
import signalmaster.com.smmobile.order.SmOrder;
import signalmaster.com.smmobile.order.SmOrderReqNoGenerator;
import signalmaster.com.smmobile.order.SmOrderRequest;
import signalmaster.com.smmobile.order.SmOrderType;
import signalmaster.com.smmobile.order.SmPriceType;
import signalmaster.com.smmobile.order.SmTotalOrderManager;
import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.position.SmPositionType;
import signalmaster.com.smmobile.position.SmTotalPositionManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;
import signalmaster.com.smmobile.symbol.SmSymbolSelector;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.SmOptionFragment;
import signalmaster.com.smmobile.ui.SmChartTypeOption;
import signalmaster.com.smmobile.ui.SmListenerManger;
import signalmaster.com.smmobile.userinfo.SmUserManager;

import static android.view.Gravity.CENTER;
import static com.rayject.table.view.TableView.TAG;
import static com.scichart.core.utility.Dispatcher.runOnUiThread;
import static java.lang.Character.getNumericValue;
import static java.lang.Character.isDigit;

public class SmPrChartFragment extends Fragment implements Serializable {

    public static SmPrChartFragment newInstance(){
        SmPrChartFragment fragment = new SmPrChartFragment();
        Bundle args =  new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public SmAutoFragment autoFragment = null;

    Button symSelectBtn;
    ImageView indexSelectImg,chartSelectImg,lineSelectImg,mockMenuImg,changeChartImg;

    SmSeriesType selectedPosition = SmSeriesType.MountainView;

    private SmPrBtnFragment _btnChartFragment = new SmPrBtnFragment();
    private SmOptionFragment _optionFragment = new SmOptionFragment();


    private SmChartFragment smChartFragment = null;
    private SmChartFragment _leftChartFragment = null;
    private SmChartFragment _rightChartFragment = null;

    public int marketIndex = 0;
    public int selectedIndex = 0;

    TextView open_qty, position_profit, average_text;

    public void hideChartFragment(){
        smChartFragment.hideChart();
        /*FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.hide(smChartFragment);
        transaction.commit();*/
    }

    public void showChartFragment(){
        smChartFragment.showChart();
        /*FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.mainChartContainer, smChartFragment);
        transaction.commit();*/
    }

    private void initPosition() {
        SmAccountManager accountManager = SmAccountManager.getInstance();
        String acno = accountManager.getDefaultAccountNo(symbolCode);
        if (acno == null)
            return;
        SmTotalPositionManager totalPositionManager = SmTotalPositionManager.getInstance();
        SmPosition position = totalPositionManager.findPosition(acno, symbolCode);
        updatePosition(position);
    }

    public void showHideFragment(final Fragment fragment){

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if (fragment.isAdded()){
            if (fragment.isHidden()) {
                Log.d(TAG,"++++++++++++++++++++ show");
                ft.show(fragment);
            } else {
                Log.d(TAG,"++++++++++++++++++++ hide");
                fragment.getView().setVisibility(View.GONE);
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            SmSymbol symbol = SmTotalOrderManager.getInstance().getOrderSymbol(getContext());
            smChartFragment = SmChartFragment.newInstance("mock_main");
            _leftChartFragment = SmChartFragment.newInstance("mock_left");
            _rightChartFragment = SmChartFragment.newInstance("mock_right");

            getActivity().getIntent().putExtra("symbolCode", symbol.code);
            symbolCode = symbol.code;
        }



        // _btnChartFragment = new SmPrBtnFragment();



        SmLayoutManager layoutManager = SmLayoutManager.getInstance();
        layoutManager.register("mock_main", smChartFragment);
        layoutManager.register("mock_left", _leftChartFragment);
        layoutManager.register("mock_right", _rightChartFragment);
    }

    public static SmPrChartFragment newInstance(Bundle bundle) {
        SmPrChartFragment smPrChartFragment = new SmPrChartFragment();
        Bundle args = bundle;
        smPrChartFragment.setArguments(args);
        return smPrChartFragment;
    }

    public SmPrChartFragment() {

    }

    private String symbolCode;
    Button buyBtn,sellBtn;

    LinearLayout slidingPanel;
    Animation translateRightAnim,translateLeftAnim;
    boolean isPageOpen = false;



    private void resizeFragment(Fragment f, int newWidth, int newHeight) {

        try {
            if (f != null) {
                View view = f.getView();
                FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(newWidth, newHeight);
                view.setLayoutParams(p);
                view.requestLayout();
            }
        } catch (Exception e) {
            // This will catch any exception, because they are all descended from Exception
            System.out.println("Error " + e.getMessage());
        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        final View _chartView = inflater.inflate(R.layout.pr_port_chart, container, false);

        open_qty = (TextView)_chartView.findViewById(R.id.open_qty);
        open_qty.setTextAlignment(_chartView.TEXT_ALIGNMENT_CENTER);
        position_profit = (TextView)_chartView.findViewById(R.id.position_profit);
        position_profit.setTextAlignment(_chartView.TEXT_ALIGNMENT_CENTER);
        average_text = _chartView.findViewById(R.id.avg);
        average_text.setTextAlignment(_chartView.TEXT_ALIGNMENT_CENTER);

        //View view = inflater.inflate(R.layout.pr_port_chart, container, false);
        FragmentManager fm = getActivity().getSupportFragmentManager();

        /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainChartContainer, getsmChartFragment());
        fragmentTransaction.add(R.id.leftChartContainer, _leftChartFragment);
        fragmentTransaction.add(R.id.rightChartContainer, _rightChartFragment);
        fragmentTransaction.add(R.id.option_container, _optionFragment);
        //fragmentTransaction.replace(R.id.btnContainer, _btnChartFragment);
        fragmentTransaction.commit();*/


        /*symSelectBtn = (Button)_chartView.findViewById(R.id.symSelectBtn);
        symSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context, SmSymbolSelector.class);
                MainActivity.context.startActivity(intent);
                SmArgManager argMgr = SmArgManager.getInstance();
                argMgr.registerToMap("symbol_selector_popup", "source_chart", smChartFragment);
                argMgr.registerToMap("symbol_selector_popup", "clicked_button", symSelectBtn);

            }
        });

        chartSelectImg = (ImageView)_chartView.findViewById(R.id.chartSelectImg);
        chartSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context, ChartChangeActivity.class);

                SmArgManager argMgr = SmArgManager.getInstance();
                argMgr.registerToMap("seriesType","chartFragment",smChartFragment);

                MainActivity.context.startActivity(intent);
            }
        });

        indexSelectImg = (ImageView)_chartView.findViewById(R.id.indexSelectImg);
        indexSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context, SmIndexSelector.class);
                MainActivity.context.startActivity(intent);
            }
        });

        lineSelectImg = (ImageView)_chartView.findViewById(R.id.lineSelectImg);
        lineSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context, LineSelectActivity.class);
                MainActivity.context.startActivity(intent);
            }
        });*/

        /*mockMenuImg = (ImageView)_chartView.findViewById(R.id.mockMenuImg);
        mockMenuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context,SmMockPopup.class);
                MainActivity.context.startActivity(intent);
            }
        });*/
        slidingPanel = getActivity().findViewById(R.id.slidingPanel);
        translateRightAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_right);
        translateLeftAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_left);

        /*translateRightAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                _chartView.setClickable(false);
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
                _chartView.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/

        //차트위는 먹히지 안는다. -> SmChartFragment 도 작업해줘야함.
        /*_chartView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP){

                    if(isPageOpen )

                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                }

                return true;
            }
        });*/

        changeChartImg = (ImageView) _chartView.findViewById(R.id.changeChartImg);
        changeChartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*SmArgManager argMgr = SmArgManager.getInstance();
                SmChartFragment smChartFragment = (SmChartFragment)argMgr.getVal("seriesType","chartFragment");
                if(smChartFragment != null){
                    smChartFragment.changeSeriesType(selectedPosition);
                    argMgr.unregisterFromMap("seriesType");
                }*/
                /*
                if(selectedPosition == SmSeriesType.CandleStick ) {
                    selectedPosition = SmSeriesType.MountainView;
                    smChartFragment.changeSeriesType(selectedPosition);
                    //smChartFragment.changeSeriesType(selectedPosition);
                }else if(selectedPosition == SmSeriesType.MountainView){
                    selectedPosition = SmSeriesType.CandleStick;
                    smChartFragment.changeSeriesType(selectedPosition);
                }
                */

                //showHideFragment(_leftChartFragment);
                //showHideFragment(_rightChartFragment);
                //showHideFragment(_optionFragment);

                int left_height = _leftChartFragment.getView().getMeasuredHeight();
                int opt_height = _optionFragment.getView().getMeasuredHeight();

                int main_height = smChartFragment.getView().getMeasuredHeight();
                main_height =  main_height + left_height + opt_height;


                //getView().findViewById(R.id.layout_option).setVisibility(View.INVISIBLE);
                //getView().findViewById(R.id.layout_subchart).setVisibility(View.INVISIBLE);

                //resizeFragment(smChartFragment, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                android.widget.LinearLayout layout1 = (android.widget.LinearLayout) getView().findViewById(R.id.layout_mainchart);

                android.widget.LinearLayout layout_parent = (android.widget.LinearLayout) getView().findViewById(R.id.chart_order_upper);

                //android.widget.LinearLayout.LayoutParams  params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                //par.height = LinearLayout.LayoutParams.MATCH_PARENT;
                //smChartFragment.getView().setLayoutParams(params);
                //layout1.getLayoutParams().height = main_height + 400;
                //resizeFragment(smChartFragment, main_width, main_height + 400);

                // Gets linearlayout

// Gets the layout params that will allow you to resize the layout
                LayoutParams params = (LinearLayout.LayoutParams)layout1.getLayoutParams();
// Changes the height and width to the specified *pixels*
                int height = layout_parent.getHeight();
                params.height = LayoutParams.MATCH_PARENT;
                //params.weight = 10.0f;
                layout1.setLayoutParams(params);

                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)getView().findViewById(R.id.layout_option).getLayoutParams();
                params1.height = 0;
                getView().findViewById(R.id.layout_option).setLayoutParams(params1);

                LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)getView().findViewById(R.id.layout_subchart).getLayoutParams();
                params2.height = 0;
                getView().findViewById(R.id.layout_subchart).setLayoutParams(params2);

                layout_parent.requestLayout();
                //layout1.setVisibility(View.INVISIBLE);

                smChartFragment.refreshOrderAnnotation();

            }
        });



        //매수 매도 버튼 추가
        // 주문 콜백 등록
        SmChartDataService chartDataService = SmChartDataService.getInstance();
        chartDataService.subscribeOrder(onNewOrder(), this.getClass().getSimpleName());
        chartDataService.subscribeSise(onUpdateSymbol(), this.getClass().getSimpleName());
        chartDataService.subscribeUpdateData(onUpdatePrice(), this.getClass().getSimpleName());
        chartDataService.subscribePosition(onUpdatePosition(), this.getClass().getSimpleName());
        //매수 매도 버튼 이벤트
        buyBtn = (Button)_chartView.findViewById(R.id.buyBtn);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SmUserManager.getInstance().isLoggedIn()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                SmSymbolManager symbolManager = SmSymbolManager.getInstance();
                SmSymbol symbol = symbolManager.findSymbol(symbolCode);
                if (symbol == null)
                    return;
                SmAccountManager accountManager = SmAccountManager.getInstance();
                SmAccount account = accountManager.getDefaultAccount(symbolCode);
                if (account == null)
                    return;
                SmOrderRequest request = new SmOrderRequest();
                request.orderType = SmOrderType.New;
                request.accountNo = account.accountNo;
                request.symbolCode = symbol.code;
                request.filledCondition = SmFilledCondition.Fas;
                request.fundName = "fund1";
                request.orderAmount = SmTotalOrderManager.defaultOrderAmount;
                request.orderPrice = symbol.quote.C;
                request.oriOrderNo = 0;
                request.password = "1234";
                request.positionType = SmPositionType.Buy;
                request.priceType = SmPriceType.Market;
                request.requestId = SmOrderReqNoGenerator.getId();
                request.symtemName = "system1";
                request.strategyName = "strategy1";
                SmServiceManager serviceManager = SmServiceManager.getInstance();
                serviceManager.requestOrder(request);
            }
        });
        sellBtn = (Button)_chartView.findViewById(R.id.sellBtn);
        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SmUserManager.getInstance().isLoggedIn()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                SmSymbolManager symbolManager = SmSymbolManager.getInstance();
                SmSymbol symbol = symbolManager.findSymbol(symbolCode);
                if (symbol == null)
                    return;
                SmAccountManager accountManager = SmAccountManager.getInstance();
                SmAccount account = accountManager.getDefaultAccount(symbolCode);
                if (account == null)
                    return;
                SmOrderRequest request = new SmOrderRequest();
                request.orderType = SmOrderType.New;
                request.accountNo = account.accountNo;
                request.symbolCode = symbol.code;
                request.filledCondition = SmFilledCondition.Fas;
                request.fundName = "fund1";
                request.orderAmount = SmTotalOrderManager.defaultOrderAmount;
                request.orderPrice = symbol.quote.C;
                request.oriOrderNo = 0;
                request.password = "1234";
                request.positionType = SmPositionType.Sell;
                request.priceType = SmPriceType.Market;
                request.requestId = SmOrderReqNoGenerator.getId();
                request.symtemName = "system1";
                request.strategyName = "strategy1";
                SmServiceManager serviceManager = SmServiceManager.getInstance();
                serviceManager.requestOrder(request);
            }
        });

        initPosition();

        return _chartView;
    }

    public void changeChartType(){
        Intent intent = new Intent(getActivity(),ChartChangeActivity.class);
        SmArgManager argManager = SmArgManager.getInstance();
        argManager.registerToMap("seriesType","chartFragment",smChartFragment);
        startActivity(intent);
    }

    public void showIndicator(){
        Intent intent = new Intent(getActivity(),SmIndexSelector.class);
        SmArgManager argManager = SmArgManager.getInstance();
        argManager.registerToMap("showIndicator","chartFragment",smChartFragment);
        startActivity(intent);
    }

    public void showLine(){
        Intent intent = new Intent(getActivity(), LineSelectActivity.class);
        SmArgManager argManager = SmArgManager.getInstance();
        argManager.registerToMap("showLine","chartFragment",smChartFragment);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        insertNestedFragment();
    }

    private void insertNestedFragment() {
        smChartFragment = SmChartFragment.newInstance("mock_main");
        //smChartFragment.createOrderAnnotations();

        _leftChartFragment = SmChartFragment.newInstance("mock_left");
        _leftChartFragment.setChartType(SmChartType.MIN);
        _leftChartFragment.setChartCycle(5);
        _rightChartFragment = SmChartFragment.newInstance("mock_right");
        _rightChartFragment.setChartType(SmChartType.MIN);
        _rightChartFragment.setChartCycle(10);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.mainChartContainer, smChartFragment);
        transaction.replace(R.id.leftChartContainer, _leftChartFragment);
        transaction.replace(R.id.rightChartContainer, _rightChartFragment);
        transaction.replace(R.id.option_container, _optionFragment);
        transaction.commit();



        /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainChartContainer, smChartFragment());
        fragmentTransaction.add(R.id.leftChartContainer, _leftChartFragment);
        fragmentTransaction.add(R.id.rightChartContainer, _rightChartFragment);
        fragmentTransaction.add(R.id.option_container, _optionFragment);
        //fragmentTransaction.replace(R.id.btnContainer, _btnChartFragment);
        fragmentTransaction.commit();*/
        _optionFragment.setParentFragment(this);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("onDestroy", "onDestroy");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onstart", "onstart");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (false) {
            Bundle savedInstance = getArguments();
        }
    }

    @NonNull
    private synchronized Action1<SmSymbol> onUpdateSymbol() {
        return new Action1<SmSymbol>() {
            @Override
            public void execute(final SmSymbol symbol) {
                if (symbol == null || !symbolCode.equals(symbol.code))
                    return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //UpdateChange(symbol);
                    }
                });
                if (smChartFragment != null) {
                    smChartFragment.onUpdateSymbol(symbol);
                }
            }
        };
    }

    /*public void UpdateChange(SmSymbol symbol){
        double gap = symbol.quote.gapToPreday / Math.pow(10, symbol.decimal);
        String ratioText = symbol.quote.sign_to_preday;
        ratioText += Double.toString(gap);
        ratioText += "(";
        ratioText += symbol.quote.ratioToPrday;
        ratioText += "%)";

        textCloseRatio.setText(ratioText);

        double closeValue = symbol.quote.C;
        closeValue = closeValue / Math.pow(10, symbol.decimal);

        textCloseValue.setText(Double.toString(closeValue));

        if(symbol.quote.sign_to_preday.equals("-")){
            textCloseRatio.setTextColor(Color.parseColor("#468ACB"));
        } else {
            textCloseRatio.setTextColor(Color.parseColor("#DD4C69"));
            arrowImg.setImageResource(R.drawable.ic_arrow_drop_up_red_24dp);
        }

    }*/



    /*@Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
        // }*/



    public SmChartFragment getsmChartFragment() {
        return smChartFragment;
    }

    public void setsmChartFragment(SmChartFragment smChartFragment) {
        this.smChartFragment = smChartFragment;
    }

    private synchronized Action1<SmOrder> onNewOrder() {
        return new Action1<SmOrder>() {
            @Override
            public void execute(final SmOrder order) {
                if (order == null || symbolCode.compareTo(order.symbolCode) != 0)
                    return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onOrder(order);
                    }
                });
            }
        };
    }


    public void onOrder(SmOrder order) {
        if (order == null)
            return;
        initPosition();
        smChartFragment.onNewOrder(order);
    }

    public void changeSymbol(Button symSelectBtn) {
        Intent intent = new Intent(getActivity(),SmSymbolSelector.class);
        SmArgManager argManager = SmArgManager.getInstance();
        argManager.registerToMap("mock_symbol_select", "fragment", this);
        argManager.registerToMap("mock_symbol_select", "old_symbol", symbolCode);
        argManager.registerToMap("mock_symbol_select", "clicked_button", symSelectBtn);
        argManager.registerToMap("mock_symbol_select", "old_market_index", marketIndex);
        argManager.registerToMap("mock_symbol_select", "old_symbol_index", selectedIndex);
        startActivity(intent);
    }

    public void onChangeSymbol(SmSymbol symbol) {
        if (symbol == null)
            return;
        symbolCode = symbol.code;
        if (smChartFragment != null)
            smChartFragment.onSymbolChanged(symbolCode);
        if (_leftChartFragment != null)
            _leftChartFragment.onSymbolChanged(symbolCode);
        if (_rightChartFragment != null)
            _rightChartFragment.onSymbolChanged(symbolCode);

        SmArgManager argManager = SmArgManager.getInstance();
        TextView textView = (TextView) argManager.getVal("mock_symbol_select", "clicked_button");
        if (textView != null)
            textView.setText(symbol.seriesNmKr);
        //unregister
        argManager.unregisterFromMap("mock_symbol_select");

        TextView positionTxt = (TextView)argManager.getVal("positionList","mockTxt");
        if(positionTxt != null)
            positionTxt.setText(symbol.seriesNmKr);

        argManager.unregisterFromMap("positionList");

        initPosition();
    }

    //포지션 목록
    /*public void onChangeSymbol(String symbolCode){
        if(symbolCode == null)
            return;
        smChartFragment.onSymbolChanged(symbolCode);
        _leftChartFragment.onSymbolChanged(symbolCode);
        _rightChartFragment.onSymbolChanged(symbolCode);

        SmArgManager argManager = SmArgManager.getInstance();
        TextView textView = (TextView) argManager.getVal("mock_symbol_select", "clicked_button");
        if (textView != null)
            textView.setText(symbolCode);
        argManager.unregisterFromMap("mock_symbol_select");
    }*/

    private synchronized Action1<SmPosition> onUpdatePosition() {
        return new Action1<SmPosition>() {
            @Override
            public void execute(final SmPosition position) {
                if (position == null || !symbolCode.equals(position.symbolCode))
                    return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updatePosition(position);
                    }
                });
            }
        };
    }

    private void clearPositionInfo() {
        if (open_qty != null)
            open_qty.setText("");
        if (average_text != null)
            average_text.setText("");
        if (position_profit != null)
            position_profit.setText("");
    }

    private void updatePosition(SmPosition position) {
        if (position == null || position.openQty == 0) {
            clearPositionInfo();
            return;
        }
        if (open_qty != null) {
            if (position.openQty < 0)
                open_qty.setTextColor(Color.BLUE);
            else if (position.openQty > 0)
                open_qty.setTextColor(Color.RED);
            else
                open_qty.setTextColor(Color.WHITE);
            open_qty.setText(Integer.toString(position.openQty));
        }
        if (average_text != null) {
            average_text.setText(String.format("%,.2f", position.avgPrice));
        }
        if (position_profit != null) {
            double total_profit = position.openPL;
            if (0 > total_profit)
                position_profit.setTextColor(Color.BLUE);
            else if (0 < total_profit)
                position_profit.setTextColor(Color.RED);
            else
                position_profit.setTextColor(Color.WHITE);
            String symbol_code = position.symbolCode;
            char second = symbol_code.charAt(1);
            String sign;
            if (isDigit(second)) {
                sign = Currency.getInstance(Locale.KOREA).getSymbol();
            } else {
                sign = Currency.getInstance(Locale.US).getSymbol();
            }
            String format = sign + "%,.0f";
            position_profit.setText(String.format(format, total_profit));
        }
    }

    @NonNull
    private synchronized Action1<SmChartDataItem> onUpdatePrice() {
        return new Action1<SmChartDataItem>() {
            @Override
            public void execute(final SmChartDataItem price) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SmTotalPositionManager totalPositionManager = SmTotalPositionManager.getInstance();
                        SmPosition position = totalPositionManager.getDefaultPositon(symbolCode);
                        updatePosition(position);
                    }
                });
            }
        };
    }

    public SmListenerManger optionListner = new SmListenerManger();

    public void changeChartData(SmChartTypeOption option) {
        SmChartData chartData = smChartFragment.getChartData();
        if (chartData == null)
            return;
        SmChartType chartType = SmChartType.MIN;
        String symbolCode = chartData.symbolCode;
        int cycle = 1;
        switch (option) {
            case M1:
                chartType = SmChartType.MIN;
                cycle = 1;
                break;
            case M5:
                cycle = 5;
                chartType = SmChartType.MIN;
                break;
            case M15:
                cycle = 15;
                chartType = SmChartType.MIN;
                break;
            case M30:
                cycle = 30;
                chartType = SmChartType.MIN;
                break;
            case M60:
                cycle = 60;
                chartType = SmChartType.MIN;
                break;
            case D1:
                cycle = 1;
                chartType = SmChartType.DAY;
                break;
            case W1:
                cycle = 1;
                chartType = SmChartType.WEEK;
                break;
            case MO1:
                cycle = 1;
                chartType = SmChartType.MON;
                break;
            default:
                break;
        }


        SmChartDataManager chartDataManager = SmChartDataManager.getInstance();
        String dataKey = chartDataManager.makeChartDataKey(symbolCode, chartType.ordinal(), cycle);
        if (dataKey == chartData.getDataKey())
            return;

        SmChartData newChartData = chartDataManager.createChartData(symbolCode, chartType, cycle);
        smChartFragment.changeChartData(newChartData);
    }
}
