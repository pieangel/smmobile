package signalmaster.com.smmobile.autoorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.rayject.table.model.ICellData;
import com.rayject.table.model.ISheetData;
import com.rayject.table.model.RichText;
import com.rayject.table.util.TableViewConfigure;
import com.rayject.table.view.TableView;
import com.scichart.core.common.Action1;

import java.util.ArrayList;

import signalmaster.com.smmobile.login.LoginActivity;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.account.SmAccount;
import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.data.SmChartDataService;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.mock.SmPrChartFragment;
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
import signalmaster.com.smmobile.symbol.SmHoga;
import signalmaster.com.smmobile.symbol.SmHogaItem;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;
import signalmaster.com.smmobile.symbol.SmSymbolSelector;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.userinfo.SmUserManager;


import static com.scichart.core.utility.Dispatcher.runOnUiThread;

public class SmAutoFragment extends Fragment {

    public static SmAutoFragment newInstance() {
        SmAutoFragment fragment = new SmAutoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SmPrChartFragment prChartFragment = null;
    public int marketIndex = 0;
    public int selectedIndex = 0;
    private boolean mPaginationEnabled = false;


    Button buyBtn, sellBtn;
    /*RecyclerView orderRecyclerView;
    OrderRecyclerViewAdapter orderRecyclerViewAdapter;*/

    public SmAutoFragment() {

    }

    SmAutoFragment self = this;

    /*private RecyclerView orderRecyclerView;
    private RecyclerView.Adapter orderRecyclerViewAdapter;
    RecyclerViewList recyclerViewList = new RecyclerViewList();
    private ArrayList<String> orderTitle = (recyclerViewList.getOrderTitleList());
    private ArrayList<String> orderTitleValue = (recyclerViewList.getOrderTitleValueList());*/


    int rowCount = 15;
    int colCount = 5;
    TableView mTableView;


    @Override
    public void onResume(){
        super.onResume();

        int i = 0;
        getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.sm_auto_fragment, null);

        mTableView = (TableView) view.findViewById(R.id.table_view);

        ISheetData sheet= SheetTemplate1.get(getContext(), rowCount, colCount, symbolCode);

        Display display = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        sheet.setColumnWidth(0, width/5);
        sheet.setColumnWidth(1, width/5);
        sheet.setColumnWidth(2, width/5);
        sheet.setColumnWidth(3, width/5);
        sheet.setColumnWidth(4, width/5);

        for(int i = 0; i < rowCount; ++i)
            sheet.setRowHeight(i, 100);

        mTableView.setSheetData(sheet);
        TableViewConfigure configure = new TableViewConfigure();
        configure.setShowHeaders(false);
        configure.setEnableResizeRow(false);
        configure.setEnableResizeColumn(false);
        configure.setEnableSelection(false);
        mTableView.setConfigure(configure);

        SmSymbol symbol = SmTotalOrderManager.getInstance().getOrderSymbol(getContext());
        symbolCode = symbol.code;

        SmChartDataService chartDataService = SmChartDataService.getInstance();
        chartDataService.subscribeHoga(onUpdateHoga(), this.getClass().getSimpleName());
        chartDataService.subscribePosition(onUpdatePosition(), this.getClass().getSimpleName());
        chartDataService.subscribeSise(onUpdateSise(), this.getClass().getSimpleName());

        //매수 매도 버튼 이벤트
        buyBtn = (Button) view.findViewById(R.id.buyBtn);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SmUserManager.getInstance().isLoggedIn()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                SmAccountManager accountManager = SmAccountManager.getInstance();
                SmAccount account = accountManager.getDefaultAccount();
                if (account == null)
                    return;
                SmSymbol symbol1 = SmSymbolManager.getInstance().findSymbol(symbolCode);
                if (symbol1 == null)
                    return;

                SmOrderRequest request = new SmOrderRequest();
                request.orderType = SmOrderType.New;
                request.accountNo = account.accountNo;
                request.symbolCode = symbol1.code;
                request.filledCondition = SmFilledCondition.Fas;
                request.fundName = "fund1";
                request.orderAmount = SmTotalOrderManager.defaultOrderAmount;
                request.orderPrice = symbol1.quote.C;
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
        sellBtn = (Button) view.findViewById(R.id.sellBtn);
        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SmUserManager.getInstance().isLoggedIn()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                SmAccountManager accountManager = SmAccountManager.getInstance();
                SmAccount account = accountManager.getDefaultAccount();
                if (account == null)
                    return;
                SmSymbol symbol1 = SmSymbolManager.getInstance().findSymbol(symbolCode);
                if (symbol1 == null)
                    return;

                SmOrderRequest request = new SmOrderRequest();
                request.orderType = SmOrderType.New;
                request.accountNo = account.accountNo;
                request.symbolCode = symbol1.code;
                request.filledCondition = SmFilledCondition.Fas;
                request.fundName = "fund1";
                request.orderAmount = SmTotalOrderManager.defaultOrderAmount;
                request.orderPrice = symbol1.quote.C;
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

        chartDataService.subscribeOrder(onNewOrder(), this.getClass().getSimpleName());

        initPosition();
        return view;
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
    }

    private void initPosition() {
        SmAccountManager accountManager = SmAccountManager.getInstance();
        String acno = accountManager.getDefaultAccountNo();
        if (acno == null)
            return;
        SmTotalPositionManager totalPositionManager = SmTotalPositionManager.getInstance();
        SmPosition position = totalPositionManager.findPosition(acno, symbolCode);
        if (position == null)
            return;
        updatePosition(position);
    }


    public void onAutoSymbolChanged(SmSymbol newSymbolCode) {
        if (newSymbolCode == null) {
            return;
        }

        SmSymbolManager symMgr = SmSymbolManager.getInstance();
        SmSymbol sym = symMgr.findSymbol(newSymbolCode.code);
        if (sym != null) {
            symbolCode = sym.code;
            //orderTitleValue.set(0, symbolCode);
            SmArgManager argManager = SmArgManager.getInstance();
            TextView txt = (TextView)argManager.getVal("mock_symbol_select","clicked_button");
            if(txt != null)
                txt.setText(sym.seriesNmKr);

            TextView positionTxt = (TextView)argManager.getVal("positionList","orderTxt");
            if(positionTxt != null)
                positionTxt.setText(sym.seriesNmKr);

            SmTotalPositionManager totalPositionManager = SmTotalPositionManager.getInstance();
            SmPosition position = totalPositionManager.getDefaultPositon(symbolCode);
            updatePosition(position);
            updateSise(sym);
            updateHoga(sym);
        }
    }

    public void setSymbol(SmSymbol symbol) {
        if (symbol == null)
            return;
        symbolCode = symbol.code;
        //orderTitleValue.set(0, symbolCode);
        SmArgManager argManager = SmArgManager.getInstance();
        TextView txt = (TextView)argManager.getVal("mock_symbol_select","clicked_button");
        if(txt != null)
            txt.setText(symbol.seriesNmKr);

        TextView positionTxt = (TextView)argManager.getVal("positionList","orderTxt");
        if(positionTxt != null)
            positionTxt.setText(symbol.seriesNmKr);

        SmTotalPositionManager totalPositionManager = SmTotalPositionManager.getInstance();
        SmPosition position = totalPositionManager.getDefaultPositon(symbolCode);
        updatePosition(position);
        updateSise(symbol);
        updateHoga(symbol);
    }

    //포지션 목록
    /*public void onAutoSymbolChanged(String sym_code){
        if(symbolCode == null)
            return;

        SmSymbolManager symMgr = SmSymbolManager.getInstance();
        SmSymbol sym = symMgr.findSymbol(sym_code);
        if (sym != null) {
            symbolCode = sym.code;
            //orderTitleValue.set(0, symbolCode);
            SmArgManager argManager = SmArgManager.getInstance();
            TextView txt = (TextView)argManager.getVal("mock_symbol_select","clicked_button");
            if(txt != null)
                txt.setText(sym.seriesNmKr);

            TextView positionTxt = (TextView)argManager.getVal("positionList","orderTxt");
            if(positionTxt != null)
                positionTxt.setText(sym.seriesNmKr);

            SmTotalPositionManager totalPositionManager = SmTotalPositionManager.getInstance();
            SmPosition position = totalPositionManager.getDefaultPositon(symbolCode);
            updatePosition(position);
            updateSise(sym);
            updateHoga(sym);
        }

    }*/

    public void changeSymbol(TextView orderSymSelectTxt){
        Intent intent = new Intent(getActivity(),SmSymbolSelector.class);
        startActivity(intent);
        SmArgManager argManager = SmArgManager.getInstance();
        argManager.registerToMap("mock_symbol_select", "auto_fragment", this);
        argManager.registerToMap("mock_symbol_select", "symbol_code", symbolCode);
        argManager.registerToMap("mock_symbol_select", "clicked_button", orderSymSelectTxt);
    }

    //종목 눌렀을때 그부분을 사용한다.
    public void onChangeSymbol(SmSymbol symbol) {
        if (symbol == null)
            return;
        symbolCode = symbol.code;
        //smChartFragment.onSymbolChanged(symbolCode);
        //_leftChartFragment.onSymbolChanged(symbolCode);
        //_rightChartFragment.onSymbolChanged(symbolCode);

        SmArgManager argManager = SmArgManager.getInstance();
        Button btn = (Button) argManager.getVal("mock_symbol_select", "clicked_button");
        if (btn != null)
            btn.setText(symbol.seriesNmKr);
        //unregister
        argManager.unregisterFromMap("mock_symbol_select");
    }
    private String symbolCode;

    @NonNull
    private synchronized Action1<SmSymbol> onUpdateHoga() {
        return new Action1<SmSymbol>() {
            @Override
            public void execute(final SmSymbol symbol) {
                if (symbol == null || !symbolCode.equals(symbol.code))
                    return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateHoga(symbol);
                        SmTotalPositionManager totalPositionManager = SmTotalPositionManager.getInstance();
                        SmPosition position = totalPositionManager.getDefaultPositon(symbolCode);
                        updatePosition(position);
                    }
                });
            }
        };
    }

    private void resetPositionInfo() {

        for(int i=0; i<colCount;i++) {
            ISheetData sheet = mTableView.getSheet();
            ICellData cell = sheet.getCellData(1, i);
            RichText value = (RichText) cell.getRichTextValue();
            value.setText("-");
            cell.setCellValue(value);
            sheet.updateData();
            mTableView.invalidate();
        }
    }

    private synchronized Action1<SmPosition> onUpdatePosition() {
        return new Action1<SmPosition>() {
            @Override
            public void execute(final SmPosition position) {
                if (position == null || symbolCode.compareTo(position.symbolCode) != 0)
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

    private synchronized Action1<SmSymbol> onUpdateSise() {
        return new Action1<SmSymbol>() {
            @Override
            public void execute(final SmSymbol symbol) {
                if (symbol == null || symbolCode.compareTo(symbol.code) != 0)
                    return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateSise(symbol);
                    }
                });
            }
        };
    }

    private ArrayList<String> orderTitleList = new ArrayList<>();
    /*private void orderTitle(){
        orderTitleList.add()
    }*/

    private void clearPositionInfo() {
        if (mTableView == null)
            return;

        ISheetData sheet = mTableView.getSheet();
        for(int i=0;i<colCount;i++) {
            ICellData cell = sheet.getCellData(1,i);
            if (cell != null) {
                RichText value = (RichText) cell.getRichTextValue();
                value.setText("");
                cell.setCellValue(value);
                sheet.updateData();
                mTableView.invalidate();
            }
        }
    }

    public void updateSise(SmSymbol symbol) {
        if (symbol == null || mTableView == null) {
            clearSiseInfo();
            return;
        }
        ISheetData sheet = mTableView.getSheet();
        double vh = 0.0, vl = 0.0, vo = 0.0, vc = 0.0;
        double div = Math.pow(10, symbol.decimal);
        vh = (symbol.quote.H / div);
        vl = (symbol.quote.L / div);
        vo = (symbol.quote.O / div);
        vc = (symbol.quote.C / div);
        String[] valList = new String[5];
        // 시가
        setData(sheet, 3, 4, String.format(symbol.getFormat() , vo));
        // 고가
        setData(sheet, 4, 4, String.format(symbol.getFormat() , vh));
        // 저가
        setData(sheet, 5, 4, String.format(symbol.getFormat() , vl));
        // 종가
        setData(sheet, 6, 4, String.format(symbol.getFormat() , vc));
        // 시세 현재가
        setData(sheet, 8, 2, String.format(symbol.getFormat() , vc));

        ICellData cell = sheet.getCellData(8, 2);
        if (vc < vo)
            cell.setStyleIndex(SheetTemplate1.sellStyleNo);
        else
            cell.setStyleIndex(SheetTemplate1.buyStyleNo);

        mTableView.invalidate();
    }

    private void clearSiseInfo() {
        if (mTableView == null)
            return;
        ISheetData sheet = mTableView.getSheet();
        for(int i=3;i<7;i++) {
            ICellData cell = sheet.getCellData(i, 4);
            RichText value = (RichText)cell.getRichTextValue();
            value.setText("");
            cell.setCellValue(value);
            sheet.updateData();
            mTableView.invalidate();
        }
    }

    public void updatePosition(SmPosition position) {
        if (position == null || position.openQty == 0) {
            clearPositionInfo();
            return;
        }

        if (position.symbolCode.compareTo(this.symbolCode) != 0)
            return;

        String type = null;
        if (position.positionType.name().equals("None")) {
            type = "";
        } else if (position.positionType.name().equals("Buy")) {
            type = "매수";
        } else if (position.positionType.name().equals("Sell")) {
            type = "매도";
        }

        ISheetData sheet = mTableView.getSheet();
        try {
            // 타입
            setData(sheet, 1, 0, type);
            // 잔고
            setData(sheet, 1, 1, Integer.toString(position.openQty));
            // 평균가
            setData(sheet, 1, 2, String.format("%,.2f", position.avgPrice));
            //평가손익
            setData(sheet, 1, 4, String.format("%,.2f", position.openPL));

            SmSymbol symbol = SmSymbolManager.getInstance().findSymbol(position.symbolCode);
            if (symbol != null) {
                double div = Math.pow(10, symbol.decimal);
                double vc = (symbol.quote.C / div);
                // 포지션 현재가
                setData(sheet, 1, 3, String.format(symbol.getFormat(), vc));
            }
        } catch (Exception e) {
            String error = e.getMessage();
            Log.d("TAG", "updatePosition" + "  code" + symbolCode);
        }


        mTableView.invalidate();
    }

    public void updateHoga(SmSymbol symbol) {
        if (symbol == null || mTableView == null)
            return;
        ISheetData sheet = mTableView.getSheet();

        SmHoga symbol_hoga = symbol.hoga;
        for(int i = 0; i < 5; ++i) {
            // 매수가격
            double div = Math.pow(10, symbol.decimal);
            double price = (symbol.hoga.hogaItem[i].buyPrice / div);
            String price_text = String.format(symbol.getFormat() , price);
            setData(sheet, 9 + i, 2, price_text);
            // 매수 수량
            String qty = Integer.toString(symbol.hoga.hogaItem[i].buyQty);
            setData(sheet, 9 + i, 3, qty);
            // 매수 건수
            String count = Integer.toString(symbol.hoga.hogaItem[i].buyCnt);
            setData(sheet, 9 + i, 4, count);
            // 매도 가격
            price = (symbol.hoga.hogaItem[i].sellPrice / div);
            price_text = String.format(symbol.getFormat() , price);
            setData(sheet, 7 - i, 2, price_text);
            // 매도 수량
            qty = Integer.toString(symbol.hoga.hogaItem[i].sellQty);
            setData(sheet, 7 - i, 1, qty);
            // 매도 건수
            count = Integer.toString(symbol.hoga.hogaItem[i].sellCnt);
            setData(sheet, 7 - i, 0, count);
        }

        setData(sheet, 14, 0,  Integer.toString(symbol.hoga.totSellCnt));
        ICellData cell = sheet.getCellData(14,0);
        cell.setStyleIndex(SheetTemplate1.sellStyle);
        setData(sheet, 14, 1,  Integer.toString(symbol.hoga.totSellQty));
        cell = sheet.getCellData(14,1);
        cell.setStyleIndex(SheetTemplate1.sellStyle);
        int deltaHoga = symbol.hoga.totBuyQty - symbol.hoga.totSellQty;
        setData(sheet, 14, 2,  Integer.toString(deltaHoga));
        cell = sheet.getCellData(14,2);
        if (deltaHoga < 0)
            cell.setStyleIndex(SheetTemplate1.sellStyleNo);
        else
            cell.setStyleIndex(SheetTemplate1.buyStyleNo);
        setData(sheet, 14, 3,  Integer.toString(symbol.hoga.totBuyQty));
        cell = sheet.getCellData(14,3);
        cell.setStyleIndex(SheetTemplate1.buyStyle);
        setData(sheet, 14, 4,  Integer.toString(symbol.hoga.totBuyCnt));
        cell = sheet.getCellData(14,4);
        cell.setStyleIndex(SheetTemplate1.buyStyle);
        sheet.updateData();

        mTableView.invalidate();
    }

    private void setData(ISheetData sheet, int row, int col, String text) {
        if (sheet == null)
            return;
        ICellData cell = sheet.getCellData(row, col);
        if (cell != null) {
            RichText value = (RichText) cell.getRichTextValue();
            value.setText(text);
            cell.setCellValue(value);
            cell.update();
            sheet.updateData();
        }
    }
}
