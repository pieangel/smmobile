package signalmaster.com.smmobile.portfolio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.RecyclerViewList;
import signalmaster.com.smmobile.Util.SmArgManager;

public class PortfolioDialog extends AppCompatActivity {

    LinearLayout Lout;
    TextView addTxt;
    ImageView closeImg;
    RecyclerView portRecyclerView;
    RecyclerViewList recyclerViewList = new RecyclerViewList();
    PortfolioSelectAdapter portfolioSelectAdapter;
    ArrayList<String> portfolioList = recyclerViewList.getPortfolioList();

    int selectPosition = -1;

    SmPortfolioFragment smPortfolioFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfolio_dialog);

        addTxt = (TextView)findViewById(R.id.addTxt);
        portRecyclerView = (RecyclerView)findViewById(R.id.portRecyclerView);
        closeImg = (ImageView)findViewById(R.id.closeImg);


        SmArgManager smArgManager = SmArgManager.getInstance();
        smPortfolioFragment = (SmPortfolioFragment)smArgManager.getVal("portFragment","fragment");

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        portfolioSelectAdapter = new PortfolioSelectAdapter(portfolioList);
        portRecyclerView.setAdapter(portfolioSelectAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.context);
        portRecyclerView.setLayoutManager(layoutManager);

        /*portfolioSelectAdapter.setOnItemClickListener(new PortfolioSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v,final int pos) {

                *//*Lout = v.findViewById(R.id.Lout);
                selectPosition = pos;
                portfolioSelectAdapter.notifyDataSetChanged();

                if(selectPosition == pos){
                    Lout.setBackgroundResource(R.drawable.symbol_selector_button);
                } else{
                    Lout.setBackgroundResource(R.drawable.symbol_selector_style);
                }*//*

                *//*Lout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*//*

            }
        });*/

        PortfolioManager portMgr = PortfolioManager.getInstance();
        portMgr.getPortList();

        addTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPosition = portfolioSelectAdapter.getSelectedPosition();

                if(selectPosition >= 0) {
                    smPortfolioFragment.addMyPortfolio(selectPosition);
                    finish();
                }
            }
        });

    }
}
