package signalmaster.com.smmobile.annotation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.Util.SmArgument;
import signalmaster.com.smmobile.mock.PrChartFragmentNOTUSE;

public class SmJeepyoSwitch extends AppCompatActivity {

    private Switch jeepyoSwitch;
    private SharedPreferences _sharedPreferences =null;
    private SharedPreferences.Editor _editor = null;
    private PrChartFragmentNOTUSE _chartFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sm_jeepyo_screen);

        jeepyoSwitch = (Switch)findViewById(R.id.jeepyoSwitch);
        _sharedPreferences = getSharedPreferences("setting",0);
        _editor = _sharedPreferences.edit();

        if(_sharedPreferences.getString("switch","")=="1"){
            jeepyoSwitch.setChecked(true);
        } else {
            jeepyoSwitch.setChecked(false);
        }


        Intent intent = getIntent();
        final SmArgManager argMgr = SmArgManager.getInstance();
        String key = intent.getStringExtra("j_click");
        final SmArgument arg = argMgr.getValue(key);
        //yAlign = (AxisAlignment)arg.get("yAlign");
        _chartFragment = (PrChartFragmentNOTUSE)arg.get("_chartFragment");
        argMgr.unregister("j_click");
    }


    //확인 버튼 클릭
    public void Submit(View v){
        //액티비티(팝업) 닫기
        if(jeepyoSwitch.isChecked()){
            _editor.putString("switch","1");
            _editor.commit();

        }

        finish();
    }

    //취소 버튼 클릭
    public void Close(View v){
        //액티비티(팝업) 닫기
        finish();
    }


}
