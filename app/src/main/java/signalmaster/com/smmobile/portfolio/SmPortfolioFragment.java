package signalmaster.com.smmobile.portfolio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.RecyclerViewList;
import signalmaster.com.smmobile.Util.SmArgManager;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.symbol.SmSymbol;
import signalmaster.com.smmobile.symbol.SmSymbolManager;
import signalmaster.com.smmobile.symbol.SmSymbolSelector;

public class SmPortfolioFragment extends Fragment {

    public static SmPortfolioFragment newInstance(){
        SmPortfolioFragment fragment = new SmPortfolioFragment();
        Bundle args =  new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    SmPortfolioFragment self = this;
    TextView clickSymbol1,clickSymbol2,clickSymbol3,
            valueSymbol1,valueSymbol2,valueSymbol3;

    Button croboBtn,myBtn;
    RecyclerView croboRecyclerView,myRecyclerView;
    PortFolioAdapter portFolioAdapter;
    MyPortfolioAdapter myPortfolioAdapter;
    RecyclerViewList recyclerViewList = new RecyclerViewList();
    ArrayList<String> portfolioList = recyclerViewList.getPortfolioList();
    ArrayList<String> myPortfolioList = recyclerViewList.getMyPortfolioList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.sm_portfolio_fragment,null);

        //리사이클러뷰
        croboRecyclerView = view.findViewById(R.id.croboRecyclerView);
        myRecyclerView = view.findViewById(R.id.myRecyclerView);

        //c로보 추천 포트폴리오
        portFolioAdapter = new PortFolioAdapter(portfolioList);
        croboRecyclerView.setAdapter(portFolioAdapter);
        croboRecyclerView.setVisibility(View.VISIBLE);
        myRecyclerView.setVisibility(View.INVISIBLE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.context);
        croboRecyclerView.setLayoutManager(layoutManager);


        //my 포트폴리오
        myPortfolioAdapter = new MyPortfolioAdapter(myPortfolioList);
        myRecyclerView.setAdapter(myPortfolioAdapter);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(MainActivity.context);
        myRecyclerView.setLayoutManager(layoutManager1);

        portFolioAdapter.setOnItemClickListener(new PortFolioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                /*Intent intent = new Intent(getActivity(), SmSymbolSelector.class);
                startActivity(intent);
                SmArgManager argManager = SmArgManager.getInstance();
                argManager.registerToMap("mock_symbol_select","portfolio_fragment",this);

                onSelectSymbol(sym_code);*/
                /*title = v.findViewById(R.id.title);
                title.setText(portfolioList.get(pos));*/

                clickSymbol1 =  v.findViewById(R.id.clickSymbol1);
                clickSymbol2 = v.findViewById(R.id.clickSymbol2);
                clickSymbol3 = v.findViewById(R.id.clickSymbol3);

                valueSymbol1 = v.findViewById(R.id.valueSymbol1);
                valueSymbol2 = v.findViewById(R.id.valueSymbol2);
                valueSymbol3 = v.findViewById(R.id.valueSymbol3);

                clickSymbol1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SmSymbolSelector.class);
                        startActivity(intent);
                        SmArgManager argManager = SmArgManager.getInstance();
                        argManager.registerToMap("portfolio_symbol_select","portfolio_fragment",self);
                        argManager.registerToMap("portfolio_symbol_select","clickSymbol",clickSymbol1);
                        argManager.registerToMap("portfolio_symbol_select","valueSymbol",valueSymbol1);
                        //여기서 SmSymbolSelector에 값을 보내주고 onSelectSymbol함수를 호출해서 값을 변경해준다 .
                    }
                });
                clickSymbol2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SmSymbolSelector.class);
                        startActivity(intent);
                        SmArgManager argManager = SmArgManager.getInstance();
                        argManager.registerToMap("portfolio_symbol_select","portfolio_fragment",self);
                        argManager.registerToMap("portfolio_symbol_select","clickSymbol",clickSymbol2);
                        argManager.registerToMap("portfolio_symbol_select","valueSymbol",valueSymbol2);
                        //여기서 SmSymbolSelector에 값을 보내주고 onSelectSymbol함수를 호출해서 값을 변경해준다 .
                    }
                });
                clickSymbol3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SmSymbolSelector.class);
                        startActivity(intent);
                        SmArgManager argManager = SmArgManager.getInstance();
                        argManager.registerToMap("portfolio_symbol_select","portfolio_fragment",self);
                        argManager.registerToMap("portfolio_symbol_select","clickSymbol",clickSymbol3);
                        argManager.registerToMap("portfolio_symbol_select","valueSymbol",valueSymbol3);
                        //여기서 SmSymbolSelector에 값을 보내주고 onSelectSymbol함수를 호출해서 값을 변경해준다 .
                    }
                });
                //clickSymbol1.setText(sym_code.seriesNmKr);

            }
        });

        croboBtn = view.findViewById(R.id.croboBtn);
        myBtn = view.findViewById(R.id.myBtn);
        croboBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            croboRecyclerView.setVisibility(View.VISIBLE);
            myRecyclerView.setVisibility(View.INVISIBLE);
            }
        });
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            myRecyclerView.setVisibility(View.VISIBLE);
            croboRecyclerView.setVisibility(View.INVISIBLE);
            }
        });




        return view;
    }

    //SmSymbol sym_code = null;
    public void onSelectSymbol(SmSymbol sym_code, TextView clickSymbol, TextView valueSymbol){
        if (sym_code == null) {
            return;
        }

        PortfolioManager port = PortfolioManager.getInstance();

        SmSymbolManager symMgr = SmSymbolManager.getInstance();
        SmSymbol sym = symMgr.findSymbol(sym_code.code);
        if (sym != null) {
            //SmMarketManager smMarketManager = SmMarketManager.getInstance();
            //clickSymbol1.setText(sym.seriesNmKr);

            String[] splitTitle = sym.getSplitName(sym.seriesNmKr);
            clickSymbol.setText(splitTitle[0]);

            double close = sym.quote.C;
            close = close / Math.pow(10, sym.decimal);

            valueSymbol.setText(Double.toString(close));



            portFolioAdapter.notifyDataSetChanged();

        }
    }

    public void openDialog(){
        Intent intent = new Intent(getActivity(), PortfolioDialog.class);
        SmArgManager smArgManager = SmArgManager.getInstance();
        smArgManager.registerToMap("portFragment","fragment",this);
         startActivity(intent);
    }

    public void addMyPortfolio(int selectPosition){
        myPortfolioList.add(portfolioList.get(selectPosition));
        myPortfolioAdapter.notifyDataSetChanged();
        myRecyclerView.setAdapter(myPortfolioAdapter);

    }
}
