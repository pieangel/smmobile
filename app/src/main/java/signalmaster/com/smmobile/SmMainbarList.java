package signalmaster.com.smmobile;

import java.util.ArrayList;

public class SmMainbarList {

    private ArrayList<String> _mainList = new ArrayList<>();

    public ArrayList<String> get_mainList() {
        getMainbar();
        return _mainList;
    }

    private void getMainbar(){

        _mainList.add("현재가");

        _mainList.add("관심종목");

        _mainList.add("모의매매");

        _mainList.add("주문");

        _mainList.add("C 로보");

        _mainList.add("포트폴리오");

        _mainList.add("상품");

        _mainList.add("전문가 클럽");

    }
}
