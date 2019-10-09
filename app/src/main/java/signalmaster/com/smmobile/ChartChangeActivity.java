package signalmaster.com.smmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.Util.SmArgument;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.chart.SmSeriesType;


public class ChartChangeActivity extends AppCompatActivity {

    TextView saveTxt;
    SmSeriesType selectedPosition = SmSeriesType.CandleStick;

    LinearLayout mountainLout, lineLout, candleLout, ohlcLout;

    SmChartFragment smChartFragment = null;

    ImageView closeImg;

    private void setSelectedSeriesType() {
        SmArgManager argMgr = SmArgManager.getInstance();
        SmChartFragment chartFragment =  (SmChartFragment) argMgr.getVal("seriesType","chartFragment");
        if (chartFragment != null)
            setSeriesType(chartFragment.getSeriesType());
    }

    //차트 시리즈 선택시 효과
    private void setSeriesType(SmSeriesType seriesType) {
        switch (seriesType) {
            case CandleStick:
                candleLout.setBackgroundResource(R.drawable.radius_black_button_selected);
                lineLout.setBackgroundResource(R.drawable.radius_black_button);
                mountainLout.setBackgroundResource(R.drawable.radius_black_button);
                ohlcLout.setBackgroundResource(R.drawable.radius_black_button);
                break;
            case MountainView:
                mountainLout.setBackgroundResource(R.drawable.radius_black_button_selected);
                lineLout.setBackgroundResource(R.drawable.radius_black_button);
                candleLout.setBackgroundResource(R.drawable.radius_black_button);
                ohlcLout.setBackgroundResource(R.drawable.radius_black_button);
                break;
            case Line:
                lineLout.setBackgroundResource(R.drawable.radius_black_button_selected);
                mountainLout.setBackgroundResource(R.drawable.radius_black_button);
                candleLout.setBackgroundResource(R.drawable.radius_black_button);
                ohlcLout.setBackgroundResource(R.drawable.radius_black_button);
                break;
            case Ohlc:
                ohlcLout.setBackgroundResource(R.drawable.radius_black_button_selected);
                lineLout.setBackgroundResource(R.drawable.radius_black_button);
                candleLout.setBackgroundResource(R.drawable.radius_black_button);
                mountainLout.setBackgroundResource(R.drawable.radius_black_button);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_change_activity);

        mountainLout = (LinearLayout) findViewById(R.id.mountainLout);
        lineLout = (LinearLayout) findViewById(R.id.lineLout);
        candleLout = (LinearLayout) findViewById(R.id.candleLout);
        ohlcLout = (LinearLayout) findViewById(R.id.ohlcLout);

        setSelectedSeriesType();

        mountainLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //효과
                setSeriesType(SmSeriesType.MountainView);

                selectedPosition = SmSeriesType.MountainView;
                applySeriesType();
            }
        });

        lineLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSeriesType(SmSeriesType.Line);
                selectedPosition = SmSeriesType.Line;
                applySeriesType();
            }
        });

        candleLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSeriesType(SmSeriesType.CandleStick);
                selectedPosition = SmSeriesType.CandleStick;
                applySeriesType();
            }
        });

        ohlcLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSeriesType(SmSeriesType.Ohlc);
                selectedPosition = SmSeriesType.Ohlc;
                applySeriesType();
            }
        });

        closeImg = findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        /*saveTxt = findViewById(R.id.saveTxt);
        saveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedPosition == null) {
                    finish();
                    return;
                }


                SmArgManager argMgr = SmArgManager.getInstance();

                SmChartFragment smChartFragment = (SmChartFragment)argMgr.getVal("seriesType","chartFragment");
                if(smChartFragment != null){
                    smChartFragment.changeSeriesType(selectedPosition);
                    argMgr.unregisterFromMap("seriesType");
                }

                finish();
            }
        });*/

    }

    public void applySeriesType(){
        SmArgManager argMgr = SmArgManager.getInstance();

        SmChartFragment smChartFragment = (SmChartFragment)argMgr.getVal("seriesType","chartFragment");
        if(smChartFragment != null){
            smChartFragment.changeSeriesType(selectedPosition);
            argMgr.unregisterFromMap("seriesType");
        }

        finish();
    }

}
