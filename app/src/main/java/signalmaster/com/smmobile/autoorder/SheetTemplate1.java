package signalmaster.com.smmobile.autoorder;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;

public class SheetTemplate1 {
    public static int sellStyleNo = 0;
    public static int buyStyleNo = 0;
    public static int buyStyle = 0;
    public static int sellStyle = 0;
    public static ISheetData get(final Context context, int rowCount, int colCount, String symbol_code) {

        SmSymbol symbol = SmSymbolManager.getInstance().findSymbol(symbol_code);
        if (symbol == null) {
            symbol = SmMarketManager.getInstance().getDefaultSymbol();
        }

        RecyclerViewList recyclerViewList = new RecyclerViewList();
        ArrayList<String> orderTitle = recyclerViewList.getOrderTitle();
        ArrayList<String> resultList = recyclerViewList.getResultList();
        ArrayList<String> blankList = recyclerViewList.getBlankList();

        ArrayList<String> orderTitleList = recyclerViewList.getOrderTitleList();
        ArrayList<String> orderTitleValue = recyclerViewList.getOrderTitleValueList();

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
        int frStyleIndex = sheet.getCellStyleManager().addCellStyle(firstRowStyle);

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
        //B2
        CellStyle cellStyleB2 = new CellStyle();
        cellStyleB2.setBgColor(0xffDDF3FF);
        cellStyleB2.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleB2.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleB2 = sheet.getCellStyleManager().addCellStyle(cellStyleB2);
        //B3
        CellStyle cellStyleB3 = new CellStyle();
        cellStyleB3.setBgColor(0xffCCE6FA);
        cellStyleB3.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleB3.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleB3 = sheet.getCellStyleManager().addCellStyle(cellStyleB3);
        //B4
        CellStyle cellStyleB4 = new CellStyle();
        cellStyleB4.setBgColor(0xffB9E0FF);
        cellStyleB4.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleB4.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleB4 = sheet.getCellStyleManager().addCellStyle(cellStyleB4);
        //B5
        CellStyle cellStyleB5 = new CellStyle();
        cellStyleB5.setBgColor(0xff9FD6FF);
        cellStyleB5.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleB5.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleB5 = sheet.getCellStyleManager().addCellStyle(cellStyleB5);
        sellStyleNo = styleB5;
        sellStyle = styleB1;
        //R1
        CellStyle cellStyleR1 = new CellStyle();
        cellStyleR1.setBgColor(0xffFDADB0);
        cellStyleR1.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleR1.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleR1 = sheet.getCellStyleManager().addCellStyle(cellStyleR1);
        //R2
        CellStyle cellStyleR2 = new CellStyle();
        cellStyleR2.setBgColor(0xffFFC4C7);
        cellStyleR2.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleR2.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleR2 = sheet.getCellStyleManager().addCellStyle(cellStyleR2);
        //R3
        CellStyle cellStyleR3 = new CellStyle();
        cellStyleR3.setBgColor(0xffFFD6D4);
        cellStyleR3.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleR3.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleR3 = sheet.getCellStyleManager().addCellStyle(cellStyleR3);
        //R4
        CellStyle cellStyleR4 = new CellStyle();
        cellStyleR4.setBgColor(0xffFFE0E1);
        cellStyleR4.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleR4.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleR4 = sheet.getCellStyleManager().addCellStyle(cellStyleR4);
        //R5
        CellStyle cellStyleR5 = new CellStyle();
        cellStyleR5.setBgColor(0xffFFE8E8);
        cellStyleR5.setAlignment(TableConst.ALIGNMENT_CENTER);
        cellStyleR5.setVerticalAlignment(TableConst.VERTICAL_ALIGNMENT_CENTRE);
        int styleR5 = sheet.getCellStyleManager().addCellStyle(cellStyleR5);
        buyStyleNo = styleR1;
        buyStyle = styleR5;


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
        valList[4] = String.format(symbol.getFormat() , vc);

        ArrayList<ArrayList<Object>> buyLineList = new ArrayList<>();
        ArrayList<ArrayList<Object>>  sellLineList = new ArrayList<>();
        ArrayList<String>  resultLineList = new ArrayList<>();

        HogaList(symbol,buyLineList,sellLineList, resultLineList);

        for(int i = 0; i < rowCount; i++) {

            if(i==0){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    cell.setStyleIndex(oddRowStyleTitle);
                    RichText richText = new RichText();
                    richText.setText(orderTitleList.get(j));
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            }
            else if(i==1){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    RichText richText = new RichText();
                    richText.setText(orderTitleValue.get(j));
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==2){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    cell.setStyleIndex(oddRowStyleTitle);
                    RichText richText = new RichText();
                    richText.setText(orderTitle.get(j));
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==3){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    if(2>j && j>=0 ){
                        cell.setStyleIndex(styleB1);
                    }
                    RichText richText = new RichText();
                    richText.setText(buyLineList.get(j).get(4).toString());
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==4){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    if(2>j && j>=0 ){
                        cell.setStyleIndex(styleB2);
                    }
                    RichText richText = new RichText();
                    richText.setText(buyLineList.get(j).get(3).toString());
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==5){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    if(2>j && j>=0 ){
                        cell.setStyleIndex(styleB3);
                    }
                    RichText richText = new RichText();
                    richText.setText(buyLineList.get(j).get(2).toString());
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==6){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    if(2>j && j>=0 ){
                        cell.setStyleIndex(styleB4);
                    }
                    RichText richText = new RichText();
                    richText.setText(buyLineList.get(j).get(1).toString());
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==7){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    if (2 > j && j >= 0) {
                        cell.setStyleIndex(styleB5);
                    }
                    RichText richText = new RichText();
                    richText.setText(buyLineList.get(j).get(0).toString());
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==8){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    RichText richText = new RichText();
                    if (j == 2)
                        richText.setText(valList[3]);
                    else
                        richText.setText("");
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==9){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    if(colCount>j && j>=3 ){
                        cell.setStyleIndex(styleR1);
                    }
                    RichText richText = new RichText();
                    richText.setText(sellLineList.get(j).get(0).toString());
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==10){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    if(colCount>j && j>=3 ){
                        cell.setStyleIndex(styleR2);
                    }
                    RichText richText = new RichText();
                    richText.setText(sellLineList.get(j).get(1).toString());
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==11){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    if(colCount>j && j>=3 ){
                        cell.setStyleIndex(styleR3);
                    }
                    RichText richText = new RichText();
                    richText.setText(sellLineList.get(j).get(2).toString());
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==12){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    if(colCount>j && j>=3 ){
                        cell.setStyleIndex(styleR4);
                    }
                    RichText richText = new RichText();
                    richText.setText(sellLineList.get(j).get(3).toString());
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==13){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    if(colCount>j && j>=3 ){
                        cell.setStyleIndex(styleR5);
                    }
                    RichText richText = new RichText();
                    richText.setText(sellLineList.get(j).get(4).toString());
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            } else if(i==14){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    RichText richText = new RichText();
                    richText.setText(resultLineList.get(j));
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            }  else if(i==15){
                for(int j = 0; j < colCount; j++) {
                    DefaultCellData cell = new DefaultCellData(sheet);
                    RichText richText = new RichText();
                    richText.setText(blankList.get(j));
                    cell.setCellValue(richText);
                    sheet.setCellData(cell, i, j);
                }
            }
        }
        return sheet;
    }

    private static void addMergeRange(DefaultSheetData sheet) {
        Range range = new Range(0, 2, 1, 3);
        sheet.addMergedRange(range);
    }



    public static void HogaList(SmSymbol symbol, ArrayList<ArrayList<Object>> buyLineList, ArrayList<ArrayList<Object>> sellLineList, ArrayList<String>  resultLineList){
        //매수란
        String [] buyText = new String[5];
        buyText[0] = "예체가";
        buyText[1] = "현재가";
        buyText[2] = "저가";
        buyText[3] = "고가";
        buyText[4] = "시가";

        //건수란
        String [] buyGun = new String[5];

        if (symbol == null) {
            buyGun[0] = "";
            buyGun[1] = "";
            buyGun[2] = "";
            buyGun[3] = "";
            buyGun[4] = "";
        }
        else {
            double vh = 0.0, vl = 0.0, vo = 0.0, vc = 0.0;
            double div = Math.pow(10, symbol.decimal);
            vh = (double)(symbol.quote.H / div);
            vl = (double)(symbol.quote.L / div);
            vo = (double)(symbol.quote.O / div);
            vc = (double)(symbol.quote.C / div);

            buyGun[0] = Double.toString(vc);
            buyGun[1] = Double.toString(vc);;
            buyGun[2] = Double.toString(vl);;
            buyGun[3] = Double.toString(vh);;
            buyGun[4] = Double.toString(vo);;
        }


        String [] sellGun = new String[5];
        sellGun[4] = "";
        sellGun[3] = "";
        sellGun[2] = "";
        sellGun[1] = "";
        sellGun[0] = "";

        String [] sellMedo = new String[5];
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

        ArrayList<Object>  sellCnt = new ArrayList<>();
        ArrayList<Object>  sellQty = new ArrayList<>();
        ArrayList<Object>  sellPrice = new ArrayList<>();
        ArrayList<Object>  sellGunList = new ArrayList<>();
        ArrayList<Object>  sellMedoList = new ArrayList<>();

        for(int i=0;i<5;i++){
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

            //add List
            /*resultList.add(result[i]);
            blankList.add(blank[i]);*/
        }

        resultLineList.add(Integer.toString(symbol.hoga.totSellCnt));
        resultLineList.add(Integer.toString(symbol.hoga.totSellQty));
        resultLineList.add(Integer.toString(symbol.hoga.totBuyCnt - symbol.hoga.totSellCnt));
        resultLineList.add(Integer.toString(symbol.hoga.totBuyQty));
        resultLineList.add(Integer.toString(symbol.hoga.totBuyCnt));

        //ArrayList<ArrayList<Object>> buyLineList = new ArrayList<>();

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

        /*resultLineList.add(resultList);
        resultLineList.add(blankList);*/
    }
}
