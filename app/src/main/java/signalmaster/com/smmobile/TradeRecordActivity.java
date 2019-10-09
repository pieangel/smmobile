package signalmaster.com.smmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class TradeRecordActivity extends AppCompatActivity {

    ImageView closeImg;
    RecyclerView conRecyclerView,outStandingRecyclerView;
    RightOptionAdapter rightOptionAdapter = new RightOptionAdapter();

    RecyclerViewList recyclerViewList = new RecyclerViewList();
    ArrayList<String> conclusionList = recyclerViewList.getConclusionList();
    ArrayList<String> outStandingList = recyclerViewList.getOutStandingList();

    Spinner accountSpinner;
    ArrayAdapter<String> adapter;

    private ArrayList<String> accountList = new ArrayList<>();
    private void account(){
        accountList.add("계좌1");
        accountList.add("계좌2");
        accountList.add("계좌3");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_record_activity);

        account();

        closeImg = findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //체결내역
        conRecyclerView = findViewById(R.id.conRecyclerView);
        conRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rightOptionAdapter = new RightOptionAdapter();
        conRecyclerView.setAdapter(rightOptionAdapter);

        //미결제
        outStandingRecyclerView = findViewById(R.id.outStandingRecyclerView);
        outStandingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rightOptionAdapter = new RightOptionAdapter();
        outStandingRecyclerView.setAdapter(rightOptionAdapter);

        accountSpinner = (Spinner)findViewById(R.id.accountSpinner);
        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.account_selector_item,accountList);
        adapter.setDropDownViewResource(R.layout.account_selector_item);
        accountSpinner.setAdapter(adapter);
    }
}
