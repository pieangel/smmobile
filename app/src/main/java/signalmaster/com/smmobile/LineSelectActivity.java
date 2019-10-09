package signalmaster.com.smmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.chart.SmChartFragment;

public class LineSelectActivity  extends AppCompatActivity {
    
    Button newLineBtn;
    Button addLineBtn;
    ImageView closeImg;
    RecyclerView lineNewRecyclerView, lineAddRecyclerView;

    LineSelectAdapter _lineNewAdapter = null;

    RecyclerViewList recyclerViewList= null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_select_activity);

        //smChartFragment = SmChartFragment.newInstance("mock_main");
        getChartFragment();


        //리사이클러뷰
        lineNewRecyclerView = findViewById(R.id.lineNewRecyclerView);
        lineAddRecyclerView = findViewById(R.id.lineAddRecyclerView);


        recyclerViewList = new RecyclerViewList();
        ArrayList<String> _lineSelectList = (recyclerViewList.get_lineSelectList());
        _lineNewAdapter = new LineSelectAdapter(_lineSelectList);
        lineNewRecyclerView.setAdapter(_lineNewAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        lineNewRecyclerView.setLayoutManager(layoutManager);

        //초기 화면 보여짐 설정
        lineNewRecyclerView.setVisibility(View.VISIBLE);
        lineAddRecyclerView.setVisibility(View.INVISIBLE);

        _lineNewAdapter.setOnItemClickListener(new LineSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (position){
                    case 0:
                       // smChartFragment.setXAxis();
                       // smChartFragment.initSeriesType();
                        //smChartFragment.setYAxis();
                        //smChartFragment.createHorizontal();
                        if(smChartFragment != null){
                            smChartFragment.newHorizontalAnnotation();
                        }
                        finish();
                        break;
                    case 1:
                        if(smChartFragment != null){
                            smChartFragment.newVerticalLineAnnotation();
                        }
                        finish();
                        break;
                }
            }
        });

        /*newLineBtn = findViewById(R.id.newLineBtn);
        newLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineNewRecyclerView.setVisibility(View.VISIBLE);
                lineAddRecyclerView.setVisibility(View.INVISIBLE);
            }
        });

        addLineBtn = findViewById(R.id.addLineBtn);
        addLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineNewRecyclerView.setVisibility(View.INVISIBLE);
                lineAddRecyclerView.setVisibility(View.VISIBLE);
            }
        });*/

        closeImg = findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    SmChartFragment smChartFragment = null;
    public void getChartFragment(){
        SmArgManager argMgr = SmArgManager.getInstance();
        SmChartFragment chartFragment =  (SmChartFragment) argMgr.getVal("showLine","chartFragment");
        smChartFragment = chartFragment;
    }
}
