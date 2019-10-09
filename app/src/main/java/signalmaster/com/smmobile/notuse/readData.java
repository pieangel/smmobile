package signalmaster.com.smmobile.notuse;

import java.util.ArrayList;

import signalmaster.com.smmobile.market.SmCategory;
import signalmaster.com.smmobile.market.SmMarket;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.symbol.SmSymbol;

public class readData {



    public void read(){
        SmMarketManager smMarketManager = SmMarketManager.getInstance();
        //SmMarket smMarket = new SmMarket();
        // ArrayList<SmMarket> _categoryList = (smMarketManager.get_marketList());
        ArrayList<SmMarket> _marketList = (smMarketManager.get_marketList());

        for(int i=0;i<_marketList.size();i++) {
            SmMarket mrkt = _marketList.get(i);
            ArrayList<SmCategory> catList = mrkt.get_categoryList();
            for(SmCategory cat : catList){
                ArrayList<SmSymbol>  smList = cat.get_symbolList();
                /*if(symList.size()>0){
                    SmSymbol sym = symList.get(catList.size());
                    String name = sym.seriesNmKr;
                }*/
            }
        }
    }
}
