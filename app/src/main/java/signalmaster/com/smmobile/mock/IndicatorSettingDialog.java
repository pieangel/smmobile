package signalmaster.com.smmobile.mock;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import signalmaster.com.smmobile.CustomSpinnerAdapter;
import signalmaster.com.smmobile.NumberInputActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.RecyclerViewList;
import signalmaster.com.smmobile.SmIndexSelector;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.index.IndexLineManager;

public class IndicatorSettingDialog extends AppCompatActivity {

    LinearLayout maSettingLayout, macdSettingLayout;
    TextView inputNumber, indicatorName, saveIndicator;
    Spinner typeSpinner, colorSpinner;
    ArrayAdapter<String> typeSpinnerAdapter;
    int[] spinnerImages;
    int selectedColor, selectedThick = 0;
    //int selectedThick = 0;
    float thickness = 1.0f;
    int color = Color.parseColor("#FFFFFF");
    int count = 0;
    String name;

    IndexLineManager indexLineManager = IndexLineManager.getInstance();
    RecyclerViewList recyclerViewList = new RecyclerViewList();
    ArrayList<String> typeSpinList = recyclerViewList.getTypeSpinList();

    public SmIndexSelector smIndexSelector = null;
    public SmChartFragment smChartFragment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicator_setting_dialog);

        final SmArgManager smArgManager = SmArgManager.getInstance();
        //name = (String) smArgManager.getVal("Indicator", "name");
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");

        indicatorName = (TextView) findViewById(R.id.indicatorName);
        inputNumber = (TextView) findViewById(R.id.inputNumber);
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
        saveIndicator = (TextView) findViewById(R.id.saveIndicator);

        indicatorName.setText(name);
        //changeIndicator(name);


        inputNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NumberInputActivity.class);
                startActivity(intent);
                smArgManager.registerToMap("input", "periodTxt", inputNumber);
                smArgManager.registerToMap("input", "periodValue", inputNumber.getText().toString());
            }
        });

        spinnerImages = new int[]{R.drawable.color_white, R.drawable.color_yellow, R.drawable.color_orange, R.drawable.color_thick_orange, R.drawable.color_citrus, R.drawable.color_blue, R.drawable.color_purple};

        // 어댑터와 스피너를 연결
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), spinnerImages);
        colorSpinner.setAdapter(customSpinnerAdapter);

        //스피너에서 아이템 선택시 호출하도록 한다.
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selected_fruit_idx = colorSpinner.getSelectedItemPosition();
                //Toast.makeText(getApplication(),spinnerImages[selected_fruit_idx],Toast.LENGTH_SHORT).show();
                /*if(selectedColor == colorSpinner.getSelectedItemPosition()){

                }*/
                selectedColor = position;
                switch (selectedColor) {
                    case 0:
                        color = Color.parseColor("#FFFFFF");
                        break;
                    case 1:
                        color = Color.parseColor("#F1C530");
                        break;
                    case 2:
                        color = Color.parseColor("#E58030");
                        break;
                    case 3:
                        color = Color.parseColor("#E65042");
                        break;
                    case 4:
                        color = Color.parseColor("#D2571B");
                        break;
                    case 5:
                        color = Color.parseColor("#2D7FB7");
                        break;
                    case 6:
                        color = Color.parseColor("#9A59B3");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //굵기 타입
        typeSpinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.symbol_selector_item, typeSpinList);
        typeSpinnerAdapter.setDropDownViewResource(R.layout.type_spinner_row);
        typeSpinner.setAdapter(typeSpinnerAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedThick = position;
                switch (selectedThick) {
                    case 0:
                        thickness = 1.0f;
                        break;
                    case 1:
                        thickness = 2.0f;
                        break;
                    case 2:
                        thickness = 3.0f;
                        break;
                    case 3:
                        thickness = 4.0f;
                        break;
                    case 4:
                        thickness = 5.0f;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //저장버튼
        saveIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                smChartFragment = (SmChartFragment) smArgManager.getVal("Indicator", "smChartFragment");
                //smArgManager.unregisterFromMap("Indicator");
                switch (name) {
                    case "MA":
                        if (smChartFragment != null) {
                            count++;
                            //smChartFragment.addMA(20,"ma1",0xffff0000,1.0f);
                            smChartFragment.addMA(Integer.parseInt(inputNumber.getText().toString()), "ma" + count, color, thickness);
                            //ArrayList<String> addIndicatorList = recyclerViewList.setAddIndicatorList();

                            ArrayList<String> arrayList = indexLineManager.getAddIndicatorList();
                            arrayList.add("name : " + "ma"+count+inputNumber.getText().toString()+ "color , " +  color+ "굵기, " + thickness);
                            //recyclerViewList.setAddIndicatorList(arrayList);
                            //addIndicatorList.add("ma"+count+inputNumber.getText().toString()+color+thickness);

                            finish();
                        }
                        break;

                    case "RSI":
                        if (smChartFragment != null) {
                            //smChartFragment.addMA(20,"ma1",0xffff0000,1.0f);

                            ArrayList<String> arrayList = indexLineManager.getAddIndicatorList();
                            ArrayList<String> outList = indexLineManager.getOutIndicatorList();
                            arrayList.add("외부 , RSI");
                            changeSize(outList);
                            //smChartFragment.addRSI(Integer.parseInt(inputNumber.getText().toString()), color, thickness, 0.25f,0.75f);
                            smChartFragment.addRSI(Integer.parseInt(inputNumber.getText().toString()), color, thickness, mainWeight,subWeight);

                            finish();
                        }
                        break;
                }

            }
        });


    }

    float mainWeight = 0f;
    float subWeight = 0f;
    public void changeSize(ArrayList<String> arrayList){


        if(arrayList.size() == 0){
            mainWeight = 4f;
            subWeight = 1f;
        } else if(arrayList.size() == 1){
            mainWeight = 0.5f;
            subWeight = 0.75f;
        } else if(arrayList.size() == 2){
            mainWeight = 0.75f;
            subWeight = 0.75f;
        } else{
            return;
        }
    }

    //들어온 값에따라 UI를 변경해준다 . LinearLayout 아이디에 번호를 매기고 지표 종류에따라 보여주고 지워주고 한다.
    public void changeIndicator(String name) {

        maSettingLayout = (LinearLayout) findViewById(R.id.maSettingLayout);
        macdSettingLayout = (LinearLayout) findViewById(R.id.macdSettingLayout);

        switch (name) {
            case "MA":
                maSettingLayout.setVisibility(View.VISIBLE);
                macdSettingLayout.setVisibility(View.GONE);
                break;
            case "스토캐스틱":
                break;
            case "MACD":
                maSettingLayout.setVisibility(View.GONE);
                macdSettingLayout.setVisibility(View.VISIBLE);
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


    //ma에대한 모든 설정
    /*private void settingMA() {
        SmArgManager smArgManager = SmArgManager.getInstance();
        smChartFragment = (SmChartFragment) smArgManager.getVal("Indicator", "smChartFragment");
        if (smChartFragment != null) {
            count++;
            //smChartFragment.addMA(20,"ma1",0xffff0000,1.0f);
            smChartFragment.addMA(Integer.parseInt(inputNumber.getText().toString()), "ma" + count, color, thickness);
            finish();
        }
    }*/

    //macd에 대한 모든 설정
    private void settingMACD() {

    }

}
