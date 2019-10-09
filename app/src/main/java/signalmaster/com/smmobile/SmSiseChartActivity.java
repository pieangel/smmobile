package signalmaster.com.smmobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scichart.charting.model.dataSeries.IOhlcDataSeries;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.core.common.Action1;

import java.util.ArrayList;

import signalmaster.com.smmobile.Util.SmLayoutManager;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.chart.SmChartType;
import signalmaster.com.smmobile.chart.SmSeriesType;
import signalmaster.com.smmobile.data.PriceBar;
import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.data.SmChartDataManager;
import signalmaster.com.smmobile.data.SmChartDataService;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.ui.SmChartTypeOption;

public class SmSiseChartActivity extends AppCompatActivity implements View.OnClickListener, Runnable {
    SmChartFragment _prChartFragment = null;
    SmOptionFragment _optionFragment = null;
    SmChartFragment smChartFragment = null;
    SmChartFragment smHistoryFragment = null;
    RecyclerView _recyclerView,_recyclerView2 = null;
    RecyclerView.Adapter _siseShowAdapter = null;
    SiseListAdapter _siseListAdapter = null;

    ImageView leftImg,arrowImg,changeChartImg;
    TextView symbolName,textCloseValue,textCloseRatio;

    SmSeriesType selectedPosition = SmSeriesType.CandleStick;

    /*TableRow*/
    TextView status1,status2,status3,status4;

    ChangeJipyoType changeStatusType;

    //ProgressBar
    FlikerProgressBar flikerProgressBar;


    Thread downLoadThread;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            flikerProgressBar.setProgress(msg.arg1);
            if(msg.arg1 == 100){
                flikerProgressBar.finishLoad();
            }
        }
    };

    public SmSiseChartActivity() {
        /*_prChartFragment = new PrChartFragmentNOTUSE();
        _optionFragment = new SmOptionFragment();*/
    }

    private String symbolCode;


    RecyclerViewList recyclerViewList = new RecyclerViewList();
    private ArrayList<String> _mainTitleList = recyclerViewList.get_mainTitleList();
    private ArrayList<String> _subTitleList = recyclerViewList.get_subTitleList();

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sm_sise_chart_activity);

        smChartFragment = SmChartFragment.newInstance("sise_main");
        _prChartFragment = SmChartFragment.newInstance("sise_sub");
        smHistoryFragment = SmChartFragment.newInstance("sise_history");
        SmLayoutManager smLayoutManager = SmLayoutManager.getInstance();
        smLayoutManager.register("sise_main",smChartFragment);
        smLayoutManager.register("sise_sub",_prChartFragment);
        smLayoutManager.register("sise_history",smHistoryFragment);

        //_prChartFragment = new SmChartFragment();
        _optionFragment = new SmOptionFragment();
        //smChartFragment = new SmChartFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainChartContainer, smChartFragment);
        fragmentTransaction.replace(R.id.option_container, _optionFragment);
        fragmentTransaction.replace(R.id.techContainer, _prChartFragment);
        fragmentTransaction.replace(R.id.historyContainer,smHistoryFragment);
        fragmentTransaction.commit();

        /*_recyclerView2 = findViewById(R.id.siseRecyclerView);
        _siseListAdapter = new SiseListAdapter();
        _recyclerView2.setAdapter(_siseListAdapter);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        _recyclerView2.setLayoutManager(layoutManager2);*/




        _recyclerView = findViewById(R.id.sise_show_container);
        _siseShowAdapter = new SiseShowAdapter(getApplication(), _mainTitleList, _subTitleList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
        _recyclerView.setAdapter(_siseShowAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(layoutManager);



        leftImg = findViewById(R.id.leftImg);
        leftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arrowImg = findViewById(R.id.arrowImg);


        symbolName = (TextView)findViewById(R.id.symbolName);
        textCloseValue = (TextView)findViewById(R.id.textCloseValue);
        textCloseRatio = (TextView)findViewById(R.id.textCloseRatio);
        Intent intent = getIntent();
        String symbol_code = intent.getExtras().getString("symbolCode");
        String name = intent.getExtras().getString("symbolName");
        Double closeValue = intent.getDoubleExtra("textCloseValue",0.0);
        String closeRatio = intent.getExtras().getString("textCloseRatio");
        String quoteSign = intent.getExtras().getString("quoteSign");

        //_prChartFragment.setSymbolCode(symbol_code);
        //getIntent().putExtra("symbol_code", symbol_code);
        _optionFragment.setSiseChartActivity(this);
        symbolCode = symbol_code;

        SmChartDataService chartDataService = SmChartDataService.getInstance();
        chartDataService.subscribeSise(onUpdateSymbol(), this.getClass().getSimpleName());

        if(quoteSign == null || name == null || closeValue == null || closeRatio ==null ){
            return;
        }

        //데이타 값마다 색을 다르게
        if (quoteSign.equals("-")) {
            textCloseRatio.setTextColor(Color.parseColor("#468ACB"));
        }else if(closeRatio.equals("+0.0(0.0%)")){
            textCloseRatio.setTextColor(Color.parseColor("#FFFFFF"));
            arrowImg.setImageResource(R.drawable.ic_drag_handle_white_24dp);
        }
        else {
            textCloseRatio.setTextColor(Color.parseColor("#DD4C69"));
            arrowImg.setImageResource(R.drawable.ic_arrow_drop_up_red_24dp);
        }

        symbolName.setText(name);
        textCloseValue.setText(closeValue.toString());
        textCloseRatio.setText(closeRatio);

        changeChartImg = (ImageView)findViewById(R.id.changeChartImg);
        changeChartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPosition == SmSeriesType.CandleStick ) {
                    selectedPosition = SmSeriesType.MountainView;
                    smChartFragment.changeSeriesType(selectedPosition);
                    //smChartFragment.changeSeriesType(selectedPosition);
                }else if(selectedPosition == SmSeriesType.MountainView){
                    selectedPosition = SmSeriesType.CandleStick;
                    smChartFragment.changeSeriesType(selectedPosition);
                }
            }
        });

        //프로그레스바
        flikerProgressBar = (FlikerProgressBar) findViewById(R.id.flikerbar);
        flikerProgressBar.setOnClickListener(this);
        downLoad();
    }

    public void reLoad(View view) {

        downLoadThread.interrupt();
        flikerProgressBar.reset();
        downLoad();
    }

    private void downLoad() {
        downLoadThread = new Thread();
        downLoadThread.start();
    }

    @Override
    public void onClick(View v) {
        if(!flikerProgressBar.isFinish()){
            flikerProgressBar.toggle();

            if(flikerProgressBar.isStop()){
                downLoadThread.interrupt();
            } else {
                downLoad();
            }

        }
    }

    public void run() {
        try {
            while( ! downLoadThread.isInterrupted()){
                float progress = flikerProgressBar.getProgress();
                progress  += 2;
                Thread.sleep(200);
                Message message = handler.obtainMessage();
                message.arg1 = (int) progress;
                handler.sendMessage(message);
                if(progress == 100){
                    break;
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /*public void ChangeStatus(ChangeJipyoType changeStatusType){

        status1 = (TextView)findViewById(R.id.status1);
        status2 = (TextView)findViewById(R.id.status2);
        status3 = (TextView)findViewById(R.id.status3);
        status4 = (TextView)findViewById(R.id.status4);

        TableRow.LayoutParams rowSpanLayout = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        rowSpanLayout.span = 4;

        TableLayout tableLayout;
        tableLayout = (TableLayout)findViewById(R.id.statusTable);
        tableLayout.setLayoutParams(rowSpanLayout);

        switch (changeStatusType){
            case red100:
                break;
            case red75:
                break;
            case red50:
                break;
            case red25:
                break;
            case redZero:
                break;
        }

    }*/



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
                        UpdateChange(symbol);
                    }
                });
            }
        };
    }

    public void UpdateChange(SmSymbol symbol){
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

        double vh = 0.0, vl = 0.0, vo = 0.0, vc = 0.0;
        double div = Math.pow(10, symbol.decimal);
        vh = (symbol.quote.H / div);
        vl = (symbol.quote.L / div);
        vo = (symbol.quote.O / div);
        vc = (symbol.quote.C / div);

        //거래변동 이전종가 시가 거래량 업데이트

        String quoteLow = String.format(symbol.getFormat(), vl);
        String quoteHigh = String.format(symbol.getFormat(), vh);
        String quoteClose = String.format(symbol.getFormat(), vc);
        String quoteOpen = String.format(symbol.getFormat(), vo);
        String quoteValue = String.format("%,d",symbol.quote.accVolume);

        String deltaDay = String.format(symbol.getFormat(), vh - vl);
        _subTitleList.set(0,deltaDay);
        _subTitleList.set(1,quoteClose);
        _subTitleList.set(2,quoteOpen);
        _subTitleList.set(3,quoteValue);

        /*_subTitleList.set(0,symbol.quote.L + "  -  " + symbol.quote.H);
        _subTitleList.set(1,String.valueOf(symbol.quote.C));
        _subTitleList.set(2,String.valueOf(symbol.quote.O));
        _subTitleList.set(3,String.valueOf(symbol.quote.V));*/
        _siseShowAdapter.notifyDataSetChanged();
        //_recyclerView.setAdapter(_siseShowAdapter);


    }





    @Override
    public void onBackPressed(){
        finish();
        super.onBackPressed();
    }

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
