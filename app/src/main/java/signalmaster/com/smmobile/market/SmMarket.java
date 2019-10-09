package signalmaster.com.smmobile.market;

import java.util.ArrayList;

import signalmaster.com.smmobile.symbol.SmSymbol;

public class SmMarket {

    // 시장 이름 : 금리, 통화 등등
    public String name = "";
    public int index = 0;
    // 시장에 있는 품목 리스트
    private ArrayList<SmCategory> _categoryList = new ArrayList<>();


    public ArrayList<SmCategory> get_categoryList() {

        return _categoryList;
    }

    public ArrayList<SmCategory> getCategoryListForDisplay() {
        ArrayList<SmCategory> catList = new ArrayList<>();
        for(SmCategory cat : _categoryList) {
            if (cat.get_symbolList().size() > 0) {
                catList.add(cat);
            }
        }
        return catList;
    }

    public ArrayList<SmSymbol> getRecentSymbolListFromCategory() {
        ArrayList<SmSymbol> symList = new ArrayList<>();
        for(SmCategory cat : _categoryList) {
            if (cat.getRecentSymbol() != null)
                symList.add(cat.getRecentSymbol());
        }
        return symList;
    }

    public SmCategory addCategory(String cat_code) {
        SmCategory new_cat = findCategory(cat_code);
        if (new_cat == null) {
            new_cat = new SmCategory();
            new_cat.marketCode = cat_code;
            _categoryList.add(new_cat);
        }

        return new_cat;
    }

    //시장에 품목을 추가하는 함수
    public SmCategory addCategory(SmCategory cat){
        _categoryList.add(cat);

        return cat;
    }

    public SmMarket() {

    }

    public SmCategory findCategory(String categoryCode) {
        for(SmCategory cat : _categoryList) {
            if (cat.marketCode.compareTo(categoryCode) == 0) {
                return cat;
            }
        }

        return null;
    }

    public void uur() {
        for(int i=0; i < _categoryList.size();i++) {
            System.out.println(i + ":" + _categoryList.get(i));
        }
    }
}
