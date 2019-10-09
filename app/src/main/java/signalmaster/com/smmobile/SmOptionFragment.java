package signalmaster.com.smmobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import signalmaster.com.smmobile.Adapter.OptionAdapter;
import signalmaster.com.smmobile.chart.SmChartFragment;
import signalmaster.com.smmobile.chart.SmChartType;
import signalmaster.com.smmobile.data.SmChartData;
import signalmaster.com.smmobile.data.SmChartDataManager;
import signalmaster.com.smmobile.mock.SmPrChartFragment;
import signalmaster.com.smmobile.ui.SmChartTypeOption;

import static android.content.ContentValues.TAG;

public class SmOptionFragment extends Fragment {

    public static SmOptionFragment newInstance(){
        SmOptionFragment fragment = new SmOptionFragment();
        Bundle args =  new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private SmPrChartFragment parentFragment = null;

    public void setParentFragment(SmPrChartFragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    private OptionAdapter _optionAdapter = null;

    private int selectPosition = -1;
    SmOptionFragment self = this;

    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _adapter;
    SmOptionList smOptionList = new SmOptionList();
    ArrayList<String> _optionList = (smOptionList.get_optionList());

    //private OptionAdapter.ViewHolder viewHolder = ((OptionAdapter)_adapter).getViewHolder();
    //private OptionAdapter.ViewHolder viewHolder = null;

    public SmOptionFragment() {

    }

    private int selectedPosition = -1;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.sm_option_list, container, false);


        _recyclerView = (RecyclerView) v.findViewById(R.id.SmOptionList);
        //_recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        _recyclerView.setLayoutManager(layoutManager);

        _adapter = new OptionAdapter(getContext(), _optionList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                changeChartData(position);
                ((OptionAdapter)_adapter).setBorderColor("#000000", "#c0c0c0", position);

            }
        });
        _adapter.notifyDataSetChanged();


        _recyclerView.setAdapter(_adapter);


        /*_optionAdapter = new OptionAdapter(optionList);
        _recyclerView.setAdapter(_optionAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        _recyclerView.setLayoutManager(layoutManager);*/


        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.


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





        return v;

    }

    private void changeChartData(int position) {
        SmChartTypeOption option = SmChartTypeOption.M1;
        switch (position) {
            case 0:
                option = SmChartTypeOption.M5;
                break;
            case 1:
                option = SmChartTypeOption.M15;
                break;
            case 2:
                option = SmChartTypeOption.M30;
                break;
            case 3:
                option = SmChartTypeOption.M60;
                break;
            case 4:
                option = SmChartTypeOption.D1;
                break;
            case 5:
                option = SmChartTypeOption.W1;
                break;
            default:
                option = SmChartTypeOption.M1;
                break;
        }
        if (parentFragment != null) {
            parentFragment.changeChartData(option);
        }

        if (siseChartActivity != null) {
            siseChartActivity.changeChartData(option );
        }
    }

    private SmSiseChartActivity siseChartActivity = null;
    public void setSiseChartActivity(SmSiseChartActivity siseChartActivity) {
        this.siseChartActivity = siseChartActivity;
    }
}
