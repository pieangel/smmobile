package signalmaster.com.smmobile.sise;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scichart.core.common.Action1;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmLayoutManager;
import signalmaster.com.smmobile.data.SmChartDataService;
import signalmaster.com.smmobile.market.SmMarket;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.symbol.SmSymbol;

import static com.scichart.core.utility.Dispatcher.runOnUiThread;

public class SmCurrentFragment extends Fragment {

    public static SmCurrentFragment newInstance(){
        SmCurrentFragment fragment = new SmCurrentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private SubbarAdapter _subBarAdapter = null;
    private SubListAdapter _subListAdapter = null;

    private RecyclerView _subBarRecyclerView = null;
    private RecyclerView _subListRecyclerView = null;

    private ArrayList<SmSymbol> symList = null;

    public RecyclerView get_subBarRecyclerView() {
        return _subBarRecyclerView;
    }

    private SmSubbarFragment _smSubbarFragment = null;

    SmMarketManager smMarketManager = SmMarketManager.getInstance();
    ArrayList<SmMarket> _marketList = (smMarketManager.get_marketList());

    Timer timer;
    TimerTask timerTask;



    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 10, 1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        _subListAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }

    public void OnMarketChanged(ArrayList<SmSymbol> symList) {

        _subListAdapter.setSymbolList(symList);
        _subListAdapter.notifyDataSetChanged();

    }

    public SmCurrentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.sm_current_fragment, container, false);

        //Fragment
        /*_frameLayout = (FrameLayout)view.findViewById(R.id.subListContainer);
        _smSubbarFragment = new SmSubbarFragment();
        FragmentManager fragmentManager = _context.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.subListContainer,_smSubbarFragment);
        fragmentTransaction.commit();*/

        //SubbarAdapter
        _subBarRecyclerView = (RecyclerView)view.findViewById(R.id.SmSubMenu);
        //_subBarRecyclerView = new RecyclerView(this.getContext());
        SmMarketManager smMarketManager = SmMarketManager.getInstance();
        _subBarAdapter = new SubbarAdapter(smMarketManager.get_marketList());
        _subBarAdapter.setParentFragment(_smSubbarFragment);
        _subBarAdapter.set_currentFragment(this);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((getContext()),LinearLayoutManager.HORIZONTAL,false);
        _subBarRecyclerView.setLayoutManager(layoutManager);
        _subBarRecyclerView.setAdapter(_subBarAdapter);



        SmLayoutManager arg = SmLayoutManager.getInstance();
        arg.register("R.id.SmSubMenu", _subBarRecyclerView );
        arg.register("R.id.SmCurrentFragment", this);


        final int itemTotalCount = _subBarRecyclerView.getAdapter().getItemCount()-3;
        final int lastVisibleItemPosition = ((LinearLayoutManager)_subBarRecyclerView.getLayoutManager()).findLastVisibleItemPosition();


        //SubListRecyclerView add .. 추가
        _subListRecyclerView = (RecyclerView) view.findViewById(R.id.SmSubList);

        SmMarket mrkt = _marketList.get(0);
        //ArrayList<SmSymbol> symList = mrkt.getRecentSymbolListFromCategory();
         symList = mrkt.getRecentSymbolListFromCategory();

        _subListAdapter = new SubListAdapter(symList);
        _subListRecyclerView.setAdapter(_subListAdapter);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        _subListRecyclerView.setLayoutManager(layoutManager2);

        _subListRecyclerView.getItemAnimator().setChangeDuration(0);
        SmChartDataService chartDataService = SmChartDataService.getInstance();
        chartDataService.subscribeSise(onUpdateSise(), this.getClass().getSimpleName());

        return view;
    }

    public void onClickItem(int position) {
        SmMarketManager smMarketManager = SmMarketManager.getInstance();
        ArrayList<SmMarket> _marketList = (smMarketManager.get_marketList());
        if (_marketList.size() == 0)
            return;
        //SmMarket mrkt = _marketList.get(position);
        //ArrayList<SmSymbol> symList =  mrkt.getRecentSymbolListFromCategory();
        //_smSubbarFragment.OnMarketChanged(symList);
        if (_smSubbarFragment != null) {
            _smSubbarFragment.set_curMarketPostion(position);
        }
    }

    public void setMarketPosition(int position) {
        if (_smSubbarFragment != null)
            _smSubbarFragment.set_curMarketPostion(position);
    }


    @NonNull
    private synchronized Action1<SmSymbol> onUpdateSise() {
        return new Action1<SmSymbol>() {
            @Override
            public void execute(final SmSymbol symbol) {
                if (symbol == null)
                    return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       ValueChange(symbol);
                    }
                });
            }
        };
    }

    public void ValueChange(SmSymbol symbol){

        //깜빡거림현상 제거
        //onCreateView -> recyclerView.getItemAnimator().setChangeDuration(0);

        //리스트에 있는 코드와 들어오는 심볼 코드값이 같을 경우 해당항목 업데이트
        for(int i = 0; i<=_subListAdapter.get_symList().size()-1;i++){
            if(_subListAdapter.get_symList().get(i).code == symbol.code){
                _subListAdapter.notifyItemChanged(i);
                break;
            }
        }

    }

}
