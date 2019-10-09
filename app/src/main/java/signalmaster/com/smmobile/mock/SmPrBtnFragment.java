package signalmaster.com.smmobile.mock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import signalmaster.com.smmobile.R;

public class SmPrBtnFragment extends Fragment {

    Button rsvBuyingBtn;
    Button rsvSellingBtn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sm_btn_fragment,null);
        ButterKnife.bind(this,view);

        rsvBuyingBtn = (Button)getActivity().findViewById(R.id.rsvBuyingBtn);
        rsvSellingBtn = (Button)getActivity().findViewById(R.id.rsvSellingBtn);


        /*rsvBuyingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rsvSellingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        return view;
    }


}
