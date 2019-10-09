package signalmaster.com.smmobile;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SmOptionList {
    private ArrayList<String> _optionList = new ArrayList<>();

    public ArrayList<String> get_optionList() {
        getOptionbar();
        return _optionList;
    }


    private void getOptionbar(){

        _optionList.add("5분");

        _optionList.add("15분");

        _optionList.add("30분");

        _optionList.add("60분");

        _optionList.add("1일");

        _optionList.add("1주");

        /*_optionList.add("1시간");

        _optionList.add("1달");

        _optionList.add("1년");*/

    }
}
