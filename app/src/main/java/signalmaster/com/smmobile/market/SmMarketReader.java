package signalmaster.com.smmobile.market;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.network.SmFileDownloader;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;

import static android.content.ContentValues.TAG;

/*
윈도우는 줄바꿈 문자를 \r\n 두개를 쓴다.
하지만 리눅스는 하나를 쓴다.
따라서, 윈도우보다 리눅스를 쓰는 안드로이드는 문자길이를 하나더 추가해 줘야 한다.
Yes, the line separator under Windows is \r\n (CR+LF), on Mac \r and on Linux (Android) \n.
Java has a clever reader which returns the line without separator, and a println which uses the platform setting System.getProperty("line.separator").
 */
public class SmMarketReader {

    private static volatile SmMarketReader mMoleInstance;

    public static SmMarketReader getInstance() {
        if (mMoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmMarketReader.class) {
                if (mMoleInstance == null) mMoleInstance = new SmMarketReader();
            }
        }
        return mMoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmMarketReader readResolve() {
        return getInstance();
    }

    private SmMarketReader() {
        //Prevent form the reflection api.
        if (mMoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }


    final int marketCodeLen = 142;
    final int categoryCodeLen = 151;
    final int symbolCodeLen = 491;

    public boolean isDownloadComplete() {
        return downloadComplete;
    }

    public void setDownloadComplete(boolean downloadComplete) {
        this.downloadComplete = downloadComplete;
    }

    class SmCategoryData {

        // 상품군 - 금리, 통화 등등
        byte[] marketType = new byte[20];
        // 거래소 코드
        byte[] exchangeCode = new byte[5];
        // 시장 구분 코드 - 고유값
        byte[] marketCode = new byte[3];
        // 시장 구분 코드 - 고유값
        byte[] marketCodePM = new byte[5];
        // 상품명 - 영문
        byte[] marketName = new byte[50];
        // 상품명 - 한글
        byte[] marketNameKr = new byte[50];

        //거래소 인덱스코드
        byte[] indexCode= new byte[4];
        //품목 구분 코드
        byte[] pmGubun= new byte[3];

        // 기타
        byte[] etc = new byte[43];

    }

    class SmSymbolData{
        //종목코드
        byte [] series = new byte[32];
        //거래소
        byte [] exchangeCode = new byte[5];
        //품목 인덱스 코드
        byte [] indexCode= new byte[4];
        //품목 코드
        byte [] marketCode= new byte[5];
        // 거래소 번호
        byte [] exchangeNumber= new byte[5];
        //소수점 번호
        byte [] pdesz= new byte[5];
        //소수점정보2
        byte [] rdesz= new byte[5];
        //계약 크기
        byte [] controlSize= new byte[20];
        //틱 사이즈
        byte [] tickSize= new byte[20];
        //틱 값
        byte [] tickValue= new byte[20];
        //거래승수
        byte []  multiPler= new byte[20];
        //진법
        byte [] dispDigit= new byte[10];
        //Full 종목명
        byte [] seriesNm= new byte[32];
        //Full 종목명 한글
        byte [] seriesNmKr= new byte[32];
        //최근 월물, 주요종목표시
        byte [] nearSeq= new byte[1];
        //거래 가능여부
        byte [] statTp= new byte[1];
        //신규거래 제한일
        byte [] lockDate= new byte[8];
        //최초거래일
        byte [] tradeFrDate= new byte[8];
        //최종거래일
        byte [] tradeToDate= new byte[8];
        //만기일, 최종결제일
        byte [] exprDate= new byte[8];
        //잔존일수
        byte [] remnCnt= new byte[4];
        //호가방식
        byte [] hogaMthd= new byte[30];
        //상하한폭비율
        byte [] minmaxRt= new byte[6];
        //기준가
        byte [] baseP= new byte[20];
        //상한가
        byte [] maxP= new byte[20];
        //하한가
        byte [] minP= new byte[20];
        //신규주문 증거금
        byte [] trstMgn= new byte[20];
        //유지증거금
        byte [] mntMgn= new byte[20];
        //결제통화코드
        byte [] crcCd= new byte[3];
        //Base CRC CD
        byte [] baseCrcCd= new byte[3];
        //COUNTER CRC CD
        byte [] counterCrcCd= new byte[3];
        //PIP COST
        byte [] pipCost= new byte[20];
        //매수이자
        byte [] buyInt= new byte[20];
        //매도이자
        byte [] sellInt= new byte[20];
        //LOUND LOTS
        byte [] roundLots= new byte[6];
        //진법자리수
        byte [] scaleChiper= new byte[10];
        //소수점 정보(KTB 기준)
        byte [] decimalChiper= new byte[5];
        //전일 거래량량
        byte [] jnilVolume= new byte[10];
    }

    private String mrktFile = "MRKT.cod";
    private String pmFile = "PMCODE.cod";
    private String jmFile = "JMCODE.cod";

    // 시장별 품목들을 읽어 온다.
    public void readMarketFile(Context context) throws Exception {

        SmMarketManager marketMgr = SmMarketManager.getInstance();
        File path = context.getFilesDir();
        File file = new File(path, mrktFile);
        Charset euckrCharset = Charset.forName("MS949");
        //InputStream inputStream = context.getAssets().openData("res/raw/mrkt.cod");
        //InputStream inputStream = context.getResources().openRawResource(R.raw.mrkt);
        //InputStream inputStream = context.getAssets().open(file);
        InputStream inputStream = new FileInputStream(file);
        byte[] input = new byte[marketCodeLen];
        int len = -1;
        while((len = inputStream.read(input)) != -1){
            Log.e("input: ", input.toString());
            SmCategoryData categoryData = new SmCategoryData();
            int pos = 0;
            categoryData.marketType = Arrays.copyOfRange(input, pos, pos + categoryData.marketType.length);
            System.out.print(categoryData.exchangeCode);
            pos += categoryData.marketType.length;
            categoryData.exchangeCode = Arrays.copyOfRange(input, pos, pos + categoryData.exchangeCode.length);
            System.out.print(categoryData.exchangeCode);
            pos += categoryData.exchangeCode.length;
            categoryData.marketCode = Arrays.copyOfRange(input, pos, pos + categoryData.marketCode.length);
            System.out.print(categoryData.marketCode);
            pos += categoryData.marketCode.length;
            categoryData.marketName = Arrays.copyOfRange(input, pos, pos + categoryData.marketName.length);
            System.out.print(categoryData.marketName);
            pos += categoryData.marketName.length;
            categoryData.marketNameKr = Arrays.copyOfRange(input, pos, pos + categoryData.marketNameKr.length);
            System.out.print(categoryData.marketNameKr);

            SmCategory category = new SmCategory();


            CharBuffer charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.marketType));
            category.marketType = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.exchangeCode));
            category.exchangeCode = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.marketCode));
            category.marketCode = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.marketName));
            category.marketName = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.marketNameKr));
            category.marketNameKr = charBuffer.toString().trim();

            SmMarket market = marketMgr.addMarket(category.marketType);
            market.addCategory(category);
        }
    }

    // 품목의 세부정보를 읽어 온다.
    public void readPmFile(Context context) throws Exception {

        SmMarketManager marketMgr = SmMarketManager.getInstance();
        File path = context.getFilesDir();
        File file = new File(path, pmFile);
        InputStream inputStream = new FileInputStream(file);
        //InputStream inputStream = context.getResources().openRawResource(R.raw.pmcode);
        byte[] input = new byte[categoryCodeLen];
        int len = -1;
        while((len = inputStream.read(input)) != -1){
            SmCategoryData categoryData = new SmCategoryData();
            int pos = 0;
            categoryData.marketType = Arrays.copyOfRange(input, pos, pos + categoryData.marketType.length);
            pos += categoryData.marketType.length;

            categoryData.indexCode = Arrays.copyOfRange(input, pos, pos + categoryData.indexCode.length);
            pos += categoryData.indexCode.length;

            categoryData.exchangeCode = Arrays.copyOfRange(input, pos, pos + categoryData.exchangeCode.length);
            pos += categoryData.exchangeCode.length;

            categoryData.marketCodePM = Arrays.copyOfRange(input, pos, pos + categoryData.marketCodePM.length);
            pos += categoryData.marketCodePM.length;

            categoryData.pmGubun = Arrays.copyOfRange(input, pos, pos + categoryData.pmGubun.length);
            pos += categoryData.pmGubun.length;

            categoryData.marketName = Arrays.copyOfRange(input, pos, pos + categoryData.marketName.length);
            pos += categoryData.marketName.length;

            categoryData.marketNameKr = Arrays.copyOfRange(input, pos, pos + categoryData.marketNameKr.length);


            SmMarketManager mrktMgr = SmMarketManager.getInstance();

            //add
            SmCategory category = new SmCategory();

            Charset euckrCharset = Charset.forName("MS949");
            CharBuffer charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.marketType));
            String marketType = charBuffer.toString().trim();
            charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.indexCode));
            String indexCode = charBuffer.toString().trim();
            charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.exchangeCode));
            String exchangeCode = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.marketCodePM));
            String marketCodePM = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.marketName));
            String marketName = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.marketNameKr));
            category.marketNameKr = charBuffer.toString().trim();

            SmCategory curCat = mrktMgr.findCategory(marketType, marketCodePM);
            if (curCat == null)
                continue;

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(categoryData.pmGubun));
            String pmGubun = charBuffer.toString().trim();
            curCat.pmGubun = pmGubun;
            SmMarketInfo mrktInfo = new SmMarketInfo();
            mrktInfo.marketName = marketType;
            mrktInfo.marketCode = pmGubun;
            mrktMgr.AddToCategoryMarketMap(marketCodePM, mrktInfo);
        }
    }

    // 품목에 대한 종목 리스트를 읽어 온다.
    public void readJmFile(Context context) throws Exception {

        SmMarketManager marketMgr = SmMarketManager.getInstance();
        File path = context.getFilesDir();
        File file = new File(path, jmFile);
        InputStream inputStream = new FileInputStream(file);
        //InputStream inputStream = context.getResources().openRawResource(R.raw.jmcode);
        byte[] input = new byte[symbolCodeLen];
        int len = -1;
        while((len = inputStream.read(input)) != -1){
            SmSymbolData symbolData = new SmSymbolData();
            int pos = 0;
            symbolData.series = Arrays.copyOfRange(input, pos, pos + symbolData.series.length);
            pos += symbolData.series.length;

            symbolData.exchangeCode = Arrays.copyOfRange(input, pos, pos + symbolData.exchangeCode.length);
            pos += symbolData.exchangeCode.length;

            symbolData.indexCode = Arrays.copyOfRange(input, pos, pos + symbolData.indexCode.length);
            pos += symbolData.indexCode.length;

            symbolData.marketCode = Arrays.copyOfRange(input, pos, pos + symbolData.marketCode.length);
            pos += symbolData.marketCode.length;

            symbolData.exchangeNumber = Arrays.copyOfRange(input, pos, pos + symbolData.exchangeNumber.length);
            pos += symbolData.exchangeNumber.length;

            symbolData.pdesz = Arrays.copyOfRange(input, pos, pos + symbolData.pdesz.length);
            pos += symbolData.pdesz.length;

            symbolData.rdesz = Arrays.copyOfRange(input, pos, pos + symbolData.rdesz.length);
            pos += symbolData.rdesz.length;

            symbolData.controlSize = Arrays.copyOfRange(input, pos, pos + symbolData.controlSize.length);
            pos += symbolData.controlSize.length;

            symbolData.tickSize = Arrays.copyOfRange(input, pos, pos + symbolData.tickSize.length);
            pos += symbolData.tickSize.length;

            symbolData.tickValue = Arrays.copyOfRange(input, pos, pos + symbolData.tickValue.length);
            pos += symbolData.tickValue.length;

            symbolData.multiPler = Arrays.copyOfRange(input, pos, pos + symbolData.multiPler.length);
            pos += symbolData.multiPler.length;

            symbolData.dispDigit = Arrays.copyOfRange(input, pos, pos + symbolData.dispDigit.length);
            pos += symbolData.dispDigit.length;

            symbolData.seriesNm = Arrays.copyOfRange(input, pos, pos + symbolData.seriesNm.length);
            pos += symbolData.seriesNm.length;

            symbolData.seriesNmKr = Arrays.copyOfRange(input, pos, pos + symbolData.seriesNmKr.length);
            pos += symbolData.seriesNmKr.length;

            symbolData.nearSeq = Arrays.copyOfRange(input, pos, pos + symbolData.nearSeq.length);
            pos += symbolData.nearSeq.length;

            symbolData.statTp = Arrays.copyOfRange(input, pos, pos + symbolData.statTp.length);
            pos += symbolData.statTp.length;

            symbolData.lockDate = Arrays.copyOfRange(input, pos, pos + symbolData.lockDate.length);
            pos += symbolData.lockDate.length;

            symbolData.tradeFrDate = Arrays.copyOfRange(input, pos, pos + symbolData.tradeFrDate.length);
            pos += symbolData.tradeFrDate.length;

            symbolData.tradeToDate = Arrays.copyOfRange(input, pos, pos + symbolData.tradeToDate.length);
            pos += symbolData.tradeToDate.length;

            symbolData.exprDate = Arrays.copyOfRange(input, pos, pos + symbolData.exprDate.length);
            pos += symbolData.exprDate.length;

            symbolData.remnCnt = Arrays.copyOfRange(input, pos, pos + symbolData.remnCnt.length);
            pos += symbolData.remnCnt.length;

            symbolData.hogaMthd = Arrays.copyOfRange(input, pos, pos + symbolData.hogaMthd.length);
            pos += symbolData.hogaMthd.length;

            symbolData.minmaxRt = Arrays.copyOfRange(input, pos, pos + symbolData.minmaxRt.length);
            pos += symbolData.minmaxRt.length;

            symbolData.baseP = Arrays.copyOfRange(input, pos, pos + symbolData.baseP.length);
            pos += symbolData.baseP.length;

            symbolData.maxP = Arrays.copyOfRange(input, pos, pos + symbolData.maxP.length);
            pos += symbolData.maxP.length;

            symbolData.minP = Arrays.copyOfRange(input, pos, pos + symbolData.minP.length);
            pos += symbolData.minP.length;

            symbolData.trstMgn = Arrays.copyOfRange(input, pos, pos + symbolData.trstMgn.length);
            pos += symbolData.trstMgn.length;

            symbolData.mntMgn = Arrays.copyOfRange(input, pos, pos + symbolData.mntMgn.length);
            pos += symbolData.mntMgn.length;

            symbolData.crcCd = Arrays.copyOfRange(input, pos, pos + symbolData.crcCd.length);
            pos += symbolData.crcCd.length;

            symbolData.baseCrcCd = Arrays.copyOfRange(input, pos, pos + symbolData.baseCrcCd.length);
            pos += symbolData.baseCrcCd.length;

            symbolData.counterCrcCd = Arrays.copyOfRange(input, pos, pos + symbolData.counterCrcCd.length);
            pos += symbolData.counterCrcCd.length;

            symbolData.pipCost = Arrays.copyOfRange(input, pos, pos + symbolData.pipCost.length);
            pos += symbolData.pipCost.length;

            symbolData.buyInt = Arrays.copyOfRange(input, pos, pos + symbolData.buyInt.length);
            pos += symbolData.buyInt.length;

            symbolData.sellInt = Arrays.copyOfRange(input, pos, pos + symbolData.sellInt.length);
            pos += symbolData.sellInt.length;

            symbolData.roundLots = Arrays.copyOfRange(input, pos, pos + symbolData.roundLots.length);
            pos += symbolData.roundLots.length;

            symbolData.scaleChiper = Arrays.copyOfRange(input, pos, pos + symbolData.scaleChiper.length);
            pos += symbolData.scaleChiper.length;

            symbolData.decimalChiper = Arrays.copyOfRange(input, pos, pos + symbolData.decimalChiper.length);
            pos += symbolData.decimalChiper.length;

            symbolData.jnilVolume = Arrays.copyOfRange(input, pos, pos + symbolData.jnilVolume.length);


            SmSymbol symbol = new SmSymbol();
            Charset euckrCharset = Charset.forName("MS949");
            CharBuffer charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.series));
            symbol.code = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.exchangeCode));
            symbol.exchangeCode = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.indexCode));
            symbol.indexCode = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.marketCode));
            symbol.marketCode = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.exchangeNumber));
            symbol.exchangeNumber = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.pdesz));
            symbol.pdesz = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.rdesz));
            symbol.rdesz = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.controlSize));
           // symbol.controlSize = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.tickSize));
           // symbol.tickSize = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.tickValue));
            //symbol.tickValue = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.multiPler));
           // symbol.multiPler = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.dispDigit));
            symbol.dispDigit = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.seriesNm));
            symbol.seriesNm = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.seriesNmKr));
            symbol.seriesNmKr = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.nearSeq));
            symbol.nearSeq = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.statTp));
            symbol.statTp = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.lockDate));
            symbol.lockDate = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.tradeFrDate));
            symbol.tradeFrDate = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.tradeToDate));
            symbol.tradeToDate = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.exprDate));
            symbol.exprDate = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.remnCnt));
            symbol.remnCnt = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.hogaMthd));
            symbol.hogaMthd = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.minmaxRt));
            symbol.minmaxRt = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.baseP));
            symbol.baseP = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.maxP));
            symbol.maxP = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.minP));
            symbol.minP = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.trstMgn));
            symbol.trstMgn = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.mntMgn));
            symbol.mntMgn = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.crcCd));
            symbol.crcCd = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.baseCrcCd));
            symbol.baseCrcCd = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.counterCrcCd));
            symbol.counterCrcCd = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.pipCost));
            symbol.pipCost = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.buyInt));
            symbol.buyInt = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.mntMgn));
            symbol.mntMgn = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.sellInt));
            symbol.sellInt = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.roundLots));
            symbol.roundLots = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.scaleChiper));
            symbol.scaleChiper = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.decimalChiper));
            symbol.decimalChiper = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.jnilVolume));
            symbol.jnilVolume = charBuffer.toString().trim();

            charBuffer = euckrCharset.decode(ByteBuffer.wrap(symbolData.exchangeCode));
            symbol.exchangeCode = charBuffer.toString().trim();

            SmMarketManager smMarketManager = SmMarketManager.getInstance();
            SmMarketInfo mrktInfo = smMarketManager.findMarketInfo(symbol.marketCode);
            if (mrktInfo == null)
                continue;
            SmCategory curCat = smMarketManager.findCategory(mrktInfo.marketName, symbol.marketCode);
            if (curCat == null)
                continue;
            symbol.set_category(curCat);
            symbol.marketType = curCat.marketType;
            curCat.addSymbol(symbol);

            SmSymbolManager symMgr = SmSymbolManager.getInstance();
            symMgr.addSymbol(symbol);
        }
    }

    private Context context = null;

    private boolean downloadComplete = false;

    public void downloadMKFile(Context ctx) {
        context = ctx;
        new SmFileDownloader().execute(mrktFile);
    }

    public void downloadPMFile(Context ctx) {
        context = ctx;
        new SmFileDownloader().execute(pmFile);
    }

    public void downloadJMFile(Context ctx) {
        context = ctx;
        new SmFileDownloader().execute(jmFile);
    }

    private class SmFileDownloader extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                File path = context.getFilesDir();
                File file = new File(path, f_url[0]);
                // http://angelpie.ddns.net:9991/mst/
                String addr = "http://angelpie.ddns.net:9991/mst/";
                addr += f_url[0];
                URL url = new URL(addr);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[symbolCodeLen];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return f_url[0];
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //pDialog.setProgress(Integer.parseInt(progress[0]));
            Log.e("downloading: ", progress[0]);
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //dismissDialog(progress_bar_type);
            Log.e("download complete: ", file_url);
            if (file_url == mrktFile) {
                downloadPMFile(context);
            } else if (file_url == pmFile) {
                downloadJMFile(context);
            } else if (file_url == jmFile) {
                downloadComplete = true;
            }
        }

    }
}
