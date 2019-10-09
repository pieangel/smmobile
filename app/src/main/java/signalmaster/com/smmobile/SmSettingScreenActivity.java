package signalmaster.com.smmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.scichart.charting.visuals.axes.AxisAlignment;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.drawing.common.FontStyle;
import com.scichart.drawing.common.PenStyle;
import com.scichart.drawing.common.SolidPenStyle;
import com.scichart.drawing.utility.ColorUtil;

import java.util.ArrayList;

import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.Util.SmArgument;
import signalmaster.com.smmobile.mock.PrChartFragmentNOTUSE;

public class SmSettingScreenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView lblColor;
    private Switch ycukSwitch,boldSwitch,tmSwitch;
    private AxisAlignment yAlign = null;
    private IAxis yAxis = null;

    private SharedPreferences _sharedPreferences1 =null;
    private SharedPreferences _sharedPreferences2 =null;
    private SharedPreferences _sharedPreferences3 =null;
    private SharedPreferences.Editor _editor1 = null;
    private SharedPreferences.Editor _editor2 = null;
    private SharedPreferences.Editor _editor3 = null;

    private Spinner chartTypeSpinner = null;
    private PrChartFragmentNOTUSE _chartFragment = null;
    private int chartType;
    private ArrayList<String> arrayList;
    private ArrayAdapter<CharSequence> adapter;

    int color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sm_setting_screen);

        ycukSwitch = (Switch)findViewById(R.id.ycukSwitch);
        _sharedPreferences1 = getSharedPreferences("setting1",0);
        _editor1 = _sharedPreferences1.edit();
        boldSwitch = (Switch)findViewById(R.id.boldSwitch);
        _sharedPreferences2 = getSharedPreferences("setting2",0);
        _editor2 = _sharedPreferences2.edit();
        tmSwitch = (Switch)findViewById(R.id.tmSwitch);
        _sharedPreferences3 = getSharedPreferences("setting3",0);
        _editor3 = _sharedPreferences3.edit();


        if(_sharedPreferences1.getString("switch1","")=="1"){
            ycukSwitch.setChecked(true);
        } else {
            ycukSwitch.setChecked(false);
        }

        if(_sharedPreferences2.getString("switch2","")=="1"){
            boldSwitch.setChecked(true);
        } else {
            boldSwitch.setChecked(false);
        }

        if(_sharedPreferences3.getString("switch3","")=="1"){
            tmSwitch.setChecked(true);
        } else {
            tmSwitch.setChecked(false);
        }

        /*} else if(_sharedPreferences.getString("switch","")=="3"){
            boldSwitch.setChecked(true);
        } else if(_sharedPreferences.getString("switch","")=="4"){
            boldSwitch.setChecked(false);
        } else if(_sharedPreferences.getString("switch","")=="5"){
            tmSwitch.setChecked(true);
        } else if(_sharedPreferences.getString("switch","")=="6"){
            tmSwitch.setChecked(false);
        } */

        /*boldSwitch = (Switch)findViewById(R.id.boldSwitch);
        if(_sharedPreferences.getString("switch","")=="2"){
            boldSwitch.setChecked(true);

        } else {
            boldSwitch.setChecked(false);
        }

        tmSwitch = (Switch)findViewById(R.id.tmSwitch);
        if(_sharedPreferences.getString("switch","")=="3"){
            tmSwitch.setChecked(true);

        } else {
            tmSwitch.setChecked(false);
        }*/

        //데이터 가져오기
        Intent intent = getIntent();
        final SmArgManager argMgr = SmArgManager.getInstance();
        String key = intent.getStringExtra("y_click");
       final SmArgument arg = argMgr.getValue(key);
        //yAlign = (AxisAlignment)arg.get("yAlign");
        yAxis = (IAxis)arg.get("yAxis");
        _chartFragment = (PrChartFragmentNOTUSE)arg.get("_chartFragment");
        argMgr.unregister("y_click");



        chartTypeSpinner = (Spinner)findViewById(R.id.chartTypeSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.chartType,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chartTypeSpinner.setAdapter(adapter);
        chartTypeSpinner.setOnItemSelectedListener(this);


        /*HashMap<Integer,String> map = new HashMap<>();
        map.put(chartType,"CandleStick");
        map.put(chartType,"Mountain");
        map.put(chartType,"CloseLine");
        map.put(chartType,"OHLC");*/





         /*ArrayList<Integer> list = new ArrayList<>();
         list.add();*/



        //UI 객체생성
        //txtText = (TextView)findViewById(R.id.txtText);



        //String data = intent.getStringExtra("data");
        //txtText.setText(data);

        /*lblColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),ColorPalette.class);
                intent1.putExtra("y_click","y_click");
                arg.add("yAxis",yAxis);
                argMgr.register("y_click",arg);
                context.startActivity(intent1);

            }
        });*/
    }


    //취소 버튼 클릭
    public void Close(View v){
        //액티비티(팝업) 닫기
        finish();
    }
    //확인 버튼 클릭
    public void Submit(View v){


        //ycukSwitch
        if(ycukSwitch.isChecked()) {
            _editor1.putString("switch1", "1");
            _editor1.commit();
            yAxis.setAxisAlignment(AxisAlignment.Left);

        } else {
            _editor1.putString("switch1", "0");
            _editor1.commit();
            yAxis.setAxisAlignment(AxisAlignment.Right);
        }
        //boldSwitch
        if(boldSwitch.isChecked()) {
            _editor2.putString("switch2", "1");
            _editor2.commit();
            /*FontStyle labelStyle = new FontStyle(25, ColorUtil.White);
            yAxis.setTickLabelStyle(labelStyle);*/

            PenStyle tickStyle = new SolidPenStyle(0xFF279B27, true , 10, null );
            yAxis.setMajorTickLineStyle(tickStyle);
            yAxis.setMinorTickLineStyle(tickStyle);

        } else {

            _editor2.putString("switch2", "0");
            _editor2.commit();
            /*FontStyle labelStyle = new FontStyle(25, ColorUtil.White);
            yAxis.setTickLabelStyle(labelStyle);*/
            PenStyle tickStyle = new SolidPenStyle(0xFF279B27, true , 5, null );
            yAxis.setMajorTickLineStyle(tickStyle);
            yAxis.setMinorTickLineStyle(tickStyle);

        }
        //tmSwitch
        if(tmSwitch.isChecked()) {
            _editor3.putString("switch3", "1");
            _editor3.commit();
            FontStyle labelStyle = new FontStyle(20, ColorUtil.Transparent);
            yAxis.setTickLabelStyle(labelStyle);
            yAxis.setDrawLabels(false);
            yAxis.setVisibility(View.GONE);

        } else {
            _editor3.putString("switch3", "0");
            _editor3.commit();
            FontStyle labelStyle = new FontStyle(20, ColorUtil.White);
            yAxis.setTickLabelStyle(labelStyle);
            yAxis.setDrawLabels(true);
            yAxis.setVisibility(View.VISIBLE);
        }
        /*if(ycukSwitch.isChecked()) {
            _editor.putString("switch", "1");
            _editor.commit();
            yAxis.setAxisAlignment(AxisAlignment.Left);

            // intent.putExtra("result", "Close Popup");
            //setResult(RESULT_OK, intent);
        } else{
            _editor.putString("switch", "4");
            _editor.commit();
        }
        if(boldSwitch.isChecked()){
            _editor.putString("switch", "2");
            _editor.commit();
            FontStyle labelStyle = new FontStyle(14, ColorUtil.Transparent);
            yAxis.setTickLabelStyle(labelStyle);
        } else{
            _editor.putString("switch", "5");
            _editor.commit();
        }

        if(tmSwitch.isChecked()){
            _editor.putString("switch", "3");
            _editor.commit();
           // SolidPenStyle labelSolid = new SolidPenStyle(ColorUtil.Orange, false, 2.0f, null);
            FontStyle labelStyle = new FontStyle(20, ColorUtil.Blue);
            yAxis.setTickLabelStyle(labelStyle);
        } else{
            _editor.putString("switch", "6");
            _editor.commit();
        }*/




        //액티비티(팝업) 닫기
        finish();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        _chartFragment.ChangeToType(position);
        /*String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
