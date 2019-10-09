package signalmaster.com.smmobile.mock;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.SmIndexSelector;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.index.IndexLineManager;

public class MACDSettingDialog extends AppCompatActivity {

    TextView indicatorName,saveIndicator;
    EditText slowPeriod,fastPeriod,signalPeriod;
    int inputSlow = 12;
    int inputFast = 25;
    int inputSignal = 9;

    //지표이름
    String name;

    /*RecyclerViewList recyclerViewList = new RecyclerViewList();
    ArrayList<String> typeSpinList = recyclerViewList.getTypeSpinList();*/

    public SmIndexSelector smIndexSelector = null;
    public SmChartFragment smChartFragment = null;

    IndexLineManager indexLineManager = IndexLineManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.macd_setting_dialog);

        final SmArgManager smArgManager = SmArgManager.getInstance();

        Intent intent = getIntent();
        //name = (String)smArgManager.getVal("Indicator","name");
        name = intent.getExtras().getString("name");

        indicatorName = (TextView)findViewById(R.id.indicatorName);
        saveIndicator = (TextView)findViewById(R.id.saveIndicator);

        slowPeriod = (EditText)findViewById(R.id.slowPeriod);
        fastPeriod = (EditText)findViewById(R.id.fastPeriod);
        signalPeriod = (EditText)findViewById(R.id.signalPeriod);

        indicatorName.setText(name);
        changeIndicator(name);



        //
        saveIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(slowPeriod.getText() != null || fastPeriod.getText() != null || signalPeriod.getText() != null) {
                    inputSlow = Integer.parseInt(slowPeriod.getText().toString());
                    inputFast = Integer.parseInt(fastPeriod.getText().toString());
                    inputSignal = Integer.parseInt(signalPeriod.getText().toString());
                }

                smChartFragment =(SmChartFragment)smArgManager.getVal("Indicator","smChartFragment");

                switch (name){
                    case "MACD":

                        if(smChartFragment != null){
                            ArrayList<String> arrayList = indexLineManager.getAddIndicatorList();
                            ArrayList<String> outList = indexLineManager.getOutIndicatorList();
                            arrayList.add("외부, MACD");
                            changeSize(outList);
                            outList.add("MACD");
                            //smChartFragment.addMA(20,"ma1",0xffff0000,1.0f);
                            smChartFragment.addMACD(inputSlow,inputFast,inputSignal,mainWeight,subWeight);
                            //외부지표 추가할때마다 외부지표 개수를 저장해준다. 함수 or 클래스 하나 만들어서 관리해주기
                            finish();
                        }
                        break;
                    case "스토캐스틱" :

                        if(smChartFragment != null){
                            ArrayList<String> arrayList = indexLineManager.getAddIndicatorList();
                            ArrayList<String> outList = indexLineManager.getOutIndicatorList();
                            arrayList.add("외부, Stochastic");
                            changeSize(outList);
                            outList.add("Stochastic");
                            //smChartFragment.addMA(20,"ma1",0xffff0000,1.0f);
                            smChartFragment.addStochastic(inputSlow,inputFast,inputSignal,mainWeight,subWeight);
                            //외부지표 추가할때마다 외부지표 개수를 저장해준다. 함수 or 클래스 하나 만들어서 관리해주기
                            finish();
                        }
                        break;
                }

                /*if(slowPeriod.getText() != null || fastPeriod.getText() != null || signalPeriod.getText() != null) {
                    inputSlow = Integer.parseInt(slowPeriod.getText().toString());
                    inputFast = Integer.parseInt(fastPeriod.getText().toString());
                    inputSignal = Integer.parseInt(signalPeriod.getText().toString());
                }

                smChartFragment =(SmChartFragment)smArgManager.getVal("Indicator","smChartFragment");
                if(smChartFragment != null){
                    //smChartFragment.addMA(20,"ma1",0xffff0000,1.0f);
                    smChartFragment.addMACD(inputSlow,inputFast,inputSignal,0.25f,0.75f);
                    //외부지표 추가할때마다 외부지표 개수를 저장해준다. 함수 or 클래스 하나 만들어서 관리해주기
                    finish();
                }*/
            }
        });


    }

    float mainWeight = 0f;
    float subWeight = 0f;
    public void changeSize(ArrayList<String> arrayList){

        //갯수가 늘어날때마다 기존에 있던 weight 도 조절해줘야함.

        if(arrayList.size() == 0){
            mainWeight = 0.25f;
            subWeight = 0.75f;
        } else if(arrayList.size() == 1){
            mainWeight = 0.5f;
            subWeight = 0.5f;
        } else if(arrayList.size() == 2){
            mainWeight = 0.75f;
            subWeight = 0.75f;
        } else{
            return;
        }
    }

    //들어온 값에따라 UI를 변경해준다 . LinearLayout 아이디에 번호를 매기고 지표 종류에따라 보여주고 지워주고 한다.
    public void changeIndicator(String name){
        switch (name){
            case "MA":
                break;
            case "스토캐스틱":
                break;
            case "MACD":
                break;
            case "RSI":
                break;
            case "CCI":
                break;
            case "볼린져밴드":
                break;
            case "파러볼릭 SAR":
                break;
            case "MOMENTUM":
                break;
            case "ATR":
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        /*SmArgManager smArgManager = SmArgManager.getInstance();
        smIndexSelector = (SmIndexSelector) smArgManager.getVal("Indicator","activity");
        //smIndexSelector.setVisible(true);
        smIndexSelector.finish();*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*SmArgManager smArgManager = SmArgManager.getInstance();
        smIndexSelector = (SmIndexSelector) smArgManager.getVal("Indicator","activity");
        smIndexSelector.setVisible(true);*/
    }
}
