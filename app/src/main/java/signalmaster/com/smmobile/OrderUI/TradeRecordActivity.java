package signalmaster.com.smmobile.OrderUI;

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

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.RecyclerViewList;
import signalmaster.com.smmobile.RightOptionAdapter;
import signalmaster.com.smmobile.account.SmAccount;
import signalmaster.com.smmobile.account.SmAccountManager;
import signalmaster.com.smmobile.order.SmTotalOrderManager;

public class TradeRecordActivity extends AppCompatActivity {

    ImageView closeImg;
    RecyclerView conRecyclerView,outStandingRecyclerView;
    OrderAdapter orderAdapter = new OrderAdapter();
    OrderAdapter acceptedAdapter = new OrderAdapter();

    RecyclerViewList recyclerViewList = new RecyclerViewList();
    ArrayList<String> conclusionList = recyclerViewList.getConclusionList();
    ArrayList<String> outStandingList = recyclerViewList.getOutStandingList();

    Spinner accountSpinner;
    ArrayAdapter<String> adapter;

    private ArrayList<String> accountList = new ArrayList<>();
    private void account(){
        ArrayList<SmAccount> accountArrayList =  SmAccountManager.getInstance().getAccountList();
        for(SmAccount item : accountArrayList) {
            accountList.add(item.accountNo);
        }
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
        orderAdapter = new OrderAdapter(SmTotalOrderManager.getInstance().getOrderList());
        conRecyclerView.setAdapter(orderAdapter);

        //미결제
        outStandingRecyclerView = findViewById(R.id.outStandingRecyclerView);
        outStandingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        acceptedAdapter = new OrderAdapter(SmTotalOrderManager.getInstance().getAcceptedOrderList());
        outStandingRecyclerView.setAdapter(acceptedAdapter);

        accountSpinner = (Spinner)findViewById(R.id.accountSpinner);
        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.account_selector_item,accountList);
        adapter.setDropDownViewResource(R.layout.account_selector_item);
        accountSpinner.setAdapter(adapter);
    }
}
