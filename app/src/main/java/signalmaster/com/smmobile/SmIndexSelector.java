package signalmaster.com.smmobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.index.IndexNewAdapter;
import signalmaster.com.smmobile.index.IndexLineManager;
import signalmaster.com.smmobile.mock.IndexAddAdapter;
import signalmaster.com.smmobile.mock.IndicatorSettingDialog;
import signalmaster.com.smmobile.mock.MACDSettingDialog;

public class SmIndexSelector extends AppCompatActivity {

    LinearLayout emptyLout;
    Button newOrderBtn;
    Button addOrderBtn;
    ImageView closeImg;
    RecyclerView indexNewRecyclerView,indexAddRecyclerView;

    //추가할 어댑터 목록
    IndexNewAdapter _indexNewAdapter = null;
    //추가되어있는 어댑터
    IndexAddAdapter indexAddAdapter = null;

    SmIndexSelector self = this ;

    RecyclerViewList recyclerViewList = new RecyclerViewList();
    ArrayList<String> _indexSelectList = ( recyclerViewList.get_indexSelectList());

    IndexLineManager indexLineManager = IndexLineManager.getInstance();


    //ArrayList<String> addIndicatorList = recyclerViewList.getAddIndicatorList();
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_selector);

        getChartFragment();

        emptyLout = findViewById(R.id.emptyLout);

        //목록 리스트
        indexNewRecyclerView = findViewById(R.id.indexNewRecyclerView);
        indexNewRecyclerView.setVisibility(View.VISIBLE);
        // 추가되어있는 리스트
        indexAddRecyclerView = findViewById(R.id.indexAddRecyclerView);
        indexAddRecyclerView.setVisibility(View.INVISIBLE);

        //newList
        _indexNewAdapter = new IndexNewAdapter(_indexSelectList);
        //_indexNewAdapter.smIndexSelector = this;
        indexNewRecyclerView.setAdapter(_indexNewAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        indexNewRecyclerView.setLayoutManager(layoutManager);

        //addList
        //계속 new 가 됨
        arrayList = indexLineManager.getAddIndicatorList();
        indexAddAdapter = new IndexAddAdapter(arrayList);
        //indexAddAdapter = new IndexAddAdapter(addIndicatorList);
        //indexAddAdapter = new IndexAddAdapter(recyclerViewList.getAddIndicatorList());
        //indexAddAdapter.notifyDataSetChanged();
        indexAddRecyclerView.setAdapter(indexAddAdapter);

        RecyclerView.LayoutManager addlayoutManager = new LinearLayoutManager(getApplicationContext());
        indexAddRecyclerView.setLayoutManager(addlayoutManager);


        newOrderBtn = findViewById(R.id.newOrderBtn);
        addOrderBtn = findViewById(R.id.addOrderBtn);
        newOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexNewRecyclerView.setVisibility(View.VISIBLE);
                indexAddRecyclerView.setVisibility(View.GONE);
                emptyLout.setVisibility(View.GONE);

                addOrderBtn.setBackgroundColor(Color.parseColor("#2B3652"));
                newOrderBtn.setBackgroundColor(Color.parseColor("#2292FF"));
            }
        });


        addOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexNewRecyclerView.setVisibility(View.GONE);
                indexAddRecyclerView.setVisibility(View.VISIBLE);

                addOrderBtn.setBackgroundColor(Color.parseColor("#2292FF"));
                newOrderBtn.setBackgroundColor(Color.parseColor("#2B3652"));

                if(arrayList.size() == 0){
                    indexAddRecyclerView.setVisibility(View.GONE);
                    emptyLout.setVisibility(View.VISIBLE);
                }

            }
        });

        closeImg = findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        _indexNewAdapter.setOnItemClickListener(new IndexNewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                //임시로 switch문 사용 IndicatorSettingDialog 에서 모든일을 처리해준다.

                //기본 인텐트 값;
                Intent intent = new Intent(MainActivity.context, IndicatorSettingDialog.class);
                SmArgManager smArgManager = SmArgManager.getInstance();
                smArgManager.registerToMap("Indicator","smChartFragment",smChartFragment);
                switch (pos){
                    case 0:
                        //Intent intent = new Intent(MainActivity.context, IndicatorSettingDialog.class);
                        intent.putExtra("name",_indexSelectList.get(pos));
                        MainActivity.context.startActivity(intent);

                        //smArgManager.registerToMap("Indicator","name",_indexSelectList.get(pos));
                        //smArgManager.registerToMap("Indicator","smChartFragment",smChartFragment);
                        //self.setVisible(false);
                        finish();
                        break;
                    case 1:
                        intent = new Intent(MainActivity.context, MACDSettingDialog.class);
                        intent.putExtra("name",_indexSelectList.get(pos));
                        MainActivity.context.startActivity(intent);
                        //smArgManager.registerToMap("Indicator","smChartFragment",smChartFragment);
                        finish();
                        break;
                    case 2:
                        //Intent intent2 = new Intent(MainActivity.context, MACDSettingDialog.class);
                        intent = new Intent(MainActivity.context, MACDSettingDialog.class);
                        intent.putExtra("name",_indexSelectList.get(pos));
                        MainActivity.context.startActivity(intent);
                        //smArgManager.registerToMap("Indicator","name",_indexSelectList.get(pos));
                        //smArgManager.registerToMap("Indicator","smChartFragment",smChartFragment);
                        //self.setVisible(false);
                        finish();
                        break;
                    case 3:
                        /*Intent intent3 = new Intent(MainActivity.context, IndicatorSettingDialog.class);
                        MainActivity.context.startActivity(intent3);*/
                        intent.putExtra("name",_indexSelectList.get(pos));
                        MainActivity.context.startActivity(intent);

                        //smArgManager.registerToMap("indicator","name",_indexSelectList.get(pos));
                        //smArgManager.registerToMap("indicator","smChartFragment",smChartFragment);
                        finish();
                }

            }
        });

    }

    public  SmChartFragment smChartFragment = null;
    private void getChartFragment(){
        SmArgManager argMgr = SmArgManager.getInstance();
        SmChartFragment chartFragment =  (SmChartFragment) argMgr.getVal("showIndicator","chartFragment");
        smChartFragment = chartFragment;
    }

}
