package signalmaster.com.smmobile.crobo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xw.repo.BubbleList;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.Locale;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.R;
import signalmaster.com.smmobile.Util.SmArgManager;

public class SmCroboLevelFragment extends Fragment {

    public static SmCroboLevelFragment newInstance(){
        SmCroboLevelFragment fragment = new SmCroboLevelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    SmCroboFragment smCroboFragment;
    SmCroboStartFragment smCroboStartFragment;

    public BubbleSeekBar seekBar;
    BubbleList bubbleList = new BubbleList();

    private ArrayList<ArrayList<String>> levelArray = new ArrayList<>();

    private void initLevelArray(){
        levelArray.add(bubbleList.getLevelQuestion());
    }

    TextView changeType,againTxt,endTxt;
    SmCroboQuestionFragment smCroboQuestionFragment = SmCroboQuestionFragment.newInstance();
    LinearLayout operationBackLout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sm_crobo_level_fragment, null);
        initLevelArray();

        //시크바 gone 하기위해
        SmArgManager smArgManager = SmArgManager.getInstance();
        smArgManager.registerToMap("survey","levelFragment",this);

        smCroboFragment = SmCroboFragment.newInstance();
        smCroboStartFragment = SmCroboStartFragment.newInstance();

        changeType = (TextView)view.findViewById(R.id.changeType);
        againTxt = (TextView)view.findViewById(R.id.againTxt);
        endTxt = (TextView)view.findViewById(R.id.endTxt);
        operationBackLout = (LinearLayout)view.findViewById(R.id.operationBackLout);



        changeType.setText((smArgManager.getVal("survey","changeType").toString()));

        seekBar = (BubbleSeekBar)view.findViewById(R.id.operationSeekbar);
        seekBar.setQuestionList(levelArray.get(0));
        seekBar.setmShowBubble(true);
        seekBar.getConfigBuilder().progress(2).build();

        seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                //String s = String.format(Locale.CHINA, "onActionUp int:%d, float:%.1f", progress, progressFloat);
                String s = String.format(Locale.CHINA, "%d", progress, progressFloat);
               /* moveList.add(s);
                answerList.add(moveList.get(moveList.size()-1));
                        *//*if(moveList.size() == 0){
                            answerList.add("2");
                        }*//*
                moveList.clear();*/
            }
        });

        /*operationBackLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(SmCroboResultFragment.newInstance());
            }
        });*/


        againTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SmArgManager smArgManager = SmArgManager.getInstance();
                smCroboFragment = (SmCroboFragment)smArgManager.getVal("survey","fragment");
                smCroboFragment.setChildFragment(smCroboStartFragment);
                //((MainActivity)getActivity()).replaceFragment(SmCroboStartFragment.newInstance());
                smCroboQuestionFragment.getAnswerList().clear();
                smCroboQuestionFragment.resultValue = 0;
                seekBar.setmShowBubble(false);
            }
        });

        endTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //seekBar.setmShowBubble(false);
                Toast.makeText(getContext(), "설문이 끝났습니다. 처음으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                SmArgManager smArgManager = SmArgManager.getInstance();
                smCroboFragment = (SmCroboFragment)smArgManager.getVal("survey","fragment");
                smCroboFragment.setChildFragment(smCroboStartFragment);
                //((MainActivity)getActivity()).replaceFragment(SmCroboStartFragment.newInstance());
                smCroboQuestionFragment.getAnswerList().clear();
                smCroboQuestionFragment.resultValue = 0;
                seekBar.setmShowBubble(false);
            }
        });

        return view;
    }
}
