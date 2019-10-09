package signalmaster.com.smmobile.autoorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.rayject.table.model.DefaultCellData;
import com.rayject.table.model.ICellData;
import com.rayject.table.model.ISheetData;
import com.rayject.table.model.RichText;
import com.rayject.table.util.TableViewConfigure;
import com.rayject.table.view.TableView;
import com.scichart.core.common.Action1;

import java.util.ArrayList;

import signalmaster.com.smmobile.LoginActivity;
import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.account.SmAccount;
import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.data.SmChartDataService;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.mock.SmPrChartFragment;
import signalmaster.com.smmobile.network.SmProtocolManager;
import signalmaster.com.smmobile.network.SmServiceManager;
import signalmaster.com.smmobile.order.SmFilledCondition;
import signalmaster.com.smmobile.order.SmOrder;
import signalmaster.com.smmobile.order.SmOrderReqNoGenerator;
import signalmaster.com.smmobile.order.SmOrderRequest;
import signalmaster.com.smmobile.order.SmOrderType;
import signalmaster.com.smmobile.order.SmPriceType;
import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.position.SmPositionType;
import signalmaster.com.smmobile.position.SmTotalPositionManager;
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

        SmMarketManager marketManager = SmMarketManager.getInstance();
        final SmSymbol symbol = marketManager.getDefaultSymbol();
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
                request.orderAmount = 1;
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
                request.orderAmount = 1;
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
        /*orderTitleValue.set(1, "");
        orderTitleValue.set(2, "");
        orderTitleValue.set(3, "");
        orderTitleValue.set(4, "");
        orderTitleValue.set(5, "");
        orderRecyclerViewAdapter.notifyDataSetChanged();*/
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
        ISheetData sheet = mTableView.getSheet();
        for(int i=0;i<colCount;i++) {
            ICellData cell = sheet.getCellData(1,i);
            sheet.getCellStyleManager().getCellStyle(0);
            RichText value = (RichText)cell.getRichTextValue();
            value.setText("");
            cell.setCellValue(value);
            sheet.updateData();
            mTableView.invalidate();
        }
    }

    public void updateSise(SmSymbol symbol) {
        if (symbol == null) {
            clearSiseInfo();
            return;
        }

        double vh = 0.0, vl = 0.0, vo = 0.0, vc = 0.0;
        double div = Math.pow(10, symbol.decimal);
        vh = (symbol.quote.H / div);
        vl = (symbol.quote.L / div);
        vo = (symbol.quote.O / div);
        vc = (symbol.quote.C / div);
        String[] valList = new String[5];
        valList[0] = String.format(symbol.getFormat() , vo);
        valList[1] = String.format(symbol.getFormat() , vh);
        valList[2] = String.format(symbol.getFormat() , vl);
        valList[3] = String.format(symbol.getFormat() , vc);
        valList[4] = "";

        ISheetData sheet = mTableView.getSheet();
        for(int i=3, j = 0;i<8;i++, j++) {
            ICellData cell = sheet.getCellData(i, 4);
            sheet.getCellStyleManager().getCellStyle(0);
            RichText value = (RichText)cell.getRichTextValue();
            value.setText(valList[j]);
            cell.setCellValue(value);
            sheet.updateData();

        }

        ICellData cell = sheet.getCellData(8, 2);
        sheet.getCellStyleManager().getCellStyle(0);
        RichText value = (RichText)cell.getRichTextValue();
        value.setText(valList[3]);
        if (vc < vo)
            cell.setStyleIndex(SheetTemplate1.sellStyleNo);
        else
            cell.setStyleIndex(SheetTemplate1.buyStyleNo);
        cell.setCellValue(value);
        sheet.updateData();

        mTableView.invalidate();
    }

    private void clearSiseInfo() {
        ISheetData sheet = mTableView.getSheet();
        for(int i=3;i<7;i++) {
            ICellData cell = sheet.getCellData(i, 4);
            sheet.getCellStyleManager().getCellStyle(0);
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

        ISheetData sheet = mTableView.getSheet();

        String type = null;


        if (position.positionType.name().equals("None")) {
            type = "";
        } else if (position.positionType.name().equals("Buy")) {
            type = "매수";
        } else if (position.positionType.name().equals("Sell")) {
            type = "매도";
        }

        orderTitleList.clear();
        orderTitleList.add(type);
        orderTitleList.add(Integer.toString(position.openQty));
        orderTitleList.add(String.format("%,.2f", position.avgPrice));
        SmSymbol symbol = SmSymbolManager.getInstance().findSymbol(position.symbolCode);
        if (symbol != null) {
            orderTitleList.add(String.format(symbol.getFormat(), position.curPrice));
        } else {
            orderTitleList.add(String.format("%,.2f", position.curPrice));
        }

        orderTitleList.add(String.format("%,.2f", position.openPL));

        for(int i=0;i<colCount;i++) {
            ICellData cell = sheet.getCellData(1,i);
            sheet.getCellStyleManager().getCellStyle(0);
            RichText value = (RichText)cell.getRichTextValue();
            value.setText(orderTitleList.get(i));
            cell.setCellValue(value);
            sheet.updateData();
            mTableView.invalidate();
        }
    }

    public void updateHoga(SmSymbol symbol) {
        if (symbol == null)
            return;

        ArrayList<ArrayList<Object>> buyLineList = new ArrayList<>();
        ArrayList<ArrayList<Object>> sellLineList = new ArrayList<>();
        ArrayList<String> resultLineList = new ArrayList<>();
        HogaList(symbol,buyLineList,sellLineList, resultLineList);
        ISheetData sheet = mTableView.getSheet();

        for (int i = 0; i < rowCount; i++) {
            if (i == 3) {
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(buyLineList.get(j).get(4).toString());
                    cell.setCellValue(value);
                    sheet.updateData();
                    mTableView.invalidate();
                }
            } else if (i == 4){
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    sheet.getCellStyleManager().getCellStyle(0);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(buyLineList.get(j).get(3).toString());
                    cell.setCellValue(value);
                    sheet.updateData();
                    mTableView.invalidate();
                }
            } else if (i == 5){
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    sheet.getCellStyleManager().getCellStyle(0);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(buyLineList.get(j).get(2).toString());
                    cell.setCellValue(value);
                    sheet.updateData();
                    mTableView.invalidate();
                }
            } else if (i == 6){
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    sheet.getCellStyleManager().getCellStyle(0);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(buyLineList.get(j).get(1).toString());
                    cell.setCellValue(value);
                    sheet.updateData();
                    mTableView.invalidate();
                }
            } else if (i == 7){
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    sheet.getCellStyleManager().getCellStyle(0);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(buyLineList.get(j).get(0).toString());
                    cell.setCellValue(value);
                    sheet.updateData();
                    mTableView.invalidate();
                }
            } else if (i == 9){
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    sheet.getCellStyleManager().getCellStyle(0);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(sellLineList.get(j).get(0).toString());
                    cell.setCellValue(value);
                    sheet.updateData();
                    mTableView.invalidate();
                }
            } else if (i == 10){
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    sheet.getCellStyleManager().getCellStyle(0);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(sellLineList.get(j).get(1).toString());
                    cell.setCellValue(value);
                    sheet.updateData();
                    mTableView.invalidate();
                }
            } else if (i == 11){
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    sheet.getCellStyleManager().getCellStyle(0);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(sellLineList.get(j).get(2).toString());
                    cell.setCellValue(value);
                    sheet.updateData();
                    mTableView.invalidate();
                }
            } else if (i == 12){
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    sheet.getCellStyleManager().getCellStyle(0);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(sellLineList.get(j).get(3).toString());
                    cell.setCellValue(value);
                    sheet.updateData();
                    mTableView.invalidate();
                }
            } else if (i == 13){
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    sheet.getCellStyleManager().getCellStyle(0);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(sellLineList.get(j).get(4).toString());
                    cell.setCellValue(value);
                    sheet.updateData();
                    mTableView.invalidate();
                }
            }
            else if(i==14) {
                for (int j = 0; j < colCount; j++) {
                    ICellData cell = sheet.getCellData(i,j);
                    sheet.getCellStyleManager().getCellStyle(0);
                    RichText value = (RichText)cell.getRichTextValue();
                    value.setText(resultLineList.get(j));
                    cell.setCellValue(value);
                    if (j == 0 || j == 1) {
                        cell.setStyleIndex(SheetTemplate1.sellStyle);
                    } else if (j == 3 || j == 4) {
                        cell.setStyleIndex(SheetTemplate1.buyStyle);
                    } else {
                        if (resultLineList.get(j).contains("-"))
                            cell.setStyleIndex(SheetTemplate1.sellStyleNo);
                        else
                            cell.setStyleIndex(SheetTemplate1.buyStyleNo);
                    }
                    sheet.updateData();
                    mTableView.invalidate();
                }
            }
        }
    }

    public void HogaList (SmSymbol symbol, ArrayList < ArrayList < Object >> buyLineList, ArrayList < ArrayList < Object >> sellLineList, ArrayList<String>  resultLineList ){
        //매수란
        String[] buyText = new String[5];
        buyText[0] = "예체가";
        buyText[1] = "현재가";
        buyText[2] = "저가";
        buyText[3] = "고가";
        buyText[4] = "시가";

        SmSymbol symbol1 = SmSymbolManager.getInstance().findSymbol(symbol.code);

        //건수란
        String[] buyGun = new String[5];
        if (symbol1 == null) {
            buyGun[0] = "";
            buyGun[1] = "";
            buyGun[2] = "";
            buyGun[3] = "";
            buyGun[4] = "";
        }
        else {
            double vh = 0.0, vl = 0.0, vo = 0.0, vc = 0.0;
            double div = Math.pow(10, symbol.decimal);
            vh = (double)(symbol1.quote.H / div);
            vl = (double)(symbol1.quote.L / div);
            vo = (double)(symbol1.quote.O / div);
            vc = (double)(symbol1.quote.C / div);

            buyGun[0] = "";
            buyGun[1] = String.format(symbol.getFormat() , vc);
            buyGun[2] = String.format(symbol.getFormat() , vl);
            buyGun[3] = String.format(symbol.getFormat() , vh);
            buyGun[4] = String.format(symbol.getFormat() , vo);
        }

        String[] sellGun = new String[5];
        sellGun[4] = "";
        sellGun[3] = "";
        sellGun[2] = "";
        sellGun[1] = "";
        sellGun[0] = "";

        String[] sellMedo = new String[5];
        sellMedo[0] = "";
        sellMedo[1] = "";
        sellMedo[2] = "";
        sellMedo[3] = "";
        sellMedo[4] = "";

        ArrayList<Object> buyCnt = new ArrayList<>();
        ArrayList<Object> buyQty = new ArrayList<>();
        ArrayList<Object> buyPrice = new ArrayList<>();
        ArrayList<Object> buyTextList = new ArrayList<>();
        ArrayList<Object> buyGunsuList = new ArrayList<>();

        ArrayList<Object> sellCnt = new ArrayList<>();
        ArrayList<Object> sellQty = new ArrayList<>();
        ArrayList<Object> sellPrice = new ArrayList<>();
        ArrayList<Object> sellGunList = new ArrayList<>();
        ArrayList<Object> sellMedoList = new ArrayList<>();


        for (int i = 0; i < 5; i++) {
            buyCnt.add(symbol.hoga.hogaItem[i].buyCnt);
            buyQty.add(symbol.hoga.hogaItem[i].buyQty);
            double div = Math.pow(10, symbol.decimal);
            double bp = (symbol.hoga.hogaItem[i].buyPrice / div);

            buyPrice.add(String.format(symbol.getFormat() , bp));
            buyTextList.add(buyText[i]);
            buyGunsuList.add(buyGun[i]);

            sellCnt.add(symbol.hoga.hogaItem[i].sellCnt);
            sellQty.add(symbol.hoga.hogaItem[i].sellQty);
            double sp = (symbol.hoga.hogaItem[i].buyPrice / div);
            sellPrice.add(String.format(symbol.getFormat() , sp));
            sellGunList.add(sellGun[i]);
            sellMedoList.add(sellMedo[i]);
        }

        //ArrayList<ArrayList<Object>> buyLineList = new ArrayList<>();

        resultLineList.add(Integer.toString(symbol.hoga.totSellCnt));
        resultLineList.add(Integer.toString(symbol.hoga.totSellQty));
        resultLineList.add(Integer.toString(symbol.hoga.totBuyQty - symbol.hoga.totSellQty));
        resultLineList.add(Integer.toString(symbol.hoga.totBuyQty));
        resultLineList.add(Integer.toString(symbol.hoga.totBuyCnt));

        buyLineList.add(buyCnt);
        buyLineList.add(buyQty);
        buyLineList.add(buyPrice);
        buyLineList.add(buyTextList);
        buyLineList.add(buyGunsuList);

        //ArrayList<ArrayList<Object>>  sellLineList = new ArrayList<>();

        sellLineList.add(sellGunList);
        sellLineList.add(sellMedoList);
        sellLineList.add(sellPrice);
        sellLineList.add(sellQty);
        sellLineList.add(sellCnt);
    }


}
