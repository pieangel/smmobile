package signalmaster.com.smmobile.sise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.market.SmMarket;
import signalmaster.com.smmobile.market.SmMarketManager;
import signalmaster.com.smmobile.symbol.SmSymbol;

public class SmSubbarFragment extends Fragment {




    public void set_curMarketPostion(int _curMarketPostion) {
        if (_subListAdapter != null) {
            _subListAdapter.set_curMarketPostion(_curMarketPostion);
        }
    }

    private SubListAdapter _subListAdapter = null;

    public SubListAdapter get_subListAdapter() {
        return _subListAdapter;
    }

    public RecyclerView _recyclerView;
    private RecyclerView.Adapter _adapter;

    SmMarketManager smMarketManager = SmMarketManager.getInstance();
    ArrayList<SmMarket> _marketList = (smMarketManager.get_marketList());

    public void OnMarketChanged(ArrayList<SmSymbol> symList) {
        if (_recyclerView != null)
            _recyclerView.getRecycledViewPool().clear();
            _subListAdapter.notifyItemRemoved(symList.size());
        if (_subListAdapter != null) {

            _subListAdapter.setSymbolList(symList);

            _subListAdapter.notifyDataSetChanged();
        }

    }

    public SmSubbarFragment() {

    }

    RecyclerView.LayoutManager layoutManager = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.subbarlist, container, false);
        _recyclerView = (RecyclerView) view.findViewById(R.id.SmSubList);

        if (_marketList.size() > 0) {
            //add
            SmMarket mrkt = _marketList.get(0);
            ArrayList<SmSymbol> symList = mrkt.getRecentSymbolListFromCategory();

            _subListAdapter = new SubListAdapter(symList);
            _recyclerView.setAdapter(_subListAdapter);
            layoutManager = new LinearLayoutManager(getActivity());
            _recyclerView.setLayoutManager(layoutManager);
        }




        /*// Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView mRecyclerView = (RecyclerView) view;
            mRecyclerView.setHasFixedSize(true);
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(context);
            mRecyclerView.setLayoutManager(mLayoutManager);
            // specify an adapter (see also next example)
           //mAdapter = new Collection<>();
            mRecyclerView.setAdapter(mAdapter);
        }*/



        return view;

    }

}
