package signalmaster.com.smmobile.fund;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import signalmaster.com.smmobile.R;

public class SmFundFragment extends Fragment {

    public static SmFundFragment newInstance(){
        SmFundFragment fragment = new SmFundFragment();
        Bundle args =  new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // view = inflater.inflate(R.layout.sm_fund_fragment,null);
        View view = inflater.inflate(R.layout.sm_fund_fragment, container, false);

        return view;
    }
}
