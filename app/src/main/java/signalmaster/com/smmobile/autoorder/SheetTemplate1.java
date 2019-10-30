package signalmaster.com.smmobile.autoorder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.rayject.table.model.DefaultCellData;
import com.rayject.table.model.DefaultSheetData;
import com.rayject.table.model.ICellData;
import com.rayject.table.model.ISheetData;
import com.rayject.table.model.Range;
import com.rayject.table.model.RichText;
import com.rayject.table.model.TextRun;
import com.rayject.table.model.action.Action;
import com.rayject.table.model.object.CellObject;
import com.rayject.table.model.object.DrawableObject;
import com.rayject.table.model.style.CellStyle;
import com.rayject.table.model.style.Font;
import com.rayject.table.model.style.TableConst;
import com.scichart.core.model.DoubleValues;

import java.util.ArrayList;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.RecyclerViewList;
import signalmaster.com.smmobile.account.SmAccount;
import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.order.SmTotalOrderManager;
import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.position.SmTotalPositionManager;
import signalmaster.com.smmobile.symbol.SmHoga;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;

public class SheetTemplate1 {
    public static int sellStyleNo = 0;
    public static int buyStyleNo = 0;
    public static int buyStyle = 0;
    public static int sellStyle = 0;
    public static int [] buy_style_list = new int[5];
    public static int [] sell_style_list = new int[5];
    private static int frStyleIndex;
    public static ISheetData get(final Context context, int rowCount, int colCount, String symbol_code) {

        SmSymbol symbol = SmSymbolManager.getInstance().findSymbol(symbol_code);
        if (symbol == null) {
            symbol = SmTotalOrderManager.getInstance().getOrderSymbol(context);
        }

        RecyclerViewList recyclerViewList = new RecyclerViewList();
        ArrayList<String> orderTitleList = recyclerViewList.getOrderTitleList();
        ArrayList<String> hogaTitleList = recyclerViewList.getOrderTitle();

        DefaultSheetData sheet = new DefaultSheetData(context);
        sheet.setMaxRowCount(rowCount);
        sheet.setMaxColumnCount(colCount);
        sheet.setFreezedRowCount(1);

        CellStyle firstRowStyle = new CellStyle();
        firstRowStyle.setBgColor(0xffff8c5d);
        firstRowStyle.setAlignment(TableConst.ALIGNMENT_CENTER);
        firstRowStyle.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);

        Font firstRowFont = Font.createDefault(context);
        firstRowFont.setColor(0xffffffff);
        int frIndex = sheet.getFontManager().addFont(firstRowFont);
        firstRowStyle.setFontIndex(frIndex);
        frStyleIndex = sheet.getCellStyleManager().addCellStyle(firstRowStyle);

        /*CellStyle cellStyle = new CellStyle();
        cellStyle.setBgColor(0xffFFA7A7);
        cellStyle.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyle.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int oddRowStyleIndex = sheet.getCellStyleManager().addCellStyle(cellStyle);


        //blue
        CellStyle cellStyleBlue = new CellStyle();
        cellStyleBlue.setBgColor(0xffB2CCFF);
        cellStyleBlue.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleBlue.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int oddRowStyleBlue = sheet.getCellStyleManager().addCellStyle(cellStyleBlue);*/

        //title
        CellStyle cellStyleTitle = new CellStyle();
        cellStyleTitle.setBgColor(0xffD2E0ED);
        cellStyleTitle.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleTitle.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int oddRowStyleTitle = sheet.getCellStyleManager().addCellStyle(cellStyleTitle);


        //그라데이션 #E6F7FF #DDF3FF #CCE6FA #B9E0FF #9FD6FF ,red #FDADB0 #FFC4C7 #FFD6D4 #FFE0E1 #FFE8E8

        //B1
        CellStyle cellStyleB1 = new CellStyle();
        cellStyleB1.setBgColor(0xffE6F7FF);
        cellStyleB1.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleB1.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleB1 = sheet.getCellStyleManager().addCellStyle(cellStyleB1);
        sell_style_list[4] = styleB1;
        //B2
        CellStyle cellStyleB2 = new CellStyle();
        cellStyleB2.setBgColor(0xffDDF3FF);
        cellStyleB2.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleB2.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleB2 = sheet.getCellStyleManager().addCellStyle(cellStyleB2);
        sell_style_list[3] = styleB2;
        //B3
        CellStyle cellStyleB3 = new CellStyle();
        cellStyleB3.setBgColor(0xffCCE6FA);
        cellStyleB3.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleB3.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleB3 = sheet.getCellStyleManager().addCellStyle(cellStyleB3);
        sell_style_list[2] = styleB3;
        //B4
        CellStyle cellStyleB4 = new CellStyle();
        cellStyleB4.setBgColor(0xffB9E0FF);
        cellStyleB4.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleB4.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleB4 = sheet.getCellStyleManager().addCellStyle(cellStyleB4);
        sell_style_list[1] = styleB4;
        //B5
        CellStyle cellStyleB5 = new CellStyle();
        cellStyleB5.setBgColor(0xff9FD6FF);
        cellStyleB5.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleB5.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleB5 = sheet.getCellStyleManager().addCellStyle(cellStyleB5);
        sell_style_list[0] = styleB5;
        sellStyleNo = styleB5;
        sellStyle = styleB1;
        //R1
        CellStyle cellStyleR1 = new CellStyle();
        cellStyleR1.setBgColor(0xffFDADB0);
        cellStyleR1.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleR1.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleR1 = sheet.getCellStyleManager().addCellStyle(cellStyleR1);
        buy_style_list[0] = styleR1;
        //R2
        CellStyle cellStyleR2 = new CellStyle();
        cellStyleR2.setBgColor(0xffFFC4C7);
        cellStyleR2.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleR2.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleR2 = sheet.getCellStyleManager().addCellStyle(cellStyleR2);
        buy_style_list[1] = styleR2;
        //R3
        CellStyle cellStyleR3 = new CellStyle();
        cellStyleR3.setBgColor(0xffFFD6D4);
        cellStyleR3.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleR3.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleR3 = sheet.getCellStyleManager().addCellStyle(cellStyleR3);
        buy_style_list[2] = styleR3;
        //R4
        CellStyle cellStyleR4 = new CellStyle();
        cellStyleR4.setBgColor(0xffFFE0E1);
        cellStyleR4.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleR4.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleR4 = sheet.getCellStyleManager().addCellStyle(cellStyleR4);
        buy_style_list[3] = styleR4;
        //R5
        CellStyle cellStyleR5 = new CellStyle();
        cellStyleR5.setBgColor(0xffFFE8E8);
        cellStyleR5.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleR5.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleR5 = sheet.getCellStyleManager().addCellStyle(cellStyleR5);
        buy_style_list[4] = styleR5;
        buyStyleNo = styleR1;
        buyStyle = styleR5;

        // 포지션 타이틀
        for(int j = 0; j < colCount; j++) {
            DefaultCellData cell = new DefaultCellData(sheet);
            cell.setStyleIndex(oddRowStyleTitle);
            RichText richText = new RichText();
            richText.setText(orderTitleList.get(j));
            cell.setCellValue(richText);
            sheet.setCellData(cell, 0, j);
        }
        // 호가 타이틀
        for(int j = 0; j < colCount; j++) {
            DefaultCellData cell = new DefaultCellData(sheet);
            cell.setStyleIndex(oddRowStyleTitle);
            RichText richText = new RichText();
            richText.setText(hogaTitleList.get(j));
            cell.setCellValue(richText);
            sheet.setCellData(cell, 2, j);
        }
        // 시세 타이틀
        ArrayList<String> siseTitleList = new ArrayList<>();
        siseTitleList.add("시가");
        siseTitleList.add("고가");
        siseTitleList.add("저가");
        siseTitleList.add("현재가");
        siseTitleList.add("예체가");

        for(int j = 3; j < 8; j++) {
            DefaultCellData cell = new DefaultCellData(sheet);
            //cell.setStyleIndex(frStyleIndex);
            RichText richText = new RichText();
            richText.setText(siseTitleList.get(j - 3));
            cell.setCellValue(richText);
            sheet.setCellData(cell, j, 3);
        }
        updatePosition(sheet, symbol);
        updateSise(sheet, symbol);
        updateHoga(sheet, symbol);
        return sheet;
    }

    private static void initPositionArea(DefaultSheetData sheet) {
        for(int i = 0; i < 5; ++i) {
            setData(sheet, 1, i, "");
        }
    }

    public static void updatePosition(DefaultSheetData sheet, SmSymbol symbol) {
        SmAccount account = SmAccountManager.getInstance().getDefaultAccount(symbol.code);
        if (sheet == null || symbol == null || account == null) {
            initPositionArea(sheet);
            return;
        }
        SmPosition position = SmTotalPositionManager.getInstance().findPosition(account.accountNo, symbol.code);
        if (position == null) {
            initPositionArea(sheet);
        }
        String type = null;
        if (position.positionType.name().equals("None")) {
            type = "";
        } else if (position.positionType.name().equals("Buy")) {
            type = "매수";
        } else if (position.positionType.name().equals("Sell")) {
            type = "매도";
        }

        try {
            // 타입
            setData(sheet, 1, 0, type);
            // 잔고
            setData(sheet, 1, 1, Integer.toString(position.openQty));
            // 평균가
            setData(sheet, 1, 2, String.format("%,.2f", position.avgPrice));
            //평가손익
            setData(sheet, 1, 4, String.format("%,.0f", position.openPL));

            if (symbol != null) {
                double div = Math.pow(10, symbol.decimal);
                double vc = (symbol.quote.C / div);
                // 포지션 현재가
                setData(sheet, 1, 3, String.format(symbol.getFormat(), vc));
            }
        } catch (Exception e) {
            String error = e.getMessage();
            Log.d("TAG", "updatePosition" + "  code" + symbol.code);
        }
    }

    public static void updateSise(DefaultSheetData sheet, SmSymbol symbol) {
        if (symbol == null) {
            return;
        }

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

        // 시간
        //setData(sheet, 2, 2, symbol.quote.time);

        DefaultCellData cell_data =  setData(sheet, 8, 2, String.format(symbol.getFormat() , vc));
        //cell_data.setStyleIndex(frStyleIndex);
        ICellData cell = sheet.getCellData(8, 2);
        if (vc < vo)
            cell.setStyleIndex(SheetTemplate1.sellStyleNo);
        else
            cell.setStyleIndex(SheetTemplate1.buyStyleNo);
    }

    public static void updateHoga(DefaultSheetData sheet, SmSymbol symbol) {
        if (symbol == null || sheet == null)
            return;
        SmHoga symbol_hoga = symbol.hoga;
        for(int i = 0; i < 5; ++i) {
            // 매수가격
            double div = Math.pow(10, symbol.decimal);
            double price = (symbol.hoga.hogaItem[i].buyPrice / div);
            String price_text = String.format(symbol.getFormat() , price);
            DefaultCellData cell = setData(sheet, 9 + i, 2, price_text);
            cell.setStyleIndex(buy_style_list[i]);
            // 매수 수량
            String qty = Integer.toString(symbol.hoga.hogaItem[i].buyQty);
            cell = setData(sheet, 9 + i, 3, qty);
            cell.setStyleIndex(buy_style_list[i]);
            // 매수 건수
            String count = Integer.toString(symbol.hoga.hogaItem[i].buyCnt);
            cell = setData(sheet, 9 + i, 4, count);
            cell.setStyleIndex(buy_style_list[i]);
            // 매도 가격
            price = (symbol.hoga.hogaItem[i].sellPrice / div);
            price_text = String.format(symbol.getFormat() , price);
            cell = setData(sheet, 7 - i, 2, price_text);
            cell.setStyleIndex(sell_style_list[i]);
            // 매도 수량
            qty = Integer.toString(symbol.hoga.hogaItem[i].sellQty);
            cell = setData(sheet, 7 - i, 1, qty);
            cell.setStyleIndex(sell_style_list[i]);
            // 매도 건수
            count = Integer.toString(symbol.hoga.hogaItem[i].sellCnt);
            cell = setData(sheet, 7 - i, 0, count);
            cell.setStyleIndex(sell_style_list[i]);
        }

        // 호가 시간
        String hoga_time = symbol.hoga.time.substring(0, 2);
        hoga_time += ":";
        hoga_time += symbol.hoga.time.substring(2, 4);
        hoga_time += ":";
        hoga_time += symbol.hoga.time.substring(4, 6);
        setData(sheet, 2, 2, hoga_time);
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
    }

    private static DefaultCellData setData(DefaultSheetData sheet, int row, int col, String text) {
        DefaultCellData cell = new DefaultCellData(sheet);
        RichText richText = new RichText();
        richText.setText(text);
        cell.setCellValue(richText);
        sheet.setCellData(cell, row, col);
        sheet.updateData();
        return cell;
    }

    private static void addMergeRange(DefaultSheetData sheet) {
        Range range = new Range(0, 2, 1, 3);
        sheet.addMergedRange(range);
    }
}
