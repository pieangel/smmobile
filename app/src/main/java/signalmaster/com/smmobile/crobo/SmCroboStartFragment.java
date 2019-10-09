package signalmaster.com.smmobile.crobo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;

public class SmCroboStartFragment extends Fragment {

    public static SmCroboStartFragment newInstance(){
        return new SmCroboStartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextView nextTxt;
    SmCroboFragment smCroboFragment;
    Fragment smCroboQuestionFragment;
    //public boolean saveFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sm_crobo_start_fragment, container, false);


        //saveFragment = true;

        //smCroboFragment = SmCroboFragment.newInstance();
        smCroboQuestionFragment = SmCroboQuestionFragment.newInstance();
        nextTxt = (TextView)view.findViewById(R.id.nextTxt);
        nextTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmArgManager smArgManager = SmArgManager.getInstance();
                smCroboFragment = (SmCroboFragment)smArgManager.getVal("survey","fragment");
                smCroboFragment.setChildFragment(smCroboQuestionFragment);

                //((MainActivity)getActivity()).replaceFragment(SmCroboQuestionFragment.newInstance());

            }
        });


        return view;
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        /*SmArgManager smArgManager = SmArgManager.getInstance();
        smArgManager.registerToMap("SmCroboStartFragment","fragment",this);*/
    }
}
