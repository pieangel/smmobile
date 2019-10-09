package signalmaster.com.smmobile.market;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import signalmaster.com.smmobile.favorite.SmFavoriteFragment;
import signalmaster.com.smmobile.symbol.SmSymbol;

public class SmMarketManager implements Serializable {


    private static volatile SmMarketManager mMoleInstance;

    public static SmMarketManager getInstance() {
        if (mMoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmMarketManager.class) {
                if (mMoleInstance == null) mMoleInstance = new SmMarketManager();
            }
        }
        return mMoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmMarketManager readResolve() {
        return getInstance();
    }

    private SmMarketManager() {
        //Prevent form the reflection api.
        if (mMoleInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    private HashMap<String, SmMarketInfo> _categoryMarketMap = new HashMap<>();
    private HashMap<String, Integer> _marketIndexMap = new HashMap<>();


    public ArrayList<SmSymbol> GetSymbolListByMarket(String market_name) {
        ArrayList<SmSymbol> list = null;
        SmMarket mrkt = findMarket(market_name);
        if (mrkt != null) {
            list = mrkt.getRecentSymbolListFromCategory();
        }

        return list;
    }

    public int getMarketIndex(String marketType) {
        if (_marketIndexMap.containsKey(marketType)) {
            return _marketIndexMap.get(marketType);
        }
        else
            return  -1;
    }

    public SmSymbol getRecentMonthSymbol(String market_name, String category_code) {
        SmMarket market = findMarket(market_name);
        if (market == null)
            return null;
        SmCategory cat = market.findCategory(category_code);
        if (cat == null)
            return null;
        SmSymbol symbol = cat.getRecentSymbol();
        return symbol;
    }
    //시장 목록
    private ArrayList<SmMarket> _marketList = new ArrayList<>();

    public ArrayList<SmMarket> get_marketList() {
        return _marketList;
    }

    public void AddToCategoryMarketMap(String pmCode, SmMarketInfo marketInfo) {
        _categoryMarketMap.put(pmCode, marketInfo);
    }

    public ArrayList<String> getMarketList() {
        ArrayList<String> mlist = new ArrayList<>();
        for(SmMarket m : _marketList) {
            mlist.add(m.name);
        }

        return mlist;
    }

    public SmMarketInfo findMarketInfo(String pmCode) {
        if (_categoryMarketMap.containsKey(pmCode))
            return _categoryMarketMap.get(pmCode);
        else
            return null;
    }
    //시장 추가
    public SmMarket addMarket(String marketType){
        SmMarket found = findMarket(marketType);
        if(found == null){
            found = new SmMarket();
            found.name = marketType;
            _marketList.add(found);
            _marketIndexMap.put(marketType, _marketList.size() - 1);
        }
        return found;
    }

    public SmMarket addMarket(String marketType, int market_index){
        SmMarket found = findMarket(marketType);
        if(found == null){
            found = new SmMarket();
            found.name = marketType;
            found.index = market_index;
            _marketList.add(found);
            _marketIndexMap.put(marketType, market_index);
        }
        found.index = market_index;
        return found;
    }

    //시장 찾기
    SmMarket findMarket(String marketType){
        for (SmMarket market : _marketList){
            if(market.name.compareTo(marketType) == 0){
                return market;
            }
        }
        return null;
    }

    public SmCategory findCategory(String marketType, String marketCode) {
        SmMarket market = addMarket(marketType);
        if (market == null)
            return null;
        SmCategory cat = market.findCategory(marketCode);
        if (cat == null)
            return null;

        return cat;
    }

    public SmSymbol getDefaultSymbol() {
        SmMarket market = findMarket("지수");
        SmCategory category = market.findCategory("CN");
        SmSymbol symbol = category.getRecentSymbol();
        return symbol;
    }

    public  ArrayList<SmSymbol> getRecentSymbolList() {
        ArrayList<SmSymbol> sym_list = new ArrayList<>();
        for (SmMarket market : _marketList){
            ArrayList<SmSymbol> curList = market.getRecentSymbolListFromCategory();
            sym_list.addAll(curList);
        }

        return sym_list;
    }


    private boolean madeFavorite = false;
    public ArrayList<SmSymbol> makeDefaultFavoriteList() {
        for (SmMarket market : _marketList){
            ArrayList<SmSymbol> curList = market.getRecentSymbolListFromCategory();
            if (curList.size() > 0)
                favoriteList.add(curList.get(0));
            //배열 거꾸로
            Collections.reverse(favoriteList);
        }

        if (favoriteList.size() > 0)
            madeFavorite = true;
        return favoriteList;
    }
    public ArrayList<SmSymbol> getFavoriteList() {
        if (!madeFavorite)
            return makeDefaultFavoriteList();
        else
            return favoriteList;
    }
    private ArrayList<SmSymbol> favoriteList = new ArrayList<>();

    //관심종목 추가
    public void AddFavorite(SmSymbol symbol) {
        if (symbol == null)
            return;

        //중복체크
        if(!favoriteList.contains(symbol)){
            favoriteList.add(0,symbol);
        }
        //favoriteList.add(0,symbol);
    }

    public void removeFavoriteList(String symbolCode) {
        for(int i = 0; i < favoriteList.size(); ++i) {
            SmSymbol symbol = favoriteList.get(i);
            if (symbol.code == symbolCode) {
                favoriteList.remove(i);
            }
        }
    }
}