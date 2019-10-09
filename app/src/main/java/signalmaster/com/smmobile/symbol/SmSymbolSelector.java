package signalmaster.com.smmobile.symbol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.autoorder.SmAutoFragment;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.favorite.SmFavoriteFragment;
import signalmaster.com.smmobile.market.SmMarket;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.mock.SmPrChartFragment;
import signalmaster.com.smmobile.mock.SmSymSelectAdapter;
import signalmaster.com.smmobile.order.SmTotalOrderManager;
import signalmaster.com.smmobile.portfolio.SmPortfolioFragment;

public class SmSymbolSelector extends AppCompatActivity {

    //portfolio
    TextView clickSymbol,valueSymbol;

    //spinner
    Spinner symbolSpinner = null;
    ArrayAdapter<String> adapter;
    //recyclerview
    RecyclerView _symSelectRecyclerView;
    SmSymSelectAdapter _symSelectAdapter = null;
    ImageView closeImg;

    EditText search_edit;

    Button okBtn;

    SmMarketManager smMarketManager = SmMarketManager.getInstance();
    private ArrayList<SmMarket> _marketList= (smMarketManager.get_marketList());

    private SmPrChartFragment prChartFragment = null;
    private SmFavoriteFragment favoriteFragment = null;
    private SmPortfolioFragment portfolioFragment = null;
    private int old_market_index = 0;
    private int old_symbol_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.symbol_selector);



        SmArgManager argManager = SmArgManager.getInstance();
        /*old_market_index = (Integer) argManager.getVal("mock_symbol_select", "old_market_index");
        old_symbol_index = (Integer) argManager.getVal("mock_symbol_select", "old_symbol_index");*/

        //favoriteFragment = (SmFavoriteFragment) argManager.getVal("mock_symbol_select", "favorite_fragment");




        SmSymbolManager symbolManager = SmSymbolManager.getInstance();


        ArrayList<String> symbolList = new ArrayList<>();
        for(int i=0;i<_marketList.size();i++){
            symbolList.add(_marketList.get(i).name);
        }

        closeImg = findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        symbolSpinner = (Spinner)findViewById(R.id.symbolSpinner);
        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.symbol_selector_item,symbolList);
        adapter.setDropDownViewResource(R.layout.symbol_selector_item);
        symbolSpinner.setAdapter(adapter);


        //spinner 값 출력
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Object result = prefs.getString("object", "0");
        String trans = (String)result;
       // symbolSpinner.setSelection(result);
        symbolSpinner.setSelection(Integer.parseInt(trans));

        search_edit = (EditText)findViewById(R.id.search_edit);
        search_edit.clearComposingText();

        _symSelectRecyclerView = (RecyclerView)findViewById(R.id.symRecyclerView);
        //spinner 이벤트 리스너
        symbolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences selectPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String transSelect = selectPrefs.getString("selectObject","0");

                String mark_name = "";
                if(symbolSpinner.getSelectedItemPosition() == 0){
                    ArrayList<SmSymbol> symList = smMarketManager.GetSymbolListByMarket("금리");
                    _symSelectAdapter = new SmSymSelectAdapter(symList);
                    _symSelectRecyclerView.setAdapter(_symSelectAdapter);

                    mark_name = "금리";
                }
                else if (symbolSpinner.getSelectedItemPosition() == 1){
                    ArrayList<SmSymbol> symList = smMarketManager.GetSymbolListByMarket("금속");
                    _symSelectAdapter = new SmSymSelectAdapter(symList);
                    _symSelectRecyclerView.setAdapter(_symSelectAdapter);

                    mark_name = "금속";
                }
                else if (symbolSpinner.getSelectedItemPosition() == 2){
                    ArrayList<SmSymbol> symList = smMarketManager.GetSymbolListByMarket("농산물");
                    _symSelectAdapter = new SmSymSelectAdapter(symList);
                    _symSelectRecyclerView.setAdapter(_symSelectAdapter);

                    mark_name = "농산물";
                }
                else if (symbolSpinner.getSelectedItemPosition() == 3){
                    ArrayList<SmSymbol> symList = smMarketManager.GetSymbolListByMarket("에너지");
                    _symSelectAdapter = new SmSymSelectAdapter(symList);
                    _symSelectRecyclerView.setAdapter(_symSelectAdapter);
                    mark_name = "에너지";
                }
                else if (symbolSpinner.getSelectedItemPosition() == 4){
                    ArrayList<SmSymbol> symList = smMarketManager.GetSymbolListByMarket("지수");
                    _symSelectAdapter = new SmSymSelectAdapter(symList);
                    _symSelectRecyclerView.setAdapter(_symSelectAdapter);
                    mark_name = "지수";
                }
                else if (symbolSpinner.getSelectedItemPosition() == 5){
                    ArrayList<SmSymbol> symList = smMarketManager.GetSymbolListByMarket("축산물");
                    _symSelectAdapter = new SmSymSelectAdapter(symList);
                    _symSelectRecyclerView.setAdapter(_symSelectAdapter);
                    mark_name = "축산물";
                }
                else if (symbolSpinner.getSelectedItemPosition() == 6){
                    ArrayList<SmSymbol> symList = smMarketManager.GetSymbolListByMarket("통화");
                    _symSelectAdapter = new SmSymSelectAdapter(symList);
                    _symSelectRecyclerView.setAdapter(_symSelectAdapter);
                    mark_name = "통화";
                }


                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                _symSelectRecyclerView.setLayoutManager(layoutManager);

                SmArgManager argMgr = SmArgManager.getInstance();
                argMgr.registerToMap("symbol_selector_popup", "market_name", mark_name);

                _symSelectAdapter.setSelectedPosition(Integer.parseInt(transSelect));


                //spinner 값 저장
                Object obj = parent.getSelectedItemPosition();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefsEditor = prefs.edit();
                prefsEditor.putString("object", obj.toString());
                prefsEditor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //리사이클러뷰
        /*SmSymbolManager symbolManager = SmSymbolManager.getInstance();
        SmSymbol old_symbol = symbolManager.findSymbol(sym_code.toString());
        String marketType = old_symbol.marketType;*/

        //old_symbol -> null 자동주문에서 종목선택시 수정해야함.
        //favorite 도 마찬가지 null
        //String marketType = old_symbol.marketType;

        /*ArrayList<SmSymbol> symList = smMarketManager.GetSymbolListByMarket("금리");
        _symSelectAdapter = new SmSymSelectAdapter(symList);
        _symSelectRecyclerView.setAdapter(_symSelectAdapter);*/


        okBtn = (Button)findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmArgManager argManager = SmArgManager.getInstance();

                /*SmChartFragment chart_fragment = (SmChartFragment) argManager.getVal("symbol_selector_popup", "source_chart");
                if (chart_fragment != null) {
                    onChartSymbolSelect(chart_fragment);
                }*/
                //모의 매매
                prChartFragment = (SmPrChartFragment) argManager.getVal("mock_symbol_select", "fragment");
                if (prChartFragment != null) {
                    prChartFragment.onChangeSymbol(_symSelectAdapter.getSelectSymbol());
                    SmTotalOrderManager.getInstance().setOrderSymbol(_symSelectAdapter.getSelectSymbol());
                    finish();
                }

                //관심 종목
                favoriteFragment = (SmFavoriteFragment)argManager.getVal("mock_symbol_select","favorite_fragment");
                if(favoriteFragment != null){
                    //favoriteFragment.onSymbolAdded(_symSelectAdapter.getSelectSymbol());
                    favoriteFragment.onChangeSymbol(_symSelectAdapter.getSelectSymbol());
                    finish();
                }

                //포트폴리오
                portfolioFragment = (SmPortfolioFragment)argManager.getVal("portfolio_symbol_select","portfolio_fragment");
                clickSymbol = (TextView)argManager.getVal("portfolio_symbol_select","clickSymbol");
                valueSymbol = (TextView)argManager.getVal("portfolio_symbol_select","valueSymbol");
                if(portfolioFragment != null && clickSymbol != null){
                    portfolioFragment.onSelectSymbol(_symSelectAdapter.getSelectSymbol(),clickSymbol,valueSymbol);
                    finish();
                }


                /*SmChartFragment chart_fragment = (SmChartFragment)argManager.getVal("mock_symbol_select","chart");
                if (chart_fragment != null) {
                    onChartSymbolSelect(chart_fragment);
                }*/


                /*try {
                    SmFavoriteFragment favor_fragment = (SmFavoriteFragment) argManager.getVal("symbol_selector_popup", "source_fragment");
                    if (favor_fragment != null) {
                        onFavoriteSymbolAdded(favor_fragment);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }*/

                //주문창
                SmAutoFragment order_fragment = (SmAutoFragment) argManager.getVal("mock_symbol_select", "auto_fragment");
                if (order_fragment != null) {
                    order_fragment.onAutoSymbolChanged(_symSelectAdapter.getSelectSymbol());
                    SmTotalOrderManager.getInstance().setOrderSymbol(_symSelectAdapter.getSelectSymbol());
                    finish();
                }
                /*SmAutoFragment order_fragment = (SmAutoFragment) argManager.getVal("symbol_selector_popup", "auto_fragment");
                if (order_fragment != null) {
                    onOrderSymbolAdded(order_fragment);
                }*/


                //리스트 선택 저장
                Object selectObj = _symSelectAdapter.getSelectedPosition();
                SharedPreferences selectPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefsEditor = selectPrefs.edit();
                prefsEditor.putString("selectObject",selectObj.toString());
                prefsEditor.commit();

            }
        });

        /*//리스트 선택 값 출력
        _symSelectAdapter = new SmSymSelectAdapter();
        SharedPreferences selectPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Object resultSelect = selectPrefs.getString("selectObject","0");
        String transSelect = (String)resultSelect;
        _symSelectAdapter.setSelectedPosition(Integer.parseInt(transSelect));
        //_symSelectAdapter.setSelect(Integer.parseInt(transSelect));
        //_symSelectAdapter.notifyDataSetChanged();*/



    }

    //주문창
    /*void onOrderSymbolAdded(SmAutoFragment chart_fragment) {
        if (chart_fragment == null) {
            finish();
            return;
        }
        SmArgManager argManager = SmArgManager.getInstance();
        Object sym_code = argManager.getVal("mock_symbol_select", "symbol_code");
        if (sym_code == null) {
            finish();
            return;
        }
        chart_fragment.onAutoSymbolChanged(sym_code.toString());
        finish();

    }*/
    /*void onOrderSymbolAdded(SmAutoFragment chart_fragment) {
        if (chart_fragment == null) {
            finish();
            return;
        }
        SmArgManager argManager = SmArgManager.getInstance();
        Object sym_code = argManager.getVal("mock_symbol_select", "symbol_code");
        if (sym_code == null) {
            finish();
            return;
        }
        chart_fragment.onAutoSymbolChanged(sym_code.toString());
        finish();

    }*/



    /*void onFavoriteSymbolAdded(SmFavoriteFragment fragment) {
        if (fragment == null) {
            finish();
            return;
        }
        SmArgManager argManager = SmArgManager.getInstance();
        Object sym_code = argManager.getVal("symbol_selector_popup", "symbol_code");
        if (sym_code == null) {
            finish();
            return;
        }

        fragment.onSymbolAdded(sym_code.toString());
        finish();
    }

    void onChartSymbolSelect(SmChartFragment chart_fragment) {
        if (chart_fragment == null) {
            finish();
            return;
        }
        SmArgManager argManager = SmArgManager.getInstance();
        Object sym_code = argManager.getVal("symbol_selector_popup", "symbol_code");
        *//*SmSymbolManager symbolManager = SmSymbolManager.getInstance();
        SmSymbol old_symbol = symbolManager.findSymbol(old_symbol_code);*//*
        if (sym_code == null) {
            finish();
            return;
        }

        chart_fragment.onSymbolChanged(sym_code.toString());
        finish();

    }*/
}
