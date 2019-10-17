package signalmaster.com.smmobile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import signalmaster.com.smmobile.account.SmAccount;
import signalmaster.com.smmobile.account.SmAccountManager;

public class AccountActivity extends AppCompatActivity {

    ImageView closeImg;
    RecyclerView profitRecyclerView;
    //RightOptionAdapter rightOptionAdapter = new RightOptionAdapter();
    RightOptionAdapter2 rightOptionAdapter2 = new RightOptionAdapter2();


    TextView inital_balance,totalPLTxt;

    Spinner accountSpinner;
    ArrayAdapter<String> adapter;

    private ArrayList<String> accountList = new ArrayList<>();
    private void account(){
        accountList.add(acList.get(0).accountNo);
        /*accountList.add("계좌2");
        accountList.add("계좌3");*/
    }

    SmAccountManager smAccountManager = SmAccountManager.getInstance();
    ArrayList<SmAccount> acList = smAccountManager.getAccountList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        if(acList.size() > 0) {
            account();
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
        rightOptionAdapter2 = new RightOptionAdapter2();
        profitRecyclerView.setAdapter(rightOptionAdapter2);



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
        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.account_selector_item,accountList);
        adapter.setDropDownViewResource(R.layout.account_selector_item);
        accountSpinner.setAdapter(adapter);

    }
}
