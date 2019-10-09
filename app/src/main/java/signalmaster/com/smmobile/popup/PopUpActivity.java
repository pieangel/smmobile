package signalmaster.com.smmobile.popup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.Util.SmArgument;
import signalmaster.com.smmobile.market.SmMarket;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.sise.SubListAdapter;
import signalmaster.com.smmobile.symbol.SmSymbol;

public class PopUpActivity extends AppCompatActivity  {
    //private ArrayList<SmSymbol> _symList = null;
    int valuePicker=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_activity);


        Intent intent = getIntent();
        SmArgManager argMgr = SmArgManager.getInstance();
        String key = intent.getStringExtra("sym_click");
        SmArgument arg = argMgr.getValue(key);
        final int position = (int)arg.get("sym_pos");
        final int market =  (int)arg.get("mrkt_pos");
        final SubListAdapter _subListAdapter =  (SubListAdapter)arg.get("caller");
        argMgr.unregister("sym_click");

        final SmMarketManager smMarketManager = SmMarketManager.getInstance();
        final ArrayList<SmMarket> _marketList = (smMarketManager.get_marketList());
        SmMarket mrkt = _marketList.get(market);
        ArrayList<SmSymbol> symList = mrkt.getRecentSymbolListFromCategory();
        //SubListAdapter를 new 해줘서 변하지않음
        //final SubListAdapter _subListAdapter =  ((MainActivity)getApplicationContext()).subListAdapter; //new SubListAdapter(symList);

      //  final String [] pickerVals = new String[5];
      // final String [] pickerVals  = new String[] {"dog", "cat", "lizard", "turtle", "axolotl"};



        List<String> ListItems = new ArrayList<>();

        for(int i=0;i<symList.get(position).get_category().get_symbolList().size();i++){
            //position 값의 월물목록
            System.out.println(symList.get(position).get_category().get_symbolList().get(i).seriesNmKr);

            //add
             ListItems.add(symList.get(position).get_category().get_symbolList().get(i).seriesNmKr);
         //  pickerVals[i] = symList.get(position).get_category().get_symbolList().get(i).seriesNmKr;
          //  System.out.println(pickerVals);
        }

        final String [] pickerVals = new String[symList.get(position).get_category().get_symbolList().size()];
        int size = 0;
        for (String temp : ListItems){
            pickerVals[size++]=temp;
        }
        System.out.println(pickerVals);

        //smCategory.getIndexByName(symList.get(position).get_category().get_symbolList().get(0).seriesNmKr);

        NumberPicker picker = findViewById(R.id.numberPicker);
        picker.setMaxValue(symList.get(position).get_category().get_symbolList().size()-1);
        picker.setMinValue(0);

        picker.setDisplayedValues(pickerVals);

        picker.setWrapSelectorWheel(false);
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                valuePicker = picker.getValue();
                Log.d("picker value",pickerVals[valuePicker]);
            }
        });

        Button okBtn = (Button)findViewById(R.id.ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택한 값에 버튼을 누름 값 가져옴
                System.out.println(pickerVals[valuePicker]);
                /*if(smCategory.getIndexByName(pickerVals[valuePicker])==-1){
                    return;
                }*/

                /*
                SmSymbol curSym = _subListAdapter.get_symList().get(position);
                SmCategory curCat = curSym.get_category();
                if (curCat == null)
                    return;
                _subListAdapter.get_symList().set(position, curCat.get_symbolList().get(1));
                _subListAdapter.notifyDataSetChanged();
                for(int i=0;i<_subListAdapter.get_symList().size();i++){
                    System.out.println(_subListAdapter.get_symList().get(i).seriesNmKr);
                }
                */
                _subListAdapter.changeSymbol(position, valuePicker);
                finish();
            }
        });

        Button cancelBtn = (Button)findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //툴바 컨트롤
        Toolbar tb = (Toolbar)findViewById(R.id.popupToolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("");
    }


    public void show(){

    }
}



