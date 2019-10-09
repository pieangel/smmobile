package signalmaster.com.smmobile;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import signalmaster.com.smmobile.Util.SmLayoutManager;
import signalmaster.com.smmobile.chart.SmChartFragment;

public class IncomeChartActivity extends AppCompatActivity {

    SmChartFragment smChartFragment = null;
    ImageView closeImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_chart_activity);

        smChartFragment = SmChartFragment.newInstance("income_chart");
        SmLayoutManager smLayoutManager = SmLayoutManager.getInstance();
        smLayoutManager.register("income_chart",smChartFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.incomeChartContainer ,smChartFragment);
        fragmentTransaction.commit();

        closeImg = (ImageView)findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
