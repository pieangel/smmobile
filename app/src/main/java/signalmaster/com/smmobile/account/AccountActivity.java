package signalmaster.com.smmobile.account;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.RightOptionAdapter2;
import signalmaster.com.smmobile.position.SmPosition;
import signalmaster.com.smmobile.position.SmTotalPositionManager;

public class AccountActivity extends AppCompatActivity {

    ImageView closeImg;
    RecyclerView profitRecyclerView;
    //RightOptionAdapter rightOptionAdapter = new RightOptionAdapter();
    RightOptionAdapter2 positionListAdapter = null;


    TextView inital_balance,totalPLTxt;

    Spinner accountSpinner;
    ArrayAdapter<String> adapter;

    private ArrayList<String> accountList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        SmAccountManager smAccountManager = SmAccountManager.getInstance();
        ArrayList<SmAccount> acList = smAccountManager.getAccountList();
        if(acList.size() > 0) {
            for(int i = 0; i < acList.size(); ++i)
                accountList.add(acList.get(i).accountNo);
        }

        inital_balance = findViewById(R.id.inital_balance);
        totalPLTxt = findViewById(R.id.totalPLTxt);
        closeImg = findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //총손익
        profitRecyclerView = findViewById(R.id.profitRecyclerView);
        profitRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        positionListAdapter = new RightOptionAdapter2();
        profitRecyclerView.setAdapter(positionListAdapter);



        if (acList.size() > 0) {
            inital_balance.setText(String.format(Locale.getDefault(),"%.0f", acList.get(0).inital_balance));
            double pure_profit_loss = acList.get(0).trade_pl - acList.get(0).fee;
            totalPLTxt.setText(String.format(Locale.getDefault(),"%.0f",pure_profit_loss));

            //순손익
            if(pure_profit_loss > 0){
                totalPLTxt.setTextColor(Color.parseColor("#46962B"));
            }  else if(pure_profit_loss == 0){
                totalPLTxt.setTextColor(Color.parseColor("#000000"));
            } else {
                totalPLTxt.setTextColor(Color.parseColor("#B14333"));
            }
        }


        accountSpinner = (Spinner)findViewById(R.id.accountSpinner);
        accountSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Object item = parent.getItemAtPosition(pos);
                        System.out.println(item.toString());     //prints the text in spinner item.
                        String account_no = item.toString();
                        ArrayList<SmPosition> positions = SmTotalPositionManager.getInstance().getPositionListByAccountNo(account_no);
                        // 새로운 리스트를 매칭 시킨다.
                        positionListAdapter.setPositionHashMap(positions);
                        // 데이터가 바뀌었음을 알린다.
                        positionListAdapter.notifyDataSetChanged();
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.account_selector_item, accountList);
        adapter.setDropDownViewResource(R.layout.account_selector_item);
        accountSpinner.setAdapter(adapter);

    }
}
